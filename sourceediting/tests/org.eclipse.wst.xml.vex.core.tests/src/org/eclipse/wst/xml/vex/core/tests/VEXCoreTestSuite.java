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

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.wst.xml.vex.core.internal.css.CssTest;
import org.eclipse.wst.xml.vex.core.internal.css.PropertyTest;
import org.eclipse.wst.xml.vex.core.internal.css.RuleTest;
import org.eclipse.wst.xml.vex.core.internal.dom.BlockElementBoxTest;
import org.eclipse.wst.xml.vex.core.internal.dom.DTDValidatorTest;
import org.eclipse.wst.xml.vex.core.internal.dom.DocumentWriterTest;
import org.eclipse.wst.xml.vex.core.internal.dom.GapContentTest;
import org.eclipse.wst.xml.vex.core.internal.dom.NamespaceStackTest;
import org.eclipse.wst.xml.vex.core.internal.dom.NamespaceTest;
import org.eclipse.wst.xml.vex.core.internal.dom.SchemaValidatorTest;
import org.eclipse.wst.xml.vex.core.internal.dom.SpaceNormalizerTest;
import org.eclipse.wst.xml.vex.core.internal.dom.TextWrapperTest;
import org.eclipse.wst.xml.vex.core.internal.layout.ImageBoxTest;
import org.eclipse.wst.xml.vex.core.internal.layout.TableLayoutTest;
import org.eclipse.wst.xml.vex.core.internal.layout.TestBlockElementBox;
import org.eclipse.wst.xml.vex.core.internal.layout.TestBlocksInInlines;
import org.eclipse.wst.xml.vex.core.internal.layout.TestDocumentTextBox;
import org.eclipse.wst.xml.vex.core.internal.layout.TestStaticTextBox;
import org.eclipse.wst.xml.vex.core.internal.widget.VexWidgetTest;

public class VEXCoreTestSuite extends TestSuite {
	public static Test suite() {
		return new VEXCoreTestSuite();
	}

	public VEXCoreTestSuite() {
		super("Vex Core Tests");
		addTest(new JUnit4TestAdapter(NamespaceStackTest.class));
		addTest(new JUnit4TestAdapter(NamespaceTest.class));
		addTest(new JUnit4TestAdapter(SchemaValidatorTest.class));
		addTestSuite(CssTest.class);
		addTestSuite(PropertyTest.class);
		addTestSuite(RuleTest.class);
		addTestSuite(BlockElementBoxTest.class);
		addTest(new JUnit4TestAdapter(ImageBoxTest.class));
		addTestSuite(DocumentWriterTest.class);
		addTestSuite(DTDValidatorTest.class);
		addTestSuite(GapContentTest.class);
		addTestSuite(SpaceNormalizerTest.class);
		addTestSuite(TextWrapperTest.class);
		addTestSuite(TestBlockElementBox.class);
		addTestSuite(TestBlocksInInlines.class);
		addTestSuite(TestDocumentTextBox.class);
		addTestSuite(TestStaticTextBox.class);
		addTestSuite(TableLayoutTest.class);
		addTestSuite(ListenerListTest.class);
		addTest(new JUnit4TestAdapter(VexWidgetTest.class));
	}
}
