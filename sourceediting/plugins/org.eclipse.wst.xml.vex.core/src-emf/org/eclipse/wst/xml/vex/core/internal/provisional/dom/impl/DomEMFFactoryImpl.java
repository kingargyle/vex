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

import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.wst.xml.vex.core.internal.dom.DocumentValidationException;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFFactory;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage;
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
import org.eclipse.wst.xml.vex.core.internal.validator.AttributeDefinition;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ProcessingInstruction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DomEMFFactoryImpl extends EFactoryImpl implements DomEMFFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DomEMFFactory init() {
		try {
			DomEMFFactory theDomEMFFactory = (DomEMFFactory)EPackage.Registry.INSTANCE.getEFactory("http:///org/eclipse/wst/xml/vex/core/internal/provisional/dom.ecore"); 
			if (theDomEMFFactory != null) {
				return theDomEMFFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DomEMFFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomEMFFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case DomEMFPackage.CONTENT: return createContent();
			case DomEMFPackage.POSITION: return createPosition();
			case DomEMFPackage.VEX_ATTRIBUTE: return createVEXAttribute();
			case DomEMFPackage.VEX_COMMENT: return createVEXComment();
			case DomEMFPackage.VEX_DOCUMENT: return createVEXDocument();
			case DomEMFPackage.VEX_DOCUMENT_FRAGMENT: return createVEXDocumentFragment();
			case DomEMFPackage.VEX_ELEMENT: return createVEXElement();
			case DomEMFPackage.VEX_NODE: return createVEXNode();
			case DomEMFPackage.VEX_PROCESSING_INSTRUCTION: return createVEXProcessingInstruction();
			case DomEMFPackage.VALIDATOR: return createValidator();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case DomEMFPackage.DOCUMENT_VALIDATION_EXCEPTION:
				return createDocumentValidationExceptionFromString(eDataType, initialValue);
			case DomEMFPackage.ATTRIBUTE_DEFINITION:
				return createAttributeDefinitionFromString(eDataType, initialValue);
			case DomEMFPackage.SET:
				return createSetFromString(eDataType, initialValue);
			case DomEMFPackage.DOM_DOCUMENT:
				return createDOMDocumentFromString(eDataType, initialValue);
			case DomEMFPackage.DOM_ELEMENT:
				return createDOMElementFromString(eDataType, initialValue);
			case DomEMFPackage.DOM_ATTR:
				return createDOMAttrFromString(eDataType, initialValue);
			case DomEMFPackage.DOM_COMMENT:
				return createDOMCommentFromString(eDataType, initialValue);
			case DomEMFPackage.DOM_PROCESSING_INSTRUCTION:
				return createDOMProcessingInstructionFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case DomEMFPackage.DOCUMENT_VALIDATION_EXCEPTION:
				return convertDocumentValidationExceptionToString(eDataType, instanceValue);
			case DomEMFPackage.ATTRIBUTE_DEFINITION:
				return convertAttributeDefinitionToString(eDataType, instanceValue);
			case DomEMFPackage.SET:
				return convertSetToString(eDataType, instanceValue);
			case DomEMFPackage.DOM_DOCUMENT:
				return convertDOMDocumentToString(eDataType, instanceValue);
			case DomEMFPackage.DOM_ELEMENT:
				return convertDOMElementToString(eDataType, instanceValue);
			case DomEMFPackage.DOM_ATTR:
				return convertDOMAttrToString(eDataType, instanceValue);
			case DomEMFPackage.DOM_COMMENT:
				return convertDOMCommentToString(eDataType, instanceValue);
			case DomEMFPackage.DOM_PROCESSING_INSTRUCTION:
				return convertDOMProcessingInstructionToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Content createContent() {
		ContentImpl content = new ContentImpl();
		return content;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Position createPosition() {
		PositionImpl position = new PositionImpl();
		return position;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VEXAttribute createVEXAttribute() {
		VEXAttributeImpl vexAttribute = new VEXAttributeImpl();
		return vexAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VEXComment createVEXComment() {
		VEXCommentImpl vexComment = new VEXCommentImpl();
		return vexComment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VEXDocument createVEXDocument() {
		VEXDocumentImpl vexDocument = new VEXDocumentImpl();
		return vexDocument;
	}
	
	public VEXDocument createVEXDocument(Content content, VEXElement rootElement) {
		VEXDocumentImpl vexDocument = new VEXDocumentImpl();
		return vexDocument;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VEXDocumentFragment createVEXDocumentFragment() {
		VEXDocumentFragmentImpl vexDocumentFragment = new VEXDocumentFragmentImpl();
		return vexDocumentFragment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VEXElement createVEXElement() {
		VEXElementImpl vexElement = new VEXElementImpl();
		return vexElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VEXNode createVEXNode() {
		VEXNodeImpl vexNode = new VEXNodeImpl();
		return vexNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VEXProcessingInstruction createVEXProcessingInstruction() {
		VEXProcessingInstructionImpl vexProcessingInstruction = new VEXProcessingInstructionImpl();
		return vexProcessingInstruction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Validator createValidator() {
		ValidatorImpl validator = new ValidatorImpl();
		return validator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentValidationException createDocumentValidationExceptionFromString(EDataType eDataType, String initialValue) {
		return (DocumentValidationException)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDocumentValidationExceptionToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeDefinition createAttributeDefinitionFromString(EDataType eDataType, String initialValue) {
		return (AttributeDefinition)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAttributeDefinitionToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Set<?> createSetFromString(EDataType eDataType, String initialValue) {
		return (Set<?>)super.createFromString(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSetToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Document createDOMDocumentFromString(EDataType eDataType, String initialValue) {
		return (Document)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDOMDocumentToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Element createDOMElementFromString(EDataType eDataType, String initialValue) {
		return (Element)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDOMElementToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attr createDOMAttrFromString(EDataType eDataType, String initialValue) {
		return (Attr)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDOMAttrToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Comment createDOMCommentFromString(EDataType eDataType, String initialValue) {
		return (Comment)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDOMCommentToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessingInstruction createDOMProcessingInstructionFromString(EDataType eDataType, String initialValue) {
		return (ProcessingInstruction)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDOMProcessingInstructionToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomEMFPackage getDomEMFPackage() {
		return (DomEMFPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DomEMFPackage getPackage() {
		return DomEMFPackage.eINSTANCE;
	}

} //DomEMFFactoryImpl
