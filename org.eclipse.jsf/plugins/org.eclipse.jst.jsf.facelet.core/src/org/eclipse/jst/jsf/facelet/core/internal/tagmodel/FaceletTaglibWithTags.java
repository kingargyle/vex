package org.eclipse.jst.jsf.facelet.core.internal.tagmodel;

import java.util.Collection;
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

    /**
     * @param namespace
     * @param tags
     */
    protected FaceletTaglibWithTags(final String namespace, final Map<String, ITagElement> tags/*, List<FaceletFunction> functions*/)
    {
        super(namespace);
        _tags = tags;
    }

    @Override
    public Map<String, ITagElement> getTags()
    {
        return internalGetTags();
    }

    private void addFaceletTag(final String name, final FaceletTag tag)
    {
        _tags.put(name, tag);
    }

    private Map<String, ITagElement> internalGetTags()
    {
        return _tags;
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
            return this;
        }

        public FaceletTaglibWithTags shallowCopy()
        {
            return new FaceletTaglibWithTags(getNSUri(), super.internalGetTags());
        }
    }

    @Override
    public String toString()
    {
        String toString =  super.toString();

        for (final ITagElement tag : getTags().values())
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
    public Collection<ITagElement> getViewElements() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ITagElement getViewElement(String name)
    {
        //TODO:
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasViewElements()
    {
      //TODO:
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isInitialized()
    {
      //TODO:
        throw new UnsupportedOperationException();
    }
}
