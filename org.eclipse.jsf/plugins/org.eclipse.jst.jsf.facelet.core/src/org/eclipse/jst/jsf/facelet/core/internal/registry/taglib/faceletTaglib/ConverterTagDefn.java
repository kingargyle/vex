/**
 * <copyright>
 * </copyright>
 *
 * $Id: ConverterTagDefn.java,v 1.1 2008/06/16 07:47:53 cbateman Exp $
 */
package org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Converter Tag Defn</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ConverterTagDefn#getConverterId <em>Converter Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibPackage#getConverterTagDefn()
 * @model
 * @generated
 */
public interface ConverterTagDefn extends HandlerTagDefn
{
    /**
     * Returns the value of the '<em><b>Converter Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Converter Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Converter Id</em>' attribute.
     * @see #setConverterId(String)
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibPackage#getConverterTagDefn_ConverterId()
     * @model
     * @generated
     */
    String getConverterId();

    /**
     * Sets the value of the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ConverterTagDefn#getConverterId <em>Converter Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Converter Id</em>' attribute.
     * @see #getConverterId()
     * @generated
     */
    void setConverterId(String value);

} // ConverterTagDefn
