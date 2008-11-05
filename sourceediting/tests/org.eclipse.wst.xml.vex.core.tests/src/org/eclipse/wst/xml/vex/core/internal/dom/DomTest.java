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
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.VEXDocument;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.VEXDocumentFragment;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.VEXElement;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.VEXNode;

import junit.framework.TestCase;

/**
 * Test the <code>org.eclipse.wst.vex.core.internal.dom</code> package.
 */
@SuppressWarnings("restriction")

public class DomTest extends TestCase {
	
	public void testRootElement() throws Exception {
		RootElement root = new RootElement("article");
		VEXDocument doc = new Document(root);
		
		assertEquals(2, doc.getLength());
		assertEquals(root, doc.getRootElement());
		assertEquals(0, root.getStartOffset());
		assertEquals(1, root.getEndOffset());		
	}
	
	public void testRootElementNoChildren() throws Exception {
		RootElement root = new RootElement("article");
		VEXDocument doc = new Document(root);
		List<VEXNode> content;
		List<VEXElement> children;
		
		content = root.getChildNodes();
		assertEquals(0, content.size());
		children = root.getChildElements();
		assertEquals(0, children.size());
	}
	
	public void testIllegaArgumentException() throws Exception {
		RootElement root = new RootElement("article");
		VEXDocument doc = new Document(root);
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
		VEXDocument doc = new Document(root);
		List<VEXNode> content;

		doc.insertText(1, "ac");
		assertEquals(4, doc.getLength());
		content = root.getChildNodes();
		assertEquals(1, content.size());
		
		assertIsText(content.get(0), "ac", 1, 3);
		
		assertEquals(1, content.get(0).getStartPosition().getOffset());
		assertEquals(3, content.get(0).getEndPosition().getOffset());
		assertEquals(0, root.getStartOffset());
		assertEquals(3, root.getEndOffset());
		
	}
	
	public void testInsertAtIllegalOffset() {
		RootElement root = new RootElement("article");
		VEXDocument doc = new Document(root);

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
		VEXDocument doc = new Document(root);
		List<VEXNode> content;
		doc.insertText(1, "ac");
		Element element = new Element("b");
		doc.insertElement(2, element);
		assertEquals(root, element.getParent());
		assertEquals(6, doc.getLength());

		Element element2 = new Element("x");
		doc.insertElement(2, element2);

		content = root.getChildNodes();
		assertEquals(4, content.size());
		assertIsText(content.get(0), "a", 1, 2);
		assertIsElement(content.get(1), "x", root, 2, 3);
		assertIsElement(content.get(2), "b", root, 4, 5);
		assertIsText(content.get(3), "c", 6, 7);
	}
	
	public void testTextFragment22() {
		VEXDocument doc;
		VEXDocumentFragment frag;
		doc = new Document(new RootElement("root"));
		doc.insertText(1, "abc");
		
		try {
			frag = doc.getFragment(2, 2);
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException ex) {
		}

		
	}
	
	public void testTextFragmentFailure() {
		VEXDocument doc;
		VEXDocumentFragment frag;
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
		VEXDocument doc = new Document(new RootElement("root"));
		doc.insertText(1, "abc");

		VEXDocumentFragment frag = doc.getFragment(2, 3);
		assertEquals(1, frag.getContent().getLength());
		assertEquals(0, frag.getElements().size());

		VEXNode[] nodes = frag.getNodes();
		assertEquals(1, nodes.length);
		assertIsText(nodes[0], "b", 0, 1);
	}
	
	public void testgetFragment2() throws Exception {
		
		VEXDocument doc = new Document(new RootElement("root"));
		doc.insertText(1, "ac");
		doc.insertElement(2, new Element("z"));
		doc.insertText(3, "b");

		VEXDocumentFragment frag = doc.getFragment(1, 6);
		
		List<VEXElement> elements = frag.getElements();
		assertEquals(1, elements.size());
		this.assertIsElement(elements.get(0), "z", null, 1, 3);
		
		VEXNode[] nodes = frag.getNodes();
		assertEquals(3, nodes.length);
		assertIsText(nodes[0], "a", 0, 1);
		assertIsElement(nodes[1], "z", null, 1, 3);
		assertIsText(nodes[2], "c", 4, 5);
		List<VEXNode>nodes2 = elements.get(0).getChildNodes();
		assertEquals(1, nodes2.size());
		assertIsText(nodes2.get(0), "b", 2, 3);
	}
	
