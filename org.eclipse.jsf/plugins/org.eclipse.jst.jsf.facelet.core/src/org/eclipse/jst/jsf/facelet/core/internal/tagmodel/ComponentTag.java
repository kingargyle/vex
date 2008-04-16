package org.eclipse.jst.jsf.facelet.core.internal.tagmodel;

import org.eclipse.jst.jsf.common.runtime.internal.model.component.ComponentTypeInfo;
import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.IComponentTagElement;




public class ComponentTag extends FaceletTag implements IComponentTagElement
{
    /**
     * 
     */
    private static final long serialVersionUID = -7457091811357699617L;
    private final ComponentTypeInfo _typeInfo;

//    public ComponentTag(final String uri, final String name, final String componentType)
//    {
//        // renderType and handlerClass are (?) in the dtd
//        this(uri, name, componentType, null);
//    }

    public ComponentTag(final String uri,
            final String name,
            final ComponentTypeInfo typeInfo)
    {
        super(uri, name, TagType.COMPONENT, typeInfo.getClassName());
        _typeInfo = typeInfo;
    }

    @Override
    public String toString() {
        String toString = super.toString();
        toString += "; Component Type: " + _typeInfo.getComponentType();

        final String rendererType = _typeInfo.getRenderFamily();
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
