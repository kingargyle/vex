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

import org.eclipse.emf.common.util.EList;
import org.w3c.dom.ProcessingInstruction;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>VEX Processing Instruction</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXProcessingInstruction#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXProcessingInstruction#getProcessingInstruction <em>Processing Instruction</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage#getVEXProcessingInstruction()
 * @model
 * @generated
 */
public interface VEXProcessingInstruction extends VEXNode {
	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' reference list.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage#getVEXProcessingInstruction_Attributes()
	 * @model
	 * @generated
	 */
	EList<VEXAttribute> getAttributes();

	/**
	 * Returns the value of the '<em><b>Processing Instruction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Processing Instruction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Processing Instruction</em>' attribute.
	 * @see #setProcessingInstruction(ProcessingInstruction)
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage#getVEXProcessingInstruction_ProcessingInstruction()
	 * @model dataType="org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.DOMProcessingInstruction"
	 * @generated
	 */
	ProcessingInstruction getProcessingInstruction();

	/**
	 * Sets the value of the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXProcessingInstruction#getProcessingInstruction <em>Processing Instruction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Processing Instruction</em>' attribute.
	 * @see #getProcessingInstruction()
	 * @generated
	 */
	void setProcessingInstruction(ProcessingInstruction value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean removeAttribute(String attributeName);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void setAttribute(String attributeName, String value);

} // VEXProcessingInstruction
