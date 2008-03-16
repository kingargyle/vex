package org.eclipse.jst.jsf.facelet.core.internal.cm;

import org.eclipse.wst.xml.core.internal.contentmodel.CMNamedNodeMap;

public abstract class ExternalTagInfo
{

    public ExternalTagInfo()
    {
        super();
    }

    public abstract CMNamedNodeMap getAttributes(final String tagName);

    public abstract Object getTagProperty(final String tagName, final String key);

}