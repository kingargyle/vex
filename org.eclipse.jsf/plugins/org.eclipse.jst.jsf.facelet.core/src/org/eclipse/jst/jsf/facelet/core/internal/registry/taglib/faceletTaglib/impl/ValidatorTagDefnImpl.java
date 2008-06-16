/**
 * <copyright>
 * </copyright>
 *
 * $Id: ValidatorTagDefnImpl.java,v 1.1 2008/06/16 07:47:53 cbateman Exp $
 */
package org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibPackage;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ValidatorTagDefn;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Validator Tag Defn</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.ValidatorTagDefnImpl#getValidatorId <em>Validator Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ValidatorTagDefnImpl extends HandlerTagDefnImpl implements ValidatorTagDefn
{
    /**
     * The default value of the '{@link #getValidatorId() <em>Validator Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getValidatorId()
     * @generated
     * @ordered
     */
    protected static final String VALIDATOR_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getValidatorId() <em>Validator Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getValidatorId()
     * @generated
     * @ordered
     */
    protected String validatorId = VALIDATOR_ID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ValidatorTagDefnImpl()
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
        return FaceletTaglibPackage.Literals.VALIDATOR_TAG_DEFN;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getValidatorId()
    {
        return validatorId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setValidatorId(String newValidatorId)
    {
        String oldValidatorId = validatorId;
        validatorId = newValidatorId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, FaceletTaglibPackage.VALIDATOR_TAG_DEFN__VALIDATOR_ID, oldValidatorId, validatorId));
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
            case FaceletTaglibPackage.VALIDATOR_TAG_DEFN__VALIDATOR_ID:
                return getValidatorId();
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
            case FaceletTaglibPackage.VALIDATOR_TAG_DEFN__VALIDATOR_ID:
                setValidatorId((String)newValue);
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
            case FaceletTaglibPackage.VALIDATOR_TAG_DEFN__VALIDATOR_ID:
                setValidatorId(VALIDATOR_ID_EDEFAULT);
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
            case FaceletTaglibPackage.VALIDATOR_TAG_DEFN__VALIDATOR_ID:
                return VALIDATOR_ID_EDEFAULT == null ? validatorId != null : !VALIDATOR_ID_EDEFAULT.equals(validatorId);
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
        result.append(" (validatorId: "); //$NON-NLS-1$
        result.append(validatorId);
        result.append(')');
        return result.toString();
    }

} //ValidatorTagDefnImpl
