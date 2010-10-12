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

import org.eclipse.core.resources.IProject;

/**
 * @author Florian Thienel
 */
public interface ConfigurationRegistry {

	ConfigurationRegistry INSTANCE = new ConfigurationRegistryImpl(new ConfigLoaderJob());
	
	void fireConfigChanged(final ConfigEvent e);
	
	void fireConfigLoaded(final ConfigEvent e);
	
	// new interface
	
	void dispose();
	
	/**
	 * Loads the configurations with a background job. Listeners are notified when all configurations are loaded.
	 */
	void loadConfigurations();
	
	boolean isLoaded();
	
	DocumentType getDocumentType(final String publicId);
	
	DocumentType[] getDocumentTypes();
	
	DocumentType[] getDocumentTypesWithStyles();
	
	Style[] getStyles(final String publicId);
	
	Style getStyle(final String styleId);
	
	Style getStyle(final String publicId, final String preferredStyleId);
	
	PluginProject getPluginProject(final IProject project);

	void addConfigListener(IConfigListener listener);

	void removeConfigListener(IConfigListener listener);
	
}
