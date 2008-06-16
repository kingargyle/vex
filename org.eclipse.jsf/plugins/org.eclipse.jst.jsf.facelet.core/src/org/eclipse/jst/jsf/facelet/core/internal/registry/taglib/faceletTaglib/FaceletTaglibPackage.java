/**
 * <copyright>
 * </copyright>
 *
 * $Id: FaceletTaglibPackage.java,v 1.1 2008/06/16 07:47:53 cbateman Exp $
 */
package org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibFactory
 * @model kind="package"
 * @generated
 */
public interface FaceletTaglibPackage extends EPackage
{
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "faceletTaglib"; //$NON-NLS-1$

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://org.eclipse.jst.jsf.facelet.core/faceletTaglib.ecore"; //$NON-NLS-1$

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "faceletTaglib"; //$NON-NLS-1$

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    FaceletTaglibPackage eINSTANCE = org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibPackageImpl.init();

    /**
     * The meta object id for the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibDefnImpl <em>Defn</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibDefnImpl
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibPackageImpl#getFaceletTaglibDefn()
     * @generated
     */
    int FACELET_TAGLIB_DEFN = 2;

    /**
     * The number of structural features of the '<em>Defn</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FACELET_TAGLIB_DEFN_FEATURE_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletLibraryClassTagLibImpl <em>Facelet Library Class Tag Lib</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletLibraryClassTagLibImpl
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibPackageImpl#getFaceletLibraryClassTagLib()
     * @generated
     */
    int FACELET_LIBRARY_CLASS_TAG_LIB = 0;

    /**
     * The feature id for the '<em><b>Library Class</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FACELET_LIBRARY_CLASS_TAG_LIB__LIBRARY_CLASS = FACELET_TAGLIB_DEFN_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Facelet Library Class Tag Lib</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FACELET_LIBRARY_CLASS_TAG_LIB_FEATURE_COUNT = FACELET_TAGLIB_DEFN_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletXMLDefnTaglibImpl <em>Facelet XML Defn Taglib</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletXMLDefnTaglibImpl
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibPackageImpl#getFaceletXMLDefnTaglib()
     * @generated
     */
    int FACELET_XML_DEFN_TAGLIB = 1;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FACELET_XML_DEFN_TAGLIB__NAMESPACE = FACELET_TAGLIB_DEFN_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Tags</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FACELET_XML_DEFN_TAGLIB__TAGS = FACELET_TAGLIB_DEFN_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Functions</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FACELET_XML_DEFN_TAGLIB__FUNCTIONS = FACELET_TAGLIB_DEFN_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Facelet XML Defn Taglib</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FACELET_XML_DEFN_TAGLIB_FEATURE_COUNT = FACELET_TAGLIB_DEFN_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.TagDefnImpl <em>Tag Defn</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.TagDefnImpl
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibPackageImpl#getTagDefn()
     * @generated
     */
    int TAG_DEFN = 8;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TAG_DEFN__NAME = 0;

    /**
     * The number of structural features of the '<em>Tag Defn</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TAG_DEFN_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.HandlerTagDefnImpl <em>Handler Tag Defn</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.HandlerTagDefnImpl
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibPackageImpl#getHandlerTagDefn()
     * @generated
     */
    int HANDLER_TAG_DEFN = 6;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HANDLER_TAG_DEFN__NAME = TAG_DEFN__NAME;

    /**
     * The feature id for the '<em><b>Handler Class</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HANDLER_TAG_DEFN__HANDLER_CLASS = TAG_DEFN_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Handler Tag Defn</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HANDLER_TAG_DEFN_FEATURE_COUNT = TAG_DEFN_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.ComponentTagDefnImpl <em>Component Tag Defn</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.ComponentTagDefnImpl
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibPackageImpl#getComponentTagDefn()
     * @generated
     */
    int COMPONENT_TAG_DEFN = 3;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPONENT_TAG_DEFN__NAME = HANDLER_TAG_DEFN__NAME;

    /**
     * The feature id for the '<em><b>Handler Class</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPONENT_TAG_DEFN__HANDLER_CLASS = HANDLER_TAG_DEFN__HANDLER_CLASS;

    /**
     * The feature id for the '<em><b>Component Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPONENT_TAG_DEFN__COMPONENT_TYPE = HANDLER_TAG_DEFN_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Renderer Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPONENT_TAG_DEFN__RENDERER_TYPE = HANDLER_TAG_DEFN_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Component Tag Defn</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPONENT_TAG_DEFN_FEATURE_COUNT = HANDLER_TAG_DEFN_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.ValidatorTagDefnImpl <em>Validator Tag Defn</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.ValidatorTagDefnImpl
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibPackageImpl#getValidatorTagDefn()
     * @generated
     */
    int VALIDATOR_TAG_DEFN = 4;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALIDATOR_TAG_DEFN__NAME = HANDLER_TAG_DEFN__NAME;

