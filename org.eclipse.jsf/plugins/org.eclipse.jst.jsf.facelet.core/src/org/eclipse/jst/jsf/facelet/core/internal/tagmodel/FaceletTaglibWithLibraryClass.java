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
    private final String                    _libraryClassName;
    private final Map<String, ITagElement>   _tags;

    public FaceletTaglibWithLibraryClass(final String namespace,
            final String libraryClassName,
            final Map<String, ITagElement> tags) {
        super(namespace);
        _libraryClassName = libraryClassName;

        if (tags == null)
        {
            _tags =  Collections.emptyMap();
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
    public FaceletTaglibWithLibraryClass(final FaceletTaglibWithLibraryClass copyMe,
            final String namespace) {
        super(namespace);
        _libraryClassName = copyMe._libraryClassName;
        _tags = new HashMap<String, ITagElement>(copyMe._tags);
    }

    public final String getLibraryClassName() {
        return _libraryClassName;
    }

    @Override
    public String toString() {
        String toString = super.toString();
        toString += "Library Class Name: " + _libraryClassName + "\n";
        return toString;
    }

    @Override
    public String getLibraryTypeDescription() {
        return "Facelet Tag Library With Library Class";
    }

    @Override
    public Map<String, ITagElement> getTags() {
        return Collections.unmodifiableMap(_tags);
    }

    @Override
    public String getDisplayName() {
        return (getNSUri() != null ? getNSUri() : _libraryClassName);
    }

    @Override
    public Collection<? extends ITagElement> getViewElements() {
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
