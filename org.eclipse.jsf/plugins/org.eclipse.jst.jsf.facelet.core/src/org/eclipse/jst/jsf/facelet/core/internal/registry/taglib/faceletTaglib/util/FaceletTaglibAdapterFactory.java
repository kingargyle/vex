/**
 * <copyright>
 * </copyright>
 *
 * $Id: FaceletTaglibAdapterFactory.java,v 1.1 2008/06/16 07:47:52 cbateman Exp $
 */
package org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibPackage
 * @generated
 */
public class FaceletTaglibAdapterFactory extends AdapterFactoryImpl
{
    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static FaceletTaglibPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FaceletTaglibAdapterFactory()
    {
        if (modelPackage == null)
        {
            modelPackage = FaceletTaglibPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object.
     * <!-- begin-user-doc -->
     * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
     * <!-- end-user-doc -->
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object)
    {
        if (object == modelPackage)
        {
            return true;
        }
        if (object instanceof EObject)
        {
            return ((EObject)object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected FaceletTaglibSwitch<Adapter> modelSwitch =
        new FaceletTaglibSwitch<Adapter>()
        {
            @Override
            public Adapter caseFaceletLibraryClassTagLib(FaceletLibraryClassTagLib object)
            {
                return createFaceletLibraryClassTagLibAdapter();
            }
            @Override
            public Adapter caseFaceletXMLDefnTaglib(FaceletXMLDefnTaglib object)
            {
                return createFaceletXMLDefnTaglibAdapter();
            }
            @Override
            public Adapter caseFaceletTaglibDefn(FaceletTaglibDefn object)
            {
                return createFaceletTaglibDefnAdapter();
            }
            @Override
            public Adapter caseComponentTagDefn(ComponentTagDefn object)
            {
                return createComponentTagDefnAdapter();
            }
            @Override
            public Adapter caseValidatorTagDefn(ValidatorTagDefn object)
            {
                return createValidatorTagDefnAdapter();
            }
            @Override
            public Adapter caseConverterTagDefn(ConverterTagDefn object)
            {
                return createConverterTagDefnAdapter();
            }
            @Override
            public Adapter caseHandlerTagDefn(HandlerTagDefn object)
            {
                return createHandlerTagDefnAdapter();
            }
            @Override
            public Adapter caseSourceTagDefn(SourceTagDefn object)
            {
                return createSourceTagDefnAdapter();
            }
            @Override
            public Adapter caseTagDefn(TagDefn object)
            {
                return createTagDefnAdapter();
            }
            @Override
            public Adapter caseFunctionDefn(FunctionDefn object)
            {
                return createFunctionDefnAdapter();
            }
            @Override
            public Adapter defaultCase(EObject object)
            {
                return createEObjectAdapter();
            }
        };

    /**
     * Creates an adapter for the <code>target</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param target the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target)
    {
        return modelSwitch.doSwitch((EObject)target);
    }


    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletLibraryClassTagLib <em>Facelet Library Class Tag Lib</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletLibraryClassTagLib
     * @generated
     */
    public Adapter createFaceletLibraryClassTagLibAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletXMLDefnTaglib <em>Facelet XML Defn Taglib</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletXMLDefnTaglib
     * @generated
     */
    public Adapter createFaceletXMLDefnTaglibAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibDefn <em>Defn</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibDefn
     * @generated
     */
    public Adapter createFaceletTaglibDefnAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ComponentTagDefn <em>Component Tag Defn</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ComponentTagDefn
     * @generated
     */
    public Adapter createComponentTagDefnAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ValidatorTagDefn <em>Validator Tag Defn</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ValidatorTagDefn
     * @generated
     */
    public Adapter createValidatorTagDefnAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ConverterTagDefn <em>Converter Tag Defn</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ConverterTagDefn
     * @generated
     */
    public Adapter createConverterTagDefnAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.HandlerTagDefn <em>Handler Tag Defn</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.HandlerTagDefn
     * @generated
     */
    public Adapter createHandlerTagDefnAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.SourceTagDefn <em>Source Tag Defn</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.SourceTagDefn
     * @generated
     */
    public Adapter createSourceTagDefnAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.TagDefn <em>Tag Defn</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.TagDefn
     * @generated
     */
    public Adapter createTagDefnAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FunctionDefn <em>Function Defn</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FunctionDefn
     * @generated
     */
    public Adapter createFunctionDefnAdapter()
    {
        return null;
    }

    /**
     * Creates a new adapter for the default case.
     * <!-- begin-user-doc -->
     * This default implementation returns null.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter()
    {
        return null;
    }

} //FaceletTaglibAdapterFactory