    /**
     * The feature id for the '<em><b>Handler Class</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALIDATOR_TAG_DEFN__HANDLER_CLASS = HANDLER_TAG_DEFN__HANDLER_CLASS;

    /**
     * The feature id for the '<em><b>Validator Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALIDATOR_TAG_DEFN__VALIDATOR_ID = HANDLER_TAG_DEFN_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Validator Tag Defn</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VALIDATOR_TAG_DEFN_FEATURE_COUNT = HANDLER_TAG_DEFN_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.ConverterTagDefnImpl <em>Converter Tag Defn</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.ConverterTagDefnImpl
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibPackageImpl#getConverterTagDefn()
     * @generated
     */
    int CONVERTER_TAG_DEFN = 5;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONVERTER_TAG_DEFN__NAME = HANDLER_TAG_DEFN__NAME;

    /**
     * The feature id for the '<em><b>Handler Class</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONVERTER_TAG_DEFN__HANDLER_CLASS = HANDLER_TAG_DEFN__HANDLER_CLASS;

    /**
     * The feature id for the '<em><b>Converter Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONVERTER_TAG_DEFN__CONVERTER_ID = HANDLER_TAG_DEFN_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Converter Tag Defn</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONVERTER_TAG_DEFN_FEATURE_COUNT = HANDLER_TAG_DEFN_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.SourceTagDefnImpl <em>Source Tag Defn</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.SourceTagDefnImpl
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibPackageImpl#getSourceTagDefn()
     * @generated
     */
    int SOURCE_TAG_DEFN = 7;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SOURCE_TAG_DEFN__NAME = TAG_DEFN__NAME;

    /**
     * The feature id for the '<em><b>Source</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SOURCE_TAG_DEFN__SOURCE = TAG_DEFN_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Source Tag Defn</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SOURCE_TAG_DEFN_FEATURE_COUNT = TAG_DEFN_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FunctionDefnImpl <em>Function Defn</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FunctionDefnImpl
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibPackageImpl#getFunctionDefn()
     * @generated
     */
    int FUNCTION_DEFN = 9;

    /**
     * The feature id for the '<em><b>Function Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FUNCTION_DEFN__FUNCTION_NAME = 0;

    /**
     * The feature id for the '<em><b>Function Class</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FUNCTION_DEFN__FUNCTION_CLASS = 1;

    /**
     * The feature id for the '<em><b>Function Signature</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FUNCTION_DEFN__FUNCTION_SIGNATURE = 2;

    /**
     * The number of structural features of the '<em>Function Defn</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FUNCTION_DEFN_FEATURE_COUNT = 3;


    /**
     * Returns the meta object for class '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletLibraryClassTagLib <em>Facelet Library Class Tag Lib</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Facelet Library Class Tag Lib</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletLibraryClassTagLib
     * @generated
     */
    EClass getFaceletLibraryClassTagLib();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletLibraryClassTagLib#getLibraryClass <em>Library Class</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Library Class</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletLibraryClassTagLib#getLibraryClass()
     * @see #getFaceletLibraryClassTagLib()
     * @generated
     */
    EAttribute getFaceletLibraryClassTagLib_LibraryClass();

    /**
     * Returns the meta object for class '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletXMLDefnTaglib <em>Facelet XML Defn Taglib</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Facelet XML Defn Taglib</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletXMLDefnTaglib
     * @generated
     */
    EClass getFaceletXMLDefnTaglib();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletXMLDefnTaglib#getNamespace <em>Namespace</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Namespace</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletXMLDefnTaglib#getNamespace()
     * @see #getFaceletXMLDefnTaglib()
     * @generated
     */
    EAttribute getFaceletXMLDefnTaglib_Namespace();

    /**
     * Returns the meta object for the reference list '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletXMLDefnTaglib#getTags <em>Tags</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Tags</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletXMLDefnTaglib#getTags()
     * @see #getFaceletXMLDefnTaglib()
     * @generated
     */
    EReference getFaceletXMLDefnTaglib_Tags();

    /**
     * Returns the meta object for the reference list '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletXMLDefnTaglib#getFunctions <em>Functions</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Functions</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletXMLDefnTaglib#getFunctions()
     * @see #getFaceletXMLDefnTaglib()
     * @generated
     */
    EReference getFaceletXMLDefnTaglib_Functions();

    /**
     * Returns the meta object for class '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibDefn <em>Defn</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Defn</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibDefn
     * @generated
     */
    EClass getFaceletTaglibDefn();

