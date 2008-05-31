package org.eclipse.jst.jsf.facelet.core.internal.tagmodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jst.jsf.common.runtime.internal.model.component.ComponentTypeInfo;
import org.eclipse.jst.jsf.common.runtime.internal.model.decorator.ConverterTypeInfo;
import org.eclipse.jst.jsf.common.runtime.internal.model.decorator.ValidatorTypeInfo;
import org.eclipse.jst.jsf.designtime.internal.view.model.jsp.IAttributeAdvisor;
import org.eclipse.jst.jsf.facelet.core.internal.cm.FaceletDocumentFactory;
import org.eclipse.jst.jsf.facelet.core.internal.tagmodel.FaceletTaglibWithTags.WorkingCopy;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A Facelet tag library document parser (dtd 1.0).
 * @author cbateman
 *
 */
public class TagModelParser
{
    private static final String ELEMENT_NAME_VALIDATOR_ID = "validator-id"; //$NON-NLS-1$
    private static final String ELEMENT_NAME_CONVERTER_ID = "converter-id"; //$NON-NLS-1$
    private static final String ELEMENT_NAME_RENDERER_TYPE = "renderer-type"; //$NON-NLS-1$
    private static final String ELEMENT_NAME_COMPONENT_TYPE = "component-type"; //$NON-NLS-1$
    private static final String ELEMENT_NAME_VALIDATOR = "validator"; //$NON-NLS-1$
    private static final String ELEMENT_NAME_CONVERTER = "converter"; //$NON-NLS-1$
    private static final String ELEMENT_NAME_COMPONENT = "component"; //$NON-NLS-1$
    private static final String ELEMENT_NAME_HANDLER_CLASS = "handler-class"; //$NON-NLS-1$
    private static final String ELEMENT_NAME_TAG_NAME = "tag-name"; //$NON-NLS-1$
    private static final String ELEMENT_NAME_TAG = "tag"; //$NON-NLS-1$
    private static final String ELEMENT_NAME_NAMESPACE = "namespace"; //$NON-NLS-1$
    private static final String ELEMENT_NAME_LIBRARY_CLASS = "library-class"; //$NON-NLS-1$
    private static final String ELEMENT_NAME_FACELET_TAGLIB = "facelet-taglib"; //$NON-NLS-1$
    private static final String URI_FACELET_TAGLIB_1_0_DTD = "http://java.sun.com/dtd/facelet-taglib_1_0.dtd"; //$NON-NLS-1$

    // public static void main(final String[] args)
    // throws ParserConfigurationException, IOException, SAXException
    // {
    // System.setProperty("http.proxyHost", "www-proxy.us.oracle.com");
    // System.setProperty("http.proxyPort", "80");
    //
    // final InputSource taglibFile = new InputSource(
    // new FileInputStream(
    // "C:/dev/workspaces/ws_3.0_2/ViewHandlerPrototype/src/viewhandlerprototype/facelet/tagmodel/jsf-core.taglib.xml"
    // ));
    //
    // final InputSource source = new InputSource(
    // new FileInputStream(
    // "C:/dev/workspaces/ws_3.0_2/ViewHandlerPrototype/src/viewhandlerprototype/facelet/tagmodel/facelet-taglib_1_0.dtd"
    // ));
    //
    // final FaceletTaglib tagLib = processDocument(getDefaultTaglibDocument(
    // taglibFile, source));
    //
    // if (tagLib != null)
    // {
    // System.out.print(tagLib.toString());
    // }
    // }

    /**
     * @param taglibFile
     * @param defaultDTDSource
     * @return the default taglib dom Document
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public static Document getDefaultTaglibDocument(
            final InputSource taglibFile, final InputSource defaultDTDSource)
    throws IOException, ParserConfigurationException, SAXException
    {
        final DocumentBuilderFactory factory = DocumentBuilderFactory
        .newInstance();
        factory.setValidating(false);
        factory.setIgnoringComments(true);

        final DocumentBuilder builder = factory.newDocumentBuilder();

        final DefaultHandler handler = new DefaultHandler()
        {
            @Override
            public InputSource resolveEntity(final String publicId,
                    final String systemId) throws IOException, SAXException
                    {
                if (URI_FACELET_TAGLIB_1_0_DTD
                        .equals(systemId))
                {
                    return defaultDTDSource;
                }
                return super.resolveEntity(publicId, systemId);
                    }
        };

        builder.setEntityResolver(handler);
        return builder.parse(taglibFile);
    }

    /**
     * @param doc
     * @param factory
     * @param advisor
     * @return the facelet tag library or null
     */
    public static FaceletTaglib processDocument(final Document doc,
            final FaceletDocumentFactory factory,
            final IAttributeAdvisor advisor)
    {
        Node curNode = null;

        for (int i = 0; i < doc.getChildNodes().getLength(); i++)
        {
            curNode = doc.getChildNodes().item(i);

            if (curNode.getNodeType() == Node.ELEMENT_NODE
                    && ELEMENT_NAME_FACELET_TAGLIB.equals(curNode.getNodeName()))
            {
                break;
            }
        }

        if (curNode == null)
        {
            throw new IllegalArgumentException(
            "Couldn't find facelet-taglib node"); //$NON-NLS-1$
        }

        for (int i = 0; i < curNode.getChildNodes().getLength(); i++)
        {
            final Node node = curNode.getChildNodes().item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                if (ELEMENT_NAME_LIBRARY_CLASS.equals(node.getNodeName()))
                {
                    return new FaceletTaglibWithLibraryClass(null, node
                            .getTextContent(), null);
                }
                return processFaceletTaglibWithTags(curNode, factory,
                        advisor);
            }
        }

