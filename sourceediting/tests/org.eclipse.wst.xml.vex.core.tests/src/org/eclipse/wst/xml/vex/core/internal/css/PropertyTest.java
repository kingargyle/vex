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
package org.eclipse.wst.xml.vex.core.internal.css;

import org.eclipse.wst.xml.vex.core.internal.core.DisplayDevice;
import org.eclipse.wst.xml.vex.core.internal.css.BorderStyleProperty;
import org.eclipse.wst.xml.vex.core.internal.css.BorderWidthProperty;
import org.eclipse.wst.xml.vex.core.internal.css.CSS;
import org.eclipse.wst.xml.vex.core.internal.css.IProperty;
import org.eclipse.wst.xml.vex.core.internal.css.Styles;
import org.w3c.css.sac.LexicalUnit;

import junit.framework.TestCase;

public class PropertyTest extends TestCase {

	/**
	 * From CSS2.1 section 8.5.3
	 */
	public void testBorderStyleProperty() throws Exception {
		Styles styles = new Styles();
		Styles parentStyles = new Styles();
		IProperty prop = new BorderStyleProperty(CSS.BORDER_TOP_STYLE);

		// Inheritance
		parentStyles.put(CSS.BORDER_TOP_STYLE, CSS.DASHED);
		assertEquals(CSS.NONE, prop.calculate(null, parentStyles, styles, null));
		assertEquals(CSS.DASHED, prop.calculate(MockLU.INHERIT, parentStyles,
				styles, null)); // not inherited

		// Regular values
		assertEquals(CSS.NONE, prop.calculate(MockLU.createIdent(CSS.NONE),
				parentStyles, styles, null));
		assertEquals(CSS.HIDDEN, prop.calculate(MockLU.createIdent(CSS.HIDDEN),
				parentStyles, styles, null));
		assertEquals(CSS.DOTTED, prop.calculate(MockLU.createIdent(CSS.DOTTED),
				parentStyles, styles, null));
		assertEquals(CSS.DASHED, prop.calculate(MockLU.createIdent(CSS.DASHED),
				parentStyles, styles, null));
		assertEquals(CSS.SOLID, prop.calculate(MockLU.createIdent(CSS.SOLID),
				parentStyles, styles, null));
		assertEquals(CSS.DOUBLE, prop.calculate(MockLU.createIdent(CSS.DOUBLE),
				parentStyles, styles, null));
		assertEquals(CSS.GROOVE, prop.calculate(MockLU.createIdent(CSS.GROOVE),
				parentStyles, styles, null));
		assertEquals(CSS.RIDGE, prop.calculate(MockLU.createIdent(CSS.RIDGE),
				parentStyles, styles, null));
		assertEquals(CSS.INSET, prop.calculate(MockLU.createIdent(CSS.INSET),
				parentStyles, styles, null));
		assertEquals(CSS.OUTSET, prop.calculate(MockLU.createIdent(CSS.OUTSET),
				parentStyles, styles, null));

		// Invalid token
		assertEquals(CSS.NONE, prop.calculate(MockLU.createIdent(CSS.BOLD),
				parentStyles, styles, null));

		// Wrong type
		assertEquals(CSS.NONE, prop.calculate(MockLU.createString(CSS.HIDDEN),
				parentStyles, styles, null));
	}

	/**
	 * From CSS2.1 section 8.5.1
	 */
	public void testBorderWidthProperty() throws Exception {

		Styles styles = new Styles();
		Styles parentStyles = new Styles();
		DisplayDevice.setCurrent(new DummyDisplayDevice(50, 100));
		IProperty prop = new BorderWidthProperty(CSS.BORDER_TOP_WIDTH,
				CSS.BORDER_TOP_STYLE, IProperty.AXIS_VERTICAL);

		styles.put(CSS.FONT_SIZE, new Float(12));
		styles.put(CSS.BORDER_TOP_STYLE, CSS.SOLID);

		// Inheritance
		parentStyles.put(CSS.BORDER_TOP_WIDTH, new Integer(27));
		assertEquals(new Integer(3), prop.calculate(null, parentStyles, styles, null));
		assertEquals(new Integer(27), prop.calculate(MockLU.INHERIT,
				parentStyles, styles, null)); // not inherited

		// Regular values
		assertEquals(new Integer(20), prop.calculate(MockLU.createFloat(
				LexicalUnit.SAC_INCH, 0.2f), parentStyles, styles, null));

		// Invalid token
		assertEquals(new Integer(3), prop.calculate(MockLU
				.createIdent(CSS.BOLD), parentStyles, styles, null));

		// Wrong type
		assertEquals(new Integer(3), prop.calculate(MockLU
				.createString(CSS.HIDDEN), parentStyles, styles, null));

		// Corresponding style is "none" or "hidden"
		styles.put(CSS.BORDER_TOP_STYLE, CSS.NONE);
		assertEquals(new Integer(0), prop.calculate(MockLU.createFloat(
				LexicalUnit.SAC_INCH, 0.2f), parentStyles, styles, null));
		styles.put(CSS.BORDER_TOP_STYLE, CSS.HIDDEN);
		assertEquals(new Integer(0), prop.calculate(MockLU.createFloat(
				LexicalUnit.SAC_INCH, 0.2f), parentStyles, styles, null));

		// check that we use the proper PPI
		styles.put(CSS.BORDER_LEFT_STYLE, CSS.SOLID);
		prop = new BorderWidthProperty(CSS.BORDER_LEFT_WIDTH,
				CSS.BORDER_LEFT_STYLE, IProperty.AXIS_HORIZONTAL);
		assertEquals(Integer.valueOf(10), prop.calculate(MockLU.createFloat(
				LexicalUnit.SAC_INCH, 0.2f), parentStyles, styles, null));
	}

	/**
	 * From CSS2.1 section 8.5.2 (border-XXX-color), section 14.1 (color), and
	 * section 14.2.1 (background-color)
	 */
	public void testColorProperty() throws Exception {
	}

	private static class DummyDisplayDevice extends DisplayDevice {

		public DummyDisplayDevice(int horizontalPPI, int verticalPPI) {
			this.horizontalPPI = horizontalPPI;
			this.verticalPPI = verticalPPI;
		}

		public int getHorizontalPPI() {
			return this.horizontalPPI;
		}

		public int getVerticalPPI() {
			return this.verticalPPI;
		}

		private int horizontalPPI;
		private int verticalPPI;
	}
}
