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

/**
 * Represents a Comment in an XML model.
 * @author dcarver
 * @model
 */
public interface VEXComment extends VEXNode {

	/**
	 * Returns the string value of this comment
	 * @return
	 * @model
	 */
	public String getValue();
}
