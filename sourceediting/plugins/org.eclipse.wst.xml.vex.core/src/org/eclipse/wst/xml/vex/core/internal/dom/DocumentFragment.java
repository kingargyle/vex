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
import java.util.ArrayList;
import java.util.List;

import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IContent;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IVEXDocumentFragment;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IVEXElement;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IVEXNode;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IValidator;

/**
 * Represents a fragment of an XML document.
 */
public class DocumentFragment implements Serializable, IVEXDocumentFragment {

	private IContent content;
	private List<IVEXElement> elements;

	/**
	 * Class constructor.
	 * 
	 * @param content
	 *            Content holding the fragment's content.
	 * @param elementArray
	 *            Elements that make up this fragment.
	 */
	public DocumentFragment(IContent content, List<IVEXElement> elementArray) {
		this.content = content;
		this.elements = elementArray;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocumentFragment#getContent()
	 */
	public IContent getContent() {
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
	public List<IVEXElement> getElements() {
		return elements;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocumentFragment#getNodeNames()
	 */
	public List<String> getNodeNames() {

		IVEXNode[] nodes = this.getNodes();
		List<String> names = new ArrayList(nodes.length);
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i] instanceof Text) {
				names.add(IValidator.PCDATA);
			} else {
				names.add(((IVEXElement) nodes[i]).getName());
			}
		}

		return names;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocumentFragment#getNodes()
	 */
	public IVEXNode[] getNodes() {
		return Document.createNodeArray(this.getContent(), 0, this.getContent()
				.getLength(), this.getElements());
	}

	// ======================================================= PRIVATE

	/*
	 * Custom Serialization Methods
	 */

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeUTF(this.content.getString(0, this.content.getLength()));
		out.writeInt(this.elements.size());
		for (int i = 0; i < this.elements.size(); i++) {
			this.writeElement(this.elements.get(i), out);
		}
	}

	private void writeElement(IVEXElement element, ObjectOutputStream out)
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
		List<IVEXElement> children = element.getChildElements();
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
		this.elements = new ArrayList<IVEXElement>(n);
		for (int i = 0; i < n; i++) {
			this.elements.add(readElement(in));
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
