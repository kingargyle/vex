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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Content;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocumentFragment;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Validator;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXDocumentFragmentImpl;

/**
 * Represents a fragment of an XML document.
 */
public class DocumentFragment extends VEXDocumentFragmentImpl implements Serializable, VEXDocumentFragment {

	/**
	 * Mime type representing document fragments: "text/x-vex-document-fragment"
	 * @model
	 */
	public static final String MIME_TYPE = "application/x-vex-document-fragment";

	//private Content content;
	//private List<VEXElement> elements;

	/**
	 * Class constructor.
	 * 
	 * @param content
	 *            Content holding the fragment's content.
	 * @param elementArray
	 *            Elements that make up this fragment.
	 */
	public DocumentFragment(Content content, EList<VEXElement> elementArray) {
		this.content = content;
		elements = elementArray;
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
	public EList<VEXElement> getElements() {
		return elements;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocumentFragment#getNodeNames()
	 */
	public EList<String> getNodeNames() {

		EList<VEXNode> nodes = getNodes();
		EList<String> names = new BasicEList(nodes.size());
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i) instanceof Text) {
				names.add(Validator.PCDATA);
			} else {
				names.add(((VEXElement) nodes.get(i)).getName());
			}
		}

		return names;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocumentFragment#getNodes()
	 */
	public EList<VEXNode> getNodes() {
		return Document.createNodeList(getContent(), 0, getContent()
				.getLength(), getNodes(getElements()));
//		return Arrays.asList(Document.createNodeArray(getContent(), 0, getContent()
//				.getLength(), getElements()));
	}
	
	private EList<VEXNode> getNodes(EList<VEXElement> elements) {
		EList<VEXNode> nodes = new BasicEList<VEXNode>();
		Iterator iter = elements.iterator();
		while (iter.hasNext()) {
			VEXNode node = (VEXNode) iter.next();
			if (node.getNodeType().equals("Element")) {
				nodes.add(node);
			}
		}
		return nodes;
	}

	// ======================================================= PRIVATE

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
 
	private void writeElement(VEXElement element, ObjectOutputStream out)
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
		List<VEXElement> children = element.getChildElements();
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
		elements = new BasicEList<VEXElement>(n);
		for (int i = 0; i < n; i++) {
			elements.add(readElement(in));
		}
	}

	private VEXElement readElement(ObjectInputStream in) throws IOException,
			ClassNotFoundException {

		String name = (String) in.readObject();
		int startOffset = in.readInt();
		int endOffset = in.readInt();
		VEXElement element = new Element(name);
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
			VEXElement child = this.readElement(in);
			child.setParent(element);
			element.insertChild(i, child);
		}

		return element;
	}
}
