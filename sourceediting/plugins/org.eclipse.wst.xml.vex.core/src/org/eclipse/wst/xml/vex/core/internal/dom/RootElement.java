/*******************************************************************************
 * Copyright (c) 2004, 2008 John Krasnay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     John Krasnay - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.dom;

import org.eclipse.core.runtime.QualifiedName;

/**
 * The root element of a document. Keeps track of the document to which it is
 * associated. Any element can find the document to which it is associated by
 * following its parents to this root. This would be done, for example, to
 * notify document listeners that the document has changed when the element
 * changes.
 */
public class RootElement extends Element {

	private Document document;

	public RootElement(final String localName) {
		super(localName);
	}
	
	public RootElement(final QualifiedName qualifiedName) {
		super(qualifiedName);
	}
	
	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

}
