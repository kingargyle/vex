package org.eclipse.jst.jsf.facelet.ui.internal.contentassist;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.jst.jsf.context.structureddocument.IStructuredDocumentContext;
import org.eclipse.jst.jsf.context.structureddocument.IStructuredDocumentContextFactory;
import org.eclipse.jst.jsf.designtime.DTAppManagerUtil;
import org.eclipse.jst.jsf.designtime.internal.view.XMLViewDefnAdapter;
import org.eclipse.jst.jsf.designtime.internal.view.IDTViewHandler.ViewHandlerException;
import org.eclipse.jst.jsf.designtime.internal.view.XMLViewDefnAdapter.DTELExpression;
import org.eclipse.jst.jsf.ui.internal.contentassist.JSFContentAssistProcessor;
import org.eclipse.jst.jsf.ui.internal.contentassist.el.JSFELContentAssistProcessor;

/**
 * Composes the EL and non-EL attribute value assist processor for JSF.  This
 * is necessary mainly because, without the EL partitioning in HTML, both
 * processors would otherwise activate on every attribute.
 * 
 * @author cbateman
 *
 */
public class CompositeAttributeAssistProcessor implements
        IContentAssistProcessor
{
    private JSFContentAssistProcessor _nonELProcessor;
    private JSFELContentAssistProcessor _elProcessor;
    private char[]                      _activationChars;

    public CompositeAttributeAssistProcessor()
    {
        _nonELProcessor = new JSFContentAssistProcessor();
        _elProcessor = new JSFELContentAssistProcessor();
        
        char[] nonELChars = 
            _nonELProcessor.getCompletionProposalAutoActivationCharacters();
        char[] elChars =
            _elProcessor.getCompletionProposalAutoActivationCharacters();
        _activationChars = new char[nonELChars.length+elChars.length];
        System.arraycopy(nonELChars, 0, _activationChars, 0, nonELChars.length);
        System.arraycopy(elChars, 0, _activationChars, nonELChars.length, elChars.length);
        
        
    }
    public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
            int offset)
    {
        if (isEL(viewer, offset))
        {
            return _elProcessor.computeCompletionProposals(viewer, offset);
        }
        return _nonELProcessor.computeCompletionProposals(viewer, offset);
    }

    public IContextInformation[] computeContextInformation(ITextViewer viewer,
            int offset)
    {
        if (isEL(viewer, offset))
        {
            return _elProcessor.computeContextInformation(viewer, offset);
        }
        return _nonELProcessor.computeContextInformation(viewer, offset);
    }

    public char[] getCompletionProposalAutoActivationCharacters()
    {
        return _activationChars;
    }

    public char[] getContextInformationAutoActivationCharacters()
    {
        // disable this functionality
        return null;
    }

    public IContextInformationValidator getContextInformationValidator()
    {
        // disable this functionality
        return null;
    }

    public String getErrorMessage()
    {
        // TODO Auto-generated method stub
        return null;
    }

    private boolean isEL(final ITextViewer viewer, int offset)
    {
        IStructuredDocumentContext context =
            IStructuredDocumentContextFactory.INSTANCE.getContext(viewer, offset);
        
        if (context != null)
        {
            XMLViewDefnAdapter adapter = 
                DTAppManagerUtil.getXMLViewDefnAdapter(context);
            if (adapter != null)
            {
                try
                {
                    DTELExpression elExpression = adapter.getELExpression(context);
    
                    // only return true if we definitively find EL
                    if(elExpression != null)
                    {
                        return true;
                    }
                }
                catch (ViewHandlerException e)
                {
                    // fall through to false, no el
                }
            }
        }
        // all other cases, return false
        return false;
    }
}
