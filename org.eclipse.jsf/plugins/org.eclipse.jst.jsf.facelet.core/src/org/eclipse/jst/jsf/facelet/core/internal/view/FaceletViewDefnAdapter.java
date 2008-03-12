package org.eclipse.jst.jsf.facelet.core.internal.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jst.jsf.context.IModelContext;
import org.eclipse.jst.jsf.context.resolver.structureddocument.IDOMContextResolver;
import org.eclipse.jst.jsf.context.resolver.structureddocument.IStructuredDocumentContextResolverFactory;
import org.eclipse.jst.jsf.context.resolver.structureddocument.internal.ITextRegionContextResolver;
import org.eclipse.jst.jsf.context.structureddocument.IStructuredDocumentContext;
import org.eclipse.jst.jsf.context.structureddocument.IStructuredDocumentContextFactory;
import org.eclipse.jst.jsf.core.internal.JSFCorePlugin;
import org.eclipse.jst.jsf.designtime.context.DTFacesContext;
import org.eclipse.jst.jsf.designtime.internal.view.TaglibBasedViewDefnAdapter;
import org.eclipse.jst.jsf.designtime.internal.view.IDTViewHandler.ViewHandlerException;
import org.eclipse.jst.jsf.designtime.internal.view.IDTViewHandler.ViewHandlerException.Cause;
import org.eclipse.jst.jsf.designtime.internal.view.model.ITagRegistry;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.xml.core.internal.regions.DOMRegionContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class FaceletViewDefnAdapter extends TaglibBasedViewDefnAdapter
{
    FaceletViewDefnAdapter(final ITagRegistry tagRegistry)
    {
        super(tagRegistry);
    }

    @Override
    public IDocument getContainer(final DTFacesContext context, final String viewId)
    {
        final IResource viewDefn = context.adaptContextObject();

        if (viewDefn instanceof IFile)
        {
            final IFile viewDefnFile = (IFile) viewDefn;
            IStructuredModel model = null;
            try
            {
                model = StructuredModelManager.getModelManager()
                .getModelForRead(viewDefnFile);

                return model.getStructuredDocument();
            }
            catch (final IOException e)
            {
                JSFCorePlugin.log(e, "Acquiring model for view root");
            }
            catch (final CoreException e)
            {
                JSFCorePlugin.log(e, "Acquiring model for view root");
            }
            finally
            {
                if (model != null)
                {
                    model.releaseFromRead();
                }
            }
        }
        return null;
    }

    @Override
    public DTELExpression getELExpression(final IModelContext genericContext)
            throws ViewHandlerException
    {
        final IStructuredDocumentContext context = (IStructuredDocumentContext) genericContext
                .getAdapter(IStructuredDocumentContext.class);

        if (context == null)
        {
            throw new ViewHandlerException(Cause.EL_NOT_FOUND);
        }

        final ITextRegionContextResolver resolver =
            IStructuredDocumentContextResolverFactory.INSTANCE
            .getTextRegionResolver(context);

        if (resolver != null)
        {
            final String regionType = resolver.getRegionType();
            int startOffset = resolver.getStartOffset();
            int relativeOffset = context.getDocumentPosition() - startOffset;
            
            if (DOMRegionContext.XML_TAG_ATTRIBUTE_VALUE.equals(regionType))
            {
                final String attributeText = resolver.getRegionText();
                int elOpenIdx = attributeText.indexOf("#");
                
                if (elOpenIdx >= 0 && elOpenIdx < relativeOffset
                        && elOpenIdx+1 < attributeText.length()
                        && attributeText.charAt(elOpenIdx+1) == '{')
                {
                    // we may have a hit
                    int elCloseIdx = attributeText.indexOf('}', elOpenIdx+1);
                    if (elCloseIdx  != -1)
                    {
                        final IStructuredDocumentContext elContext =
                            IStructuredDocumentContextFactory.INSTANCE.getContext(
                                    context.getStructuredDocument(), resolver
                                    .getStartOffset()+elOpenIdx+2);
                        final String elText = attributeText.substring(
                                elOpenIdx + 2, elCloseIdx);
                        return new DTELExpression(elContext, elText);
                    }
                }
            }
        }

        return null;
    }

    @Override
    public String getNamespace(final Element element, final IDocument doc)
    {
        final Map<String, PrefixEntry> namespaces = getDocumentNamespaces(element
                .getOwnerDocument());
        final String prefix = element.getPrefix();

        final PrefixEntry prefixEntry = namespaces.get(prefix);

        if (prefixEntry != null)
        {
            return prefixEntry.getUri();
        }

        return null;
    }

    @Override
    public String getPrefix(String namespace, IDocument document)
    {
        if (namespace == null || "".equals(namespace.trim()))
        {
            return null;
        }
        final IStructuredDocumentContext context = IStructuredDocumentContextFactory.INSTANCE
                .getContext(document, -1);
        if (context != null)
        {
            final IDOMContextResolver resolver = IStructuredDocumentContextResolverFactory.INSTANCE
                    .getDOMContextResolver(context);

            if (resolver != null)
            {
                final Document xmlDoc = resolver.getDOMDocument();

                if (xmlDoc != null)
                {
                    Map<String, PrefixEntry> map = getDocumentNamespaces(xmlDoc);
                
                    for (final Map.Entry<String, PrefixEntry> mapEntry : map.entrySet())
                    {
                        if (namespace.equals(mapEntry.getValue().getUri()))
                        {
                            return mapEntry.getKey();
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param doc
     * @return map of available prefices in doc keyed by tag prefix
     */
    private Map<String, PrefixEntry> getDocumentNamespaces(final Document doc)
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

    private static class PrefixEntry
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
