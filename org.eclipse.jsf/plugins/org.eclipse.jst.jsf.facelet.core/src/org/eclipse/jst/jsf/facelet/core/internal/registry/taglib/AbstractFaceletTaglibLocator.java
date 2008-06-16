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

import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Parent of all locators of facelet taglibs.
 * 
 * @author cbateman
 * 
 */
public abstract class AbstractFaceletTaglibLocator
{
    private final CopyOnWriteArrayList<Listener>        _listeners = new CopyOnWriteArrayList<Listener>();

    /**
     * @return a list of all tag libraries known to this locator
     */
    public abstract Map<String, ? extends IFaceletTagRecord> locate();

    /**
     * @param listener
     */
    public void addListener(final Listener listener)
    {
        _listeners.addIfAbsent(listener);
    }
    
    /**
     * @param listener
     */
    public void removeListener(final Listener listener)
    {
        _listeners.remove(listener);
    }

    /**
     */
    public abstract void startLocating();

    /**
     * 
     */
    public abstract void stopLocating();

    /**
     * @param event
     */
    protected final void fireEvent(final Listener.TaglibChangedEvent event)
    {
        for (final Listener listener : _listeners)
        {
            listener.tagLibChanged(event);
        }
    }
    
}
