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

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.wst.common.uriresolver.internal.provisional.URIResolver;
import org.eclipse.wst.common.uriresolver.internal.provisional.URIResolverPlugin;
import org.eclipse.wst.xml.core.internal.contentmodel.CMDocument;
import org.eclipse.wst.xml.core.internal.contentmodel.CMElementDeclaration;
import org.eclipse.wst.xml.core.internal.contentmodel.CMNode;
import org.eclipse.wst.xml.core.internal.contentmodel.ContentModelManager;
import org.eclipse.wst.xml.core.internal.contentmodel.internal.util.CMValidator;
import org.junit.Test;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Florian Thienel
 */
public class SchemaValidatorTest {
	
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
		assertEquals(new QualifiedName("http://www.eclipse.org/vex/test/structure", "chapter"), rootElement.getQualifiedName());
		assertEquals("http://www.eclipse.org/vex/test/structure", rootElement.getDefaultNamespaceURI());
		assertEquals("http://www.eclipse.org/vex/test/content", rootElement.getNamespaceURI("c"));
		
		final Element subChapterElement = rootElement.getChildElements().get(1);
		assertEquals("chapter", subChapterElement.getPrefixedName());
		assertEquals(new QualifiedName("http://www.eclipse.org/vex/test/structure", "chapter"), subChapterElement.getQualifiedName());
		
		final Element paragraphElement = subChapterElement.getChildElements().get(1);
		assertEquals("p", paragraphElement.getLocalName());
		assertEquals("c:p", paragraphElement.getPrefixedName());
		assertEquals(new QualifiedName("http://www.eclipse.org/vex/test/content", "p"), paragraphElement.getQualifiedName());
	}

	@Test
	public void getCMDocumentsByLogicalName() throws Exception {
		final URIResolver uriResolver = URIResolverPlugin.createResolver();
		final ContentModelManager modelManager = ContentModelManager.getInstance();
		
		final String schemaLocation = uriResolver.resolve(null, "http://www.eclipse.org/vex/test/structure", null);
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
		
		final String structureSchemaLocation = uriResolver.resolve(null, "http://www.eclipse.org/vex/test/structure", null);
		final CMDocument structureSchema = modelManager.createCMDocument(structureSchemaLocation, null);
		
		assertEquals(1, structureSchema.getElements().getLength());
		
		final CMElementDeclaration chapterElement = (CMElementDeclaration) structureSchema.getElements().item(0);
		assertEquals("chapter", chapterElement.getNodeName());
		
		assertEquals(2, chapterElement.getLocalElements().getLength());
	}
	
}
