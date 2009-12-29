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

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.wst.xml.vex.ui.internal.editor.tests.FindReplaceTargetTest;
import org.eclipse.wst.xml.vex.ui.internal.tests.ResourceTrackerTest;

public class VexUiTestSuite extends TestSuite {
	public static Test suite() {
		return new VexUiTestSuite();
	}

	public VexUiTestSuite() {
		super("Vex Core Tests"); //$NON-NLS-1$
		addTestSuite(IconTest.class);
		addTestSuite(FindReplaceTargetTest.class);
		addTestSuite(ResourceTrackerTest.class);
	}

}
