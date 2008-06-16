package org.eclipse.jst.jsf.facelet.core.internal.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jst.jsf.common.internal.managedobject.IManagedObject;
import org.eclipse.jst.jsf.common.internal.managedobject.ObjectManager.ManagedObjectException;
import org.eclipse.jst.jsf.common.internal.policy.IdentifierOrderedIteratorPolicy;
import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.Namespace;
import org.eclipse.jst.jsf.core.internal.JSFCorePlugin;
import org.eclipse.jst.jsf.core.internal.JSFCoreTraceOptions;
import org.eclipse.jst.jsf.designtime.internal.view.model.AbstractTagRegistry;
import org.eclipse.jst.jsf.designtime.internal.view.model.jsp.CompositeTagResolvingStrategy;
import org.eclipse.jst.jsf.facelet.core.internal.FaceletCorePlugin;
import org.eclipse.jst.jsf.facelet.core.internal.FaceletCoreTraceOptions;
import org.eclipse.jst.jsf.facelet.core.internal.cm.FaceletDocumentFactory;
import org.eclipse.jst.jsf.facelet.core.internal.registry.IFaceletTagResolvingStrategy.TLDWrapper;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.FaceletTagIndex;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.IFaceletTagRecord;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.IProjectTaglibDescriptor;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.Listener;
import org.eclipse.jst.jsf.facelet.core.internal.tagmodel.FaceletNamespace;

/**
 * Registry of all facelet tag registries: at most one per project.
 * 
 */
