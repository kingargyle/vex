/*******************************************************************************
 * Copyright (c) 2004, 2008 John Krasnay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     John Krasnay - initial API and implementation
 *     Ed Burnette - 7/23/2006 -  Changes needed to build on 3.2.
 *     Igor Jacy Lino Campista - Java 5 warnings fixed (bug 311325)
 *******************************************************************************/
package org.eclipse.wst.xml.vex.ui.internal.config;

import java.text.MessageFormat;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.wst.xml.vex.ui.internal.VexPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;

/**
 * Job that loads Vex configuration objects from plugins and plugin projects.
 */
public class ConfigLoaderJob extends Job {

	/**
	 * Class constructor.
	 */
	public ConfigLoaderJob() {
		super(Messages.getString("ConfigLoaderJob.loadingConfig")); //$NON-NLS-1$
	}

	protected IStatus run(IProgressMonitor monitor) {

		// System.out.println("ConfigLoaderJob starts");

		int pluginCount = Platform.getExtensionRegistry().getNamespaces().length;
		int projectCount = ResourcesPlugin.getWorkspace().getRoot().getProjects().length;

		monitor.beginTask(Messages.getString("ConfigLoaderJob.loadingConfig"), pluginCount + projectCount); //$NON-NLS-1$

		this.loadPlugins(monitor);
		this.loadPluginProjects(monitor);
		ConfigRegistry.getInstance().fireConfigLoaded(new ConfigEvent(this));

		monitor.done();

		// System.out.println("ConfigLoaderJob ends");

		return Status.OK_STATUS;
	}

	// ======================================================= PRIVATE

	/**
	 * Load configurations from all registered plugins.
	 */
	private void loadPlugins(IProgressMonitor monitor) {
		final ConfigRegistry configRegistry = ConfigRegistry.getInstance();
		final IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		for (final String namespace : extensionRegistry.getNamespaces()) {
			final Bundle bundle = Platform.getBundle(namespace);
			if (bundle == null)
				continue;

			final String name = (String) bundle.getHeaders().get(Constants.BUNDLE_NAME);
			monitor.subTask(Messages.getString("ConfigLoaderJob.loading") + name); //$NON-NLS-1$

			final ConfigSource source = ConfigPlugin.load(namespace);
			if (source != null)
				configRegistry.addConfigSource(source);
			monitor.worked(1);
		}
	}

	/**
	 * Load configurations from all Vex Plugin Projects in the workspace.
	 */
	private void loadPluginProjects(IProgressMonitor monitor) {

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] projects = root.getProjects();

		for (IProject project : projects) {
			try {
				if (project.isOpen() && project.hasNature(PluginProjectNature.ID)) {
					monitor.subTask(Messages.getString("ConfigLoaderJob.loadingProject") + project.getName()); //$NON-NLS-1$
					PluginProject.load(project);
					monitor.worked(1);
				}
			} catch (CoreException e) {
				String message = MessageFormat.format(Messages.getString("ConfigLoaderJob.natureError"), //$NON-NLS-1$
						new Object[] { project.getName() });
				VexPlugin.getInstance().log(IStatus.ERROR, message, e);
			}
		}
	}

}
