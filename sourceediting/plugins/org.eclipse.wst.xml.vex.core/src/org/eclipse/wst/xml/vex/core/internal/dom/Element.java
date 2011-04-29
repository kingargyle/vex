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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.wst.xml.vex.core.internal.core.QualifiedNameComparator;
import org.eclipse.wst.xml.vex.core.internal.undo.CannotRedoException;
import org.eclipse.wst.xml.vex.core.internal.undo.CannotUndoException;
import org.eclipse.wst.xml.vex.core.internal.undo.IUndoableEdit;

/**
 * Represents a tag in an XML document. Methods are
 * available for managing the element's attributes and children.
 */
public class Element extends Node implements Cloneable {

	private static final QualifiedName XML_BASE_ATTRIBUTE = new QualifiedName(Namespace.XML_NAMESPACE_URI, "base");
	
	private final QualifiedName name;
	
	private Element parent = null;
	private List<Node> childNodes = new ArrayList<Node>();
	private Map<QualifiedName, Attribute> attributes = new HashMap<QualifiedName, Attribute>();
	private Map<String, String> namespaceDeclarations = new HashMap<String, String>();

	public Element(final String localName) {
		this(new QualifiedName(null, localName));
	}
	
	public Element(final QualifiedName qualifiedName) {
		this.name = qualifiedName;
	}

	public void addChild(Element child) {
		childNodes.add(child);
		child.setParent(this);
	}

	public Object clone() {
		try {
			final Element element = new Element(getQualifiedName());
			//add the attributes to the element instance to be cloned
			for (Map.Entry<QualifiedName, Attribute> attr : this.attributes.entrySet())
				element.setAttribute(attr.getKey(), attr.getValue().getValue());
			return element;
		} catch (DocumentValidationException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public Attribute getAttribute(String localName) {
		return getAttribute(qualify(localName));
	}
	
	public Attribute getAttribute(QualifiedName name) {
		return attributes.get(name);
	}
	
	public String getAttributeValue(String localName) {
		return getAttributeValue(qualify(localName));
	}

	public String getAttributeValue(QualifiedName name) {
		final Attribute attribute = getAttribute(name);
		if (attribute == null || "".equals(attribute.getValue().trim()))
			return null;
		return attribute.getValue();
	}
	
	public void removeAttribute(String localName) throws DocumentValidationException {
		removeAttribute(qualify(localName));
	}
	
	public void removeAttribute(QualifiedName name) throws DocumentValidationException {
		final Attribute attribute = this.getAttribute(name);
		if (attribute == null)
			return;
		final String oldValue = attribute.getValue();
		final String newValue = null;
		if (oldValue != null)
			this.attributes.remove(name);

		final Document document = getDocument();
		if (document == null) // document may be null, e.g. when we're cloning an element to produce a document fragment
			return;

		final IUndoableEdit edit = document.isUndoEnabled() ? new AttributeChangeEdit(name, oldValue, newValue) : null;
		document.fireAttributeChanged(new DocumentEvent(document, this, name, oldValue, newValue, edit));
	}

	public void setAttribute(String name, String value) throws DocumentValidationException {
		setAttribute(qualify(name), value);
	}
	
	private QualifiedName qualify(String localName) {
		return new QualifiedName(name.getQualifier(), localName);
	}
	
	public void setAttribute(QualifiedName name, String value) throws DocumentValidationException {
		final Attribute oldAttribute = attributes.get(name);
		final String oldValue = oldAttribute != null ? oldAttribute.getValue() : null;
		
		if (value == null && oldValue == null)
			return;
		
		if (value == null) {
			this.removeAttribute(name);
		} else {
			if (value.equals(oldValue)) {
				return;
			} else {
				final Attribute newAttribute = new Attribute(this, name, value);
				this.attributes.put(name, newAttribute);

				final Document document = getDocument();
				if (document == null) // doc may be null, e.g. when we're cloning an element to produce a document fragment
					return;

				final IUndoableEdit edit = document.isUndoEnabled() ? new AttributeChangeEdit(name, oldValue, value) : null;
				document.fireAttributeChanged(new DocumentEvent(document, this, name, oldValue, value, edit));
			}
		}
	}

	public Collection<Attribute> getAttributes() {
		final ArrayList<Attribute> result = new ArrayList<Attribute>(attributes.values());
		Collections.sort(result);
		return Collections.unmodifiableCollection(result);
	}
	
	public List<QualifiedName> getAttributeNames() {
		ArrayList<QualifiedName> result = new ArrayList<QualifiedName>();
		for (Attribute attribute : attributes.values()) {
			result.add(attribute.getQualifiedName());
		}
		Collections.sort(result, new QualifiedNameComparator());
		return result;
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
				elements.add((Element) node);
			}
		}
		return elements;
	}

