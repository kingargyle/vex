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
package org.eclipse.wst.xml.vex.core.internal.dom;

import org.eclipse.core.runtime.QualifiedName;

/**
 * An immutable representation of an attribute within the start tag of an
 * element.
 * The attribute is Comparable by its qualified name.
 * 
 * @author Florian Thienel
 */
public class Attribute implements Comparable<Attribute> {

	private final Element parent;

	private final QualifiedName name;

	private final String value;

	public Attribute(final Element parent, final String localName, final String value) {
		this(parent, new QualifiedName(null, localName), value);
	}

	public Attribute(final Element parent, final QualifiedName name, final String value) {
		this.parent = parent;
		this.name = name;
		this.value = value;
	}

	public Element getParent() {
		return parent;
	}

	public String getLocalName() {
		return name.getLocalName();
	}

	public String getValue() {
		return value;
	}

	/**
	 * @return a pair of the namespace URI and the local name
	 */
	public QualifiedName getQualifiedName() {
		return name;
	}

	/**
	 * @return prefix:localName, or localName if prefix is null or this
	 *         attribute is in the same namespace as the parent element.
	 */
	public String getPrefixedName() {
		final String attributeQualifier = name.getQualifier();
		if (parent == null || attributeQualifier == null)
			return getLocalName();
		final String elementQualifier = parent.getQualifiedName().getQualifier();
		if (elementQualifier == null || attributeQualifier.equals(elementQualifier))
			return getLocalName();
		final String prefix = parent.getNamespacePrefix(attributeQualifier);
		return (prefix == null ? "" : prefix + ":") + getLocalName();
	}

	/**
	 * Compares two attributes by their name.
	 * 
	 * @param otherAttribute
	 *            the other attribute
	 * @see Comparable
	 */
	public int compareTo(final Attribute otherAttribute) {
		return name.toString().compareTo(otherAttribute.name.toString());
	}
}
