package org.eclipse.jst.jsf.facelet.core.internal.registry;

import org.eclipse.core.resources.IProject;
import org.eclipse.jem.internal.proxy.core.IBeanProxy;
import org.eclipse.jem.internal.proxy.core.IBeanTypeProxy;
import org.eclipse.jem.internal.proxy.core.IConfigurationContributor;
import org.eclipse.jem.internal.proxy.core.IFieldProxy;
import org.eclipse.jem.internal.proxy.core.IStringBeanProxy;
import org.eclipse.jem.internal.proxy.core.ProxyFactoryRegistry;
import org.eclipse.jem.internal.proxy.core.ThrowableProxy;
import org.eclipse.jst.jsf.common.dom.TagIdentifier;
import org.eclipse.jst.jsf.common.runtime.internal.model.component.ComponentTypeInfo;
import org.eclipse.jst.jsf.common.runtime.internal.model.decorator.ConverterTypeInfo;
import org.eclipse.jst.jsf.common.runtime.internal.model.decorator.ValidatorTypeInfo;
import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.ITagElement;
import org.eclipse.jst.jsf.core.internal.tld.TagIdentifierFactory;
import org.eclipse.jst.jsf.designtime.internal.view.DTComponentIntrospector;
import org.eclipse.jst.jsf.designtime.internal.view.mapping.ViewMetadataLoader;
import org.eclipse.jst.jsf.designtime.internal.view.model.jsp.AbstractTagResolvingStrategy;
import org.eclipse.jst.jsf.designtime.internal.view.model.jsp.IAttributeAdvisor;
import org.eclipse.jst.jsf.facelet.core.internal.FaceletCorePlugin;
import org.eclipse.jst.jsf.facelet.core.internal.cm.FaceletDocumentFactory;
import org.eclipse.jst.jsf.facelet.core.internal.tagmodel.ComponentTag;
import org.eclipse.jst.jsf.facelet.core.internal.tagmodel.ConverterTag;
import org.eclipse.jst.jsf.facelet.core.internal.tagmodel.FaceletTag;
import org.eclipse.jst.jsf.facelet.core.internal.tagmodel.NoArchetypeFaceletTag;
import org.eclipse.jst.jsf.facelet.core.internal.tagmodel.ValidatorTag;

