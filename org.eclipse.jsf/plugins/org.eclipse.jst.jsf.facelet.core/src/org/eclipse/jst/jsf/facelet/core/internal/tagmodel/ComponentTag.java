package org.eclipse.jst.jsf.facelet.core.internal.tagmodel;

import org.eclipse.jst.jsf.common.runtime.internal.model.component.ComponentTypeInfo;
import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.IComponentTagElement;




public class ComponentTag extends FaceletTag implements IComponentTagElement
{
    private final String _rendererType; // may be null
    private final String _componentType;
    private final ComponentTypeInfo _typeInfo;

    public ComponentTag(final String uri, final String name, final String componentType)
    {
        // renderType and handlerClass are (?) in the dtd
        this(uri, name, componentType, null, null, null);
    }

    public ComponentTag(final String uri,
            final String name,
            final String componentType,
            final String rendererType,
            final String handlerClass,
            final ComponentTypeInfo typeInfo)
    {
        super(uri, name, TagType.COMPONENT, handlerClass);
        _componentType = componentType;
        _rendererType = rendererType;
        _typeInfo = typeInfo;
    }

    public String getComponentType() {
        return _componentType;
    }

    /**
     * @return the renderer type or null if none provided
     */
    public String getRendererType() {
        return _rendererType;
    }

    @Override
    public String toString() {
        String toString = super.toString();
        toString += "; Component Type: " + getComponentType();

        final String rendererType = getRendererType();
        if (rendererType != null)
        {
            toString += "; Renderer Type: " + rendererType;
        }

        final String handlerClass = getTagHandlerClassName();
        if (handlerClass != null)
        {
            toString += "; Handler Class: " + handlerClass;
        }

        return toString;
    }

    public ComponentTypeInfo getComponent()
    {
        return _typeInfo;
    }


}
