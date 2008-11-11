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
package org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage;

import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXAttribute;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXProcessingInstruction;
import org.w3c.dom.ProcessingInstruction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>VEX Processing Instruction</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXProcessingInstructionImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXProcessingInstructionImpl#getProcessingInstruction <em>Processing Instruction</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VEXProcessingInstructionImpl extends VEXNodeImpl implements VEXProcessingInstruction {
	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<VEXAttribute> attributes;

	/**
	 * The default value of the '{@link #getProcessingInstruction() <em>Processing Instruction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProcessingInstruction()
	 * @generated
	 * @ordered
	 */
	protected static final ProcessingInstruction PROCESSING_INSTRUCTION_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getProcessingInstruction() <em>Processing Instruction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProcessingInstruction()
	 * @generated
	 * @ordered
	 */
	protected ProcessingInstruction processingInstruction = PROCESSING_INSTRUCTION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VEXProcessingInstructionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomEMFPackage.Literals.VEX_PROCESSING_INSTRUCTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<VEXAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectResolvingEList<VEXAttribute>(VEXAttribute.class, this, DomEMFPackage.VEX_PROCESSING_INSTRUCTION__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessingInstruction getProcessingInstruction() {
		return processingInstruction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProcessingInstruction(ProcessingInstruction newProcessingInstruction) {
		ProcessingInstruction oldProcessingInstruction = processingInstruction;
		processingInstruction = newProcessingInstruction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomEMFPackage.VEX_PROCESSING_INSTRUCTION__PROCESSING_INSTRUCTION, oldProcessingInstruction, processingInstruction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean removeAttribute(String attributeName) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttribute(String attributeName, String value) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DomEMFPackage.VEX_PROCESSING_INSTRUCTION__ATTRIBUTES:
				return getAttributes();
			case DomEMFPackage.VEX_PROCESSING_INSTRUCTION__PROCESSING_INSTRUCTION:
				return getProcessingInstruction();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DomEMFPackage.VEX_PROCESSING_INSTRUCTION__ATTRIBUTES:
				getAttributes().clear();
				getAttributes().addAll((Collection<? extends VEXAttribute>)newValue);
				return;
			case DomEMFPackage.VEX_PROCESSING_INSTRUCTION__PROCESSING_INSTRUCTION:
				setProcessingInstruction((ProcessingInstruction)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case DomEMFPackage.VEX_PROCESSING_INSTRUCTION__ATTRIBUTES:
				getAttributes().clear();
				return;
			case DomEMFPackage.VEX_PROCESSING_INSTRUCTION__PROCESSING_INSTRUCTION:
				setProcessingInstruction(PROCESSING_INSTRUCTION_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DomEMFPackage.VEX_PROCESSING_INSTRUCTION__ATTRIBUTES:
				return attributes != null && !attributes.isEmpty();
			case DomEMFPackage.VEX_PROCESSING_INSTRUCTION__PROCESSING_INSTRUCTION:
				return PROCESSING_INSTRUCTION_EDEFAULT == null ? processingInstruction != null : !PROCESSING_INSTRUCTION_EDEFAULT.equals(processingInstruction);
		}
		return super.eIsSet(featureID);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (processingInstruction: ");
		result.append(processingInstruction);
		result.append(')');
		return result.toString();
	}

	@Override
	public String getNodeType() {
		return "ProcessingInstruction";
	}

} //VEXProcessingInstructionImpl
