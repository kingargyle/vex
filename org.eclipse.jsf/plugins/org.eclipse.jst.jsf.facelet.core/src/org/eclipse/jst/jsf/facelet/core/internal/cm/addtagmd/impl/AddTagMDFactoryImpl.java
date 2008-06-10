/**
 * <copyright>
 * </copyright>
 *
 * $Id: AddTagMDFactoryImpl.java,v 1.1 2008/06/10 17:24:02 cbateman Exp $
 */
package org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AddTagMDFactoryImpl extends EFactoryImpl implements AddTagMDFactory
{
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * @return the factory
     * <!-- end-user-doc -->
     * @generated
     */
    public static AddTagMDFactory init()
    {
        try
        {
            AddTagMDFactory theAddTagMDFactory = (AddTagMDFactory)EPackage.Registry.INSTANCE.getEFactory("http://org.eclipse.jst.jsf.facelet.core/additionalTagMetadata.ecore"); //$NON-NLS-1$ 
            if (theAddTagMDFactory != null)
            {
                return theAddTagMDFactory;
            }
        }
        catch (Exception exception)
        {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new AddTagMDFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AddTagMDFactoryImpl()
    {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass)
    {
        switch (eClass.getClassifierID())
        {
            case AddTagMDPackage.ELEMENT_DATA: return createElementData();
            case AddTagMDPackage.ATTRIBUTE_DATA: return createAttributeData();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue)
    {
        switch (eDataType.getClassifierID())
        {
            case AddTagMDPackage.ATTRIBUTE_USAGE:
                return createAttributeUsageFromString(eDataType, initialValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue)
    {
        switch (eDataType.getClassifierID())
        {
            case AddTagMDPackage.ATTRIBUTE_USAGE:
                return convertAttributeUsageToString(eDataType, instanceValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ElementData createElementData()
    {
        ElementDataImpl elementData = new ElementDataImpl();
        return elementData;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AttributeData createAttributeData()
    {
        AttributeDataImpl attributeData = new AttributeDataImpl();
        return attributeData;
    }

    /**
     * <!-- begin-user-doc -->
     * @param eDataType 
     * @param initialValue 
     * @return the attribute usage
     * <!-- end-user-doc -->
     * @generated
     */
    public AttributeUsage createAttributeUsageFromString(EDataType eDataType, String initialValue)
    {
        AttributeUsage result = AttributeUsage.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * @param eDataType 
     * @param instanceValue 
     * @return the usage string
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertAttributeUsageToString(EDataType eDataType, Object instanceValue)
    {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AddTagMDPackage getAddTagMDPackage()
    {
        return (AddTagMDPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * @return the package
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static AddTagMDPackage getPackage()
    {
        return AddTagMDPackage.eINSTANCE;
    }

} //AddTagMDFactoryImpl
