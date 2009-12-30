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
package org.eclipse.wst.xml.vex.core.internal.layout;

import java.net.URL;

import org.eclipse.wst.xml.vex.core.internal.core.DisplayDevice;
import org.eclipse.wst.xml.vex.core.internal.css.MockDisplayDevice;
import org.eclipse.wst.xml.vex.core.internal.css.StyleSheet;
import org.eclipse.wst.xml.vex.core.internal.css.StyleSheetReader;
import org.eclipse.wst.xml.vex.core.internal.css.Styles;
import org.eclipse.wst.xml.vex.core.internal.dom.RootElement;
import org.eclipse.wst.xml.vex.core.internal.layout.CssBoxFactory;
import org.eclipse.wst.xml.vex.core.internal.layout.InlineBox;
import org.eclipse.wst.xml.vex.core.internal.layout.LayoutContext;
import org.eclipse.wst.xml.vex.core.internal.layout.StaticTextBox;

import junit.framework.TestCase;

public class TestStaticTextBox extends TestCase {

	FakeGraphics g;
	LayoutContext context;
	RootElement root = new RootElement("root");
	Styles styles;

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		DisplayDevice.setCurrent(new MockDisplayDevice(90, 90));
		URL url = this.getClass().getResource("test.css");
		StyleSheetReader reader = new StyleSheetReader();
		StyleSheet ss = reader.read(url);

		g = new FakeGraphics();

		context = new LayoutContext();
		context.setBoxFactory(new CssBoxFactory());
		context.setGraphics(this.g);
		context.setStyleSheet(ss);

		styles = context.getStyleSheet().getStyles(root);
		
	}
	
	public TestStaticTextBox() throws Exception {

	}

	public void testSplit() throws Exception {

		int width = g.getCharWidth();

		StaticTextBox box = new StaticTextBox(this.context, root,
				"baggy orange trousers");
		assertEquals(box.getText().length() * width, box.getWidth());
		assertEquals(styles.getLineHeight(), box.getHeight());
		assertSplit(box, 22, false, "baggy orange ", "trousers");
		assertSplit(box, 21, false, "baggy orange ", "trousers");
		assertSplit(box, 20, false, "baggy orange ", "trousers");
		assertSplit(box, 13, false, "baggy orange ", "trousers");
		assertSplit(box, 12, false, "baggy ", "orange trousers");
		assertSplit(box, 6, false, "baggy ", "orange trousers");
		assertSplit(box, 5, false, null, "baggy orange trousers");
		assertSplit(box, 1, false, null, "baggy orange trousers");
		assertSplit(box, 0, false, null, "baggy orange trousers");
		assertSplit(box, -1, false, null, "baggy orange trousers");

		assertSplit(box, 22, true, "baggy orange ", "trousers");
		assertSplit(box, 21, true, "baggy orange ", "trousers");
		assertSplit(box, 20, true, "baggy orange ", "trousers");
		assertSplit(box, 13, true, "baggy orange ", "trousers");
		assertSplit(box, 12, true, "baggy ", "orange trousers");
		assertSplit(box, 6, true, "baggy ", "orange trousers");
		assertSplit(box, 5, true, "baggy ", "orange trousers");
		assertSplit(box, 4, true, "bagg", "y orange trousers");
		assertSplit(box, 3, true, "bag", "gy orange trousers");
		assertSplit(box, 2, true, "ba", "ggy orange trousers");
		assertSplit(box, 1, true, "b", "aggy orange trousers");
		assertSplit(box, 0, true, "b", "aggy orange trousers");
		assertSplit(box, -1, true, "b", "aggy orange trousers");
	}
	
	public void testSpaceSplit() throws Exception {
		StaticTextBox box = new StaticTextBox(context, root, "red  green");
		assertSplit(box, 11, false, "red  ", "green");
		assertSplit(box, 10, false, "red  ", "green");
		assertSplit(box, 9, false, "red  ", "green");
		assertSplit(box, 5, false, "red  ", "green");
		
	}
	

	private void assertSplit(StaticTextBox box, int splitPos, boolean force,
			String left, String right) {

		Styles styles = this.context.getStyleSheet()
				.getStyles(box.getElement());
		int width = g.getCharWidth();

		InlineBox.Pair pair = box.split(context, splitPos * width, force);

		StaticTextBox leftBox = (StaticTextBox) pair.getLeft();
		StaticTextBox rightBox = (StaticTextBox) pair.getRight();

		if (left == null) {
			assertNull(leftBox);
		} else {
			assertNotNull(leftBox);
			assertEquals(left, leftBox.getText());
			assertEquals(left.length() * width, leftBox.getWidth());
			assertEquals(styles.getLineHeight(), leftBox.getHeight());
		}

		if (right == null) {
			assertNull(rightBox);
		} else {
			assertNotNull(rightBox);
			assertEquals(right, rightBox.getText());
			assertEquals(right.length() * width, rightBox.getWidth());
			assertEquals(styles.getLineHeight(), rightBox.getHeight());
		}

	}
}
