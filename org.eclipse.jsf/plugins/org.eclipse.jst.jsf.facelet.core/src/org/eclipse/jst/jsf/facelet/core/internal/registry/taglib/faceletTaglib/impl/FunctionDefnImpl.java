/**
 * <copyright>
 * </copyright>
 *
 * $Id: FunctionDefnImpl.java,v 1.1 2008/06/16 07:47:53 cbateman Exp $
 */
package org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibPackage;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FunctionDefn;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Function Defn</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FunctionDefnImpl#getFunctionName <em>Function Name</em>}</li>
 *   <li>{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FunctionDefnImpl#getFunctionClass <em>Function Class</em>}</li>
 *   <li>{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FunctionDefnImpl#getFunctionSignature <em>Function Signature</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FunctionDefnImpl extends EObjectImpl implements FunctionDefn
{
    /**
     * The default value of the '{@link #getFunctionName() <em>Function Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFunctionName()
     * @generated
     * @ordered
     */
    protected static final String FUNCTION_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFunctionName() <em>Function Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFunctionName()
     * @generated
     * @ordered
     */
    protected String functionName = FUNCTION_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getFunctionClass() <em>Function Class</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFunctionClass()
     * @generated
     * @ordered
     */
    protected static final String FUNCTION_CLASS_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFunctionClass() <em>Function Class</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFunctionClass()
     * @generated
     * @ordered
     */
    protected String functionClass = FUNCTION_CLASS_EDEFAULT;

    /**
     * The default value of the '{@link #getFunctionSignature() <em>Function Signature</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFunctionSignature()
     * @generated
     * @ordered
     */
    protected static final String FUNCTION_SIGNATURE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFunctionSignature() <em>Function Signature</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFunctionSignature()
     * @generated
     * @ordered
     */
    protected String functionSignature = FUNCTION_SIGNATURE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected FunctionDefnImpl()
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
        return FaceletTaglibPackage.Literals.FUNCTION_DEFN;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFunctionName()
    {
        return functionName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFunctionName(String newFunctionName)
    {
        String oldFunctionName = functionName;
        functionName = newFunctionName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, FaceletTaglibPackage.FUNCTION_DEFN__FUNCTION_NAME, oldFunctionName, functionName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFunctionClass()
    {
        return functionClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFunctionClass(String newFunctionClass)
    {
        String oldFunctionClass = functionClass;
        functionClass = newFunctionClass;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, FaceletTaglibPackage.FUNCTION_DEFN__FUNCTION_CLASS, oldFunctionClass, functionClass));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFunctionSignature()
    {
        return functionSignature;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFunctionSignature(String newFunctionSignature)
    {
        String oldFunctionSignature = functionSignature;
        functionSignature = newFunctionSignature;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, FaceletTaglibPackage.FUNCTION_DEFN__FUNCTION_SIGNATURE, oldFunctionSignature, functionSignature));
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
            case FaceletTaglibPackage.FUNCTION_DEFN__FUNCTION_NAME:
                return getFunctionName();
            case FaceletTaglibPackage.FUNCTION_DEFN__FUNCTION_CLASS:
                return getFunctionClass();
            case FaceletTaglibPackage.FUNCTION_DEFN__FUNCTION_SIGNATURE:
                return getFunctionSignature();
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
            case FaceletTaglibPackage.FUNCTION_DEFN__FUNCTION_NAME:
                setFunctionName((String)newValue);
                return;
            case FaceletTaglibPackage.FUNCTION_DEFN__FUNCTION_CLASS:
                setFunctionClass((String)newValue);
                return;
            case FaceletTaglibPackage.FUNCTION_DEFN__FUNCTION_SIGNATURE:
                setFunctionSignature((String)newValue);
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
            case FaceletTaglibPackage.FUNCTION_DEFN__FUNCTION_NAME:
                setFunctionName(FUNCTION_NAME_EDEFAULT);
                return;
            case FaceletTaglibPackage.FUNCTION_DEFN__FUNCTION_CLASS:
                setFunctionClass(FUNCTION_CLASS_EDEFAULT);
                return;
            case FaceletTaglibPackage.FUNCTION_DEFN__FUNCTION_SIGNATURE:
                setFunctionSignature(FUNCTION_SIGNATURE_EDEFAULT);
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
            case FaceletTaglibPackage.FUNCTION_DEFN__FUNCTION_NAME:
                return FUNCTION_NAME_EDEFAULT == null ? functionName != null : !FUNCTION_NAME_EDEFAULT.equals(functionName);
            case FaceletTaglibPackage.FUNCTION_DEFN__FUNCTION_CLASS:
                return FUNCTION_CLASS_EDEFAULT == null ? functionClass != null : !FUNCTION_CLASS_EDEFAULT.equals(functionClass);
            case FaceletTaglibPackage.FUNCTION_DEFN__FUNCTION_SIGNATURE:
                return FUNCTION_SIGNATURE_EDEFAULT == null ? functionSignature != null : !FUNCTION_SIGNATURE_EDEFAULT.equals(functionSignature);
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
        result.append(" (functionName: "); //$NON-NLS-1$
        result.append(functionName);
        result.append(", functionClass: "); //$NON-NLS-1$
        result.append(functionClass);
        result.append(", functionSignature: "); //$NON-NLS-1$
        result.append(functionSignature);
        result.append(')');
        return result.toString();
    }

} //FunctionDefnImpl
