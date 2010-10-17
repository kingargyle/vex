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

/**
 * Singleton registry of configuration sources and listeners.
 * 
 * The configuration sources may be accessed by multiple threads, and are
 * protected by a lock. All methods that modify or iterate over config sources
 * do so after acquiring the lock. Callers that wish to perform multiple
 * operations as an atomic transaction must lock and unlock the registry as
 * follows.
 * 
 * <pre>
 * ConfigRegistry reg = ConfigRegistry.getInstance();
 * try {
 * 	reg.lock();
 * 	// make modifications
 * } finally {
 * 	reg.unlock();
 * }
 * </pre>
 * 
 * <p>
 * This class also maintains a list of ConfigListeners. The addConfigListener
 * and removeConfigListener methods must be called from the main UI thread. The
 * fireConfigXXX methods may be called from other threads; this class will
 * ensure the listeners are called on the UI thread.
 */
public class ConfigurationRegistryImpl implements ConfigurationRegistry {

	private final ConfigurationLoader loader;
	private volatile boolean loaded = false;

	public ConfigurationRegistryImpl(final ConfigurationLoader loader) {
		this.loader = loader;
		ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener);
	}

	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);
	}

	public void loadConfigurations() {
		lock();
		try {
			loader.load(new Runnable() {
				public void run() {
					lock();
					try {
						configurationSources = new HashMap<String, ConfigSource>();
						for (final ConfigSource configSource : loader.getLoadedConfigSources())
							configurationSources.put(configSource.getUniqueIdentifer(), configSource);
						loaded = true;
					} finally {
						unlock();
					}
					fireConfigLoaded(new ConfigEvent(ConfigurationRegistryImpl.this));
				}
			});
		} finally {
			unlock();
		}
	}

	private List<ConfigItem> getAllConfigItems(final String extensionPointId) {
		waitUntilLoaded();
		lock();
		try {
			final List<ConfigItem> result = new ArrayList<ConfigItem>();
			for (final ConfigSource configurationSource : configurationSources.values())
				result.addAll(configurationSource.getValidItems(extensionPointId));
			return result;
		} finally {
			unlock();
		}
	}

	private List<ConfigSource> getAllConfigSources() {
		waitUntilLoaded();
		lock();
		try {
			final List<ConfigSource> result = new ArrayList<ConfigSource>();
			result.addAll(configurationSources.values());
			return result;
		} finally {
			unlock();
		}
	}

	private PluginProject addNewPluginProject(final IProject project) {
		final PluginProject result = new PluginProject(project);
		addConfigSource(result);
		return result;
	}

	private void addConfigSource(final ConfigSource configSource) {
		waitUntilLoaded();
		lock();
		try {
			configurationSources.put(configSource.getUniqueIdentifer(), configSource);
		} finally {
			unlock();
		}
	}

	private void removeConfigSource(final ConfigSource configSource) {
		waitUntilLoaded();
		lock();
		try {
			configurationSources.remove(configSource.getUniqueIdentifer());
		} finally {
			unlock();
		}
	}

	/**
	 * Locks the registry for modification or iteration over its config sources.
	 */
	public void lock() {
		lock.acquire();
	}

	/**
	 * Unlocks the registry.
	 */
	public void unlock() {
		lock.release();
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

	/**
	 * Returns true if the Vex configuration has been loaded.
	 * 
	 * @see org.eclipse.wst.xml.vex.ui.internal.config.ConfigLoaderJob
	 */
	public boolean isLoaded() {
		return loaded;
	}

	/**
	 * Adds a ConfigChangeListener to the notification list.
	 * 
	 * @param listener
	 *            Listener to be added.
	 */
	public void addConfigListener(final IConfigListener listener) {
		configListeners.add(listener);
	}

	/**
	 * Removes a ConfigChangeListener from the notification list.
	 * 
	 * @param listener
	 *            Listener to be removed.
	 */
	public void removeConfigListener(final IConfigListener listener) {
		configListeners.remove(listener);
	}

	private void fireConfigChanged(final ConfigEvent e) {
		configListeners.fireEvent("configChanged", e); //$NON-NLS-1$
	}

	private void fireConfigLoaded(final ConfigEvent e) {
		configListeners.fireEvent("configLoaded", e); //$NON-NLS-1$
	}

	/**
	 * The document type configuration for the given public identifier, of null
	 * if there is no configuration for the given public identifier.
	 * 
	 * @param publicId
	 *            the public identifier
	 * @return the document type configuration for the given public identifier,
	 *         of null if there is no configuration for the given public
	 *         identifier.
	 */
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

	/**
	 * Return a list of document types for which there is at least one
	 * registered style.
	 * 
	 * @return a list of document types for which there is at least one
	 *         registered style.
	 */
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

	/**
	 * Factory method that returns the plugin project for the given IProject. If
	 * the given project does not have the Vex plugin project nature, null is
	 * returned. PluginProject instances are cached so they can be efficiently
	 * returned.
	 * 
	 * @param project
	 *            IProject for which to return the PluginProject.
	 * @return the corresponding PluginProject
	 */
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

	// ======================================================== PRIVATE

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
							if (pluginProject == null) {
								reloadPluginProject(addNewPluginProject(project));
							} else {
								reloadPluginProject(pluginProject);
							}
					}
			}
		}

	};

}
