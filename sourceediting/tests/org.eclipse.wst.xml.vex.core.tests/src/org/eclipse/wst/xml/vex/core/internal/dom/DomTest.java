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
package org.eclipse.wst.xml.vex.core.internal.dom;

import java.util.List;

import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.xml.core.internal.XMLCorePlugin;
import org.eclipse.wst.xml.core.internal.document.DOMModelImpl;
import org.eclipse.wst.xml.core.internal.provisional.contenttype.ContentTypeIdForXML;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMDocument;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.eclipse.wst.xml.vex.core.internal.dom.Document;
import org.eclipse.wst.xml.vex.core.internal.dom.Element;
import org.eclipse.wst.xml.vex.core.internal.dom.RootElement;
import org.eclipse.wst.xml.vex.core.internal.dom.Text;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocumentFragment;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode;

import junit.framework.TestCase;

/**
 * Test the <code>org.eclipse.wst.vex.core.internal.dom</code> package.
 */
public class DomTest extends TestCase {
	
	public void testWTPStructuredDocumentRetrieval() throws Exception {
		IDOMModel model = initDOM();
		IDOMDocument domDocument = model.getDocument();
		
		this.assertNotNull("Structured Document NULL.", domDocument.getStructuredDocument());
	}

	private IDOMModel initDOM() {
		IDOMModel model = new DOMModelImpl();
		IModelManager modelManager = StructuredModelManager.getModelManager();
		IStructuredDocument structuredDocument = modelManager.createStructuredDocumentFor(ContentTypeIdForXML.ContentTypeID_XML);
		model.setStructuredDocument(structuredDocument);
		return model;
	}
	
	public void testWTPStructruedDocument() throws Exception {
		IStructuredModel model = null;
		IModelManager modelManager = StructuredModelManager.getModelManager();
		
		IStructuredDocument structuredDocument = modelManager.createStructuredDocumentFor(ContentTypeIdForXML.ContentTypeID_XML);
		assertNotNull("Structured Document NULL.", structuredDocument);
		
	}
	
	public void testWTPDOMRetrieval() throws Exception {
		IDOMModel model = initDOM();
		IDOMDocument domDocument =  model.getDocument();
		
		Element root = new Element("article");
		root.setElement(domDocument.createElement("article"));
		domDocument.appendChild(root.getElement());
		VEXDocument doc = new Document(root);
		
		org.w3c.dom.Element domElement = doc.getRootElement().getElement();
		assertEquals("Incorrect element name.", "article", domElement.getNodeName());
	}
	
	public void testWTPDOMOwnerDocumentElement() throws Exception {
		IDOMModel model = initDOM();
		IDOMDocument domDocument =  model.getDocument();
		
		Element root = new Element("article");
		VEXDocument doc = new Document(root);
		root.setElement(domDocument.createElement(root.getName()));
		domDocument.appendChild(root.getElement());
		doc.setDocument(domDocument);
		
		VEXElement subelement = new Element("b");
		root.addChild(subelement);
		subelement.setElement(domDocument.createElement("b"));
		root.getElement().appendChild(subelement.getElement());
		
		assertEquals("Incorrect WTP Owner Document Element", "article", subelement.getElement().getOwnerDocument().getDocumentElement().getNodeName());
	}

