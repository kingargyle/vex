package org.eclipse.jst.jsf.facelet.core.internal.cm;

import org.eclipse.jst.jsp.core.internal.contentmodel.tld.provisional.TLDDocument;
import org.eclipse.wst.xml.core.internal.contentmodel.CMElementDeclaration;
import org.eclipse.wst.xml.core.internal.contentmodel.CMNamedNodeMap;

/*package*/ class ExtraTagInfo
{
    private final TLDDocument _doc;

    final static ExtraTagInfo NULL_EXTRATAGINFO = new ExtraTagInfo(null);
    
    public ExtraTagInfo(final TLDDocument doc)
    {
        _doc = doc;
    }

    /**
     * @param uri
     * @param tagName
     * @return a named node map of known attributes for the tag, or null if not
     *         found
     */
    public CMNamedNodeMap getAttributes(final String tagName)
    {
        if (_doc != null)
        {
            final CMElementDeclaration element = (CMElementDeclaration) _doc
                    .getElements().getNamedItem(tagName);

            if (element != null)
            {
                return element.getAttributes();
            }
        }

        return null;
    }
}
