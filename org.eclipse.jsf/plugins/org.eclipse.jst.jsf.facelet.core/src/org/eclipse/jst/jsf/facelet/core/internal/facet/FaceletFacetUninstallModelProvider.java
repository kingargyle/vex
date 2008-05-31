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
 * The data model provider for facet uninstall.
 * 
 * @author cbateman
 *
 */
public class FaceletFacetUninstallModelProvider extends FaceletFacetChangeDataModelProvider
{
    @Override
    public Object getDefaultProperty(final String propertyName)
    {
        if (propertyName.equals(CHG_DEFAULT_SUFFIX))
        {
            return Boolean.FALSE;
        }
        else if (propertyName.equals(CHG_VIEW_HANDLER))
        {
            return Boolean.FALSE;
        }
        else if (propertyName.equals(CHG_CONFIGURE_LISTENER))
        {
            return Boolean.FALSE;
        }
        else if (propertyName.equals(CHG_WEB_APP_LIFECYCLE_LISTENER))
        {
            return Boolean.FALSE;
        }
        return super.getDefaultProperty(propertyName);
    }

    @Override
    protected FacetChangeModel createChangeModel()
    {
        return new FaceletUninstallModel();
    }
}
