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
package org.eclipse.jst.jsf.facelet.core.internal.cm;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Externalized messages.
 * 
 * @author cbateman
 *
 */
public final class Messages
{
    private static final String         BUNDLE_NAME     = "org.eclipse.jst.jsf.facelet.core.internal.cm.messages"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
                                                                .getBundle(BUNDLE_NAME);

    private Messages()
    {
        // no instantiation
    }

    /**
     * @param key
     * @return the string for the key
     */
    public static String getString(String key)
    {
        try
        {
            return RESOURCE_BUNDLE.getString(key);
        }
        catch (MissingResourceException e)
        {
            return '!' + key + '!';
        }
    }
}
