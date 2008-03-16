/**
 * 
 */
package org.eclipse.jst.jsf.facelet.core.internal.cm;

import java.util.Iterator;

import org.eclipse.jst.jsf.common.runtime.internal.view.model.common.ITagElement;
import org.eclipse.jst.jsp.core.internal.contentmodel.tld.CMDataTypeImpl;
import org.eclipse.wst.xml.core.internal.contentmodel.CMContent;
import org.eclipse.wst.xml.core.internal.contentmodel.CMDataType;
import org.eclipse.wst.xml.core.internal.contentmodel.CMElementDeclaration;
import org.eclipse.wst.xml.core.internal.contentmodel.CMNamedNodeMap;
import org.eclipse.wst.xml.core.internal.contentmodel.CMNode;

class ElementCMAdapter implements CMElementDeclaration, CMNamedNodeMap
{
    private final ITagElement  _tagElement;
    private final ExternalTagInfo _tLDTagInfo;

    ElementCMAdapter(final ITagElement tagElement, final ExternalTagInfo tldTagInfo)
    {
        _tagElement = tagElement;
        _tLDTagInfo = tldTagInfo;
    }

    public CMNamedNodeMap getAttributes()
    {
        return this;
    }

    public CMContent getContent()
    {
        return null;
    }

    public int getContentType()
    {
        return ELEMENT;
    }

    public CMDataType getDataType()
    {
        return new CMDataTypeImpl(CMDataType.CDATA);
    }

    public String getElementName()
    {
        return _tagElement.getName();
    }

    public CMNamedNodeMap getLocalElements()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public int getMaxOccur()
    {
        // unbounded
        return -1;
    }

    public int getMinOccur()
    {
        // optional
        return 0;
    }

    public String getNodeName()
    {
        return _tagElement.getName();
    }

    public int getNodeType()
    {
        return CMNode.ELEMENT_DECLARATION;
    }

    public Object getProperty(final String propertyName)
    {
        return _tLDTagInfo.getTagProperty(_tagElement.getName(), propertyName);
    }

    public boolean supports(final String propertyName)
    {
        return false;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (obj instanceof ElementCMAdapter)
        {
            return ((ElementCMAdapter) obj)._tagElement.equals(_tagElement);
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        return _tagElement.hashCode();
    }

    public int getLength()
    {
        final CMNamedNodeMap map = _tLDTagInfo.getAttributes(_tagElement.getName());

        if (map != null)
        {
            return map.getLength();
        }

        return 0;
    }

    public CMNode getNamedItem(final String name)
    {
        final CMNamedNodeMap map = _tLDTagInfo.getAttributes(_tagElement.getName());

        if (map != null)
        {
            return map.getNamedItem(name);
        }
        return null;
    }

    public CMNode item(final int index)
    {
        final CMNamedNodeMap map = _tLDTagInfo.getAttributes(_tagElement.getName());

        if (map != null)
        {
            return map.item(index);
        }
        return null;
    }

    public Iterator<?> iterator()
    {
        final CMNamedNodeMap map = _tLDTagInfo.getAttributes(_tagElement.getName());
        return map.iterator();
    }

}