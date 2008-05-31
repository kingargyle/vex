package org.eclipse.jst.jsf.facelet.core.internal.cm;

import java.util.Enumeration;

import org.eclipse.jst.jsp.core.internal.contentmodel.tld.CMDataTypeImpl;
import org.eclipse.wst.xml.core.internal.contentmodel.CMAttributeDeclaration;
import org.eclipse.wst.xml.core.internal.contentmodel.CMDataType;
import org.eclipse.wst.xml.core.internal.contentmodel.CMNode;

/**
 * Adapts Facelet attribute date to the CM model.
 * 
 * @author cbateman
 *
 */
public class AttributeCMAdapter implements CMAttributeDeclaration
{
    private static final String DESCRIPTION = "description"; //$NON-NLS-1$
    private int     _usage;
    private String _name;
    private String _description;

    /**
     * @param name
     * @param usage
     */
    public AttributeCMAdapter(final String name, final int usage)
    {
        _name = name;
        _usage = usage;
    }
    
    public String getAttrName()
    {
        return _name;
    }

    public CMDataType getAttrType()
    {
        return new CMDataTypeImpl(CMDataType.CDATA);
    }

    public String getDefaultValue()
    {
        return null;
    }

    public Enumeration<?> getEnumAttr()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public int getUsage()
    {
        return _usage;
    }

    public String getNodeName()
    {
        return _name;
    }

    public int getNodeType()
    {
        return CMNode.ATTRIBUTE_DECLARATION;
    }

    public Object getProperty(String propertyName)
    {
        if (DESCRIPTION.equals(propertyName))
        {
            return _description;
        }
        return null;
    }

    /**
     * @param description
     */
    public void setDescription(final String description)
    {
        _description = description;
    }
    
    public boolean supports(String propertyName)
    {
        return false;
    }
}
