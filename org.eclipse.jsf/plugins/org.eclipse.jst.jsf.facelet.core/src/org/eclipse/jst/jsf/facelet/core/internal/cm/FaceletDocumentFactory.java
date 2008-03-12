package org.eclipse.jst.jsf.facelet.core.internal.cm;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.Namespace;
import org.eclipse.jst.jsf.designtime.internal.view.model.ITagRegistry;
import org.eclipse.jst.jsf.designtime.internal.view.model.TagRegistryFactory.TagRegistryFactoryException;
import org.eclipse.jst.jsf.facelet.core.internal.registry.FaceletRegistryManager.MyRegistryFactory;
import org.eclipse.wst.xml.core.internal.contentmodel.CMDocument;
import org.eclipse.wst.xml.core.internal.contentmodel.CMElementDeclaration;
import org.eclipse.wst.xml.core.internal.contentmodel.factory.CMDocumentFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class FaceletDocumentFactory implements CMDocumentFactory
{

    //private final Map<String, NamespaceCMAdapter> _cmDocuments;

    public FaceletDocumentFactory()
    {
        //_cmDocuments = new HashMap<String, NamespaceCMAdapter>();
    }

    public CMDocument createCMDocument(String uri)
    {
        return getNamespace(uri);
    }
    
    public CMDocument createCMDocumentForContext(final IProject project,
            final String uri, final String prefix)
    {
        NamespaceCMAdapter cmDoc = getOrCreateCMDocument(project, uri);
        
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
        final Map<String, PrefixEntry> namespaces = getDocumentNamespaces(element
                .getOwnerDocument());
        final PrefixEntry prefixEntry = namespaces.get(prefix);

        if (prefixEntry != null)
        {
            final CMDocument cmDoc = createCMDocumentForContext(project,
                    prefixEntry.getUri(), prefixEntry.getPrefix());

            if (cmDoc != null)
            {
                return (CMElementDeclaration) cmDoc.getElements().getNamedItem(
                        element.getLocalName());
            }
        }

        return null;
    }
    
    public Map<String, PrefixEntry> getDocumentNamespaces(final Document doc)
    {
        final Map<String, PrefixEntry> namespaces = new HashMap<String, PrefixEntry>();

        final Element rootElement = doc.getDocumentElement();

        if (rootElement != null)
        {
            final NamedNodeMap attrs = rootElement.getAttributes();
            for (int i = 0; i < attrs.getLength(); i++)
            {
                final Attr a = (Attr) attrs.item(i);
                final PrefixEntry ns = PrefixEntry.parseNamespace(a);
                if (ns != null)
                {
                    namespaces.put(ns._prefix, ns);
                }
            }
        }
        return namespaces;
    }


    public static class PrefixEntry
    {
        private final String _uri;
        private final String _prefix;

        public static PrefixEntry parseNamespace(final Attr attr)
        {
            final String prefix = attr.getPrefix();

            if ("xmlns".equals(prefix))
            {
                final String prefixName = attr.getLocalName();
                if (prefixName != null)
                {
                    final String uri = attr.getNodeValue();

                    if (uri != null)
                    {
                        return new PrefixEntry(uri, prefixName);
                    }
                }
            }

            return null;
        }

        public PrefixEntry(final String uri, final String prefix)
        {
            _uri = uri;
            _prefix = prefix;
        }

        public final String getUri()
        {
            return _uri;
        }

        public final String getPrefix()
        {
            return _prefix;
        }

        @Override
        public int hashCode()
        {
            return _uri.hashCode();
        }

        @Override
        public boolean equals(final Object obj)
        {
            return _uri.equals(obj);
        }
    }

    private NamespaceCMAdapter getNamespace(final String uri)
    {
      IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
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
    
    private synchronized NamespaceCMAdapter getOrCreateCMDocument(final IProject project, final String uri)
    {
        final MyRegistryFactory factory = new MyRegistryFactory();

        ITagRegistry registry;
        try
        {
            registry = factory.createTagRegistry(project);
            Namespace ns = registry.getTagLibrary(uri);

            if (ns != null)
            {
                return new NamespaceCMAdapter(ns, project);
            }
        }
        catch (TagRegistryFactoryException e)
        {
            // fall-through
        }
        return null;
    }
}
