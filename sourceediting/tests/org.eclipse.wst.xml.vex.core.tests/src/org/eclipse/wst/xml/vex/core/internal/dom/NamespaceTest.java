/*******************************************************************************
 * Copyright (c) 2010 Florian Thienel and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 		Florian Thienel - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.dom;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collections;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.wst.xml.vex.core.internal.css.Rule;
import org.eclipse.wst.xml.vex.core.internal.css.StyleSheet;
import org.eclipse.wst.xml.vex.core.internal.widget.CssWhitespacePolicy;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * @author Florian Thienel
 */
public class NamespaceTest {

	@Test
	public void attributeQualifiedName() throws Exception {
		final Attribute attributeWithoutNamespace = new Attribute(null, "localName", "value");
		assertEquals("localName", attributeWithoutNamespace.getQualifiedName().toString());
		final Attribute attributeWithNamespace = new Attribute(null, new QualifiedName("http://namespace/uri", "localName"), "value");
		assertEquals("http://namespace/uri:localName", attributeWithNamespace.getQualifiedName().toString());
	}

	@Test
	public void declareNamespace() throws Exception {
		final Element element = new Element("element");
		assertNull(element.getNamespaceURI("nsPrefix"));
		assertNull(element.getNamespacePrefix("http://namespace/uri"));

		element.declareNamespace("nsPrefix", "http://namespace/uri");
		assertEquals("http://namespace/uri", element.getNamespaceURI("nsPrefix"));
		assertEquals("nsPrefix", element.getNamespacePrefix("http://namespace/uri"));
	}

	@Test
	public void transitiveNamespaceDeclaration() throws Exception {
		final Element parent = new Element("parent");
		final Element child = new Element("child");
		child.setParent(parent);
		assertNull(child.getNamespaceURI("nsPrefix"));
		assertNull(child.getNamespacePrefix("http://namespace/uri"));

		parent.declareNamespace("nsPrefix", "http://namespace/uri");
		assertEquals("http://namespace/uri", child.getNamespaceURI("nsPrefix"));
		assertEquals("nsPrefix", child.getNamespacePrefix("http://namespace/uri"));
	}

	@Test
	public void removeNamespaceDeclaration() throws Exception {
		final Element element = new Element("element");
		element.declareNamespace("nsPrefix", "http://namespace/uri");
		assertEquals("http://namespace/uri", element.getNamespaceURI("nsPrefix"));
		assertEquals("nsPrefix", element.getNamespacePrefix("http://namespace/uri"));

		element.removeNamespace("nsPrefix");
		assertNull(element.getNamespaceURI("nsPrefix"));
		assertNull(element.getNamespacePrefix("http://namespace/uri"));
	}

	@Test
	public void overloadNamespaceDeclaration() throws Exception {
		final Element element = new Element("element");
		element.declareNamespace("nsPrefix", "http://namespace/uri");
		element.declareNamespace("nsPrefix2", "http://namespace/uri");
		assertEquals("http://namespace/uri", element.getNamespaceURI("nsPrefix"));
		assertEquals("http://namespace/uri", element.getNamespaceURI("nsPrefix2"));
		assertNotNull(element.getNamespacePrefix("http://namespace/uri"));
	}

	@Test
	public void overrideNamespaceDeclaration() throws Exception {
		final Element parent = new Element("parent");
		final Element child = new Element("child");
		child.setParent(parent);
		parent.declareNamespace("nsPrefix", "http://namespace/uri");
		assertEquals("http://namespace/uri", child.getNamespaceURI("nsPrefix"));
		assertEquals("nsPrefix", child.getNamespacePrefix("http://namespace/uri"));

		child.declareNamespace("nsPrefix", "http://namespace2/uri");
		assertNull(child.getNamespacePrefix("http://namespace/uri"));
		assertEquals("http://namespace2/uri", child.getNamespaceURI("nsPrefix"));
		assertEquals("nsPrefix", child.getNamespacePrefix("http://namespace2/uri"));
	}

	@Test
	public void ignoreEmptyNamespaceURI() throws Exception {
		final Element element = new Element("element");
		element.declareNamespace("nsPrefix", null);
		assertNull(element.getNamespaceURI("nsPrefix"));
		assertNull(element.getNamespacePrefix(null));

		element.declareNamespace("nsPrefix", "");
		assertNull(element.getNamespaceURI("nsPrefix"));
		assertNull(element.getNamespacePrefix(""));

		element.declareNamespace("nsPrefix", " ");
		assertNull(element.getNamespaceURI("nsPrefix"));
		assertNull(element.getNamespacePrefix(" "));
		assertNull(element.getNamespacePrefix(""));
	}

