package org.eclipse.jst.jsf.facelet.core.internal.tagmodel;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.ITagElement;


/**
 * @author cbateman
 *
 */
public class FaceletTaglibWithTags extends FaceletTaglib
{
    /**
     * 
     */
    private static final long serialVersionUID = 5754003734420972494L;
    private final Map<String, ITagElement> _tags;
    private boolean                        _isInitialized; // = false

    /**
     * @param namespace
     * @param tags
     */
    protected FaceletTaglibWithTags(final String namespace, final Map<String, ITagElement> tags/*, List<FaceletFunction> functions*/)
    {
        super(namespace);
        _tags = tags;
    }

    private void addFaceletTag(final String name, final FaceletTag tag)
    {
        _tags.put(name, tag);
    }

    static class WorkingCopy extends FaceletTaglibWithTags
    {
        /**
         * 
         */
        private static final long serialVersionUID = -6792400224772536931L;

        protected WorkingCopy(final String namespace) {
            super(namespace, new HashMap<String, ITagElement>());
        }

        public void addFaceletTag(final FaceletTag tag)
        {
            super.addFaceletTag(tag.getName(), tag);
        }

        public FaceletTaglibWithTags closeWorkingCopy()
        {
            setInitialized();
            return this;
        }

//        public FaceletTaglibWithTags shallowCopy()
//        {
//            return new FaceletTaglibWithTags(getNSUri(), super.internalGetTags());
//        }
    }

    @Override
    public String toString()
    {
        String toString =  super.toString();

        for (final ITagElement tag : getViewElements())
        {
            toString += tag.toString();
        }

        return toString;
    }

    @Override
    public String getLibraryTypeDescription()
    {
        return Messages.FaceletTaglibWithTags_TAG_LIBRARY_WITH_TAGS_TYPE_DESCRIPTION;
    }

    @Override
    public String getDisplayName()
    {
        return getNSUri();
    }

    @Override
    public Collection<? extends ITagElement> getViewElements() {
        return Collections.unmodifiableCollection(_tags.values());
    }

    @Override
    public ITagElement getViewElement(String name)
    {
        return _tags.get(name);
    }

    @Override
    public boolean hasViewElements()
    {
        return !_tags.isEmpty();
    }

    @Override
    public boolean isInitialized()
    {
        return _isInitialized;
    }
    
    /**
     * 
     */
    protected final void setInitialized()
    {
        //latch only 
        if (!_isInitialized)
        {
            _isInitialized = true;
        }
    }
}
