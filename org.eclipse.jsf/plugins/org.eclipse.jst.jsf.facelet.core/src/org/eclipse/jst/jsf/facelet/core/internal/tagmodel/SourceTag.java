package org.eclipse.jst.jsf.facelet.core.internal.tagmodel;


public class SourceTag extends FaceletTag
{
    /**
     * 
     */
    private static final long serialVersionUID = 4648054050352065079L;
    private final String _source;

    public SourceTag(final String uri, final String name, final String source) {
        super(uri, name, TagType.HANDLER, null);
        _source = source;
    }

    public final String getSource() {
        return _source;
    }
}