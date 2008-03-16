package org.eclipse.jst.jsf.facelet.core.internal.view;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jst.jsf.common.runtime.internal.model.component.ComponentInfo;
import org.eclipse.jst.jsf.designtime.context.DTFacesContext;
import org.eclipse.jst.jsf.designtime.internal.view.DTUIViewRoot;
import org.eclipse.jst.jsf.designtime.internal.view.DefaultDTUIViewRoot;
import org.eclipse.jst.jsf.designtime.internal.view.IDTViewHandler;
import org.eclipse.jst.jsf.designtime.internal.view.XMLComponentTreeConstructionStrategy;
import org.eclipse.jst.jsf.designtime.internal.view.XMLViewDefnAdapter;

public class FaceletUIViewRoot extends DefaultDTUIViewRoot
{
    /**
     * 
     */
    private static final long serialVersionUID = -7289148553566455867L;

    public FaceletUIViewRoot(DTFacesContext context,
            IDTViewHandler viewHandler, XMLViewDefnAdapter adapter)
    {
        super(context, viewHandler, adapter);
    }

    @Override
    protected XMLComponentTreeConstructionStrategy createTreeConstructionStrategy(
            XMLViewDefnAdapter adapter, IProject project)
    {
        return new XMLComponentTreeConstructionStrategy(adapter, project)
        {
            @SuppressWarnings("unchecked")
            @Override
            protected void populateViewRoot(DTUIViewRoot viewRoot, List children)
            {
                // facelets effectively ignores view roots created by the view
                // defn.  So we simply need to loop through all of children
                // and add them to viewRoot unless they are view roots, in which
                // case we add their children
                for (final Iterator<?> it = children.iterator(); it.hasNext();)
                {
                    final ComponentInfo child = (ComponentInfo) it.next();
                    
                    if (child instanceof DTUIViewRoot ||
                            "javax.faces.ViewRoot".equals(child.getComponentTypeInfo().getComponentType()))
                    {
                        // add recursively
                        populateViewRoot(viewRoot, child.getChildren());
                    }
                    else
                    {
                        viewRoot.addChild(child);
                    }
                }
            }
        };
    }
}