    /**
     * Returns the meta object for class '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ComponentTagDefn <em>Component Tag Defn</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Component Tag Defn</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ComponentTagDefn
     * @generated
     */
    EClass getComponentTagDefn();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ComponentTagDefn#getComponentType <em>Component Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Component Type</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ComponentTagDefn#getComponentType()
     * @see #getComponentTagDefn()
     * @generated
     */
    EAttribute getComponentTagDefn_ComponentType();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ComponentTagDefn#getRendererType <em>Renderer Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Renderer Type</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ComponentTagDefn#getRendererType()
     * @see #getComponentTagDefn()
     * @generated
     */
    EAttribute getComponentTagDefn_RendererType();

    /**
     * Returns the meta object for class '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ValidatorTagDefn <em>Validator Tag Defn</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Validator Tag Defn</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ValidatorTagDefn
     * @generated
     */
    EClass getValidatorTagDefn();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ValidatorTagDefn#getValidatorId <em>Validator Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Validator Id</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ValidatorTagDefn#getValidatorId()
     * @see #getValidatorTagDefn()
     * @generated
     */
    EAttribute getValidatorTagDefn_ValidatorId();

    /**
     * Returns the meta object for class '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ConverterTagDefn <em>Converter Tag Defn</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Converter Tag Defn</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ConverterTagDefn
     * @generated
     */
    EClass getConverterTagDefn();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ConverterTagDefn#getConverterId <em>Converter Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Converter Id</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ConverterTagDefn#getConverterId()
     * @see #getConverterTagDefn()
     * @generated
     */
    EAttribute getConverterTagDefn_ConverterId();

    /**
     * Returns the meta object for class '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.HandlerTagDefn <em>Handler Tag Defn</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Handler Tag Defn</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.HandlerTagDefn
     * @generated
     */
    EClass getHandlerTagDefn();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.HandlerTagDefn#getHandlerClass <em>Handler Class</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Handler Class</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.HandlerTagDefn#getHandlerClass()
     * @see #getHandlerTagDefn()
     * @generated
     */
    EAttribute getHandlerTagDefn_HandlerClass();

    /**
     * Returns the meta object for class '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.SourceTagDefn <em>Source Tag Defn</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Source Tag Defn</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.SourceTagDefn
     * @generated
     */
    EClass getSourceTagDefn();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.SourceTagDefn#getSource <em>Source</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Source</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.SourceTagDefn#getSource()
     * @see #getSourceTagDefn()
     * @generated
     */
    EAttribute getSourceTagDefn_Source();

    /**
     * Returns the meta object for class '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.TagDefn <em>Tag Defn</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Tag Defn</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.TagDefn
     * @generated
     */
    EClass getTagDefn();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.TagDefn#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.TagDefn#getName()
     * @see #getTagDefn()
     * @generated
     */
    EAttribute getTagDefn_Name();

    /**
     * Returns the meta object for class '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FunctionDefn <em>Function Defn</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Function Defn</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FunctionDefn
     * @generated
     */
    EClass getFunctionDefn();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FunctionDefn#getFunctionName <em>Function Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Function Name</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FunctionDefn#getFunctionName()
     * @see #getFunctionDefn()
     * @generated
     */
    EAttribute getFunctionDefn_FunctionName();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FunctionDefn#getFunctionClass <em>Function Class</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Function Class</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FunctionDefn#getFunctionClass()
     * @see #getFunctionDefn()
     * @generated
     */
    EAttribute getFunctionDefn_FunctionClass();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FunctionDefn#getFunctionSignature <em>Function Signature</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Function Signature</em>'.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FunctionDefn#getFunctionSignature()
     * @see #getFunctionDefn()
     * @generated
     */
    EAttribute getFunctionDefn_FunctionSignature();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    FaceletTaglibFactory getFaceletTaglibFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("hiding")
    interface Literals
    {
        /**
         * The meta object literal for the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletLibraryClassTagLibImpl <em>Facelet Library Class Tag Lib</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletLibraryClassTagLibImpl
         * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibPackageImpl#getFaceletLibraryClassTagLib()
         * @generated
         */
        EClass FACELET_LIBRARY_CLASS_TAG_LIB = eINSTANCE.getFaceletLibraryClassTagLib();

        /**
         * The meta object literal for the '<em><b>Library Class</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FACELET_LIBRARY_CLASS_TAG_LIB__LIBRARY_CLASS = eINSTANCE.getFaceletLibraryClassTagLib_LibraryClass();

        /**
         * The meta object literal for the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletXMLDefnTaglibImpl <em>Facelet XML Defn Taglib</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletXMLDefnTaglibImpl
         * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibPackageImpl#getFaceletXMLDefnTaglib()
         * @generated
         */
        EClass FACELET_XML_DEFN_TAGLIB = eINSTANCE.getFaceletXMLDefnTaglib();

