/*******************************************************************************
 * Copyright (c) 2009 Holger Voormann and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Holger Voormann - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.ui.tests;

import org.eclipse.wst.xml.vex.ui.internal.Icon;

import junit.framework.TestCase;

public class IconTest extends TestCase {

	public void testWhetherAllIconsExist() throws Exception {
		for (Icon icon : Icon.values()) {
			assertNotNull(Icon.get(icon));
		}
	}

}
