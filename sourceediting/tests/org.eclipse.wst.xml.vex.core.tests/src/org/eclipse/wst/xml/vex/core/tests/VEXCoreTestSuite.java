/*******************************************************************************
 * Copyright (c) 2004, 2010 John Krasnay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     John Krasnay - initial API and implementation
 *     Florian Thienel - bug 306639 - remove serializability from StyleSheet
 *                       and dependend classes
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.wst.xml.vex.core.internal.css.*;
import org.eclipse.wst.xml.vex.core.internal.dom.*;
import org.eclipse.wst.xml.vex.core.internal.layout.*;
import org.eclipse.wst.xml.vex.core.internal.widget.VexWidgetTest;

public class VEXCoreTestSuite extends TestSuite {
	public static Test suite() {
		return new VEXCoreTestSuite();
	}

	public VEXCoreTestSuite() {
		super("Vex Core Tests");
		addTestSuite(CssTest.class);
		addTestSuite(PropertyTest.class);
		addTestSuite(RuleTest.class);
		addTestSuite(SerializationTest.class);
		addTestSuite(BlockElementBoxTest.class);
		addTestSuite(DocumentWriterTest.class);
		addTestSuite(DomTest.class);
		addTestSuite(DTDValidatorTest.class);
		addTestSuite(GapContentTest.class);
		addTestSuite(SpaceNormalizerTest.class);
		addTestSuite(TextWrapperTest.class);
		addTestSuite(TestBlockElementBox.class);
		addTestSuite(TestBlocksInInlines.class);
		addTestSuite(TestDocumentTextBox.class);
		addTestSuite(TestStaticTextBox.class);
		addTestSuite(DOMSynchronizationTest.class);
		addTestSuite(TableLayoutTest.class);
		addTestSuite(ListenerListTest.class);
		addTestSuite(VexWidgetTest.class);
	}
}
