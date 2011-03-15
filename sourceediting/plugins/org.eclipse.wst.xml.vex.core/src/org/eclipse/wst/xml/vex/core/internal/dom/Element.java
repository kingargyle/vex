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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.wst.xml.vex.core.internal.undo.CannotRedoException;
import org.eclipse.wst.xml.vex.core.internal.undo.CannotUndoException;
import org.eclipse.wst.xml.vex.core.internal.undo.IUndoableEdit;

/**
 * <code>Element</code> represents a tag in an XML document. Methods are
 * available for managing the element's attributes and children.
 */
public class Element extends Node implements Cloneable {

	private static final String XML_BASE_ATTRIBUTE = "xml:base";
	private String name;
	private Element parent = null;
	private List<Node> childNodes = new ArrayList<Node>();
	private Map<String,String> attributes = new HashMap<String,String>();
	// private String namespaceURI = null;
	// private String namespacePrefix = null;

	/**
	 * Class constructor.
	 * 
	 * @param name
	 *            element name
	 */
	public Element(String name) {
		this.name = name;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.xml.vex.core.internal.dom.IVEXElement#addChild(org.eclipse
	 * .wst.xml.vex.core.internal.dom.Element)
	 */
	public void addChild(Element child) {
		childNodes.add(child);
		child.setParent(this);
		//getElement().appendChild(child.getElement());
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	public Object clone() {
		try {
			Element element = new Element(this.getName());
			//add the attributes to the element instance to be cloned
			for (Map.Entry<String, String> attr : this.attributes.entrySet()) {
				element.setAttribute(attr.getKey(), attr.getValue());
			}
			return element;

		} catch (DocumentValidationException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public String getAttribute(String name) {
		return (String) attributes.get(name);
	}

	public List<String> getAttributeNames() {
		final Collection<String> names = this.attributes.keySet();
		return Arrays.<String> asList(names.toArray(new String[names.size()]));
	}

	public Iterator<Node> getChildIterator() {
		return childNodes.iterator();
	}

	public List<Element> getChildElements() {
		List<Node> nodes = getChildNodes();
		Iterator<Node> iter = nodes.iterator();
		List<Element> elements = new ArrayList<Element>();
		while (iter.hasNext()) {
			Node node = iter.next();
			if (node.getNodeType().equals("Element")) {
				elements.add((Element)node);
			}
		}
		return elements;
	}

	public List<Node> getChildNodes() {
		return Document.createNodeList(getContent(), getStartOffset() + 1,
				getEndOffset(), childNodes);
	}
 
	public Document getDocument() {
		Element root = this;
		while (root.getParent() != null) {
			root = root.getParent();
		}
		if (root instanceof RootElement) {
			return root.getDocument();
		} else {
			return null;
		}
	}

	public String getName() {
		return this.name;
	}

	public Element getParent() {
		return this.parent;
	}

	public String getText() {
		String s = super.getText();
		StringBuffer sb = new StringBuffer(s.length());
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c != 0) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * Inserts the given element as a child at the given child index. Sets the
	 * parent attribute of the given element to this element.
	 */
	public void insertChild(int index, Element child) {
		childNodes.add(index, child);
		child.setParent(this);
	}

	public boolean isEmpty() {
		return this.getStartOffset() + 1 == this.getEndOffset();
	}

	public void removeAttribute(String name) throws DocumentValidationException {

		String oldValue = this.getAttribute(name);
		String newValue = null;
		if (oldValue != null) {
			this.attributes.remove(name);
		}
		Document doc = (Document)this.getDocument();
		if (doc != null) { // doc may be null, e.g. when we're cloning an
			// element
			// to produce a document fragment

			IUndoableEdit edit = doc.isUndoEnabled() ? new AttributeChangeEdit(
					name, oldValue, newValue) : null;

			doc.fireAttributeChanged(new DocumentEvent(doc, this, name,
					oldValue, newValue, edit));
		}
	}

	public void setAttribute(String name, String value)
			throws DocumentValidationException {

		String oldValue = getAttribute(name);

		if (value == null && oldValue == null) {
			return;
		} else if (value == null) {
			this.removeAttribute(name);
		} else if (value.equals(oldValue)) {
			return;
		} else {
			this.attributes.put(name, value);
			Document doc = (Document)this.getDocument();
			if (doc != null) { // doc may be null, e.g. when we're cloning an
				// element
				// to produce a document fragment

				IUndoableEdit edit = doc.isUndoEnabled() ? new AttributeChangeEdit(
						name, oldValue, value)
						: null;

				doc.fireAttributeChanged(new DocumentEvent(doc, this, name,
						oldValue, value, edit));
			}
		}

	}

	public String toString() {

		StringBuffer sb = new StringBuffer();
		sb.append("<");
		sb.append(this.getName());
		List<String> attrs = this.getAttributeNames();

		for (int i = 0; i < attrs.size(); i++) {
			if (i > 0) {
				sb.append(",");
			}
			sb.append(" ");
			sb.append(attrs.get(i));
			sb.append("=\"");
			sb.append(this.getAttribute(attrs.get(i)));
			sb.append("\"");
		}

		sb.append("> (");
		sb.append(this.getStartPosition());
		sb.append(",");
		sb.append(this.getEndPosition());
		sb.append(")");

		return sb.toString();
	}

	// ========================================================= PRIVATE

	private class AttributeChangeEdit implements IUndoableEdit {

		private String name;
		private String oldValue;
		private String newValue;

		public AttributeChangeEdit(String name, String oldValue, String newValue) {
			this.name = name;
			this.oldValue = oldValue;
			this.newValue = newValue;
		}

		public boolean combine(IUndoableEdit edit) {
			return false;
		}

		public void undo() throws CannotUndoException {
			Document doc = (Document)getDocument();
			try {
				doc.setUndoEnabled(false);
				setAttribute(name, oldValue);
			} catch (DocumentValidationException ex) {
				throw new CannotUndoException();
			} finally {
				doc.setUndoEnabled(true);
			}
		}

		public void redo() throws CannotRedoException {
			Document doc = (Document)getDocument();
			try {
				doc.setUndoEnabled(false);
				setAttribute(name, newValue);
			} catch (DocumentValidationException ex) {
				throw new CannotUndoException();
			} finally {
				doc.setUndoEnabled(true);
			}
		}
	}

	public void setParent(Element parent) {
		this.parent = parent;
	}

	public String getNamespacePrefix() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNamespaceURI() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setNamespace(String prefix, String namespaceURI) {
		// TODO Auto-generated method stub
		
	}

	public void setNamespacePrefix(String prefix) {
		// TODO Auto-generated method stub
		
	}

	public void setNamespaceURI(String namespaceURI) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getNodeType() {
		return "Element";
	}
	
	/**
	 * Sets the content of the node
	 * 
	 * @param content
	 *            Content object holding the node's content
	 * @param startOffset
	 *            offset at which the node's content starts
	 * @param endOffset
	 *            offset at which the node's content ends
	 */
	public void setContent(Content content, int startOffset, int endOffset) {
		super.setContent(content, startOffset, endOffset);
	}	

	@Override
	public String getBaseURI() {
		final String baseAttributeValue = getAttribute(XML_BASE_ATTRIBUTE);
		if (baseAttributeValue != null)
			return baseAttributeValue;
		if (getParent() != null)
			return getParent().getBaseURI();
		if (getDocument() != null)
			return getDocument().getBaseURI();
		return null;
	}
}
