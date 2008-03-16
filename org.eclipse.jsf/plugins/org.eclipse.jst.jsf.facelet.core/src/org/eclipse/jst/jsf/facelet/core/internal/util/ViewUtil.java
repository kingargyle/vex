package org.eclipse.jst.jsf.facelet.core.internal.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jst.jsf.core.internal.CompositeTagRegistryFactory;
import org.eclipse.jst.jsf.core.internal.CompositeTagRegistryFactory.TagRegistryIdentifier;
import org.eclipse.jst.jsf.designtime.internal.view.model.ITagRegistry;
import org.eclipse.jst.jsp.core.internal.contentmodel.tld.CMDocumentFactoryTLD;
import org.eclipse.jst.jsp.core.internal.contentmodel.tld.provisional.TLDDocument;
import org.eclipse.jst.jsp.core.taglib.ITaglibRecord;
import org.eclipse.jst.jsp.core.taglib.TaglibIndex;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public final class ViewUtil
{
    /**
     * Warning! This call can be very expensive.  Cache results whenever
     * possible.
     * 
     * @param project
     * @param uri
     * @return the tld document for uri in project or null if not found
     */
    public static TLDDocument findTLDDocument(final IProject project,
            final String uri)
    {
        final ITaglibRecord[] tldrecs = TaglibIndex
                .getAvailableTaglibRecords(project.getFullPath());

        for (final ITaglibRecord rec : tldrecs)
        {
            final String matchUri = rec.getDescriptor().getURI();
            if (uri.equals(matchUri))
            {
                final CMDocumentFactoryTLD factory = new CMDocumentFactoryTLD();
                return (TLDDocument) factory.createCMDocument(rec);
            }
        }
        return null;
    }

    /**
     * @param attributes
     * @return the set of uri's that declared in attributes
     */
    public static Set<Attr> getDeclaredNamespaces(final NamedNodeMap attributes)
    {
        final Set<Attr> alreadyUsed = new HashSet<Attr>();
        for (int i = 0; i < attributes.getLength(); i++)
        {
            final Node node = attributes.item(i);
            if ("xmlns".equals(node.getNodeName())
                    || "xmlns".equals(node.getPrefix()))
            {
                final String attrValue = node.getNodeValue();

                if (attrValue != null && !"".equals(attrValue.trim())
                        && node instanceof Attr)
                {
                    alreadyUsed.add((Attr) node);
                }
            }
        }

        return alreadyUsed;
    }

    public static boolean hasAttributeValue(final Set<Attr> attrSet,
            final String value)
    {
        for (final Attr attr : attrSet)
        {
            if (value.equals(attr.getValue()))
            {
                return true;
            }
        }
        return false;
    }

    public static ITagRegistry getHtmlTagRegistry(final IProject project)
    {
        final IContentType contentType = Platform.getContentTypeManager()
                .getContentType("org.eclipse.wst.html.core.htmlsource");
        final TagRegistryIdentifier id = new TagRegistryIdentifier(project,
                contentType);
        final ITagRegistry tagRegistry = CompositeTagRegistryFactory
                .getInstance().getRegistry(id);
        return tagRegistry;
    }

    public static Map<String, PrefixEntry> getDocumentNamespaces(
            final Document doc)
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

}
