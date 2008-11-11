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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFFactory
 * @model kind="package"
 * @generated
 */
public interface DomEMFPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "dom";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///org/eclipse/wst/xml/vex/core/internal/provisional/dom.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.wst.xml.vex.core.internal.provisional.dom";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DomEMFPackage eINSTANCE = org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.ContentImpl <em>Content</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.ContentImpl
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getContent()
	 * @generated
	 */
	int CONTENT = 0;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT__LENGTH = 0;

	/**
	 * The number of structural features of the '<em>Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.PositionImpl <em>Position</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.PositionImpl
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getPosition()
	 * @generated
	 */
	int POSITION = 1;

	/**
	 * The feature id for the '<em><b>Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION__OFFSET = 0;

	/**
	 * The number of structural features of the '<em>Position</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXNodeImpl <em>VEX Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXNodeImpl
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getVEXNode()
	 * @generated
	 */
	int VEX_NODE = 7;

	/**
	 * The feature id for the '<em><b>Content</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_NODE__CONTENT = 0;

	/**
	 * The feature id for the '<em><b>End Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_NODE__END_OFFSET = 1;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_NODE__END_POSITION = 2;

	/**
	 * The feature id for the '<em><b>Start Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_NODE__START_OFFSET = 3;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_NODE__START_POSITION = 4;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_NODE__TEXT = 5;

	/**
	 * The feature id for the '<em><b>Node Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_NODE__NODE_TYPE = 6;

	/**
	 * The number of structural features of the '<em>VEX Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_NODE_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXAttributeImpl <em>VEX Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXAttributeImpl
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getVEXAttribute()
	 * @generated
	 */
	int VEX_ATTRIBUTE = 2;

	/**
	 * The feature id for the '<em><b>Content</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ATTRIBUTE__CONTENT = VEX_NODE__CONTENT;

	/**
	 * The feature id for the '<em><b>End Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ATTRIBUTE__END_OFFSET = VEX_NODE__END_OFFSET;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ATTRIBUTE__END_POSITION = VEX_NODE__END_POSITION;

	/**
	 * The feature id for the '<em><b>Start Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ATTRIBUTE__START_OFFSET = VEX_NODE__START_OFFSET;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ATTRIBUTE__START_POSITION = VEX_NODE__START_POSITION;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ATTRIBUTE__TEXT = VEX_NODE__TEXT;

	/**
	 * The feature id for the '<em><b>Node Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ATTRIBUTE__NODE_TYPE = VEX_NODE__NODE_TYPE;

	/**
	 * The feature id for the '<em><b>Document</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ATTRIBUTE__DOCUMENT = VEX_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ATTRIBUTE__VALUE = VEX_NODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ATTRIBUTE__PARENT = VEX_NODE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Local Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ATTRIBUTE__LOCAL_NAME = VEX_NODE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Namespace Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ATTRIBUTE__NAMESPACE_PREFIX = VEX_NODE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Namespace URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ATTRIBUTE__NAMESPACE_URI = VEX_NODE_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>VEX Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ATTRIBUTE_FEATURE_COUNT = VEX_NODE_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXCommentImpl <em>VEX Comment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXCommentImpl
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getVEXComment()
	 * @generated
	 */
	int VEX_COMMENT = 3;

	/**
	 * The feature id for the '<em><b>Content</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_COMMENT__CONTENT = VEX_NODE__CONTENT;

	/**
	 * The feature id for the '<em><b>End Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_COMMENT__END_OFFSET = VEX_NODE__END_OFFSET;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_COMMENT__END_POSITION = VEX_NODE__END_POSITION;

	/**
	 * The feature id for the '<em><b>Start Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_COMMENT__START_OFFSET = VEX_NODE__START_OFFSET;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_COMMENT__START_POSITION = VEX_NODE__START_POSITION;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_COMMENT__TEXT = VEX_NODE__TEXT;

	/**
	 * The feature id for the '<em><b>Node Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_COMMENT__NODE_TYPE = VEX_NODE__NODE_TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_COMMENT__VALUE = VEX_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>VEX Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_COMMENT_FEATURE_COUNT = VEX_NODE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXDocumentImpl <em>VEX Document</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXDocumentImpl
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getVEXDocument()
	 * @generated
	 */
	int VEX_DOCUMENT = 4;

	/**
	 * The feature id for the '<em><b>Encoding</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_DOCUMENT__ENCODING = 0;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_DOCUMENT__LENGTH = 1;

	/**
	 * The feature id for the '<em><b>Root Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_DOCUMENT__ROOT_ELEMENT = 2;

	/**
	 * The feature id for the '<em><b>Validator</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_DOCUMENT__VALIDATOR = 3;

	/**
	 * The feature id for the '<em><b>Public ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_DOCUMENT__PUBLIC_ID = 4;

	/**
	 * The number of structural features of the '<em>VEX Document</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_DOCUMENT_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXDocumentFragmentImpl <em>VEX Document Fragment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXDocumentFragmentImpl
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getVEXDocumentFragment()
	 * @generated
	 */
	int VEX_DOCUMENT_FRAGMENT = 5;

	/**
	 * The feature id for the '<em><b>Content</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_DOCUMENT_FRAGMENT__CONTENT = 0;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_DOCUMENT_FRAGMENT__LENGTH = 1;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_DOCUMENT_FRAGMENT__ELEMENTS = 2;

	/**
	 * The feature id for the '<em><b>Node Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_DOCUMENT_FRAGMENT__NODE_NAMES = 3;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_DOCUMENT_FRAGMENT__NODES = 4;

	/**
	 * The number of structural features of the '<em>VEX Document Fragment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_DOCUMENT_FRAGMENT_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXElementImpl <em>VEX Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXElementImpl
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getVEXElement()
	 * @generated
	 */
	int VEX_ELEMENT = 6;

	/**
	 * The feature id for the '<em><b>Content</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ELEMENT__CONTENT = VEX_NODE__CONTENT;

	/**
	 * The feature id for the '<em><b>End Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ELEMENT__END_OFFSET = VEX_NODE__END_OFFSET;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ELEMENT__END_POSITION = VEX_NODE__END_POSITION;

	/**
	 * The feature id for the '<em><b>Start Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ELEMENT__START_OFFSET = VEX_NODE__START_OFFSET;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ELEMENT__START_POSITION = VEX_NODE__START_POSITION;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ELEMENT__TEXT = VEX_NODE__TEXT;

	/**
	 * The feature id for the '<em><b>Node Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ELEMENT__NODE_TYPE = VEX_NODE__NODE_TYPE;

	/**
	 * The feature id for the '<em><b>Attribute Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ELEMENT__ATTRIBUTE_NAMES = VEX_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Child Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ELEMENT__CHILD_ELEMENTS = VEX_NODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Child Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ELEMENT__CHILD_NODES = VEX_NODE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Document</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ELEMENT__DOCUMENT = VEX_NODE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ELEMENT__NAME = VEX_NODE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Empty</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ELEMENT__EMPTY = VEX_NODE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ELEMENT__PARENT = VEX_NODE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Namespace Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ELEMENT__NAMESPACE_PREFIX = VEX_NODE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Namespace URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ELEMENT__NAMESPACE_URI = VEX_NODE_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>VEX Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_ELEMENT_FEATURE_COUNT = VEX_NODE_FEATURE_COUNT + 9;

	/**
	 * The meta object id for the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXProcessingInstructionImpl <em>VEX Processing Instruction</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXProcessingInstructionImpl
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getVEXProcessingInstruction()
	 * @generated
	 */
	int VEX_PROCESSING_INSTRUCTION = 8;

	/**
	 * The feature id for the '<em><b>Content</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_PROCESSING_INSTRUCTION__CONTENT = VEX_NODE__CONTENT;

	/**
	 * The feature id for the '<em><b>End Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_PROCESSING_INSTRUCTION__END_OFFSET = VEX_NODE__END_OFFSET;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_PROCESSING_INSTRUCTION__END_POSITION = VEX_NODE__END_POSITION;

	/**
	 * The feature id for the '<em><b>Start Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_PROCESSING_INSTRUCTION__START_OFFSET = VEX_NODE__START_OFFSET;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_PROCESSING_INSTRUCTION__START_POSITION = VEX_NODE__START_POSITION;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_PROCESSING_INSTRUCTION__TEXT = VEX_NODE__TEXT;

	/**
	 * The feature id for the '<em><b>Node Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_PROCESSING_INSTRUCTION__NODE_TYPE = VEX_NODE__NODE_TYPE;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_PROCESSING_INSTRUCTION__ATTRIBUTES = VEX_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>VEX Processing Instruction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VEX_PROCESSING_INSTRUCTION_FEATURE_COUNT = VEX_NODE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link java.io.Serializable <em>Serializable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.io.Serializable
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getSerializable()
	 * @generated
	 */
	int SERIALIZABLE = 10;

	/**
	 * The number of structural features of the '<em>Serializable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERIALIZABLE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.ValidatorImpl <em>Validator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.ValidatorImpl
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getValidator()
	 * @generated
	 */
	int VALIDATOR = 9;

	/**
	 * The feature id for the '<em><b>Valid Root Elements</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATOR__VALID_ROOT_ELEMENTS = SERIALIZABLE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Validator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALIDATOR_FEATURE_COUNT = SERIALIZABLE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '<em>Document Validation Exception</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.DocumentValidationException
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getDocumentValidationException()
	 * @generated
	 */
	int DOCUMENT_VALIDATION_EXCEPTION = 11;

	/**
	 * The meta object id for the '<em>Attribute Definition</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.wst.xml.vex.core.internal.validator.AttributeDefinition
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getAttributeDefinition()
	 * @generated
	 */
	int ATTRIBUTE_DEFINITION = 12;

	/**
	 * The meta object id for the '<em>Set</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.Set
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getSet()
	 * @generated
	 */
	int SET = 13;


	/**
	 * Returns the meta object for class '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Content <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Content</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Content
	 * @generated
	 */
	EClass getContent();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Content#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Content#getLength()
	 * @see #getContent()
	 * @generated
	 */
	EAttribute getContent_Length();

	/**
	 * Returns the meta object for class '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Position <em>Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Position</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Position
	 * @generated
	 */
	EClass getPosition();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Position#getOffset <em>Offset</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Offset</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Position#getOffset()
	 * @see #getPosition()
	 * @generated
	 */
	EAttribute getPosition_Offset();

	/**
	 * Returns the meta object for class '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXAttribute <em>VEX Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>VEX Attribute</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXAttribute
	 * @generated
	 */
	EClass getVEXAttribute();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXAttribute#getDocument <em>Document</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Document</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXAttribute#getDocument()
	 * @see #getVEXAttribute()
	 * @generated
	 */
	EReference getVEXAttribute_Document();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXAttribute#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXAttribute#getValue()
	 * @see #getVEXAttribute()
	 * @generated
	 */
	EAttribute getVEXAttribute_Value();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXAttribute#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXAttribute#getParent()
	 * @see #getVEXAttribute()
	 * @generated
	 */
	EReference getVEXAttribute_Parent();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXAttribute#getLocalName <em>Local Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Local Name</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXAttribute#getLocalName()
	 * @see #getVEXAttribute()
	 * @generated
	 */
	EAttribute getVEXAttribute_LocalName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXAttribute#getNamespacePrefix <em>Namespace Prefix</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Namespace Prefix</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXAttribute#getNamespacePrefix()
	 * @see #getVEXAttribute()
	 * @generated
	 */
	EAttribute getVEXAttribute_NamespacePrefix();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXAttribute#getNamespaceURI <em>Namespace URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Namespace URI</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXAttribute#getNamespaceURI()
	 * @see #getVEXAttribute()
	 * @generated
	 */
	EAttribute getVEXAttribute_NamespaceURI();

	/**
	 * Returns the meta object for class '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXComment <em>VEX Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>VEX Comment</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXComment
	 * @generated
	 */
	EClass getVEXComment();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXComment#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXComment#getValue()
	 * @see #getVEXComment()
	 * @generated
	 */
	EAttribute getVEXComment_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument <em>VEX Document</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>VEX Document</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument
	 * @generated
	 */
	EClass getVEXDocument();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getEncoding <em>Encoding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Encoding</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getEncoding()
	 * @see #getVEXDocument()
	 * @generated
	 */
	EAttribute getVEXDocument_Encoding();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getLength()
	 * @see #getVEXDocument()
	 * @generated
	 */
	EAttribute getVEXDocument_Length();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getRootElement <em>Root Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Root Element</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getRootElement()
	 * @see #getVEXDocument()
	 * @generated
	 */
	EReference getVEXDocument_RootElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getValidator <em>Validator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Validator</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getValidator()
	 * @see #getVEXDocument()
	 * @generated
	 */
	EReference getVEXDocument_Validator();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getPublicID <em>Public ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Public ID</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument#getPublicID()
	 * @see #getVEXDocument()
	 * @generated
	 */
	EAttribute getVEXDocument_PublicID();

	/**
	 * Returns the meta object for class '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocumentFragment <em>VEX Document Fragment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>VEX Document Fragment</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocumentFragment
	 * @generated
	 */
	EClass getVEXDocumentFragment();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocumentFragment#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Content</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocumentFragment#getContent()
	 * @see #getVEXDocumentFragment()
	 * @generated
	 */
	EReference getVEXDocumentFragment_Content();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocumentFragment#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocumentFragment#getLength()
	 * @see #getVEXDocumentFragment()
	 * @generated
	 */
	EAttribute getVEXDocumentFragment_Length();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocumentFragment#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Elements</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocumentFragment#getElements()
	 * @see #getVEXDocumentFragment()
	 * @generated
	 */
	EReference getVEXDocumentFragment_Elements();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocumentFragment#getNodeNames <em>Node Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Node Names</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocumentFragment#getNodeNames()
	 * @see #getVEXDocumentFragment()
	 * @generated
	 */
	EAttribute getVEXDocumentFragment_NodeNames();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocumentFragment#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Nodes</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocumentFragment#getNodes()
	 * @see #getVEXDocumentFragment()
	 * @generated
	 */
	EReference getVEXDocumentFragment_Nodes();

	/**
	 * Returns the meta object for class '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement <em>VEX Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>VEX Element</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement
	 * @generated
	 */
	EClass getVEXElement();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement#getAttributeNames <em>Attribute Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Attribute Names</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement#getAttributeNames()
	 * @see #getVEXElement()
	 * @generated
	 */
	EAttribute getVEXElement_AttributeNames();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement#getChildElements <em>Child Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Child Elements</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement#getChildElements()
	 * @see #getVEXElement()
	 * @generated
	 */
	EReference getVEXElement_ChildElements();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement#getChildNodes <em>Child Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Child Nodes</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement#getChildNodes()
	 * @see #getVEXElement()
	 * @generated
	 */
	EReference getVEXElement_ChildNodes();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement#getDocument <em>Document</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Document</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement#getDocument()
	 * @see #getVEXElement()
	 * @generated
	 */
	EReference getVEXElement_Document();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement#getName()
	 * @see #getVEXElement()
	 * @generated
	 */
	EAttribute getVEXElement_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement#isEmpty <em>Empty</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Empty</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement#isEmpty()
	 * @see #getVEXElement()
	 * @generated
	 */
	EAttribute getVEXElement_Empty();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement#getParent()
	 * @see #getVEXElement()
	 * @generated
	 */
	EReference getVEXElement_Parent();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement#getNamespacePrefix <em>Namespace Prefix</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Namespace Prefix</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement#getNamespacePrefix()
	 * @see #getVEXElement()
	 * @generated
	 */
	EAttribute getVEXElement_NamespacePrefix();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement#getNamespaceURI <em>Namespace URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Namespace URI</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement#getNamespaceURI()
	 * @see #getVEXElement()
	 * @generated
	 */
	EAttribute getVEXElement_NamespaceURI();

	/**
	 * Returns the meta object for class '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode <em>VEX Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>VEX Node</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode
	 * @generated
	 */
	EClass getVEXNode();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Content</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode#getContent()
	 * @see #getVEXNode()
	 * @generated
	 */
	EReference getVEXNode_Content();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode#getEndOffset <em>End Offset</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End Offset</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode#getEndOffset()
	 * @see #getVEXNode()
	 * @generated
	 */
	EAttribute getVEXNode_EndOffset();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode#getEndPosition <em>End Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>End Position</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode#getEndPosition()
	 * @see #getVEXNode()
	 * @generated
	 */
	EReference getVEXNode_EndPosition();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode#getStartOffset <em>Start Offset</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start Offset</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode#getStartOffset()
	 * @see #getVEXNode()
	 * @generated
	 */
	EAttribute getVEXNode_StartOffset();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode#getStartPosition <em>Start Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Start Position</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode#getStartPosition()
	 * @see #getVEXNode()
	 * @generated
	 */
	EReference getVEXNode_StartPosition();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode#getText()
	 * @see #getVEXNode()
	 * @generated
	 */
	EAttribute getVEXNode_Text();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode#getNodeType <em>Node Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Node Type</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode#getNodeType()
	 * @see #getVEXNode()
	 * @generated
	 */
	EAttribute getVEXNode_NodeType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXProcessingInstruction <em>VEX Processing Instruction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>VEX Processing Instruction</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXProcessingInstruction
	 * @generated
	 */
	EClass getVEXProcessingInstruction();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXProcessingInstruction#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Attributes</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXProcessingInstruction#getAttributes()
	 * @see #getVEXProcessingInstruction()
	 * @generated
	 */
	EReference getVEXProcessingInstruction_Attributes();

	/**
	 * Returns the meta object for class '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Validator <em>Validator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Validator</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Validator
	 * @generated
	 */
	EClass getValidator();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Validator#getValidRootElements <em>Valid Root Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Valid Root Elements</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Validator#getValidRootElements()
	 * @see #getValidator()
	 * @generated
	 */
	EAttribute getValidator_ValidRootElements();

	/**
	 * Returns the meta object for class '{@link java.io.Serializable <em>Serializable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Serializable</em>'.
	 * @see java.io.Serializable
	 * @model instanceClass="java.io.Serializable"
	 * @generated
	 */
	EClass getSerializable();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.wst.xml.vex.core.internal.dom.DocumentValidationException <em>Document Validation Exception</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Document Validation Exception</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.DocumentValidationException
	 * @model instanceClass="org.eclipse.wst.xml.vex.core.internal.dom.DocumentValidationException"
	 * @generated
	 */
	EDataType getDocumentValidationException();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.wst.xml.vex.core.internal.validator.AttributeDefinition <em>Attribute Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Attribute Definition</em>'.
	 * @see org.eclipse.wst.xml.vex.core.internal.validator.AttributeDefinition
	 * @model instanceClass="org.eclipse.wst.xml.vex.core.internal.validator.AttributeDefinition"
	 * @generated
	 */
	EDataType getAttributeDefinition();

	/**
	 * Returns the meta object for data type '{@link java.util.Set <em>Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Set</em>'.
	 * @see java.util.Set
	 * @model instanceClass="java.util.Set" typeParameters="T"
	 * @generated
	 */
	EDataType getSet();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DomEMFFactory getDomEMFFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.ContentImpl <em>Content</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.ContentImpl
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getContent()
		 * @generated
		 */
		EClass CONTENT = eINSTANCE.getContent();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTENT__LENGTH = eINSTANCE.getContent_Length();

		/**
		 * The meta object literal for the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.PositionImpl <em>Position</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.PositionImpl
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getPosition()
		 * @generated
		 */
		EClass POSITION = eINSTANCE.getPosition();

		/**
		 * The meta object literal for the '<em><b>Offset</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POSITION__OFFSET = eINSTANCE.getPosition_Offset();

		/**
		 * The meta object literal for the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXAttributeImpl <em>VEX Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXAttributeImpl
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getVEXAttribute()
		 * @generated
		 */
		EClass VEX_ATTRIBUTE = eINSTANCE.getVEXAttribute();

		/**
		 * The meta object literal for the '<em><b>Document</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VEX_ATTRIBUTE__DOCUMENT = eINSTANCE.getVEXAttribute_Document();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VEX_ATTRIBUTE__VALUE = eINSTANCE.getVEXAttribute_Value();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VEX_ATTRIBUTE__PARENT = eINSTANCE.getVEXAttribute_Parent();

		/**
		 * The meta object literal for the '<em><b>Local Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VEX_ATTRIBUTE__LOCAL_NAME = eINSTANCE.getVEXAttribute_LocalName();

		/**
		 * The meta object literal for the '<em><b>Namespace Prefix</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VEX_ATTRIBUTE__NAMESPACE_PREFIX = eINSTANCE.getVEXAttribute_NamespacePrefix();

		/**
		 * The meta object literal for the '<em><b>Namespace URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VEX_ATTRIBUTE__NAMESPACE_URI = eINSTANCE.getVEXAttribute_NamespaceURI();

		/**
		 * The meta object literal for the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXCommentImpl <em>VEX Comment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXCommentImpl
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getVEXComment()
		 * @generated
		 */
		EClass VEX_COMMENT = eINSTANCE.getVEXComment();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VEX_COMMENT__VALUE = eINSTANCE.getVEXComment_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXDocumentImpl <em>VEX Document</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXDocumentImpl
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getVEXDocument()
		 * @generated
		 */
		EClass VEX_DOCUMENT = eINSTANCE.getVEXDocument();

		/**
		 * The meta object literal for the '<em><b>Encoding</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VEX_DOCUMENT__ENCODING = eINSTANCE.getVEXDocument_Encoding();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VEX_DOCUMENT__LENGTH = eINSTANCE.getVEXDocument_Length();

		/**
		 * The meta object literal for the '<em><b>Root Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VEX_DOCUMENT__ROOT_ELEMENT = eINSTANCE.getVEXDocument_RootElement();

		/**
		 * The meta object literal for the '<em><b>Validator</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VEX_DOCUMENT__VALIDATOR = eINSTANCE.getVEXDocument_Validator();

		/**
		 * The meta object literal for the '<em><b>Public ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VEX_DOCUMENT__PUBLIC_ID = eINSTANCE.getVEXDocument_PublicID();

		/**
		 * The meta object literal for the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXDocumentFragmentImpl <em>VEX Document Fragment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXDocumentFragmentImpl
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getVEXDocumentFragment()
		 * @generated
		 */
		EClass VEX_DOCUMENT_FRAGMENT = eINSTANCE.getVEXDocumentFragment();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VEX_DOCUMENT_FRAGMENT__CONTENT = eINSTANCE.getVEXDocumentFragment_Content();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VEX_DOCUMENT_FRAGMENT__LENGTH = eINSTANCE.getVEXDocumentFragment_Length();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VEX_DOCUMENT_FRAGMENT__ELEMENTS = eINSTANCE.getVEXDocumentFragment_Elements();

		/**
		 * The meta object literal for the '<em><b>Node Names</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VEX_DOCUMENT_FRAGMENT__NODE_NAMES = eINSTANCE.getVEXDocumentFragment_NodeNames();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VEX_DOCUMENT_FRAGMENT__NODES = eINSTANCE.getVEXDocumentFragment_Nodes();

		/**
		 * The meta object literal for the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXElementImpl <em>VEX Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXElementImpl
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getVEXElement()
		 * @generated
		 */
		EClass VEX_ELEMENT = eINSTANCE.getVEXElement();

		/**
		 * The meta object literal for the '<em><b>Attribute Names</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VEX_ELEMENT__ATTRIBUTE_NAMES = eINSTANCE.getVEXElement_AttributeNames();

		/**
		 * The meta object literal for the '<em><b>Child Elements</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VEX_ELEMENT__CHILD_ELEMENTS = eINSTANCE.getVEXElement_ChildElements();

		/**
		 * The meta object literal for the '<em><b>Child Nodes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VEX_ELEMENT__CHILD_NODES = eINSTANCE.getVEXElement_ChildNodes();

		/**
		 * The meta object literal for the '<em><b>Document</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VEX_ELEMENT__DOCUMENT = eINSTANCE.getVEXElement_Document();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VEX_ELEMENT__NAME = eINSTANCE.getVEXElement_Name();

		/**
		 * The meta object literal for the '<em><b>Empty</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VEX_ELEMENT__EMPTY = eINSTANCE.getVEXElement_Empty();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VEX_ELEMENT__PARENT = eINSTANCE.getVEXElement_Parent();

		/**
		 * The meta object literal for the '<em><b>Namespace Prefix</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VEX_ELEMENT__NAMESPACE_PREFIX = eINSTANCE.getVEXElement_NamespacePrefix();

		/**
		 * The meta object literal for the '<em><b>Namespace URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VEX_ELEMENT__NAMESPACE_URI = eINSTANCE.getVEXElement_NamespaceURI();

		/**
		 * The meta object literal for the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXNodeImpl <em>VEX Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXNodeImpl
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getVEXNode()
		 * @generated
		 */
		EClass VEX_NODE = eINSTANCE.getVEXNode();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VEX_NODE__CONTENT = eINSTANCE.getVEXNode_Content();

		/**
		 * The meta object literal for the '<em><b>End Offset</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VEX_NODE__END_OFFSET = eINSTANCE.getVEXNode_EndOffset();

		/**
		 * The meta object literal for the '<em><b>End Position</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VEX_NODE__END_POSITION = eINSTANCE.getVEXNode_EndPosition();

		/**
		 * The meta object literal for the '<em><b>Start Offset</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VEX_NODE__START_OFFSET = eINSTANCE.getVEXNode_StartOffset();

		/**
		 * The meta object literal for the '<em><b>Start Position</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VEX_NODE__START_POSITION = eINSTANCE.getVEXNode_StartPosition();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VEX_NODE__TEXT = eINSTANCE.getVEXNode_Text();

		/**
		 * The meta object literal for the '<em><b>Node Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VEX_NODE__NODE_TYPE = eINSTANCE.getVEXNode_NodeType();

		/**
		 * The meta object literal for the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXProcessingInstructionImpl <em>VEX Processing Instruction</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXProcessingInstructionImpl
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getVEXProcessingInstruction()
		 * @generated
		 */
		EClass VEX_PROCESSING_INSTRUCTION = eINSTANCE.getVEXProcessingInstruction();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VEX_PROCESSING_INSTRUCTION__ATTRIBUTES = eINSTANCE.getVEXProcessingInstruction_Attributes();

		/**
		 * The meta object literal for the '{@link org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.ValidatorImpl <em>Validator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.ValidatorImpl
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getValidator()
		 * @generated
		 */
		EClass VALIDATOR = eINSTANCE.getValidator();

		/**
		 * The meta object literal for the '<em><b>Valid Root Elements</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALIDATOR__VALID_ROOT_ELEMENTS = eINSTANCE.getValidator_ValidRootElements();

		/**
		 * The meta object literal for the '{@link java.io.Serializable <em>Serializable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.io.Serializable
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getSerializable()
		 * @generated
		 */
		EClass SERIALIZABLE = eINSTANCE.getSerializable();

		/**
		 * The meta object literal for the '<em>Document Validation Exception</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.wst.xml.vex.core.internal.dom.DocumentValidationException
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getDocumentValidationException()
		 * @generated
		 */
		EDataType DOCUMENT_VALIDATION_EXCEPTION = eINSTANCE.getDocumentValidationException();

		/**
		 * The meta object literal for the '<em>Attribute Definition</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.wst.xml.vex.core.internal.validator.AttributeDefinition
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getAttributeDefinition()
		 * @generated
		 */
		EDataType ATTRIBUTE_DEFINITION = eINSTANCE.getAttributeDefinition();

		/**
		 * The meta object literal for the '<em>Set</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.Set
		 * @see org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.DomEMFPackageImpl#getSet()
		 * @generated
		 */
		EDataType SET = eINSTANCE.getSet();

	}

} //DomEMFPackage
