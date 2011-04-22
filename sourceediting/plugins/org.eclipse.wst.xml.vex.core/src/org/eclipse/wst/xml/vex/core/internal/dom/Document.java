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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.wst.xml.vex.core.internal.core.ListenerList;
import org.eclipse.wst.xml.vex.core.internal.undo.CannotRedoException;
import org.eclipse.wst.xml.vex.core.internal.undo.CannotUndoException;
import org.eclipse.wst.xml.vex.core.internal.undo.IUndoableEdit;

/**
 * Represents an XML document.
 * 
 */
public class Document {

	private final Content content;
	private final RootElement rootElement;
	private final ListenerList<DocumentListener, DocumentEvent> listeners = new ListenerList<DocumentListener, DocumentEvent>(DocumentListener.class);
	private boolean undoEnabled = true;

	private String publicID;
	protected String systemID;
	private String documentURI;

	private String encoding;
	private Validator validator;

	/**
	 * Class constructor.
	 * 
	 * @param rootElement
	 *            root element of the document. The document property of this
	 *            RootElement is set by this constructor.
	 * 
	 */
	public Document(final RootElement rootElement) {
		content = new GapContent(100);
		content.insertString(0, "\0\0");

		this.rootElement = rootElement;
		rootElement.setDocument(this);
		rootElement.setContent(content, 0, 1);
	}

