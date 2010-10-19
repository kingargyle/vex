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

import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.wst.xml.vex.ui.internal.VexPlugin;

/**
 * A plugin or plugin project that contributes ConfigItems. This class and all
 * configuration items added to it must be serializable, since it is persisted
 * across Vex invocations due to the expense of reparsing the configuration
 * items.
 */
public abstract class ConfigSource {

	private static final IConfigItemFactory[] CONFIG_ITEM_FACTORIES = new IConfigItemFactory[] { new DoctypeFactory(), new StyleFactory() };

	// Globally-unique identifier of this configuration
	// == the plugin id.
	private final String id;

	// all config items in this configuration
	private final List<ConfigItem> items = new ArrayList<ConfigItem>();

	// map String URI => parsed resource
	private final Map<String, Object> parsedResources = new HashMap<String, Object>();

	protected static IConfigItemFactory getConfigItemFactory(final String extensionPointId) {
		for (final IConfigItemFactory factory : CONFIG_ITEM_FACTORIES)
			if (factory.getExtensionPointId().equals(extensionPointId))
				return factory;
		return null;
	}

	public ConfigSource(final String id) {
		this.id = id;
	}

	/**
	 * Adds the given item to the configuration.
	 * 
	 * @param item
	 *            ConfigItem to be added.
	 */
	public void addItem(final ConfigItem item) {
		items.add(item);
	}

	/**
	 * Creates a configuration item and adds it to this configuration. If the
	 * given extension point does not have a factory registered in VexPlugin, no
	 * action is taken and null is returned.
	 * 
	 * @param extensionPoint
	 *            Extension point of the item to be added.
	 * @param simpleIdentifier
	 *            Simple (i.e. no dots) identifier of the item.
	 * @param name
	 *            Name of the item.
	 * @param configElements
	 *            Array of IConfigElement objects representing the item's
	 *            settings.
	 * @return The newly created ConfigItem, or null if extensionPoint is not
	 *         recognized.
	 * @throws IOException
	 */
	protected ConfigItem addItem(final String extensionPoint, final String simpleIdentifier, final String name, final IConfigElement[] configElements)
			throws IOException {
		final IConfigItemFactory factory = getConfigItemFactory(extensionPoint);
		if (factory == null)
			return null;
		final ConfigItem item = factory.createItem(this, configElements);
		item.setSimpleId(simpleIdentifier);
		item.setName(name);
		this.addItem(item);
		return item;

	}

	/**
	 * Removes the given item from the configuration.
	 * 
	 * @param item
	 *            ConfigItem to be removed.
	 */
	public void remove(final ConfigItem item) {
		items.remove(item);
	}

	/**
	 * Remove all items from this configuration.
	 */
	public void removeAllItems() {
		items.clear();
	}

	/**
	 * Remove all parsed resources from this configuration.
	 */
	public void removeAllResources() {
		parsedResources.clear();
	}

	/**
	 * Remove the resource associated with the given URI from the resource
	 * cache. The factory must handle any of the following scenarios.
	 * 
	 * <ul>
	 * <li>The URI represents the primary resource associated with a
	 * configuration item.</li>
	 * <li>The URI is a secondary resource associated with a primary resource.
	 * In this case the primary resource is removed.</li>
	 * <li>The URI has nothing to do with a configuration item, in which case no
	 * action is taken.</li>
	 * </ul>
	 * 
	 * To fully implement this method, the factory must interact with the parser
	 * and track which secondary resources are associated with which primaries.
	 * 
	 * @param uri
	 *            Relative URI of the resource to remove.
	 */
	public void removeResource(final String uri) {
		parsedResources.remove(uri); // TODO Respect secondary resources
	}

	/**
	 * Returns a list of all items in this configuration.
	 */
	public List<ConfigItem> getAllItems() {
		return items;
	}

	/**
	 * Returns the base URL of this factory. This is used to resolve relative
	 * URLs in config items
	 */
	public abstract URL getBaseUrl();

	/**
	 * Returns a particular item from the configuration. Returns null if no
	 * matching item could be found.
	 * 
	 * @param simpleId
	 *            Simple ID of the item to return.
	 */
	public ConfigItem getItem(final String simpleId) {
		for (final ConfigItem item : items)
			if (item.getSimpleId() != null && item.getSimpleId().equals(simpleId))
				return item;
		return null;
	}

	/**
	 * Returns the item for the resource with the given path relative to the
	 * plugin or project. May return null if no such item exists.
	 * 
	 * @param resourcePath
	 *            Path of the resource.
	 */
	public ConfigItem getItemForResource(final String resourcePath) {
		for (final ConfigItem item : items)
			if (item.getResourcePath().equals(resourcePath))
				return item;
		return null;
	}

	/**
	 * Returns the parsed resource object for the given URI, or null of none
	 * exists.
	 * 
	 * @param uri
	 *            URI of the resource, relative to the base URL of this
	 *            configuration.
	 */
	public Object getParsedResource(final String uri) {
		return parsedResources.get(uri);
	}

	/**
	 * Returns the unique identifier of this configuration. This is the same as
	 * the ID of the plugin that defines the configuration.
	 */
	public String getUniqueIdentifer() {
		return id;
	}

	/**
	 * Returns all ConfigItems of the given type for which isValid returns true.
	 * 
	 * @param extensionPointId
	 *            The type of ConfigItem to return.
	 */
	public Collection<ConfigItem> getValidItems(final String extensionPointId) {
		final List<ConfigItem> result = new ArrayList<ConfigItem>();
		for (final ConfigItem item : items)
			if (item.getExtensionPointId().equals(extensionPointId) && item.isValid())
				result.add(item);
		return result;
	}

	/**
	 * Returns true if there are no items in this configuration.
	 */
	public boolean isEmpty() {
		return items.isEmpty();
	}

	/**
	 * Parses all resources required by the registered items.
	 * 
	 * @param problemHandler
	 *            Handler for build problems. May be null.
	 */
	public void parseResources(final IBuildProblemHandler problemHandler) {
		parsedResources.clear();
		for (final ConfigItem item : items) {
			final String uri = item.getResourcePath();
			if (!parsedResources.containsKey(uri)) {
				final IConfigItemFactory factory = getConfigItemFactory(item.getExtensionPointId());
				try {
					final Object parsedResource = factory.parseResource(getBaseUrl(), uri, problemHandler);
					if (parsedResource != null)
						parsedResources.put(uri, parsedResource);
				} catch (final IOException ex) {
					final String message = MessageFormat.format(Messages.getString("ConfigSource.errorParsingUri"), new Object[] { uri });
					VexPlugin.getInstance().log(IStatus.ERROR, message, ex);
				}
			}
		}
	}

}