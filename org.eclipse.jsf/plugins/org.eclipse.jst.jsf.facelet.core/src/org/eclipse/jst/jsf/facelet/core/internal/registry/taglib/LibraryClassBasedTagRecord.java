/*******************************************************************************
 * Copyright (c) 2008 Oracle Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Cameron Bateman - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.jsf.facelet.core.internal.registry.taglib;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jem.internal.proxy.core.IBeanProxy;
import org.eclipse.jem.internal.proxy.core.IBeanTypeProxy;
import org.eclipse.jem.internal.proxy.core.IBooleanBeanProxy;
import org.eclipse.jem.internal.proxy.core.IFieldProxy;
import org.eclipse.jem.internal.proxy.core.IMethodProxy;
import org.eclipse.jem.internal.proxy.core.IStringBeanProxy;
import org.eclipse.jem.internal.proxy.core.ProxyFactoryRegistry;
import org.eclipse.jem.internal.proxy.core.ThrowableProxy;
import org.eclipse.jst.jsf.core.internal.JSFCorePlugin;
import org.eclipse.jst.jsf.core.internal.jem.BeanProxyUtil;
import org.eclipse.jst.jsf.core.internal.jem.BeanProxyUtil.BeanProxyWrapper;
import org.eclipse.jst.jsf.core.internal.jem.BeanProxyUtil.ProxyException;
import org.eclipse.jst.jsf.facelet.core.internal.FaceletCorePlugin;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ComponentTagDefn;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ConverterTagDefn;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletLibraryClassTagLib;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibFactory;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.TagDefn;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.ValidatorTagDefn;

/**
 * @author cbateman
 * 
 */
