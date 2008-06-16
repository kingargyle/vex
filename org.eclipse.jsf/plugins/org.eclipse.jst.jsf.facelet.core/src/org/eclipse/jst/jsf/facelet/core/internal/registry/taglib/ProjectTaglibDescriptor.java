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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jem.internal.proxy.core.IConfigurationContributor;
import org.eclipse.jem.internal.proxy.core.ProxyFactoryRegistry;
import org.eclipse.jem.internal.proxy.ide.IDERegistration;
import org.eclipse.jst.jsf.core.JSFVersion;
import org.eclipse.jst.jsf.facelet.core.internal.FaceletCorePlugin;
import org.eclipse.jst.jsf.facelet.core.internal.registry.ELProxyContributor;
import org.eclipse.jst.jsf.facelet.core.internal.registry.ServletBeanProxyContributor;

class ProjectTaglibDescriptor implements IProjectTaglibDescriptor
{

    private ProxyFactoryRegistry                     _registry;
    private final AtomicInteger                      _isInitialized = new AtomicInteger(
                                                                            0);
    private final IProject                           _project;
    private final List<AbstractFaceletTaglibLocator> _locators;
    private final MyChangeListener                   _libChangeListener;
    private final Map<String, IFaceletTagRecord>     _tagRecords;

    public ProjectTaglibDescriptor(final IProject project)
    {
        _project = project;
        _locators = new ArrayList<AbstractFaceletTaglibLocator>();
        _tagRecords = new HashMap<String, IFaceletTagRecord>();

        try
        {
            _registry = createProxyRegistry(_project);
        }
        catch (final CoreException e)
        {
            FaceletCorePlugin.log("While creatinng proxy", e); //$NON-NLS-1$
        }

        final TagRecordFactory factory = new TagRecordFactory(project,
                _registry);
        _locators.add(new JarFileFaceletTaglibLocator(_project, factory));
        _locators.add(new ContextParamSpecifiedFaceletTaglibLocator(_project,
                factory));

        _libChangeListener = new MyChangeListener();
    }

    private static ProxyFactoryRegistry createProxyRegistry(
            final IProject project) throws CoreException
    {
        final IConfigurationContributor[] contributor = new IConfigurationContributor[]
        { new ServletBeanProxyContributor(JSFVersion.V1_1),
                new ELProxyContributor(project) };

        return IDERegistration.startAnImplementation(contributor, false,
                project, project.getName(), FaceletCorePlugin.PLUGIN_ID,
                new NullProgressMonitor());
    }

    private void initialize()
    {
        if (_isInitialized.addAndGet(1) == 1)
        {
            synchronized (this)
            {

                for (final AbstractFaceletTaglibLocator locator : _locators)
                {
                    SafeRunner.run(new ISafeRunnable()
                    {
                        public void handleException(final Throwable exception)
                        {
                            FaceletCorePlugin
                                    .log(
                                            "While locating facelet libraries on project: " + _project.getName(), new Exception(exception)); //$NON-NLS-1$
                        }

                        public void run() throws Exception
                        {
                            locator.addListener(_libChangeListener);
                            locator.startLocating();
                            _tagRecords.putAll(locator.locate());
                        }
                    });
                }
            }
        }
    }

    public Collection<? extends IFaceletTagRecord> getTagLibraries()
    {
        initialize();
        return Collections.unmodifiableCollection(_tagRecords.values());
    }

    void maybeLog(final Exception e)
    {
        if (_isInitialized.get() <= 1)
        {
            FaceletCorePlugin.log("Failed initializing taglib descriptor", e); //$NON-NLS-1$
        }
    }

    public IFaceletTagRecord getTagLibrary(final String uri)
    {
        initialize();
        return _tagRecords.get(uri);
    }

    public void addListener(final Listener listener)
    {
        for (final AbstractFaceletTaglibLocator locator : _locators)
        {
            locator.addListener(listener);
        }
    }

    public void removeListener(final Listener listener)
    {
        for (final AbstractFaceletTaglibLocator locator : _locators)
        {
            locator.removeListener(listener);
        }
    }

    public void checkpoint()
    {
        // TODO Auto-generated method stub
    }

    public void destroy()
    {
        // TODO Auto-generated method stub
    }

    public void dispose()
    {
        if (_registry != null)
        {
            _registry.terminateRegistry(false);

            for (final AbstractFaceletTaglibLocator locator : _locators)
            {
                locator.stopLocating();
            }
        }
    }

    private class MyChangeListener extends Listener
    {
        @Override
        public void tagLibChanged(final TaglibChangedEvent event)
        {
            switch (event.getChangeType())
            {
                case ADDED:
                    _tagRecords.put(event.getNewValue().getURI(), event
                            .getNewValue());
                break;
                case CHANGED:
                    _tagRecords.put(event.getNewValue().getURI(), event
                            .getNewValue());
                break;
                case REMOVED:
                    _tagRecords.remove(event.getOldValue());
                break;
            }
        }
    }
}
