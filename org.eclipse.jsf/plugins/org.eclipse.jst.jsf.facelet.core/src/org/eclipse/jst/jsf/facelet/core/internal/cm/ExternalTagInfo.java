package org.eclipse.jst.jsf.facelet.core.internal.cm;

import org.eclipse.wst.xml.core.internal.contentmodel.CMNamedNodeMap;

/**
 * Represents information about Facelets brought from external sources such
 * as the JSP tag library.
 * 
 * @author cbateman
 *
 */
public abstract class ExternalTagInfo
{
    /**
     * @param tagName
     * @return the node map of attributes for the tag called tagName
     */
    public abstract CMNamedNodeMap getAttributes(final String tagName);

    /**
     * @param tagName
     * @param key
     * @return the tag property in the CM model for tagName at key or null if
     * not found.
     */
    public abstract Object getTagProperty(final String tagName, final String key);

}