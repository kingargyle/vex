/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Ian Trimble - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.jsf.facelet.core.internal.registry.taglib;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jst.jsf.common.internal.managedobject.AbstractManagedObject;
import org.eclipse.jst.jsf.common.internal.managedobject.ObjectManager.ManagedObjectException;
import org.eclipse.jst.jsf.common.internal.resource.IResourceLifecycleListener;
import org.eclipse.jst.jsf.common.internal.resource.ResourceLifecycleEvent;
import org.eclipse.jst.jsf.common.internal.resource.ResourceSingletonObjectManager;
import org.eclipse.jst.jsf.common.internal.resource.ResourceLifecycleEvent.EventType;
import org.eclipse.jst.jsf.facelet.core.internal.FaceletCorePlugin;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.Listener.TaglibChangedEvent;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.Listener.TaglibChangedEvent.CHANGE_TYPE;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.WebappConfiguration.WebappListener;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibDefn;
import org.xml.sax.SAXException;

/**
 * Attempts to locate Facelet taglib's specified as xml files in project
 * relative paths specified in the Facelet.LIBRARIES servlet parameters.
 * 
 * @author Based on class in org.eclipse.jst.jsf.coreby Ian Trimble - Oracle
 * 
 *         TODO:merge back with common code in JSFAppConfig framework
 */
