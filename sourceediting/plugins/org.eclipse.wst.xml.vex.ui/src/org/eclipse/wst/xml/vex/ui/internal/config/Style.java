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

	private static final long serialVersionUID = 1L;
	public static final String EXTENSION_POINT = "org.eclipse.wst.xml.vex.ui.styles"; //$NON-NLS-1$

	public Style(final ConfigSource config) {
		super(config);
	}

	/**
	 * Adds the public ID of a document type to which the style applies.
	 * 
	 * @param publicId
	 *            public ID of the document type
	 */
	public void addDocumentType(final String publicId) {
		publicIds.add(publicId);
	}

	/**
	 * Returns true if this style applies to the documents with the given type.
	 * 
	 * @param publicId
	 *            public ID of the document type being sought
	 */
	public boolean appliesTo(final String publicId) {
		return publicIds.contains(publicId);
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
		return Collections.unmodifiableSet(publicIds);
	}

	/**
	 * Returns an array of all styles applicable to the given public Id.
	 * 
	 * @param publicId
	 *            Public ID for which to find styles.
	 */
	public static Style[] getStylesForDoctype(final String publicId) {
		final List<Style> styles = new ArrayList<Style>();
		for (final ConfigItem configItem : ConfigurationRegistry.INSTANCE.getAllConfigItems(Style.EXTENSION_POINT)) {
			final Style style = (Style) configItem;
			if (style.appliesTo(publicId))
				styles.add(style);
		}
		return styles.toArray(new Style[styles.size()]);
	}

	/**
	 * Returns the style sheet from which element styles are taken.
	 */
	public StyleSheet getStyleSheet() {
		return (StyleSheet) getConfig().getParsedResource(getResourcePath());
	}

	@Override
	public String getExtensionPointId() {
		return EXTENSION_POINT;
	}

	/**
	 * Disassociates this style from all document types.
	 */
	public void removeAllDocumentTypes() {
		publicIds.clear();
	}

	/**
	 * Removes the public ID of a document type to which the style no longer
	 * applies.
	 * 
	 * @param publicId
	 *            public ID of the document type
	 */
	public void removeDocumentType(final String publicId) {
		publicIds.remove(publicId);
	}

	/**
	 * Sets the box factory used to generate boxes for document elements.
	 * 
	 * @param factory
	 *            the new box factory.
	 */
	public void setBoxFactory(final BoxFactory factory) {
		boxFactory = factory;
	}

	// ===================================================== PRIVATE

	private BoxFactory boxFactory;
	private final Set<String> publicIds = new HashSet<String>();

}
