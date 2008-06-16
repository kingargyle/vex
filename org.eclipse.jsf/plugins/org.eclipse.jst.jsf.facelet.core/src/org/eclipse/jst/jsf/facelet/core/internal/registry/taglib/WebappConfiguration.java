/*******************************************************************************
 * Copyright (c) 2008 Oracle Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Cameron Bateman - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.jsf.facelet.core.internal.registry.taglib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jst.j2ee.common.ParamValue;
import org.eclipse.jst.j2ee.internal.J2EEVersionConstants;
import org.eclipse.jst.j2ee.model.IModelProvider;
import org.eclipse.jst.j2ee.model.ModelProviderManager;
import org.eclipse.jst.j2ee.web.componentcore.util.WebArtifactEdit;
import org.eclipse.jst.j2ee.webapplication.ContextParam;
import org.eclipse.jst.j2ee.webapplication.WebApp;
import org.eclipse.jst.jsf.core.internal.JSFCorePlugin;
import org.eclipse.jst.jsf.core.jsfappconfig.JSFAppConfigUtils;
import org.eclipse.jst.jsf.facelet.core.internal.FaceletCorePlugin;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.WebappConfiguration.WebappListener.WebappChangeEvent;
import org.eclipse.wst.common.componentcore.resources.IVirtualFile;
import org.eclipse.wst.common.componentcore.resources.IVirtualFolder;

/**
 * Manages the web.xml elements of interest to Facelet tag libraries
 * 
 * @author cbateman
 * 
 */
public class WebappConfiguration
{
    private static final String FACELET_LIBRARIES_CONTEXT_PARAM_NAME = "facelets.LIBRARIES"; //$NON-NLS-1$
    private final IProject      _project;
    /**
     * Cached instance of ContextParamAdapter.
     */
    private final ContextParamAdapter _contextParamAdapter;
    private List<IFile>         _cachedFiles;

    /**
     * @param project
     */
    /*package*/ WebappConfiguration(final IProject project)
    {
        _project = project;
        _contextParamAdapter = new ContextParamAdapter();
    }

    /**
     * @param listener
     */
    public void addListener(final WebappListener listener)
    {
        _contextParamAdapter.addListener(listener);
    }
    
    /**
     * @param listener
     */
    public void removeListener(final WebappListener listener)
    {
        _contextParamAdapter.removeListener(listener);
    }

    /**
     * @return the list of IFile's 
     */
    public List<IFile> getFiles()
    {
        final IVirtualFolder folder = JSFAppConfigUtils
                .getWebContentFolder(_project);

        if (folder == null)
        {
            return Collections.emptyList();
        }

        final List<String> filenames = getConfigFilesFromContextParam(_project);
        final List<IFile> files = new ArrayList<IFile>();

        for (final String filename : filenames)
        {
            final IVirtualFile vfile = folder.getFile(new Path(filename));
            if (vfile != null)
            {
                files.add(vfile.getUnderlyingFile());
            }
        }
        _cachedFiles = files;
        return Collections.unmodifiableList(files);
    }

    private Object getModelObject()
    {
        final IModelProvider provider = ModelProviderManager
                .getModelProvider(_project);
        return provider.getModelObject();
    }

    /**
     * 
     */
    public void start()
    {
        final Object webAppObj = getModelObject();
        if (webAppObj != null)
        {
            if (webAppObj instanceof WebApp)
            {
                startLocatingJ2EEConfigs((WebApp) webAppObj);
            }
            else if (webAppObj instanceof org.eclipse.jst.javaee.web.WebApp)
            {
                startLocatingJEEConfigs((org.eclipse.jst.javaee.web.WebApp) webAppObj);
            }
        }
        else
        {
            FaceletCorePlugin
                    .log(
                            "Could not get webApp for project: " + _project, new Exception()); //$NON-NLS-1$
        }
    }

    /**
     * 
     */
    public void dispose()
    {
        if (_contextParamAdapter != null)
        {
            final Object webAppObj = getModelObject();
            if (webAppObj != null)
            {
                if (webAppObj instanceof WebApp)
                {
                    stopLocatingJ2EEConfigs((WebApp) webAppObj);
                }
                else if (webAppObj instanceof org.eclipse.jst.javaee.web.WebApp)
                {
                    stopLocatingJEEConfigs((org.eclipse.jst.javaee.web.WebApp) webAppObj);
                }
            }
            else
            {
                FaceletCorePlugin
                        .log(
                                "Failed stopping locator for project: " + _project.getName() //$NON-NLS-1$
                                , new Exception());
            }
            //_contextParamAdapter.dispose();
        }
    }

