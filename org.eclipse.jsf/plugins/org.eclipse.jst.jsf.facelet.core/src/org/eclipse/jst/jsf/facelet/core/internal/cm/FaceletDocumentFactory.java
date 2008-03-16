package org.eclipse.jst.jsf.facelet.core.internal.cm;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.Namespace;
import org.eclipse.jst.jsf.designtime.internal.view.model.ITagRegistry;
import org.eclipse.jst.jsf.designtime.internal.view.model.TagRegistryFactory.TagRegistryFactoryException;
import org.eclipse.jst.jsf.facelet.core.internal.registry.FaceletRegistryManager.MyRegistryFactory;
import org.eclipse.jst.jsf.facelet.core.internal.util.ViewUtil;
import org.eclipse.jst.jsf.facelet.core.internal.util.ViewUtil.PrefixEntry;
import org.eclipse.wst.xml.core.internal.contentmodel.CMDocument;
import org.eclipse.wst.xml.core.internal.contentmodel.CMElementDeclaration;
import org.w3c.dom.Element;

public class FaceletDocumentFactory
{
    private  final IProject _project;
    private final Map<String, NamespaceCMAdapter> _cmDocuments;

    public FaceletDocumentFactory(final IProject project)
    {
        _project = project;
        _cmDocuments = new HashMap<String, NamespaceCMAdapter>(8);
    }

    public CMDocument createCMDocument(final String uri)
    {
        return getNamespace(uri);
    }

    public CMDocument createCMDocumentForContext(final String uri, final String prefix)
    {
        final NamespaceCMAdapter cmDoc = getOrCreateCMDocument(_project, uri);

        if (cmDoc != null)
        {
            return new DocumentNamespaceCMAdapter(cmDoc, prefix);
        }
        return null;
    }

    public CMElementDeclaration createCMElementDeclaration(
            final IProject project, final Element element)
    {
        final String prefix = element.getPrefix();
        final Map<String, PrefixEntry> namespaces = ViewUtil.getDocumentNamespaces(element
                .getOwnerDocument());
        final PrefixEntry prefixEntry = namespaces.get(prefix);

        if (prefixEntry != null)
        {
            final CMDocument cmDoc = createCMDocumentForContext(
                    prefixEntry.getUri(), prefixEntry.getPrefix());

            if (cmDoc != null)
            {
                return (CMElementDeclaration) cmDoc.getElements().getNamedItem(
                        element.getLocalName());
            }
        }

        return null;
    }


    private NamespaceCMAdapter getNamespace(final String uri)
    {
        final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
        .getProjects();
        NamespaceCMAdapter ns = null;

        FIND_PROJECT: for (final IProject project : projects)
        {
            if (project.isAccessible())
            {
                ns =
                    getOrCreateCMDocument(project, uri);
                if (ns != null)
                {
                    break FIND_PROJECT;
                }
            }
        }
        return ns;
    }
    //    private synchronized NamespaceCMAdapter getOrCreateCMDocument(final String uri)
    //    {
    //        NamespaceCMAdapter doc = _cmDocuments.get(uri);
    //
    //        if (doc == null)
    //        {
    //            IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
    //                    .getProjects();
    //            Namespace ns = null;
    //
    //            IProject foundProject = null;
    //            FIND_PROJECT: for (final IProject project : projects)
    //            {
    //                if (project.isAccessible())
    //                {
    //                    try
    //                    {
    //
    //                        if (ns != null)
    //                        {
    //                            foundProject = project;
    //                            break FIND_PROJECT;
    //                        }
    //                    }
    //                    catch (TagRegistryFactoryException e)
    //                    {
    //                        // do nothing
    //                    }
    //                }
    //            }
    //            if (ns != null)
    //            {
    //                doc = getOrCreateD
    //            }
    //        }
    //        return doc;
    //    }

    private NamespaceCMAdapter getOrCreateCMDocument(final IProject project, final String uri)
    {
        NamespaceCMAdapter       adapter = _cmDocuments.get(uri);
        
        if (adapter == null)
        {
            final MyRegistryFactory factory = new MyRegistryFactory();
    
            ITagRegistry registry;
            try
            {
                registry = factory.createTagRegistry(project);
                final Namespace ns = registry.getTagLibrary(uri);
    
                if (ns != null)
                {
                    adapter = new NamespaceCMAdapter(ns, project);
                    _cmDocuments.put(uri, adapter);
                }
            }
            catch (final TagRegistryFactoryException e)
            {
                // fall-through
            }
        }
        return adapter;
    }
    
}