	public void testDom() throws Exception {

		//
		// Document initialisation
		//
		Element root = new Element("article");
		VEXDocument doc = new Document(root);
		List<VEXNode> content;

		// root
		// | |
		// * *

		assertEquals(2, doc.getLength());
		assertEquals(root, doc.getRootElement());
		assertEquals(0, root.getStartOffset());
		assertEquals(1, root.getEndOffset());

		content = root.getChildNodes();
		assertEquals(0, content.size());
		List<VEXElement> children = root.getChildElements();
		assertEquals(0, children.size());

		// root
		// | |
		// * a c *

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

		doc.insertText(1, "ac");
		assertEquals(4, doc.getLength());
		content = root.getChildNodes();
		assertEquals(1, content.size());
		assertIsText(content.get(0), "ac", 1, 3);
		assertEquals(1, content.get(0).getStartPosition().getOffset());
		assertEquals(3, content.get(0).getEndPosition().getOffset());
		assertEquals(0, root.getStartOffset());
		assertEquals(3, root.getEndOffset());

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

		// root
		// | |
		// | z |
		// | | | |
		// * a * * c *
		// 0 1 2 3 4 5 6
		//
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

	public void testFragments() throws Exception {

		VEXDocument doc;
		VEXDocumentFragment frag;
		List<VEXNode> nodes;
		VEXElement root;
		VEXElement x;
		VEXElement y;
		VEXElement z;

		// Case 1: just text
		//
		// root
		// * a b c *
		// 0 1 2 3 4 5
		doc = new Document(new RootElement("root"));
		doc.insertText(1, "abc");

		try {
			frag = doc.getFragment(2, 2);
			fail();
		} catch (IllegalArgumentException ex) {
		}

		try {
			frag = doc.getFragment(-1, 0);
			fail();
		} catch (IllegalArgumentException ex) {
		}

		try {
			frag = doc.getFragment(4, 5);
			fail();
		} catch (IllegalArgumentException ex) {
		}

		frag = doc.getFragment(2, 3);
		assertEquals(1, frag.getContent().getLength());
		assertEquals(0, frag.getElements().size());
		nodes = frag.getNodes();
		assertEquals(1, nodes.size());
		this.assertIsText(nodes.get(0), "b", 0, 1);

		// Case 2: single element, no children
		//        
		// root
		// | |
		// | z |
		// | | | |
		// * a * b * c *
		// 0 1 2 3 4 5 6 7

		// z
		// | |
		// a * b * c
		// 0 1 2 3 4 5

		doc = new Document(new RootElement("root"));
		doc.insertText(1, "ac");
		doc.insertElement(2, new Element("z"));
		doc.insertText(3, "b");

		frag = doc.getFragment(1, 6);
		
		List<VEXElement> elements = frag.getElements();
		assertEquals(1, elements.size());
		this.assertIsElement(elements.get(0), "z", null, 1, 3);
		nodes = frag.getNodes();
		assertEquals(3, nodes.size());
		assertIsText(nodes.get(0), "a", 0, 1);
		assertIsElement(nodes.get(1), "z", null, 1, 3);
		assertIsText(nodes.get(2), "c", 4, 5);
		List<VEXNode> childNodes = elements.get(0).getChildNodes();
		assertEquals(1, childNodes.size());
		assertIsText(childNodes.get(0), "b", 2, 3);

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

        // 3a
        expect_a_bc_d_e_f_gh_i_(doc);

        // 3b: remove and re-insert fragment "c*d*e*f*g"
        frag = doc.getFragment(4, 13);
        doc.delete(4, 13);
        doc.insertFragment(4, frag);
        expect_a_bc_d_e_f_gh_i_(doc);

        // 3c: remove and re-insert fragment "*bc*d*e*f*gh*"
        frag = doc.getFragment(2, 15);
        doc.delete(2, 15);
        doc.insertFragment(2, frag);
        expect_a_bc_d_e_f_gh_i_(doc);
    }

    private void expect_a_bc_d_e_f_gh_i_(VEXDocument doc) {

        VEXElement root = doc.getRootElement();
        assertIsElement(root, "root", null, 0, 16);
        List<VEXNode> childNodes = root.getChildNodes();
        assertEquals(3, childNodes.size());
        assertIsText(childNodes.get(0), "a", 1, 2);
        assertIsElement(childNodes.get(1), "z", doc.getRootElement(), 2, 14);
        assertIsText(childNodes.get(2), "i", 15, 16);

        VEXDocumentFragment frag = doc.getFragment(4, 13);
        // | x | | y |
        // c * d * e * f * g
        // 0 1 2 3 4 5 6 7 8 9
        assertEquals(9, frag.getContent().getLength());

        List<VEXElement> elements = frag.getElements();
        assertEquals(2, elements.size());
        assertIsElement(elements.get(0), "x", null, 1, 3);
        assertIsElement(elements.get(1), "y", null, 5, 7);

        List<VEXNode> nodes = frag.getNodes();
        assertEquals(5, nodes.size());
        assertIsText(nodes.get(0), "c", 0, 1);
        assertIsElement(nodes.get(1), "x", null, 1, 3);
        assertIsText(nodes.get(2), "e", 4, 5);
        assertIsElement(nodes.get(3), "y", null, 5, 7);
        assertIsText(nodes.get(4), "g", 8, 9);

        frag = doc.getFragment(2, 15);
        // z
        // | |
        // | | x | | y | |
        // * b c * d * e * f * g h *
        // 0 1 2 3 4 5 6 7 8 9 0 1 2 3
        assertEquals(13, frag.getContent().getLength());
        elements = frag.getElements();
        VEXElement z = elements.get(0);
        childNodes = z.getChildNodes();

        assertEquals(5, childNodes.size());
        assertIsText(childNodes.get(0), "bc", 1, 3);
        assertIsElement(childNodes.get(1), "x", z, 3, 5);
        assertIsText(childNodes.get(2), "e", 6, 7);
        assertIsElement(childNodes.get(3), "y", z, 7, 9);
        assertIsText(childNodes.get(4), "gh", 10, 12);

        VEXElement x = (VEXElement) childNodes.get(1);
        VEXElement y = (VEXElement) childNodes.get(3);
        childNodes = x.getChildNodes();

        nodes = frag.getNodes();
        assertEquals(1, nodes.size());
        assertIsText(childNodes.get(0), "d", 4, 5);
        childNodes = y.getChildNodes();
        assertEquals(1, nodes.size());
        assertIsText(childNodes.get(0), "f", 8, 9);
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
