/**
 * <copyright>
 * </copyright>
 *
 * $Id: AttributeDataImpl.java,v 1.1 2008/06/10 17:24:02 cbateman Exp $
 */
package org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AddTagMDPackage;
import org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeData;
import org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeUsage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.impl.AttributeDataImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.impl.AttributeDataImpl#getUsage <em>Usage</em>}</li>
 *   <li>{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.impl.AttributeDataImpl#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttributeDataImpl extends EObjectImpl implements AttributeData
{
    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getUsage() <em>Usage</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUsage()
     * @generated
     * @ordered
     */
    protected static final AttributeUsage USAGE_EDEFAULT = AttributeUsage.OPTIONAL;

    /**
     * The cached value of the '{@link #getUsage() <em>Usage</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUsage()
     * @generated
     * @ordered
     */
    protected AttributeUsage usage = USAGE_EDEFAULT;

    /**
     * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected String description = DESCRIPTION_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AttributeDataImpl()
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
        return AddTagMDPackage.Literals.ATTRIBUTE_DATA;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName()
    {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setName(String newName)
    {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AddTagMDPackage.ATTRIBUTE_DATA__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AttributeUsage getUsage()
    {
        return usage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUsage(AttributeUsage newUsage)
    {
        AttributeUsage oldUsage = usage;
        usage = newUsage == null ? USAGE_EDEFAULT : newUsage;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AddTagMDPackage.ATTRIBUTE_DATA__USAGE, oldUsage, usage));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDescription(String newDescription)
    {
        String oldDescription = description;
        description = newDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AddTagMDPackage.ATTRIBUTE_DATA__DESCRIPTION, oldDescription, description));
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
            case AddTagMDPackage.ATTRIBUTE_DATA__NAME:
                return getName();
            case AddTagMDPackage.ATTRIBUTE_DATA__USAGE:
                return getUsage();
            case AddTagMDPackage.ATTRIBUTE_DATA__DESCRIPTION:
                return getDescription();
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
            case AddTagMDPackage.ATTRIBUTE_DATA__NAME:
                setName((String)newValue);
                return;
            case AddTagMDPackage.ATTRIBUTE_DATA__USAGE:
                setUsage((AttributeUsage)newValue);
                return;
            case AddTagMDPackage.ATTRIBUTE_DATA__DESCRIPTION:
                setDescription((String)newValue);
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
            case AddTagMDPackage.ATTRIBUTE_DATA__NAME:
                setName(NAME_EDEFAULT);
                return;
            case AddTagMDPackage.ATTRIBUTE_DATA__USAGE:
                setUsage(USAGE_EDEFAULT);
                return;
            case AddTagMDPackage.ATTRIBUTE_DATA__DESCRIPTION:
                setDescription(DESCRIPTION_EDEFAULT);
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
            case AddTagMDPackage.ATTRIBUTE_DATA__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case AddTagMDPackage.ATTRIBUTE_DATA__USAGE:
                return usage != USAGE_EDEFAULT;
            case AddTagMDPackage.ATTRIBUTE_DATA__DESCRIPTION:
                return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
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
        result.append(" (name: "); //$NON-NLS-1$
        result.append(name);
        result.append(", usage: "); //$NON-NLS-1$
        result.append(usage);
        result.append(", description: "); //$NON-NLS-1$
        result.append(description);
        result.append(')');
        return result.toString();
    }

} //AttributeDataImpl
