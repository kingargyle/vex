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

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Set;

import org.eclipse.jst.jsf.facelet.core.internal.FaceletCorePlugin;
import org.eclipse.wst.common.componentcore.datamodel.FacetInstallDataModelProvider;

/**
 * The base data  model provider for the facet install/uninstall
 * 
 * @author cbateman
 *
 */
public abstract class FaceletFacetChangeDataModelProvider extends
FacetInstallDataModelProvider
{
    /**
     * Change key identifier for web app lifecycle listener
     */
    protected static final String CHG_WEB_APP_LIFECYCLE_LISTENER = "chgWebAppLifecycleListener"; //$NON-NLS-1$
    /**
     * Change key identifier for configure listener
     */
    protected static final String CHG_CONFIGURE_LISTENER         = "chgConfigureListener"; //$NON-NLS-1$
    /**
     * Change key identifier for the Facelet runtime view handler
     */
    protected static final String CHG_VIEW_HANDLER               = "chgViewHandler"; //$NON-NLS-1$
    /**
     * Change key identifier for the default suffix
     */
    protected static final String CHG_DEFAULT_SUFFIX             = "chgDefaultSuffix"; //$NON-NLS-1$

    private BeanInfo              _beanInfo;

    /**
     * 
     */
    public FaceletFacetChangeDataModelProvider()
    {
        try
        {
            _beanInfo = Introspector.getBeanInfo(FacetInstallModel.class);
        }
        catch (final IntrospectionException e)
        {
            // TODO: suppress for now
            FaceletCorePlugin.log("Problem getting bean info for FacetInstallModel", e); //$NON-NLS-1$
        }
    }

    @Override
    public Object create()
    {
        final FacetChangeModel model_ = createChangeModel();

        model_
        .setChgDefaultSuffix(((Boolean) getDefaultProperty(CHG_DEFAULT_SUFFIX)).booleanValue());
        model_.setChgViewHandler(((Boolean) getDefaultProperty(CHG_VIEW_HANDLER)).booleanValue());
        model_
        .setChgConfigureListener(((Boolean) getDefaultProperty(CHG_CONFIGURE_LISTENER)).booleanValue());
        model_
        .setChgWebAppLifecycleListener(((Boolean) getDefaultProperty(CHG_WEB_APP_LIFECYCLE_LISTENER)).booleanValue());

        return model_;
    }

    /**
     * @return the change model to be used by the delegate.
     */
    protected abstract FacetChangeModel createChangeModel();

    /**
     * Set the default property value
     */
    @Override
    public Object getDefaultProperty(final String propertyName)
    {
        return super.getDefaultProperty(propertyName);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set getPropertyNames()
    {
        final Set<String> propSet = super.getPropertyNames();
        final PropertyDescriptor[] props = _beanInfo.getPropertyDescriptors();
        for (final PropertyDescriptor prop : props)
        {
            propSet.add(prop.getName());
        }
        return propSet;
    }
}
