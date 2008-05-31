package org.eclipse.jst.jsf.facelet.ui.internal.contentassist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.ITagElement;
import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.Namespace;
import org.eclipse.jst.jsf.context.resolver.structureddocument.IDOMContextResolver;
import org.eclipse.jst.jsf.context.resolver.structureddocument.IStructuredDocumentContextResolverFactory;
import org.eclipse.jst.jsf.context.resolver.structureddocument.IWorkspaceContextResolver;
import org.eclipse.jst.jsf.context.resolver.structureddocument.internal.ITextRegionContextResolver;
import org.eclipse.jst.jsf.context.structureddocument.IStructuredDocumentContext;
import org.eclipse.jst.jsf.context.structureddocument.IStructuredDocumentContextFactory;
import org.eclipse.jst.jsf.designtime.internal.view.model.ITagRegistry;
import org.eclipse.jst.jsf.designtime.internal.view.model.TagRegistryFactory.TagRegistryFactoryException;
import org.eclipse.jst.jsf.facelet.core.internal.registry.FaceletRegistryManager.MyRegistryFactory;
import org.eclipse.jst.jsf.facelet.ui.internal.FaceletUiPlugin;
import org.eclipse.wst.xml.core.internal.regions.DOMRegionContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

/**
 * Temporary.
 * 
 * @author cbateman
 *
 */
public class XHTMLContentAssistProcessor2 implements IContentAssistProcessor
{
    private final static ICompletionProposal[] NO_PROPOSALS = new ICompletionProposal[0];

