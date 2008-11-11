/*******************************************************************************
 *Copyright (c) 2008 Standards for Technology in Automotive Retail and others.
 *All rights reserved. This program and the accompanying materials
 *are made available under the terms of the Eclipse Public License v1.0
 *which accompanies this distribution, and is available at
 *http://www.eclipse.org/legal/epl-v10.html
 *
 *Contributors:
 *    David Carver (STAR)  - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.provisional.dom.I;

import org.w3c.dom.Comment;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>VEX Comment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXComment#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXComment#getComment <em>Comment</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage#getVEXComment()
 * @model
 * @generated
 */
public interface VEXComment extends VEXNode {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage#getVEXComment_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXComment#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comment</em>' attribute.
	 * @see #setComment(Comment)
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage#getVEXComment_Comment()
	 * @model dataType="org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.DOMComment"
	 * @generated
	 */
	Comment getComment();

	/**
	 * Sets the value of the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXComment#getComment <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comment</em>' attribute.
	 * @see #getComment()
	 * @generated
	 */
	void setComment(Comment value);

} // VEXComment
