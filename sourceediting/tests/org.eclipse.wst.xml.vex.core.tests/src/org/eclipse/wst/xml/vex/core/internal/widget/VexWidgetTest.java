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

import java.util.Arrays;

import org.eclipse.wst.xml.vex.core.internal.css.StyleSheet;
import org.eclipse.wst.xml.vex.core.internal.dom.Document;
import org.eclipse.wst.xml.vex.core.internal.dom.Element;
import org.eclipse.wst.xml.vex.core.internal.dom.RootElement;
import org.eclipse.wst.xml.vex.core.internal.dom.Validator;
import org.eclipse.wst.xml.vex.core.internal.validator.WTPVEXValidator;
import org.junit.Test;

public class VexWidgetTest {

	private static Document createDocument(final String rootSchemaIdentifier, final String rootElementName) {
		final Validator validator = new WTPVEXValidator(rootSchemaIdentifier);
		final Document document = new Document(new RootElement(rootElementName));
		document.setValidator(validator);
		return document;
	}

	@Test
	public void testProvideOnlyAllowedElements() throws Exception {
		final VexWidgetImpl widget = new VexWidgetImpl(new MockHostComponent());
		widget.setDocument(createDocument("-//Eclipse Foundation//DTD Vex Test//EN", "section"), StyleSheet.NULL);
		assertCanInsertOnly(widget, "title", "para");
		widget.insertElement(new Element("title"));
		assertCanInsertOnly(widget);
		widget.moveBy(1);
		assertCanInsertOnly(widget, "para");
		widget.insertElement(new Element("para"));
		widget.moveBy(1);
		assertCanInsertOnly(widget, "para");
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