/* package */class LibraryClassBasedTagRecord extends FaceletTagRecord
{
    private static final String             METHOD_NAME_GET_VALUE                                                     = "getValue";                                                           //$NON-NLS-1$
    private static final String             METHOD_NAME_GET_KEY                                                       = "getKey";                                                             //$NON-NLS-1$
    private static final String             METHOD_NAME_HAS_NEXT                                                      = "hasNext";                                                            //$NON-NLS-1$
    private static final String             METHOD_NAME_NEXT                                                          = "next";                                                               //$NON-NLS-1$
    private static final String             METHOD_NAME_ITERATOR                                                      = "iterator";                                                           //$NON-NLS-1$
    private static final String             METHOD_NAME_ENTRY_SET                                                     = "entrySet";                                                           //$NON-NLS-1$
    private static final String             PROPERTY_NAME_FACTORIES                                                   = "factories";                                                          //$NON-NLS-1$
    private static final String             QUALIFIED_CLASS_NAME__COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY           = "com.sun.facelets.tag.AbstractTagLibrary";                            //$NON-NLS-1$
    private static final String             QUALIFIED_CLASS_NAME_JAVA_UTIL_MAP                                        = "java.util.Map";                                                      //$NON-NLS-1$

    private static final String             COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_HANDLER_FACTORY                = "com.sun.facelets.tag.AbstractTagLibrary$HandlerFactory";             //$NON-NLS-1$
    private static final String             COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_USER_CONVERTER_HANDLER_FACTORY = "com.sun.facelets.tag.AbstractTagLibrary$UserConverterHandlerFactory"; //$NON-NLS-1$
    private static final String             COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_CONVERTER_HANDLER_FACTORY      = "com.sun.facelets.tag.AbstractTagLibrary$ConverterHandlerFactory";    //$NON-NLS-1$
    private static final String             COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_USER_VALIDATOR_HANDLER_FACTORY = "com.sun.facelets.tag.AbstractTagLibrary$UserValidatorHandlerFactory"; //$NON-NLS-1$
    private static final String             COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_VALIDATOR_HANDLER_FACTORY      = "com.sun.facelets.tag.AbstractTagLibrary$ValidatorHandlerFactory";    //$NON-NLS-1$
    private static final String             COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_USER_COMPONENT_HANDLER_FACTORY = "com.sun.facelets.tag.AbstractTagLibrary$UserComponentHandlerFactory"; //$NON-NLS-1$
    private static final String             COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_COMPONENT_HANDLER_FACTORY      = "com.sun.facelets.tag.AbstractTagLibrary$ComponentHandlerFactory";    //$NON-NLS-1$

    private static final String             FIELD_NAME_CONVERTER_ID                                                   = "converterId";                                                        //$NON-NLS-1$
    private static final String             FIELD_NAME_VALIDATOR_ID                                                   = "validatorId";                                                        //$NON-NLS-1$
    private static final String             FIELD_NAME_RENDER_TYPE                                                    = "renderType";                                                         //$NON-NLS-1$
    private static final String             FIELD_NAME_COMPONENT_TYPE                                                 = "componentType";                                                      //$NON-NLS-1$

    /**
     * 
     */
    private static final long               serialVersionUID                                                          = 4174629773250721041L;
    private static final String             STATIC_MEMBER_NAMESPACE                                                   = "Namespace";                                                          //$NON-NLS-1$
    private static final String             METHOD_NAME_GET_NAMESPACE                                                 = "getNamespace";                                                       //$NON-NLS-1$

    private final FaceletLibraryClassTagLib _model;
    private final ProxyFactoryRegistry      _registry;
    private final AtomicBoolean             _isInitialized                                                            = new AtomicBoolean(
                                                                                                                              false);

    private String                          _uri;
    private BeanProxyWrapper                _classTypeWrapper;
    private Map<String, TagDefn>            _tags;

    /**
     * @param registry
     * @param model
     */
    public LibraryClassBasedTagRecord(final ProxyFactoryRegistry registry,
            final FaceletLibraryClassTagLib model)
    {
        _registry = registry;
        _model = model;
    }

    public void initURI() throws CoreException
    {
        if (_isInitialized.get())
        {
            throw new CoreException(
                    new Status(
                            IStatus.ERROR,
                            JSFCorePlugin.PLUGIN_ID,
                            "Cannot initURI once the library is initialized for: " + _model.getLibraryClass())); //$NON-NLS-1$
        }

        final IBeanTypeProxy libFactoryTypeProxy = _registry
                .getBeanTypeProxyFactory().getBeanTypeProxy(
                        _model.getLibraryClass());

        if (libFactoryTypeProxy == null)
        {
            throw new CoreException(new Status(IStatus.ERROR,
                    JSFCorePlugin.PLUGIN_ID,
                    "Couldn't find type proxy for " + _model.getLibraryClass())); //$NON-NLS-1$
        }

        _classTypeWrapper = new BeanProxyWrapper(libFactoryTypeProxy);

        try
        {
            _classTypeWrapper.init();
        }
        catch (final ProxyException e)
        {
            throw new CoreException(new Status(IStatus.ERROR,
                    JSFCorePlugin.PLUGIN_ID,
                    "Couldn't load class: " + _model.getLibraryClass(), e)); //$NON-NLS-1$
        }

        final String namespace = resolveNS(_classTypeWrapper);
        System.out.println(namespace);

        if (namespace == null)
        {
            throw new CoreException(new Status(IStatus.ERROR,
                    JSFCorePlugin.PLUGIN_ID,
                    "Couldn't load uri: " + _model.getLibraryClass())); //$NON-NLS-1$

        }
        _uri = namespace;
    }

    @Override
    public synchronized TagDefn getTag(final String name)
    {
        initializeIfNecessary();
        return _tags.get(name);
    }

    @Override
    public synchronized Collection<? extends TagDefn> getTags()
    {
        initializeIfNecessary();
        return Collections.unmodifiableCollection(_tags.values());
    }

    private void initializeIfNecessary()
    {
        if (_isInitialized.compareAndSet(false, true))
        {
            if (_tags == null)
            {
                _tags = resolveTags();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.FaceletTagRecord
     * #getURI()
     */
    @Override
    public String getURI()
    {
        return _uri;
    }

    private Map<String, TagDefn> resolveTags()
    {
        final Map<String, TagDefn> tags = new HashMap<String, TagDefn>();

        // if the tag factory is a child of AbstractTagFactory, then we
        // can try to get our hands on its private parts ...
        final IBeanTypeProxy mapTypeProxy = _registry.getBeanTypeProxyFactory()
                .getBeanTypeProxy(QUALIFIED_CLASS_NAME_JAVA_UTIL_MAP);
        final IBeanTypeProxy componentFactoryTypeProxy = _registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        QUALIFIED_CLASS_NAME__COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY);

        if (mapTypeProxy != null && componentFactoryTypeProxy != null)
        {
            final IFieldProxy fieldProxy = componentFactoryTypeProxy
                    .getDeclaredFieldProxy(PROPERTY_NAME_FACTORIES);

            if (fieldProxy != null)
            {
                if (fieldProxy.getFieldType().isKindOf(mapTypeProxy))
                {
                    IBeanProxy factories = null;

                    try
                    {
                        // need to turn off security checking on the private
                        // field
                        fieldProxy.setAccessible(true);
                        factories = fieldProxy.get(_classTypeWrapper
                                .getInstance());
                    }
                    catch (final ThrowableProxy e)
                    {
                        FaceletCorePlugin.log(
                                "Error getting factories from bean instance", //$NON-NLS-1$
                                e);
                    }

                    if (factories != null)
                    {
                        final IMethodProxy entrySetMethod = fieldProxy
                                .getFieldType().getMethodProxy(
                                        METHOD_NAME_ENTRY_SET);
                        if (entrySetMethod != null)
                        {
                            try
                            {
                                entrySetMethod.setAccessible(true);
                                final IBeanProxy entrySetProxy = entrySetMethod
                                        .invoke(factories);

                                if (entrySetProxy != null)
                                {
                                    final IMethodProxy iteratorMethod = entrySetProxy
                                            .getTypeProxy().getMethodProxy(
                                                    METHOD_NAME_ITERATOR);
                                    iteratorMethod.setAccessible(true);
                                    final IBeanProxy iteratorProxy = iteratorMethod
                                            .invoke(entrySetProxy);

                                    if (iteratorProxy != null)
                                    {
                                        final IMethodProxy nextMethod = iteratorProxy
                                                .getTypeProxy().getMethodProxy(
                                                        METHOD_NAME_NEXT);
                                        nextMethod.setAccessible(true);
                                        final IMethodProxy hasNextMethod = iteratorProxy
                                                .getTypeProxy().getMethodProxy(
                                                        METHOD_NAME_HAS_NEXT);
                                        hasNextMethod.setAccessible(true);

                                        while (((IBooleanBeanProxy) hasNextMethod
                                                .invoke(iteratorProxy))
                                                .booleanValue())
                                        {
                                            final IBeanProxy entryProxy = nextMethod
                                                    .invoke(iteratorProxy);
                                            final IMethodProxy getKeyProxy = entryProxy
                                                    .getTypeProxy()
                                                    .getMethodProxy(
                                                            METHOD_NAME_GET_KEY);
                                            final IMethodProxy getValueProxy = entryProxy
                                                    .getTypeProxy()
                                                    .getMethodProxy(
                                                            METHOD_NAME_GET_VALUE);
                                            if (getKeyProxy != null
                                                    && getValueProxy != null)
                                            {
                                                getKeyProxy.setAccessible(true);
                                                final IBeanProxy key = getKeyProxy
                                                        .invoke(entryProxy);

                                                if (key instanceof IStringBeanProxy)
                                                {
                                                    final String name = ((IStringBeanProxy) key)
                                                            .stringValue();
                                                    getValueProxy
                                                            .setAccessible(true);
                                                    final IBeanProxy value = getValueProxy
                                                            .invoke(entryProxy);

                                                    if (value != null)
                                                    {
                                                        final TagDefn tagDefn = createTagDefn(
                                                                name,
                                                                value);
                                                        if (tagDefn != null)
                                                        {
                                                            tags.put(name,
                                                                    tagDefn);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            catch (final ThrowableProxy e)
                            {
                                FaceletCorePlugin.log(
                                        "Error invoking entrySet", e); //$NON-NLS-1$
                            }
                        }
                    }

                }
            }
        }
        return tags;
    }

    private TagDefn createTagDefn(final String name,
            final IBeanProxy handlerValueProxy)
    {
        final IBeanTypeProxy handlerTypeProxy = handlerValueProxy.getTypeProxy();
        final FaceletTaglibFactory TAGDEFN_FACTORY = FaceletTaglibFactory.eINSTANCE;

        final IBeanTypeProxy componentHandlerFactory = _registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_COMPONENT_HANDLER_FACTORY);
        final IBeanTypeProxy userComponentHandlerFactory = _registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_USER_COMPONENT_HANDLER_FACTORY);
        final IBeanTypeProxy validatorHandlerFactory = _registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_VALIDATOR_HANDLER_FACTORY);
        final IBeanTypeProxy userValidatorHandlerFactory = _registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_USER_VALIDATOR_HANDLER_FACTORY);
        final IBeanTypeProxy converterHandlerFactory = _registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_CONVERTER_HANDLER_FACTORY);
        final IBeanTypeProxy userConverterHandlerFactory = _registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_USER_CONVERTER_HANDLER_FACTORY);
        final IBeanTypeProxy handlerFactory = _registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_HANDLER_FACTORY);
        final IBeanTypeProxy userTagFactory = _registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_HANDLER_FACTORY);

        TagDefn tagDefn = null;

        if (handlerTypeProxy.isKindOf(componentHandlerFactory)
                || handlerTypeProxy.isKindOf(userComponentHandlerFactory))
        {
            final IFieldProxy componentTypeProxy = handlerTypeProxy
                    .getDeclaredFieldProxy(FIELD_NAME_COMPONENT_TYPE);
            final IFieldProxy rendererTypeProxy = handlerTypeProxy
                    .getDeclaredFieldProxy(FIELD_NAME_RENDER_TYPE);
            try
            {
                if (componentTypeProxy != null)
                {
                    componentTypeProxy.setAccessible(true);
                    rendererTypeProxy.setAccessible(true);
                    final IBeanProxy componentType = componentTypeProxy
                            .get(handlerValueProxy);
                    // final IBeanProxy rendererType = rendererTypeProxy
                    // .get(handlerValueProxy);

                    // render type is optional, but must have component type
                    if (componentType instanceof IStringBeanProxy)
                    {
                        final String componentTypeValue = getMeaningfulString(((IStringBeanProxy) componentType)
                                .stringValue());

                        if (componentTypeValue != null)
                        {
                            final ComponentTagDefn compTagDefn = TAGDEFN_FACTORY
                                    .createComponentTagDefn();
                            compTagDefn.setComponentType(componentTypeValue);
                            // if (rendererType instanceof IStringBeanProxy)
                            // {
                            // compTagDefn
                            // .setRendererType(getMeaningfulString(((
                            // IStringBeanProxy) rendererType)
                            // .stringValue()));
                            // }
                            tagDefn = compTagDefn;
                        }
                    }
                }
            }
            catch (final ThrowableProxy e)
            {
                FaceletCorePlugin.log("Error get component info", e); //$NON-NLS-1$
            }
        }
        else if (handlerTypeProxy.isKindOf(validatorHandlerFactory)
                || handlerTypeProxy.isKindOf(userValidatorHandlerFactory))
        {
            final IFieldProxy validatorIdProxy = handlerTypeProxy
                    .getDeclaredFieldProxy(FIELD_NAME_VALIDATOR_ID);

            try
            {
                if (validatorIdProxy != null)
                {
                    validatorIdProxy.setAccessible(true);
                    final IBeanProxy validatorId = validatorIdProxy
                            .get(handlerValueProxy);

                    final ValidatorTagDefn valTagDefn = TAGDEFN_FACTORY
                            .createValidatorTagDefn();
                    tagDefn = valTagDefn;

                    if (validatorId instanceof IStringBeanProxy)
                    {
                        final String validatorIdValue = getMeaningfulString(((IStringBeanProxy) validatorId)
                                .stringValue());

                        if (validatorIdValue != null)
                        {
                            valTagDefn.setValidatorId(validatorIdValue);
                        }
                    }
                }
            }
            catch (final ThrowableProxy e)
            {
                FaceletCorePlugin.log("Error getting validator info", e); //$NON-NLS-1$
            }
        }
        else if (handlerTypeProxy.isKindOf(converterHandlerFactory)
                || handlerTypeProxy.isKindOf(userConverterHandlerFactory))
        {
            final IFieldProxy converterIdProxy = handlerTypeProxy
                    .getDeclaredFieldProxy(FIELD_NAME_CONVERTER_ID);

            try
            {
                if (converterIdProxy != null)
                {
                    converterIdProxy.setAccessible(true);
                    final IBeanProxy converterId = converterIdProxy
                            .get(handlerValueProxy);

                    final ConverterTagDefn converterTagDefn = TAGDEFN_FACTORY
                            .createConverterTagDefn();
                    tagDefn = converterTagDefn;

                    if (converterId instanceof IStringBeanProxy)
                    {
                        final String converterIdValue = getMeaningfulString(((IStringBeanProxy) converterId)
                                .stringValue());

                        if (converterIdValue != null)
                        {
                            converterTagDefn.setConverterId(converterIdValue);
                        }
                    }
                }
            }
            catch (final ThrowableProxy e)
            {
                FaceletCorePlugin.log("Error getting validator info", e); //$NON-NLS-1$
            }
        }
        else if (handlerTypeProxy.isKindOf(handlerFactory)
                || handlerTypeProxy.isKindOf(userTagFactory))
        {
            tagDefn = TAGDEFN_FACTORY.createHandlerTagDefn();
        }

        if (tagDefn != null)
        {
            tagDefn.setName(name);
        }
        return tagDefn;
    }

    private String getMeaningfulString(final String value)
    {
        if (value == null)
        {
            return null;
        }

        String retValue = value.trim();

        if ("".equals(retValue)) //$NON-NLS-1$
        {
            retValue = null;
        }
        return retValue;
    }

    private String resolveNS(final BeanProxyWrapper beanProxy)
    {
        IBeanProxy resultProxy = null;
        try
        {
            resultProxy = beanProxy.call(METHOD_NAME_GET_NAMESPACE);

            if (resultProxy instanceof IStringBeanProxy)
            {
                return ((IStringBeanProxy) resultProxy).stringValue();
            }
        }
        catch (final BeanProxyUtil.ProxyException e)
        {
            // fall through
        }

        return resolveNSAggressively(beanProxy);
    }

    private String resolveNSAggressively(final BeanProxyWrapper beanProxy)
    {
        try
        {
            return beanProxy.getStringFieldValue(STATIC_MEMBER_NAMESPACE);
        }
        catch (final ProxyException e)
        {
            // fall through
        }
        return null;
    }

    public synchronized int getNumTags()
    {
        initializeIfNecessary();
        return _tags.size();
    }
}