	/**
	 * Class constructor. This constructor is used by the document builder and
	 * assumes that the content and root element have bee properly set up.
	 * 
	 * @param content
	 *            Content object used to store the document's content.
	 * @param rootElement
	 *            RootElement of the document.
	 * 
	 */
	public Document(final Content content, final RootElement rootElement) {
		this.content = content;
		this.rootElement = rootElement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocument#addDocumentListener
	 * (org.eclipse.wst.xml.vex.core.internal.dom.DocumentListener)
	 */
	public void addDocumentListener(final DocumentListener listener) {
		listeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocument#canInsertFragment
	 * (int, org.eclipse.wst.xml.vex.core.internal.dom.DocumentFragment)
	 */
	public boolean canInsertFragment(final int offset, final DocumentFragment fragment) {
		if (validator == null)
			return true;

		final Element element = getElementAt(offset);
		final List<QualifiedName> seq1 = getNodeNames(element.getStartOffset() + 1, offset);
		final List<QualifiedName> seq2 = fragment.getNodeNames();
		final List<QualifiedName> seq3 = getNodeNames(offset, element.getEndOffset());

		return validator.isValidSequence(element.getQualifiedName(), seq1, seq2, seq3, true);
	}

	public boolean canInsertText(final int offset) {
		if (validator == null)
			return true;

		final Element element = getElementAt(offset);
		final List<QualifiedName> seq1 = getNodeNames(element.getStartOffset() + 1, offset);
		final List<QualifiedName> seq2 = Collections.singletonList(Validator.PCDATA);
		final List<QualifiedName> seq3 = getNodeNames(offset, element.getEndOffset());

		return validator.isValidSequence(element.getQualifiedName(), seq1, seq2, seq3, true);
	}

	public Position createPosition(final int offset) {
		return content.createPosition(offset);
	}

	public void delete(final int startOffset, final int endOffset) throws DocumentValidationException {

		final Element e1 = getElementAt(startOffset);
		final Element e2 = getElementAt(endOffset);
		if (e1 != e2)
			throw new IllegalArgumentException("Deletion from " + startOffset + " to " + endOffset + " is unbalanced");

		final Validator validator = getValidator();
		if (validator != null) {
			final List<QualifiedName> seq1 = getNodeNames(e1.getStartOffset() + 1, startOffset);
			final List<QualifiedName> seq2 = getNodeNames(endOffset, e1.getEndOffset());

			if (!validator.isValidSequence(e1.getQualifiedName(), seq1, seq2, null, true))
				throw new DocumentValidationException("Unable to delete from " + startOffset + " to " + endOffset);
		}

		// Grab the fragment for the undoable edit while it's still here
		final DocumentFragment frag = getFragment(startOffset, endOffset);

		fireBeforeContentDeleted(new DocumentEvent(this, e1, startOffset, endOffset - startOffset, null));

		Iterator<Node> iter = e1.getChildNodes().iterator();
		if (e1 instanceof Element)
			iter = (e1).getChildIterator();
		while (iter.hasNext()) {
			final Node child = iter.next();
			if (startOffset <= child.getStartOffset() && child.getEndOffset() < endOffset)
				iter.remove();
		}

		content.remove(startOffset, endOffset - startOffset);

		final IUndoableEdit edit = undoEnabled ? new DeleteEdit(startOffset, endOffset, frag) : null;

		fireContentDeleted(new DocumentEvent(this, e1, startOffset, endOffset - startOffset, edit));
	}

	public Element findCommonElement(final int offset1, final int offset2) {
		Element element = rootElement;
		for (;;) {
			boolean tryAgain = false;
			final List<Element> children = element.getChildElements();
			for (int i = 0; i < children.size(); i++)
				if (offset1 > children.get(i).getStartOffset() && offset2 > children.get(i).getStartOffset() && offset1 <= children.get(i).getEndOffset()
						&& offset2 <= children.get(i).getEndOffset()) {

					element = children.get(i);
					tryAgain = true;
					break;
				}
			if (!tryAgain)
				break;
		}
		return element;
	}

	public char getCharacterAt(final int offset) {
		return content.getString(offset, 1).charAt(0);
	}

	public Element getElementAt(final int offset) {
		if (offset < 1 || offset >= getLength())
			throw new IllegalArgumentException("Illegal offset: " + offset + ". Must be between 1 and n-1");
		Element element = rootElement;
		for (;;) {
			boolean tryAgain = false;
			final List<Element> children = element.getChildElements();
			for (int i = 0; i < children.size(); i++) {
				final Element child = children.get(i);
				if (offset <= child.getStartOffset())
					return element;
				else if (offset <= child.getEndOffset()) {
					element = child;
					tryAgain = true;
					break;
				}
			}
			if (!tryAgain)
				break;
		}
		return element;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(final String encoding) {
		this.encoding = encoding;
	}

	public DocumentFragment getFragment(final int startOffset, final int endOffset) {

		assertOffset(startOffset, 0, content.getLength());
		assertOffset(endOffset, 0, content.getLength());

		if (endOffset <= startOffset)
			throw new IllegalArgumentException("Invalid range (" + startOffset + ", " + endOffset + ")");

		final Element e1 = getElementAt(startOffset);
		final Element e2 = getElementAt(endOffset);
		if (e1 != e2)
			throw new IllegalArgumentException("Fragment from " + startOffset + " to " + endOffset + " is unbalanced");

		final List<Element> children = e1.getChildElements();

		final Content newContent = new GapContent(endOffset - startOffset);
		final String s = content.getString(startOffset, endOffset - startOffset);
		newContent.insertString(0, s);
		final List<Element> newChildren = new ArrayList<Element>();
		for (int i = 0; i < children.size(); i++) {
			final Element child = children.get(i);
			if (child.getEndOffset() <= startOffset)
				continue;
			else if (child.getStartOffset() >= endOffset)
				break;
			else
				newChildren.add(cloneElement(child, newContent, -startOffset, null));
		}

		return new DocumentFragment(newContent, newChildren);
	}

	public int getLength() {
		return content.getLength();
	}

	public List<QualifiedName> getNodeNames(final int startOffset, final int endOffset) {

		final List<Node> nodes = getNodes(startOffset, endOffset);
		final List<QualifiedName> names = new ArrayList<QualifiedName>(nodes.size());

		for (int i = 0; i < nodes.size(); i++) {
			final Node node = nodes.get(i);
			if (node instanceof Element)
				names.add(((Element) node).getQualifiedName());
			else
				names.add(Validator.PCDATA);
		}

		return names;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocument#getNodes(int,
	 * int)
	 */
	public List<Node> getNodes(final int startOffset, final int endOffset) {

		final Element element = getElementAt(startOffset);
		if (element != getElementAt(endOffset))
			throw new IllegalArgumentException("Offsets are unbalanced: " + startOffset + " is in " + element.getName() + ", " + endOffset + " is in "
					+ getElementAt(endOffset).getName());

		final List<Node> list = new ArrayList<Node>();
		final List<Node> nodes = element.getChildNodes();
		for (int i = 0; i < nodes.size(); i++) {
			final Node node = nodes.get(i);
			if (node.getEndOffset() <= startOffset)
				continue;
			else if (node.getStartOffset() >= endOffset)
				break;
			else if (node instanceof Element)
				list.add(node);
			else {
				final Text text = (Text) node;
				if (text.getStartOffset() < startOffset)
					text.setContent(text.getContent(), startOffset, text.getEndOffset());
				else if (text.getEndOffset() > endOffset)
					text.setContent(text.getContent(), text.getStartOffset(), endOffset);
				list.add(text);
			}
		}

		return list;
	}

	/**
	 * Creates an array of nodes for a given run of content. The returned array
	 * includes the given child elements and <code>Text</code> objects where
	 * text appears between elements.
	 * 
	 * @param content
	 *            Content object containing the content
	 * @param startOffset
	 *            start offset of the run
	 * @param endOffset
	 *            end offset of the run
	 * @param elements
	 *            child elements that are within the run
	 */
	static List<Node> createNodeList(final Content content, final int startOffset, final int endOffset, final List<Node> elements) {

		final List<Node> nodes = new ArrayList<Node>();
		int offset = startOffset;
		for (int i = 0; i < elements.size(); i++) {
			final int start = elements.get(i).getStartOffset();
			if (offset < start)
				nodes.add(new Text(content, offset, start));
			nodes.add(elements.get(i));
			offset = elements.get(i).getEndOffset() + 1;
		}

		if (offset < endOffset)
			nodes.add(new Text(content, offset, endOffset));

		return nodes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocument#getPublicID()
	 */
	public String getPublicID() {
		return publicID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocument#getRawText(int,
	 * int)
	 */
	public String getRawText(final int startOffset, final int endOffset) {
		return content.getString(startOffset, endOffset - startOffset);
	}

	public Element getRootElement() {
		return rootElement;
	}

	public String getSystemID() {
		return systemID;
	}

	public String getText(final int startOffset, final int endOffset) {
		final String raw = content.getString(startOffset, endOffset - startOffset);
		final StringBuffer sb = new StringBuffer(raw.length());
		for (int i = 0; i < raw.length(); i++) {
			final char c = raw.charAt(i);
			if (c != '\0')
				sb.append(c);
		}
		return sb.toString();
	}

	public Validator getValidator() {
		return validator;
	}

	public void insertElement(final int offset, final Element element) throws DocumentValidationException {

		if (offset < 1 || offset >= getLength())
			throw new IllegalArgumentException("Error inserting element <" + element.getName() + ">: offset is " + offset + ", but it must be between 1 and "
					+ (getLength() - 1));

		final Validator validator = getValidator();
		if (validator != null) {
			final Element parent = getElementAt(offset);
			final List<QualifiedName> seq1 = getNodeNames(parent.getStartOffset() + 1, offset);
			final List<QualifiedName> seq2 = Collections.singletonList(element.getQualifiedName());
			final List<QualifiedName> seq3 = getNodeNames(offset, parent.getEndOffset());

			if (!validator.isValidSequence(parent.getQualifiedName(), seq1, seq2, seq3, true))
				throw new DocumentValidationException("Cannot insert element " + element.getName() + " at offset " + offset);
		}

		// find the parent, and the index into its children at which
		// this element should be inserted
		Element parent = rootElement;
		int childIndex = -1;
		while (childIndex == -1) {
			boolean tryAgain = false;
			final List<Element> children = parent.getChildElements();
			for (int i = 0; i < children.size(); i++) {
				final Element child = children.get(i);
				if (offset <= child.getStartOffset()) {
					childIndex = i;
					break;
				} else if (offset <= child.getEndOffset()) {
					parent = child;
					tryAgain = true;
					break;
				}
			}
			if (!tryAgain && childIndex == -1) {
				childIndex = children.size();
				break;
			}
		}

		fireBeforeContentInserted(new DocumentEvent(this, parent, offset, 2, null));

		content.insertString(offset, "\0\0");

		element.setContent(content, offset, offset + 1);
		element.setParent(parent);
		parent.insertChild(childIndex, element);

		final IUndoableEdit edit = undoEnabled ? new InsertElementEdit(offset, element) : null;

		fireContentInserted(new DocumentEvent(this, parent, offset, 2, edit));
	}

	public void insertFragment(final int offset, final DocumentFragment fragment) throws DocumentValidationException {

		if (offset < 1 || offset >= getLength())
			throw new IllegalArgumentException("Error inserting document fragment");

		final Element parent = getElementAt(offset);

		if (validator != null) {
			final List<QualifiedName> seq1 = getNodeNames(parent.getStartOffset() + 1, offset);
			final List<QualifiedName> seq2 = fragment.getNodeNames();
			final List<QualifiedName> seq3 = getNodeNames(offset, parent.getEndOffset());

			if (!validator.isValidSequence(parent.getQualifiedName(), seq1, seq2, seq3, true))
				throw new DocumentValidationException("Cannot insert document fragment");
		}

		fireBeforeContentInserted(new DocumentEvent(this, parent, offset, 2, null));

		final Content c = fragment.getContent();
		final String s = c.getString(0, c.getLength());
		content.insertString(offset, s);

		final List<Element> children = parent.getChildElements();
		int index = 0;
		while (index < children.size() && children.get(index).getEndOffset() < offset)
			index++;

		final List<Element> elements = fragment.getElements();
		for (int i = 0; i < elements.size(); i++) {
			final Element newElement = cloneElement(elements.get(i), content, offset, parent);
			parent.insertChild(index, newElement);
			index++;
		}

		final IUndoableEdit edit = undoEnabled ? new InsertFragmentEdit(offset, fragment) : null;

		fireContentInserted(new DocumentEvent(this, parent, offset, fragment.getContent().getLength(), edit));
	}

	public void insertText(final int offset, final String text) throws DocumentValidationException {

		if (offset < 1 || offset >= getLength())
			throw new IllegalArgumentException("Offset must be between 1 and n-1");

		final Element parent = getElementAt(offset);

		boolean isValid = false;
		if (getCharacterAt(offset - 1) != '\0')
			isValid = true;
		else if (getCharacterAt(offset) != '\0')
			isValid = true;
		else {
			final Validator validator = getValidator();
			if (validator != null) {
				final List<QualifiedName> seq1 = getNodeNames(parent.getStartOffset() + 1, offset);
				final List<QualifiedName> seq2 = Collections.singletonList(Validator.PCDATA);
				final List<QualifiedName> seq3 = getNodeNames(offset, parent.getEndOffset());

				isValid = validator.isValidSequence(parent.getQualifiedName(), seq1, seq2, seq3, true);
			} else
				isValid = true;
		}

		if (!isValid)
			throw new DocumentValidationException("Cannot insert text '" + text + "' at offset " + offset);

		// Convert control chars to spaces
		final StringBuffer sb = new StringBuffer(text);
		for (int i = 0; i < sb.length(); i++)
			if (Character.isISOControl(sb.charAt(i)) && sb.charAt(i) != '\n')
				sb.setCharAt(i, ' ');

		final String s = sb.toString();

		fireBeforeContentInserted(new DocumentEvent(this, parent, offset, 2, null));

		content.insertString(offset, s);

		final IUndoableEdit edit = undoEnabled ? new InsertTextEdit(offset, s) : null;

		fireContentInserted(new DocumentEvent(this, parent, offset, s.length(), edit));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocument#isUndoEnabled()
	 */
	public boolean isUndoEnabled() {
		return undoEnabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocument#removeDocumentListener
	 * (org.eclipse.wst.xml.vex.core.internal.dom.DocumentListener)
	 */
	public void removeDocumentListener(final DocumentListener listener) {
		listeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocument#setPublicID(java
	 * .lang.String)
	 */
	public void setPublicID(final String publicID) {
		this.publicID = publicID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocument#setSystemID(java
	 * .lang.String)
	 */
	public void setSystemID(final String systemID) {
		this.systemID = systemID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocument#setUndoEnabled
	 * (boolean)
	 */
	public void setUndoEnabled(final boolean undoEnabled) {
		this.undoEnabled = undoEnabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocument#setValidator(org
	 * .eclipse.wst.xml.vex.core.internal.dom.Validator)
	 */
	public void setValidator(final Validator validator) {
		this.validator = validator;
	}

	// ==================================================== PRIVATE

	/**
	 * Represents a deletion from a document that can be undone and redone.
	 */
	private class DeleteEdit implements IUndoableEdit {

		private final int startOffset;
		private final int endOffset;
		private final DocumentFragment frag;

		public DeleteEdit(final int startOffset, final int endOffset, final DocumentFragment frag) {
			this.startOffset = startOffset;
			this.endOffset = endOffset;
			this.frag = frag;
		}

		public boolean combine(final IUndoableEdit edit) {
			return false;
		}

		public void undo() throws CannotUndoException {
			try {
				setUndoEnabled(false);
				insertFragment(startOffset, frag);
			} catch (final DocumentValidationException ex) {
				throw new CannotUndoException();
			} finally {
				setUndoEnabled(true);
			}
		}

		public void redo() throws CannotRedoException {
			try {
				setUndoEnabled(false);
				delete(startOffset, endOffset);
			} catch (final DocumentValidationException ex) {
				throw new CannotUndoException();
			} finally {
				setUndoEnabled(true);
			}
		}

	}

	/**
	 * Represents an insertion of an element into the document.
	 */
	private class InsertElementEdit implements IUndoableEdit {

		private final int offset;
		private final Element element;

		public InsertElementEdit(final int offset, final Element element2) {
			this.offset = offset;
			element = element2;
		}

		public boolean combine(final IUndoableEdit edit) {
			return false;
		}

		public void undo() throws CannotUndoException {
			try {
				setUndoEnabled(false);
				delete(offset, offset + 2);
			} catch (final DocumentValidationException ex) {
				throw new CannotUndoException();
			} finally {
				setUndoEnabled(true);
			}
		}

		public void redo() throws CannotRedoException {
			try {
				setUndoEnabled(false);
				insertElement(offset, element);
			} catch (final DocumentValidationException ex) {
				throw new CannotUndoException();
			} finally {
				setUndoEnabled(true);
			}
		}

	}

	/**
	 * Represents an insertion of a fragment into the document.
	 */
	private class InsertFragmentEdit implements IUndoableEdit {

		private final int offset;
		private final DocumentFragment frag;

		public InsertFragmentEdit(final int offset, final DocumentFragment frag) {
			this.offset = offset;
			this.frag = frag;
		}

		public boolean combine(final IUndoableEdit edit) {
			return false;
		}

		public void undo() throws CannotUndoException {
			try {
				setUndoEnabled(false);
				final int length = frag.getContent().getLength();
				delete(offset, offset + length);
			} catch (final DocumentValidationException ex) {
				throw new CannotUndoException();
			} finally {
				setUndoEnabled(true);
			}
		}

		public void redo() throws CannotRedoException {
			try {
				setUndoEnabled(false);
				insertFragment(offset, frag);
			} catch (final DocumentValidationException ex) {
				throw new CannotUndoException();
			} finally {
				setUndoEnabled(true);
			}
		}

	}

	/**
	 * Represents an insertion of text into the document.
	 */
	private class InsertTextEdit implements IUndoableEdit {

		private final int offset;
		private String text;

		public InsertTextEdit(final int offset, final String text) {
			this.offset = offset;
			this.text = text;
		}

		public boolean combine(final IUndoableEdit edit) {
			if (edit instanceof InsertTextEdit) {
				final InsertTextEdit ite = (InsertTextEdit) edit;
				if (ite.offset == offset + text.length()) {
					text = text + ite.text;
					return true;
				}
			}
			return false;
		}

		public void undo() throws CannotUndoException {
			try {
				setUndoEnabled(false);
				delete(offset, offset + text.length());
			} catch (final DocumentValidationException ex) {
				throw new CannotUndoException();
			} finally {
				setUndoEnabled(true);
			}
		}

		public void redo() throws CannotRedoException {
			try {
				setUndoEnabled(false);
				insertText(offset, text);
			} catch (final DocumentValidationException ex) {
				throw new CannotUndoException();
			} finally {
				setUndoEnabled(true);
			}
		}

	}

	/**
	 * Assert that the given offset is within the given range, throwing
	 * IllegalArgumentException if not.
	 */
	private static void assertOffset(final int offset, final int min, final int max) {
		if (offset < min || offset > max)
			throw new IllegalArgumentException("Bad offset " + offset + "must be between " + min + " and " + max);
	}

	/**
	 * Clone an element tree, pointing to a new Content object.
	 * 
	 * @param original
	 *            Element to be cloned
	 * @param content
	 *            new Content object to which the clone will point
	 * @param shift
	 *            amount to shift offsets to be valid in the new Content.
	 * @param parent
	 *            parent for the cloned Element
	 */
	private Element cloneElement(final Element original, final Content content, final int shift, final Element parent) {

		final Element clone = new Element(original.getName());
		clone.setContent(content, original.getStartOffset() + shift, original.getEndOffset() + shift);
		for (final Attribute attribute : original.getAttributes())
			try {
				clone.setAttribute(attribute.getQualifiedName(), attribute.getValue());
			} catch (final DocumentValidationException ex) {
				throw new RuntimeException("Unexpected exception: " + ex);
			}
		clone.setParent(parent);

		final List<Element> children = original.getChildElements();
		for (int i = 0; i < children.size(); i++) {
			final Element cloneChild = cloneElement(children.get(i), content, shift, clone);
			clone.insertChild(i, cloneChild);
		}

		return clone;
	}

	public void fireAttributeChanged(final DocumentEvent e) {
		listeners.fireEvent("attributeChanged", e);
	}

	private void fireBeforeContentDeleted(final DocumentEvent e) {
		listeners.fireEvent("beforeContentDeleted", e);
	}

	private void fireBeforeContentInserted(final DocumentEvent e) {
		listeners.fireEvent("beforeContentInserted", e);
	}

	private void fireContentDeleted(final DocumentEvent e) {
		listeners.fireEvent("contentDeleted", e);
	}

	private void fireContentInserted(final DocumentEvent e) {
		listeners.fireEvent("contentInserted", e);
	}

	public void setDocumentURI(final String documentURI) {
		this.documentURI = documentURI;
	}

	public String getDocumentURI() {
		return documentURI;
	}

	public String getBaseURI() {
		return getDocumentURI();
	}
}
