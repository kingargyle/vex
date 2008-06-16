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
package org.eclipse.jst.jsf.facelet.core.internal.registry.taglib;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.TagDefn;

abstract class FaceletTagRecord implements IFaceletTagRecord
{
    private final CopyOnWriteArrayList<ITagRecordChangeListener> _listeners;

    public FaceletTagRecord()
    {
        _listeners = new CopyOnWriteArrayList<ITagRecordChangeListener>();
    }

    public final void addListener(final ITagRecordChangeListener listener)
    {
        _listeners.addIfAbsent(listener);
    }

    public final void removeListener(final ITagRecordChangeListener listener)
    {
        _listeners.remove(listener);
    }

    protected void fireEvent(final TagRecordChangeEvent event)
    {
        for (final ITagRecordChangeListener listener : _listeners)
        {
            listener.changed(event);
        }
    }

    /**
     * 
     */
    private static final long serialVersionUID = -4606745577562951499L;

    public abstract String getURI();

    public abstract TagDefn getTag(final String name);

    public abstract Collection<? extends TagDefn> getTags();
}