    public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
            int offset)
    {
        List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
        final IStructuredDocumentContext context = IStructuredDocumentContextFactory.INSTANCE
                .getContext(viewer, offset);

        if (context != null)
        {
            final IDOMContextResolver domContextResolver = IStructuredDocumentContextResolverFactory.INSTANCE
                    .getDOMContextResolver(context);

            final Document doc = domContextResolver.getDOMDocument();

            if (doc == null)
            {
                return NO_PROPOSALS;
            }

            // get the namespaces that are already declared
            final Map<String, PrefixEntry> documentNamespaces = getDocumentNamespaces(doc);

            final ITextRegionContextResolver resolver = IStructuredDocumentContextResolverFactory.INSTANCE
                    .getTextRegionResolver(context);

            if (resolver != null)
            {
                final String regionType = resolver.getRegionType();

                if (DOMRegionContext.XML_CONTENT.equals(regionType))
                {
                    // TODO: this may be in the open it may occur on the inside
                    // of a
                    // "<" that doesn't have any further tag name yet
                    proposals = getTagCompletionsForDocument(context,
                            documentNamespaces, TagPrefix.NO_PREFIX);
                }
                else if (DOMRegionContext.XML_TAG_NAME.equals(regionType))
                {
                    final TagPrefix tagPrefix = new TagPrefix(resolver
                            .getRegionText());
                    proposals = getTagCompletionsForDocument(context,
                            documentNamespaces, tagPrefix);
                }
                else
                {
                    System.out.println(regionType);
                }
            }
        }

        return proposals.toArray(NO_PROPOSALS);
    }

    private List<ICompletionProposal> getTagCompletionsForDocument(
            final IStructuredDocumentContext context,
            final Map<String, PrefixEntry> namespaces, final TagPrefix tagPrefix)
    {
        final List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();

        final IWorkspaceContextResolver wkspaceResolver = IStructuredDocumentContextResolverFactory.INSTANCE
                .getWorkspaceContextResolver(context);
        final IProject project = wkspaceResolver.getProject();

        MyRegistryFactory factory = new MyRegistryFactory();
        ITagRegistry registry;
        try
        {
            registry = factory.createTagRegistry(project);
            final Collection<? extends Namespace> tagLibs = registry
                    .getAllTagLibraries();

            for (final Namespace tagLib : tagLibs)
            {
                final PrefixEntry ns = namespaces.get(tagLib.getNSUri());

                // if the tag library is not initialized, don't trigger
                // a potentially expensive operation during the content assist
                if (ns != null && tagLib.isInitialized())
                {
                    // only add if this namespace's prefix starts with
                    // the prefix that the user provided
                    if (ns._prefix.startsWith(tagPrefix.getPrefix()))
                    {
                        final Collection<?> tags = tagLib.getViewElements();

                        for (final Iterator<?> it = tags.iterator(); it
                                .hasNext();)
                        {
                            final ITagElement tagElement = (ITagElement) it
                                    .next();

                            // only add an element it starts with the prefix
                            // provided
                            if (tagElement.getName().startsWith(
                                    tagPrefix.getLocalName()))
                            {
                                final String userPrefix = tagPrefix
                                        .getUserPrefix();

                                final String tagName = ns._prefix + ":"
                                        + tagElement.getName();
                                String replacementText = "";
                                if ("".equals(userPrefix))
                                {
                                    replacementText = "<" + tagName + "></"
                                            + tagName + ">";
                                }
                                else
                                {
                                    replacementText = tagName
                                            .substring(userPrefix.length());
                                }
                                proposals.add(new CompletionProposal(
                                        replacementText, context
                                                .getDocumentPosition(), 0, 0,
                                        null, tagName, null, null));
                            }
                        }
                    }
                }
            }
        }
        catch (TagRegistryFactoryException e)
        {
            FaceletUiPlugin.log(e);
        }

        return proposals;
    }

    private Map<String, PrefixEntry> getDocumentNamespaces(Document doc)
    {
        final Map<String, PrefixEntry> namespaces = new HashMap<String, PrefixEntry>();

        Element rootElement = doc.getDocumentElement();

        if (rootElement != null)
        {
            NamedNodeMap attrs = rootElement.getAttributes();
            for (int i = 0; i < attrs.getLength(); i++)
            {
                Attr a = (Attr) attrs.item(i);
                PrefixEntry ns = PrefixEntry.parseNamespace(a);
                if (ns != null)
                {
                    namespaces.put(ns.getUri(), ns);
                }
            }
        }

        return namespaces;
    }

    private static class TagPrefix
    {
        public final static TagPrefix NO_PREFIX = new TagPrefix("");

        private final String          _prefix;
        private final String          _localName;
        private final boolean         _hasColon;

        public TagPrefix(final String tagName)
        {
            int prefixIdx = tagName.indexOf(':');

            if (prefixIdx != -1)
            {
                _prefix = tagName.substring(0, prefixIdx);
                _hasColon = true;
                if (tagName.length() > prefixIdx)
                {
                    _localName = tagName.substring(prefixIdx + 1);
                }
                else
                {
                    _localName = "";
                }
            }
            else
            {
                _hasColon = false;
                _prefix = tagName;
                _localName = "";
            }
        }

        public String getUserPrefix()
        {
            String userPrefix = _prefix;

            if (_hasColon)
            {
                userPrefix += ":";
                userPrefix += _localName;
            }

            return userPrefix;
        }

        public String getPrefix()
        {
            return _prefix;
        }

        public String getLocalName()
        {
            return _localName;
        }

        public boolean isHasColon()
        {
            return _hasColon;
        }
    }

    private static class PrefixEntry
    {
        private final String _uri;
        private final String _prefix;

        public static PrefixEntry parseNamespace(Attr attr)
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

        public int hashCode()
        {
            return _uri.hashCode();
        }

        public boolean equals(Object obj)
        {
            return _uri.equals(obj);
        }
    }

    public IContextInformation[] computeContextInformation(ITextViewer viewer,
            int offset)
    {
        return null;
    }

    public char[] getCompletionProposalAutoActivationCharacters()
    {
        return null;
    }

    public char[] getContextInformationAutoActivationCharacters()
    {
        return null;
    }

    public IContextInformationValidator getContextInformationValidator()
    {
        return null;
    }

    public String getErrorMessage()
    {
        return null;
    }

}
