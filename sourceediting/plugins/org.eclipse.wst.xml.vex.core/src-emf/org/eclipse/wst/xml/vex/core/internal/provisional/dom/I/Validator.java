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

import java.io.Serializable;

import java.util.Set;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.wst.xml.vex.core.internal.validator.AttributeDefinition;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Validator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Validator#getValidRootElements <em>Valid Root Elements</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage#getValidator()
 * @model superTypes="org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Serializable"
 * @generated
 */
public interface Validator extends EObject, Serializable {
	public static String PCDATA = "#PCDATA";
	
	/**
	 * Returns the value of the '<em><b>Valid Root Elements</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Valid Root Elements</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Valid Root Elements</em>' attribute.
	 * @see #setValidRootElements(Set)
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage#getValidator_ValidRootElements()
	 * @model dataType="org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Set<org.eclipse.emf.ecore.EString>"
	 * @generated
	 */
	Set<String> getValidRootElements();

	/**
	 * Sets the value of the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Validator#getValidRootElements <em>Valid Root Elements</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Valid Root Elements</em>' attribute.
	 * @see #getValidRootElements()
	 * @generated
	 */
	void setValidRootElements(Set<String> value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.AttributeDefinition"
	 * @generated
	 */
	AttributeDefinition getAttributeDefinition(String element, String attribute);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.AttributeDefinition"
	 * @generated
	 */
	EList<AttributeDefinition> getAttributeDefinitions(String element);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Set<org.eclipse.emf.ecore.EString>"
	 * @generated
	 */
	Set<String> getValidItems(String element);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model nodesMany="true"
	 * @generated
	 */
	boolean isValidSequence(String element, EList<String> nodes, boolean partial);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model seq1Many="true" seq2Many="true" seq3Many="true"
	 * @generated
	 */
	boolean isValidSequence(String element, EList<String> seq1, EList<String> seq2, EList<String> seq3, boolean partial);

} // Validator
