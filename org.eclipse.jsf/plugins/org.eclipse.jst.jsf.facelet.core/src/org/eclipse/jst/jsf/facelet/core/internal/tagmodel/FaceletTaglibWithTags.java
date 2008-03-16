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
    private final Map<String, ITagElement> _tags;

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
        return "Facelet Tag Library with Tag/Function Definitions";
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
