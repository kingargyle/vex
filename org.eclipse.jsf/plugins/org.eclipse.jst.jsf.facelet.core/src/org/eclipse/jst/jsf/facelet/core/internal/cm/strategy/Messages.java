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
package org.eclipse.jst.jsf.facelet.core.internal.cm.strategy;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * @author cbateman
 *
 */
/*package*/ class Messages extends NLS
{
    private static final String BUNDLE_NAME = "org.eclipse.jst.jsf.facelet.core.internal.cm.strategy.messages"; //$NON-NLS-1$


    public static String JSPExternalMetadataStrategy_DisplayName;


    /**
     * Display name for MDExternalMetadataStrategy
     */
    public static String        MDExternalMetadataStrategy_DisplayName;
    
    public static String        MDExternalMetadataStrategy_BINDING_DESCRIPTION;
    public static String        MDExternalMetadataStrategy_DEFINE_NAME_DESCRIPTION;
    public static String        MDExternalMetadataStrategy_HOTKEY_DESCRIPTION;
    public static String        MDExternalMetadataStrategy_ID_DESCRIPTION;
    public static String        MDExternalMetadataStrategy_INSERT_NAME_DESCRIPTION;
    public static String        MDExternalMetadataStrategy_PARAM_NAME_DESCRIPTION;
    public static String        MDExternalMetadataStrategy_PARAM_VALUE_DESCRIPTION;
    public static String        MDExternalMetadataStrategy_REPEAT_VALUE_DESCRIPTION;
    public static String        MDExternalMetadataStrategy_REPEAT_VAR_DESCRIPTION;
    public static String        MDExternalMetadataStrategy_SRC_DESCRIPTION;
    public static String        MDExternalMetadataStrategy_TEMPLATE_DESCRIPTION;


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
