package org.eclipse.jst.jsf.facelet.core.internal.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jst.j2ee.common.CommonFactory;
import org.eclipse.jst.j2ee.model.IModelProvider;
import org.eclipse.jst.j2ee.model.ModelProviderManager;
import org.eclipse.jst.javaee.core.JavaeeFactory;
import org.eclipse.jst.jsf.facelet.core.internal.FaceletCorePlugin;

/**
 * Insulates caller from the insane J2EE/JavaEE dual model.
 * 
 * @author cbateman
 * 
 */
public abstract class WebAppConfigurator
{
    public static WebAppConfigurator getConfigurator(final IProject project)
    {
        final IModelProvider provider = ModelProviderManager
                .getModelProvider(project);
        final Object webAppObj = provider.getModelObject();
        if (webAppObj == null)
        {
            FaceletCorePlugin.log("Error getting web app configurator",
                    new Throwable());
            return null;
        }

        if (webAppObj instanceof org.eclipse.jst.javaee.web.WebApp)
        {
            return new JavaEEWebAppConfigurator(project);
        }
        else if (webAppObj instanceof org.eclipse.jst.j2ee.webapplication.WebApp)
        {
            return new J2EEWebAppConfigurator(project);
        }

        return null;
    }

    protected final IProject _project;

    WebAppConfigurator(final IProject project)
    {
        _project = project;
    }

    public abstract void addContextParam(final String paramName,
            final String paramValue);

    public abstract void addListener(final String listenerClass);

    protected void executeChange(final Runnable runnable)
    {
        final IPath webXMLPath = new Path("WEB-INF").append("web.xml");
        final IModelProvider provider = ModelProviderManager
                .getModelProvider(_project);
        provider.modify(runnable, webXMLPath);
    }

    @SuppressWarnings("unchecked")
    private static class JavaEEWebAppConfigurator extends WebAppConfigurator
    {
        public JavaEEWebAppConfigurator(final IProject project)
        {
            super(project);
        }

        @Override
        public void addContextParam(final String paramName,
                final String paramValue)
        {
            final Runnable runnable = new Runnable()
            {
                public void run()
                {
                    final org.eclipse.jst.javaee.web.WebApp webApp = (org.eclipse.jst.javaee.web.WebApp) ModelProviderManager
                            .getModelProvider(_project).getModelObject();
                    final org.eclipse.jst.javaee.core.ParamValue newParamValue = JavaeeFactory.eINSTANCE
                            .createParamValue();
                    newParamValue.setParamName(paramName);
                    newParamValue.setParamValue(paramValue);
                    webApp.getContextParams().add(newParamValue);
                }
            };
            executeChange(runnable);
        }

        @Override
        public void addListener(final String listenerClass)
        {
            final Runnable runnable = new Runnable()
            {
                public void run()
                {
                    final org.eclipse.jst.javaee.web.WebApp webApp = (org.eclipse.jst.javaee.web.WebApp) ModelProviderManager
                            .getModelProvider(_project).getModelObject();

                    final org.eclipse.jst.javaee.core.Listener listener = JavaeeFactory.eINSTANCE
                            .createListener();
                    listener.setListenerClass(listenerClass);
                    webApp.getListeners().add(listener);
                }
            };
            executeChange(runnable);
        }
    }

    @SuppressWarnings("unchecked")
    private static class J2EEWebAppConfigurator extends WebAppConfigurator
    {
        public J2EEWebAppConfigurator(final IProject project)
        {
            super(project);
        }

        @Override
        public void addContextParam(final String paramName,
                final String paramValue)
        {
            final Runnable runnable = new Runnable()
            {
                public void run()
                {
                    final org.eclipse.jst.j2ee.webapplication.WebApp webApp = (org.eclipse.jst.j2ee.webapplication.WebApp) ModelProviderManager
                            .getModelProvider(_project).getModelObject();

                    final org.eclipse.jst.j2ee.common.ParamValue newParamValue = CommonFactory.eINSTANCE
                            .createParamValue();
                    newParamValue.setName(paramName);
                    newParamValue.setValue(paramValue);
                    webApp.getContextParams().add(newParamValue);
                }
            };
            executeChange(runnable);
        }

        @Override
        public void addListener(final String listenerClass)
        {
            final Runnable runnable = new Runnable()
            {
                public void run()
                {
                    final org.eclipse.jst.j2ee.webapplication.WebApp webApp = (org.eclipse.jst.j2ee.webapplication.WebApp) ModelProviderManager
                            .getModelProvider(_project).getModelObject();

                    final org.eclipse.jst.j2ee.common.Listener listener = CommonFactory.eINSTANCE
                            .createListener();
                    listener.setListenerClassName(listenerClass);
                    webApp.getListeners().add(listener);
                }
            };
            executeChange(runnable);
        }
    }

}
