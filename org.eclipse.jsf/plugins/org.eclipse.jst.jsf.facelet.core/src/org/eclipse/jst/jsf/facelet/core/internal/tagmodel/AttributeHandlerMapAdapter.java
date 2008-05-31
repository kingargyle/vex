/*******************************************************************************
 * Copyright (c) 2008 Oracle Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Cameron Bateman - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.jsf.facelet.core.internal.tagmodel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.ITagAttributeHandler;
import org.eclipse.jst.jsf.core.internal.JSFCorePlugin;
import org.eclipse.jst.jsf.designtime.internal.view.model.jsp.IAttributeAdvisor;
import org.eclipse.jst.jsf.designtime.internal.view.model.jsp.IAttributeAdvisor.UnknownAttributeException;
import org.eclipse.jst.jsf.facelet.core.internal.cm.ExternalTagInfo;
import org.eclipse.wst.xml.core.internal.contentmodel.CMAttributeDeclaration;
import org.eclipse.wst.xml.core.internal.contentmodel.CMNode;

/**
 * TODO: merge back with common elements of AttributeHandlerMapAdapter
 * 
 * Adapts TLDDocument attributes to a simple map of ITagAttributeHandler. Map is
 * unmodifiable.
 * 
 * @author cbateman
 * 
 */
public class AttributeHandlerMapAdapter implements
        Map<String, ITagAttributeHandler>, Serializable
{
    /**
     * 
     */
    private static final long                       serialVersionUID = -6052662048278098351L;
    private transient final IAttributeAdvisor       _advisor;
    private transient AtomicBoolean                 _isInitialized   = new AtomicBoolean(
                                                                             false);
    private final transient ExternalTagInfo         _tagInfo;
    private final Map<String, ITagAttributeHandler> _cache;
    private final String                            _tagName;

    /**
     * @param tagInfo
     * @param advisor
     * @param tagName
     */
    public AttributeHandlerMapAdapter(final ExternalTagInfo tagInfo,
            final IAttributeAdvisor advisor, final String tagName)
    {
        _tagInfo = tagInfo;
        _advisor = advisor;
        _tagName = tagName;
        _cache = new HashMap<String, ITagAttributeHandler>();
    }

    public boolean containsKey(final Object key)
    {
        ensureAllAttributes();
        return _cache.containsKey(key);
    }

    public boolean containsValue(final Object value)
    {
        ensureAllAttributes();
        return _cache.containsValue(value);
    }

    public Set<java.util.Map.Entry<String, ITagAttributeHandler>> entrySet()
    {
        ensureAllAttributes();
        return _cache.entrySet();
    }

    public ITagAttributeHandler get(final Object key)
    {
        if (key instanceof String)
        {
            return getOrCreateAttribute((String) key);
        }
        return null;
    }

    public boolean isEmpty()
    {
        return size() == 0;
    }

    public Set<String> keySet()
    {
        ensureAllAttributes();
        return Collections.unmodifiableSet(_cache.keySet());
    }

    public int size()
    {
        if (_tagInfo != null)
        {
            return _tagInfo.getAttributes(_tagName).getLength();
        }
        return _cache.size();
    }

    public Collection<ITagAttributeHandler> values()
    {
        ensureAllAttributes();
        return Collections.unmodifiableCollection(_cache.values());
    }

    private synchronized ITagAttributeHandler getOrCreateAttribute(
            final String name)
    {
        ITagAttributeHandler tagAttr = _cache.get(name);

        if (tagAttr == null)
        {
            try
            {
                tagAttr = _advisor.createAttributeHandler(name);
                _cache.put(name, tagAttr);
            }
            catch (final UnknownAttributeException e)
            {
                JSFCorePlugin.log(e, "Trying to get attribute for " + name); //$NON-NLS-1$
            }
        }

        return tagAttr;
    }

    private void ensureAllAttributes()
    {
        if (_isInitialized.compareAndSet(false, true))
        {
            for (final Iterator<?> it = _tagInfo.getAttributes(_tagName)
                    .iterator(); it.hasNext();)
            {
                final CMNode attrDecl = (CMAttributeDeclaration) it.next();
                getOrCreateAttribute(attrDecl.getNodeName());
            }
        }
    }

    public void clear()
    {
        throw new UnsupportedOperationException("Cannot modify map"); //$NON-NLS-1$
    }

    public ITagAttributeHandler put(final String key,
            final ITagAttributeHandler value)
    {
        throw new UnsupportedOperationException("Cannot modify map"); //$NON-NLS-1$
    }

    public void putAll(
            final Map<? extends String, ? extends ITagAttributeHandler> t)
    {
        throw new UnsupportedOperationException("Cannot modify map"); //$NON-NLS-1$
    }

    public ITagAttributeHandler remove(final Object key)
    {
        throw new UnsupportedOperationException("Cannot modify map"); //$NON-NLS-1$
    }

    private void readObject(final ObjectInputStream in) throws IOException,
            ClassNotFoundException
    {
        in.defaultReadObject();
        _isInitialized = new AtomicBoolean(true);
    }

    private void writeObject(final ObjectOutputStream out) throws IOException
    {
        ensureAllAttributes();
        out.defaultWriteObject();
    }
}
