package org.eclipse.jst.jsf.facelet.core.internal.facet;


/**
 * The data model provider for the facet install.
 * 
 * @author cbateman
 *
 */
public class FaceletFacetInstallDataModelProvider extends FaceletFacetChangeDataModelProvider
{
    @Override
    public Object getDefaultProperty(final String propertyName)
    {
        if (propertyName.equals(CHG_DEFAULT_SUFFIX))
        {
            return Boolean.TRUE;
        }
        else if (propertyName.equals(CHG_VIEW_HANDLER))
        {
            return Boolean.TRUE;
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
        return new FacetInstallModel();
    }
}
