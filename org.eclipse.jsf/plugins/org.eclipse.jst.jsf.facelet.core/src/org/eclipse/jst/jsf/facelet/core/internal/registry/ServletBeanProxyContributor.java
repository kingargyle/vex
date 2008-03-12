/**
 * 
 */
package org.eclipse.jst.jsf.facelet.core.internal.registry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jem.internal.proxy.core.ConfigurationContributorAdapter;
import org.eclipse.jem.internal.proxy.core.IConfigurationContributionController;
import org.eclipse.jst.jsf.core.JSFVersion;
import org.osgi.framework.Bundle;

class ServletBeanProxyContributor extends ConfigurationContributorAdapter
{
    private final JSFVersion _jsfVersion;

    public ServletBeanProxyContributor(final JSFVersion jsfVersion)
    {
        if (jsfVersion == null)
        {
            throw new IllegalArgumentException("jsfVersion must not be null");
        }
        
        _jsfVersion = jsfVersion;
    }

    @Override
    public void contributeClasspaths(
            final IConfigurationContributionController controller)
            throws CoreException
    {
        if (_jsfVersion != JSFVersion.V1_2)
        {
            final Bundle servletBundle = Platform.getBundle("javax.servlet");
            controller.contributeClasspath(servletBundle, (IPath) null,
                    IConfigurationContributionController.APPEND_USER_CLASSPATH,
                    true);

            final Bundle jspBundle = Platform.getBundle("javax.servlet.jsp");
            controller.contributeClasspath(jspBundle, (IPath) null,
                    IConfigurationContributionController.APPEND_USER_CLASSPATH,
                    true);
        }
        
    }
}