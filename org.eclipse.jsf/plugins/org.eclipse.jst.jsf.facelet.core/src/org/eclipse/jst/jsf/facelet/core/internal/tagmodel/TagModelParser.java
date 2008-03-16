package org.eclipse.jst.jsf.facelet.core.internal.tagmodel;

import java.io.FileInputStream;
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
import org.eclipse.jst.jsf.facelet.core.internal.tagmodel.FaceletTaglibWithTags.WorkingCopy;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class TagModelParser
{
    public static void main(final String[] args) throws ParserConfigurationException,
    IOException, SAXException
    {
        System.setProperty("http.proxyHost", "www-proxy.us.oracle.com");
        System.setProperty("http.proxyPort", "80");

        final InputSource taglibFile =
            new InputSource(
                    new FileInputStream(
                    "C:/dev/workspaces/ws_3.0_2/ViewHandlerPrototype/src/viewhandlerprototype/facelet/tagmodel/jsf-core.taglib.xml"));

        final InputSource source =
            new InputSource(
                    new FileInputStream(
                    "C:/dev/workspaces/ws_3.0_2/ViewHandlerPrototype/src/viewhandlerprototype/facelet/tagmodel/facelet-taglib_1_0.dtd"));

        final FaceletTaglib tagLib =
            processDocument(getDefaultTaglibDocument(taglibFile, source));

        if (tagLib != null) {
            System.out.print(tagLib.toString());
        }
    }

    public static Document getDefaultTaglibDocument(final InputSource taglibFile, final InputSource defaultDTDSource) throws IOException, ParserConfigurationException, SAXException
    {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setIgnoringComments(true);


        final DocumentBuilder builder = factory.newDocumentBuilder();

        final DefaultHandler handler = new DefaultHandler()
        {
            @Override
            public InputSource resolveEntity(String publicId, String systemId)
                    throws IOException, SAXException
            {
                if ("http://java.sun.com/dtd/facelet-taglib_1_0.dtd"
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

    public static FaceletTaglib processDocument(final Document doc) {
        Node curNode = null;

        for (int i = 0; i < doc.getChildNodes().getLength(); i++) {
            curNode = doc.getChildNodes().item(i);

            if (curNode.getNodeType() == Node.ELEMENT_NODE
                    && "facelet-taglib".equals(curNode.getNodeName())) {
                break;
            }
        }

        if (curNode == null) {
            throw new IllegalArgumentException(
            "Couldn't find facelet-taglib node");
        }

        for (int i = 0; i < curNode.getChildNodes().getLength(); i++) {
            final Node node = curNode.getChildNodes().item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if ("library-class".equals(node.getNodeName())) {
                    return new FaceletTaglibWithLibraryClass(
                            null, node.getTextContent(), null);
                } else {
                    return processFaceletTaglibWithTags(curNode);
                }
            }
        }

        return null;
    }

    private static FaceletTaglib processFaceletTaglibWithTags(final Node node) {
        String namespace = null;
        final List<Node> tagNodes = new ArrayList<Node>();

        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            final Node childNode = node.getChildNodes().item(i);

            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                if ("namespace".equals(childNode.getNodeName())) {
                    namespace = childNode.getTextContent();
                } else if ("tag".equals(childNode.getNodeName())) {
                    tagNodes.add(childNode);
                }
            }
        }

        if (namespace != null) {
            final FaceletTaglibWithTags.WorkingCopy workingCopy = new WorkingCopy(
                    namespace);

            for (final Node tagNode : tagNodes) {
                final FaceletTag tag = createTagFromNode(namespace, tagNode);

                if (tag != null) {
                    workingCopy.addFaceletTag(tag);
                }
            }

            return workingCopy.closeWorkingCopy();
        }
        return null;
    }

    private static FaceletTag createTagFromNode(final String namespace, final Node tagNode)
    {
        final Map<String, Node> children = indexChildren(tagNode);

        Node node = children.get("tag-name");

        if (node != null) {
            final String name = node.getTextContent();

            node = children.get("handler-class");

            if (node != null) {
                return new HandlerTag(namespace,name, null, node.getTextContent());
            }

            node = children.get("component");

            if (node != null) {
                return createTagWithComponentType(namespace,name, node);
            }

            node = children.get("converter");

            if (node != null) {
                return createTagWithConverter(namespace,name, node);
            }

            node = children.get("validator");

            if (node != null) {
                return createValidatorTag(namespace,name, node);
            }
        }

        return null;
    }

    private static Map<String, Node> indexChildren(final Node node) {
        final Map<String, Node> children = new HashMap<String, Node>();
        final NodeList nodeList = node.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            final Node childNode = nodeList.item(i);

            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                children.put(childNode.getNodeName(), childNode);
            }
        }

        return children;
    }

    private static ComponentTag createTagWithComponentType(final String uri, final String tagName,
            Node node) {
        final Map<String, Node> componentChildren = indexChildren(node);
        node = componentChildren.get("component-type");

        if (node != null) {
            final String componentType = node.getTextContent();
            String rendererType = null;
            String handlerClass = null;

            node = componentChildren.get("renderer-type");
            if (node != null) {
                rendererType = node.getTextContent();
            }

            node = componentChildren.get("handler-class");
            if (node != null) {
                handlerClass = node.getTextContent();
            }
            ComponentTypeInfo typeInfo = new ComponentTypeInfo(componentType, handlerClass, rendererType,null);
            return new ComponentTag(uri, tagName, typeInfo);
        }
        return null;
    }

    private static ConverterTag createTagWithConverter(final String uri, final String tagName, Node node) {
        final Map<String, Node> converterChildren = indexChildren(node);
        node = converterChildren.get("converter-id");

        if (node != null) {
            //final String converterId = node.getTextContent();
            String handlerClass = null;

            node = converterChildren.get("handler-class");
            if (node != null) {
                handlerClass = node.getTextContent();
            }
            // for now, all converters are unknown
            return new ConverterTag(uri, tagName, ConverterTypeInfo.UNKNOWN, handlerClass);
        }
        return null;
    }

    private static ValidatorTag createValidatorTag(final String uri, final String tagName, Node node) {
        final Map<String, Node> converterChildren = indexChildren(node);
        node = converterChildren.get("validator-id");

        if (node != null) {
            //final String validatorId = node.getTextContent();
            String handlerClass = null;

            node = converterChildren.get("handler-class");
            if (node != null) {
                handlerClass = node.getTextContent();
            }
            return new ValidatorTag(uri, tagName, ValidatorTypeInfo.UNKNOWN, handlerClass);
        }
        return null;
    }
}