/* package */class ContextParamSpecifiedFaceletTaglibLocator extends
        AbstractFaceletTaglibLocator
{
    private final IProject                       _project;
    private final Map<String, IFaceletTagRecord> _records;
    private final TagRecordFactory               _factory;
    private final TaglibFileManager              _fileManager;

    public ContextParamSpecifiedFaceletTaglibLocator(final IProject project,
            final TagRecordFactory factory)
    {
        _project = project;
        _records = new HashMap<String, IFaceletTagRecord>();
        _factory = factory;
        _fileManager = new TaglibFileManager(project,
                new LibraryChangeHandler());
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.jst.jsf.core.jsfappconfig.AbstractJSFAppConfigLocater#
     * startLocating()
     */
    @Override
    public void startLocating()
    {
        _fileManager.initFiles();
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.jst.jsf.core.jsfappconfig.AbstractJSFAppConfigLocater#
     * stopLocating()
     */
    @Override
    public void stopLocating()
    {

        _fileManager.dispose();
    }


    @Override
    public Map<String, ? extends IFaceletTagRecord> locate()
    {
        return findInWebRoot();
    }

    private Map<String, ? extends IFaceletTagRecord> findInWebRoot()
    {
        final List<IFile> files = _fileManager.getFiles();

        _records.clear();

        for (final IFile file : files)
        {
            if (file.exists())
            {
                TaglibFileTracker tracker = null;
                try
                {
                    tracker = _fileManager.getInstance(file);
                }
                catch (final ManagedObjectException e)
                {
                    FaceletCorePlugin.log("Creating record", e); //$NON-NLS-1$
                }

                final IFaceletTagRecord record = createTagRecord(file);
                if (record != null)
                {
                    _records.put(record.getURI(), record);
                    if (tracker != null)
                    {
                        tracker.setUri(record.getURI());
                    }
                }
            }
        }

        return _records;
    }

    private IFaceletTagRecord createTagRecord(final IFile file)
    {
        InputStream is = null;
        try
        {
            is = file.getContents();
            FaceletTaglibDefn taglib = TagModelParser.loadFromInputStream(is, null);
            if (taglib != null)
            {
                return _factory.createRecords(taglib);
            }
        }
        catch (final CoreException e)
        {
            FaceletCorePlugin
                    .log(
                            "Loading web root taglibs for project: " + _project.getName(), e); //$NON-NLS-1$
        }
        catch (final IOException e)
        {
            FaceletCorePlugin
                    .log(
                            "Loading web root taglibs for project: " + _project.getName(), e); //$NON-NLS-1$
        }
        catch (final ParserConfigurationException e)
        {
            FaceletCorePlugin
                    .log(
                            "Loading web root taglibs for project: " + _project.getName(), e); //$NON-NLS-1$
        }
        catch (final SAXException e)
        {
            FaceletCorePlugin
                    .log(
                            "Loading web root taglibs for project: " + _project.getName(), e); //$NON-NLS-1$
        }
        finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (final IOException e)
                {
                    FaceletCorePlugin.log("Closing taglib.xml", e); //$NON-NLS-1$
                }
            }
        }
        return null;
    }

    private static class TaglibFileManager extends
            ResourceSingletonObjectManager<TaglibFileTracker, IFile>
    {
        private final LibraryChangeHandler    _handler;
        private final WebappConfiguration     _webAppConfiguration;
        private final IResourceChangeListener _newFileListener;

        public TaglibFileManager(final IProject project,
                final LibraryChangeHandler handler)
        {
            _handler = handler;
            _webAppConfiguration = new WebappConfiguration(project);
            // TODO: fold into LifecycleListener
            _newFileListener = new IResourceChangeListener()
            {
                public void resourceChanged(final IResourceChangeEvent event)
                {
                    // if the event is post change && has the same parent
                    // project
                    if (event.getType() == IResourceChangeEvent.POST_CHANGE
                            && event.getDelta().findMember(
                                    project.getFullPath()) != null)
                    {
                        for (final IFile file : _webAppConfiguration.getFiles())
                        {
                            final IResourceDelta delta = event.getDelta()
                                    .findMember(file.getFullPath());

                            if (delta != null)
                            {
                                if (delta.getKind() == IResourceDelta.ADDED)
                                {
                                    _handler.added(file);
                                }
                            }
                        }
                    }
                }
            };

            ResourcesPlugin.getWorkspace().addResourceChangeListener(
                    _newFileListener);
        }

        public List<IFile> getFiles()
        {
            return _webAppConfiguration.getFiles();
        }

        public void initFiles()
        {
            _webAppConfiguration.start();
            _webAppConfiguration.addListener(new WebappListener()
            {
                @Override
                public void webappChanged(WebappChangeEvent event)
                {
                    for (final IFile file : event.getRemoved())
                    {
                        TaglibFileTracker tracker;
                        try
                        {
                            tracker = getInstance(file);
                            _handler.removed(tracker._uri, file);
                        }
                        catch (ManagedObjectException e)
                        {
                            FaceletCorePlugin.log("While removing for webapp change", e); //$NON-NLS-1$
                        }
                    }
                    
                    for (final IFile file  : event.getAdded())
                    {
                        _handler.added(file);
                    }
                }
            });
        }

        @Override
        protected TaglibFileTracker createNewInstance(final IFile file)
        {
            return new TaglibFileTracker(file, this, _handler);
        }

        public void addListener(final IResourceLifecycleListener listener)
        {
            super.addLifecycleEventListener(listener);
        }

        public void removeListener(final IResourceLifecycleListener listener)
        {
            super.removeLifecycleEventListener(listener);
        }

        public void dispose()
        {
            ResourcesPlugin.getWorkspace().removeResourceChangeListener(
                    _newFileListener);

            final Collection<IFile> managedResources = getManagedResources();

            for (final IFile file : managedResources)
            {
                unmanageResource(file);
            }
            
            _webAppConfiguration.dispose();
        }
    }

    private static class TaglibFileTracker extends AbstractManagedObject
            implements IResourceLifecycleListener
    {
        private final IFile                _file;
        private String                     _uri;
        private final AtomicLong           _lastModifiedStamp = new AtomicLong();
        private TaglibFileManager          _manager;
        private final LibraryChangeHandler _handler;

        public TaglibFileTracker(final IFile file,
                final TaglibFileManager manager,
                final LibraryChangeHandler handler)
        {
            _manager = manager;
            _manager.addListener(this);
            _file = file;
            _lastModifiedStamp.set(file.getModificationStamp());
            _handler = handler;
        }

        public final void setUri(final String uri)
        {
            _uri = uri;
        }

        @Override
        public void checkpoint()
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void destroy()
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void dispose()
        {
            _manager.removeListener(this);
            _manager = null;
        }

        public EventResult acceptEvent(final ResourceLifecycleEvent event)
        {
            if (!_file.equals(event.getAffectedResource()))
            {
                return EventResult.getDefaultEventResult();
            }

            final EventType eventType = event.getEventType();

            switch (eventType)
            {
                case RESOURCE_ADDED:
                    // added resources kick an add event.
                    _handler.added(_file);
                break;
                case RESOURCE_CHANGED:
                    // changed resources kick a change event
                    _handler.changed(_uri, _file);
                break;
                case RESOURCE_INACCESSIBLE:
                    // removed resources kick a remove event
                    _handler.removed(_uri, _file);
                break;
            }

            return EventResult.getDefaultEventResult();
        }

    }

    private class LibraryChangeHandler
    {
        public void added(final IFile file)
        {
            final IFaceletTagRecord tagRecord = createTagRecord(file);
            TaglibFileTracker tracker = null;
            try
            {
                tracker = _fileManager
                        .getInstance(file);
            }
            catch (final ManagedObjectException e)
            {
                FaceletCorePlugin.log("Adding new library", e); //$NON-NLS-1$
            }

            if (tagRecord != null)
            {

                _records.put(tagRecord.getURI(), tagRecord);
                if (tracker != null)
                {
                    tracker.setUri(tagRecord.getURI());
                }

                fireEvent(new TaglibChangedEvent(
                        ContextParamSpecifiedFaceletTaglibLocator.this, null,
                        tagRecord, CHANGE_TYPE.ADDED));
            }
        }

        public void removed(final String uri, final IFile file)
        {
            final IFaceletTagRecord tagRecord = _records.remove(uri);
            fireEvent(new TaglibChangedEvent(
                    ContextParamSpecifiedFaceletTaglibLocator.this, tagRecord,
                    null, CHANGE_TYPE.REMOVED));
        }

        public void changed(final String uri, final IFile file)
        {
            final IFaceletTagRecord oldValue = _records.remove(uri);
            final IFaceletTagRecord newValue = createTagRecord(file);
            if (newValue != null)
            {
                _records.put(uri, newValue);
                fireEvent(new TaglibChangedEvent(
                        ContextParamSpecifiedFaceletTaglibLocator.this,
                        oldValue, newValue, CHANGE_TYPE.CHANGED));
            }
        }
    }
}