    private void startLocatingJ2EEConfigs(final WebApp webApp)
    {
        webApp.eAdapters().add(_contextParamAdapter);
        final EList contexts = webApp.getContexts();
        if (contexts != null)
        {
            final Iterator itContexts = contexts.iterator();
            while (itContexts.hasNext())
            {
                final ContextParam contextParam = (ContextParam) itContexts
                        .next();
                contextParam.eAdapters().add(_contextParamAdapter);
            }
        }
        final EList contextParams = webApp.getContextParams();
        if (contextParams != null)
        {
            final Iterator itContextParams = contextParams.iterator();
            while (itContextParams.hasNext())
            {
                final ParamValue paramValue = (ParamValue) itContextParams
                        .next();
                paramValue.eAdapters().add(_contextParamAdapter);
            }
        }
    }

    private void startLocatingJEEConfigs(
            final org.eclipse.jst.javaee.web.WebApp webApp)
    {
        ((EObject) webApp).eAdapters().add(_contextParamAdapter);
        // System.out.println(((EObject)webApp).eDeliver());
        final List params = webApp.getContextParams();
        if (params != null)
        {
            final Iterator itContexts = params.iterator();
            while (itContexts.hasNext())
            {
                final EObject contextParam = (EObject) itContexts.next();
                contextParam.eAdapters().add(_contextParamAdapter);
            }
        }
    }

    private void stopLocatingJ2EEConfigs(final WebApp webApp)
    {
        webApp.eAdapters().remove(_contextParamAdapter);
        final EList contexts = webApp.getContexts();
        if (contexts != null)
        {
            final Iterator itContexts = contexts.iterator();
            while (itContexts.hasNext())
            {
                final ContextParam contextParam = (ContextParam) itContexts
                        .next();
                contextParam.eAdapters().remove(_contextParamAdapter);
            }
        }
        final EList contextParams = webApp.getContextParams();
        if (contextParams != null)
        {
            final Iterator itContextParams = contextParams.iterator();
            while (itContextParams.hasNext())
            {
                final ParamValue paramValue = (ParamValue) itContextParams
                        .next();
                paramValue.eAdapters().remove(_contextParamAdapter);
            }
        }
    }

    private void stopLocatingJEEConfigs(
            final org.eclipse.jst.javaee.web.WebApp webApp)
    {
        ((EObject) webApp).eAdapters().remove(_contextParamAdapter);
        final List contextParams = webApp.getContextParams();
        if (contextParams != null)
        {
            final Iterator itContextParams = contextParams.iterator();
            while (itContextParams.hasNext())
            {
                final EObject paramValue = (EObject) itContextParams.next();
                paramValue.eAdapters().remove(_contextParamAdapter);
            }
        }
    }

    /**
     * Gets list of application configuration file names as listed in the JSF
     * CONFIG_FILES context parameter ("javax.faces.CONFIG_FILES"). Will return
     * an empty list if WebArtifactEdit is null, if WebApp is null, if context
     * parameter does not exist, or if trimmed context parameter's value is an
     * empty String.
     * 
     * @param project
     *            IProject instance for which to get the context parameter's
     *            value.
     * @return List of application configuration file names as listed in the JSF
     *         CONFIG_FILES context parameter ("javax.faces.CONFIG_FILES"); list
     *         may be empty.
     */
    public static List<String> getConfigFilesFromContextParam(
            final IProject project)
    {
        List<String> filesList = Collections.EMPTY_LIST;
        if (JSFAppConfigUtils.isValidJSFProject(project))
        {
            final IModelProvider provider = ModelProviderManager
                    .getModelProvider(project);
            final Object webAppObj = provider.getModelObject();
            if (webAppObj != null)
            {
                if (webAppObj instanceof WebApp)
                {
                    filesList = getConfigFilesForJ2EEApp(project);
                }
                else if (webAppObj instanceof org.eclipse.jst.javaee.web.WebApp)
                {
                    filesList = getConfigFilesForJEEApp((org.eclipse.jst.javaee.web.WebApp) webAppObj);
                }
            }

        }
        return filesList;
    }

    private static List<String> getConfigFilesForJEEApp(
            final org.eclipse.jst.javaee.web.WebApp webApp)
    {
        String filesString = null;
        final List contextParams = webApp.getContextParams();
        final Iterator itContextParams = contextParams.iterator();
        while (itContextParams.hasNext())
        {
            final org.eclipse.jst.javaee.core.ParamValue paramValue = (org.eclipse.jst.javaee.core.ParamValue) itContextParams
                    .next();
            if (paramValue.getParamName().equals(
                    FACELET_LIBRARIES_CONTEXT_PARAM_NAME))
            {
                filesString = paramValue.getParamValue();
                break;
            }
        }
        return parseFilesString(filesString);
    }

