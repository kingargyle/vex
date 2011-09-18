/*******************************************************************************
 * Copyright (c) 2010, Florian Thienel and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Florian Thienel - bug 315914, initial implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.widget;

import static org.junit.Assert.*;
import static org.eclipse.wst.xml.vex.core.tests.TestResources.*;

import java.util.Arrays;

import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.wst.xml.vex.core.internal.css.StyleSheet;
import org.eclipse.wst.xml.vex.core.internal.dom.Document;
import org.eclipse.wst.xml.vex.core.internal.dom.Element;
import org.eclipse.wst.xml.vex.core.internal.dom.RootElement;
import org.eclipse.wst.xml.vex.core.internal.dom.Validator;
import org.eclipse.wst.xml.vex.core.internal.validator.WTPVEXValidator;
import org.junit.Test;

public class VexWidgetTest {

	@Test
	public void provideOnlyAllowedElementsFromDtd() throws Exception {
		final VexWidgetImpl widget = new VexWidgetImpl(new MockHostComponent());
		widget.setDocument(createDocument(TEST_DTD, "section"), StyleSheet.NULL);
		assertCanInsertOnly(widget, "title", "para");
		widget.insertElement(new Element("title"));
		assertCanInsertOnly(widget);
		widget.moveBy(1);
		assertCanInsertOnly(widget, "para");
		widget.insertElement(new Element("para"));
		widget.moveBy(1);
		assertCanInsertOnly(widget, "para");
	}
	
	@Test
	public void provideOnlyAllowedElementsFromSimpleSchema() throws Exception {
		final VexWidgetImpl widget = new VexWidgetImpl(new MockHostComponent());
		widget.setDocument(createDocument(CONTENT_NS, "p"), StyleSheet.NULL);
		assertCanInsertOnly(widget, "b", "i");
		widget.insertElement(new Element("b"));
		assertCanInsertOnly(widget, "b", "i");
		widget.moveBy(1);
		assertCanInsertOnly(widget, "b", "i");
	}
	
	@Test
	public void provideOnlyAllowedElementFromComplexSchema() throws Exception {
		final VexWidgetImpl widget = new VexWidgetImpl(new MockHostComponent());
		widget.setDocument(createDocument(STRUCTURE_NS, "chapter"), StyleSheet.NULL);
		assertCanInsertOnly(widget, "title", "chapter", "p");
		widget.insertElement(new Element("title"));
		assertCanInsertOnly(widget);
		widget.moveBy(1);
//		assertCanInsertOnly(widget, "chapter", "p");
		widget.insertElement(new Element(new QualifiedName(CONTENT_NS, "p")));
		assertCanInsertOnly(widget, "b", "i");
		widget.moveBy(1);
//		assertCanInsertOnly(widget, "p");
		// FIXME: maybe the schema is still not what I mean
	}
	
	private static Document createDocument(final String rootSchemaIdentifier, final String rootElementName) {
		final Validator validator = new WTPVEXValidator(rootSchemaIdentifier);
		final Document document = new Document(new RootElement(rootElementName));
		document.setValidator(validator);
		return document;
	}
	
	private static void assertCanInsertOnly(IVexWidget widget, final String... elementNames) {
		assertTrue(Arrays.equals(sortedCopyOf(elementNames), sortedCopyOf(widget.getValidInsertElements())));
	}
	
	private static String[] sortedCopyOf(String[] strings) {
		final String[] result = new String[strings.length];
		System.arraycopy(strings, 0, result, 0, strings.length);
		Arrays.sort(result);
		return result;
	}

	
}
