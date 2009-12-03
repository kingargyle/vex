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
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Validator;
import org.eclipse.wst.xml.vex.core.internal.validator.AttributeDefinition;
import org.eclipse.wst.xml.vex.core.internal.validator.WTPVEXValidator;

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
				.getAttributeDefinitions("section").get(0).getType();

		// Test serialization while we're at it
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(validator);

		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);
		validator = (Validator) ois.readObject();

		AttributeDefinition.Type adType2 = validator
				.getAttributeDefinitions("section").get(0).getType();

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

		// <section> <title> a b </title> <para> </para> </section>
		// 1 2 3 4 5 6 7
		VEXDocument doc = new Document(new RootElement("section"));
		doc.setValidator(validator);
		doc.insertElement(1, new Element("title"));
		doc.insertText(2, "ab");
		doc.insertElement(5, new Element("para"));
		
		assertValidItemsAt(doc, 1, "title", "para");
		assertValidItemsAt(doc, 2);
		assertValidItemsAt(doc, 3);
		assertValidItemsAt(doc, 4);
		assertValidItemsAt(doc, 5, "title", "para");
		assertValidItemsAt(doc, 6, "emphasis");
		assertValidItemsAt(doc, 7, "title", "para");
	}

	public void testOneKindOfChild() {
		VEXDocument doc = new Document(new RootElement("one-kind-of-child"));
		doc.setValidator(validator);
		assertValidItemsAt(doc, 1, "section");
	}
	
	public void testSequences() {
		assertValidSequence("title", "#PCDATA");
		assertInvalidSequence("empty", "#PCDATA");
        // TODO test more sequences like:
        // assertInvalidSequence("title", "section");
	}

	private void assertValidItemsAt(VEXDocument doc, int offset,
			String... expectedItems) {
		Set<String> expected = new HashSet<String>(expectedItems.length);
		for (int i = 0; i < expectedItems.length; i++) {
			expected.add(expectedItems[i]);
		}
		
		String elementName = doc.getElementAt(offset).getName();
		Set<String> validItems = doc.getValidator().getValidItems(elementName);
		assertEquals(expected, validItems);
	}

	private void assertValidSequence(String element, String...sequence) {
		assertValidSequence(true, element, sequence);
	}

	private void assertInvalidSequence(String element, String...sequence) {
		assertValidSequence(false, element, sequence);
	}

	private void assertValidSequence(boolean expected, String element, String...sequence) {
		final EList<String> emptyList = new BasicEList<String>(0);
		for (int i = 0; i < sequence.length; i++) {
			
			EList<String> pre = new BasicEList<String>();
			for (int j = 0; j < i; j++) {
				pre.add(sequence[j]);
			}

			EList<String> toInsert = new BasicEList<String>(1);
			toInsert.add(sequence[i]);
			
			EList<String> suf = new BasicEList<String>();
			for (int j = i + 1; j < sequence.length; j++) {
				pre.add(sequence[j]);
			}

			assertEquals(expected, validator.isValidSequence(element,
					                                         pre,
					                                         toInsert,
					                                         suf,
					                                         false));
			assertEquals(expected, validator.isValidSequence(element,
					                                         pre,
					                                         toInsert,
					                                         emptyList,
					                                         true));
		}
	}

}
