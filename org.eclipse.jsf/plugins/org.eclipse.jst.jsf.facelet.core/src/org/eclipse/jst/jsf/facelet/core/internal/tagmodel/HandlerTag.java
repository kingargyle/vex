package org.eclipse.jst.jsf.facelet.core.internal.tagmodel;

import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.IHandlerTagElement;


public class HandlerTag extends FaceletTag
{
    private final IHandlerTagElement.TagHandlerType _handlerType;
    public HandlerTag(final String uri, final String name, final IHandlerTagElement.TagHandlerType handlerType,
            final String handlerClassName) {
        super(uri, name, TagType.HANDLER, handlerClassName);
        _handlerType = handlerType;
    }

    public IHandlerTagElement.TagHandlerType getHandlerType()
    {
        return _handlerType;
    }

    @Override
    public String toString()
    {
        String toString = super.toString();

        toString += "Handler Class: " + getTagHandlerClassName() + "\n";

        return toString;
    }


}