public final class FaceletTagRegistry extends AbstractTagRegistry implements
        IManagedObject
{
    // INSTANCE
    private final ConcurrentLinkedQueue<LibraryOperation>   _changeOperations = new ConcurrentLinkedQueue<LibraryOperation>();

    private final IProject                                  _project;
    private final Map<String, FaceletNamespace>             _nsResolved;
    private final Set<FaceletNamespace>                     _unResolved;
    private final CompositeTagResolvingStrategy<TLDWrapper> _resolver;
    private final FaceletDocumentFactory                    _factory;
    private final LibraryOperationFactory                   _operationFactory = new LibraryOperationFactory(
                                                                                      this);
    private boolean                                         _isInitialized;

    private ChangeJob                                       _changeJob;
    private MyTaglibListener                                _listener;

    FaceletTagRegistry(final IProject project)
    {
        _project = project;
        _nsResolved = new HashMap<String, FaceletNamespace>();
        _unResolved = new HashSet<FaceletNamespace>();

        final List<String> ids = new ArrayList<String>();
        ids.add(VeryTemporaryDefaultFaceletResolver.ID);
        ids.add(FaceletTagResolvingStrategy.ID);
        final IdentifierOrderedIteratorPolicy<String> policy = new IdentifierOrderedIteratorPolicy<String>(
                ids);

        // exclude things that are not explicitly listed in the policy. That
        // way preference-based disablement will cause those strategies to
        // be excluded.
        policy.setExcludeNonExplicitValues(true);
        _resolver = new CompositeTagResolvingStrategy<TLDWrapper>(policy);

        _factory = new FaceletDocumentFactory(project);
        // add the strategies
        _resolver.addStrategy(new FaceletTagResolvingStrategy(_project,
                _factory));
        _resolver.addStrategy(new VeryTemporaryDefaultFaceletResolver(_project,
                _factory));
        // _resolver.addStrategy(new DefaultJSPTagResolver(_project));
        // makes sure that a tag element will always be created for any
        // given tag definition even if other methods fail
        // _resolver.addStrategy(new UnresolvedJSPTagResolvingStrategy());
        _changeJob = new ChangeJob(project.getName());
    }

    /**
     * @return a copy of all tag libs, both with namespaces resolved and without
     *         Changing the returned may has no effect on the registry, however
     *         the containned objects are not copies.
     */
    @Override
    public synchronized Collection<FaceletNamespace> getAllTagLibraries()
    {
        final Set<FaceletNamespace> allTagLibraries = new HashSet<FaceletNamespace>();
        if (!_isInitialized)
        {
            try
            {
                initialize(false);
                _isInitialized = true;
            }
            catch (final JavaModelException e)
            {
                FaceletCorePlugin.log("Problem during initialization", e); //$NON-NLS-1$
            }
            catch (final CoreException e)
            {
                FaceletCorePlugin.log("Problem during initialization", e); //$NON-NLS-1$
            }
        }
        allTagLibraries.addAll(_nsResolved.values());
        allTagLibraries.addAll(_unResolved);
        return allTagLibraries;
    }

    private void initialize(boolean fireEvent) throws JavaModelException, CoreException
    {
        if (!_project.exists() || !_project.hasNature(JavaCore.NATURE_ID))
        {
            throw new CoreException(new Status(IStatus.ERROR,
                    FaceletCorePlugin.PLUGIN_ID,
                    "Project either does not exists or is not a java project: " //$NON-NLS-1$
                            + _project));
        }

        final FaceletTagIndex index = FaceletTagIndex.getInstance();

        IProjectTaglibDescriptor tagDesc;
        try
        {
            tagDesc = index.getInstance(_project);
        }
        catch (ManagedObjectException e)
        {
            throw new CoreException(
                    new Status(
                            IStatus.ERROR,
                            FaceletCorePlugin.PLUGIN_ID,
                            "Error instantiating facelet tag index for project: " + _project.getName(), e)); //$NON-NLS-1$
        }

        if (tagDesc != null)
        {
            for (final IFaceletTagRecord taglib : tagDesc.getTagLibraries())
            {
                initialize(taglib, fireEvent);
            }

            _listener = new MyTaglibListener();
            tagDesc.addListener(_listener);
        }
    }

    FaceletNamespace initialize(final IFaceletTagRecord tagRecord,
            final boolean fireEvent)
    {
        if (JSFCoreTraceOptions.TRACE_JSPTAGREGISTRY_CHANGES)
        {
            FaceletCoreTraceOptions
                    .log("TLDTagRegistry.initialize_TagRecord: Initializing new tld record: " + tagRecord.toString()); //$NON-NLS-1$
        }
        // long startTime = 0;
        //
        // if (JSFCoreTraceOptions.TRACE_JSPTAGREGISTRY_PERF)
        // {
        // startTime = System.nanoTime();
        // }
        final FaceletNamespace ns = new FaceletNamespace(tagRecord, _resolver);
        _nsResolved.put(tagRecord.getURI(), ns);

        if (fireEvent)
        {
            fireEvent(new TagRegistryChangeEvent(this,
                    TagRegistryChangeEvent.EventType.ADDED_NAMESPACE,
                    Collections.singletonList(ns)));
        }

        // if (FaceletCoreTraceOptions.TRACE_JSPTAGREGISTRY_PERF)
        // {
        //                System.out.printf("Time to update namespace %s was %d\n", //$NON-NLS-1$
        // ns.getNSUri(), Long.valueOf(System.nanoTime()
        // - startTime));
        // }
        return ns;
    }

    void remove(final IFaceletTagRecord tagRecord)
    {
        final FaceletNamespace ns = _nsResolved.remove(tagRecord.getURI());

        if (ns != null)
        {
            fireEvent(new TagRegistryChangeEvent(this,
                    TagRegistryChangeEvent.EventType.REMOVED_NAMESPACE,
                    Collections.singletonList(ns)));
        }
    }

    @Override
    public synchronized Namespace getTagLibrary(final String uri)
    {
        // TODO:
        getAllTagLibraries();
        return _nsResolved.get(uri);
    }

    @Override
    protected Job getRefreshJob(final boolean flushCaches)
    {
        return new Job("Refreshing Facelet tag registry for " + _project.getName()) //$NON-NLS-1$
        {
            @Override
            protected IStatus run(final IProgressMonitor monitor)
            {
//                if (FaceletCoreTraceOptions.TRACE_JSPTAGREGISTRY)
//                {
//                    JSFCoreTraceOptions.log("FaceletTagRegistry.refresh: start"); //$NON-NLS-1$
//                }

                synchronized (FaceletTagRegistry.this)
                {
                    if (JSFCoreTraceOptions.TRACE_JSPTAGREGISTRY)
                    {
                        JSFCoreTraceOptions
                                .log("FaceletTagRegistry.refresh: start"); //$NON-NLS-1$
                    }

                    final List<Namespace> namespaces = new ArrayList(
                            _nsResolved.values());

                    if (flushCaches)
                    {
                        FaceletTagIndex.getInstance().flush(_project);
                    }
                    // if we aren't flushing caches, then check point the
                    // current namespace data, so it isn't lost when we clear
                    // THE NAMESPACES
                    else
                    {
                        checkpoint();
                    }

                    _nsResolved.clear();

                    fireEvent(new TagRegistryChangeEvent(FaceletTagRegistry.this,
                            TagRegistryChangeEvent.EventType.REMOVED_NAMESPACE,
                            namespaces));
                    try
                    {
                        initialize(true);
                    }
                    catch (JavaModelException e)
                    {
                        return new Status(IStatus.ERROR, FaceletCorePlugin.PLUGIN_ID, "Problem refreshing registry", e); //$NON-NLS-1$
                    }
                    catch (CoreException e)
                    {
                        return new Status(IStatus.ERROR, FaceletCorePlugin.PLUGIN_ID, "Problem refreshing registry", e); //$NON-NLS-1$
                    }

//                    if (JSFCoreTraceOptions.TRACE_JSPTAGREGISTRY)
//                    {
//                        JSFCoreTraceOptions
//                                .log("TLDTagRegistry.refresh: finished");
//                    }
                    return Status.OK_STATUS;
                }
            }
        };
    }

    private class MyTaglibListener extends Listener
    {
        @Override
        public void tagLibChanged(TaglibChangedEvent event)
        {
            switch (event.getChangeType())
            {
                case ADDED:
                    addLibraryOperation(_operationFactory
                            .createAddOperation(event.getNewValue()));
                break;
                case CHANGED:
                    addLibraryOperation(_operationFactory
                            .createChangeOperation(event.getNewValue()));
                break;
                case REMOVED:
                    addLibraryOperation(_operationFactory
                            .createRemoveOperation(event.getOldValue()));
                break;
            }
        }
    }

    private void addLibraryOperation(final LibraryOperation operation)
    {
        _changeOperations.add(operation);
        _changeJob.schedule();
    }

    private class ChangeJob extends Job
    {
        private int _rescheduleTime = -1;

        public ChangeJob(final String projectName)
        {
            super("Update job for project " + projectName); //$NON-NLS-1$
        }

        @Override
        protected IStatus run(final IProgressMonitor monitor)
        {
            synchronized (FaceletTagRegistry.this)
            {
                _rescheduleTime = -1;

                LibraryOperation operation = null;
                final MultiStatus multiStatus = new MultiStatus(
                        JSFCorePlugin.PLUGIN_ID, 0, "Result of change job", //$NON-NLS-1$
                        new Throwable());
                while ((operation = _changeOperations.poll()) != null)
                {
                    _rescheduleTime = 10000; // ms

                    operation.run();
                    multiStatus.add(operation.getResult());
                }

                if (_rescheduleTime >= 0 && !monitor.isCanceled())
                {
                    // if any operations were found on this run, reschedule
                    // to run again in 10seconds based on the assumption that
                    // events may be coming in bursts
                    schedule(_rescheduleTime);
                }

                return multiStatus;
            }
        }
    }

    @Override
    protected void doDispose()
    {
        if (_listener != null)
        {
            FaceletTagIndex index = FaceletTagIndex.getInstance();
            try
            {
                IProjectTaglibDescriptor instance = index.getInstance(_project);
                instance.removeListener(_listener);
            }
            catch (ManagedObjectException e)
            {
                FaceletCorePlugin
                        .log(
                                "Disposing facelet tag registry for project: " + _project.getName(), e); //$NON-NLS-1$
            }

            _nsResolved.clear();
        }
    }

    @Override
    protected void cleanupPersistentState()
    {
        // TODO ??

    }

    public void checkpoint()
    {
        // TODO ??

    }
}