        return null;
    }

    private static FaceletTaglib processFaceletTaglibWithTags(final Node node,
            final FaceletDocumentFactory factory,
            final IAttributeAdvisor advisor)
    {
        String namespace = null;
        final List<Node> tagNodes = new ArrayList<Node>();

        for (int i = 0; i < node.getChildNodes().getLength(); i++)
        {
            final Node childNode = node.getChildNodes().item(i);

            if (childNode.getNodeType() == Node.ELEMENT_NODE)
            {
                if (ELEMENT_NAME_NAMESPACE.equals(childNode.getNodeName()))
                {
                    namespace = childNode.getTextContent();
                }
                else if (ELEMENT_NAME_TAG.equals(childNode.getNodeName()))
                {
                    tagNodes.add(childNode);
                }
            }
        }

        if (namespace != null)
        {
            final FaceletTaglibWithTags.WorkingCopy workingCopy = new WorkingCopy(
                    namespace);

            for (final Node tagNode : tagNodes)
            {
                final FaceletTag tag = createTagFromNode(namespace, tagNode,
                        factory, advisor);

                if (tag != null)
                {
                    workingCopy.addFaceletTag(tag);
                }
            }

            return workingCopy.closeWorkingCopy();
        }
        return null;
    }

    private static FaceletTag createTagFromNode(final String namespace,
            final Node tagNode, final FaceletDocumentFactory factory,
            final IAttributeAdvisor advisor)
    {
        final Map<String, Node> children = indexChildren(tagNode);

        Node node = children.get(ELEMENT_NAME_TAG_NAME);

        if (node != null)
        {
            final String name = node.getTextContent();

            node = children.get(ELEMENT_NAME_HANDLER_CLASS);

            if (node != null)
            {
                return new HandlerTag(namespace, name, null, node
                        .getTextContent(), factory, advisor);
            }

            node = children.get(ELEMENT_NAME_COMPONENT);

            if (node != null)
            {
                return createTagWithComponentType(namespace, name, node,
                        factory, advisor);
            }

            node = children.get(ELEMENT_NAME_CONVERTER);

            if (node != null)
            {
                return createTagWithConverter(namespace, name, node, factory,
                        advisor);
            }

            node = children.get(ELEMENT_NAME_VALIDATOR);

            if (node != null)
            {
                return createValidatorTag(namespace, name, node, factory,
                        advisor);
            }
        }

        return null;
    }

    private static Map<String, Node> indexChildren(final Node node)
    {
        final Map<String, Node> children = new HashMap<String, Node>();
        final NodeList nodeList = node.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++)
        {
            final Node childNode = nodeList.item(i);

            if (childNode.getNodeType() == Node.ELEMENT_NODE)
            {
                children.put(childNode.getNodeName(), childNode);
            }
        }

        return children;
    }

    private static ComponentTag createTagWithComponentType(final String uri,
            final String tagName, final Node paramNode,
            final FaceletDocumentFactory factory,
            final IAttributeAdvisor advisor)
    {
        final Map<String, Node> componentChildren = indexChildren(paramNode);
        Node node = componentChildren.get(ELEMENT_NAME_COMPONENT_TYPE);

        if (node != null)
        {
            final String componentType = node.getTextContent();
            String rendererType = null;
            String handlerClass = null;

            node = componentChildren.get(ELEMENT_NAME_RENDERER_TYPE);
            if (node != null)
            {
                rendererType = node.getTextContent();
            }

            node = componentChildren.get(ELEMENT_NAME_HANDLER_CLASS);
            if (node != null)
            {
                handlerClass = node.getTextContent();
            }
            final ComponentTypeInfo typeInfo = new ComponentTypeInfo(
                    componentType, handlerClass, rendererType, null);
            return new ComponentTag(uri, tagName, typeInfo, factory, advisor);
        }
        return null;
    }

    private static ConverterTag createTagWithConverter(final String uri,
            final String tagName, final Node paramNode,
            final FaceletDocumentFactory factory,
            final IAttributeAdvisor advisor)
    {
        final Map<String, Node> converterChildren = indexChildren(paramNode);
        Node node = converterChildren.get(ELEMENT_NAME_CONVERTER_ID);

        if (node != null)
        {
            // final String converterId = node.getTextContent();
            String handlerClass = null;

            node = converterChildren.get(ELEMENT_NAME_HANDLER_CLASS);
            if (node != null)
            {
                handlerClass = node.getTextContent();
            }
            // for now, all converters are unknown
            return new ConverterTag(uri, tagName, ConverterTypeInfo.UNKNOWN,
                    handlerClass, factory, advisor);
        }
        return null;
    }

    private static ValidatorTag createValidatorTag(final String uri,
            final String tagName, final Node paramNode,
            final FaceletDocumentFactory factory,
            final IAttributeAdvisor advisor)
    {
        final Map<String, Node> converterChildren = indexChildren(paramNode);
        Node node = converterChildren.get(ELEMENT_NAME_VALIDATOR_ID);

        if (node != null)
        {
            // final String validatorId = node.getTextContent();
            String handlerClass = null;

            node = converterChildren.get(ELEMENT_NAME_HANDLER_CLASS);
            if (node != null)
            {
                handlerClass = node.getTextContent();
            }
            return new ValidatorTag(uri, tagName, ValidatorTypeInfo.UNKNOWN,
                    handlerClass, factory, advisor);
        }
        return null;
    }
}
