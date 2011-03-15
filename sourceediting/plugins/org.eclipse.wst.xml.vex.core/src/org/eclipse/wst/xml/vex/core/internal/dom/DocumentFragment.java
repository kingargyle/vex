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
package org.eclipse.wst.xml.vex.core.internal.dom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a fragment of an XML document.
 */
public class DocumentFragment implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Mime type representing document fragments: "text/x-vex-document-fragment"
	 * @model
	 */
	public static final String MIME_TYPE = "application/x-vex-document-fragment";

	private Content content;
	private List<Element> elements;

	/**
	 * Class constructor.
	 * 
	 * @param content
	 *            Content holding the fragment's content.
	 * @param elements
	 *            Elements that make up this fragment.
	 */
	public DocumentFragment(Content content, List<Element> elements) {
		this.content = content;
		this.elements = elements;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocumentFragment#getContent()
	 */
	public Content getContent() {
		return this.content;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocumentFragment#getLength()
	 */
	public int getLength() {
		return this.content.getLength();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocumentFragment#getElements()
	 */
	public List<Element> getElements() {
		return elements;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocumentFragment#getNodeNames()
	 */
	public List<String> getNodeNames() {
		List<Node> nodes = getNodes();
		List<String> names = new ArrayList<String>(nodes.size());
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i) instanceof Text) {
				names.add(Validator.PCDATA);
			} else {
				names.add(((Element) nodes.get(i)).getName());
			}
		}

		return names;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocumentFragment#getNodes()
	 */
	public List<Node> getNodes() {
		return Document.createNodeList(getContent(), 0, getContent()
				.getLength(), getNodes(getElements()));
	}
	
	private List<Node> getNodes(List<Element> elements) {
		List<Node> nodes = new ArrayList<Node>();
		for (Node node : elements) {
			if (node.getNodeType().equals("Element")) {
				nodes.add(node);
			}	
		}
		return nodes;
	}

	/*
	 * Custom Serialization Methods
	 */

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeUTF(content.getString(0, content.getLength()));
		out.writeInt(elements.size());
		for (int i = 0; i < elements.size(); i++) {
			this.writeElement(elements.get(i), out);
		}
	}
 
	private void writeElement(Element element, ObjectOutputStream out)
			throws IOException {

		out.writeObject(element.getName());
		out.writeInt(element.getStartOffset());
		out.writeInt(element.getEndOffset());
		List<String> attrNames = element.getAttributeNames();
		out.writeInt(attrNames.size());
		for (int i = 0; i < attrNames.size(); i++) {
			out.writeObject(attrNames.get(i));
			out.writeObject(element.getAttribute(attrNames.get(i)));
		}
		List<Element> children = element.getChildElements();
		out.writeInt(children.size());
		for (int i = 0; i < children.size(); i++) {
			this.writeElement(children.get(i), out);
		}
	}

	private void readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException {

		String s = in.readUTF();
		this.content = new GapContent(s.length());
		content.insertString(0, s);
		int n = in.readInt();
		elements = new ArrayList<Element>(n);
		for (int i = 0; i < n; i++) {
			elements.add(readElement(in));
		}
	}

	private Element readElement(ObjectInputStream in) throws IOException,
			ClassNotFoundException {

		String name = (String) in.readObject();
		int startOffset = in.readInt();
		int endOffset = in.readInt();
		Element element = new Element(name);
		element.setContent(this.content, startOffset, endOffset);

		int attrCount = in.readInt();
		for (int i = 0; i < attrCount; i++) {
			String key = (String) in.readObject();
			String value = (String) in.readObject();
			try {
				element.setAttribute(key, value);
			} catch (DocumentValidationException e) {
				// Should never happen; there ain't no document
				e.printStackTrace();
			}
		}

		int childCount = in.readInt();
		for (int i = 0; i < childCount; i++) {
			Element child = this.readElement(in);
			child.setParent(element);
			element.insertChild(i, child);
		}

		return element;
	}
}
