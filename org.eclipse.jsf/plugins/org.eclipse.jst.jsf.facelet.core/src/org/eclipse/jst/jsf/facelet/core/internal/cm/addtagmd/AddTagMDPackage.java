/**
 * <copyright>
 * </copyright>
 *
 * $Id: AddTagMDPackage.java,v 1.1 2008/06/10 17:24:00 cbateman Exp $
 */
package org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AddTagMDFactory
 * @model kind="package"
 * @generated
 */
public interface AddTagMDPackage extends EPackage
{
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "addtagmd"; //$NON-NLS-1$

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://org.eclipse.jst.jsf.facelet.core/additionalTagMetadata.ecore"; //$NON-NLS-1$

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "addTagMD"; //$NON-NLS-1$

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    AddTagMDPackage eINSTANCE = org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.impl.AddTagMDPackageImpl.init();

    /**
     * The meta object id for the '{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.impl.ElementDataImpl <em>Element Data</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.impl.ElementDataImpl
     * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.impl.AddTagMDPackageImpl#getElementData()
     * @generated
     */
    int ELEMENT_DATA = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ELEMENT_DATA__NAME = 0;

    /**
     * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ELEMENT_DATA__ATTRIBUTES = 1;

    /**
     * The number of structural features of the '<em>Element Data</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ELEMENT_DATA_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.impl.AttributeDataImpl <em>Attribute Data</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.impl.AttributeDataImpl
     * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.impl.AddTagMDPackageImpl#getAttributeData()
     * @generated
     */
    int ATTRIBUTE_DATA = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_DATA__NAME = 0;

    /**
     * The feature id for the '<em><b>Usage</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_DATA__USAGE = 1;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_DATA__DESCRIPTION = 2;

    /**
     * The number of structural features of the '<em>Attribute Data</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_DATA_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeUsage <em>Attribute Usage</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeUsage
     * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.impl.AddTagMDPackageImpl#getAttributeUsage()
     * @generated
     */
    int ATTRIBUTE_USAGE = 2;


    /**
     * Returns the meta object for class '{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.ElementData <em>Element Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Element Data</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.ElementData
     * @generated
     */
    EClass getElementData();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.ElementData#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.ElementData#getName()
     * @see #getElementData()
     * @generated
     */
    EAttribute getElementData_Name();

    /**
     * Returns the meta object for the containment reference list '{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.ElementData#getAttributes <em>Attributes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Attributes</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.ElementData#getAttributes()
     * @see #getElementData()
     * @generated
     */
    EReference getElementData_Attributes();

    /**
     * Returns the meta object for class '{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeData <em>Attribute Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Attribute Data</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeData
     * @generated
     */
    EClass getAttributeData();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeData#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeData#getName()
     * @see #getAttributeData()
     * @generated
     */
    EAttribute getAttributeData_Name();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeData#getUsage <em>Usage</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Usage</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeData#getUsage()
     * @see #getAttributeData()
     * @generated
     */
    EAttribute getAttributeData_Usage();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeData#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeData#getDescription()
     * @see #getAttributeData()
     * @generated
     */
    EAttribute getAttributeData_Description();

    /**
     * Returns the meta object for enum '{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeUsage <em>Attribute Usage</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Attribute Usage</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeUsage
     * @generated
     */
    EEnum getAttributeUsage();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    AddTagMDFactory getAddTagMDFactory();

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
    @SuppressWarnings("hiding")
    interface Literals
    {
        /**
         * The meta object literal for the '{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.impl.ElementDataImpl <em>Element Data</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.impl.ElementDataImpl
         * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.impl.AddTagMDPackageImpl#getElementData()
         * @generated
         */
        EClass ELEMENT_DATA = eINSTANCE.getElementData();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ELEMENT_DATA__NAME = eINSTANCE.getElementData_Name();

        /**
         * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ELEMENT_DATA__ATTRIBUTES = eINSTANCE.getElementData_Attributes();

        /**
         * The meta object literal for the '{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.impl.AttributeDataImpl <em>Attribute Data</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.impl.AttributeDataImpl
         * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.impl.AddTagMDPackageImpl#getAttributeData()
         * @generated
         */
        EClass ATTRIBUTE_DATA = eINSTANCE.getAttributeData();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE_DATA__NAME = eINSTANCE.getAttributeData_Name();

        /**
         * The meta object literal for the '<em><b>Usage</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE_DATA__USAGE = eINSTANCE.getAttributeData_Usage();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE_DATA__DESCRIPTION = eINSTANCE.getAttributeData_Description();

        /**
         * The meta object literal for the '{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeUsage <em>Attribute Usage</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeUsage
         * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.impl.AddTagMDPackageImpl#getAttributeUsage()
         * @generated
         */
        EEnum ATTRIBUTE_USAGE = eINSTANCE.getAttributeUsage();

    }

} //AddTagMDPackage