	public void testComplexChildElementFragments() throws Exception {

		VEXDocument doc;
		VEXDocumentFragment frag;
		List<VEXElement> elements;
		VEXNode[] nodes;
		VEXElement root;
		VEXElement x;
		VEXElement y;
		VEXElement z;




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
		List<VEXNode>nodes2 = z.getChildNodes();
		assertEquals(5, nodes2.size());
		assertIsText(nodes2.get(0), "bc", 1, 3);
		assertIsElement(nodes2.get(1), "x", z, 3, 5);
		assertIsText(nodes2.get(2), "e", 6, 7);
		assertIsElement(nodes2.get(3), "y", z, 7, 9);
		assertIsText(nodes2.get(4), "gh", 10, 12);

		// 3c: remove and re-insert the same frag as in 3a
		frag = doc.getFragment(4, 13);
		doc.delete(4, 13);
		doc.insertFragment(4, frag);

		root = doc.getRootElement();
		assertIsElement(root, "root", null, 0, 16);
		nodes2 = root.getChildNodes();
		assertEquals(3, nodes2.size());
		assertIsText(nodes2.get(0), "a", 1, 2);
		assertIsElement(nodes2.get(1), "z", doc.getRootElement(), 2, 14);
		assertIsText(nodes2.get(2), "i", 15, 16);
		z = (VEXElement) nodes2.get(1);
		nodes2 = z.getChildNodes();
		assertEquals(5, nodes2.size());
		assertIsText(nodes2.get(0), "bc", 3, 5);
		assertIsElement(nodes2.get(1), "x", z, 5, 7);
		assertIsText(nodes2.get(2), "e", 8, 9);
		assertIsElement(nodes2.get(3), "y", z, 9, 11);
		assertIsText(nodes2.get(4), "gh", 12, 14);
		x = (VEXElement) nodes2.get(1);
		y = (VEXElement) nodes2.get(3);
		nodes2 = x.getChildNodes();
		assertEquals(1, nodes2.size());
		assertIsText(nodes2.get(0), "d", 6, 7);
		nodes2 = y.getChildNodes();
		assertEquals(1, nodes2.size());
		assertIsText(nodes2.get(0), "f", 10, 11);

		// 3d: remove and re-insert the same frag as in 3b
		frag = doc.getFragment(2, 15);
		doc.delete(2, 15);
		doc.insertFragment(2, frag);

		root = doc.getRootElement();
		assertIsElement(root, "root", null, 0, 16);
		nodes2 = root.getChildNodes();
		assertEquals(3, nodes2.size());
		assertIsText(nodes2.get(0), "a", 1, 2);
		assertIsElement(nodes2.get(1), "z", doc.getRootElement(), 2, 14);
		assertIsText(nodes2.get(2), "i", 15, 16);
		z = (VEXElement) nodes2.get(1);
		nodes2 = z.getChildNodes();
		assertEquals(5, nodes2.size());
		assertIsText(nodes2.get(0), "bc", 3, 5);
		assertIsElement(nodes2.get(1), "x", z, 5, 7);
		assertIsText(nodes2.get(2), "e", 8, 9);
		assertIsElement(nodes2.get(3), "y", z, 9, 11);
		assertIsText(nodes2.get(4), "gh", 12, 14);
		x = (VEXElement) nodes2.get(1);
		y = (VEXElement) nodes2.get(3);
		nodes2 = x.getChildNodes();
		assertEquals(1, nodes2.size());
		assertIsText(nodes2.get(0), "d", 6, 7);
		nodes2 = y.getChildNodes();
		assertEquals(1, nodes2.size());
		assertIsText(nodes2.get(0), "f", 10, 11);

	}

	public void assertIsElement(VEXNode node, String name, VEXElement parent,
			int startOffset, int endOffset) {

		assertTrue(node instanceof Element);
		assertEquals(name, ((VEXElement) node).getName());
		assertEquals(parent, ((VEXElement) node).getParent());
		assertEquals(startOffset, node.getStartOffset());
		assertEquals(endOffset, node.getEndOffset());
	}

	public void assertIsText(VEXNode node, String text, int startOffset,
			int endOffset) {

		assertTrue(node instanceof Text);
		assertEquals(text, node.getText());
		assertEquals(startOffset, node.getStartOffset());
		assertEquals(endOffset, node.getEndOffset());
	}
}
