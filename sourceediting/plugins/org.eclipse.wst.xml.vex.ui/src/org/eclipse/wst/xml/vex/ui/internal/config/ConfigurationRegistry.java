/*******************************************************************************
 * Copyright (c) 2010 Florian Thienel and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 		Florian Thienel - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.ui.internal.config;

import java.util.List;

/**
 * @author Florian Thienel
 */
public interface ConfigurationRegistry {

	ConfigurationRegistry INSTANCE = new ConfigurationRegistryImpl();

	void dispose();
	
	void clear();

	boolean isLoaded();
	
	/**
	 * Loads the configurations with a background job. Listeners are notified when all configurations are loaded.
	 */
	void loadConfigurations();

	/**
	 * Returns all ConfigSources that were loaded. 
	 * If the loading was not yet triggered by a call to loadConfigurations, an IllegalStateException is thrown.
	 * If the loading is not yet finished, this method waits until the loading is finished and returns the loaded ConfigSources.
	 * 
	 * @throws IllegalStateException if loadConfigurations was not called yet
	 */
	List<ConfigSource> getAllConfigSources();
	
	IConfigItemFactory getConfigItemFactory(String extensionPointId);

	IConfigItemFactory[] getAllConfigItemFactories();

	ConfigItem getConfigItem(String extensionPoint, String id);
	
	List<ConfigItem> getAllConfigItems(String extensionPointId);

	void addConfigListener(IConfigListener listener);

	void removeConfigListener(IConfigListener listener);
	
	void fireConfigChanged(final ConfigEvent e);
	
	void fireConfigLoaded(final ConfigEvent e);
	
	// new interface
	
	DocumentType getDocumentType(final String publicId);
	
	DocumentType[] getDocumentTypesWithStyles();
	
	Style[] getStyles(final String publicId);
	
	Style getStyle(final String styleId);
	
	Style getStyle(final String publicId, final String preferredStyleId);
}