	@Test
	public void handleDefaultNamespace() throws Exception {
		final Element element = new Element("element");
		assertNull(element.getDefaultNamespaceURI());
		element.declareDefaultNamespace("http://namespace/uri");
		assertEquals("http://namespace/uri", element.getDefaultNamespaceURI());
	}

	@Test
	public void elementLocalName() throws Exception {
		final Element elementWithoutNamespace = new Element("localName");
		assertEquals("localName", elementWithoutNamespace.getLocalName());
		final Element elementWithNamespace = new Element(new QualifiedName("http://namespace/uri", "localName"));
		assertEquals("localName", elementWithNamespace.getLocalName());
	}

	@Test
	public void elementQualifiedName() throws Exception {
		final Element elementWithoutNamespace = new Element("localName");
		assertEquals("localName", elementWithoutNamespace.getQualifiedName().toString());
		final Element elementWithNamespace = new Element(new QualifiedName("http://namespace/uri", "localName"));
		assertEquals("http://namespace/uri:localName", elementWithNamespace.getQualifiedName().toString());
	}

	@Test
	public void elementPrefix() throws Exception {
		final Element elementWithoutNamespace = new Element("element");
		assertNull(elementWithoutNamespace.getPrefix());

		final Element elementWithNamespace = new Element(new QualifiedName("http://namespace/uri", "element"));
		elementWithNamespace.declareNamespace("nsPrefix", "http://namespace/uri");
		assertEquals("nsPrefix", elementWithNamespace.getPrefix());

		final Element elementWithDefaultNamespace = new Element(new QualifiedName("http://namespace/uri", "element"));
		elementWithDefaultNamespace.declareDefaultNamespace("http://namespace/uri");
		assertNull(elementWithDefaultNamespace.getPrefix());
	}

	@Test
	public void elementPrefixedName() throws Exception {
		final Element elementWithoutNamespace = new Element("element");
		assertEquals("element", elementWithoutNamespace.getPrefixedName());

		final Element elementWithNamespace = new Element(new QualifiedName("http://namespace/uri", "element"));
		elementWithNamespace.declareNamespace("nsPrefix", "http://namespace/uri");
		assertEquals("nsPrefix:element", elementWithNamespace.getPrefixedName());

		final Element elementWithDefaultNamespace = new Element(new QualifiedName("http://namespace/uri", "element"));
		elementWithDefaultNamespace.declareDefaultNamespace("http://namespace/uri");
		assertEquals("element", elementWithDefaultNamespace.getPrefixedName());
	}

	@Test
	public void attributePrefixedName() throws Exception {
		final Element element = new Element(new QualifiedName("http://namespace/uri/1", "element"));
		element.declareDefaultNamespace("http://namespace/uri/default");
		element.declareNamespace("ns1", "http://namespace/uri/1");
		element.declareNamespace("ns2", "http://namespace/uri/2");
		element.setAttribute("attribute1", "value1");
		element.setAttribute(new QualifiedName("http://namespace/uri/1", "attribute2"), "value2");
		element.setAttribute(new QualifiedName("http://namespace/uri/2", "attribute3"), "value3");

		assertEquals("attribute1", element.getAttribute("attribute1").getPrefixedName());
		assertEquals("attribute2", element.getAttribute(new QualifiedName("http://namespace/uri/1", "attribute2")).getPrefixedName());
		assertEquals("ns2:attribute3", element.getAttribute(new QualifiedName("http://namespace/uri/2", "attribute3")).getPrefixedName());
	}

	@Test
	public void readNamespaceDeclarations() throws Exception {
		final Document document = readDocumentFromString("<ns1:a xmlns=\"http://namespace/default\" xmlns:ns1=\"http://namespace/uri/1\"/>");
		final Element rootElement = document.getRootElement();

		assertEquals("http://namespace/default", rootElement.getDefaultNamespaceURI());
		assertEquals("http://namespace/uri/1", rootElement.getNamespaceURI("ns1"));
	}

