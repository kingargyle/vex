/*******************************************************************************
 *Copyright (c) 2008 Standards for Technology in Automotive Retail and others.
 *All rights reserved. This program and the accompanying materials
 *are made available under the terms of the Eclipse Public License v1.0
 *which accompanies this distribution, and is available at
 *http://www.eclipse.org/legal/epl-v10.html
 *
 *Contributors:
 *    David Carver (STAR) - initial API and implementation
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.wst.xml.vex.core.internal.provisional.dom;

/**
 * Based losely on the JDOM API but implemented and enhanced for VEX
 * @author dcarver
 * @model
 */
public interface VEXAttribute extends VEXNode {
	
	/**
	 * The VEXDocument for this attribute.
	 * @return
	 * @model
	 */
	public VEXDocument getDocument();
	
	/**
	 * Sets the document for this attribute.
	 */
	public void setDocument(VEXDocument document);
	
	/**
	 * The Value for the attribute.
	 * @return
	 * @model
	 */
	public String getValue();
	
	public void setValue(String value);
	
	/**
	 * The parent element for this attribute.
	 * @model
	 */
	public VEXElement getParent();
	
	public void setParent(VEXElement element);
	
	/**
	 * Returns the local attribute name.
	 * 
	 * @return Attribute Name
	 * @model
	 */
	public String getLocalName();
	
	/**
	 * This will return the fully qualified name 
	 * @return
	 */
	public String getQualifiedName();
	
	/**
	 * This returns the namespace prefix for the attribute or null if none is defined.
	 * @return
	 * @model
	 */
	public String getNamespacePrefix();
	
	public void setNamespacePrefix(String namespacePrefix);
	
	
	/**
	 * This returns the namespace URI
	 * @return 
	 * @model 
	 */
	public String getNamespaceURI();
	
	public void setNamespaceURI(String namespaceURI);
	
	/**
	 * @model
	 */
	public void setNamespace(String prefix, String namespaceURI);
	
}
