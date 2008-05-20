package org.eclipse.jst.jsf.facelet.core.internal.facet;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jst.jsf.core.internal.JSFCorePlugin;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class FaceletFacet
{
    public final static String FACET_ID        = "jsf.facelet";
    public final static String VIEW_HANDLER_ID = "org.eclipse.jst.jsf.facelet.core.html.viewhandler";

    public static boolean hasFacet(final IProject project)
    {
        final IProjectFacetVersion facetVersion = getProjectFacet(project);

        if (facetVersion != null)
        {
            return true;
        }
        return false;
    }

    /**
     * Get the facet version for the project
     * 
     * @param project
     * @return the project facet version or null if could not be found or if
     *  project is not accessible
     */
    @SuppressWarnings("unchecked")
    public static IProjectFacetVersion getProjectFacet(final IProject project)
    {
        // check for null or inaccessible project
        if (project != null && project.isAccessible())
        {
            // check for Facelet facet on project
            try
            {
                final IFacetedProject facetedProject = ProjectFacetsManager
                        .create(project);
                if (facetedProject != null)
                {
                    final Set projectFacets = facetedProject.getProjectFacets();
                    final Iterator itProjectFacets = projectFacets.iterator();
                    while (itProjectFacets.hasNext())
                    {
                        final IProjectFacetVersion projectFacetVersion = (IProjectFacetVersion) itProjectFacets
                                .next();
                        if (FACET_ID.equals(projectFacetVersion
                                .getProjectFacet().getId()))
                        {
                            return projectFacetVersion;
                        }
                    }
                }
            }
            catch (final CoreException ce)
            {
                // log error
                JSFCorePlugin.log(IStatus.ERROR, ce.getLocalizedMessage(), ce);
            }
        }
        return null;
    }
}
