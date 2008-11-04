package org.eclipse.jst.jsf.facelet.core.internal.registry;

import org.eclipse.core.resources.IProject;
import org.eclipse.jst.jsf.common.internal.resource.ResourceSingletonObjectManager;
import org.eclipse.jst.jsf.designtime.internal.view.model.ITagRegistry;
import org.eclipse.jst.jsf.designtime.internal.view.model.TagRegistryFactory;
import org.eclipse.jst.jsf.facelet.core.internal.FaceletCoreTraceOptions;

/**
 * A per-resource singleton manager for TLDTagRegistry's.
 * 
 * @author cbateman
 * 
 */
public final class FaceletRegistryManager extends
                        ResourceSingletonObjectManager<FaceletTagRegistry, IProject>
{
    // STATIC
    private static FaceletRegistryManager                  INSTANCE;

    /**
     * @return the singleton instance
     */
    private static FaceletRegistryManager getGlobalManager()
    {
        if (FaceletCoreTraceOptions.TRACE_REGISTRYMANAGER)
        {
            FaceletCoreTraceOptions
                    .log("FaceletRegistryManager: Initializing FaceletRegistryManager singleton"); //$NON-NLS-1$
        }

        synchronized(FaceletRegistryManager.class)
        {
            if (INSTANCE == null)
            {
                INSTANCE = new FaceletRegistryManager();
            }
            
            return INSTANCE;
        }
    }

    private FaceletRegistryManager()
    {
        // do nothing
    }

    @Override
    protected FaceletTagRegistry createNewInstance(final IProject project)
    {
        if (FaceletCoreTraceOptions.TRACE_REGISTRYMANAGER)
        {
            FaceletCoreTraceOptions.log("FaceletRegistryManager: creating new instance for " //$NON-NLS-1$
                    + project.toString());
        }

        return new FaceletTagRegistry(project);
    }

    @Override
    protected void runAfterGetInstance(final IProject resource)
    {
        if (FaceletCoreTraceOptions.TRACE_REGISTRYMANAGER)
        {
            FaceletCoreTraceOptions.log("FaceletRegistryManager: Acquired instance for " //$NON-NLS-1$
                    + resource.toString());
        }
    }

    @Override
    protected void runBeforeGetInstance(final IProject resource)
    {
        if (FaceletCoreTraceOptions.TRACE_REGISTRYMANAGER)
        {
            FaceletCoreTraceOptions.log("FaceletRegistryManager: Getting registry for " //$NON-NLS-1$
                    + resource.toString());
        }
    }
    
    /**
     * Adapter used to allow creation by extension point.
     * 
     * @author cbateman
     *
     */
    public static class MyRegistryFactory extends TagRegistryFactory
    {
        @Override
        public ITagRegistry createTagRegistry(IProject project) throws TagRegistryFactoryException
        {
            try
            {
                return getGlobalManager().getInstance(project);
            }
            catch (ManagedObjectException e)
            {
                throw new TagRegistryFactoryException(e);
            }
        }

        @Override
        public boolean isInstance(IProject project)
        {
            return getGlobalManager().isInstance(project);
        }

        public String getDisplayName()
        {
            return Messages.FaceletRegistryManager_REGISTRY_FACTORY_DISPLAYNAME;
        }
    }
}
