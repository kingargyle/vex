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
package org.eclipse.jst.jsf.facelet.core.internal.facet;

/**
 * Encapsulate configuration change data during facet install/uninstall
 * @author cbateman
 *
 */
public abstract class FacetChangeModel
{
    private boolean _chgDefaultSuffix;
    private boolean _chgViewHandler;
    private boolean _chgConfigureListener;
    private boolean _chgWebAppLifecycleListener;

    /**
     * @return the type of change action
     */
    public abstract ChangeActionType getChangeActionType();

    /**
     * @return true if should change the runtime view handler configuration
     */
    public boolean isChgViewHandler()
    {
        return _chgViewHandler;
    }

    /**
     * @param chgViewHandler
     */
    public void setChgViewHandler(final boolean chgViewHandler)
    {
        _chgViewHandler = chgViewHandler;
    }

    /**
     * @return true if should change DEFAULT_SUFFIX option
     */
    public boolean isChgDefaultSuffix()
    {
        return _chgDefaultSuffix;
    }

    /**
     * @param chgDefaultSuffix
     */
    public void setChgDefaultSuffix(final boolean chgDefaultSuffix)
    {
        _chgDefaultSuffix = chgDefaultSuffix;
    }

    /**
     * @return true if should change the configure listener option
     */
    public boolean isChgConfigureListener()
    {
        return _chgConfigureListener;
    }

    /**
     * @param chgConfigureListener
     */
    public void setChgConfigureListener(final boolean chgConfigureListener)
    {
        _chgConfigureListener = chgConfigureListener;
    }

    /**
     * @return true if should change the webapp lifecycle listener option
     */
    public boolean isChgWebAppLifecycleListener()
    {
        return _chgWebAppLifecycleListener;
    }

    /**
     * @param chgWebAppLifecycleListener
     */
    public void setChgWebAppLifecycleListener(
            final boolean chgWebAppLifecycleListener)
    {
        _chgWebAppLifecycleListener = chgWebAppLifecycleListener;
    }

}
