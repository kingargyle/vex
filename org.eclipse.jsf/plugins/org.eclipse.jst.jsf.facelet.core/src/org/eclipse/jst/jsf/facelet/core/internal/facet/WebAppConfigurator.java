package org.eclipse.jst.jsf.facelet.core.internal.facet;

import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jst.j2ee.model.IModelProvider;
import org.eclipse.jst.j2ee.model.ModelProviderManager;
import org.eclipse.jst.jsf.facelet.core.internal.FaceletCorePlugin;

/**
 * Insulates caller from the insane J2EE/JavaEE dual model.
 * 
 * @author cbateman
 * 
 */
public abstract class WebAppConfigurator
{
    /**
     * @param project
     * @return the configurator for project or null if none
     */
    public static WebAppConfigurator getConfigurator(final IProject project)
    {
        final IModelProvider provider = ModelProviderManager
                .getModelProvider(project);
        final Object webAppObj = provider.getModelObject();
        if (webAppObj == null)
        {
            FaceletCorePlugin.log("Error getting web app configurator", //$NON-NLS-1$
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

    private final IProject _project;

    WebAppConfigurator(final IProject project)
    {
        _project = project;
    }

    /**
     * @param paramName
     * @param paramValue
     * @param addEvenIfPresent
     */
    public abstract void addContextParam(final String paramName,
            final String paramValue, final boolean addEvenIfPresent);

    /**
     * @param paramName
     * @param paramValue
     */
    public abstract void removeContextParam(final String paramName,
            final String paramValue);

    /**
     * Adds the listenerClass to the webapp config. If addEventIfPresent is
     * false, then it will not add it if it finds it already. If true, it will
     * forcibly add.
     * 
     * @param listenerClass
     * @param addEvenIfPresent
     */
    public abstract void addListener(final String listenerClass,
            final boolean addEvenIfPresent);

    /**
     * @param listenerClass
     */
    public abstract void removeListener(final String listenerClass);

    /**
     * @param runnable
     */
    protected void executeChange(final Runnable runnable)
    {
        final IPath webXMLPath = new Path("WEB-INF").append("web.xml"); //$NON-NLS-1$ //$NON-NLS-2$
        final IModelProvider provider = ModelProviderManager
                .getModelProvider(getProject());
        provider.modify(runnable, webXMLPath);
    }

    /**
     * @return the project
     */
    protected IProject getProject()
    {
        return _project;
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
                final String paramValue, final boolean addEvenIfPresent)
        {
            final Runnable runnable = new Runnable()
            {
                public void run()
                {
                    final org.eclipse.jst.javaee.web.WebApp webApp = (org.eclipse.jst.javaee.web.WebApp) ModelProviderManager
                            .getModelProvider(getProject()).getModelObject();
                    if (addEvenIfPresent
                            || !isContextParamPresent(webApp, paramName))
                    {
                        final org.eclipse.jst.javaee.core.ParamValue newParamValue = org.eclipse.jst.javaee.core.JavaeeFactory.eINSTANCE
                                .createParamValue();
                        newParamValue.setParamName(paramName);
                        newParamValue.setParamValue(paramValue);
                        webApp.getContextParams().add(newParamValue);
                    }
                }
            };
            executeChange(runnable);
        }

        @Override
        public void removeContextParam(final String paramName,
                final String paramVal)
        {
            final Runnable runnable = new Runnable()
            {
                public void run()
                {
                    final org.eclipse.jst.javaee.web.WebApp webApp = (org.eclipse.jst.javaee.web.WebApp) ModelProviderManager
                            .getModelProvider(getProject()).getModelObject();
                    for (final Iterator<?> it = webApp.getContextParams()
                            .iterator(); it.hasNext();)
                    {
                        final org.eclipse.jst.javaee.core.ParamValue paramValue = (org.eclipse.jst.javaee.core.ParamValue) it
                                .next();
                        if (paramName.equals(paramValue.getParamName().trim())
                                && paramVal.equals(paramValue.getParamValue()
                                        .trim()))
                        {
                            it.remove();
                        }
                    }
                }
            };
            executeChange(runnable);
        }

        private boolean isContextParamPresent(
                final org.eclipse.jst.javaee.web.WebApp webApp,
                final String paramName)
        {
            for (final Iterator<?> it = webApp.getContextParams().iterator(); it
                    .hasNext();)
            {
                final org.eclipse.jst.javaee.core.ParamValue paramValue = (org.eclipse.jst.javaee.core.ParamValue) it
                        .next();
                if (paramName.equals(paramValue.getParamName().trim()))
                {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void addListener(final String listenerClass,
                final boolean addEvenIfPresent)
        {
            final Runnable runnable = new Runnable()
            {
                public void run()
                {
                    final org.eclipse.jst.javaee.web.WebApp webApp = (org.eclipse.jst.javaee.web.WebApp) ModelProviderManager
                            .getModelProvider(getProject()).getModelObject();

                    if (addEvenIfPresent
                            || !isListenerPresent(webApp, listenerClass))
                    {
                        final org.eclipse.jst.javaee.core.Listener listener = org.eclipse.jst.javaee.core.JavaeeFactory.eINSTANCE
                                .createListener();
                        listener.setListenerClass(listenerClass);
                        webApp.getListeners().add(listener);
                    }
                }
            };
            executeChange(runnable);
        }

        @Override
        public void removeListener(final String listenerClass)
        {
            final Runnable runnable = new Runnable()
            {
                public void run()
                {
                    final org.eclipse.jst.javaee.web.WebApp webApp = (org.eclipse.jst.javaee.web.WebApp) ModelProviderManager
                            .getModelProvider(getProject()).getModelObject();
                    for (final Iterator listenerIt = webApp.getListeners()
                            .iterator(); listenerIt.hasNext();)
                    {
                        final org.eclipse.jst.javaee.core.Listener listener = (org.eclipse.jst.javaee.core.Listener) listenerIt
                                .next();
                        if (listenerClass.equals(listener.getListenerClass()
                                .trim()))
                        {
                            listenerIt.remove();
                        }
                    }
                }
            };
            executeChange(runnable);
        }

        private boolean isListenerPresent(
                final org.eclipse.jst.javaee.web.WebApp webApp,
                final String listenerClass)
        {
            for (final Iterator listenerIt = webApp.getListeners().iterator(); listenerIt
                    .hasNext();)
            {
                final org.eclipse.jst.javaee.core.Listener listener = (org.eclipse.jst.javaee.core.Listener) listenerIt
                        .next();
                if (listenerClass.equals(listener.getListenerClass().trim()))
                {
                    return true;
                }
            }
            return false;
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
                final String paramValue, final boolean addEvenIfPresent)
        {
            final Runnable runnable = new Runnable()
            {
                public void run()
                {
                    final org.eclipse.jst.j2ee.webapplication.WebApp webApp = (org.eclipse.jst.j2ee.webapplication.WebApp) ModelProviderManager
                            .getModelProvider(getProject()).getModelObject();

                    if (addEvenIfPresent
                            || !isContextParamPresent(webApp, paramName))
                    {
                        final org.eclipse.jst.j2ee.common.ParamValue newParamValue = org.eclipse.jst.j2ee.common.CommonFactory.eINSTANCE
                                .createParamValue();
                        newParamValue.setName(paramName);
                        newParamValue.setValue(paramValue);
                        webApp.getContextParams().add(newParamValue);
                    }
                }
            };
            executeChange(runnable);
        }

        @Override
        public void removeContextParam(final String paramName,
                final String paramVal)
        {
            final Runnable runnable = new Runnable()
            {
                public void run()
                {
                    final org.eclipse.jst.j2ee.webapplication.WebApp webApp = (org.eclipse.jst.j2ee.webapplication.WebApp) ModelProviderManager
                            .getModelProvider(getProject()).getModelObject();
                    for (final Iterator it = webApp.getContextParams()
                            .iterator(); it.hasNext();)
                    {
                        final org.eclipse.jst.j2ee.common.ParamValue paramValue = (org.eclipse.jst.j2ee.common.ParamValue) it
                                .next();
                        if (paramName.equals(paramValue.getName().trim())
                                && paramVal
                                        .equals(paramValue.getValue().trim()))
                        {
                            it.remove();
                        }
                    }
                }
            };
            executeChange(runnable);
        }

        private boolean isContextParamPresent(
                final org.eclipse.jst.j2ee.webapplication.WebApp webApp,
                final String paramName)
        {
            for (final Iterator it = webApp.getContextParams().iterator(); it
                    .hasNext();)
            {
                final org.eclipse.jst.j2ee.common.ParamValue paramValue = (org.eclipse.jst.j2ee.common.ParamValue) it
                        .next();
                if (paramName.equals(paramValue.getName().trim()))
                {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void addListener(final String listenerClass,
                final boolean addEvenIfPresent)
        {
            final Runnable runnable = new Runnable()
            {
                public void run()
                {
                    final org.eclipse.jst.j2ee.webapplication.WebApp webApp = (org.eclipse.jst.j2ee.webapplication.WebApp) ModelProviderManager
                            .getModelProvider(getProject()).getModelObject();

                    if (addEvenIfPresent
                            || !isListenerPresent(webApp, listenerClass))
                    {
                        final org.eclipse.jst.j2ee.common.Listener listener = org.eclipse.jst.j2ee.common.CommonFactory.eINSTANCE
                                .createListener();
                        listener.setListenerClassName(listenerClass);
                        webApp.getListeners().add(listener);
                    }
                }
            };
            executeChange(runnable);
        }

        @Override
        public void removeListener(final String listenerClass)
        {
            final Runnable runnable = new Runnable()
            {
                public void run()
                {
                    final org.eclipse.jst.j2ee.webapplication.WebApp webApp = (org.eclipse.jst.j2ee.webapplication.WebApp) ModelProviderManager
                            .getModelProvider(getProject()).getModelObject();

                    for (final Iterator listenerIt = webApp.getListeners()
                            .iterator(); listenerIt.hasNext();)
                    {
                        final org.eclipse.jst.j2ee.common.Listener listener = (org.eclipse.jst.j2ee.common.Listener) listenerIt
                                .next();
                        if (listenerClass.equals(listener
                                .getListenerClassName().trim()))
                        {
                            listenerIt.remove();
                        }
                    }
                }
            };
            executeChange(runnable);
        }

        private boolean isListenerPresent(
                final org.eclipse.jst.j2ee.webapplication.WebApp webApp,
                final String listenerClass)
        {
            for (final Iterator listenerIt = webApp.getListeners().iterator(); listenerIt
                    .hasNext();)
            {
                final org.eclipse.jst.j2ee.common.Listener listener = (org.eclipse.jst.j2ee.common.Listener) listenerIt
                        .next();
                if (listenerClass
                        .equals(listener.getListenerClassName().trim()))
                {
                    return true;
                }
            }
            return false;
        }
    }

}
