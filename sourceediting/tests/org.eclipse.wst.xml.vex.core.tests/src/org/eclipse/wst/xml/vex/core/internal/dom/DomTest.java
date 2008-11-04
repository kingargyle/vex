/*******************************************************************************
 * Copyright (c) 2004, 2008 John Krasnay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     John Krasnay - initial API and implementation
 *     David Carver - refactored unit tests
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.dom;

import java.util.List;

import org.eclipse.wst.xml.vex.core.internal.dom.Document;
import org.eclipse.wst.xml.vex.core.internal.dom.Element;
import org.eclipse.wst.xml.vex.core.internal.dom.RootElement;
import org.eclipse.wst.xml.vex.core.internal.dom.Text;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IVEXDocument;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IVEXDocumentFragment;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IVEXElement;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IVEXNode;

import junit.framework.TestCase;

/**
 * Test the <code>org.eclipse.wst.vex.core.internal.dom</code> package.
 */
@SuppressWarnings("restriction")

public class DomTest extends TestCase {
	
	public void testRootElement() throws Exception {
		RootElement root = new RootElement("article");
		IVEXDocument doc = new Document(root);
		
		assertEquals(2, doc.getLength());
		assertEquals(root, doc.getRootElement());
		assertEquals(0, root.getStartOffset());
		assertEquals(1, root.getEndOffset());		
	}
	
	public void testRootElementNoChildren() throws Exception {
		RootElement root = new RootElement("article");
		IVEXDocument doc = new Document(root);
		IVEXNode[] content;
		List<IVEXElement> children;
		
		content = root.getChildNodes();
		assertEquals(0, content.length);
		children = root.getChildElements();
		assertEquals(0, children.size());
	}
	
	public void testIllegaArgumentException() throws Exception {
		RootElement root = new RootElement("article");
		IVEXDocument doc = new Document(root);
		try {
			doc.insertText(0, "ac");
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException ex) {
		}
		
		try {
			doc.insertText(2, "ac");
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException ex) {
		}
		
	}
	
	public void testInsertTextIntoDocument() throws Exception {
		RootElement root = new RootElement("article");
		IVEXDocument doc = new Document(root);
		IVEXNode[] content;

		doc.insertText(1, "ac");
		assertEquals(4, doc.getLength());
		content = root.getChildNodes();
		assertEquals(1, content.length);
		
		assertIsText(content[0], "ac", 1, 3);
		
		assertEquals(1, content[0].getStartPosition().getOffset());
		assertEquals(3, content[0].getEndPosition().getOffset());
		assertEquals(0, root.getStartOffset());
		assertEquals(3, root.getEndOffset());
		
	}
	
	public void testInsertAtIllegalOffset() {
		RootElement root = new RootElement("article");
		IVEXDocument doc = new Document(root);

		//
		// Try inserting at illegal offset
		//
		Element element = new Element("b");

		try {
			doc.insertElement(0, element);
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException ex) {
		}

		try {
			doc.insertElement(4, element);
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException ex) {
		}
		
	}
	
	public void testElementInsert() throws Exception {
		RootElement root = new RootElement("article");
		IVEXDocument doc = new Document(root);
		IVEXNode[] content;
		doc.insertText(1, "ac");
		Element element = new Element("b");
		doc.insertElement(2, element);
		assertEquals(root, element.getParent());
		assertEquals(6, doc.getLength());

		Element element2 = new Element("x");
		doc.insertElement(2, element2);

		content = root.getChildNodes();
		assertEquals(4, content.length);
		assertIsText(content[0], "a", 1, 2);
		assertIsElement(content[1], "x", root, 2, 3);
		assertIsElement(content[2], "b", root, 4, 5);
		assertIsText(content[3], "c", 6, 7);
	}
	
	public void testTextFragment22() {
		IVEXDocument doc;
		IVEXDocumentFragment frag;
		doc = new Document(new RootElement("root"));
		doc.insertText(1, "abc");
		
		try {
			frag = doc.getFragment(2, 2);
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException ex) {
		}

		
	}
	
