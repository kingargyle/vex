/**
 * <copyright>
 * </copyright>
 *
 * $Id: ConverterTagDefnImpl.java,v 1.1 2008/06/16 07:47:53 cbateman Exp $
 */
package org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ConverterTagDefn;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Converter Tag Defn</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.ConverterTagDefnImpl#getConverterId <em>Converter Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConverterTagDefnImpl extends HandlerTagDefnImpl implements ConverterTagDefn
{
    /**
     * The default value of the '{@link #getConverterId() <em>Converter Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getConverterId()
     * @generated
     * @ordered
     */
    protected static final String CONVERTER_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getConverterId() <em>Converter Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getConverterId()
     * @generated
     * @ordered
     */
    protected String converterId = CONVERTER_ID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ConverterTagDefnImpl()
    {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass()
    {
        return FaceletTaglibPackage.Literals.CONVERTER_TAG_DEFN;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getConverterId()
    {
        return converterId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setConverterId(String newConverterId)
    {
        String oldConverterId = converterId;
        converterId = newConverterId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, FaceletTaglibPackage.CONVERTER_TAG_DEFN__CONVERTER_ID, oldConverterId, converterId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType)
    {
        switch (featureID)
        {
            case FaceletTaglibPackage.CONVERTER_TAG_DEFN__CONVERTER_ID:
                return getConverterId();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue)
    {
        switch (featureID)
        {
            case FaceletTaglibPackage.CONVERTER_TAG_DEFN__CONVERTER_ID:
                setConverterId((String)newValue);
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
    public void eUnset(int featureID)
    {
        switch (featureID)
        {
            case FaceletTaglibPackage.CONVERTER_TAG_DEFN__CONVERTER_ID:
                setConverterId(CONVERTER_ID_EDEFAULT);
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
    public boolean eIsSet(int featureID)
    {
        switch (featureID)
        {
            case FaceletTaglibPackage.CONVERTER_TAG_DEFN__CONVERTER_ID:
                return CONVERTER_ID_EDEFAULT == null ? converterId != null : !CONVERTER_ID_EDEFAULT.equals(converterId);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString()
    {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (converterId: "); //$NON-NLS-1$
        result.append(converterId);
        result.append(')');
        return result.toString();
    }

} //ConverterTagDefnImpl
