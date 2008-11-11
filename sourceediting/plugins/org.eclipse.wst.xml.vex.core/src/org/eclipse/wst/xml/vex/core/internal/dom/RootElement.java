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

import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement;

/**
 * The root element of a document. Keeps track of the document to which it is
 * associated. Any element can find the document to which it is associated by
 * following its parents to this root. This would be done, for example, to
 * notify document listeners that the document has changed when the element
 * changes.
 */
public class RootElement extends Element implements VEXElement {

	//private VEXDocument document;

	/**
	 * Class constructor
	 * 
	 * @param name
	 *            Name of the element.
	 */
	public RootElement(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXRootElement#getDocument()
	 */
	public VEXDocument getDocument() {
		return document;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXRootElement#setDocument(org.eclipse.wst.xml.vex.core.internal.dom.Document)
	 */
	public void setDocument(VEXDocument document) {
		this.document = document;
	}


}
