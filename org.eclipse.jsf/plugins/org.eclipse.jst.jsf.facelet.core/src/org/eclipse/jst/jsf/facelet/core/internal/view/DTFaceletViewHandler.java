package org.eclipse.jst.jsf.facelet.core.internal.view;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.jst.jsf.core.internal.JSFCorePlugin;
import org.eclipse.jst.jsf.designtime.context.DTFacesContext;
import org.eclipse.jst.jsf.designtime.internal.view.AbstractDTViewHandler;
import org.eclipse.jst.jsf.designtime.internal.view.DTUIViewRoot;
import org.eclipse.jst.jsf.designtime.internal.view.DefaultDTUIViewRoot;
import org.eclipse.jst.jsf.designtime.internal.view.IViewDefnAdapter;
import org.eclipse.jst.jsf.designtime.internal.view.IViewDefnAdapterFactory;
import org.eclipse.jst.jsf.designtime.internal.view.XMLViewDefnAdapter;
import org.eclipse.jst.jsf.facelet.core.internal.facet.FaceletFacet;

public class DTFaceletViewHandler extends AbstractDTViewHandler
{

    @Override
    public String calculateLocale(DTFacesContext context)
            throws ViewHandlerException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResource getActionDefinition(DTFacesContext context, String viewId)
            throws ViewHandlerException
    {
        // TODO: this seems like a bit of a cope out...
        return context.adaptContextObject();
    }

    @Override
    public IPath getActionURL(DTFacesContext context, IResource resource,
            IPath requestPath) throws ViewHandlerException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IPath getRelativeActionPath(DTFacesContext context,
            String relativeToViewId, String uri) throws ViewHandlerException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IViewDefnAdapterFactory getViewMetadataAdapterFactory(
            DTFacesContext context) throws ViewHandlerException
    {
        final IResource res = context.adaptContextObject();

        if (res instanceof IFile)
        {
            return new ViewDefnAdapterFactory(this);
        }

        return null;
    }

    @Override
    protected DTUIViewRoot internalCreateView(DTFacesContext facesContext,
            String viewId)
    {
        IViewDefnAdapterFactory factory;
        try
        {
            factory = getViewMetadataAdapterFactory(facesContext);
            if (factory != null)
            {
                IViewDefnAdapter<?, ?> adapter = 
                    factory.createAdapter(facesContext, viewId);
                if (adapter instanceof XMLViewDefnAdapter)
                {
                    return new DefaultDTUIViewRoot(facesContext, this, (XMLViewDefnAdapter) adapter);
                }
            }
        }
        catch (ViewHandlerException e)
        {
            JSFCorePlugin.log(e, "While acquiring view defn adapter factory");
            // fall-through
        }
        
        return null;
    }

    @Override
    public boolean supportsViewDefinition(IFile file)
    {
        return FaceletFacet.hasFacet(file.getProject()) && isHTMLContent(file);
    }

    boolean isHTMLContent(final IFile file)
    {
        final IContentTypeManager typeManager = Platform
                .getContentTypeManager();
        IContentType htmlContentType = typeManager
                .getContentType("org.eclipse.wst.html.core.htmlsource");
        if (htmlContentType != null
                && htmlContentType.isAssociatedWith(file.getName()))
        {
            return true;
        }

        return false;

    }

}
