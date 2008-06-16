/**
 * <copyright>
 * </copyright>
 *
 * $Id: FaceletTaglibSwitch.java,v 1.1 2008/06/16 07:47:52 cbateman Exp $
 */
package org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * @param <T> 
 * <!-- end-user-doc -->
 * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibPackage
 * @generated
 */
public class FaceletTaglibSwitch<T>
{
    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static FaceletTaglibPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FaceletTaglibSwitch()
    {
        if (modelPackage == null)
        {
            modelPackage = FaceletTaglibPackage.eINSTANCE;
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * @param theEObject 
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    public T doSwitch(EObject theEObject)
    {
        return doSwitch(theEObject.eClass(), theEObject);
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * @param theEClass 
     * @param theEObject 
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(EClass theEClass, EObject theEObject)
    {
        if (theEClass.eContainer() == modelPackage)
        {
            return doSwitch(theEClass.getClassifierID(), theEObject);
        }
        List<EClass> eSuperTypes = theEClass.getESuperTypes();
        return
            eSuperTypes.isEmpty() ?
                defaultCase(theEObject) :
                doSwitch(eSuperTypes.get(0), theEObject);
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * @param classifierID 
     * @param theEObject 
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(int classifierID, EObject theEObject)
    {
        switch (classifierID)
        {
            case FaceletTaglibPackage.FACELET_LIBRARY_CLASS_TAG_LIB:
            {
                FaceletLibraryClassTagLib faceletLibraryClassTagLib = (FaceletLibraryClassTagLib)theEObject;
                T result = caseFaceletLibraryClassTagLib(faceletLibraryClassTagLib);
                if (result == null) result = caseFaceletTaglibDefn(faceletLibraryClassTagLib);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case FaceletTaglibPackage.FACELET_XML_DEFN_TAGLIB:
            {
                FaceletXMLDefnTaglib faceletXMLDefnTaglib = (FaceletXMLDefnTaglib)theEObject;
                T result = caseFaceletXMLDefnTaglib(faceletXMLDefnTaglib);
                if (result == null) result = caseFaceletTaglibDefn(faceletXMLDefnTaglib);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case FaceletTaglibPackage.FACELET_TAGLIB_DEFN:
            {
                FaceletTaglibDefn faceletTaglibDefn = (FaceletTaglibDefn)theEObject;
                T result = caseFaceletTaglibDefn(faceletTaglibDefn);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case FaceletTaglibPackage.COMPONENT_TAG_DEFN:
            {
                ComponentTagDefn componentTagDefn = (ComponentTagDefn)theEObject;
                T result = caseComponentTagDefn(componentTagDefn);
                if (result == null) result = caseHandlerTagDefn(componentTagDefn);
                if (result == null) result = caseTagDefn(componentTagDefn);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case FaceletTaglibPackage.VALIDATOR_TAG_DEFN:
            {
                ValidatorTagDefn validatorTagDefn = (ValidatorTagDefn)theEObject;
                T result = caseValidatorTagDefn(validatorTagDefn);
                if (result == null) result = caseHandlerTagDefn(validatorTagDefn);
                if (result == null) result = caseTagDefn(validatorTagDefn);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case FaceletTaglibPackage.CONVERTER_TAG_DEFN:
            {
                ConverterTagDefn converterTagDefn = (ConverterTagDefn)theEObject;
                T result = caseConverterTagDefn(converterTagDefn);
                if (result == null) result = caseHandlerTagDefn(converterTagDefn);
                if (result == null) result = caseTagDefn(converterTagDefn);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case FaceletTaglibPackage.HANDLER_TAG_DEFN:
            {
                HandlerTagDefn handlerTagDefn = (HandlerTagDefn)theEObject;
                T result = caseHandlerTagDefn(handlerTagDefn);
                if (result == null) result = caseTagDefn(handlerTagDefn);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case FaceletTaglibPackage.SOURCE_TAG_DEFN:
            {
                SourceTagDefn sourceTagDefn = (SourceTagDefn)theEObject;
                T result = caseSourceTagDefn(sourceTagDefn);
                if (result == null) result = caseTagDefn(sourceTagDefn);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case FaceletTaglibPackage.TAG_DEFN:
            {
                TagDefn tagDefn = (TagDefn)theEObject;
                T result = caseTagDefn(tagDefn);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case FaceletTaglibPackage.FUNCTION_DEFN:
            {
                FunctionDefn functionDefn = (FunctionDefn)theEObject;
                T result = caseFunctionDefn(functionDefn);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Facelet Library Class Tag Lib</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Facelet Library Class Tag Lib</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFaceletLibraryClassTagLib(FaceletLibraryClassTagLib object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Facelet XML Defn Taglib</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Facelet XML Defn Taglib</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFaceletXMLDefnTaglib(FaceletXMLDefnTaglib object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Defn</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Defn</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFaceletTaglibDefn(FaceletTaglibDefn object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Component Tag Defn</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Component Tag Defn</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseComponentTagDefn(ComponentTagDefn object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Validator Tag Defn</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Validator Tag Defn</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseValidatorTagDefn(ValidatorTagDefn object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Converter Tag Defn</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Converter Tag Defn</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConverterTagDefn(ConverterTagDefn object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Handler Tag Defn</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Handler Tag Defn</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseHandlerTagDefn(HandlerTagDefn object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Source Tag Defn</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Source Tag Defn</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSourceTagDefn(SourceTagDefn object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Tag Defn</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Tag Defn</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTagDefn(TagDefn object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Function Defn</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Function Defn</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFunctionDefn(FunctionDefn object)
    {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch, but this is the last case anyway.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public T defaultCase(EObject object)
    {
        return null;
    }

} //FaceletTaglibSwitch
