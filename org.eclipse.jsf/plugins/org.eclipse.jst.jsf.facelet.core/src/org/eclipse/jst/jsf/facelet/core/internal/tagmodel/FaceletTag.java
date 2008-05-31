package org.eclipse.jst.jsf.facelet.core.internal.tagmodel;

import java.util.Map;

import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.IJSFTagElement;
import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.TagElement;
import org.eclipse.jst.jsf.designtime.internal.view.model.jsp.IAttributeAdvisor;
import org.eclipse.jst.jsf.facelet.core.internal.cm.ExternalTagInfo;
import org.eclipse.jst.jsf.facelet.core.internal.cm.FaceletDocumentFactory;

/**
 * A description of the a facelet tag
 * 
 * @author cbateman
 * 
 */
public abstract class FaceletTag extends TagElement implements IJSFTagElement
{
    /**
     * 
     */
    private static final long                    serialVersionUID = 3027895246947365781L;
    private final String                         _uri;
    private final String                         _name;
    private final TagType                        _type;
    private final String                         _tagHandlerClass;
    private final AttributeHandlerMapAdapter     _attributeHandlerMapAdapter;

    /**
     * @param uri
     * @param name
     * @param type
     * @param tagHandlerClassName
     * @param docFactory
     * @param advisor
     */
    protected FaceletTag(final String uri, final String name,
            final TagType type, final String tagHandlerClassName,
            final FaceletDocumentFactory docFactory,
            final IAttributeAdvisor advisor)
    {
        _uri = uri;
        _name = name;
        _type = type;
        _tagHandlerClass = tagHandlerClassName;
        final ExternalTagInfo tagInfo = docFactory.getOrCreateExtraTagInfo(uri);
        _attributeHandlerMapAdapter = new AttributeHandlerMapAdapter(tagInfo, advisor, name);
    }

    /**
     * @return the name of the tag
     */
    @Override
    public final String getName()
    {
        return _name;
    }

    public final TagType getType()
    {
        return _type;
    }

    @Override
    public String getUri()
    {
        return _uri;
    }

    @Override
    public String getTagHandlerClassName()
    {
        return _tagHandlerClass;
    }

    @Override
    public String toString()
    {
        return "Tag Name: " + getName() + "Tag Type: " + getType(); //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    public Map<?, ?> getAttributeHandlers()
    {
        return _attributeHandlerMapAdapter;
    }

}
