package org.eclipse.jst.jsf.facelet.core.internal.cm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jst.jsf.facelet.core.internal.tagmodel.IFaceletTagConstants;
import org.eclipse.jst.jsp.core.internal.contentmodel.tld.provisional.TLDDocument;
import org.eclipse.wst.xml.core.internal.contentmodel.CMAttributeDeclaration;
import org.eclipse.wst.xml.core.internal.contentmodel.CMElementDeclaration;
import org.eclipse.wst.xml.core.internal.contentmodel.CMNamedNodeMap;
import org.eclipse.wst.xml.core.internal.contentmodel.CMNode;

/**
 * An external tag info that checks first the meta-data repository and second in
 * the provided TLDDocument for data.
 * 
 * @author cbateman
 * 
 */
/* package */class MetadataTagInfo extends ExternalTagInfo
{
    private static final String VAR = "var"; //$NON-NLS-1$
    private static final String VALUE = "value"; //$NON-NLS-1$
    private static final String SRC = "src"; //$NON-NLS-1$
    private static final String NAME = "name"; //$NON-NLS-1$
    private static final String HOTKEY = "hotkey"; //$NON-NLS-1$
    private static final String TEMPLATE = "template"; //$NON-NLS-1$
    private static final String BINDING = "binding"; //$NON-NLS-1$
    private static final String ID = "id"; //$NON-NLS-1$
    private final TLDDocument _doc;
    private final String      _uri;

    public MetadataTagInfo(final String uri)
    {
        _doc = null;
        _uri = uri;
    }

    public MetadataTagInfo(final TLDDocument doc)
    {
        if (doc == null)
        {
            throw new IllegalArgumentException(
                    "Use MetadataTagInfo(uri) if no tld document"); //$NON-NLS-1$
        }
        _doc = doc;
        _uri = doc.getUri();
    }

    @Override
    public Object getTagProperty(final String tagName, final String key)
    {
        // TODO: this is a temporary hack for EclipseCon
        if (IFaceletTagConstants.URI_JSF_FACELETS.equals(_uri)
                && "description".equals(key)) //$NON-NLS-1$
        {
            return FACELET_TAG_DESCRIPTIONS.get(tagName);
        }

        if (_doc != null)
        {
            final CMElementDeclaration element = (CMElementDeclaration) _doc
                    .getElements().getNamedItem(tagName);
            if (element != null)
            {
                return element.getProperty(key);
            }
        }
        return null;
    }

    /**
     * @param tagName
     * @return a named node map of known attributes for the tag, or null if not
     *         found
     */
    @Override
    public CMNamedNodeMap getAttributes(final String tagName)
    {
        // TODO: this is a temporary hack for EclipseCon
        if (IFaceletTagConstants.URI_JSF_FACELETS.equals(_uri))
        {
            return _faceletData.get(tagName);
        }

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

    // private CMNamedNodeMap getOrCreateNodeMap(final String tagName)
    // {
    // InternalNamedNodeMap elementNamed = _nodeMap.get(tagName);
    //
    // if (elementNamed == null)
    // {
    // elementNamed = new InternalNamedNodeMap();
    //
    // _nodeMap.put(tagName, elementNamed);
    // }
    // return elementNamed;
    // }

    private static class InternalNamedNodeMap implements CMNamedNodeMap
    {
        private final List<CMNode> _nodes = new ArrayList<CMNode>();

        private void add(CMNode node)
        {
            _nodes.add(node);
        }

        public int getLength()
        {
            return _nodes.size();
        }

        public CMNode getNamedItem(final String name)
        {
            for (final CMNode foundNode : _nodes)
            {
                if (name.equals(foundNode.getNodeName()))
                {
                    return foundNode;
                }
            }
            return null;
        }

        public CMNode item(int index)
        {
            if (index < _nodes.size())
            {
                return _nodes.get(index);
            }
            return null;
        }

        public Iterator<?> iterator()
        {
            return Collections.unmodifiableList(_nodes).iterator();
        }
    }

    // temporary: transfer out to metadata
    final static Map<String, InternalNamedNodeMap> _faceletData;

    static
    {
        final String ID_DESCRIPTION = Messages.getString("MetadataTagInfo.ID_DESCRIPTION"); //$NON-NLS-1$
        final String BINDING_DESCRIPTION = Messages.getString("MetadataTagInfo.BINDING_DESCRIPTION"); //$NON-NLS-1$
        final String TEMPLATE_DESCRIPTION = Messages.getString("MetadataTagInfo.TEMPLATE_DESCRIPTION"); //$NON-NLS-1$
        final String HOTKEY_DESCRIPTION = Messages.getString("MetadataTagInfo.HOTKEY_DESCRIPTION"); //$NON-NLS-1$
        final String DEFINE_NAME_DESCRIPTION = Messages.getString("MetadataTagInfo.DEFINE_NAME_DESCRIPTION"); //$NON-NLS-1$
        final String SRC_DESCRIPTION = Messages.getString("MetadataTagInfo.SRC_DESCRIPTION"); //$NON-NLS-1$
        final String INSERT_NAME_DESCRIPTION = Messages.getString("MetadataTagInfo.INSERT_NAME_DESCRIPTION"); //$NON-NLS-1$
        final String PARAM_NAME_DESCRIPTION = Messages.getString("MetadataTagInfo.PARAM_NAME_DESCRIPTION"); //$NON-NLS-1$
        final String PARAM_VALUE_DESCRIPTION = Messages.getString("MetadataTagInfo.PARAM_VALUE_DESCRIPTION"); //$NON-NLS-1$
        final String REPEAT_VAR_DESCRIPTION = Messages.getString("MetadataTagInfo.REPEAT_VAR_DESCRIPTION"); //$NON-NLS-1$
        final String REPEAT_VALUE_DESCRIPTION = Messages.getString("MetadataTagInfo.REPEAT_VALUE_DESCRIPTION"); //$NON-NLS-1$

        final Map<String, InternalNamedNodeMap> map = new HashMap<String, InternalNamedNodeMap>();
        // component
        InternalNamedNodeMap nodeMap = new InternalNamedNodeMap();
        nodeMap.add(createAttribute(ID, CMAttributeDeclaration.OPTIONAL,
                ID_DESCRIPTION));
        nodeMap.add(createAttribute(BINDING, CMAttributeDeclaration.OPTIONAL,
                BINDING_DESCRIPTION));
        map.put(IFaceletTagConstants.TAG_COMPONENT, nodeMap);

        // composition
        nodeMap = new InternalNamedNodeMap();
        nodeMap.add(createAttribute(TEMPLATE,
                CMAttributeDeclaration.OPTIONAL, TEMPLATE_DESCRIPTION));
        map.put(IFaceletTagConstants.TAG_COMPOSITION, nodeMap);

        // debug
        nodeMap = new InternalNamedNodeMap();
        nodeMap.add(createAttribute(HOTKEY, CMAttributeDeclaration.OPTIONAL,
                HOTKEY_DESCRIPTION));
        map.put(IFaceletTagConstants.TAG_DEBUG, nodeMap);

        // decorate
        nodeMap = new InternalNamedNodeMap();
        nodeMap.add(createAttribute(TEMPLATE,
                CMAttributeDeclaration.REQUIRED, TEMPLATE_DESCRIPTION));
        map.put(IFaceletTagConstants.TAG_DECORATE, nodeMap);

        // define
        nodeMap = new InternalNamedNodeMap();
        nodeMap.add(createAttribute(NAME, CMAttributeDeclaration.REQUIRED,
                DEFINE_NAME_DESCRIPTION));
        map.put(IFaceletTagConstants.TAG_DEFINE, nodeMap);

        // fragment
        nodeMap = new InternalNamedNodeMap();
        nodeMap.add(createAttribute(ID, CMAttributeDeclaration.OPTIONAL,
                ID_DESCRIPTION));
        nodeMap.add(createAttribute(BINDING, CMAttributeDeclaration.OPTIONAL,
                BINDING_DESCRIPTION));
        map.put(IFaceletTagConstants.TAG_FRAGMENT, nodeMap);

        // include
        nodeMap = new InternalNamedNodeMap();
        nodeMap.add(createAttribute(SRC, CMAttributeDeclaration.REQUIRED,
                SRC_DESCRIPTION));
        map.put(IFaceletTagConstants.TAG_INCLUDE, nodeMap);

        // insert
        nodeMap = new InternalNamedNodeMap();
        nodeMap.add(createAttribute(NAME, CMAttributeDeclaration.OPTIONAL,
                INSERT_NAME_DESCRIPTION));
        map.put(IFaceletTagConstants.TAG_INSERT, nodeMap);

        // param
        nodeMap = new InternalNamedNodeMap();
        nodeMap.add(createAttribute(NAME, CMAttributeDeclaration.REQUIRED,
                PARAM_NAME_DESCRIPTION));
        nodeMap.add(createAttribute(VALUE, CMAttributeDeclaration.REQUIRED,
                PARAM_VALUE_DESCRIPTION));
        map.put(IFaceletTagConstants.TAG_PARAM, nodeMap);

        // remove
        nodeMap = new InternalNamedNodeMap();
        // no attributes
        map.put(IFaceletTagConstants.TAG_PARAM, nodeMap);

        // repeat
        nodeMap = new InternalNamedNodeMap();
        nodeMap.add(createAttribute(VALUE, CMAttributeDeclaration.REQUIRED,
                REPEAT_VALUE_DESCRIPTION));
        nodeMap.add(createAttribute(VAR, CMAttributeDeclaration.REQUIRED,
                REPEAT_VAR_DESCRIPTION));
        map.put(IFaceletTagConstants.TAG_REPEAT, nodeMap);

        _faceletData = Collections.unmodifiableMap(map);
    }

    private static CMAttributeDeclaration createAttribute(final String name,
            final int usage, final String description)
    {
        AttributeCMAdapter attribute = new AttributeCMAdapter(name, usage);
        attribute.setDescription(description);
        return attribute;
    }

    final static Map<String, String> FACELET_TAG_DESCRIPTIONS = new HashMap<String, String>();

    static
    {
        FACELET_TAG_DESCRIPTIONS
                .put(
                        IFaceletTagConstants.TAG_COMPONENT,
                        Messages.getString("MetadataTagInfo.COMPONENT_TAG_DESCRIPTION.1")); //$NON-NLS-1$
        StringBuffer text = new StringBuffer();
        text
                .append(Messages.getString("MetadataTagInfo.COMPONENT_TAG_DETAIL.1")); //$NON-NLS-1$
        text.append(Messages.getString("MetadataTagInfo.COMPONENT_TAG_DETAIL.2")); //$NON-NLS-1$
        text.append(Messages.getString("MetadataTagInfo.COMPONENT_TAG_DETAIL.3")); //$NON-NLS-1$
        text.append(Messages.getString("MetadataTagInfo.COMPONENT_TAG_DETAIL.4")); //$NON-NLS-1$
        text.append(Messages.getString("MetadataTagInfo.COMPONENT_TAG_DETAIL.5")); //$NON-NLS-1$
        text.append(Messages.getString("MetadataTagInfo.COMPONENT_TAG_DETAIL.6")); //$NON-NLS-1$
        text.append(Messages.getString("MetadataTagInfo.COMPONENT_TAG_DETAIL.7")); //$NON-NLS-1$

        FACELET_TAG_DESCRIPTIONS.put(IFaceletTagConstants.TAG_COMPOSITION, text
                .toString());

        FACELET_TAG_DESCRIPTIONS
                .put(
                        IFaceletTagConstants.TAG_DEBUG,
                        Messages.getString("MetadataTagInfo.DEBUG_TAG_DESCRIPTION.1")); //$NON-NLS-1$

        text = new StringBuffer();
        text
                .append(Messages.getString("MetadataTagInfo.DEBUG_TAG_DETAIL.1")); //$NON-NLS-1$
        text.append(Messages.getString("MetadataTagInfo.DEBUG_TAG_DETAIL.2")); //$NON-NLS-1$
        text.append(Messages.getString("MetadataTagInfo.DEBUG_TAG_DETAIL.3")); //$NON-NLS-1$
        text.append(Messages.getString("MetadataTagInfo.DEBUG_TAG_DETAIL.4")); //$NON-NLS-1$
        text.append(Messages.getString("MetadataTagInfo.DEBUG_TAG_DETAIL.5")); //$NON-NLS-1$
        text.append(Messages.getString("MetadataTagInfo.DEBUG_TAG_DETAIL.6")); //$NON-NLS-1$
        text.append(Messages.getString("MetadataTagInfo.DEBUG_TAG_DETAIL.7")); //$NON-NLS-1$
        FACELET_TAG_DESCRIPTIONS.put(IFaceletTagConstants.TAG_DECORATE, text
                .toString());
        FACELET_TAG_DESCRIPTIONS.put(IFaceletTagConstants.TAG_DEFINE
                , Messages.getString("MetadataTagInfo.DEFINE_TAG_DESCRIPTION.1")); //$NON-NLS-1$
        FACELET_TAG_DESCRIPTIONS.put(IFaceletTagConstants.TAG_FRAGMENT
                , Messages.getString("MetadataTagInfo.FRAGMENT_TAG_DESCRIPTION.1")); //$NON-NLS-1$
        FACELET_TAG_DESCRIPTIONS.put(IFaceletTagConstants.TAG_INCLUDE
                , Messages.getString("MetadataTagInfo.INCLUDE_TAG_DESCRIPTION.1")); //$NON-NLS-1$
        FACELET_TAG_DESCRIPTIONS.put(IFaceletTagConstants.TAG_INSERT
                , Messages.getString("MetadataTagInfo.INSERT_TAG_DESCRIPTION.1")); //$NON-NLS-1$
        FACELET_TAG_DESCRIPTIONS.put(IFaceletTagConstants.TAG_PARAM
                , Messages.getString("MetadataTagInfo.PARAM_TAG_DESCRIPTION.1")); //$NON-NLS-1$
        FACELET_TAG_DESCRIPTIONS.put(IFaceletTagConstants.TAG_REMOVE
                , Messages.getString("MetadataTagInfo.REMOVE_TAG_DESCRIPTION.1")); //$NON-NLS-1$
        FACELET_TAG_DESCRIPTIONS.put(IFaceletTagConstants.TAG_REPEAT
                , Messages.getString("MetadataTagInfo.REPEAT_TAG_DESCRIPTION.1")); //$NON-NLS-1$
    }

}
