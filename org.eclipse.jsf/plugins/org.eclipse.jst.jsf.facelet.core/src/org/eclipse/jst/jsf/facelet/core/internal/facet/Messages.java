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

import org.eclipse.osgi.util.NLS;

/**
 * NLS message file
 * 
 * @author cbateman
 *
 */
public class Messages extends NLS
{
    private static final String BUNDLE_NAME = "org.eclipse.jst.jsf.facelet.core.internal.facet.messages"; //$NON-NLS-1$
    /**
     * see messages.properties
     */
    public static String        FaceletInstallDelegate_FACET_INSTALLER_DELEGATE_DISPLAY_NAME;
    /**
     * see messages.properties
     */
    public static String FaceletUninstallDelegate_FACET_INSTALLER_DELEGATE_DISPLAY_NAME;
    static
    {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages()
    {
        // no external instantiation
    }
}
