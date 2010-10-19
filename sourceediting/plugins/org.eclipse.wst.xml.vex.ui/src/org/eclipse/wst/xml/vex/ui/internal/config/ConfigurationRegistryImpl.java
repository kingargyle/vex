/*******************************************************************************
 * Copyright (c) 2004, 2008 John Krasnay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     John Krasnay - initial API and implementation
 *     Igor Jacy Lino Campista - Java 5 warnings fixed (bug 311325)
 *******************************************************************************/
package org.eclipse.wst.xml.vex.ui.internal.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.wst.xml.vex.core.internal.core.ListenerList;
import org.eclipse.wst.xml.vex.ui.internal.VexPlugin;

public class ConfigurationRegistryImpl implements ConfigurationRegistry {

	private final ConfigurationLoader loader;
	private volatile boolean loaded = false;

	private final ILock lock = Job.getJobManager().newLock();
	private Map<String, ConfigSource> configurationSources = new HashMap<String, ConfigSource>();
	private final ListenerList<IConfigListener, ConfigEvent> configListeners = new ListenerList<IConfigListener, ConfigEvent>(IConfigListener.class);

	private final IResourceChangeListener resourceChangeListener = new IResourceChangeListener() {
		public void resourceChanged(final IResourceChangeEvent event) {
			if (event.getType() == IResourceChangeEvent.PRE_CLOSE || event.getType() == IResourceChangeEvent.PRE_DELETE) {
				final PluginProject pluginProject = getPluginProject((IProject) event.getResource());
				if (pluginProject != null) {
					// this project is about to be closed or deleted
					removeConfigSource(pluginProject);
					fireConfigChanged(new ConfigEvent(this));
				}
			} else if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
				final IResourceDelta[] resources = event.getDelta().getAffectedChildren();
				for (final IResourceDelta delta : resources)
					if (delta.getResource() instanceof IProject) {
						final IProject project = (IProject) delta.getResource();
						final PluginProject pluginProject = getPluginProject(project);
						if (!project.isOpen() && pluginProject != null) {
							// we know this project and it has been closed
							removeConfigSource(pluginProject);
							fireConfigChanged(new ConfigEvent(this));
						} else if (PluginProject.isOpenPluginProject(project))
							if (pluginProject == null)
								reloadPluginProject(addNewPluginProject(project));
							else
								reloadPluginProject(pluginProject);
					}
			}
		}

	};

	public ConfigurationRegistryImpl(final ConfigurationLoader loader) {
		this.loader = loader;
		ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener);
	}

	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);
	}

	public void loadConfigurations() {
		lock.acquire();
		try {
			loader.load(new Runnable() {
				public void run() {
					lock.acquire();
					try {
						configurationSources = new HashMap<String, ConfigSource>();
						for (final ConfigSource configSource : loader.getLoadedConfigSources())
							configurationSources.put(configSource.getUniqueIdentifer(), configSource);
						loaded = true;
					} finally {
						lock.release();
					}
					fireConfigLoaded(new ConfigEvent(ConfigurationRegistryImpl.this));
				}
			});
		} finally {
			lock.release();
		}
	}

	private List<ConfigItem> getAllConfigItems(final String extensionPointId) {
		waitUntilLoaded();
		lock.acquire();
		try {
			final List<ConfigItem> result = new ArrayList<ConfigItem>();
			for (final ConfigSource configurationSource : configurationSources.values())
				result.addAll(configurationSource.getValidItems(extensionPointId));
			return result;
		} finally {
			lock.release();
		}
	}

	private List<ConfigSource> getAllConfigSources() {
		waitUntilLoaded();
		lock.acquire();
		try {
			final List<ConfigSource> result = new ArrayList<ConfigSource>();
			result.addAll(configurationSources.values());
			return result;
		} finally {
			lock.release();
		}
	}

	private PluginProject addNewPluginProject(final IProject project) {
		final PluginProject result = new PluginProject(project);
		addConfigSource(result);
		return result;
	}

	private void addConfigSource(final ConfigSource configSource) {
		waitUntilLoaded();
		lock.acquire();
		try {
			configurationSources.put(configSource.getUniqueIdentifer(), configSource);
		} finally {
			lock.release();
		}
	}

	private void removeConfigSource(final ConfigSource configSource) {
		waitUntilLoaded();
		lock.acquire();
		try {
			configurationSources.remove(configSource.getUniqueIdentifer());
		} finally {
			lock.release();
		}
	}

	private void waitUntilLoaded() {
		if (loaded)
			return;
		if (!loader.isLoading())
			throw new IllegalStateException("The configurations are not loaded yet. Call 'loadConfigurations' first.");
		try {
			loader.join();
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void addConfigListener(final IConfigListener listener) {
		configListeners.add(listener);
	}

	public void removeConfigListener(final IConfigListener listener) {
		configListeners.remove(listener);
	}

	private void fireConfigChanged(final ConfigEvent e) {
		configListeners.fireEvent("configChanged", e); //$NON-NLS-1$
	}

	private void fireConfigLoaded(final ConfigEvent e) {
		configListeners.fireEvent("configLoaded", e); //$NON-NLS-1$
	}

	public DocumentType getDocumentType(final String publicId) {
		final List<ConfigItem> configItems = getAllConfigItems(DocumentType.EXTENSION_POINT);
		for (final ConfigItem configItem : configItems) {
			final DocumentType doctype = (DocumentType) configItem;
			if (doctype.getPublicId().equals(publicId))
				return doctype;
		}
		return null;
	}

	public DocumentType[] getDocumentTypes() {
		final List<DocumentType> result = new ArrayList<DocumentType>();
		for (final ConfigItem configItem : getAllConfigItems(DocumentType.EXTENSION_POINT))
			result.add((DocumentType) configItem);
		return result.toArray(new DocumentType[result.size()]);
	}

	public DocumentType[] getDocumentTypesWithStyles() {
		final List<DocumentType> result = new ArrayList<DocumentType>();
		for (final ConfigItem configItem : getAllConfigItems(DocumentType.EXTENSION_POINT)) {
			final DocumentType doctype = (DocumentType) configItem;
			if (getStyles(doctype.getPublicId()).length > 0)
				result.add(doctype);
		}
		return result.toArray(new DocumentType[result.size()]);
	}

	public Style[] getStyles(final String publicId) {
		final ArrayList<Style> result = new ArrayList<Style>();
		for (final ConfigItem configItem : getAllConfigItems(Style.EXTENSION_POINT)) {
			final Style style = (Style) configItem;
			if (style.appliesTo(publicId))
				result.add(style);
		}
		return result.toArray(new Style[result.size()]);
	}

	public Style getStyle(final String styleId) {
		for (final ConfigItem configItem : getAllConfigItems(Style.EXTENSION_POINT)) {
			final Style style = (Style) configItem;
			if (style.getUniqueId().equals(styleId))
				return style;
		}
		return null;
	}

	public Style getStyle(final String publicId, final String preferredStyleId) {
		final Style[] styles = getStyles(publicId);
		if (styles.length == 0)
			return null;
		if (preferredStyleId != null)
			for (final Style style : styles)
				if (style.getUniqueId().equals(preferredStyleId))
					return style;
		return styles[0];
	}

	public PluginProject getPluginProject(final IProject project) {
		for (final ConfigSource source : getAllConfigSources())
			if (source instanceof PluginProject) {
				final PluginProject pluginProject = (PluginProject) source;
				if (project.equals(pluginProject.getProject()))
					return pluginProject;
			}
		return null;
	}

	private void reloadPluginProject(final PluginProject pluginProject) {
		try {
			pluginProject.load();
		} catch (final CoreException e) {
			VexPlugin.getInstance().getLog().log(e.getStatus());
		}
		fireConfigChanged(new ConfigEvent(this));
	}
}
