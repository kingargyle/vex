/**
 * <copyright>
 * </copyright>
 *
 * $Id: FaceletXMLDefnTaglibImpl.java,v 1.1 2008/06/16 07:47:53 cbateman Exp $
 */
package org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibPackage;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletXMLDefnTaglib;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FunctionDefn;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.TagDefn;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Facelet XML Defn Taglib</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletXMLDefnTaglibImpl#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletXMLDefnTaglibImpl#getTags <em>Tags</em>}</li>
 *   <li>{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletXMLDefnTaglibImpl#getFunctions <em>Functions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FaceletXMLDefnTaglibImpl extends FaceletTaglibDefnImpl implements FaceletXMLDefnTaglib
{
    /**
     * The default value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNamespace()
     * @generated
     * @ordered
     */
    protected static final String NAMESPACE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNamespace()
     * @generated
     * @ordered
     */
    protected String namespace = NAMESPACE_EDEFAULT;

    /**
     * The cached value of the '{@link #getTags() <em>Tags</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTags()
     * @generated
     * @ordered
     */
    protected EList<TagDefn> tags;

    /**
     * The cached value of the '{@link #getFunctions() <em>Functions</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFunctions()
     * @generated
     * @ordered
     */
    protected EList<FunctionDefn> functions;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected FaceletXMLDefnTaglibImpl()
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
        return FaceletTaglibPackage.Literals.FACELET_XML_DEFN_TAGLIB;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getNamespace()
    {
        return namespace;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNamespace(String newNamespace)
    {
        String oldNamespace = namespace;
        namespace = newNamespace;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, FaceletTaglibPackage.FACELET_XML_DEFN_TAGLIB__NAMESPACE, oldNamespace, namespace));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<TagDefn> getTags()
    {
        if (tags == null)
        {
            tags = new EObjectResolvingEList<TagDefn>(TagDefn.class, this, FaceletTaglibPackage.FACELET_XML_DEFN_TAGLIB__TAGS);
        }
        return tags;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<FunctionDefn> getFunctions()
    {
        if (functions == null)
        {
            functions = new EObjectResolvingEList<FunctionDefn>(FunctionDefn.class, this, FaceletTaglibPackage.FACELET_XML_DEFN_TAGLIB__FUNCTIONS);
        }
        return functions;
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
            case FaceletTaglibPackage.FACELET_XML_DEFN_TAGLIB__NAMESPACE:
                return getNamespace();
            case FaceletTaglibPackage.FACELET_XML_DEFN_TAGLIB__TAGS:
                return getTags();
            case FaceletTaglibPackage.FACELET_XML_DEFN_TAGLIB__FUNCTIONS:
                return getFunctions();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue)
    {
        switch (featureID)
        {
            case FaceletTaglibPackage.FACELET_XML_DEFN_TAGLIB__NAMESPACE:
                setNamespace((String)newValue);
                return;
            case FaceletTaglibPackage.FACELET_XML_DEFN_TAGLIB__TAGS:
                getTags().clear();
                getTags().addAll((Collection<? extends TagDefn>)newValue);
                return;
            case FaceletTaglibPackage.FACELET_XML_DEFN_TAGLIB__FUNCTIONS:
                getFunctions().clear();
                getFunctions().addAll((Collection<? extends FunctionDefn>)newValue);
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
            case FaceletTaglibPackage.FACELET_XML_DEFN_TAGLIB__NAMESPACE:
                setNamespace(NAMESPACE_EDEFAULT);
                return;
            case FaceletTaglibPackage.FACELET_XML_DEFN_TAGLIB__TAGS:
                getTags().clear();
                return;
            case FaceletTaglibPackage.FACELET_XML_DEFN_TAGLIB__FUNCTIONS:
                getFunctions().clear();
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
            case FaceletTaglibPackage.FACELET_XML_DEFN_TAGLIB__NAMESPACE:
                return NAMESPACE_EDEFAULT == null ? namespace != null : !NAMESPACE_EDEFAULT.equals(namespace);
            case FaceletTaglibPackage.FACELET_XML_DEFN_TAGLIB__TAGS:
                return tags != null && !tags.isEmpty();
            case FaceletTaglibPackage.FACELET_XML_DEFN_TAGLIB__FUNCTIONS:
                return functions != null && !functions.isEmpty();
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
        result.append(" (namespace: "); //$NON-NLS-1$
        result.append(namespace);
        result.append(')');
        return result.toString();
    }

} //FaceletXMLDefnTaglibImpl
