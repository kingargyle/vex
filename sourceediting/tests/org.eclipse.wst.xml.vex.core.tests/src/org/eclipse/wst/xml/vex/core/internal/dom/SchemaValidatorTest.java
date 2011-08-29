/*******************************************************************************
 * Copyright (c) 2011 Florian Thienel and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 		Florian Thienel - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.dom;

import static org.eclipse.wst.xml.vex.core.internal.dom.Validator.PCDATA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.wst.common.uriresolver.internal.provisional.URIResolver;
import org.eclipse.wst.common.uriresolver.internal.provisional.URIResolverPlugin;
import org.eclipse.wst.xml.core.internal.contentmodel.CMDocument;
import org.eclipse.wst.xml.core.internal.contentmodel.CMElementDeclaration;
import org.eclipse.wst.xml.core.internal.contentmodel.ContentModelManager;
import org.eclipse.wst.xml.vex.core.internal.validator.WTPVEXValidator;
import org.junit.Test;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Florian Thienel
 */
public class SchemaValidatorTest {
	
	private static final String STRUCTURE_NS = "http://www.eclipse.org/vex/test/structure";
	private static final QualifiedName CHAPTER = new QualifiedName(STRUCTURE_NS, "chapter");
	private static final QualifiedName TITLE = new QualifiedName(STRUCTURE_NS, "title");
	
	private static final String CONTENT_NS = "http://www.eclipse.org/vex/test/content";
	private static final QualifiedName P = new QualifiedName(CONTENT_NS, "p");
	private static final QualifiedName B = new QualifiedName(CONTENT_NS, "b");
	private static final QualifiedName I = new QualifiedName(CONTENT_NS, "i");
	
	@Test
	public void readDocumentWithTwoSchemas() throws Exception {
		final EntityResolver entityResolver = new EntityResolver() {
			public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
				return null;
			}
		};
		
		final InputStream documentStream = getClass().getResourceAsStream("document.xml");
		final InputSource documentInputSource = new InputSource(documentStream);
		
		final DocumentReader reader = new DocumentReader();
		reader.setDebugging(true);
		reader.setEntityResolver(entityResolver);
		reader.setWhitespacePolicyFactory(IWhitespacePolicyFactory.NULL);
		final Document document = reader.read(documentInputSource);
		assertNotNull(document);
		
		final Element rootElement = document.getRootElement();
		assertNotNull(rootElement);
		assertEquals("chapter", rootElement.getLocalName());
		assertEquals("chapter", rootElement.getPrefixedName());
		assertEquals(CHAPTER, rootElement.getQualifiedName());
		assertEquals(STRUCTURE_NS, rootElement.getDefaultNamespaceURI());
		assertEquals(CONTENT_NS, rootElement.getNamespaceURI("c"));
		
		final Element subChapterElement = rootElement.getChildElements().get(1);
		assertEquals("chapter", subChapterElement.getPrefixedName());
		assertEquals(CHAPTER, subChapterElement.getQualifiedName());
		
		final Element paragraphElement = subChapterElement.getChildElements().get(1);
		assertEquals("p", paragraphElement.getLocalName());
		assertEquals("c:p", paragraphElement.getPrefixedName());
		assertEquals(P, paragraphElement.getQualifiedName());
	}

	@Test
	public void getCMDocumentsByLogicalName() throws Exception {
		final URIResolver uriResolver = URIResolverPlugin.createResolver();
		final ContentModelManager modelManager = ContentModelManager.getInstance();
		
		final String schemaLocation = uriResolver.resolve(null, STRUCTURE_NS, null);
		assertNotNull(schemaLocation);
		final CMDocument schema = modelManager.createCMDocument(schemaLocation, null);
		assertNotNull(schema);
		
		final String dtdLocation = uriResolver.resolve(null, "-//Eclipse Foundation//DTD Vex Test//EN", null);
		assertNotNull(dtdLocation);
		final CMDocument dtd = modelManager.createCMDocument(dtdLocation, null);
		assertNotNull(dtd);
	}
	
	@Test
	public void useCMDocument() throws Exception {
		final URIResolver uriResolver = URIResolverPlugin.createResolver();
		final ContentModelManager modelManager = ContentModelManager.getInstance();
		
		final String structureSchemaLocation = uriResolver.resolve(null, STRUCTURE_NS, null);
		final CMDocument structureSchema = modelManager.createCMDocument(structureSchemaLocation, null);
		
		assertEquals(1, structureSchema.getElements().getLength());
		
		final CMElementDeclaration chapterElement = (CMElementDeclaration) structureSchema.getElements().item(0);
		assertEquals("chapter", chapterElement.getNodeName());
		
		assertEquals(2, chapterElement.getLocalElements().getLength());
	}
	
	@Test
	public void createValidatorWithNamespaceUri() throws Exception {
		final WTPVEXValidator validator = new WTPVEXValidator(CONTENT_NS);
		assertEquals(1, validator.getValidRootElements().size());
		assertTrue(validator.getValidRootElements().contains(P));
	}
	
	@Test
	public void createValidatorWithDTDPublicId() throws Exception {
		final Validator validator = new WTPVEXValidator("-//Eclipse Foundation//DTD Vex Test//EN");
		assertEquals(10, validator.getValidRootElements().size());
	}
	
	@Test
	public void validateSimpleSchema() throws Exception {
		final Validator validator = new WTPVEXValidator("http://www.eclipse.org/vex/test/content");
		assertIsValidSequence(validator, P, PCDATA);
	}
	
	private void assertIsValidSequence(final Validator validator, final QualifiedName parentElement, final QualifiedName... sequence) {
		for (int i = 0; i < sequence.length; i++) {
			final List<QualifiedName> prefix = createPrefix(i, sequence);
			final List<QualifiedName> toInsert = Collections.singletonList(sequence[i]);
			final List<QualifiedName> suffix = createSuffix(i, sequence);

			assertTrue(validator.isValidSequence(parentElement, prefix, toInsert, suffix, false));
		}
	}

	private static List<QualifiedName> createPrefix(final int index, final QualifiedName... sequence) {
		final List<QualifiedName> prefix = new ArrayList<QualifiedName>();
		for (int i = 0; i < index; i++)
			prefix.add(sequence[i]);
		return prefix;
	}

	private static List<QualifiedName> createSuffix(final int index, final QualifiedName... sequence) {
		final List<QualifiedName> suffix = new ArrayList<QualifiedName>();
		for (int i = index + 1; i < sequence.length; i++)
			suffix.add(sequence[i]);
		return suffix;
	}
	
}