	public List<Node> getChildNodes() {
		return Document.createNodeList(getContent(), getStartOffset() + 1, getEndOffset(), childNodes);
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

	public String getLocalName() {
		return name.getLocalName();
	}
	
	public QualifiedName getQualifiedName() {
		return name;
	}
	
	public String getPrefix() {
		return getNamespacePrefix(name.getQualifier());
	}
	
	public String getPrefixedName() {
		String prefix = getPrefix();
		if (prefix == null)
			return getLocalName();
		return prefix + ":" + getLocalName();
	}

	public Element getParent() {
		return this.parent;
	}

	public String getText() {
		final String s = super.getText();
		final StringBuilder sb = new StringBuilder(s.length());
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

	public String toString() {

		StringBuffer sb = new StringBuffer();
		sb.append("<");
		sb.append(this.getPrefixedName().toString());
		
		for (Attribute attribute : getAttributes()) {
			sb.append(" ");
			sb.append(attribute.getPrefixedName());
			sb.append("=\"");
			sb.append(attribute.getValue());
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

		private QualifiedName name;
		private String oldValue;
		private String newValue;

		public AttributeChangeEdit(QualifiedName name, String oldValue, String newValue) {
			this.name = name;
			this.oldValue = oldValue;
			this.newValue = newValue;
		}

		public boolean combine(IUndoableEdit edit) {
			return false;
		}

		public void undo() throws CannotUndoException {
			Document doc = (Document) getDocument();
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
			Document doc = (Document) getDocument();
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

	public String getNamespaceURI(final String namespacePrefix) {
		if (namespaceDeclarations.containsKey(namespacePrefix))
			return namespaceDeclarations.get(namespacePrefix);
		if (parent != null)
			return parent.getNamespaceURI(namespacePrefix);
		return null;
	}
	
	public String getDefaultNamespaceURI() {
		return getNamespaceURI(null);
	}
	
	public String getDeclaredDefaultNamespaceURI() {
		return namespaceDeclarations.get(null);
	}

	public String getNamespacePrefix(final String namespaceURI) {
		if (namespaceURI == null)
			return null;
		for (Entry<String, String> entry: namespaceDeclarations.entrySet())
			if (entry.getValue().equals(namespaceURI))
				return entry.getKey();
		if (parent != null) {
			final String parentPrefix = parent.getNamespacePrefix(namespaceURI);
			if (!namespaceDeclarations.containsKey(parentPrefix))
				return parentPrefix;
		}
		return null;
	}
	
	public Collection<String> getDeclaredNamespacePrefixes() {
		final ArrayList<String> result = new ArrayList<String>();
		for (final String prefix : namespaceDeclarations.keySet()) {
			if (prefix != null)
				result.add(prefix);
		}
		Collections.sort(result);
		return result;
	}
	
	public void declareNamespace(final String namespacePrefix, final String namespaceURI) {
		if (namespaceURI == null || "".equals(namespaceURI.trim()))
			return;
		namespaceDeclarations.put(namespacePrefix, namespaceURI);
	}
	
	public void removeNamespace(String namespacePrefix) {
		namespaceDeclarations.remove(namespacePrefix);
	}
	
	public void declareDefaultNamespace(final String namespaceURI) {
		declareNamespace(null, namespaceURI);
	}
	
	public void removeDefaultNamespace() {
		removeNamespace(null);
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
		final Attribute baseAttribute = getAttribute(XML_BASE_ATTRIBUTE);
		if (baseAttribute != null)
			return baseAttribute.getValue();
		if (getParent() != null)
			return getParent().getBaseURI();
		if (getDocument() != null)
			return getDocument().getBaseURI();
		return null;
	}
}
