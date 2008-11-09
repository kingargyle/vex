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
package org.eclipse.wst.xml.vex.core.internal.provisional.dom;

import java.util.List;

/**
 * @model
 */
public interface VEXProcessingInstruction extends VEXNode {

	/**
	 * @model
	 */
	public List<VEXAttribute> getAttributes();
	
	/**
	 * 
	 * @param attributes
	 * 
	 */
	public void setAttributes(List<VEXAttribute> attributes);
	
	/**
	 * Removes the attribute with the specified name
	 * @param attributeName
	 * @model
	 */
	public boolean removeAttribute(String attributeName);
	
	/**
	 * Sets the attribute with the name to the given value.
	 * @param attributeName
	 * @param value
	 * @model
	 */
	public void setAttribute(String attributeName, String value);
	
}
