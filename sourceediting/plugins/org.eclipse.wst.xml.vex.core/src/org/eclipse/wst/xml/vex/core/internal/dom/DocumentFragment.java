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
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.QualifiedName;

/**
 * Represents a fragment of an XML document.
 */
public class DocumentFragment implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Mime type representing document fragments: "text/x-vex-document-fragment"
	 * 
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
	public DocumentFragment(final Content content, final List<Element> elements) {
		this.content = content;
		this.elements = elements;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocumentFragment#getContent
	 * ()
	 */
	public Content getContent() {
		return content;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocumentFragment#getLength
	 * ()
	 */
	public int getLength() {
		return content.getLength();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocumentFragment#getElements
	 * ()
	 */
	public List<Element> getElements() {
		return elements;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocumentFragment#getNodeNames
	 * ()
	 */
	public List<QualifiedName> getNodeNames() {
		final List<Node> nodes = getNodes();
		final List<QualifiedName> names = new ArrayList<QualifiedName>(nodes.size());
		for (Node node : nodes)
			if (node instanceof Text)
				names.add(Validator.PCDATA);
			else
				names.add(((Element) node).getQualifiedName());

		return names;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocumentFragment#getNodes()
	 */
	public List<Node> getNodes() {
		return Document.createNodeList(getContent(), 0, getContent().getLength(), getNodes(getElements()));
	}

	private List<Node> getNodes(final List<Element> elements) {
		final List<Node> nodes = new ArrayList<Node>();
		for (final Node node : elements)
			if (node.getNodeType().equals("Element"))
				nodes.add(node);
		return nodes;
	}

	/*
	 * Custom Serialization Methods
	 */

	private void writeObject(final ObjectOutputStream out) throws IOException {
		out.writeUTF(content.getString(0, content.getLength()));
		out.writeInt(elements.size());
		for (int i = 0; i < elements.size(); i++)
			writeElement(elements.get(i), out);
	}

	private void writeElement(final Element element, final ObjectOutputStream out) throws IOException {

		out.writeObject(element.getQualifiedName());
		out.writeInt(element.getStartOffset());
		out.writeInt(element.getEndOffset());
		final Collection<Attribute> attributes = element.getAttributes();
		out.writeInt(attributes.size());
		for (final Attribute attribute : attributes) {
			out.writeObject(attribute.getQualifiedName());
			out.writeObject(attribute.getValue());
		}
		final List<Element> children = element.getChildElements();
		out.writeInt(children.size());
		for (int i = 0; i < children.size(); i++)
			writeElement(children.get(i), out);
	}

	private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
		final String s = in.readUTF();
		content = new GapContent(s.length());
		content.insertString(0, s);
		final int n = in.readInt();
		elements = new ArrayList<Element>(n);
		for (int i = 0; i < n; i++)
			elements.add(readElement(in));
	}

	private Element readElement(final ObjectInputStream in) throws IOException, ClassNotFoundException {
		final QualifiedName elementName = createQualifiedName(in.readObject());
		final int startOffset = in.readInt();
		final int endOffset = in.readInt();
		final Element element = new Element(elementName);
		element.setContent(content, startOffset, endOffset);

		final int attrCount = in.readInt();
		for (int i = 0; i < attrCount; i++) {
			final QualifiedName attributeName = createQualifiedName(in.readObject());
			final String value = (String) in.readObject();
			try {
				element.setAttribute(attributeName, value);
			} catch (final DocumentValidationException e) {
				// Should never happen; there ain't no document
				e.printStackTrace();
			}
		}

		final int childCount = in.readInt();
		for (int i = 0; i < childCount; i++) {
			final Element child = readElement(in);
			child.setParent(element);
			element.insertChild(i, child);
		}

		return element;
	}

	private static QualifiedName createQualifiedName(final Object object) {
		final String serializedQualifiedName = object.toString();
		final int localNameStartIndex = serializedQualifiedName.lastIndexOf(':') + 1;
		if (localNameStartIndex == 0)
			return new QualifiedName(null, serializedQualifiedName);
		final String qualifier = serializedQualifiedName.substring(0, localNameStartIndex - 1);
		final String localName = serializedQualifiedName.substring(localNameStartIndex);
		return new QualifiedName(qualifier, localName);
	}
}
