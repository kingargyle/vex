/**
 * <copyright>
 * </copyright>
 *
 * $Id: ComponentTagDefnImpl.java,v 1.1 2008/06/16 07:47:53 cbateman Exp $
 */
package org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ComponentTagDefn;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Component Tag Defn</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.ComponentTagDefnImpl#getComponentType <em>Component Type</em>}</li>
 *   <li>{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.ComponentTagDefnImpl#getRendererType <em>Renderer Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ComponentTagDefnImpl extends HandlerTagDefnImpl implements ComponentTagDefn
{
    /**
     * The default value of the '{@link #getComponentType() <em>Component Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getComponentType()
     * @generated
     * @ordered
     */
    protected static final String COMPONENT_TYPE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getComponentType() <em>Component Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getComponentType()
     * @generated
     * @ordered
     */
    protected String componentType = COMPONENT_TYPE_EDEFAULT;

    /**
     * The default value of the '{@link #getRendererType() <em>Renderer Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRendererType()
     * @generated
     * @ordered
     */
    protected static final String RENDERER_TYPE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getRendererType() <em>Renderer Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRendererType()
     * @generated
     * @ordered
     */
    protected String rendererType = RENDERER_TYPE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ComponentTagDefnImpl()
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
        return FaceletTaglibPackage.Literals.COMPONENT_TAG_DEFN;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getComponentType()
    {
        return componentType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setComponentType(String newComponentType)
    {
        String oldComponentType = componentType;
        componentType = newComponentType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, FaceletTaglibPackage.COMPONENT_TAG_DEFN__COMPONENT_TYPE, oldComponentType, componentType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getRendererType()
    {
        return rendererType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRendererType(String newRendererType)
    {
        String oldRendererType = rendererType;
        rendererType = newRendererType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, FaceletTaglibPackage.COMPONENT_TAG_DEFN__RENDERER_TYPE, oldRendererType, rendererType));
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
            case FaceletTaglibPackage.COMPONENT_TAG_DEFN__COMPONENT_TYPE:
                return getComponentType();
            case FaceletTaglibPackage.COMPONENT_TAG_DEFN__RENDERER_TYPE:
                return getRendererType();
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
            case FaceletTaglibPackage.COMPONENT_TAG_DEFN__COMPONENT_TYPE:
                setComponentType((String)newValue);
                return;
            case FaceletTaglibPackage.COMPONENT_TAG_DEFN__RENDERER_TYPE:
                setRendererType((String)newValue);
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
            case FaceletTaglibPackage.COMPONENT_TAG_DEFN__COMPONENT_TYPE:
                setComponentType(COMPONENT_TYPE_EDEFAULT);
                return;
            case FaceletTaglibPackage.COMPONENT_TAG_DEFN__RENDERER_TYPE:
                setRendererType(RENDERER_TYPE_EDEFAULT);
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
            case FaceletTaglibPackage.COMPONENT_TAG_DEFN__COMPONENT_TYPE:
                return COMPONENT_TYPE_EDEFAULT == null ? componentType != null : !COMPONENT_TYPE_EDEFAULT.equals(componentType);
            case FaceletTaglibPackage.COMPONENT_TAG_DEFN__RENDERER_TYPE:
                return RENDERER_TYPE_EDEFAULT == null ? rendererType != null : !RENDERER_TYPE_EDEFAULT.equals(rendererType);
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
        result.append(" (componentType: "); //$NON-NLS-1$
        result.append(componentType);
        result.append(", rendererType: "); //$NON-NLS-1$
        result.append(rendererType);
        result.append(')');
        return result.toString();
    }

} //ComponentTagDefnImpl
