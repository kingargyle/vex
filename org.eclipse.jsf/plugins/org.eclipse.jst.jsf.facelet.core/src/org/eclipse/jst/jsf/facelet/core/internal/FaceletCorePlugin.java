package org.eclipse.jst.jsf.facelet.core.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class FaceletCorePlugin extends Plugin
{

    /**
     * The plug-in ID
     */
    public static final String       PLUGIN_ID = "org.eclipse.jst.jsf.facelet.core"; //$NON-NLS-1$

    // The shared instance
    private static FaceletCorePlugin plugin;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(final BundleContext context) throws Exception
    {
        super.start(context);
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(final BundleContext context) throws Exception
    {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static FaceletCorePlugin getDefault()
    {
        return plugin;
    }

    /**
     * @param string
     * @param exception
     */
    public static void log(final String string, final Throwable exception)
    {
        final IStatus status = new Status(IStatus.ERROR, PLUGIN_ID,
                "Exception logged", exception); //$NON-NLS-1$
        getDefault().getLog().log(status);
    }

}
