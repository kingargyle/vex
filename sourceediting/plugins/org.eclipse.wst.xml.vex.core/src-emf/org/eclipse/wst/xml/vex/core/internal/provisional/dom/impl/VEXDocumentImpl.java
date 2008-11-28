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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.wst.xml.vex.core.internal.dom.DocumentValidationException;
import org.eclipse.wst.xml.vex.core.internal.dom.RootElement;

import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Content;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage;

import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Position;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocumentFragment;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Validator;
import org.w3c.dom.Document;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>VEX Document</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXDocumentImpl#getEncoding <em>Encoding</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXDocumentImpl#getLength <em>Length</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXDocumentImpl#getRootElement <em>Root Element</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXDocumentImpl#getValidator <em>Validator</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXDocumentImpl#getPublicID <em>Public ID</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXDocumentImpl#getSystemID <em>System ID</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXDocumentImpl#getDocument <em>Document</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VEXDocumentImpl extends EObjectImpl implements VEXDocument {
	/**
	 * The default value of the '{@link #getEncoding() <em>Encoding</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEncoding()
	 * @generated
	 * @ordered
	 */
	protected static final String ENCODING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEncoding() <em>Encoding</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEncoding()
	 * @generated
	 * @ordered
	 */
	protected String encoding = ENCODING_EDEFAULT;

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
	 * The cached value of the '{@link #getRootElement() <em>Root Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRootElement()
	 * @generated
	 * @ordered
	 */
	protected VEXElement rootElement;

	/**
	 * The cached value of the '{@link #getValidator() <em>Validator</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidator()
	 * @generated
	 * @ordered
	 */
	protected Validator validator;

	/**
	 * The default value of the '{@link #getPublicID() <em>Public ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPublicID()
	 * @generated
	 * @ordered
	 */
	protected static final String PUBLIC_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPublicID() <em>Public ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPublicID()
	 * @generated
	 * @ordered
	 */
	protected String publicID = PUBLIC_ID_EDEFAULT;
	
	/**
	 * The default value of the '{@link #getSystemID() <em>System ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSystemID()
	 * @generated
	 * @ordered
	 */
	protected static final String SYSTEM_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSystemID() <em>System ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSystemID()
	 * @generated
	 * @ordered
	 */
	protected String systemID = SYSTEM_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getDocument() <em>Document</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocument()
	 * @generated
	 * @ordered
	 */
	protected static final Document DOCUMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDocument() <em>Document</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocument()
	 * @generated
	 * @ordered
	 */
	protected Document document = DOCUMENT_EDEFAULT;

	protected Content content;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VEXDocumentImpl() {
		
		super();
	}

	public VEXDocumentImpl(Content content, VEXElement rootElement) {
		this.content = content;
		this.rootElement = rootElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomEMFPackage.Literals.VEX_DOCUMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEncoding(String newEncoding) {
		String oldEncoding = encoding;
		encoding = newEncoding;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomEMFPackage.VEX_DOCUMENT__ENCODING, oldEncoding, encoding));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DomEMFPackage.VEX_DOCUMENT__LENGTH, oldLength, length));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VEXElement getRootElement() {
		if (rootElement != null && rootElement.eIsProxy()) {
			InternalEObject oldRootElement = (InternalEObject)rootElement;
			rootElement = (VEXElement)eResolveProxy(oldRootElement);
			if (rootElement != oldRootElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DomEMFPackage.VEX_DOCUMENT__ROOT_ELEMENT, oldRootElement, rootElement));
			}
		}
		return rootElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VEXElement basicGetRootElement() {
		return rootElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRootElement(VEXElement newRootElement) {
		VEXElement oldRootElement = rootElement;
		rootElement = newRootElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomEMFPackage.VEX_DOCUMENT__ROOT_ELEMENT, oldRootElement, rootElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Validator getValidator() {
		if (validator != null && validator.eIsProxy()) {
			InternalEObject oldValidator = (InternalEObject)validator;
			validator = (Validator)eResolveProxy(oldValidator);
			if (validator != oldValidator) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DomEMFPackage.VEX_DOCUMENT__VALIDATOR, oldValidator, validator));
			}
		}
		return validator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Validator basicGetValidator() {
		return validator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValidator(Validator newValidator) {
		Validator oldValidator = validator;
		validator = newValidator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomEMFPackage.VEX_DOCUMENT__VALIDATOR, oldValidator, validator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPublicID() {
		return publicID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPublicID(String newPublicID) {
		String oldPublicID = publicID;
		publicID = newPublicID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomEMFPackage.VEX_DOCUMENT__PUBLIC_ID, oldPublicID, publicID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSystemID() {
		return systemID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSystemID(String newSystemID) {
		String oldSystemID = systemID;
		systemID = newSystemID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomEMFPackage.VEX_DOCUMENT__SYSTEM_ID, oldSystemID, systemID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDocument(Document newDocument) {
		Document oldDocument = document;
		document = newDocument;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomEMFPackage.VEX_DOCUMENT__DOCUMENT, oldDocument, document));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean canInsertFragment(int offset, VEXDocumentFragment fragment) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean canInsertText(int offset) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Position createPosition(int offset) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void delete(int startOffset, int endOffset) throws DocumentValidationException {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VEXElement findCommonElement(int offset1, int offset2) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public char getCharacterAt(int offset) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VEXElement getElementAt(int offset) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VEXDocumentFragment getFragment(int startOffset, int endOffset) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getNodeNames(int startOffset, int endOffset) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<VEXNode> getNodes(int startOffset, int endOffset) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRawText(int startOffset, int endOffset) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getText(int startOffset, int endOffset) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void insertElement(int offset, VEXElement defaults) throws DocumentValidationException {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void insertFragment(int offset, VEXDocumentFragment fragment) throws DocumentValidationException {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void insertText(int offset, String text) throws DocumentValidationException {
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
			case DomEMFPackage.VEX_DOCUMENT__ENCODING:
				return getEncoding();
			case DomEMFPackage.VEX_DOCUMENT__LENGTH:
				return new Integer(getLength());
			case DomEMFPackage.VEX_DOCUMENT__ROOT_ELEMENT:
				if (resolve) return getRootElement();
				return basicGetRootElement();
			case DomEMFPackage.VEX_DOCUMENT__VALIDATOR:
				if (resolve) return getValidator();
				return basicGetValidator();
			case DomEMFPackage.VEX_DOCUMENT__PUBLIC_ID:
				return getPublicID();
			case DomEMFPackage.VEX_DOCUMENT__SYSTEM_ID:
				return getSystemID();
			case DomEMFPackage.VEX_DOCUMENT__DOCUMENT:
				return getDocument();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DomEMFPackage.VEX_DOCUMENT__ENCODING:
				setEncoding((String)newValue);
				return;
			case DomEMFPackage.VEX_DOCUMENT__LENGTH:
				setLength(((Integer)newValue).intValue());
				return;
			case DomEMFPackage.VEX_DOCUMENT__ROOT_ELEMENT:
				setRootElement((VEXElement)newValue);
				return;
			case DomEMFPackage.VEX_DOCUMENT__VALIDATOR:
				setValidator((Validator)newValue);
				return;
			case DomEMFPackage.VEX_DOCUMENT__PUBLIC_ID:
				setPublicID((String)newValue);
				return;
			case DomEMFPackage.VEX_DOCUMENT__SYSTEM_ID:
				setSystemID((String)newValue);
				return;
			case DomEMFPackage.VEX_DOCUMENT__DOCUMENT:
				setDocument((Document)newValue);
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
			case DomEMFPackage.VEX_DOCUMENT__ENCODING:
				setEncoding(ENCODING_EDEFAULT);
				return;
			case DomEMFPackage.VEX_DOCUMENT__LENGTH:
				setLength(LENGTH_EDEFAULT);
				return;
			case DomEMFPackage.VEX_DOCUMENT__ROOT_ELEMENT:
				setRootElement((VEXElement)null);
				return;
			case DomEMFPackage.VEX_DOCUMENT__VALIDATOR:
				setValidator((Validator)null);
				return;
			case DomEMFPackage.VEX_DOCUMENT__PUBLIC_ID:
				setPublicID(PUBLIC_ID_EDEFAULT);
				return;
			case DomEMFPackage.VEX_DOCUMENT__SYSTEM_ID:
				setSystemID(SYSTEM_ID_EDEFAULT);
				return;
			case DomEMFPackage.VEX_DOCUMENT__DOCUMENT:
				setDocument(DOCUMENT_EDEFAULT);
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
			case DomEMFPackage.VEX_DOCUMENT__ENCODING:
				return ENCODING_EDEFAULT == null ? encoding != null : !ENCODING_EDEFAULT.equals(encoding);
			case DomEMFPackage.VEX_DOCUMENT__LENGTH:
				return length != LENGTH_EDEFAULT;
			case DomEMFPackage.VEX_DOCUMENT__ROOT_ELEMENT:
				return rootElement != null;
			case DomEMFPackage.VEX_DOCUMENT__VALIDATOR:
				return validator != null;
			case DomEMFPackage.VEX_DOCUMENT__PUBLIC_ID:
				return PUBLIC_ID_EDEFAULT == null ? publicID != null : !PUBLIC_ID_EDEFAULT.equals(publicID);
			case DomEMFPackage.VEX_DOCUMENT__SYSTEM_ID:
				return SYSTEM_ID_EDEFAULT == null ? systemID != null : !SYSTEM_ID_EDEFAULT.equals(systemID);
			case DomEMFPackage.VEX_DOCUMENT__DOCUMENT:
				return DOCUMENT_EDEFAULT == null ? document != null : !DOCUMENT_EDEFAULT.equals(document);
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
		result.append(" (encoding: ");
		result.append(encoding);
		result.append(", length: ");
		result.append(length);
		result.append(", publicID: ");
		result.append(publicID);
		result.append(", systemID: ");
		result.append(systemID);
		result.append(", document: ");
		result.append(document);
		result.append(')');
		return result.toString();
	}

} //VEXDocumentImpl