    private static List<String> getConfigFilesForJ2EEApp(final IProject project)
    {
        List filesList = new ArrayList();
        final WebArtifactEdit webArtifactEdit = WebArtifactEdit
                .getWebArtifactEditForRead(project);
        if (webArtifactEdit != null)
        {
            try
            {
                WebApp webApp = null;
                try
                {
                    webApp = webArtifactEdit.getWebApp();
                }
                catch (final ClassCastException cce)
                {
                    // occasionally thrown from WTP code in RC3 and possibly
                    // later
                    JSFCorePlugin.log(IStatus.ERROR, cce.getLocalizedMessage(),
                            cce);
                    return filesList;
                }
                if (webApp != null)
                {
                    String filesString = null;
                    // need to branch here due to model version differences
                    // (BugZilla #119442)
                    if (webApp.getVersionID() == J2EEVersionConstants.WEB_2_3_ID)
                    {
                        final EList contexts = webApp.getContexts();
                        final Iterator itContexts = contexts.iterator();
                        while (itContexts.hasNext())
                        {
                            final ContextParam contextParam = (ContextParam) itContexts
                                    .next();
                            if (contextParam.getParamName().equals(
                                    FACELET_LIBRARIES_CONTEXT_PARAM_NAME))
                            {
                                filesString = contextParam.getParamValue();
                                break;
                            }
                        }
                    }
                    else
                    {
                        final EList contextParams = webApp.getContextParams();
                        final Iterator itContextParams = contextParams
                                .iterator();
                        while (itContextParams.hasNext())
                        {
                            final ParamValue paramValue = (ParamValue) itContextParams
                                    .next();
                            if (paramValue.getName().equals(
                                    FACELET_LIBRARIES_CONTEXT_PARAM_NAME))
                            {
                                filesString = paramValue.getValue();
                                break;
                            }
                        }
                    }
                    filesList = parseFilesString(filesString);
                }
            }
            finally
            {
                webArtifactEdit.dispose();
            }
        }

        return filesList;
    }

    private static List parseFilesString(final String filesString)
    {
        final List filesList = new ArrayList();
        if (filesString != null && filesString.trim().length() > 0)
        {
            final StringTokenizer stFilesString = new StringTokenizer(
                    filesString, ";"); //$NON-NLS-1$
            while (stFilesString.hasMoreTokens())
            {
                final String configFile = stFilesString.nextToken().trim();
                filesList.add(configFile);
            }
        }
        return filesList;
    }

    /**
     * Adapter implementation used to monitor addition/removal of context-param
     * nodes and change in name of existing nodes in order to respond to changes
     * to the JSF CONFIG_FILES context-param.
     * 
     * @author Ian Trimble - Oracle
     */
    private class ContextParamAdapter extends AdapterImpl
    {
        private final CopyOnWriteArrayList<WebappListener> _listeners = new CopyOnWriteArrayList<WebappListener>();

        public void addListener(final WebappListener listener)
        {
            _listeners.addIfAbsent(listener);
        }

        public void removeListener(final WebappListener listener)
        {
            _listeners.remove(listener);
        }

