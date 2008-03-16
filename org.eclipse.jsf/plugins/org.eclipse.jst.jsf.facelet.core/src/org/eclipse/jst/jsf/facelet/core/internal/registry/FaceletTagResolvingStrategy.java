package org.eclipse.jst.jsf.facelet.core.internal.registry;

import org.eclipse.core.resources.IProject;
import org.eclipse.jem.internal.proxy.core.IBeanProxy;
import org.eclipse.jem.internal.proxy.core.IBeanTypeProxy;
import org.eclipse.jem.internal.proxy.core.IConfigurationContributor;
import org.eclipse.jem.internal.proxy.core.IFieldProxy;
import org.eclipse.jem.internal.proxy.core.IStringBeanProxy;
import org.eclipse.jem.internal.proxy.core.ProxyFactoryRegistry;
import org.eclipse.jem.internal.proxy.core.ThrowableProxy;
import org.eclipse.jst.jsf.common.runtime.internal.model.component.ComponentTypeInfo;
import org.eclipse.jst.jsf.common.runtime.internal.model.decorator.ConverterTypeInfo;
import org.eclipse.jst.jsf.common.runtime.internal.model.decorator.ValidatorTypeInfo;
import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.ITagElement;
import org.eclipse.jst.jsf.designtime.internal.view.DTComponentIntrospector;
import org.eclipse.jst.jsf.designtime.internal.view.model.jsp.AbstractTagResolvingStrategy;
import org.eclipse.jst.jsf.facelet.core.internal.FaceletCorePlugin;
import org.eclipse.jst.jsf.facelet.core.internal.tagmodel.ComponentTag;
import org.eclipse.jst.jsf.facelet.core.internal.tagmodel.ConverterTag;
import org.eclipse.jst.jsf.facelet.core.internal.tagmodel.FaceletTag;
import org.eclipse.jst.jsf.facelet.core.internal.tagmodel.NoArchetypeFaceletTag;
import org.eclipse.jst.jsf.facelet.core.internal.tagmodel.ValidatorTag;

/*package*/class FaceletTagResolvingStrategy extends
        AbstractTagResolvingStrategy<FaceletTagElement, String>
{
    public final static String ID = "org.eclipse.jst.jsf.facelet.core.FaceletTagResolvingStrategy";
    private final IProject     _project;

    public FaceletTagResolvingStrategy(final IProject project)
    {
        _project = project;
    }

    @Override
    public final String getId()
    {
        return ID;
    }

    @Override
    public final ITagElement resolve(FaceletTagElement element)
    {
        return createFaceletTag(element.getUri(), element.getName(), element
                .getFactory(), element.getRegistry());
    }

    public final String getDisplayName()
    {
        return "Facelet Introspecting Tag Resolver";
    }

    private FaceletTag createFaceletTag(final String uri, final String name,
            final IBeanProxy factory, final ProxyFactoryRegistry registry)
    {
        final IBeanTypeProxy componentHandlerFactory = registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        "com.sun.facelets.tag.AbstractTagLibrary$ComponentHandlerFactory");
        final IBeanTypeProxy userComponentHandlerFactory = registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        "com.sun.facelets.tag.AbstractTagLibrary$UserComponentHandlerFactory");
        final IBeanTypeProxy validatorHandlerFactory = registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        "com.sun.facelets.tag.AbstractTagLibrary$ValidatorHandlerFactory");
        final IBeanTypeProxy userValidatorHandlerFactory = registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        "com.sun.facelets.tag.AbstractTagLibrary$UserValidatorHandlerFactory");
        final IBeanTypeProxy converterHandlerFactory = registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        "com.sun.facelets.tag.AbstractTagLibrary$ConverterHandlerFactory");
        final IBeanTypeProxy userConverterHandlerFactory = registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        "com.sun.facelets.tag.AbstractTagLibrary$UserConverterHandlerFactory");
        final IBeanTypeProxy handlerFactory = registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        "com.sun.facelets.tag.AbstractTagLibrary$HandlerFactory");
        final IBeanTypeProxy userTagFactory = registry
                .getBeanTypeProxyFactory()
                .getBeanTypeProxy(
                        "com.sun.facelets.tag.AbstractTagLibrary$HandlerFactory");

        if (factory.getTypeProxy().isKindOf(componentHandlerFactory)
                || factory.getTypeProxy().isKindOf(userComponentHandlerFactory))
        {
            final IFieldProxy componentTypeProxy = factory.getTypeProxy()
                    .getDeclaredFieldProxy("componentType");
            final IFieldProxy rendererTypeProxy = factory.getTypeProxy()
                    .getDeclaredFieldProxy("renderType");

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
                            return new ComponentTag(uri, name, typeInfo);
                        }
                    }
                }
            }
            catch (final ThrowableProxy e)
            {
                FaceletCorePlugin.log("Error get component info", e);
            }
        }
        else if (factory.getTypeProxy().isKindOf(validatorHandlerFactory)
                || factory.getTypeProxy().isKindOf(userValidatorHandlerFactory))
        {
            final IFieldProxy validatorIdProxy = factory.getTypeProxy()
                    .getDeclaredFieldProxy("validatorId");

            try
            {
                validatorIdProxy.setAccessible(true);
                if (validatorIdProxy != null)
                {
                    final IBeanProxy validatorId = validatorIdProxy
                            .get(factory);

                    // render type is optional, but must have component type
                    if (validatorId instanceof IStringBeanProxy)
                    {
                        return new ValidatorTag(uri, name,
                                ValidatorTypeInfo.UNKNOWN,
                                null);
                    }
                }
            }
            catch (final ThrowableProxy e)
            {
                FaceletCorePlugin.log("Error getting validator info", e);
            }
        }
        else if (factory.getTypeProxy().isKindOf(converterHandlerFactory)
                || factory.getTypeProxy().isKindOf(userConverterHandlerFactory))
        {
            final IFieldProxy converterIdProxy = factory.getTypeProxy()
                    .getDeclaredFieldProxy("converterId");

            try
            {
                converterIdProxy.setAccessible(true);
                if (converterIdProxy != null)
                {
                    final IBeanProxy converterId = converterIdProxy
                            .get(factory);

                    // render type is optional, but must have component type
                    if (converterId instanceof IStringBeanProxy)
                    {
                        // for now, all converters are unknown
                        return new ConverterTag(uri, name,
                                ConverterTypeInfo.UNKNOWN,
                                null);
                    }
                }
            }
            catch (final ThrowableProxy e)
            {
                FaceletCorePlugin.log("Error getting validator info", e);
            }
        }
        else if (factory.getTypeProxy().isKindOf(handlerFactory)
                || factory.getTypeProxy().isKindOf(userTagFactory))
        {
            return new NoArchetypeFaceletTag(uri, name);
        }
        return null;
    }
}
