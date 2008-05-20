package org.eclipse.jst.jsf.facelet.core.internal.tagmodel;

import java.util.Collections;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.IJSFTagElement;
import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.TagElement;
import org.eclipse.jst.jsf.facelet.core.internal.FaceletCorePlugin;

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
    private static final long serialVersionUID = 3027895246947365781L;
    private final String  _uri;
    private final String  _name;
    private final TagType _type;
    private final String  _tagHandlerClass;

    protected FaceletTag(final String uri, final String name, final TagType type, final String tagHandlerClassName)
    {
        _uri = uri;
        _name = name;
        _type = type;
        _tagHandlerClass = tagHandlerClassName;
    }

    /**
     * @return the name of the tag
     */
    public final String getName() {
        return _name;
    }

    public final TagType getType() {
        return _type;
    }

    public String getUri()
    {
        return _uri;
    }

    public String getTagHandlerClassName() {
        return _tagHandlerClass;
    }

    @Override
    public String toString()
    {
        return "Tag Name: " + getName() + "Tag Type: " + getType();
    }

    public Map<?, ?> getAttributeHandlers()
    {
        FaceletCorePlugin.getDefault().getLog().log
            (new Status(IStatus.WARNING, FaceletCorePlugin.PLUGIN_ID, -1
                    , "Don't forget to implement this", null));
        return Collections.emptyMap();
    }
}
