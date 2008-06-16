/**
 * <copyright>
 * </copyright>
 *
 * $Id: ComponentTagDefn.java,v 1.1 2008/06/16 07:47:53 cbateman Exp $
 */
package org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Component Tag Defn</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ComponentTagDefn#getComponentType <em>Component Type</em>}</li>
 *   <li>{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ComponentTagDefn#getRendererType <em>Renderer Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibPackage#getComponentTagDefn()
 * @model
 * @generated
 */
public interface ComponentTagDefn extends HandlerTagDefn
{
    /**
     * Returns the value of the '<em><b>Component Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Component Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Component Type</em>' attribute.
     * @see #setComponentType(String)
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibPackage#getComponentTagDefn_ComponentType()
     * @model
     * @generated
     */
    String getComponentType();

    /**
     * Sets the value of the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ComponentTagDefn#getComponentType <em>Component Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Component Type</em>' attribute.
     * @see #getComponentType()
     * @generated
     */
    void setComponentType(String value);

    /**
     * Returns the value of the '<em><b>Renderer Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Renderer Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Renderer Type</em>' attribute.
     * @see #setRendererType(String)
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibPackage#getComponentTagDefn_RendererType()
     * @model
     * @generated
     */
    String getRendererType();

    /**
     * Sets the value of the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ComponentTagDefn#getRendererType <em>Renderer Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Renderer Type</em>' attribute.
     * @see #getRendererType()
     * @generated
     */
    void setRendererType(String value);

} // ComponentTagDefn
