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
package org.eclipse.wst.xml.vex.core.internal.provisional.dom;

import org.eclipse.emf.ecore.EFactory;

import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Content;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Position;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXAttribute;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXComment;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocumentFragment;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXProcessingInstruction;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Validator;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage
 * @generated
 */
public interface DomEMFFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DomEMFFactory eINSTANCE = org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Content</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Content</em>'.
	 * @generated
	 */
	Content createContent();

	/**
	 * Returns a new object of class '<em>Position</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Position</em>'.
	 * @generated
	 */
	Position createPosition();

	/**
	 * Returns a new object of class '<em>VEX Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>VEX Attribute</em>'.
	 * @generated
	 */
	VEXAttribute createVEXAttribute();

	/**
	 * Returns a new object of class '<em>VEX Comment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>VEX Comment</em>'.
	 * @generated
	 */
	VEXComment createVEXComment();

	/**
	 * Returns a new object of class '<em>VEX Document</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>VEX Document</em>'.
	 * @generated
	 */
	VEXDocument createVEXDocument();

	/**
	 * Returns a new object of class '<em>VEX Document Fragment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>VEX Document Fragment</em>'.
	 * @generated
	 */
	VEXDocumentFragment createVEXDocumentFragment();

	/**
	 * Returns a new object of class '<em>VEX Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>VEX Element</em>'.
	 * @generated
	 */
	VEXElement createVEXElement();

	/**
	 * Returns a new object of class '<em>VEX Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>VEX Node</em>'.
	 * @generated
	 */
	VEXNode createVEXNode();

	/**
	 * Returns a new object of class '<em>VEX Processing Instruction</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>VEX Processing Instruction</em>'.
	 * @generated
	 */
	VEXProcessingInstruction createVEXProcessingInstruction();

	/**
	 * Returns a new object of class '<em>Validator</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Validator</em>'.
	 * @generated
	 */
	Validator createValidator();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DomEMFPackage getDomEMFPackage();

} //DomEMFFactory
