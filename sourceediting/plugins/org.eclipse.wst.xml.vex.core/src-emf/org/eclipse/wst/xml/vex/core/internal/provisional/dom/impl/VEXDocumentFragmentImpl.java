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
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage;

import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Content;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocumentFragment;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>VEX Document Fragment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXDocumentFragmentImpl#getContent <em>Content</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXDocumentFragmentImpl#getLength <em>Length</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXDocumentFragmentImpl#getElements <em>Elements</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXDocumentFragmentImpl#getNodeNames <em>Node Names</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXDocumentFragmentImpl#getNodes <em>Nodes</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VEXDocumentFragmentImpl extends EObjectImpl implements VEXDocumentFragment {
	/**
	 * The cached value of the '{@link #getContent() <em>Content</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected Content content;

	/**
	 * The default value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected static final int LENGTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected int length = LENGTH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getElements() <em>Elements</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElements()
	 * @generated
	 * @ordered
	 */
	protected EList<VEXElement> elements;

	/**
	 * The cached value of the '{@link #getNodeNames() <em>Node Names</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodeNames()
	 * @generated
	 * @ordered
	 */
	protected EList<String> nodeNames;

	/**
	 * The cached value of the '{@link #getNodes() <em>Nodes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<VEXNode> nodes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VEXDocumentFragmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomEMFPackage.Literals.VEX_DOCUMENT_FRAGMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Content getContent() {
		if (content != null && content.eIsProxy()) {
			InternalEObject oldContent = (InternalEObject)content;
			content = (Content)eResolveProxy(oldContent);
			if (content != oldContent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DomEMFPackage.VEX_DOCUMENT_FRAGMENT__CONTENT, oldContent, content));
			}
		}
		return content;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Content basicGetContent() {
		return content;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContent(Content newContent) {
		Content oldContent = content;
		content = newContent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomEMFPackage.VEX_DOCUMENT_FRAGMENT__CONTENT, oldContent, content));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLength() {
		return length;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLength(int newLength) {
		int oldLength = length;
		length = newLength;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomEMFPackage.VEX_DOCUMENT_FRAGMENT__LENGTH, oldLength, length));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<VEXElement> getElements() {
		if (elements == null) {
			elements = new EObjectResolvingEList<VEXElement>(VEXElement.class, this, DomEMFPackage.VEX_DOCUMENT_FRAGMENT__ELEMENTS);
		}
		return elements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getNodeNames() {
		if (nodeNames == null) {
			nodeNames = new EDataTypeUniqueEList<String>(String.class, this, DomEMFPackage.VEX_DOCUMENT_FRAGMENT__NODE_NAMES);
		}
		return nodeNames;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<VEXNode> getNodes() {
		if (nodes == null) {
			nodes = new EObjectResolvingEList<VEXNode>(VEXNode.class, this, DomEMFPackage.VEX_DOCUMENT_FRAGMENT__NODES);
		}
		return nodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DomEMFPackage.VEX_DOCUMENT_FRAGMENT__CONTENT:
				if (resolve) return getContent();
				return basicGetContent();
			case DomEMFPackage.VEX_DOCUMENT_FRAGMENT__LENGTH:
				return new Integer(getLength());
			case DomEMFPackage.VEX_DOCUMENT_FRAGMENT__ELEMENTS:
				return getElements();
			case DomEMFPackage.VEX_DOCUMENT_FRAGMENT__NODE_NAMES:
				return getNodeNames();
			case DomEMFPackage.VEX_DOCUMENT_FRAGMENT__NODES:
				return getNodes();
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
			case DomEMFPackage.VEX_DOCUMENT_FRAGMENT__CONTENT:
				setContent((Content)newValue);
				return;
			case DomEMFPackage.VEX_DOCUMENT_FRAGMENT__LENGTH:
				setLength(((Integer)newValue).intValue());
				return;
			case DomEMFPackage.VEX_DOCUMENT_FRAGMENT__ELEMENTS:
				getElements().clear();
				getElements().addAll((Collection<? extends VEXElement>)newValue);
				return;
			case DomEMFPackage.VEX_DOCUMENT_FRAGMENT__NODE_NAMES:
				getNodeNames().clear();
				getNodeNames().addAll((Collection<? extends String>)newValue);
				return;
			case DomEMFPackage.VEX_DOCUMENT_FRAGMENT__NODES:
				getNodes().clear();
				getNodes().addAll((Collection<? extends VEXNode>)newValue);
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
			case DomEMFPackage.VEX_DOCUMENT_FRAGMENT__CONTENT:
				setContent((Content)null);
				return;
			case DomEMFPackage.VEX_DOCUMENT_FRAGMENT__LENGTH:
				setLength(LENGTH_EDEFAULT);
				return;
			case DomEMFPackage.VEX_DOCUMENT_FRAGMENT__ELEMENTS:
				getElements().clear();
				return;
			case DomEMFPackage.VEX_DOCUMENT_FRAGMENT__NODE_NAMES:
				getNodeNames().clear();
				return;
			case DomEMFPackage.VEX_DOCUMENT_FRAGMENT__NODES:
				getNodes().clear();
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
			case DomEMFPackage.VEX_DOCUMENT_FRAGMENT__CONTENT:
				return content != null;
			case DomEMFPackage.VEX_DOCUMENT_FRAGMENT__LENGTH:
				return length != LENGTH_EDEFAULT;
			case DomEMFPackage.VEX_DOCUMENT_FRAGMENT__ELEMENTS:
				return elements != null && !elements.isEmpty();
			case DomEMFPackage.VEX_DOCUMENT_FRAGMENT__NODE_NAMES:
				return nodeNames != null && !nodeNames.isEmpty();
			case DomEMFPackage.VEX_DOCUMENT_FRAGMENT__NODES:
				return nodes != null && !nodes.isEmpty();
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
		result.append(" (length: ");
		result.append(length);
		result.append(", nodeNames: ");
		result.append(nodeNames);
		result.append(')');
		return result.toString();
	}

} //VEXDocumentFragmentImpl
