package org.eclipse.jst.jsf.facelet.core.internal.tagmodel;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.ITagElement;

/**
 * A facelet tag library using only a library class to specify its runtime
 * tag/component mappings
 * 
 * @author cbateman
 * 
 */
public final class FaceletTaglibWithLibraryClass extends FaceletTaglib
{
    /**
     * 
     */
    private static final long              serialVersionUID = -7747289728027784505L;
    private final String                   _libraryClassName;
    private final Map<String, ITagElement> _tags;

    /**
     * @param namespace
     * @param libraryClassName
     * @param tags
     */
    public FaceletTaglibWithLibraryClass(final String namespace,
            final String libraryClassName, final Map<String, ITagElement> tags)
    {
        super(namespace);
        _libraryClassName = libraryClassName;

        if (tags == null)
        {
            _tags = Collections.emptyMap();
        }
        else
        {
            _tags = tags;
        }
    }

    /**
     * @param copyMe
     * @param namespace
     */
    public FaceletTaglibWithLibraryClass(
            final FaceletTaglibWithLibraryClass copyMe, final String namespace)
    {
        super(namespace);
        _libraryClassName = copyMe._libraryClassName;
        _tags = new HashMap<String, ITagElement>(copyMe._tags);
    }

    /**
     * @return the library class name
     */
    public final String getLibraryClassName()
    {
        return _libraryClassName;
    }

    @Override
    public String toString()
    {
        String toString = super.toString();
        toString += "Library Class Name: " + _libraryClassName + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
        return toString;
    }

    @Override
    public String getLibraryTypeDescription()
    {
        return Messages.FaceletTaglibWithLibraryClass_TAG_LIBRARY_TYPE_DESCRIPTION;
    }

    @Override
    public String getDisplayName()
    {
        return (getNSUri() != null ? getNSUri() : _libraryClassName);
    }

    @Override
    public Collection<? extends ITagElement> getViewElements()
    {
        return _tags.values();
    }

    @Override
    public ITagElement getViewElement(final String name)
    {
        return _tags.get(name);
    }

    @Override
    public boolean hasViewElements()
    {
        return _tags.size() > 0;
    }

    @Override
    public boolean isInitialized()
    {
        return true;
    }
}
