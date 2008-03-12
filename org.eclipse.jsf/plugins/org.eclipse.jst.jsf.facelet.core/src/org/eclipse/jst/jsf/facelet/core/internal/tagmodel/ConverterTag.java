package org.eclipse.jst.jsf.facelet.core.internal.tagmodel;


public class ConverterTag extends FaceletTag
{
    private final String    _converter;
    private final String    _handler; // may be null

    public ConverterTag(final String uri, final String name, final String converter, final String handler)
    {
        super(uri, name, TagType.CONVERTER, null);
        _converter = converter;
        _handler = handler;
    }

    /**
     * @return the converter id
     */
    public String getConverter()
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
