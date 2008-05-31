package org.eclipse.jst.jsf.facelet.core.internal.registry;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jem.internal.proxy.core.IBeanProxy;
import org.eclipse.jem.internal.proxy.core.IBeanTypeProxy;
import org.eclipse.jem.internal.proxy.core.IBooleanBeanProxy;
import org.eclipse.jem.internal.proxy.core.IConfigurationContributor;
import org.eclipse.jem.internal.proxy.core.IFieldProxy;
import org.eclipse.jem.internal.proxy.core.IMethodProxy;
import org.eclipse.jem.internal.proxy.core.IStringBeanProxy;
import org.eclipse.jem.internal.proxy.core.ProxyFactoryRegistry;
import org.eclipse.jem.internal.proxy.core.ThrowableProxy;
import org.eclipse.jem.internal.proxy.ide.IDERegistration;
import org.eclipse.jst.jsf.common.internal.managedobject.IManagedObject;
import org.eclipse.jst.jsf.common.internal.policy.IdentifierOrderedIteratorPolicy;
import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.ITagElement;
import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.Namespace;
import org.eclipse.jst.jsf.core.JSFVersion;
import org.eclipse.jst.jsf.core.internal.jem.BeanProxyUtil;
import org.eclipse.jst.jsf.core.internal.jem.BeanProxyUtil.BeanProxyWrapper;
import org.eclipse.jst.jsf.core.internal.jem.BeanProxyUtil.ProxyException;
import org.eclipse.jst.jsf.designtime.internal.view.model.AbstractTagRegistry;
import org.eclipse.jst.jsf.designtime.internal.view.model.jsp.CompositeTagResolvingStrategy;
import org.eclipse.jst.jsf.designtime.internal.view.model.jsp.IAttributeAdvisor.NullAttributeAdvisor;
import org.eclipse.jst.jsf.facelet.core.internal.FaceletCorePlugin;
import org.eclipse.jst.jsf.facelet.core.internal.cm.FaceletDocumentFactory;
import org.eclipse.jst.jsf.facelet.core.internal.tagmodel.FaceletTaglib;
import org.eclipse.jst.jsf.facelet.core.internal.tagmodel.FaceletTaglibWithLibraryClass;
import org.eclipse.jst.jsf.facelet.core.internal.tagmodel.FaceletTaglibWithTags;
import org.eclipse.jst.jsf.facelet.core.internal.tagmodel.TagModelParser;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Registry of all facelet tag registries: at most one per project.
 * 
 */
