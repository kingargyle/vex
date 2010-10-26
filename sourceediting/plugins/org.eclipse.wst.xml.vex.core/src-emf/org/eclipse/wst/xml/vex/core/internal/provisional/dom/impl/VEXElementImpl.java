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

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.wst.xml.vex.core.internal.dom.DocumentValidationException;
import org.eclipse.wst.xml.vex.core.internal.dom.Node;

import org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage;

import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Content;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>VEX Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXElementImpl#getAttributeNames <em>Attribute Names</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXElementImpl#getChildElements <em>Child Elements</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXElementImpl#getChildNodes <em>Child Nodes</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXElementImpl#getDocument <em>Document</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXElementImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXElementImpl#isEmpty <em>Empty</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXElementImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXElementImpl#getNamespacePrefix <em>Namespace Prefix</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXElementImpl#getNamespaceURI <em>Namespace URI</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXElementImpl#getElement <em>Element</em>}</li>
 * </ul>
 * </p>
 *
 * @generated not
 */
public class VEXElementImpl extends Node implements VEXElement {
	/**
	 * The cached value of the '{@link #getAttributeNames() <em>Attribute Names</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeNames()
	 * @generated
	 * @ordered
	 */
	protected EList<String> attributeNames;

	/**
	 * The cached value of the '{@link #getChildElements() <em>Child Elements</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildElements()
	 * @generated not
	 * @ordered
	 * @deprecated use childNodes
	 */
	protected EList<VEXElement> childElements;

	/**
	 * The cached value of the '{@link #getChildNodes() <em>Child Nodes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<VEXNode> childNodes;

	/**
	 * The cached value of the '{@link #getDocument() <em>Document</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocument()
	 * @generated
	 * @ordered
	 */
	protected VEXDocument document;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isEmpty() <em>Empty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEmpty()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EMPTY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isEmpty() <em>Empty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEmpty()
	 * @generated
	 * @ordered
	 */
	protected boolean empty = EMPTY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected VEXElement parent;

	/**
	 * The default value of the '{@link #getNamespacePrefix() <em>Namespace Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespacePrefix()
	 * @generated
	 * @ordered
	 */
	protected static final String NAMESPACE_PREFIX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNamespacePrefix() <em>Namespace Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespacePrefix()
	 * @generated
	 * @ordered
	 */
	protected String namespacePrefix = NAMESPACE_PREFIX_EDEFAULT;

	/**
	 * The default value of the '{@link #getNamespaceURI() <em>Namespace URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespaceURI()
	 * @generated
	 * @ordered
	 */
	protected static final String NAMESPACE_URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNamespaceURI() <em>Namespace URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespaceURI()
	 * @generated
	 * @ordered
	 */
	protected String namespaceURI = NAMESPACE_URI_EDEFAULT;

