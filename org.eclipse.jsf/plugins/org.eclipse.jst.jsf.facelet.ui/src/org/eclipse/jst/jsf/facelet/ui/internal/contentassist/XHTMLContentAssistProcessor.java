package org.eclipse.jst.jsf.facelet.ui.internal.contentassist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jst.jsf.context.resolver.structureddocument.IDOMContextResolver;
import org.eclipse.jst.jsf.context.resolver.structureddocument.IStructuredDocumentContextResolverFactory;
import org.eclipse.jst.jsf.context.resolver.structureddocument.IWorkspaceContextResolver;
import org.eclipse.jst.jsf.context.structureddocument.IStructuredDocumentContext;
import org.eclipse.jst.jsf.context.structureddocument.IStructuredDocumentContextFactory;
import org.eclipse.jst.jsf.facelet.core.internal.cm.FaceletDocumentFactory;
import org.eclipse.jst.jsf.facelet.core.internal.cm.FaceletDocumentFactory.PrefixEntry;
import org.eclipse.jst.jsf.facelet.core.internal.facet.FaceletFacet;
import org.eclipse.wst.xml.core.internal.contentmodel.CMDocument;
import org.eclipse.wst.xml.core.internal.contentmodel.CMElementDeclaration;
import org.eclipse.wst.xml.ui.internal.contentassist.AbstractContentAssistProcessor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XHTMLContentAssistProcessor extends AbstractContentAssistProcessor
{
    private IProject _project;

    @Override
    public ICompletionProposal[] computeCompletionProposals(
            final ITextViewer textViewer, final int documentPosition)
    {
        _project = getProject(textViewer, documentPosition);

        if (_project != null && shouldContribute(_project))
        {
            return super.computeCompletionProposals(textViewer,
                    documentPosition);
        }
        return new ICompletionProposal[0];
    }

    @SuppressWarnings("unchecked")
    @Override
    protected List getAvailableChildElementDeclarations(final Element parent,
            final int childPosition, final int kindOfAction)
    {
        final FaceletDocumentFactory factory = new FaceletDocumentFactory();
        final Map<String, PrefixEntry> namespaces = getDocumentNamespaces(
                factory, childPosition);
        final List availableChildElements = new ArrayList();

        for (final Map.Entry<String, PrefixEntry> entry : namespaces.entrySet())
        {
            final String prefix = entry.getValue().getPrefix();
            final CMDocument cmDocument = factory.createCMDocumentForContext(
                    _project, entry.getValue().getUri(), prefix);

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
        return factory.getDocumentNamespaces(doc);
    }

    @Override
    protected CMElementDeclaration getCMElementDeclaration(final Node node)
    {
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getPrefix() != null)
        {
            final Element element = (Element) node;
            final FaceletDocumentFactory factory = new FaceletDocumentFactory();

            final CMElementDeclaration elementDecl = factory
                    .createCMElementDeclaration(_project, element);

            if (elementDecl != null)
            {
                return elementDecl;
            }
        }

        return super.getCMElementDeclaration(node);
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
