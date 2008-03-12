package org.eclipse.jst.jsf.facelet.core.internal.tagmodel;


/**
 * A facelet tag with no information about it than its name
 * 
 * @author cbateman
 *
 */
public final class NoArchetypeFaceletTag extends FaceletTag {

    public NoArchetypeFaceletTag(final String uri, final String name) {
        super(uri, name, TagType.HANDLER, null);
    }

}
