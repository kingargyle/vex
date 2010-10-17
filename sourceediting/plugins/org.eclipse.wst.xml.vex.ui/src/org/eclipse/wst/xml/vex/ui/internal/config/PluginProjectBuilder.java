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
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IDecoratorManager;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.xml.vex.ui.internal.VexPlugin;

/**
 * Parses and registers Vex configuration objects in a Vex Plug-in project.
 */
public class PluginProjectBuilder extends IncrementalProjectBuilder {

	public static final String ID = "org.eclipse.wst.xml.vex.ui.pluginBuilder"; //$NON-NLS-1$

	final IBuildProblemHandler buildProblemHandler = new IBuildProblemHandler() {
		public void foundProblem(final BuildProblem problem) {
			try {
				final IResource resource = getProject().getFile(problem.getResourcePath());
				markError(resource, problem.getMessage(), problem.getLineNumber());
			} catch (final CoreException e) {
				VexPlugin.getInstance().getLog().log(e.getStatus());
			}
		}
	};

	@Override
	protected IProject[] build(final int kind, @SuppressWarnings("rawtypes") final Map args, final IProgressMonitor monitor) throws CoreException {
		final IProject project = getProject();
		final IMarker[] oldMarkers = project.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		final IResource[] markedResources = new IResource[oldMarkers.length];
		for (int i = 0; i < markedResources.length; i++)
			markedResources[i] = oldMarkers[i].getResource();

		project.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		updateMarkedResourcesDecoration(markedResources);

		final PluginProject pluginProject = new PluginProject(project);
		if (getProject().getFile(PluginProject.PLUGIN_XML).exists())
			pluginProject.parseConfigXml(buildProblemHandler);
		else {
			final String message = MessageFormat.format(Messages.getString("PluginProjectBuilder.missingFile"), //$NON-NLS-1$
					new Object[] { PluginProject.PLUGIN_XML });
			markError(getProject(), message);
		}

		pluginProject.parseResources(buildProblemHandler);
		return null;
	}

	@Override
	protected void clean(final IProgressMonitor monitor) throws CoreException {
		getProject().deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		// trigger reload to get a clean and consistent state
		ConfigurationRegistry.INSTANCE.loadConfigurations(); 
	}

	private static void markError(final IResource resource, final String message) throws CoreException {
		markError(resource, message, -1);
	}

	private static void markError(final IResource resource, final String message, final int lineNumber) throws CoreException {
		final IMarker marker = resource.createMarker(IMarker.PROBLEM);
		if (!marker.exists())
			return;
		marker.setAttribute(IMarker.MESSAGE, message);
		marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
		marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		if (lineNumber > 0)
			marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
		updateMarkedResourcesDecoration(resource);
	}

	private static void updateMarkedResourcesDecoration(IResource... resources) {
		getBuildProblemDecorator().update(resources);
	}
	private static BuildProblemDecorator getBuildProblemDecorator() {
		final IDecoratorManager decorationManager = PlatformUI.getWorkbench().getDecoratorManager();
		return (BuildProblemDecorator) decorationManager.getBaseLabelProvider(BuildProblemDecorator.ID);
	}

}