	/**
	 * The default value of the '{@link #getElement() <em>Element</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElement()
	 * @generated
	 * @ordered
	 */
	protected static final Element ELEMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getElement() <em>Element</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElement()
	 * @generated
	 * @ordered
	 */
	protected Element element = ELEMENT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VEXElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomEMFPackage.Literals.VEX_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getAttributeNames() {
		if (attributeNames == null) {
			attributeNames = new EDataTypeUniqueEList<String>(String.class, this, DomEMFPackage.VEX_ELEMENT__ATTRIBUTE_NAMES);
		}
		return attributeNames;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<VEXElement> getChildElements() {
		if (childElements == null) {
			childElements = new EObjectResolvingEList<VEXElement>(VEXElement.class, this, DomEMFPackage.VEX_ELEMENT__CHILD_ELEMENTS);
		}
		return childElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<VEXNode> getChildNodes() {
		if (childNodes == null) {
			childNodes = new EObjectResolvingEList<VEXNode>(VEXNode.class, this, DomEMFPackage.VEX_ELEMENT__CHILD_NODES);
		}
		return childNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VEXDocument getDocument() {
		if (document != null && document.eIsProxy()) {
			InternalEObject oldDocument = (InternalEObject)document;
			document = (VEXDocument)eResolveProxy(oldDocument);
			if (document != oldDocument) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DomEMFPackage.VEX_ELEMENT__DOCUMENT, oldDocument, document));
			}
		}
		return document;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VEXDocument basicGetDocument() {
		return document;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDocument(VEXDocument newDocument) {
		VEXDocument oldDocument = document;
		document = newDocument;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomEMFPackage.VEX_ELEMENT__DOCUMENT, oldDocument, document));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomEMFPackage.VEX_ELEMENT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isEmpty() {
		return empty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEmpty(boolean newEmpty) {
		boolean oldEmpty = empty;
		empty = newEmpty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomEMFPackage.VEX_ELEMENT__EMPTY, oldEmpty, empty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VEXElement getParent() {
		if (parent != null && parent.eIsProxy()) {
			InternalEObject oldParent = (InternalEObject)parent;
			parent = (VEXElement)eResolveProxy(oldParent);
			if (parent != oldParent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DomEMFPackage.VEX_ELEMENT__PARENT, oldParent, parent));
			}
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VEXElement basicGetParent() {
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(VEXElement newParent) {
		VEXElement oldParent = parent;
		parent = newParent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomEMFPackage.VEX_ELEMENT__PARENT, oldParent, parent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNamespacePrefix() {
		return namespacePrefix;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNamespacePrefix(String newNamespacePrefix) {
		String oldNamespacePrefix = namespacePrefix;
		namespacePrefix = newNamespacePrefix;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomEMFPackage.VEX_ELEMENT__NAMESPACE_PREFIX, oldNamespacePrefix, namespacePrefix));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNamespaceURI() {
		return namespaceURI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNamespaceURI(String newNamespaceURI) {
		String oldNamespaceURI = namespaceURI;
		namespaceURI = newNamespaceURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomEMFPackage.VEX_ELEMENT__NAMESPACE_URI, oldNamespaceURI, namespaceURI));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Element getElement() {
		return element;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setElement(Element newElement) {
		Element oldElement = element;
		element = newElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomEMFPackage.VEX_ELEMENT__ELEMENT, oldElement, element));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void addChild(VEXElement child) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object clone() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAttribute(String name) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void removeAttribute(String name) throws DocumentValidationException {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttribute(String name, String value) throws DocumentValidationException {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setContent(Content content, int offset, int i) {
		super.setContent(content, offset, i);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void insertChild(int index, VEXElement child) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setNamespace(String prefix, String namespaceURI) {
		setNamespacePrefix(prefix);
		setNamespaceURI(namespaceURI);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DomEMFPackage.VEX_ELEMENT__ATTRIBUTE_NAMES:
				return getAttributeNames();
			case DomEMFPackage.VEX_ELEMENT__CHILD_ELEMENTS:
				return getChildElements();
			case DomEMFPackage.VEX_ELEMENT__CHILD_NODES:
				return getChildNodes();
			case DomEMFPackage.VEX_ELEMENT__DOCUMENT:
				if (resolve) return getDocument();
				return basicGetDocument();
			case DomEMFPackage.VEX_ELEMENT__NAME:
				return getName();
			case DomEMFPackage.VEX_ELEMENT__EMPTY:
				return isEmpty();
			case DomEMFPackage.VEX_ELEMENT__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
			case DomEMFPackage.VEX_ELEMENT__NAMESPACE_PREFIX:
				return getNamespacePrefix();
			case DomEMFPackage.VEX_ELEMENT__NAMESPACE_URI:
				return getNamespaceURI();
			case DomEMFPackage.VEX_ELEMENT__ELEMENT:
				return getElement();
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
			case DomEMFPackage.VEX_ELEMENT__ATTRIBUTE_NAMES:
				getAttributeNames().clear();
				getAttributeNames().addAll((Collection<? extends String>)newValue);
				return;
			case DomEMFPackage.VEX_ELEMENT__CHILD_ELEMENTS:
				getChildElements().clear();
				getChildElements().addAll((Collection<? extends VEXElement>)newValue);
				return;
			case DomEMFPackage.VEX_ELEMENT__CHILD_NODES:
				getChildNodes().clear();
				getChildNodes().addAll((Collection<? extends VEXNode>)newValue);
				return;
			case DomEMFPackage.VEX_ELEMENT__DOCUMENT:
				setDocument((VEXDocument)newValue);
				return;
			case DomEMFPackage.VEX_ELEMENT__NAME:
				setName((String)newValue);
				return;
			case DomEMFPackage.VEX_ELEMENT__EMPTY:
				setEmpty((Boolean)newValue);
				return;
			case DomEMFPackage.VEX_ELEMENT__PARENT:
				setParent((VEXElement)newValue);
				return;
			case DomEMFPackage.VEX_ELEMENT__NAMESPACE_PREFIX:
				setNamespacePrefix((String)newValue);
				return;
			case DomEMFPackage.VEX_ELEMENT__NAMESPACE_URI:
				setNamespaceURI((String)newValue);
				return;
			case DomEMFPackage.VEX_ELEMENT__ELEMENT:
				setElement((Element)newValue);
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
			case DomEMFPackage.VEX_ELEMENT__ATTRIBUTE_NAMES:
				getAttributeNames().clear();
				return;
			case DomEMFPackage.VEX_ELEMENT__CHILD_ELEMENTS:
				getChildElements().clear();
				return;
			case DomEMFPackage.VEX_ELEMENT__CHILD_NODES:
				getChildNodes().clear();
				return;
			case DomEMFPackage.VEX_ELEMENT__DOCUMENT:
				setDocument((VEXDocument)null);
				return;
			case DomEMFPackage.VEX_ELEMENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DomEMFPackage.VEX_ELEMENT__EMPTY:
				setEmpty(EMPTY_EDEFAULT);
				return;
			case DomEMFPackage.VEX_ELEMENT__PARENT:
				setParent((VEXElement)null);
				return;
			case DomEMFPackage.VEX_ELEMENT__NAMESPACE_PREFIX:
				setNamespacePrefix(NAMESPACE_PREFIX_EDEFAULT);
				return;
			case DomEMFPackage.VEX_ELEMENT__NAMESPACE_URI:
				setNamespaceURI(NAMESPACE_URI_EDEFAULT);
				return;
			case DomEMFPackage.VEX_ELEMENT__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
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
			case DomEMFPackage.VEX_ELEMENT__ATTRIBUTE_NAMES:
				return attributeNames != null && !attributeNames.isEmpty();
			case DomEMFPackage.VEX_ELEMENT__CHILD_ELEMENTS:
				return childElements != null && !childElements.isEmpty();
			case DomEMFPackage.VEX_ELEMENT__CHILD_NODES:
				return childNodes != null && !childNodes.isEmpty();
			case DomEMFPackage.VEX_ELEMENT__DOCUMENT:
				return document != null;
			case DomEMFPackage.VEX_ELEMENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DomEMFPackage.VEX_ELEMENT__EMPTY:
				return empty != EMPTY_EDEFAULT;
			case DomEMFPackage.VEX_ELEMENT__PARENT:
				return parent != null;
			case DomEMFPackage.VEX_ELEMENT__NAMESPACE_PREFIX:
				return NAMESPACE_PREFIX_EDEFAULT == null ? namespacePrefix != null : !NAMESPACE_PREFIX_EDEFAULT.equals(namespacePrefix);
			case DomEMFPackage.VEX_ELEMENT__NAMESPACE_URI:
				return NAMESPACE_URI_EDEFAULT == null ? namespaceURI != null : !NAMESPACE_URI_EDEFAULT.equals(namespaceURI);
			case DomEMFPackage.VEX_ELEMENT__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
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
		result.append(" (attributeNames: ");
		result.append(attributeNames);
		result.append(", name: ");
		result.append(name);
		result.append(", empty: ");
		result.append(empty);
		result.append(", namespacePrefix: ");
		result.append(namespacePrefix);
		result.append(", namespaceURI: ");
		result.append(namespaceURI);
		result.append(", element: ");
		result.append(element);
		result.append(')');
		return result.toString();
	}
	
	@Override
	public String getNodeType() {
		return "Element";
	}
	
	@Override
	public String getBaseURI() {
		final String baseAttributeValue = getAttribute("xml:base");
		if (baseAttributeValue != null)
			return baseAttributeValue;
		if (getParent() != null)
			return getParent().getBaseURI();
		if (getDocument() != null)
			return getDocument().getBaseURI();
		return null;
	}

} //VEXElementImpl