	public void testTextFragmentFailure() {
		IVEXDocument doc;
		IVEXDocumentFragment frag;
		doc = new Document(new RootElement("root"));
		doc.insertText(1, "abc");
		
		try {
			frag = doc.getFragment(-1, 0);
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException ex) {
		}

		try {
			frag = doc.getFragment(4, 5);
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException ex) {
		}
		
	}
	
	public void testgetFragment() throws Exception {
		IVEXDocument doc = new Document(new RootElement("root"));
		doc.insertText(1, "abc");

		IVEXDocumentFragment frag = doc.getFragment(2, 3);
		assertEquals(1, frag.getContent().getLength());
		assertEquals(0, frag.getElements().size());

		IVEXNode[] nodes = frag.getNodes();
		assertEquals(1, nodes.length);
		assertIsText(nodes[0], "b", 0, 1);
	}
	
	public void testgetFragment2() throws Exception {
		
		IVEXDocument doc = new Document(new RootElement("root"));
		doc.insertText(1, "ac");
		doc.insertElement(2, new Element("z"));
		doc.insertText(3, "b");

		IVEXDocumentFragment frag = doc.getFragment(1, 6);
		
		List<IVEXElement> elements = frag.getElements();
		assertEquals(1, elements.size());
		this.assertIsElement(elements.get(0), "z", null, 1, 3);
		
		IVEXNode[] nodes = frag.getNodes();
		assertEquals(3, nodes.length);
		assertIsText(nodes[0], "a", 0, 1);
		assertIsElement(nodes[1], "z", null, 1, 3);
		assertIsText(nodes[2], "c", 4, 5);
		nodes = elements.get(0).getChildNodes();
		assertEquals(1, nodes.length);
		assertIsText(nodes[0], "b", 2, 3);
	}
	
