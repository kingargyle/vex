package org.eclipse.jst.jsf.facelet.core.internal.facet;

import org.eclipse.wst.common.project.facet.core.IDefaultVersionProvider;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

/**
 * Provides the default Facelet project facet version. Currently always returns
 * "1.1" facet version.
 */
public final class FaceletFacetDefaultVersionProvider implements
        IDefaultVersionProvider
{

    /**
     * The global id for the Facelet facet
     */
    private static final String DEFAULT_FACET_ID      = FaceletFacet.FACET_ID;

    private static final String DEFAULT_FACET_VERSION = "1.0"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.wst.common.project.facet.core.IDefaultVersionProvider#getDefaultVersion()
     */

    public IProjectFacetVersion getDefaultVersion()
    {
        return ProjectFacetsManager.getProjectFacet(DEFAULT_FACET_ID)
                .getVersion(DEFAULT_FACET_VERSION);
    }

}
