/*******************************************************************************
 * Copyright (c) 2010 Florian Thienel and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 		Florian Thienel - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.dom;

import static org.junit.Assert.*;

import org.eclipse.core.runtime.QualifiedName;
import org.junit.Test;

/**
 * @author Florian Thienel
 */
public class AttributeTest {

	@Test
	public void qualifiedName() throws Exception {
		final Attribute attributeWithoutNamespace = new Attribute(null, "localName", "value");
		assertEquals("localName", attributeWithoutNamespace.getQualifiedName().toString());
		final Attribute attributeWithNamespace = new Attribute(null, new QualifiedName("http://vex.eclipse.org/tests", "localName"), "value");
		assertEquals("http://vex.eclipse.org/tests:localName", attributeWithNamespace.getQualifiedName().toString());
	}
}
