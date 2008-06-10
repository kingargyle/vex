/**
 * <copyright>
 * </copyright>
 *
 * $Id: AddTagMDPackageImpl.java,v 1.1 2008/06/10 17:24:01 cbateman Exp $
 */
package org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AddTagMDFactory;
import org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AddTagMDPackage;
import org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeData;
import org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeUsage;
import org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.ElementData;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AddTagMDPackageImpl extends EPackageImpl implements AddTagMDPackage
{
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass elementDataEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass attributeDataEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum attributeUsageEEnum = null;

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
     * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AddTagMDPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private AddTagMDPackageImpl()
    {
        super(eNS_URI, AddTagMDFactory.eINSTANCE);
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
     * @return the package
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static AddTagMDPackage init()
    {
        if (isInited) return (AddTagMDPackage)EPackage.Registry.INSTANCE.getEPackage(AddTagMDPackage.eNS_URI);

        // Obtain or create and register package
        AddTagMDPackageImpl theAddTagMDPackage = (AddTagMDPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof AddTagMDPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new AddTagMDPackageImpl());

        isInited = true;

        // Create package meta-data objects
        theAddTagMDPackage.createPackageContents();

        // Initialize created meta-data
        theAddTagMDPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theAddTagMDPackage.freeze();

        return theAddTagMDPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getElementData()
    {
        return elementDataEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getElementData_Name()
    {
        return (EAttribute)elementDataEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getElementData_Attributes()
    {
        return (EReference)elementDataEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAttributeData()
    {
        return attributeDataEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttributeData_Name()
    {
        return (EAttribute)attributeDataEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttributeData_Usage()
    {
        return (EAttribute)attributeDataEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAttributeData_Description()
    {
        return (EAttribute)attributeDataEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getAttributeUsage()
    {
        return attributeUsageEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AddTagMDFactory getAddTagMDFactory()
    {
        return (AddTagMDFactory)getEFactoryInstance();
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
    public void createPackageContents()
    {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        elementDataEClass = createEClass(ELEMENT_DATA);
        createEAttribute(elementDataEClass, ELEMENT_DATA__NAME);
        createEReference(elementDataEClass, ELEMENT_DATA__ATTRIBUTES);

        attributeDataEClass = createEClass(ATTRIBUTE_DATA);
        createEAttribute(attributeDataEClass, ATTRIBUTE_DATA__NAME);
        createEAttribute(attributeDataEClass, ATTRIBUTE_DATA__USAGE);
        createEAttribute(attributeDataEClass, ATTRIBUTE_DATA__DESCRIPTION);

        // Create enums
        attributeUsageEEnum = createEEnum(ATTRIBUTE_USAGE);
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
    public void initializePackageContents()
    {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes

        // Initialize classes and features; add operations and parameters
        initEClass(elementDataEClass, ElementData.class, "ElementData", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getElementData_Name(), ecorePackage.getEString(), "name", null, 0, 1, ElementData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getElementData_Attributes(), this.getAttributeData(), null, "attributes", null, 0, -1, ElementData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(attributeDataEClass, AttributeData.class, "AttributeData", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getAttributeData_Name(), ecorePackage.getEString(), "name", null, 0, 1, AttributeData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getAttributeData_Usage(), this.getAttributeUsage(), "usage", null, 0, 1, AttributeData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getAttributeData_Description(), ecorePackage.getEString(), "description", null, 0, 1, AttributeData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        // Initialize enums and add enum literals
        initEEnum(attributeUsageEEnum, AttributeUsage.class, "AttributeUsage"); //$NON-NLS-1$
        addEEnumLiteral(attributeUsageEEnum, AttributeUsage.OPTIONAL);
        addEEnumLiteral(attributeUsageEEnum, AttributeUsage.REQUIRED);
        addEEnumLiteral(attributeUsageEEnum, AttributeUsage.FIXED);
        addEEnumLiteral(attributeUsageEEnum, AttributeUsage.PROHIBITED);

        // Create resource
        createResource(eNS_URI);

        // Create annotations
        // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
        createExtendedMetaDataAnnotations();
    }

    /**
     * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void createExtendedMetaDataAnnotations()
    {
        String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData"; //$NON-NLS-1$		
        addAnnotation
          (getElementData_Name(), 
           source, 
           new String[] 
           {
             "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "name" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getElementData_Attributes(), 
           source, 
           new String[] 
           {
             "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "attribute" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getAttributeData_Name(), 
           source, 
           new String[] 
           {
             "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "name" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getAttributeData_Usage(), 
           source, 
           new String[] 
           {
             "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "usage" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getAttributeData_Description(), 
           source, 
           new String[] 
           {
             "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "description" //$NON-NLS-1$ //$NON-NLS-2$
           });
    }

} //AddTagMDPackageImpl