	public void testComplexChildElementFragments() throws Exception {

		IVEXDocument doc;
		IVEXDocumentFragment frag;
		List<IVEXElement> elements;
		IVEXNode[] nodes;
		IVEXElement root;
		IVEXElement x;
		IVEXElement y;
		IVEXElement z;




		// Case 3: complex with child elements
		//        
		// root
		// | |
		// | z |
		// | | | |
		// | | | x | | y | | |
		// * a * b c * d * e * f * g h * i *
		// 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7
		// 3a: |<------frag----->|
		// 3b: |<--------frag----------->|

		doc = new Document(new RootElement("root"));
		doc.insertText(1, "ai");
		doc.insertElement(2, new Element("z"));
		doc.insertText(3, "bcgh");
		doc.insertElement(5, new Element("x"));
		doc.insertText(6, "d");
		doc.insertText(8, "e");
		doc.insertElement(9, new Element("y"));
		doc.insertText(10, "f");

		// 3a:
		// | x | | y |
		// c * d * e * f * g
		// 0 1 2 3 4 5 6 7 8 9
		frag = doc.getFragment(4, 13);
		assertEquals(9, frag.getContent().getLength());

		elements = frag.getElements();
		assertEquals(2, elements.size());
		assertIsElement(elements.get(0), "x", null, 1, 3);
		assertIsElement(elements.get(1), "y", null, 5, 7);

		nodes = frag.getNodes();
		assertEquals(5, nodes.length);
		assertIsText(nodes[0], "c", 0, 1);
		assertIsElement(nodes[1], "x", null, 1, 3);
		assertIsText(nodes[2], "e", 4, 5);
		assertIsElement(nodes[3], "y", null, 5, 7);
		assertIsText(nodes[4], "g", 8, 9);

		// 3b:
		// z
		// | |
		// | | x | | y | |
		// * b c * d * e * f * g h *
		// 0 1 2 3 4 5 6 7 8 9 0 1 2 3
		frag = doc.getFragment(2, 15);
		assertEquals(13, frag.getContent().getLength());

		elements = frag.getElements();
		assertEquals(1, elements.size());
		assertIsElement(elements.get(0), "z", null, 0, 12);

		nodes = frag.getNodes();
		assertEquals(1, nodes.length);
		assertIsElement(nodes[0], "z", null, 0, 12);

		z = elements.get(0);
		nodes = z.getChildNodes();
		assertEquals(5, nodes.length);
		assertIsText(nodes[0], "bc", 1, 3);
		assertIsElement(nodes[1], "x", z, 3, 5);
		assertIsText(nodes[2], "e", 6, 7);
		assertIsElement(nodes[3], "y", z, 7, 9);
		assertIsText(nodes[4], "gh", 10, 12);

		// 3c: remove and re-insert the same frag as in 3a
		frag = doc.getFragment(4, 13);
		doc.delete(4, 13);
		doc.insertFragment(4, frag);

		root = doc.getRootElement();
		assertIsElement(root, "root", null, 0, 16);
		nodes = root.getChildNodes();
		assertEquals(3, nodes.length);
		assertIsText(nodes[0], "a", 1, 2);
		assertIsElement(nodes[1], "z", doc.getRootElement(), 2, 14);
		assertIsText(nodes[2], "i", 15, 16);
		z = (IVEXElement) nodes[1];
		nodes = z.getChildNodes();
		assertEquals(5, nodes.length);
		assertIsText(nodes[0], "bc", 3, 5);
		assertIsElement(nodes[1], "x", z, 5, 7);
		assertIsText(nodes[2], "e", 8, 9);
		assertIsElement(nodes[3], "y", z, 9, 11);
		assertIsText(nodes[4], "gh", 12, 14);
		x = (IVEXElement) nodes[1];
		y = (IVEXElement) nodes[3];
		nodes = x.getChildNodes();
		assertEquals(1, nodes.length);
		assertIsText(nodes[0], "d", 6, 7);
		nodes = y.getChildNodes();
		assertEquals(1, nodes.length);
		assertIsText(nodes[0], "f", 10, 11);

		// 3d: remove and re-insert the same frag as in 3b
		frag = doc.getFragment(2, 15);
		doc.delete(2, 15);
		doc.insertFragment(2, frag);

		root = doc.getRootElement();
		assertIsElement(root, "root", null, 0, 16);
		nodes = root.getChildNodes();
		assertEquals(3, nodes.length);
		assertIsText(nodes[0], "a", 1, 2);
		assertIsElement(nodes[1], "z", doc.getRootElement(), 2, 14);
		assertIsText(nodes[2], "i", 15, 16);
		z = (IVEXElement) nodes[1];
		nodes = z.getChildNodes();
		assertEquals(5, nodes.length);
		assertIsText(nodes[0], "bc", 3, 5);
		assertIsElement(nodes[1], "x", z, 5, 7);
		assertIsText(nodes[2], "e", 8, 9);
		assertIsElement(nodes[3], "y", z, 9, 11);
		assertIsText(nodes[4], "gh", 12, 14);
		x = (IVEXElement) nodes[1];
		y = (IVEXElement) nodes[3];
		nodes = x.getChildNodes();
		assertEquals(1, nodes.length);
		assertIsText(nodes[0], "d", 6, 7);
		nodes = y.getChildNodes();
		assertEquals(1, nodes.length);
		assertIsText(nodes[0], "f", 10, 11);

	}

	public void assertIsElement(IVEXNode node, String name, IVEXElement parent,
			int startOffset, int endOffset) {

		assertTrue(node instanceof Element);
		assertEquals(name, ((IVEXElement) node).getName());
		assertEquals(parent, ((IVEXElement) node).getParent());
		assertEquals(startOffset, node.getStartOffset());
		assertEquals(endOffset, node.getEndOffset());
	}

	public void assertIsText(IVEXNode node, String text, int startOffset,
			int endOffset) {

		assertTrue(node instanceof Text);
		assertEquals(text, node.getText());
		assertEquals(startOffset, node.getStartOffset());
		assertEquals(endOffset, node.getEndOffset());
	}
}
