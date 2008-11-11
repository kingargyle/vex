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

import java.io.Serializable;

import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

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

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DomEMFPackageImpl extends EPackageImpl implements DomEMFPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass contentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass positionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass vexAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass vexCommentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass vexDocumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass vexDocumentFragmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass vexElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass vexNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass vexProcessingInstructionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass validatorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass serializableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType documentValidationExceptionEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType attributeDefinitionEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType setEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DomEMFPackageImpl() {
		super(eNS_URI, DomEMFFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DomEMFPackage init() {
		if (isInited) return (DomEMFPackage)EPackage.Registry.INSTANCE.getEPackage(DomEMFPackage.eNS_URI);

		// Obtain or create and register package
		DomEMFPackageImpl theDomEMFPackage = (DomEMFPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof DomEMFPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new DomEMFPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theDomEMFPackage.createPackageContents();

		// Initialize created meta-data
		theDomEMFPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDomEMFPackage.freeze();

		return theDomEMFPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getContent() {
		return contentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getContent_Length() {
		return (EAttribute)contentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPosition() {
		return positionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPosition_Offset() {
		return (EAttribute)positionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVEXAttribute() {
		return vexAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVEXAttribute_Document() {
		return (EReference)vexAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVEXAttribute_Value() {
		return (EAttribute)vexAttributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVEXAttribute_Parent() {
		return (EReference)vexAttributeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVEXAttribute_LocalName() {
		return (EAttribute)vexAttributeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVEXAttribute_NamespacePrefix() {
		return (EAttribute)vexAttributeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVEXAttribute_NamespaceURI() {
		return (EAttribute)vexAttributeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVEXComment() {
		return vexCommentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVEXComment_Value() {
		return (EAttribute)vexCommentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVEXDocument() {
		return vexDocumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVEXDocument_Encoding() {
		return (EAttribute)vexDocumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVEXDocument_Length() {
		return (EAttribute)vexDocumentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVEXDocument_RootElement() {
		return (EReference)vexDocumentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVEXDocument_Validator() {
		return (EReference)vexDocumentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVEXDocument_PublicID() {
		return (EAttribute)vexDocumentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVEXDocumentFragment() {
		return vexDocumentFragmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVEXDocumentFragment_Content() {
		return (EReference)vexDocumentFragmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVEXDocumentFragment_Length() {
		return (EAttribute)vexDocumentFragmentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVEXDocumentFragment_Elements() {
		return (EReference)vexDocumentFragmentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVEXDocumentFragment_NodeNames() {
		return (EAttribute)vexDocumentFragmentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVEXDocumentFragment_Nodes() {
		return (EReference)vexDocumentFragmentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVEXElement() {
		return vexElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVEXElement_AttributeNames() {
		return (EAttribute)vexElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVEXElement_ChildElements() {
		return (EReference)vexElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVEXElement_ChildNodes() {
		return (EReference)vexElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVEXElement_Document() {
		return (EReference)vexElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVEXElement_Name() {
		return (EAttribute)vexElementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVEXElement_Empty() {
		return (EAttribute)vexElementEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVEXElement_Parent() {
		return (EReference)vexElementEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVEXElement_NamespacePrefix() {
		return (EAttribute)vexElementEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVEXElement_NamespaceURI() {
		return (EAttribute)vexElementEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVEXNode() {
		return vexNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVEXNode_Content() {
		return (EReference)vexNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVEXNode_EndOffset() {
		return (EAttribute)vexNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVEXNode_EndPosition() {
		return (EReference)vexNodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVEXNode_StartOffset() {
		return (EAttribute)vexNodeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVEXNode_StartPosition() {
		return (EReference)vexNodeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVEXNode_Text() {
		return (EAttribute)vexNodeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVEXNode_NodeType() {
		return (EAttribute)vexNodeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVEXProcessingInstruction() {
		return vexProcessingInstructionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVEXProcessingInstruction_Attributes() {
		return (EReference)vexProcessingInstructionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getValidator() {
		return validatorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getValidator_ValidRootElements() {
		return (EAttribute)validatorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSerializable() {
		return serializableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getDocumentValidationException() {
		return documentValidationExceptionEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getAttributeDefinition() {
		return attributeDefinitionEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getSet() {
		return setEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomEMFFactory getDomEMFFactory() {
		return (DomEMFFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		contentEClass = createEClass(CONTENT);
		createEAttribute(contentEClass, CONTENT__LENGTH);

		positionEClass = createEClass(POSITION);
		createEAttribute(positionEClass, POSITION__OFFSET);

		vexAttributeEClass = createEClass(VEX_ATTRIBUTE);
		createEReference(vexAttributeEClass, VEX_ATTRIBUTE__DOCUMENT);
		createEAttribute(vexAttributeEClass, VEX_ATTRIBUTE__VALUE);
		createEReference(vexAttributeEClass, VEX_ATTRIBUTE__PARENT);
		createEAttribute(vexAttributeEClass, VEX_ATTRIBUTE__LOCAL_NAME);
		createEAttribute(vexAttributeEClass, VEX_ATTRIBUTE__NAMESPACE_PREFIX);
		createEAttribute(vexAttributeEClass, VEX_ATTRIBUTE__NAMESPACE_URI);

		vexCommentEClass = createEClass(VEX_COMMENT);
		createEAttribute(vexCommentEClass, VEX_COMMENT__VALUE);

		vexDocumentEClass = createEClass(VEX_DOCUMENT);
		createEAttribute(vexDocumentEClass, VEX_DOCUMENT__ENCODING);
		createEAttribute(vexDocumentEClass, VEX_DOCUMENT__LENGTH);
		createEReference(vexDocumentEClass, VEX_DOCUMENT__ROOT_ELEMENT);
		createEReference(vexDocumentEClass, VEX_DOCUMENT__VALIDATOR);
		createEAttribute(vexDocumentEClass, VEX_DOCUMENT__PUBLIC_ID);

		vexDocumentFragmentEClass = createEClass(VEX_DOCUMENT_FRAGMENT);
		createEReference(vexDocumentFragmentEClass, VEX_DOCUMENT_FRAGMENT__CONTENT);
		createEAttribute(vexDocumentFragmentEClass, VEX_DOCUMENT_FRAGMENT__LENGTH);
		createEReference(vexDocumentFragmentEClass, VEX_DOCUMENT_FRAGMENT__ELEMENTS);
		createEAttribute(vexDocumentFragmentEClass, VEX_DOCUMENT_FRAGMENT__NODE_NAMES);
		createEReference(vexDocumentFragmentEClass, VEX_DOCUMENT_FRAGMENT__NODES);

		vexElementEClass = createEClass(VEX_ELEMENT);
		createEAttribute(vexElementEClass, VEX_ELEMENT__ATTRIBUTE_NAMES);
		createEReference(vexElementEClass, VEX_ELEMENT__CHILD_ELEMENTS);
		createEReference(vexElementEClass, VEX_ELEMENT__CHILD_NODES);
		createEReference(vexElementEClass, VEX_ELEMENT__DOCUMENT);
		createEAttribute(vexElementEClass, VEX_ELEMENT__NAME);
		createEAttribute(vexElementEClass, VEX_ELEMENT__EMPTY);
		createEReference(vexElementEClass, VEX_ELEMENT__PARENT);
		createEAttribute(vexElementEClass, VEX_ELEMENT__NAMESPACE_PREFIX);
		createEAttribute(vexElementEClass, VEX_ELEMENT__NAMESPACE_URI);

		vexNodeEClass = createEClass(VEX_NODE);
		createEReference(vexNodeEClass, VEX_NODE__CONTENT);
		createEAttribute(vexNodeEClass, VEX_NODE__END_OFFSET);
		createEReference(vexNodeEClass, VEX_NODE__END_POSITION);
		createEAttribute(vexNodeEClass, VEX_NODE__START_OFFSET);
		createEReference(vexNodeEClass, VEX_NODE__START_POSITION);
		createEAttribute(vexNodeEClass, VEX_NODE__TEXT);
		createEAttribute(vexNodeEClass, VEX_NODE__NODE_TYPE);

		vexProcessingInstructionEClass = createEClass(VEX_PROCESSING_INSTRUCTION);
		createEReference(vexProcessingInstructionEClass, VEX_PROCESSING_INSTRUCTION__ATTRIBUTES);

		validatorEClass = createEClass(VALIDATOR);
		createEAttribute(validatorEClass, VALIDATOR__VALID_ROOT_ELEMENTS);

		serializableEClass = createEClass(SERIALIZABLE);

		// Create data types
		documentValidationExceptionEDataType = createEDataType(DOCUMENT_VALIDATION_EXCEPTION);
		attributeDefinitionEDataType = createEDataType(ATTRIBUTE_DEFINITION);
		setEDataType = createEDataType(SET);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters
		addETypeParameter(setEDataType, "T");

		// Set bounds for type parameters

		// Add supertypes to classes
		vexAttributeEClass.getESuperTypes().add(this.getVEXNode());
		vexCommentEClass.getESuperTypes().add(this.getVEXNode());
		vexElementEClass.getESuperTypes().add(this.getVEXNode());
		vexProcessingInstructionEClass.getESuperTypes().add(this.getVEXNode());
		validatorEClass.getESuperTypes().add(this.getSerializable());

		// Initialize classes and features; add operations and parameters
		initEClass(contentEClass, Content.class, "Content", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getContent_Length(), ecorePackage.getEInt(), "length", null, 0, 1, Content.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(contentEClass, this.getPosition(), "createPosition", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "offset", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(contentEClass, null, "insertString", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "offset", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "s", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(contentEClass, null, "remove", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "offset", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "length", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(contentEClass, ecorePackage.getEString(), "getString", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "offset", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "length", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(positionEClass, Position.class, "Position", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPosition_Offset(), ecorePackage.getEInt(), "offset", null, 0, 1, Position.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(vexAttributeEClass, VEXAttribute.class, "VEXAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVEXAttribute_Document(), this.getVEXDocument(), null, "document", null, 0, 1, VEXAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVEXAttribute_Value(), ecorePackage.getEString(), "value", null, 0, 1, VEXAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVEXAttribute_Parent(), this.getVEXElement(), null, "parent", null, 0, 1, VEXAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVEXAttribute_LocalName(), ecorePackage.getEString(), "localName", null, 0, 1, VEXAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVEXAttribute_NamespacePrefix(), ecorePackage.getEString(), "namespacePrefix", null, 0, 1, VEXAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVEXAttribute_NamespaceURI(), ecorePackage.getEString(), "namespaceURI", null, 0, 1, VEXAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(vexAttributeEClass, null, "setNamespace", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "prefix", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "namespaceURI", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(vexCommentEClass, VEXComment.class, "VEXComment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVEXComment_Value(), ecorePackage.getEString(), "value", null, 0, 1, VEXComment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(vexDocumentEClass, VEXDocument.class, "VEXDocument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVEXDocument_Encoding(), ecorePackage.getEString(), "encoding", null, 0, 1, VEXDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVEXDocument_Length(), ecorePackage.getEInt(), "length", null, 0, 1, VEXDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVEXDocument_RootElement(), this.getVEXElement(), null, "rootElement", null, 0, 1, VEXDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVEXDocument_Validator(), this.getValidator(), null, "validator", null, 0, 1, VEXDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVEXDocument_PublicID(), ecorePackage.getEString(), "publicID", null, 0, 1, VEXDocument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(vexDocumentEClass, ecorePackage.getEBoolean(), "canInsertFragment", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "offset", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVEXDocumentFragment(), "fragment", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(vexDocumentEClass, ecorePackage.getEBoolean(), "canInsertText", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "offset", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(vexDocumentEClass, this.getPosition(), "createPosition", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "offset", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(vexDocumentEClass, null, "delete", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "startOffset", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "endOffset", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getDocumentValidationException());

		op = addEOperation(vexDocumentEClass, this.getVEXElement(), "findCommonElement", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "offset1", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "offset2", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(vexDocumentEClass, ecorePackage.getEChar(), "getCharacterAt", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "offset", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(vexDocumentEClass, this.getVEXElement(), "getElementAt", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "offset", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(vexDocumentEClass, this.getVEXDocumentFragment(), "getFragment", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "startOffset", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "endOffset", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(vexDocumentEClass, ecorePackage.getEString(), "getNodeNames", 0, -1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "startOffset", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "endOffset", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(vexDocumentEClass, this.getVEXNode(), "getNodes", 0, -1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "startOffset", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "endOffset", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(vexDocumentEClass, ecorePackage.getEString(), "getRawText", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "startOffset", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "endOffset", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(vexDocumentEClass, ecorePackage.getEString(), "getText", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "startOffset", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "endOffset", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(vexDocumentEClass, null, "insertElement", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "offset", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVEXElement(), "defaults", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getDocumentValidationException());

		op = addEOperation(vexDocumentEClass, null, "insertFragment", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "offset", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVEXDocumentFragment(), "fragment", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getDocumentValidationException());

		op = addEOperation(vexDocumentEClass, null, "insertText", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "offset", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "text", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getDocumentValidationException());

		initEClass(vexDocumentFragmentEClass, VEXDocumentFragment.class, "VEXDocumentFragment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVEXDocumentFragment_Content(), this.getContent(), null, "content", null, 0, 1, VEXDocumentFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVEXDocumentFragment_Length(), ecorePackage.getEInt(), "length", null, 0, 1, VEXDocumentFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVEXDocumentFragment_Elements(), this.getVEXElement(), null, "elements", null, 0, -1, VEXDocumentFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVEXDocumentFragment_NodeNames(), ecorePackage.getEString(), "nodeNames", null, 0, -1, VEXDocumentFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVEXDocumentFragment_Nodes(), this.getVEXNode(), null, "nodes", null, 0, -1, VEXDocumentFragment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(vexElementEClass, VEXElement.class, "VEXElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVEXElement_AttributeNames(), ecorePackage.getEString(), "attributeNames", null, 0, -1, VEXElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVEXElement_ChildElements(), this.getVEXElement(), null, "childElements", null, 0, -1, VEXElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVEXElement_ChildNodes(), this.getVEXNode(), null, "childNodes", null, 0, -1, VEXElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVEXElement_Document(), this.getVEXDocument(), null, "document", null, 0, 1, VEXElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVEXElement_Name(), ecorePackage.getEString(), "name", null, 0, 1, VEXElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVEXElement_Empty(), ecorePackage.getEBoolean(), "empty", null, 0, 1, VEXElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVEXElement_Parent(), this.getVEXElement(), null, "parent", null, 0, 1, VEXElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVEXElement_NamespacePrefix(), ecorePackage.getEString(), "namespacePrefix", null, 0, 1, VEXElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVEXElement_NamespaceURI(), ecorePackage.getEString(), "namespaceURI", null, 0, 1, VEXElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(vexElementEClass, null, "addChild", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVEXElement(), "child", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(vexElementEClass, ecorePackage.getEJavaObject(), "clone", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(vexElementEClass, ecorePackage.getEString(), "getAttribute", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(vexElementEClass, null, "removeAttribute", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getDocumentValidationException());

		op = addEOperation(vexElementEClass, null, "setAttribute", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "value", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEException(op, this.getDocumentValidationException());

		op = addEOperation(vexElementEClass, null, "setContent", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getContent(), "content", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "offset", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "i", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(vexElementEClass, null, "insertChild", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "index", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getVEXElement(), "child", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(vexElementEClass, null, "setNamespace", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "prefix", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "namespaceURI", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(vexNodeEClass, VEXNode.class, "VEXNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVEXNode_Content(), this.getContent(), null, "content", null, 0, 1, VEXNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVEXNode_EndOffset(), ecorePackage.getEInt(), "endOffset", null, 0, 1, VEXNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVEXNode_EndPosition(), this.getPosition(), null, "endPosition", null, 0, 1, VEXNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVEXNode_StartOffset(), ecorePackage.getEInt(), "startOffset", null, 0, 1, VEXNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVEXNode_StartPosition(), this.getPosition(), null, "startPosition", null, 0, 1, VEXNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVEXNode_Text(), ecorePackage.getEString(), "text", null, 0, 1, VEXNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVEXNode_NodeType(), ecorePackage.getEString(), "nodeType", null, 0, 1, VEXNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(vexProcessingInstructionEClass, VEXProcessingInstruction.class, "VEXProcessingInstruction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVEXProcessingInstruction_Attributes(), this.getVEXAttribute(), null, "attributes", null, 0, -1, VEXProcessingInstruction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(vexProcessingInstructionEClass, ecorePackage.getEBoolean(), "removeAttribute", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "attributeName", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(vexProcessingInstructionEClass, null, "setAttribute", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "attributeName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "value", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(validatorEClass, Validator.class, "Validator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getValidator_ValidRootElements(), this.getSet(), "validRootElements", null, 0, 1, Validator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(validatorEClass, this.getAttributeDefinition(), "getAttributeDefinition", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "attribute", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(validatorEClass, this.getAttributeDefinition(), "getAttributeDefinitions", 0, -1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(validatorEClass, null, "getValidItems", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);
		EGenericType g1 = createEGenericType(this.getSet());
		EGenericType g2 = createEGenericType(ecorePackage.getEString());
		g1.getETypeArguments().add(g2);
		initEOperation(op, g1);

		op = addEOperation(validatorEClass, ecorePackage.getEBoolean(), "isValidSequence", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "nodes", 0, -1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEBoolean(), "partial", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(validatorEClass, ecorePackage.getEBoolean(), "isValidSequence", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "seq1", 0, -1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "seq2", 0, -1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "seq3", 0, -1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEBoolean(), "partial", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(serializableEClass, Serializable.class, "Serializable", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);

		// Initialize data types
		initEDataType(documentValidationExceptionEDataType, DocumentValidationException.class, "DocumentValidationException", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(attributeDefinitionEDataType, AttributeDefinition.class, "AttributeDefinition", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(setEDataType, Set.class, "Set", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //DomEMFPackageImpl
