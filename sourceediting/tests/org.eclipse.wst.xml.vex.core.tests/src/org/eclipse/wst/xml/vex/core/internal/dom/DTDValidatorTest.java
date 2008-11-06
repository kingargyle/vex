/*******************************************************************************
 * Copyright (c) 2004, 2008 John Krasnay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     John Krasnay - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.dom;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.wst.xml.vex.core.internal.dom.Document;
import org.eclipse.wst.xml.vex.core.internal.dom.Element;
import org.eclipse.wst.xml.vex.core.internal.dom.RootElement;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.VEXDocument;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.VEXElement;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.Validator;
import org.eclipse.wst.xml.vex.core.internal.validator.AttributeDefinition;
import org.eclipse.wst.xml.vex.core.internal.validator.DTDValidator;
import org.eclipse.wst.xml.vex.core.internal.validator.WTPVEXValidator;

import junit.framework.TestCase;

@SuppressWarnings("restriction")
public class DTDValidatorTest extends TestCase {
	Validator validator = null;

	protected void setUp() {
		try {
			URL url = DTDValidatorTest.class.getResource("test1.dtd");
			validator = WTPVEXValidator.create(url);
		} catch (Exception ex) {
			fail("Failed to load test1.dtd");
		}
	}

	public void testAttributeDefinition() throws Exception {
		AttributeDefinition.Type adType = validator
				.getAttributeDefinitions("section")[0].getType();

		// Test serialization while we're at it
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(validator);

		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);
		validator = (Validator) ois.readObject();

		AttributeDefinition.Type adType2 = validator
				.getAttributeDefinitions("section")[0].getType();

		assertSame(adType, adType2);

	}
	
//	public void testEmptyDTD() throws Exception {
//		VEXDocument doc;
//		Set expected;
//
//		doc = new Document(new RootElement("empty"));
//		doc.setValidator(validator);
//		assertEquals(Collections.EMPTY_SET, getValidItemsAt(doc, 1));
//	}
	
//	public void testAnyDTD() throws Exception {
//		VEXDocument doc;
//		Set expected;
//
//		doc = new Document(new RootElement("any"));
//		doc.setValidator(validator);
//		Set anySet = new HashSet();
//		anySet.add(Validator.PCDATA);
//		anySet.add("any");
//		anySet.add("empty");
//		anySet.add("section");
//		anySet.add("title");
//		anySet.add("para");
//		anySet.add("emphasis");
//		assertEquals(anySet, getValidItemsAt(doc, 1));
//		
//	}
	
	public void testSectionElement() {
		VEXDocument doc;
		Set expected;

		// <section> <title> a b </title> <para> </para> </section>
		// 1 2 3 4 5 6 7
		doc = new Document(new RootElement("section"));
		doc.setValidator(validator);
		doc.insertElement(1, new Element("title"));
		doc.insertText(2, "ab");
		doc.insertElement(5, new Element("para"));
		
		//Bug 250828 - These tests will eventually be gone.  They are acting a little odd
		// with the new content model, but appear to be working within the editor.  Not
		// sure if these tests are truely still valid or were ever working correctly.
		// New tests will need to be written when further refactoring of the content model
		// is done.
		
		expected = new HashSet();
		expected.add("title");
		expected.add("para");
		assertEquals(expected, getValidItemsAt(doc, 1));
		
		expected = Collections.emptySet();
		assertEquals(expected, getValidItemsAt(doc, 2));
		assertEquals(expected, getValidItemsAt(doc, 3));
		assertEquals(expected, getValidItemsAt(doc, 4));
		
		expected = new HashSet();
		expected.add("title");
		expected.add("para");
		assertEquals(expected, getValidItemsAt(doc, 5));
		assertEquals(expected, getValidItemsAt(doc, 7));
		
		expected = new HashSet();
		expected.add("emphasis");
		assertEquals(expected, getValidItemsAt(doc, 6));		
	}


	private Set getValidItemsAt(VEXDocument doc, int offset) {
		VEXElement element = doc.getElementAt(offset);
		String[] prefix = doc
				.getNodeNames(element.getStartOffset() + 1, offset);
		String[] suffix = doc.getNodeNames(offset, element.getEndOffset());
		return doc.getValidator().getValidItems(element.getName());
	}
	/*
	 * private void dump(Validator validator, Document doc, int offset) { Set
	 * set = getValidItemsAt(doc, offset);
	 * 
	 * Iterator iter = set.iterator(); while (iter.hasNext()) {
	 * System.out.println("  " + iter.next()); } }
	 */
}