public final class FaceletTagRegistry extends AbstractTagRegistry implements
        IManagedObject
{
    private static final String FACELET_TAGLIB_DTD_PATH = "/dtd/facelet-taglib_1_0.dtd"; //$NON-NLS-1$
    private static final String STATIC_MEMBER_NAMESPACE = "Namespace"; //$NON-NLS-1$
    private static final String METHOD_NAME_GET_NAMESPACE = "getNamespace"; //$NON-NLS-1$
    private static final String METHOD_NAME_GET_VALUE = "getValue"; //$NON-NLS-1$
    private static final String METHOD_NAME_GET_KEY = "getKey"; //$NON-NLS-1$
    private static final String METHOD_NAME_HAS_NEXT = "hasNext"; //$NON-NLS-1$
    private static final String METHOD_NAME_NEXT = "next"; //$NON-NLS-1$
    private static final String METHOD_NAME_ITERATOR = "iterator"; //$NON-NLS-1$
    private static final String METHOD_NAME_ENTRY_SET = "entrySet"; //$NON-NLS-1$
    private static final String PROPERTY_NAME_FACTORIES = "factories"; //$NON-NLS-1$
    private static final String QUALIFIED_CLASS_NAME__COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY = "com.sun.facelets.tag.AbstractTagLibrary"; //$NON-NLS-1$
    private static final String QUALIFIED_CLASS_NAME_JAVA_UTIL_MAP = "java.util.Map"; //$NON-NLS-1$
    // INSTANCE
    private final IProject                                         _project;
    private final Map<String, FaceletTaglib>                       _nsResolved;
    private final Set<FaceletTaglib>                               _unResolved;
    private final CompositeTagResolvingStrategy<FaceletTagElement> _resolver;
    private final FaceletDocumentFactory                           _factory;
    private ProxyFactoryRegistry                                   _registry;
    private boolean                                                _isInitialized;

    FaceletTagRegistry(final IProject project)
    {
        _project = project;
        _nsResolved = new HashMap<String, FaceletTaglib>();
        _unResolved = new HashSet<FaceletTaglib>();

        final List<String> ids = new ArrayList<String>();
        ids.add(VeryTemporaryDefaultFaceletResolver.ID);
        ids.add(FaceletTagResolvingStrategy.ID);
        final IdentifierOrderedIteratorPolicy<String> policy = new IdentifierOrderedIteratorPolicy<String>(
                ids);

        // exclude things that are not explicitly listed in the policy. That
        // way preference-based disablement will cause those strategies to
        // be excluded.
        policy.setExcludeNonExplicitValues(true);
        _resolver = new CompositeTagResolvingStrategy<FaceletTagElement>(policy);

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
    }

    /**
     * @return a copy of all tag libs, both with namespaces resolved and without
     *         Changing the returned may has no effect on the registry, however
     *         the containned objects are not copies.
     */
    @Override
    public synchronized Collection<FaceletTaglib> getAllTagLibraries()
    {
        final Set<FaceletTaglib> allTagLibraries = new HashSet<FaceletTaglib>();
        if (!_isInitialized)
        {
            try
            {
                initialize();
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
            catch (final IOException e)
            {
                FaceletCorePlugin.log("Problem during initialization", e); //$NON-NLS-1$
            }
        }
        allTagLibraries.addAll(_nsResolved.values());
        allTagLibraries.addAll(_unResolved);
        return allTagLibraries;
    }

    private void initialize() throws JavaModelException, CoreException,
            IOException
    {
        if (!_project.exists() || !_project.hasNature(JavaCore.NATURE_ID))
        {
            throw new CoreException(new Status(IStatus.ERROR,
                    FaceletCorePlugin.PLUGIN_ID,
                    "Project either does not exists or is not a java project: " //$NON-NLS-1$
                            + _project));
        }

        final IJavaProject javaProject = JavaCore.create(_project);
        final IClasspathEntry[] entries = javaProject
                .getResolvedClasspath(true);
        final InputStream dtdStream = getDefaultDTDSource();

        try
        {
            for (final IClasspathEntry entry : entries)
            {
                List<FaceletTaglib> tagLibsFound = null;

                switch (entry.getEntryKind())
                {
                    // this entry describes a source root in its project
                    case IClasspathEntry.CPE_SOURCE:

                    break;
                    // - this entry describes a folder or JAR containing
                    // binaries
                    case IClasspathEntry.CPE_LIBRARY:
                    {
                        tagLibsFound = processJar(entry, dtdStream);
                    }
                    break;
                    // - this entry describes another project
                    case IClasspathEntry.CPE_PROJECT:
                    break;
                    // - this entry describes a project or library indirectly
                    // via a
                    // classpath variable in the first segment of the path *
                    case IClasspathEntry.CPE_VARIABLE:
                    break;
                    // - this entry describes set of entries referenced
                    // indirectly
                    // via a classpath container
                    case IClasspathEntry.CPE_CONTAINER:
                    break;
                }

                if (tagLibsFound != null)
                {
                    for (final FaceletTaglib taglib : tagLibsFound)
                    {
                        if (taglib instanceof FaceletTaglibWithTags)
                        {
                            _nsResolved.put(taglib.getNSUri(), taglib);
                        }
                        else if (taglib instanceof FaceletTaglibWithLibraryClass)
                        {
                            // TODO: resolve the namespace using the class
                            final FaceletTaglib resolvedTaglib = resolveTaglib(((FaceletTaglibWithLibraryClass) taglib)
                                    .getLibraryClassName());
                            if (resolvedTaglib != null)
                            {
                                _nsResolved.put(resolvedTaglib.getNSUri(),
                                        resolvedTaglib);
                            }
                            else
                            {
                                _unResolved.add(taglib);
                            }
                        }
                    }
                }
            }
        }
        finally
        {
            if (dtdStream != null)
            {
                dtdStream.close();
            }
        }
    }

    /**
     * @param entry
     * @param defaultDtdStream
     */
    private List<FaceletTaglib> processJar(final IClasspathEntry entry,
            final InputStream defaultDtdStream)
    {
        JarFile jarFile = null;
        final List<FaceletTaglib> tagLibsFound = new LinkedList<FaceletTaglib>();

        try
        {
            jarFile = getJarFileFromCPE(entry);

            if (jarFile != null)
            {
                final Enumeration<JarEntry> jarEntries = jarFile.entries();
                while (jarEntries.hasMoreElements())
                {
                    final JarEntry jarEntry = jarEntries.nextElement();
                    final String name = jarEntry.getName();

                    if (name.startsWith("META-INF/") //$NON-NLS-1$
                            && name.endsWith(".taglib.xml")) //$NON-NLS-1$
                    {
                        InputStream is = null;
                        try
                        {
                            is = jarFile.getInputStream(jarEntry);

                            final byte[] buffer = getBufferForEntry(is);
                            final InputSource inputSource = new InputSource(
                                    new ByteArrayInputStream(buffer));

                            final Document doc = TagModelParser
                                    .getDefaultTaglibDocument(inputSource,
                                            new InputSource(
                                                    getDefaultDTDSource()));
                            final FaceletTaglib tagLib = TagModelParser
                                    .processDocument(doc, _factory,
                                            new NullAttributeAdvisor());
                            if (tagLib.getNSUri() != null)
                            {
                                // System.out.println("Namespace found: "
                                // + tagLib.getNSUri());
                            }
                            tagLibsFound.add(tagLib);
                        }
                        catch (final ParserConfigurationException e)
                        {
                            FaceletCorePlugin
                                    .log(
                                            "Error initializing facelet registry entry", //$NON-NLS-1$
                                            e);
                        }
                        catch (final IOException ioe)
                        {
                            FaceletCorePlugin
                                    .log(
                                            "Error initializing facelet registry entry", //$NON-NLS-1$
                                            ioe);
                        }
                        catch (final SAXException ioe)
                        {
                            FaceletCorePlugin
                                    .log(
                                            "Error initializing facelet registry entry", //$NON-NLS-1$
                                            ioe);
                        }
                        finally
                        {
                            if (is != null)
                            {
                                // is.close();
                            }
                        }
                    }
                }
            }
        }
        catch (final IOException e)
        {
            FaceletCorePlugin.log("Error opening classpath jar file", e); //$NON-NLS-1$
        }
        finally
        {
            if (jarFile != null)
            {
                try
                {
                    jarFile.close();
                }
                catch (final IOException ioe)
                {
                    FaceletCorePlugin.log("Error closing jar file", ioe); //$NON-NLS-1$
                }
            }
        }
        return tagLibsFound;
    }

    private FaceletTaglib resolveTaglib(final String className)
    {
        if (_registry == null)
        {
            try
            {
                final IConfigurationContributor[] contributor = new IConfigurationContributor[]
                { new ServletBeanProxyContributor(JSFVersion.V1_1),
                        new ELProxyContributor(_project) };

                _registry = IDERegistration.startAnImplementation(contributor,
                        false, _project, _project.getName(),
                        FaceletCorePlugin.PLUGIN_ID, new NullProgressMonitor());
            }
            catch (final CoreException e)
            {
                FaceletCorePlugin.log("Error starting vm for project: " //$NON-NLS-1$
                        + _project.getName(), e);
                return null;
            }
        }

        final IBeanTypeProxy typeProxy = _registry.getBeanTypeProxyFactory()
                .getBeanTypeProxy(className);

        if (typeProxy == null)
        {
            return null;
        }

        final BeanProxyWrapper classTypeWrapper = new BeanProxyWrapper(
                typeProxy);

        try
        {
            classTypeWrapper.init();
        }
        catch (final ProxyException e)
        {
            FaceletCorePlugin.log("Couldn't load class " + className, e); //$NON-NLS-1$
        }

        final String namespace = resolveNS(classTypeWrapper);
        System.out.println(namespace);
        if (namespace != null)
        {
            final Map<String, ITagElement> tags = resolveTags(namespace,
                    typeProxy, classTypeWrapper);

            return new FaceletTaglibWithLibraryClass(namespace, className, tags);
        }

        return null;
    }

    private Map<String, ITagElement> resolveTags(final String uri,
            final IBeanTypeProxy typeProxy, final BeanProxyWrapper beanProxy)
    {
        final Map<String, ITagElement> tags = new HashMap<String, ITagElement>();

        // if the tag factory is a child of AbstractTagFactory, then we
        // can try to get our hands on its private parts ...
        final IBeanTypeProxy mapTypeProxy = _registry.getBeanTypeProxyFactory()
                .getBeanTypeProxy(QUALIFIED_CLASS_NAME_JAVA_UTIL_MAP);
        final IBeanTypeProxy componentFactoryTypeProxy = _registry
                .getBeanTypeProxyFactory().getBeanTypeProxy(
                        QUALIFIED_CLASS_NAME__COM_SUN_FACELETS_TAG_ABSTRACT_TAG_LIBRARY);

        if (mapTypeProxy != null && componentFactoryTypeProxy != null)
        {
            final IFieldProxy fieldProxy = componentFactoryTypeProxy
                    .getDeclaredFieldProxy(PROPERTY_NAME_FACTORIES);

            if (fieldProxy != null)
            {
                if (fieldProxy.getFieldType().isKindOf(mapTypeProxy))
                {
                    IBeanProxy factories = null;

                    try
                    {
                        // need to turn off security checking on the private
                        // field
                        fieldProxy.setAccessible(true);
                        factories = fieldProxy.get(beanProxy.getInstance());
                    }
                    catch (final ThrowableProxy e)
                    {
                        FaceletCorePlugin.log(
                                "Error getting factories from bean instance", //$NON-NLS-1$
                                e);
                    }

                    if (factories != null)
                    {
                        final IMethodProxy entrySetMethod = fieldProxy
                                .getFieldType().getMethodProxy(METHOD_NAME_ENTRY_SET);
                        if (entrySetMethod != null)
                        {
                            try
                            {
                                entrySetMethod.setAccessible(true);
                                final IBeanProxy entrySetProxy = entrySetMethod
                                        .invoke(factories);

                                if (entrySetProxy != null)
                                {
                                    final IMethodProxy iteratorMethod = entrySetProxy
                                            .getTypeProxy().getMethodProxy(
                                                    METHOD_NAME_ITERATOR);
                                    iteratorMethod.setAccessible(true);
                                    final IBeanProxy iteratorProxy = iteratorMethod
                                            .invoke(entrySetProxy);

                                    if (iteratorProxy != null)
                                    {
                                        final IMethodProxy nextMethod = iteratorProxy
                                                .getTypeProxy().getMethodProxy(
                                                        METHOD_NAME_NEXT);
                                        nextMethod.setAccessible(true);
                                        final IMethodProxy hasNextMethod = iteratorProxy
                                                .getTypeProxy().getMethodProxy(
                                                        METHOD_NAME_HAS_NEXT);
                                        hasNextMethod.setAccessible(true);

                                        while (((IBooleanBeanProxy) hasNextMethod
                                                .invoke(iteratorProxy))
                                                .booleanValue())
                                        {
                                            final IBeanProxy entryProxy = nextMethod
                                                    .invoke(iteratorProxy);
                                            final IMethodProxy getKeyProxy = entryProxy
                                                    .getTypeProxy()
                                                    .getMethodProxy(METHOD_NAME_GET_KEY);
                                            final IMethodProxy getValueProxy = entryProxy
                                                    .getTypeProxy()
                                                    .getMethodProxy(METHOD_NAME_GET_VALUE);
                                            if (getKeyProxy != null
                                                    && getValueProxy != null)
                                            {
                                                getKeyProxy.setAccessible(true);
                                                final IBeanProxy key = getKeyProxy
                                                        .invoke(entryProxy);

                                                if (key instanceof IStringBeanProxy)
                                                {
                                                    final String name = ((IStringBeanProxy) key)
                                                            .stringValue();
                                                    getValueProxy
                                                            .setAccessible(true);
                                                    final IBeanProxy value = getValueProxy
                                                            .invoke(entryProxy);

                                                    if (value != null)
                                                    {
                                                        final FaceletTagElement input = new FaceletTagElement(
                                                                uri, name,
                                                                value,
                                                                _registry);
                                                        final ITagElement tag = _resolver
                                                                .resolve(input);

                                                        if (tag != _resolver
                                                                .getNoResult())
                                                        {
                                                            tags.put(name, tag);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }

                            }
                            catch (final ThrowableProxy e)
                            {
                                FaceletCorePlugin.log(
                                        "Error invoking entrySet", e); //$NON-NLS-1$
                            }
                        }
                    }

                }
            }
        }

        return tags;
    }

    private String resolveNS(final BeanProxyWrapper beanProxy)
    {

        // final IMethodProxy methodProxy = typeProxy
        // .getMethodProxy("getNamespace");

        IBeanProxy resultProxy = null;
        try
        {
            resultProxy = beanProxy.call(METHOD_NAME_GET_NAMESPACE);

            if (resultProxy instanceof IStringBeanProxy)
            {
                return ((IStringBeanProxy) resultProxy).stringValue();
            }
        }
        catch (final BeanProxyUtil.ProxyException e)
        {
            // fall through
        }

        return resolveNSAggressively(beanProxy);
    }

    private String resolveNSAggressively(final BeanProxyWrapper beanProxy)
    {
        try
        {
            return beanProxy.getStringFieldValue(STATIC_MEMBER_NAMESPACE);
        }
        catch (final ProxyException e)
        {
            // fall through
        }
        return null;
    }

    private byte[] getBufferForEntry(final InputStream is)
    {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        final byte[] buffer = new byte[2048];

        int bytesRead = 0;

        try
        {
            while (((bytesRead = is.read(buffer))) != -1)
            {
                stream.write(buffer, 0, bytesRead);
            }
        }
        catch (final IOException e)
        {
            FaceletCorePlugin.log("Error loading buffer", e); //$NON-NLS-1$
            return null;
        }

        return stream.toByteArray();
    }

    /**
     * TODO: Merge into JSFAppConfigUtils.
     * 
     * @param entry
     * @return
     */
    private static JarFile getJarFileFromCPE(final IClasspathEntry entry)
            throws IOException
    {
        if (entry.getEntryKind() == IClasspathEntry.CPE_LIBRARY)
        {
            IPath libraryPath = entry.getPath();
            if (libraryPath.getFileExtension() != null
                    && libraryPath.getFileExtension().length() > 0)
            {
                final IWorkspaceRoot workspaceRoot = ResourcesPlugin
                        .getWorkspace().getRoot();
                if (libraryPath.getDevice() == null
                        && workspaceRoot.getProject(libraryPath.segment(0))
                                .exists())
                {
                    libraryPath = workspaceRoot.getFile(libraryPath)
                            .getLocation();
                }
                final String libraryPathString = libraryPath.toString();
                return new JarFile(libraryPathString);
            }
        }
        return null;
    }

    private static InputStream getDefaultDTDSource() throws IOException
    {
        final URL url = FaceletCorePlugin.getDefault().getBundle().getEntry(
                FACELET_TAGLIB_DTD_PATH);

        if (url != null)
        {
            return url.openStream();
        }
        return null;
    }

    @Override
    public synchronized Namespace getTagLibrary(final String uri)
    {
        // TODO:
        getAllTagLibraries();
        return _nsResolved.get(uri);
    }

    @Override
    public Job getRefreshJob(final boolean flushCaches)
    {
        return new Job(
                Messages.FaceletTagRegistry_TAG_REGISTRY_REFRESH_JOB_DESCRIPTION
                        + _project.getName())
        {
            @Override
            protected IStatus run(final IProgressMonitor monitor)
            {
                // TODO: possible optimization would be to start with known
                // namespaces
                // and see if they have changed
                _nsResolved.clear();
                _unResolved.clear();

                IStatus status = Status.OK_STATUS;
                try
                {
                    initialize();
                }
                catch (final JavaModelException e)
                {
                    status = new Status(IStatus.ERROR,
                            FaceletCorePlugin.PLUGIN_ID,
                            "Error refreshing facelet library registry", e); //$NON-NLS-1$
                }
                catch (final CoreException e)
                {
                    status = new Status(IStatus.ERROR,
                            FaceletCorePlugin.PLUGIN_ID,
                            "Error refreshing facelet library registry", e); //$NON-NLS-1$
                }
                catch (final IOException e)
                {
                    status = new Status(IStatus.ERROR,
                            FaceletCorePlugin.PLUGIN_ID,
                            "Error refreshing facelet library registry", e); //$NON-NLS-1$
                }
                return status;
            }
        };
    }

    @Override
    protected void doDispose()
    {
        // TODO Auto-generated method stub

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
