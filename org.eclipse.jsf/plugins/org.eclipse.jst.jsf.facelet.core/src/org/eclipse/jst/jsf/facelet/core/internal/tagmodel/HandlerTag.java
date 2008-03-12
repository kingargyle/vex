package org.eclipse.jst.jsf.facelet.core.internal.tagmodel;


public class HandlerTag extends FaceletTag
{
    public HandlerTag(final String uri, final String name, final String handlerClassName) {
        super(uri, name, TagType.HANDLER, handlerClassName);
    }

    @Override
    public String toString()
    {
        String toString = super.toString();

        toString += "Handler Class: " + getTagHandlerClassName() + "\n";

        return toString;
    }


}
