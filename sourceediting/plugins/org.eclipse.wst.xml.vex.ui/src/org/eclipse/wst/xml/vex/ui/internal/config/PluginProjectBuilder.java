/*******************************************************************************
 * Copyright (c) 2004, 2008 John Krasnay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     John Krasnay - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.ui.internal.config;

import java.text.MessageFormat;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IDecoratorManager;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.xml.vex.ui.internal.VexPlugin;
import org.xml.sax.SAXParseException;

/**
 * Parses and registers Vex configuration objects in a Vex Plug-in project.
 */
public class PluginProjectBuilder extends IncrementalProjectBuilder {

	public static final String ID = "org.eclipse.wst.xml.vex.ui.pluginBuilder"; //$NON-NLS-1$

	@Override
	protected IProject[] build(final int kind, @SuppressWarnings("rawtypes") final Map args, final IProgressMonitor monitor) throws CoreException {

		final IProject project = getProject();

		final PluginProject pluginProject = ConfigurationRegistry.INSTANCE.getPluginProject(project);

		if (pluginProject == null) {
			final String message = MessageFormat.format(Messages.getString("PluginProjectBuilder.notConfigSource"), //$NON-NLS-1$
					new Object[] { project.getName() });
			VexPlugin.getInstance().log(IStatus.ERROR, message);
			return null;
		}

		boolean parseConfigXml;

		final IResourceDelta delta = getDelta(project);

		if (kind == FULL_BUILD || delta == null) {

			// System.out.println("PluginProjectBuilder.build (full) starts for project "
			// + project.getName());

			clean(null);
			parseConfigXml = true;

		} else { // incremental or auto build

			// System.out.println("PluginProjectBuilder.build (incremental) starts for project "
			// + project.getName());

			parseConfigXml = delta.findMember(new Path(PluginProject.PLUGIN_XML)) != null;

			// If a resource is deleted, renamed, or moved, we'll update the
			// config, but only if we're not going to parse it.
			final boolean canUpdateConfig = !parseConfigXml;

			final IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
				public boolean visit(final IResourceDelta delta) throws CoreException {
					final IResource resource = delta.getResource();
					final String path = resource.getProjectRelativePath().toString();
					pluginProject.removeResource(path);

					if (delta.getKind() == IResourceDelta.REMOVED) {

						final ConfigItem item = pluginProject.getItemForResource(path);

						if (item == null)
							return true;

						if (canUpdateConfig && (delta.getFlags() & IResourceDelta.MOVED_TO) > 0) {
							// Resource was moved.
							final String newPath = delta.getMovedToPath().removeFirstSegments(1).toString();
							item.setResourcePath(newPath);
						} else
							// Resource deleted, so let's nuke the item from the
							// config
							pluginProject.remove(item);

						try {
							pluginProject.writeConfigXml();
						} catch (final Exception ex) {
							final String message = MessageFormat.format(Messages.getString("PluginProjectBuilder.cantSaveFile"), //$NON-NLS-1$
									new Object[] { PluginProject.PLUGIN_XML });

							VexPlugin.getInstance().log(IStatus.ERROR, message, ex);
						}
					}

					return true;
				}
			};

			delta.accept(visitor);
		}

		final IMarker[] oldMarkers = project.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		final IResource[] markedResources = new IResource[oldMarkers.length];
		for (int i = 0; i < markedResources.length; i++)
			markedResources[i] = oldMarkers[i].getResource();

		project.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		getBuildProblemDecorator().update(markedResources);

		final ConfigurationRegistryImpl registry = (ConfigurationRegistryImpl) ConfigurationRegistry.INSTANCE;

		try {
			registry.lock();

			if (parseConfigXml) {
				final IResource pluginXmlResource = getProject().getFile(PluginProject.PLUGIN_XML);
				try {
					if (pluginXmlResource.exists())
						pluginProject.parseConfigXml();
					else {
						pluginProject.removeAllItems();
						final String message = MessageFormat.format(Messages.getString("PluginProjectBuilder.missingFile"), //$NON-NLS-1$
								new Object[] { PluginProject.PLUGIN_XML });
						this.flagError(getProject(), message);
					}
				} catch (final SAXParseException ex) {
					this.flagError(pluginXmlResource, ex.getLocalizedMessage(), ex.getLineNumber());
				} catch (final Exception ex) {
					final String message = MessageFormat.format(Messages.getString("PluginProjectBuilder.parseError"), //$NON-NLS-1$
							new Object[] { PluginProject.PLUGIN_XML });
					VexPlugin.getInstance().log(IStatus.ERROR, message, ex);
					this.flagError(pluginXmlResource, ex.getLocalizedMessage());
				}
			}

			final IBuildProblemHandler problemHandler = new IBuildProblemHandler() {
				public void foundProblem(final BuildProblem problem) {
					try {
						final IResource resource = getProject().getFile(problem.getResourcePath());
						flagError(resource, problem.getMessage(), problem.getLineNumber());
					} catch (final CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};

			pluginProject.parseResources(problemHandler);
		} finally {
			registry.unlock();
		}

		registry.fireConfigChanged(new ConfigEvent(this));

		return null;
	}

	@Override
	protected void clean(final IProgressMonitor monitor) throws CoreException {
		final ConfigurationRegistryImpl registry = (ConfigurationRegistryImpl) ConfigurationRegistry.INSTANCE;
		try {
			registry.lock();
			final PluginProject pluginProject = ConfigurationRegistry.INSTANCE.getPluginProject(getProject());
			if (pluginProject != null) {
				pluginProject.removeAllItems();
				pluginProject.removeAllResources();
				registry.fireConfigChanged(new ConfigEvent(this));
			}
		} finally {
			registry.unlock();
		}
	}

	// ======================================================== PRIVATE

	private BuildProblemDecorator buildProblemDecorator;

	private void flagError(final IResource resource, final String message) throws CoreException {
		flagError(resource, message, -1);
	}

	private void flagError(final IResource resource, final String message, final int lineNumber) throws CoreException {
		final IMarker marker = resource.createMarker(IMarker.PROBLEM);
		if (marker.exists()) {
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
			if (lineNumber > 0)
				marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
			getBuildProblemDecorator().update(resource);
		}
	}

	private BuildProblemDecorator getBuildProblemDecorator() {
		if (buildProblemDecorator == null) {
			final IDecoratorManager dm = PlatformUI.getWorkbench().getDecoratorManager();
			buildProblemDecorator = (BuildProblemDecorator) dm.getBaseLabelProvider(BuildProblemDecorator.ID);
		}
		return buildProblemDecorator;
	}

}
