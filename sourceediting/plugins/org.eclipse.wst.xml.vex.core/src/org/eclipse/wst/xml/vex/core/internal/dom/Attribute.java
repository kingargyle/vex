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

/**
 * An immutable representation of an attribute within the start tag of an element.
 *  
 * @author Florian Thienel
 */
public class Attribute {
	
	private final Element parent;
	
	private final String localName;
	
	private final String value;
	
	public Attribute(final Element parent, final String localName, final String value) {
		this.parent = parent;
		this.localName = localName;
		this.value = value;
	}

	public Element getParent() {
		return parent;
	}
	
	public String getLocalName() {
		return localName;
	}
	
	public String getValue() {
		return value;
	}
	
	/**
	 * @return namespaceName:localName
	 */
	public String getExpandedName() {
		return localName;
	}
	
	/**
	 * @return prefix:localName
	 */
	public String getQualifiedName() {
		return localName;
	}
}
