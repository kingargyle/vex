package org.eclipse.jst.jsf.facelet.core.internal;

import org.eclipse.osgi.framework.debug.FrameworkDebugOptions;
import org.eclipse.osgi.service.debug.DebugOptions;

/**
 * Defines that standard runtime trace options for debugging. See .options file
 * for definitions.
 * 
 * @author cbateman
 * 
 */
public final class FaceletCoreTraceOptions
{
    /**
     * True if debug tracing is enabled. Other tracing cannot be enabled unless
     * this is enabled.
     */
    public static final boolean ENABLED;

    /**
     * True if the registry manager tracing is enabled
     */
    public static final boolean TRACE_REGISTRYMANAGER;

    /**
     * True if the facet installer is being traced.
     */
    public static final boolean TRACE_FACETINSTALLDELEGATE;
    /**
     * True if the facet uninstaller is being traced
     */
    public static final boolean TRACE_FACETUNINSTALLDELEGATE;
    /**
     * True if the base facet change delegate is being traced
     */
    public static final boolean TRACE_FACETCHANGEDELEGATE;

    private static final String KEY_DEBUG_ENABLED = "/debug"; //$NON-NLS-1$
//    private static final String KEY_VIEW_TAGREGISTRY = "/jsptagregistry";
//    private static final String KEY_VIEW_JSPTAGREGISTRY_CHANGES =
//        KEY_VIEW_TAGREGISTRY + "/changes";
    private static final String KEY_VIEW_REGISTRYMANAGER = "/registrymanager"; //$NON-NLS-1$
    private static final String KEY_FACETINSTALLDELEGATE = "/facetinstalldelegate"; //$NON-NLS-1$
    private static final String KEY_FACETUNINSTALLDELEGATE = "facetuninstalldelegate"; //$NON-NLS-1$
    private static final String KEY_FACETCHANGEDELEGATE = "facetchangedelegate"; //$NON-NLS-1$

    static
    {
        final DebugOptions debugOptions = FrameworkDebugOptions.getDefault();

        ENABLED = debugOptions != null
                && debugOptions.getBooleanOption(FaceletCorePlugin.PLUGIN_ID
                        + KEY_DEBUG_ENABLED, false);

        if (ENABLED && debugOptions != null)
        {
            TRACE_REGISTRYMANAGER = debugOptions.getBooleanOption(
                    FaceletCorePlugin.PLUGIN_ID + KEY_VIEW_REGISTRYMANAGER, false);
            TRACE_FACETINSTALLDELEGATE = debugOptions.getBooleanOption(
                    FaceletCorePlugin.PLUGIN_ID + KEY_FACETINSTALLDELEGATE, false);
            TRACE_FACETUNINSTALLDELEGATE = debugOptions.getBooleanOption(
                    FaceletCorePlugin.PLUGIN_ID + KEY_FACETUNINSTALLDELEGATE, false);
            TRACE_FACETCHANGEDELEGATE = debugOptions.getBooleanOption(
                    FaceletCorePlugin.PLUGIN_ID + KEY_FACETCHANGEDELEGATE, false);
        }
        else
        {
            TRACE_REGISTRYMANAGER = false;
            TRACE_FACETINSTALLDELEGATE = false;
            TRACE_FACETUNINSTALLDELEGATE = false;
            TRACE_FACETCHANGEDELEGATE = false;
        }
    }

    /**
     * @param message
     */
    public static void log(final String message)
    {
        System.out.println(message);
    }

    private FaceletCoreTraceOptions()
    {
        // no instantiation
    }
}
