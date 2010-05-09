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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.wst.xml.vex.core.internal.css.StyleSheet;
import org.eclipse.wst.xml.vex.core.internal.layout.BoxFactory;

/**
 * Represents the combination of a style sheet and a box factory that defines
 * the styling of an XML document during editing.
 */
public class Style extends ConfigItem {

	public static final String EXTENSION_POINT = "org.eclipse.wst.xml.vex.ui.styles"; //$NON-NLS-1$

	public Style(ConfigSource config) {
		super(config);
	}

	/**
	 * Adds the public ID of a document type to which the style applies.
	 * 
	 * @param publicId
	 *            public ID of the document type
	 */
	public void addDocumentType(String publicId) {
		this.publicIds.add(publicId);
	}

	/**
	 * Returns true if this style applies to the documents with the given type.
	 * 
	 * @param publicId
	 *            public ID of the document type being sought
	 */
	public boolean appliesTo(String publicId) {
		return this.publicIds.contains(publicId);
	}

	/**
	 * Returns the box factory used to generate boxes for document elements.
	 */
	public BoxFactory getBoxFactory() {
		return boxFactory;
	}

	/**
	 * Returns a set of public IDs of all document types supported by this
	 * style.
	 */
	public Set<String> getDocumentTypes() {
		return Collections.unmodifiableSet(this.publicIds);
	}

	/**
	 * Returns an array of all styles applicable to the given public Id.
	 * 
	 * @param publicId
	 *            Public ID for which to find styles.
	 */
	public static Style[] getStylesForDoctype(String publicId) {
		ConfigRegistry registry = ConfigRegistry.getInstance();
		List<Style> styles = new ArrayList<Style>();
		for (ConfigItem configItem : registry.getAllConfigItems(Style.EXTENSION_POINT)) {
			Style style = (Style) configItem;
			if (style.appliesTo(publicId)) {
				styles.add(style);
			}	
		}
		return styles.toArray(new Style[styles.size()]);
	}

	/**
	 * Returns the style sheet from which element styles are taken.
	 */
	public StyleSheet getStyleSheet() {
		return (StyleSheet) this.getConfig().getParsedResource(
				this.getResourcePath());
	}

	public String getExtensionPointId() {
		return EXTENSION_POINT;
	}

	/**
	 * Disassociates this style from all document types.
	 */
	public void removeAllDocumentTypes() {
		this.publicIds.clear();
	}

	/**
	 * Removes the public ID of a document type to which the style no longer
	 * applies.
	 * 
	 * @param publicId
	 *            public ID of the document type
	 */
	public void removeDocumentType(String publicId) {
		this.publicIds.remove(publicId);
	}

	/**
	 * Sets the box factory used to generate boxes for document elements.
	 * 
	 * @param factory
	 *            the new box factory.
	 */
	public void setBoxFactory(BoxFactory factory) {
		boxFactory = factory;
	}

	// ===================================================== PRIVATE

	private BoxFactory boxFactory;
	private Set<String> publicIds = new HashSet<String>();

}
