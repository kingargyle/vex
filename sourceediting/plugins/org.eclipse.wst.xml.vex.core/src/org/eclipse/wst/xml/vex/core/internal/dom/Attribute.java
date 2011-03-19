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
 * An immutable representation of an attribute within the start tag of an element.
 *  
 * @author Florian Thienel
 */
public class Attribute {
	
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
	 * @return prefix:localName, or localName if prefix is null
	 */
	public String getPrefixedName() {
		return getLocalName();
	}
}
