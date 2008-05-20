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
                    "Use MetadataTagInfo(uri) if no tld document");
        }
        _doc = doc;
        _uri = doc.getUri();
    }

    @Override
    public Object getTagProperty(final String tagName, final String key)
    {
        // TODO: this is a temporary hack for EclipseCon
        if (IFaceletTagConstants.URI_JSF_FACELETS.equals(_uri)
                && "description".equals(key))
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
     * @param uri
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
        final String ID_DESCRIPTION = "Assign a unique idea to generated component.  If not present, Facelets will automatically generate one.";
        final String BINDING_DESCRIPTION = "A method binding of the form of the form #{bean.binding} where 'binding' has the signature <b>UIComponent binding()</b>, that returns the component instance to use. If not present, Facelets will automatically instantiate one.";
        final String TEMPLATE_DESCRIPTION = "A uri that points to the template to use.  e.g. /WEB-INF/templates/mytemplate.xhtml";
        final String HOTKEY_DESCRIPTION = "The key in combination with CTRL-SHIFT to use to launch the debug viewer";
        final String DEFINE_NAME_DESCRIPTION = "The name of a template area.  This name is referenced in instance documents using the insert tag";
        final String SRC_DESCRIPTION = "The path, absolute or relative to the original request, to another Facelet to include.  May be EL.  e.g. 'headerPage.xhtml'";
        final String INSERT_NAME_DESCRIPTION = "The name of a template area to insert (defined in the template using the define tag).  If not specified, the entire template will be inserted.";
        final String PARAM_NAME_DESCRIPTION = "The name of the new EL variable to declare";
        final String PARAM_VALUE_DESCRIPTION = "The literal or EL value to assign to the new variable";
        final String REPEAT_VAR_DESCRIPTION = "The name of the EL variable to use as the iterator";
        final String REPEAT_VALUE_DESCRIPTION = "The EL expression used to derive the list of items to repeat over";

        final Map<String, InternalNamedNodeMap> map = new HashMap<String, InternalNamedNodeMap>();
        // component
        InternalNamedNodeMap nodeMap = new InternalNamedNodeMap();
        nodeMap.add(createAttribute("id", CMAttributeDeclaration.OPTIONAL,
                ID_DESCRIPTION));
        nodeMap.add(createAttribute("binding", CMAttributeDeclaration.OPTIONAL,
                BINDING_DESCRIPTION));
        map.put(IFaceletTagConstants.TAG_COMPONENT, nodeMap);

        // composition
        nodeMap = new InternalNamedNodeMap();
        nodeMap.add(createAttribute("template",
                CMAttributeDeclaration.OPTIONAL, TEMPLATE_DESCRIPTION));
        map.put(IFaceletTagConstants.TAG_COMPOSITION, nodeMap);

        // debug
        nodeMap = new InternalNamedNodeMap();
        nodeMap.add(createAttribute("hotkey", CMAttributeDeclaration.OPTIONAL,
                HOTKEY_DESCRIPTION));
        map.put(IFaceletTagConstants.TAG_DEBUG, nodeMap);

        // decorate
        nodeMap = new InternalNamedNodeMap();
        nodeMap.add(createAttribute("template",
                CMAttributeDeclaration.REQUIRED, TEMPLATE_DESCRIPTION));
        map.put(IFaceletTagConstants.TAG_DECORATE, nodeMap);

        // define
        nodeMap = new InternalNamedNodeMap();
        nodeMap.add(createAttribute("name", CMAttributeDeclaration.REQUIRED,
                DEFINE_NAME_DESCRIPTION));
        map.put(IFaceletTagConstants.TAG_DEFINE, nodeMap);

        // fragment
        nodeMap = new InternalNamedNodeMap();
        nodeMap.add(createAttribute("id", CMAttributeDeclaration.OPTIONAL,
                ID_DESCRIPTION));
        nodeMap.add(createAttribute("binding", CMAttributeDeclaration.OPTIONAL,
                BINDING_DESCRIPTION));
        map.put(IFaceletTagConstants.TAG_FRAGMENT, nodeMap);

        // include
        nodeMap = new InternalNamedNodeMap();
        nodeMap.add(createAttribute("src", CMAttributeDeclaration.REQUIRED,
                SRC_DESCRIPTION));
        map.put(IFaceletTagConstants.TAG_INCLUDE, nodeMap);

        // insert
        nodeMap = new InternalNamedNodeMap();
        nodeMap.add(createAttribute("name", CMAttributeDeclaration.OPTIONAL,
                INSERT_NAME_DESCRIPTION));
        map.put(IFaceletTagConstants.TAG_INSERT, nodeMap);

        // param
        nodeMap = new InternalNamedNodeMap();
        nodeMap.add(createAttribute("name", CMAttributeDeclaration.REQUIRED,
                PARAM_NAME_DESCRIPTION));
        nodeMap.add(createAttribute("value", CMAttributeDeclaration.REQUIRED,
                PARAM_VALUE_DESCRIPTION));
        map.put(IFaceletTagConstants.TAG_PARAM, nodeMap);

        // remove
        nodeMap = new InternalNamedNodeMap();
        // no attributes
        map.put(IFaceletTagConstants.TAG_PARAM, nodeMap);

        // repeat
        nodeMap = new InternalNamedNodeMap();
        nodeMap.add(createAttribute("value", CMAttributeDeclaration.REQUIRED,
                REPEAT_VALUE_DESCRIPTION));
        nodeMap.add(createAttribute("var", CMAttributeDeclaration.REQUIRED,
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
                        "Adds a new UIComponent into the view's component tree.  The new component will be the parent of the tag's component children.  Text outside the tag is removed before view rendering similar to composition.");
        StringBuffer text = new StringBuffer();
        text
                .append("<p><em>Inserts a composite component that ignores content around it:</em></p>");
        text.append("<br><p><i>This text will be removed</i><br>");
        text.append("<b>&lt;ui:composition&gt;</b><br>");
        text.append("#{el.text}<br>");
        text.append("&lt;h:inputText &nbsp;value=\"#{some.value}\"/&gt;<br>");
        text.append("<b>&lt;/ui:composition&gt;</b><br>");
        text.append("<i>This text will be removed</i></p>");

        FACELET_TAG_DESCRIPTIONS.put(IFaceletTagConstants.TAG_COMPOSITION, text
                .toString());

        FACELET_TAG_DESCRIPTIONS
                .put(
                        IFaceletTagConstants.TAG_DEBUG,
                        "Saves the component tree and EL variables in a view.  Accessible by hitting the hotkey (CTRL-SHIFT-D by default).");

        text = new StringBuffer();
        text
                .append("<p><em>Inserts a composite component that keeps the content around it:</em></p>");
        text.append("<br><p><i>This text will be removed</i><br>");
        text.append("<b>&lt;ui:composition&gt;</b><br>");
        text.append("#{el.text}<br>");
        text.append("&lt;h:inputText &nbsp;value=\"#{some.value}\"/&gt;<br>");
        text.append("<b>&lt;/ui:composition&gt;</b><br>");
        text.append("<i>This text will be removed</i></p>");
        FACELET_TAG_DESCRIPTIONS.put(IFaceletTagConstants.TAG_DECORATE, text
                .toString());
        FACELET_TAG_DESCRIPTIONS.put(IFaceletTagConstants.TAG_DEFINE
                , "Defines a template area that can be used instances using insert.");
        FACELET_TAG_DESCRIPTIONS.put(IFaceletTagConstants.TAG_FRAGMENT
                , "Adds a new UIComponent into the view's component tree.  The new component will be the parent of the tag's component children.  Text outside the tag is kept, similar to decorate.");
        FACELET_TAG_DESCRIPTIONS.put(IFaceletTagConstants.TAG_INCLUDE
                , "Includes another facelet.");
        FACELET_TAG_DESCRIPTIONS.put(IFaceletTagConstants.TAG_INSERT
                , "Insert a named template area created using the define tag.");
        FACELET_TAG_DESCRIPTIONS.put(IFaceletTagConstants.TAG_PARAM
                , "Declares a new EL variable on the facelet page.");
        FACELET_TAG_DESCRIPTIONS.put(IFaceletTagConstants.TAG_REMOVE
                , "Removes its content from the rendered view.");
        FACELET_TAG_DESCRIPTIONS.put(IFaceletTagConstants.TAG_REPEAT
                , "Repeatedly renders its content by iterating through the List returned from the value attribute.  Intended as a JSF-safe replacement for c:forEach.");
    }

}