        /**
         * The meta object literal for the '<em><b>Namespace</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FACELET_XML_DEFN_TAGLIB__NAMESPACE = eINSTANCE.getFaceletXMLDefnTaglib_Namespace();

        /**
         * The meta object literal for the '<em><b>Tags</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference FACELET_XML_DEFN_TAGLIB__TAGS = eINSTANCE.getFaceletXMLDefnTaglib_Tags();

        /**
         * The meta object literal for the '<em><b>Functions</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference FACELET_XML_DEFN_TAGLIB__FUNCTIONS = eINSTANCE.getFaceletXMLDefnTaglib_Functions();

        /**
         * The meta object literal for the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibDefnImpl <em>Defn</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibDefnImpl
         * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibPackageImpl#getFaceletTaglibDefn()
         * @generated
         */
        EClass FACELET_TAGLIB_DEFN = eINSTANCE.getFaceletTaglibDefn();

        /**
         * The meta object literal for the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.ComponentTagDefnImpl <em>Component Tag Defn</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.ComponentTagDefnImpl
         * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibPackageImpl#getComponentTagDefn()
         * @generated
         */
        EClass COMPONENT_TAG_DEFN = eINSTANCE.getComponentTagDefn();

        /**
         * The meta object literal for the '<em><b>Component Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COMPONENT_TAG_DEFN__COMPONENT_TYPE = eINSTANCE.getComponentTagDefn_ComponentType();

        /**
         * The meta object literal for the '<em><b>Renderer Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COMPONENT_TAG_DEFN__RENDERER_TYPE = eINSTANCE.getComponentTagDefn_RendererType();

        /**
         * The meta object literal for the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.ValidatorTagDefnImpl <em>Validator Tag Defn</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.ValidatorTagDefnImpl
         * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibPackageImpl#getValidatorTagDefn()
         * @generated
         */
        EClass VALIDATOR_TAG_DEFN = eINSTANCE.getValidatorTagDefn();

        /**
         * The meta object literal for the '<em><b>Validator Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute VALIDATOR_TAG_DEFN__VALIDATOR_ID = eINSTANCE.getValidatorTagDefn_ValidatorId();

        /**
         * The meta object literal for the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.ConverterTagDefnImpl <em>Converter Tag Defn</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.ConverterTagDefnImpl
         * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibPackageImpl#getConverterTagDefn()
         * @generated
         */
        EClass CONVERTER_TAG_DEFN = eINSTANCE.getConverterTagDefn();

        /**
         * The meta object literal for the '<em><b>Converter Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CONVERTER_TAG_DEFN__CONVERTER_ID = eINSTANCE.getConverterTagDefn_ConverterId();

        /**
         * The meta object literal for the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.HandlerTagDefnImpl <em>Handler Tag Defn</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.HandlerTagDefnImpl
         * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibPackageImpl#getHandlerTagDefn()
         * @generated
         */
        EClass HANDLER_TAG_DEFN = eINSTANCE.getHandlerTagDefn();

        /**
         * The meta object literal for the '<em><b>Handler Class</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HANDLER_TAG_DEFN__HANDLER_CLASS = eINSTANCE.getHandlerTagDefn_HandlerClass();

        /**
         * The meta object literal for the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.SourceTagDefnImpl <em>Source Tag Defn</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.SourceTagDefnImpl
         * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibPackageImpl#getSourceTagDefn()
         * @generated
         */
        EClass SOURCE_TAG_DEFN = eINSTANCE.getSourceTagDefn();

        /**
         * The meta object literal for the '<em><b>Source</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SOURCE_TAG_DEFN__SOURCE = eINSTANCE.getSourceTagDefn_Source();

        /**
         * The meta object literal for the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.TagDefnImpl <em>Tag Defn</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.TagDefnImpl
         * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibPackageImpl#getTagDefn()
         * @generated
         */
        EClass TAG_DEFN = eINSTANCE.getTagDefn();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TAG_DEFN__NAME = eINSTANCE.getTagDefn_Name();

        /**
         * The meta object literal for the '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FunctionDefnImpl <em>Function Defn</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FunctionDefnImpl
         * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.impl.FaceletTaglibPackageImpl#getFunctionDefn()
         * @generated
         */
        EClass FUNCTION_DEFN = eINSTANCE.getFunctionDefn();

        /**
         * The meta object literal for the '<em><b>Function Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FUNCTION_DEFN__FUNCTION_NAME = eINSTANCE.getFunctionDefn_FunctionName();

        /**
         * The meta object literal for the '<em><b>Function Class</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FUNCTION_DEFN__FUNCTION_CLASS = eINSTANCE.getFunctionDefn_FunctionClass();

        /**
         * The meta object literal for the '<em><b>Function Signature</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FUNCTION_DEFN__FUNCTION_SIGNATURE = eINSTANCE.getFunctionDefn_FunctionSignature();

    }

} //FaceletTaglibPackage
