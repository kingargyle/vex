package org.eclipse.jst.jsf.facelet.core.internal.tagmodel;

import org.eclipse.jst.jsf.designtime.internal.view.model.jsp.IAttributeAdvisor;
import org.eclipse.jst.jsf.facelet.core.internal.cm.FaceletDocumentFactory;


/**
 * A facelet tag with no information about it than its name
 * 
 * @author cbateman
 *
 */
public final class NoArchetypeFaceletTag extends FaceletTag {

    /**
     * 
     */
    private static final long serialVersionUID = 4810723162936027305L;

    /**
     * @param uri
     * @param name
     * @param factory
     * @param advisor
     */
    public NoArchetypeFaceletTag(final String uri, final String name, final FaceletDocumentFactory factory, 
            final IAttributeAdvisor advisor) {
        super(uri, name, TagType.HANDLER, null, factory, advisor);
    }

}
