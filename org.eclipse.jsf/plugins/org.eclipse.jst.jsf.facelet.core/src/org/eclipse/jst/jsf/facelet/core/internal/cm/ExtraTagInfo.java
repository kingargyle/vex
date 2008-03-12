package org.eclipse.jst.jsf.facelet.core.internal.cm;

import org.eclipse.core.resources.IProject;
import org.eclipse.jst.jsp.core.internal.contentmodel.tld.CMDocumentFactoryTLD;
import org.eclipse.jst.jsp.core.internal.contentmodel.tld.provisional.TLDDocument;
import org.eclipse.jst.jsp.core.taglib.ITaglibRecord;
import org.eclipse.jst.jsp.core.taglib.TaglibIndex;
import org.eclipse.wst.xml.core.internal.contentmodel.CMElementDeclaration;
import org.eclipse.wst.xml.core.internal.contentmodel.CMNamedNodeMap;

public class ExtraTagInfo
{
    private final IProject _project;
    private final String   _uri;
    private TLDDocument    _doc = null;

    public ExtraTagInfo(final IProject project, final String uri)
    {
        _project = project;
        _uri = uri;
        final ITaglibRecord[] tldrecs = TaglibIndex
                .getAvailableTaglibRecords(_project.getFullPath());
        for (final ITaglibRecord rec : tldrecs)
        {
            final String matchUri = rec.getDescriptor().getURI();
            if (_uri.equals(matchUri))
            {
                final CMDocumentFactoryTLD factory = new CMDocumentFactoryTLD();
                _doc = (TLDDocument) factory.createCMDocument(rec);
                break;
            }
        }
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
