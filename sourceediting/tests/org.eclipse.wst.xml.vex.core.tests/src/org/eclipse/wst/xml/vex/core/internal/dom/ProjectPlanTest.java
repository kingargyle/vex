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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.QualifiedName;
import org.junit.Test;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Florian Thienel
 */
public class ProjectPlanTest {
	
	@Test
	public void readProjectPlan() throws Exception {
		final EntityResolver entityResolver = new EntityResolver() {
			public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
				return null;
			}
		};
		
		final InputStream projectPlanStream = getClass().getResourceAsStream("projectplan.xml");
		final InputSource projectPlanInputSource = new InputSource(projectPlanStream);
		
		final DocumentReader reader = new DocumentReader();
		reader.setDebugging(true);
		reader.setEntityResolver(entityResolver);
		reader.setWhitespacePolicyFactory(IWhitespacePolicyFactory.NULL);
		final Document projectPlan = reader.read(projectPlanInputSource);
		assertNotNull(projectPlan);
		
		final Element rootElement = projectPlan.getRootElement();
		assertNotNull(rootElement);
		assertEquals("plan", rootElement.getLocalName());
		assertEquals("plan", rootElement.getPrefixedName());
		assertEquals(new QualifiedName("http://www.eclipse.org/project/plan", "plan"), rootElement.getQualifiedName());
		assertEquals("http://www.eclipse.org/project/plan", rootElement.getDefaultNamespaceURI());
		assertEquals("http://www.w3.org/1999/xhtml", rootElement.getNamespaceURI("html"));
		
		final Element introductionElement = rootElement.getChildElements().get(1);
		assertEquals("introduction", introductionElement.getPrefixedName());
		assertEquals(new QualifiedName("http://www.eclipse.org/project/plan", "introduction"), introductionElement.getQualifiedName());
		
		final Element introductionDivElement = introductionElement.getChildElements().get(0);
		assertEquals("div", introductionDivElement.getLocalName());
		assertEquals("html:div", introductionDivElement.getPrefixedName());
		assertEquals(new QualifiedName("http://www.w3.org/1999/xhtml", "div"), introductionDivElement.getQualifiedName());
	}

}
