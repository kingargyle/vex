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
 * Registry of configuration sources and listeners.
 * 
 * @author Florian Thienel
 */
public interface ConfigurationRegistry {

	/**
	 * Dispose the registry and clean-up free acquired resources.
	 */
	void dispose();

	/**
	 * Load all configurations from installed bundles and plug-in projects in
	 * the workspace.
	 */
	void loadConfigurations();

	/**
	 * @return true if the configurations have been loaded.
	 */
	boolean isLoaded();

	/**
	 * The document type configuration for the given public identifier, or null
	 * if there is no configuration for the given public identifier.
	 * 
	 * @param publicId
	 *            the public identifier
	 * @return the document type configuration for the given public identifier,
	 *         or null if there is no configuration for the given public
	 *         identifier.
	 */
	DocumentType getDocumentType(final String publicId);

	/**
	 * @return all document type configurations
	 */
	DocumentType[] getDocumentTypes();

	/**
	 * @return all document type configurations for which there is at least one
	 *         registered style.
	 */
	DocumentType[] getDocumentTypesWithStyles();

	/**
	 * All styles for the document type with the given public identifier.
	 * 
	 * @param publicId
	 *            the document type's public identifier
	 * @return all styles for the document type with the given public identifier
	 */
	Style[] getStyles(final String publicId);

	/**
	 * The style with the given id, or null if there is no style with this id.
	 * 
	 * @param styleId
	 *            the style's id
	 * @return the style with the given id, or null if there is no style with
	 *         this id.
	 */
	Style getStyle(final String styleId);

	/**
	 * An arbitrary style for the document type with the given public
	 * identifier. If available,
	 * the style with the given style id is preferred.
	 * 
	 * @param publicId
	 *            the document type's public identifier
	 * @param preferredStyleId
	 *            the preferred style's id
	 * @return a style for the given document type, or null if no style is
	 *         configured for the given document type
	 */
	Style getStyle(final String publicId, final String preferredStyleId);

	/**
	 * The representation of the given plug-in project in the workspace.
	 * 
	 * @param project
	 *            the project
	 * @return the representation of the given plug-in project in the workspace.
	 */
	PluginProject getPluginProject(final IProject project);

	/**
	 * Adds a ConfigChangeListener to the notification list.
	 * 
	 * @param listener
	 *            Listener to be added.
	 */
	void addConfigListener(IConfigListener listener);

	/**
	 * Removes a ConfigChangeListener from the notification list.
	 * 
	 * @param listener
	 *            Listener to be removed.
	 */
	void removeConfigListener(IConfigListener listener);
}
