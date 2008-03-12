package org.eclipse.jst.jsf.facelet.core.internal.facet;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Set;

import org.eclipse.wst.common.componentcore.datamodel.FacetInstallDataModelProvider;

public class FaceletFacetDataModelProvider extends
        FacetInstallDataModelProvider
{
    private BeanInfo _beanInfo;

    public FaceletFacetDataModelProvider()
    {
        try
        {
            _beanInfo = Introspector.getBeanInfo(FacetInstallModel.class);
        }
        catch (IntrospectionException e)
        {
        }
    }

    @Override
    public Object create()
    {
        FacetInstallModel model = new FacetInstallModel();
        model
                .setAddDefaultSuffix((Boolean) getDefaultProperty("addDefaultSuffix"));
        model.setAddViewHandler((Boolean) getDefaultProperty("addViewHandler"));
        model
                .setAddConfigureListener((Boolean) getDefaultProperty("addConfigureListener"));
        model.setAddWebAppLifecycleListener((Boolean) getDefaultProperty("addWebAppLifecycleListener"));

        return model;
    }

    @Override
    public Object getDefaultProperty(String propertyName)
    {
        if (propertyName.equals("addDefaultSuffix"))
        {
            return Boolean.TRUE;
        }
        else if (propertyName.equals("addViewHandler"))
        {
            return Boolean.TRUE;
        }
        else if (propertyName.equals("addConfigureListener"))
        {
            return Boolean.FALSE;
        }
        else if (propertyName.equals("addWebAppLifecycleListener"))
        {
            return Boolean.FALSE;
        }
        return super.getDefaultProperty(propertyName);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set getPropertyNames()
    {
        Set<String> propSet = super.getPropertyNames();
        PropertyDescriptor[] props = _beanInfo.getPropertyDescriptors();
        for (final PropertyDescriptor prop : props)
        {
            propSet.add(prop.getName());
        }
        return propSet;
    }
}
