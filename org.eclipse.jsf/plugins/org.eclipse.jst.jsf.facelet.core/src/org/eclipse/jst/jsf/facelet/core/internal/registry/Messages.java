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
package org.eclipse.jst.jsf.facelet.core.internal.registry;

import org.eclipse.osgi.util.NLS;

/**
 * Externalized string manager.
 * 
 * @author cbateman
 *
 */
public final class Messages extends NLS
{
    private static final String BUNDLE_NAME = "org.eclipse.jst.jsf.facelet.core.internal.registry.messages"; //$NON-NLS-1$
    /**
     * see messages.properties
     */
    public static String        FaceletRegistryManager_REGISTRY_FACTORY_DISPLAYNAME;
    /**
     * see messages.properties
     */
    public static String FaceletTagRegistry_TAG_REGISTRY_REFRESH_JOB_DESCRIPTION;
    /**
     * see messages.properties
     */
    public static String FaceletTagResolvingStrategy_FACELET_TAG_RESOLVER_DISPLAY_NAME;
    static
    {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages()
    {
        // no instantiation
    }
}