/*package*/class FaceletTagResolvingStrategy extends
        AbstractTagResolvingStrategy<FaceletTagElement, String>
{
    private static final String FIELD_NAME_CONVERTER_ID = "converterId"; //$NON-NLS-1$
    private static final String FIELD_NAME_VALIDATOR_ID = "validatorId"; //$NON-NLS-1$
    private static final String FIELD_NAME_RENDER_TYPE = "renderType"; //$NON-NLS-1$
    private static final String FIELD_NAME_COMPONENT_TYPE = "componentType"; //$NON-NLS-1$
    private static final String COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_HANDLER_FACTORY = "com.sun.facelets.tag.AbstractTagLibrary$HandlerFactory"; //$NON-NLS-1$
    private static final String COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_USER_CONVERTER_HANDLER_FACTORY = "com.sun.facelets.tag.AbstractTagLibrary$UserConverterHandlerFactory"; //$NON-NLS-1$
    private static final String COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_CONVERTER_HANDLER_FACTORY = "com.sun.facelets.tag.AbstractTagLibrary$ConverterHandlerFactory"; //$NON-NLS-1$
    private static final String COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_USER_VALIDATOR_HANDLER_FACTORY = "com.sun.facelets.tag.AbstractTagLibrary$UserValidatorHandlerFactory"; //$NON-NLS-1$
    private static final String COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_VALIDATOR_HANDLER_FACTORY = "com.sun.facelets.tag.AbstractTagLibrary$ValidatorHandlerFactory"; //$NON-NLS-1$
    private static final String COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_USER_COMPONENT_HANDLER_FACTORY = "com.sun.facelets.tag.AbstractTagLibrary$UserComponentHandlerFactory"; //$NON-NLS-1$
    private static final String COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_COMPONENT_HANDLER_FACTORY = "com.sun.facelets.tag.AbstractTagLibrary$ComponentHandlerFactory"; //$NON-NLS-1$
    public final static String ID = "org.eclipse.jst.jsf.facelet.core.FaceletTagResolvingStrategy"; //$NON-NLS-1$
    private final IProject     _project;
    private final FaceletDocumentFactory    _factory;
    private final ViewMetadataLoader _viewLoader;

    public FaceletTagResolvingStrategy(final IProject project,
            final FaceletDocumentFactory factory)
    {
        _project = project;
        _factory = factory;
        _viewLoader = new ViewMetadataLoader(project);
    }

    @Override
    public final String getId()
    {
        return ID;
    }

    @Override
    public final ITagElement resolve(final FaceletTagElement element)
    {
        return createFaceletTag(element.getUri(), element.getName(), element
                .getFactory(), element.getRegistry());
    }

    public final String getDisplayName()
    {
        return Messages.FaceletTagResolvingStrategy_FACELET_TAG_RESOLVER_DISPLAY_NAME;
    }

    private FaceletTag createFaceletTag(final String uri, final String name,
            final IBeanProxy factory, final ProxyFactoryRegistry registry)
    {
        final IBeanTypeProxy componentHandlerFactory = registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_COMPONENT_HANDLER_FACTORY);
        final IBeanTypeProxy userComponentHandlerFactory = registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_USER_COMPONENT_HANDLER_FACTORY);
        final IBeanTypeProxy validatorHandlerFactory = registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_VALIDATOR_HANDLER_FACTORY);
        final IBeanTypeProxy userValidatorHandlerFactory = registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_USER_VALIDATOR_HANDLER_FACTORY);
        final IBeanTypeProxy converterHandlerFactory = registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_CONVERTER_HANDLER_FACTORY);
        final IBeanTypeProxy userConverterHandlerFactory = registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_USER_CONVERTER_HANDLER_FACTORY);
        final IBeanTypeProxy handlerFactory = registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_HANDLER_FACTORY);
        final IBeanTypeProxy userTagFactory = registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY$_HANDLER_FACTORY);

        final TagIdentifier tagId = TagIdentifierFactory.createJSPTagWrapper(uri, name);
        final IAttributeAdvisor advisor = new MetadataAttributeAdvisor(tagId, _viewLoader);

        if (factory.getTypeProxy().isKindOf(componentHandlerFactory)
                || factory.getTypeProxy().isKindOf(userComponentHandlerFactory))
        {
            final IFieldProxy componentTypeProxy = factory.getTypeProxy()
                    .getDeclaredFieldProxy(FIELD_NAME_COMPONENT_TYPE);
            final IFieldProxy rendererTypeProxy = factory.getTypeProxy()
                    .getDeclaredFieldProxy(FIELD_NAME_RENDER_TYPE);
            try
            {
                if (componentTypeProxy != null)
                {
                    componentTypeProxy.setAccessible(true);
                    rendererTypeProxy.setAccessible(true);
                    final IBeanProxy componentType = componentTypeProxy
                            .get(factory);

                    // render type is optional, but must have component type
                    if (componentType instanceof IStringBeanProxy)
                    {
                        final String componentTypeValue = ((IStringBeanProxy) componentType)
                                .stringValue();

                        if (componentTypeValue != null)
                        {
                            final String componentClass = DTComponentIntrospector
                                    .findComponentClass(componentTypeValue,
                                            _project);

                            ComponentTypeInfo typeInfo = null;

                            if (componentClass != null)
                            {
                                typeInfo = DTComponentIntrospector
                                        .getComponent(componentTypeValue,
                                                componentClass, _project,
                                                new IConfigurationContributor[]
                                                { new ELProxyContributor(
                                                        _project) });
                            }
                            return new ComponentTag(uri, name, typeInfo, _factory, advisor);
                        }
                    }
                }
            }
            catch (final ThrowableProxy e)
            {
                FaceletCorePlugin.log("Error get component info", e); //$NON-NLS-1$
            }
        }
        else if (factory.getTypeProxy().isKindOf(validatorHandlerFactory)
                || factory.getTypeProxy().isKindOf(userValidatorHandlerFactory))
        {
            final IFieldProxy validatorIdProxy = factory.getTypeProxy()
                    .getDeclaredFieldProxy(FIELD_NAME_VALIDATOR_ID);

            try
            {
                if (validatorIdProxy != null)
                {
                    validatorIdProxy.setAccessible(true);
                    final IBeanProxy validatorId = validatorIdProxy
                            .get(factory);

                    // render type is optional, but must have component type
                    if (validatorId instanceof IStringBeanProxy)
                    {
                        return new ValidatorTag(uri, name,
                                ValidatorTypeInfo.UNKNOWN, null, _factory, advisor);
                    }
                }
            }
            catch (final ThrowableProxy e)
            {
                FaceletCorePlugin.log("Error getting validator info", e); //$NON-NLS-1$
            }
        }
        else if (factory.getTypeProxy().isKindOf(converterHandlerFactory)
                || factory.getTypeProxy().isKindOf(userConverterHandlerFactory))
        {
            final IFieldProxy converterIdProxy = factory.getTypeProxy()
                    .getDeclaredFieldProxy(FIELD_NAME_CONVERTER_ID);

            try
            {
                if (converterIdProxy != null)
                {
                    converterIdProxy.setAccessible(true);
                    final IBeanProxy converterId = converterIdProxy
                            .get(factory);

                    // render type is optional, but must have component type
                    if (converterId instanceof IStringBeanProxy)
                    {
                        // for now, all converters are unknown
                        return new ConverterTag(uri, name,
                                ConverterTypeInfo.UNKNOWN, null, _factory, advisor);
                    }
                }
            }
            catch (final ThrowableProxy e)
            {
                FaceletCorePlugin.log("Error getting validator info", e); //$NON-NLS-1$
            }
        }
        else if (factory.getTypeProxy().isKindOf(handlerFactory)
                || factory.getTypeProxy().isKindOf(userTagFactory))
        {
            return new NoArchetypeFaceletTag(uri, name, _factory, advisor);
        }
        return null;
    }
}
