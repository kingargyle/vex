package org.eclipse.jst.jsf.facelet.core.internal.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jst.jsf.designtime.DesignTimeApplicationManager;
import org.eclipse.jst.jsf.facelet.core.internal.FaceletCoreTraceOptions;
import org.eclipse.jst.jsf.facesconfig.emf.ApplicationType;
import org.eclipse.jst.jsf.facesconfig.emf.FacesConfigFactory;
import org.eclipse.jst.jsf.facesconfig.emf.FacesConfigType;
import org.eclipse.jst.jsf.facesconfig.emf.ViewHandlerType;
import org.eclipse.jst.jsf.facesconfig.util.FacesConfigArtifactEdit;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

public class FaceletInstallDelegate implements IDelegate
{

    public void execute(IProject project, IProjectFacetVersion fv,
            Object config, IProgressMonitor monitor) throws CoreException
    {
        final FacetInstallModel model = (FacetInstallModel) config;
        try
        {
            if (FaceletCoreTraceOptions.TRACE_FACETINSTALLDELEGATE)
            {
                FaceletCoreTraceOptions.log("Installing facet on project: "+project.getName());

                FaceletCoreTraceOptions.log(String.format(
                        "FaceletInstallDelegate: Add default selection %b",
                        model.isAddDefaultSuffix()));
                FaceletCoreTraceOptions.log(String.format(
                        "FaceletInstallDelegate: Add view handler %b", model
                                .isAddViewHandler()));
                FaceletCoreTraceOptions.log(String.format(
                        "FaceletInstallDelegate: Add configure listener %b",
                        model.isAddConfigureListener()));
                FaceletCoreTraceOptions
                        .log(String
                                .format(
                                        "FaceletInstallDelegate: Add web app lifecycle listener %b",
                                        model.isAddWebAppLifecycleListener()));
            }

            if (monitor != null)
            {
                monitor.beginTask("Installing facelet facet", 1); //$NON-NLS-1$
            }

            final DesignTimeApplicationManager manager = DesignTimeApplicationManager
                    .getInstance(project);

            manager.setViewHandlerId(FaceletFacet.VIEW_HANDLER_ID);

            if (model.isAddViewHandler())
            {
                installFaceletViewHandler(project, monitor);
            }
            
            WebAppConfigurator configurator = WebAppConfigurator
                    .getConfigurator(project);

            if (configurator != null)
            {
                installDefaultSuffix(model, configurator);

                installConfigureListener(model, configurator);

                installWebLifecycleListener(model, configurator);
            }
            else
            {
                FaceletCoreTraceOptions.log("FaceletInstallDelegate: No web configurator");
            }
        }
        finally
        {
            if (monitor != null)
            {
                monitor.done();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void installFaceletViewHandler(final IProject project,
            final IProgressMonitor monitor)
    {
        if (FaceletCoreTraceOptions.TRACE_FACETINSTALLDELEGATE)
        {
            FaceletCoreTraceOptions
                    .log("FaceletInstallDelegate: Installing facelet view handler");
        }

        final FacesConfigArtifactEdit edit = FacesConfigArtifactEdit
                .getFacesConfigArtifactEditForWrite(project,
                        "WEB-INF/faces-config.xml");
        if (edit != null)
        {
            try
            {
                FacesConfigType root = edit.getFacesConfig();
                EList applications = root.getApplication();
                ApplicationType application = null;
                if (applications.size() > 0)
                {
                    application = (ApplicationType) applications.get(0);
                }
                else
                {
                    application = FacesConfigFactory.eINSTANCE
                            .createApplicationType();
                    applications.add(application);
                }
                ViewHandlerType viewHandlerType = FacesConfigFactory.eINSTANCE
                        .createViewHandlerType();
                viewHandlerType
                        .setTextContent("com.sun.facelets.FaceletViewHandler");
                application.getViewHandler().add(viewHandlerType);
                edit.save(monitor);

                if (FaceletCoreTraceOptions.TRACE_FACETINSTALLDELEGATE)
                {
                    FaceletCoreTraceOptions
                            .log("FaceletInstallDelegate: Saved changes for facelet view handler");
                }

            }
            finally
            {
                edit.dispose();
            }
        }
    }

    private void installDefaultSuffix(final FacetInstallModel model,
            final WebAppConfigurator configurator)
    {
        if (model.isAddDefaultSuffix())
        {
            if (FaceletCoreTraceOptions.TRACE_FACETINSTALLDELEGATE)
            {
                FaceletCoreTraceOptions
                        .log("FaceletInstallDelegate: Installing DEFAULT_SUFFIX");
            }
            configurator
                    .addContextParam("javax.faces.DEFAULT_SUFFIX", ".xhtml");
        }

    }

    private void installConfigureListener(final FacetInstallModel model,
            final WebAppConfigurator configurator)
    {
        if (model.isAddConfigureListener())
        {
            if (FaceletCoreTraceOptions.TRACE_FACETINSTALLDELEGATE)
            {
                FaceletCoreTraceOptions
                        .log("FaceletInstallDelegate: Install Configure Listener");
            }
            configurator.addListener("com.sun.faces.config.ConfigureListener");
        }
    }

    private void installWebLifecycleListener(final FacetInstallModel model,
            final WebAppConfigurator configurator)
    {
        if (model.isAddWebAppLifecycleListener())
        {
            if (FaceletCoreTraceOptions.TRACE_FACETINSTALLDELEGATE)
            {
                FaceletCoreTraceOptions
                        .log("FaceletInstallDelegate: Install WebappLifecycleListener");
            }
            configurator
                    .addListener("com.sun.faces.application.WebappLifecycleListener");
        }
    }
}
