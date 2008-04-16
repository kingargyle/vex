package org.eclipse.jst.jsf.facelet.core.internal.tagmodel;

import org.eclipse.jst.jsf.common.runtime.internal.model.decorator.ConverterTypeInfo;
import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.IConverterTagElement;


public class ConverterTag extends FaceletTag implements IConverterTagElement
{
    /**
     * 
     */
    private static final long serialVersionUID = -5310748504219020605L;
    private final ConverterTypeInfo _converter;
    private final String            _handler;  // may be null

    public ConverterTag(final String uri, final String name, final ConverterTypeInfo converter, final String handler)
    {
        super(uri, name, TagType.CONVERTER, null);
        _converter = converter;
        _handler = handler;
    }

    /**
     * @return the converter id
     */
    public ConverterTypeInfo getConverter()
    {
        return _converter;
    }

    /**
     * @return the handler or null if none provided
     */
    public String getHandler() {
        return _handler;
    }

    @Override
    public String toString()
    {
        String toString = super.toString();

        toString += "Converter Id: "+getConverter()+"\n";

        if  (_handler != null)
        {
            toString += "Handler Class: "+getHandler()+"\n";
        }

        return toString;
    }


}