        private void fireEvent(final WebappChangeEvent event)
        {
            for (final WebappListener listener : _listeners)
            {
                listener.webappChanged(event);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.
         * eclipse.emf.common.notify.Notification)
         */
        @Override
        public void notifyChanged(final Notification notification)
        {
            final Object objNotifier = notification.getNotifier();
            // System.out.println(objNotifier.toString());
            if (objNotifier instanceof WebApp
                    || objNotifier instanceof org.eclipse.jst.javaee.web.WebApp)
            {
                final int eventType = notification.getEventType();
                switch (eventType)
                {
                    case Notification.ADD:
                        final Object objNewValue = notification.getNewValue();
                        if (objNewValue instanceof ContextParam
                                || objNewValue instanceof org.eclipse.jst.javaee.core.ParamValue)
                        {
                            contextParamAdded((EObject) objNewValue);
                        }
                        else if (objNewValue instanceof ParamValue)
                        {
                            paramValueAdded((EObject) objNewValue);
                        }
                    break;
                    case Notification.REMOVE:
                        final Object objOldValue = notification.getOldValue();
                        if (objOldValue instanceof ContextParam
                                || objOldValue instanceof org.eclipse.jst.javaee.core.ParamValue)
                        {
                            contextParamRemoved((EObject) objOldValue);
                        }
                        else if (objOldValue instanceof ParamValue)
                        {
                            paramValueRemoved((EObject) objOldValue);
                        }
                    break;
                }
            }
            // else if (objNotifier instanceof ContextParam
            // || objNotifier instanceof org.eclipse.jst.javaee.core.ParamValue)
            // {
            // if (notification.getEventType() != Notification.REMOVING_ADAPTER)
            // {
            // _listener
            // .tagLibChanged(ContextParamSpecifiedFaceletTaglibLocator.this);
            // }
            // }
            // else if (objNotifier instanceof ParamValue)
            // {
            // if (notification.getEventType() != Notification.REMOVING_ADAPTER)
            // {
            // _listener
            // .tagLibChanged(ContextParamSpecifiedFaceletTaglibLocator.this);
            // }
            // }
        }

        /**
         * Called when a new ContextParam instance is added.
         * 
         * @param contextParam
         *            ContextParam instance.
         */
        protected void contextParamAdded(final EObject contextParam)
        {
            if (isConfigFilesContextParam(contextParam))
            {
                checkAndFireFileChanges();
            }
            contextParam.eAdapters().add(this);
        }

        private void checkAndFireFileChanges()
        {
            final List<IFile> oldFiles = _cachedFiles;
            final List<IFile> newFiles = getFiles();

            final List<IFile> filesAdded = new ArrayList<IFile>();
            final List<IFile> filesRemoved = new ArrayList<IFile>();

            for (final IFile oldFile : oldFiles)
            {
                if (!newFiles.contains(oldFile))
                {
                    filesRemoved.add(oldFile);
                }
            }

            for (final IFile newFile : newFiles)
            {
                if (!oldFiles.contains(newFile))
                {
                    filesAdded.add(newFile);
                }
            }

            if (filesAdded.size() > 0 || filesRemoved.size() > 0)
            {
                fireEvent(new WebappChangeEvent(filesRemoved, filesAdded));
            }
        }

        /**
         * Called when a new ParamValue instance is added.
         * 
         * @param paramValue
         *            ParamValue instance.
         */
        protected void paramValueAdded(final EObject paramValue)
        {
            if (isConfigFilesParamValue(paramValue))
            {
                checkAndFireFileChanges();
            }
            paramValue.eAdapters().add(this);
        }

        /**
         * Called when a ContextParam instance is removed.
         * 
         * @param contextParam
         *            ContextParam instance.
         */
        protected void contextParamRemoved(final EObject contextParam)
        {
            if (isConfigFilesContextParam(contextParam))
            {
                checkAndFireFileChanges();
            }
            contextParam.eAdapters().remove(this);
        }

        /**
         * Called when a ParamValue instance is removed.
         * 
         * @param paramValue
         *            ParamValue instance.
         */
        protected void paramValueRemoved(final EObject paramValue)
        {
            if (isConfigFilesParamValue(paramValue))
            {
                checkAndFireFileChanges();
            }
            paramValue.eAdapters().remove(this);
        }

        /**
         * Tests if the passed ContextParam instance is the JSF CONFIG_FILES
         * context parameter.
         * 
         * @param contextParam
         *            ContextParam instance.
         * @return true if the passed ContextParam instance is the JSF
         *         CONFIG_FILES context parameter, else false
         */
        protected boolean isConfigFilesContextParam(final EObject contextParam)
        {
            boolean isConfigFiles = false;
            if (contextParam != null)
            {
                String name = null;
                if (contextParam instanceof ContextParam)
                {
                    name = ((ContextParam) contextParam).getParamName();
                }
                else if (contextParam instanceof org.eclipse.jst.javaee.core.ParamValue)
                {
                    name = ((org.eclipse.jst.javaee.core.ParamValue) contextParam)
                            .getParamName();
                }

                if (FACELET_LIBRARIES_CONTEXT_PARAM_NAME.equals(name))
                {
                    isConfigFiles = true;
                }
            }
            return isConfigFiles;
        }

        /**
         * Tests if the passed ParamValue instance is the JSF CONFIG_FILES
         * context parameter.
         * 
         * @param paramVal
         *            as EObject ParamValue instance.
         * @return true if the passed ParamValue instance is the JSF
         *         CONFIG_FILES context parameter, else false
         */
        protected boolean isConfigFilesParamValue(final EObject paramVal)
        {
            boolean isConfigFiles = false;
            if (paramVal != null)
            {
                String name = null;
                if (paramVal instanceof ParamValue)
                {
                    name = ((ParamValue) paramVal).getName();
                }
                else if (paramVal instanceof org.eclipse.jst.javaee.core.ParamValue)
                {
                    name = ((org.eclipse.jst.javaee.core.ParamValue) paramVal)
                            .getParamName();
                }

                if (FACELET_LIBRARIES_CONTEXT_PARAM_NAME.equals(name))
                {
                    isConfigFiles = true;
                }
            }
            return isConfigFiles;
        }
    }

    abstract static class WebappListener
    {
        public static class WebappChangeEvent
        {
            private final List<IFile> _removed;
            private final List<IFile> _added;

            WebappChangeEvent(final List<IFile> removed, final List<IFile> added)
            {
                _removed = Collections.unmodifiableList(removed);
                _added = Collections.unmodifiableList(added);
            }

            public final List<IFile> getRemoved()
            {
                return _removed;
            }

            public final List<IFile> getAdded()
            {
                return _added;
            }
        }

        public abstract void webappChanged(final WebappChangeEvent event);
    }
}
