package org.eclipse.jst.jsf.facelet.ui.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jst.jsf.designtime.internal.view.model.TagRegistryFactory.TagRegistryFactoryException;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class FaceletUiPlugin extends AbstractUIPlugin
{

    /**
     * The plug-in ID
     */
    public static final String     PLUGIN_ID = "org.eclipse.jst.jsf.facelet.ui";

    // The shared instance
    private static FaceletUiPlugin plugin;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
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
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
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
    public static FaceletUiPlugin getDefault()
    {
        return plugin;
    }

    /**
     * @param e
     */
    public static void log(final TagRegistryFactoryException e)
    {
        getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, "", e));
    }

}
