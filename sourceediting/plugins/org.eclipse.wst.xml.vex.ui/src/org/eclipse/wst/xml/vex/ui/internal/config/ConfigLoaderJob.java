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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.wst.xml.vex.ui.internal.VexPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;

/**
 * Job that loads Vex configuration objects from plug-ins and plug-in projects.
 */
public class ConfigLoaderJob extends Job implements ConfigurationLoader {

	private List<ConfigSource> loadedConfigSources = Collections.<ConfigSource> emptyList();

	public ConfigLoaderJob() {
		super(Messages.getString("ConfigLoaderJob.loadingConfig")); //$NON-NLS-1$
	}

	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		final int pluginCount = Platform.getExtensionRegistry().getNamespaces().length;
		final int projectCount = ResourcesPlugin.getWorkspace().getRoot().getProjects().length;

		monitor.beginTask(Messages.getString("ConfigLoaderJob.loadingConfig"), pluginCount + projectCount); //$NON-NLS-1$
		final ArrayList<ConfigSource> result = new ArrayList<ConfigSource>();
		result.addAll(loadPlugins(monitor));
		result.addAll(loadPluginProjects(monitor));
		synchronized (this) {
			loadedConfigSources = result;
		}
		monitor.done();

		return Status.OK_STATUS;
	}

	private static List<ConfigSource> loadPlugins(final IProgressMonitor monitor) {
		final ArrayList<ConfigSource> result = new ArrayList<ConfigSource>();
		final IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		for (final String namespace : extensionRegistry.getNamespaces()) {
			final Bundle bundle = Platform.getBundle(namespace);
			if (bundle == null)
				continue;

			final String name = (String) bundle.getHeaders().get(Constants.BUNDLE_NAME);
			monitor.subTask(Messages.getString("ConfigLoaderJob.loading") + name); //$NON-NLS-1$

			final ConfigPlugin configPlugin = new ConfigPlugin(namespace);
			if (!configPlugin.isEmpty())
				result.add(configPlugin);
			monitor.worked(1);
		}
		return result;
	}

	private static List<ConfigSource> loadPluginProjects(final IProgressMonitor monitor) {
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		final IProject[] projects = root.getProjects();
		final ArrayList<ConfigSource> result = new ArrayList<ConfigSource>();

		for (final IProject project : projects)
			try {
				if (project.isOpen() && project.hasNature(PluginProjectNature.ID)) {
					monitor.subTask(Messages.getString("ConfigLoaderJob.loadingProject") + project.getName()); //$NON-NLS-1$
					final ConfigSource source = PluginProject.load(project);
					if (source != null)
						result.add(source);
					monitor.worked(1);
				}
			} catch (final CoreException e) {
				final String message = MessageFormat.format(Messages.getString("ConfigLoaderJob.natureError"), //$NON-NLS-1$
						new Object[] { project.getName() });
				VexPlugin.getInstance().log(IStatus.ERROR, message, e);
			}

		return result;
	}

	public synchronized List<ConfigSource> getLoadedConfigSources() {
		return loadedConfigSources;
	}
	
	public boolean isLoading() {
		return getState() == Job.RUNNING;
	}

	public void load(final Runnable whenDone) {
		addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				removeJobChangeListener(this);
				whenDone.run();
			}
		});
		schedule();
	}
}
