package org.eclipse.jst.jsf.facelet.core.internal.registry;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jem.internal.proxy.core.ConfigurationContributorAdapter;
import org.eclipse.jem.internal.proxy.core.IConfigurationContributionController;
import org.eclipse.jst.jsf.facelet.core.internal.FaceletCorePlugin;
import org.osgi.framework.Bundle;

public class ELProxyContributor extends ConfigurationContributorAdapter
{
    private final IProject _project;

    ELProxyContributor(final IProject project)
    {
        _project = project;
    }

    @Override
    public void contributeClasspaths(
            final IConfigurationContributionController controller)
    throws CoreException
    {
        if (_project.isAccessible())
        {
            final IJavaProject javaProject = JavaCore.create(_project);
            final IType type  = javaProject.findType("java.el.ELException");

            // if we can't find ELException on the classpath,then inject
            // our fake el jar to aid linkage while introspecting facelet libs
            if (type == null)
            {
                final Bundle faceletBundle = FaceletCorePlugin.getDefault().getBundle();
                controller.contributeClasspath(faceletBundle, "/jars/fake_el.jar",
                        IConfigurationContributionController.APPEND_USER_CLASSPATH,
                        false);
            }
        }
    }
}
