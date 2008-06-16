package org.eclipse.jst.jsf.facelet.core.internal.tagmodel;

import org.eclipse.jst.jsf.common.runtime.internal.model.decorator.ConverterTypeInfo;
import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.IConverterTagElement;
import org.eclipse.jst.jsf.designtime.internal.view.model.jsp.IAttributeAdvisor;
import org.eclipse.jst.jsf.facelet.core.internal.cm.FaceletDocumentFactory;


/**
 * A basic JSF converter facelet tag element
 * 
 * @author cbateman
 *
 */
public class ConverterTag extends FaceletTag implements IConverterTagElement
{
    /**
     * 
     */
    private static final long serialVersionUID = -5310748504219020605L;
    private final ConverterTypeInfo _converter;

    /**
     * @param uri
     * @param name
     * @param converter
     * @param handler
     * @param factory
     * @param advisor
     */
    public ConverterTag(final String uri, final String name, final ConverterTypeInfo converter, final String handler, final FaceletDocumentFactory factory, 
            final IAttributeAdvisor advisor)
    {
        super(uri, name, TagType.CONVERTER, handler, factory, advisor);
        _converter = converter;
    }

    /**
     * @return the converter id
     */
    public ConverterTypeInfo getConverter()
    {
        return _converter;
    }

    @Override
    public String toString()
    {
        String toString = super.toString();

        toString += "Converter Id: "+getConverter()+"\n"; //$NON-NLS-1$ //$NON-NLS-2$

        if  (getTagHandlerClassName() != null)
        {
            toString += "Handler Class: "+getTagHandlerClassName()+"\n"; //$NON-NLS-1$ //$NON-NLS-2$
        }

        return toString;
    }


}
