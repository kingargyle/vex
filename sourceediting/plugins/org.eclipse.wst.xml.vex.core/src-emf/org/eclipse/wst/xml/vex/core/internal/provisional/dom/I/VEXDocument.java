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

import org.eclipse.emf.ecore.EObject;

import org.eclipse.wst.xml.vex.core.internal.dom.DocumentValidationException;
import org.w3c.dom.Document;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>VEX Document</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getEncoding <em>Encoding</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getLength <em>Length</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getRootElement <em>Root Element</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getValidator <em>Validator</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getPublicID <em>Public ID</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getSystemID <em>System ID</em>}</li>
 *   <li>{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getDocument <em>Document</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage#getVEXDocument()
 * @model
 * @generated
 */
public interface VEXDocument extends EObject {
	/**
	 * Returns the value of the '<em><b>Encoding</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Encoding</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Encoding</em>' attribute.
	 * @see #setEncoding(String)
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage#getVEXDocument_Encoding()
	 * @model
	 * @generated
	 */
	String getEncoding();

	/**
	 * Sets the value of the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getEncoding <em>Encoding</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Encoding</em>' attribute.
	 * @see #getEncoding()
	 * @generated
	 */
	void setEncoding(String value);

	/**
	 * Returns the value of the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Length</em>' attribute.
	 * @see #setLength(int)
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage#getVEXDocument_Length()
	 * @model
	 * @generated
	 */
	int getLength();

	/**
	 * Sets the value of the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getLength <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Length</em>' attribute.
	 * @see #getLength()
	 * @generated
	 */
	void setLength(int value);

	/**
	 * Returns the value of the '<em><b>Root Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root Element</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root Element</em>' reference.
	 * @see #setRootElement(VEXElement)
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage#getVEXDocument_RootElement()
	 * @model
	 * @generated
	 */
	VEXElement getRootElement();

	/**
	 * Sets the value of the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getRootElement <em>Root Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root Element</em>' reference.
	 * @see #getRootElement()
	 * @generated
	 */
	void setRootElement(VEXElement value);

	/**
	 * Returns the value of the '<em><b>Validator</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Validator</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Validator</em>' reference.
	 * @see #setValidator(Validator)
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage#getVEXDocument_Validator()
	 * @model
	 * @generated
	 */
	Validator getValidator();

	/**
	 * Sets the value of the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getValidator <em>Validator</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Validator</em>' reference.
	 * @see #getValidator()
	 * @generated
	 */
	void setValidator(Validator value);

	/**
	 * Returns the value of the '<em><b>Public ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Public ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Public ID</em>' attribute.
	 * @see #setPublicID(String)
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage#getVEXDocument_PublicID()
	 * @model
	 * @generated
	 */
	String getPublicID();

	/**
	 * Sets the value of the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getPublicID <em>Public ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Public ID</em>' attribute.
	 * @see #getPublicID()
	 * @generated
	 */
	void setPublicID(String value);

	/**
	 * Returns the value of the '<em><b>System ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>System ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>System ID</em>' attribute.
	 * @see #setSystemID(String)
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage#getVEXDocument_SystemID()
	 * @model
	 * @generated
	 */
	String getSystemID();

	/**
	 * Sets the value of the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getSystemID <em>System ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>System ID</em>' attribute.
	 * @see #getSystemID()
	 * @generated
	 */
	void setSystemID(String value);

	/**
	 * Returns the value of the '<em><b>Document</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Document</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Document</em>' attribute.
	 * @see #setDocument(Document)
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage#getVEXDocument_Document()
	 * @model dataType="org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.DOMDocument"
	 * @generated
	 */
	Document getDocument();

	/**
	 * Sets the value of the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getDocument <em>Document</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Document</em>' attribute.
	 * @see #getDocument()
	 * @generated
	 */
	void setDocument(Document value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean canInsertFragment(int offset, VEXDocumentFragment fragment);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean canInsertText(int offset);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Position createPosition(int offset);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model exceptions="org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.DocumentValidationException"
	 * @generated
	 */
	void delete(int startOffset, int endOffset) throws DocumentValidationException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	VEXElement findCommonElement(int offset1, int offset2);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	char getCharacterAt(int offset);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	VEXElement getElementAt(int offset);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	VEXDocumentFragment getFragment(int startOffset, int endOffset);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	EList<String> getNodeNames(int startOffset, int endOffset);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	EList<VEXNode> getNodes(int startOffset, int endOffset);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	String getRawText(int startOffset, int endOffset);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	String getText(int startOffset, int endOffset);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model exceptions="org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.DocumentValidationException"
	 * @generated
	 */
	void insertElement(int offset, VEXElement defaults) throws DocumentValidationException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model exceptions="org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.DocumentValidationException"
	 * @generated
	 */
	void insertFragment(int offset, VEXDocumentFragment fragment) throws DocumentValidationException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model exceptions="org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.DocumentValidationException"
	 * @generated
	 */
	void insertText(int offset, String text) throws DocumentValidationException;
	
	void setDOMDocument(org.w3c.dom.Document domDocument);
	
	org.w3c.dom.Document getDOMDocument();

} // VEXDocument
