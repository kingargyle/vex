package org.eclipse.jst.jsf.facelet.ui.internal.contentassist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.Namespace;
import org.eclipse.jst.jsf.context.resolver.structureddocument.IDOMContextResolver;
import org.eclipse.jst.jsf.context.resolver.structureddocument.IStructuredDocumentContextResolverFactory;
import org.eclipse.jst.jsf.context.resolver.structureddocument.IWorkspaceContextResolver;
import org.eclipse.jst.jsf.context.structureddocument.IStructuredDocumentContext;
import org.eclipse.jst.jsf.context.structureddocument.IStructuredDocumentContextFactory;
import org.eclipse.jst.jsf.designtime.internal.view.model.ITagRegistry;
import org.eclipse.jst.jsf.facelet.core.internal.cm.FaceletDocumentFactory;
import org.eclipse.jst.jsf.facelet.core.internal.facet.FaceletFacet;
import org.eclipse.jst.jsf.facelet.core.internal.util.ViewUtil;
import org.eclipse.jst.jsf.facelet.core.internal.util.ViewUtil.PrefixEntry;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionList;
import org.eclipse.wst.sse.ui.internal.contentassist.CustomCompletionProposal;
import org.eclipse.wst.xml.core.internal.contentmodel.CMDocument;
import org.eclipse.wst.xml.core.internal.contentmodel.CMElementDeclaration;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;
import org.eclipse.wst.xml.core.internal.regions.DOMRegionContext;
import org.eclipse.wst.xml.ui.internal.contentassist.AbstractContentAssistProcessor;
import org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest;
import org.eclipse.wst.xml.ui.internal.contentassist.XMLRelevanceConstants;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class XHTMLContentAssistProcessor extends AbstractContentAssistProcessor
{
    private IProject _project;
    private FaceletDocumentFactory      _factory;

    @Override
    public ICompletionProposal[] computeCompletionProposals(
            final ITextViewer textViewer, final int documentPosition)
    {
        final long startTime = System.nanoTime();
        ICompletionProposal[] proposals = new ICompletionProposal[0];
        _project = getProject(textViewer, documentPosition);

        if (_project != null && shouldContribute(_project))
        {
            _factory = new FaceletDocumentFactory(_project);
            proposals =  super.computeCompletionProposals(textViewer,
                    documentPosition);
        }
        System.out.printf("computeCompletionProposals time: %d\n", (System.nanoTime()-startTime)/1000);
        return proposals;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected List getAvailableChildElementDeclarations(final Element parent,
            final int childPosition, final int kindOfAction)
    {
        final Map<String, PrefixEntry> namespaces = getDocumentNamespaces(
                _factory, childPosition);
        final List availableChildElements = new ArrayList();

        for (final Map.Entry<String, PrefixEntry> entry : namespaces.entrySet())
        {
            final String prefix = entry.getValue().getPrefix();
            final CMDocument cmDocument = _factory.createCMDocumentForContext(
                    entry.getValue().getUri(), prefix);

            if (cmDocument != null)
            {
                final Iterator it = cmDocument.getElements().iterator();
                while (it.hasNext())
                {
                    availableChildElements.add(it.next());
                }
            }
        }
       
        return availableChildElements;
    }

    @Override
    protected void addAttributeValueProposals(
            final ContentAssistRequest contentAssistRequest)
    {
        final ITextRegion textRegion = findNameRegionIfIsHTMLRoot(contentAssistRequest);

        if (textRegion != null)
        {
            final IDOMNode node = ((IDOMNode) contentAssistRequest.getNode());
            final NamedNodeMap attributes = node.getAttributes();
            final String attrName = node.getFirstStructuredDocumentRegion()
                    .getText(textRegion);
            if (attrName != null)
            {
                final int colonPos = attrName.indexOf(':');
                // must have a colon && it must not be the last char, otherwise
                // there is no localName
                if (colonPos > -1 && colonPos < attrName.length() - 1)
                {
                    final String prefix = attrName.substring(0, colonPos);

                    if ("xmlns".equals(prefix))
                    {
                        final ITagRegistry tagRegistry = ViewUtil.getHtmlTagRegistry(_project);
                        if (tagRegistry != null)
                        {
                            final Set<Attr> alreadyUsed = ViewUtil.getDeclaredNamespaces(attributes);
                            final Collection<? extends Namespace> namespaces = tagRegistry
                                    .getAllTagLibraries();
                            NAMESPACE_LOOP: for (final Namespace ns : namespaces)
                            {
                                final String possibleValue = ns.getNSUri();
                               
                                if (ViewUtil.hasAttributeValue(alreadyUsed, possibleValue))
                                {
                                    continue NAMESPACE_LOOP;
                                }
                                // we have an attribute of the form xmlns:X in
                                // the
                                // html root node. Here we can provide value
                                // proposals
                                // for all of the known namespaces.
                                final String rString = "\"" + possibleValue + "\""; //$NON-NLS-2$//$NON-NLS-1$
                                final int rOffset = contentAssistRequest
                                        .getReplacementBeginPosition();
                                final int rLength = contentAssistRequest
                                        .getReplacementLength();
                                final int cursorAfter = possibleValue.length() + 1;
                                final String displayString = "\"" + possibleValue + "\""; //$NON-NLS-2$//$NON-NLS-1$

                                final CustomCompletionProposal proposal = new CustomCompletionProposal(
                                        rString,
                                        rOffset,
                                        rLength,
                                        cursorAfter,
                                        null,
                                        displayString,
                                        null,
                                        null,
                                        XMLRelevanceConstants.R_XML_ATTRIBUTE_VALUE);
                                contentAssistRequest.addProposal(proposal);
                            }
                        }
                        // now bail, since super only adds annoying identity completions
                        // for this case
                        return;
                    }
                }
            }
        }
        super.addAttributeValueProposals(contentAssistRequest);
    }

    private ITextRegion findNameRegionIfIsHTMLRoot(
            final ContentAssistRequest contentAssistRequest)
    {
        final IDOMNode node = (IDOMNode) contentAssistRequest.getNode();
        if (node.getNodeType() == Node.ELEMENT_NODE
                && "html".equals(node.getNodeName())
                && node.getOwnerDocument().getDocumentElement() == node)
        {
            // Find the attribute region and name for which this position should
            // have a value proposed
            final IStructuredDocumentRegion open = node
                    .getFirstStructuredDocumentRegion();
            final ITextRegionList openRegions = open.getRegions();
            int i = openRegions.indexOf(contentAssistRequest.getRegion());
            if (i < 0)
            {
                return null;
            }
            ITextRegion nameRegion = null;
            while (i >= 0)
            {
                nameRegion = openRegions.get(i--);
                if (nameRegion.getType() == DOMRegionContext.XML_TAG_ATTRIBUTE_NAME)
                {
                    break;
                }
            }
            return nameRegion;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private Map<String, PrefixEntry> getDocumentNamespaces(
            final FaceletDocumentFactory factory, final int offset)
    {
        final IStructuredDocumentContext context = IStructuredDocumentContextFactory.INSTANCE
                .getContext(fTextViewer, offset);

        Document doc = null;
        if (context != null)
        {
            final IDOMContextResolver domContextResolver = IStructuredDocumentContextResolverFactory.INSTANCE
                    .getDOMContextResolver(context);

            doc = domContextResolver.getDOMDocument();

            if (doc == null)
            {
                return Collections.EMPTY_MAP;
            }
        }
        return ViewUtil.getDocumentNamespaces(doc);
    }

    @Override
    protected CMElementDeclaration getCMElementDeclaration(final Node node)
    {
        if (node.getNodeType() == Node.ELEMENT_NODE)
        {
            if (node.getPrefix() != null)
            {
                final Element element = (Element) node;

                final CMElementDeclaration elementDecl = _factory
                        .createCMElementDeclaration(_project, element);

                if (elementDecl != null)
                {
                    return elementDecl;
                }
            }
        }

        return null;
        // return super.getCMElementDeclaration(node);
    }

    private boolean shouldContribute(final IProject project)
    {
        return FaceletFacet.hasFacet(project);
    }

    private IProject getProject(final ITextViewer textViewer,
            final int documentPosition)
    {
        final IStructuredDocumentContext context = IStructuredDocumentContextFactory.INSTANCE
                .getContext(textViewer, documentPosition);

        if (context != null)
        {
            final IWorkspaceContextResolver resolver = IStructuredDocumentContextResolverFactory.INSTANCE
                    .getWorkspaceContextResolver(context);

            if (resolver != null)
            {
                return resolver.getProject();
            }
        }
        return null;
    }
}
