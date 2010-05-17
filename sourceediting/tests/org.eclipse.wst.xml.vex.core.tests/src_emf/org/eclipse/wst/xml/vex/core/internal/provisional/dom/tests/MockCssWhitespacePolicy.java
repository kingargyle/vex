/*******************************************************************************
 * Copyright (c) 2004, 2008 John Krasnay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     John Krasnay - initial API and implementation
 *     Igor Jacy Lino Campista - Java 5 warnings fixed (bug 311325)
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.provisional.dom.tests;

import org.eclipse.wst.xml.vex.core.internal.css.StyleSheet;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IWhitespacePolicy;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement;

/**
 * Mock implementation of WhitespacePolicy using a CSS stylesheet.
 */
public class MockCssWhitespacePolicy implements IWhitespacePolicy {

	/**
	 * Class constructor.
	 * 
	 * @param styleSheet
	 *            The stylesheet used for the policy.
	 */
	public MockCssWhitespacePolicy(StyleSheet styleSheet) {
	}

	public boolean isBlock(VEXElement element) {
		return false;
	}

	public boolean isPre(VEXElement element) {
		return false;
	}

}
