/*******************************************************************************
 * Copyright (c) 2011 Florian Thienel and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 		Florian Thienel - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.dom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Florian Thienel
 */
public class NamespaceStackTest {

	@Test
	public void pushPeekPopDefault() throws Exception {
		final NamespaceStack stack = new NamespaceStack();
		assertNull(stack.peekDefault());

		stack.pushDefault("http://namespace/default/1");
		assertEquals("http://namespace/default/1", stack.peekDefault());

		stack.pushDefault("http://namespace/default/2");
		assertEquals("http://namespace/default/2", stack.peekDefault());

		assertEquals("http://namespace/default/2", stack.popDefault());
		assertEquals("http://namespace/default/1", stack.peekDefault());

		assertEquals("http://namespace/default/1", stack.popDefault());
		assertNull(stack.peekDefault());

		assertNull(stack.popDefault());
	}

	@Test
	public void pushPeekPopNamespace() throws Exception {
		final NamespaceStack stack = new NamespaceStack();
		assertNull(stack.peek("ns1"));

		stack.push("ns1", "http://namespace/uri/1");
		assertEquals("http://namespace/uri/1", stack.peek("ns1"));

		stack.push("ns1", "http://namespace/uri/2");
		assertEquals("http://namespace/uri/2", stack.peek("ns1"));

		assertEquals("http://namespace/uri/2", stack.pop("ns1"));
		assertEquals("http://namespace/uri/1", stack.peek("ns1"));

		assertEquals("http://namespace/uri/1", stack.pop("ns1"));
		assertNull(stack.peek("ns1"));

		assertNull(stack.pop("ns1"));
	}

	@Test
	public void clear() throws Exception {
		final NamespaceStack stack = new NamespaceStack();
		stack.pushDefault("http://namespace/default");
		stack.push("ns1", "http://namespace/uri/1");
		stack.push("ns2", "http://namespace/uri/2");
		stack.clear();
		assertNull(stack.peekDefault());
		assertNull(stack.peek("ns1"));
		assertNull(stack.peek("ns2"));
	}
	
	@Test
	public void getPrefixes() throws Exception {
		final NamespaceStack stack = new NamespaceStack();
		assertTrue(stack.getPrefixes().isEmpty());

		stack.pushDefault("http://namespace/default");
		assertTrue(stack.getPrefixes().isEmpty());
		
		stack.push("ns1", "http://namespace/uri/1");
		assertEquals(1, stack.getPrefixes().size());
		assertTrue(stack.getPrefixes().contains("ns1"));
		
		stack.push("ns2", "http://namespace/uri/2");
		assertEquals(2, stack.getPrefixes().size());
		assertTrue(stack.getPrefixes().contains("ns1"));
		assertTrue(stack.getPrefixes().contains("ns2"));
		
		stack.clear();
		assertTrue(stack.getPrefixes().isEmpty());
	}
}
