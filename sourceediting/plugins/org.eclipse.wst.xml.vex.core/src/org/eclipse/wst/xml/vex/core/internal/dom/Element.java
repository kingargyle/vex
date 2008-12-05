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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.wst.xml.core.internal.document.DOMModelImpl;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMDocument;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMText;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Content;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXElementImpl;
import org.eclipse.wst.xml.vex.core.internal.undo.CannotRedoException;
import org.eclipse.wst.xml.vex.core.internal.undo.CannotUndoException;
import org.eclipse.wst.xml.vex.core.internal.undo.IUndoableEdit;

/**
 * <code>Element</code> represents a tag in an XML document. Methods are
 * available for managing the element's attributes and children.
 */
public class Element extends VEXElementImpl implements Cloneable, VEXElement {

	//private String name;
	//private VEXElement parent = null;
	//private List children = new ArrayList();
	private Map attributes = new HashMap();
	//private String namespaceURI = null;
	//private String namespacePrefix = null;

	/**
	 * Class constructor.
	 * 
	 * @param name
	 *            element name
	 */
	public Element(String name) {
		this.name = name;
		childNodes = new BasicEList();
	}
	
	public Element(String name, IDOMDocument document) {
		this.name = name;
		childNodes = new BasicEList();
		setElement(document.createElement(name));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.xml.vex.core.internal.dom.IVEXElement#addChild(org.eclipse
	 * .wst.xml.vex.core.internal.dom.Element)
	 */
	public void addChild(VEXElement child) {
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
			VEXElement element = new Element(this.getName());
			for (Iterator it = this.attributes.keySet().iterator(); it
					.hasNext();) {
				String attrName = (String) it.next();
				element.setAttribute(attrName, (String) this.attributes
						.get(attrName));
			}
			return element;

		} catch (DocumentValidationException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	public String getAttribute(String name) {
		return (String) attributes.get(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.xml.vex.core.internal.dom.IVEXElement#getAttributeNames()
	 */
	public EList<String> getAttributeNames() {
		Collection names = this.attributes.keySet();
		EList attributeNames = new BasicEList(Arrays.asList((String[]) names.toArray(new String[names.size()])));
		return attributeNames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.xml.vex.core.internal.dom.IVEXElement#getChildIterator()
	 */
	public Iterator getChildIterator() {
		return childNodes.iterator();
	}

	/**
	 * 
	 */
	public EList<VEXElement> getChildElements() {
		EList<VEXNode> nodes = getChildNodes();
		Iterator<VEXNode> iter = nodes.iterator();
		EList<VEXElement> elements = new BasicEList();
		while (iter.hasNext()) {
			VEXNode node = iter.next();
			if (node.getNodeType().equals("Element")) {
				elements.add((VEXElement)node);
			}
		}
		return elements;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.xml.vex.core.internal.dom.IVEXElement#getChildNodes()
	 */
	public EList<VEXNode> getChildNodes() {
		return Document.createNodeList(getContent(), getStartOffset() + 1,
				getEndOffset(), childNodes);
	}
 
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXElement#getDocument()
	 */
	public VEXDocument getDocument() {
		VEXElement root = this;
		while (root.getParent() != null) {
			root = root.getParent();
		}
		if (root instanceof RootElement) {
			return root.getDocument();
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXElement#getName()
	 */
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXElement#getParent()
	 */
	public VEXElement getParent() {
		return this.parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXElement#getText()
	 */
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
	public void insertChild(int index, VEXElement child) {
		childNodes.add(index, child);
		Node node = (Node)childNodes.get(index);
		if (node instanceof Element) {
			org.w3c.dom.Element domElement = ((Element) node).getElement();
			if (domElement != null) {
				domElement.insertBefore(child.getElement(), domElement);
			}
		}
		child.setParent(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXElement#isEmpty()
	 */
	public boolean isEmpty() {
		return this.getStartOffset() + 1 == this.getEndOffset();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.xml.vex.core.internal.dom.IVEXElement#removeAttribute
	 * (java.lang.String)
	 */
	public void removeAttribute(String name) throws DocumentValidationException {

		String oldValue = this.getAttribute(name);
		String newValue = null;
		if (oldValue != null) {
			this.attributes.remove(name);
		}
		getElement().removeAttribute(name);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.xml.vex.core.internal.dom.IVEXElement#setAttribute(java
	 * .lang.String, java.lang.String)
	 */
	public void setAttribute(String name, String value)
			throws DocumentValidationException {

		String oldValue = getAttribute(name);
		org.w3c.dom.Element domElement = getElement();
		if (domElement != null) {
			//TODO: Remove the NULL check, or add Unit tests to specifically test for the additional Attributes
			domElement.setAttribute(name, value);
		}

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXElement#toString()
	 */
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

	public void setParent(VEXElement parent) {
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

}
