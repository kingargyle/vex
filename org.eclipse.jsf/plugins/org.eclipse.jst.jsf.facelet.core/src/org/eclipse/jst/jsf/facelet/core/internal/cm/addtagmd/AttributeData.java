/**
 * <copyright>
 * </copyright>
 *
 * $Id: AttributeData.java,v 1.1 2008/06/10 17:24:00 cbateman Exp $
 */
package org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Data</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeData#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeData#getUsage <em>Usage</em>}</li>
 *   <li>{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeData#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AddTagMDPackage#getAttributeData()
 * @model
 * @generated
 */
public interface AttributeData extends EObject
{
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AddTagMDPackage#getAttributeData_Name()
     * @model extendedMetaData="kind='element' name='name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeData#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Usage</b></em>' attribute.
     * The literals are from the enumeration {@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeUsage}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Usage</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Usage</em>' attribute.
     * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeUsage
     * @see #setUsage(AttributeUsage)
     * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AddTagMDPackage#getAttributeData_Usage()
     * @model extendedMetaData="kind='element' name='usage'"
     * @generated
     */
    AttributeUsage getUsage();

    /**
     * Sets the value of the '{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeData#getUsage <em>Usage</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Usage</em>' attribute.
     * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeUsage
     * @see #getUsage()
     * @generated
     */
    void setUsage(AttributeUsage value);

    /**
     * Returns the value of the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Description</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Description</em>' attribute.
     * @see #setDescription(String)
     * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AddTagMDPackage#getAttributeData_Description()
     * @model extendedMetaData="kind='element' name='description'"
     * @generated
     */
    String getDescription();

    /**
     * Sets the value of the '{@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeData#getDescription <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Description</em>' attribute.
     * @see #getDescription()
     * @generated
     */
    void setDescription(String value);

} // AttributeData