	private Document readDocumentFromString(final String documentContent) throws IOException, ParserConfigurationException, SAXException {
		final DocumentReader documentReader = new DocumentReader();
		documentReader.setWhitespacePolicyFactory(new IWhitespacePolicyFactory() {
			public IWhitespacePolicy getPolicy(final String publicId) {
				return new CssWhitespacePolicy(new StyleSheet(Collections.<Rule> emptyList()));
			}
		});
		return documentReader.read(documentContent);
	}

	@Test
	public void readNestedNamespaceDeclarations() throws Exception {
		final Document document = readDocumentFromString(
				"<ns1:a xmlns=\"http://namespace/default\" xmlns:ns1=\"http://namespace/uri/1\">"
				+ "<ns2:b xmlns:ns2=\"http://namespace/uri/2\"/>" 
				+ "</ns1:a>");
		final Element rootElement = document.getRootElement();
		final Element nestedElement = rootElement.getChildElements().get(0);
		assertEquals("http://namespace/default", nestedElement.getDefaultNamespaceURI());
		assertEquals("http://namespace/uri/1", nestedElement.getNamespaceURI("ns1"));
		assertEquals("http://namespace/uri/2", nestedElement.getNamespaceURI("ns2"));
		assertNull(rootElement.getNamespaceURI("ns2"));
	}
	
	@Test
	public void evaluateElementPrefix() throws Exception {
		final Document document = readDocumentFromString(
				"<ns1:a xmlns=\"http://namespace/default\" xmlns:ns1=\"http://namespace/uri/1\">"
				+ "<ns2:b xmlns:ns2=\"http://namespace/uri/2\"/>" 
				+ "<c />" 
				+ "</ns1:a>");
		final Element rootElement = document.getRootElement();
		assertEquals("http://namespace/uri/1", rootElement.getQualifiedName().getQualifier());
		assertEquals("a", rootElement.getLocalName());
		assertEquals("ns1:a", rootElement.getPrefixedName());
		
		final Element firstNestedElement = rootElement.getChildElements().get(0);
		assertEquals("http://namespace/uri/2", firstNestedElement.getQualifiedName().getQualifier());
		assertEquals("b", firstNestedElement.getLocalName());
		assertEquals("ns2:b", firstNestedElement.getPrefixedName());
		
		final Element secondNestedElement = rootElement.getChildElements().get(1);
		assertEquals("http://namespace/default", secondNestedElement.getQualifiedName().getQualifier());
		assertEquals("c", secondNestedElement.getLocalName());
		assertEquals("c", secondNestedElement.getPrefixedName());
	}
	
	@Test
	public void evaluateAttributePrefix() throws Exception {
		final Document document = readDocumentFromString(
				"<ns1:a xmlns=\"http://namespace/default\" xmlns:ns1=\"http://namespace/uri/1\" attr1=\"value1\">"
				+ "<ns2:b xmlns:ns2=\"http://namespace/uri/2\" ns1:attr2=\"value2\" ns2:attr3=\"value3\" attr4=\"value4\" />" 
				+ "<c ns1:attr5=\"value5\" attr6=\"value6\" />" 
				+ "</ns1:a>");
		final Element rootElement = document.getRootElement();
		assertTrue(rootElement.getAttributeNames().contains(new QualifiedName("http://namespace/uri/1", "attr1")));
		assertFalse(rootElement.getAttributeNames().contains(new QualifiedName("http://namespace/default", "attr1")));
		assertFalse(rootElement.getAttributeNames().contains(new QualifiedName("", "attr1")));
		assertFalse(rootElement.getAttributeNames().contains(new QualifiedName(null, "attr1")));
		
		final Element firstNestedElement = rootElement.getChildElements().get(0);
		assertTrue(firstNestedElement.getAttributeNames().contains(new QualifiedName("http://namespace/uri/1", "attr2")));
		assertTrue(firstNestedElement.getAttributeNames().contains(new QualifiedName("http://namespace/uri/2", "attr3")));
		assertTrue(firstNestedElement.getAttributeNames().contains(new QualifiedName("http://namespace/uri/2", "attr4")));
		
		final Element secondNestedElement = rootElement.getChildElements().get(1);
		assertTrue(secondNestedElement.getAttributeNames().contains(new QualifiedName("http://namespace/uri/1", "attr5")));
		assertTrue(secondNestedElement.getAttributeNames().contains(new QualifiedName("http://namespace/default", "attr6")));
	}
}
