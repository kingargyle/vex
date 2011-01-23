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

import java.util.LinkedList;

import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.xml.core.internal.document.DOMModelImpl;
import org.eclipse.wst.xml.core.internal.provisional.contenttype.ContentTypeIdForXML;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IWhitespacePolicy;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IWhitespacePolicyFactory;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Content;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.LexicalHandler;

/**
 * A SAX handler that builds a Vex document. This builder collapses whitespace
 * as it goes, according to the following rules.
 * 
 * <ul>
 * <li>Elements with style white-space: pre are left alone.</li>
 * <li>Runs of whitespace are replaced with a single space.</li>
 * <li>Space just inside the start and end of elements is removed.</li>
 * <li>Space just outside the start and end of block-formatted elements is
 * removed.</li>
 * </ul>
 */
public class DocumentBuilder implements ContentHandler, LexicalHandler {
	private IWhitespacePolicyFactory policyFactory;
	private IWhitespacePolicy policy;

	// Holds pending characters until we see another element boundary.
	// This is (a) so we can collapse spaces in multiple adjacent character
	// blocks, and (b) so we can trim trailing whitespace, if necessary.
	private StringBuffer pendingChars = new StringBuffer();

	// If true, trim the leading whitespace from the next received block of
	// text.
	private boolean trimLeading = false;

	// Content object to hold document content
	private Content content = new GapContent(100);

	// Stack of StackElement objects
	private LinkedList<StackEntry> stack = new LinkedList<StackEntry>();

	private VEXElement rootElement;

	private String dtdPublicID;
	private String dtdSystemID;
	private Document doc;
	private org.eclipse.wst.xml.core.internal.provisional.document.IDOMDocument domDocument;
	private Locator locator;

	/**
	 * Class constructor.
	 * 
	 * @param policyFactory
	 *            Used to determine the WhitespacePolicy to use for a given
	 *            document type.
	 */
	public DocumentBuilder(IWhitespacePolicyFactory policyFactory) {
		this.policyFactory = policyFactory;
	}
	
//	public DocumentBuilder(IWhitespacePolicyFactory policyFactory, IDOMDocument domDocument) {
//		this.policyFactory = policyFactory;
//		this.domDocument = domDocument;
//	}

	/**
	 * Returns the newly built <code>Document</code> object.
	 */
	public VEXDocument getDocument() {
		return this.doc;
	}

	// ============================================= ContentHandler methods

	public void characters(char[] ch, int start, int length)
			throws SAXException {

		// Convert nuls to spaces, since we use nulls for element delimiters
		char[] chars = new char[length];
		System.arraycopy(ch, start, chars, 0, length);
		for (int i = 0; i < chars.length; i++) {
			if (Character.isISOControl(chars[i]) && chars[i] != '\n'
					&& chars[i] != '\r' && chars[i] != '\t') {
				chars[i] = ' ';
			}
			
		}
		this.pendingChars.append(chars);
	}

	public void endDocument() {
		if (rootElement == null)
			return;
		
		doc = new Document(content, rootElement);
		doc.setPublicID(dtdPublicID);
		doc.setSystemID(dtdSystemID);
		rootElement.setDocument(doc);
		
		org.w3c.dom.Node node = rootElement.getElement();
		domDocument.appendChild(node);
		doc.setDocument(domDocument);
	}

	public void endElement(String namespaceURI, String localName, String qName) {

		this.appendChars(true);

		StackEntry entry = this.stack.removeLast();

		// we must insert the trailing sentinel first, else the insertion
		// pushes the end position of the element to after the sentinel
		this.content.insertString(content.getLength(), "\0");
		entry.element.setContent(this.content, entry.offset, content
				.getLength() - 1);
//		int length = entry.element.getEndOffset() - entry.element.getStartOffset();
//		String text = content.getString(entry.element.getStartOffset(), length);
//		org.w3c.dom.Text textNode = entry.element.getElement().getOwnerDocument().createTextNode(text);
//		entry.element.getElement().appendChild(textNode);

		if (this.isBlock(entry.element)) {
			this.trimLeading = true;
		}
	}

	public void endPrefixMapping(java.lang.String prefix) {
	}

	public void ignorableWhitespace(char[] ch, int start, int length) {
	}

	public void processingInstruction(String target, String data) {
	}

	public void setDocumentLocator(Locator locator) {
		this.locator = locator;
	}

	public void skippedEntity(java.lang.String name) {
	}

	public void startDocument() {
		initDOM();
	}

	private void initDOM() {
		IDOMModel model = null;
		model = new DOMModelImpl();
		IModelManager modelManager = StructuredModelManager.getModelManager();
		IStructuredDocument structuredDocument = modelManager.createStructuredDocumentFor(ContentTypeIdForXML.ContentTypeID_XML);
		model.setStructuredDocument(structuredDocument);
		domDocument =  model.getDocument();
	}

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes attrs)

	throws SAXException {

		try {
			VEXElement element;
			if (domDocument == null) {
				initDOM();
			}
			if (stack.isEmpty()) {
				rootElement = new RootElement(qName);
				org.w3c.dom.Element domElement = domDocument.createElement(qName);
				element = this.rootElement;
				rootElement.setElement(domElement);
				domDocument.appendChild(domElement);
				if (this.policyFactory != null) {
					this.policy = this.policyFactory
							.getPolicy(this.dtdPublicID);
				}
			} else {
				element = new Element(qName);
				org.w3c.dom.Element domElement = domDocument.createElement(qName);
				element.setElement(domElement);

				VEXElement parent = stack.getLast().element;
				parent.addChild(element);
			}

			int n = attrs.getLength();
			for (int i = 0; i < n; i++) {
				element.setAttribute(attrs.getQName(i), attrs.getValue(i));
			}

//			org.w3c.dom.Text textNode = domDocument.createTextNode(cleanUpText(this.isBlock(element)).toString());
//			element.getElement().appendChild(textNode);
			this.appendChars(this.isBlock(element));

			stack.add(new StackEntry(element, content.getLength(), this
					.isPre(element)));
			content.insertString(content.getLength(), "\0");

			this.trimLeading = true;

		} catch (DocumentValidationException ex) {
			throw new SAXParseException("DocumentValidationException",
					this.locator, ex);
		}

	}
	
	public void startPrefixMapping(String prefix, String uri) {
	}

	// ============================================== LexicalHandler methods

	public void comment(char[] ch, int start, int length) {
	}

	public void endCDATA() {
	}

	public void endDTD() {
	}

	public void endEntity(String name) {
	}

	public void startCDATA() {
	}

	public void startDTD(String name, String publicId, String systemId) {
		this.dtdPublicID = publicId;
		this.dtdSystemID = systemId;
	}

	public void startEntity(java.lang.String name) {
	}

	// ======================================================== PRIVATE


	// Append any pending characters to the content
	private void appendChars(boolean trimTrailing) {

		StringBuffer sb;

		sb = cleanUpTextContent(trimTrailing);

		this.content.insertString(this.content.getLength(), sb.toString());

		this.pendingChars.setLength(0);
		this.trimLeading = false;
	}

	private StringBuffer cleanUpTextContent(boolean trimTrailing) {
		StringBuffer sb;
		StackEntry entry = this.stack.isEmpty() ? null : this.stack.getLast();

		if (entry != null && entry.pre) {

			sb = this.pendingChars;

		} else {

			// collapse the space in the pending characters
			sb = new StringBuffer(this.pendingChars.length());
			boolean ws = false; // true if we're in a run of whitespace
			for (int i = 0; i < this.pendingChars.length(); i++) {
				char c = this.pendingChars.charAt(i);
				if (Character.isWhitespace(c)) {
					ws = true;
				} else {
					if (ws) {
						sb.append(' ');
						ws = false;
					}
					sb.append(c);
				}
			}
			if (ws) {
				sb.append(' ');
			}
			// trim leading and trailing space, if necessary
			if (this.trimLeading && sb.length() > 0 && sb.charAt(0) == ' ') {
				sb.deleteCharAt(0);
			}
			if (trimTrailing && sb.length() > 0
					&& sb.charAt(sb.length() - 1) == ' ') {
				sb.setLength(sb.length() - 1);
			}
		}

		this.normalizeNewlines(sb);
		return sb;
	}

	private boolean isBlock(VEXElement element) {
		return this.policy != null && this.policy.isBlock(element);
	}

	private boolean isPre(VEXElement element) {
		return this.policy != null && this.policy.isPre(element);
	}

	/**
	 * Convert lines that end in CR and CRLFs to plain newlines.
	 * 
	 * @param sb
	 *            StringBuffer to be normalized.
	 */
	private void normalizeNewlines(StringBuffer sb) {

		// State machine states
		final int START = 0;
		final int SEEN_CR = 1;

		int state = START;
		int i = 0;
		while (i < sb.length()) {
			// No simple 'for' here, since we may delete chars

			char c = sb.charAt(i);

			switch (state) {
			case START:
				if (c == '\r') {
					state = SEEN_CR;
				}
				i++;
				break;

			case SEEN_CR:
				if (c == '\n') {
					// CR-LF, just delete the previous CR
					sb.deleteCharAt(i - 1);
					state = START;
					// no need to advance i, since it's done implicitly
				} else if (c == '\r') {
					// CR line ending followed by another
					// Replace the first with a newline...
					sb.setCharAt(i - 1, '\n');
					i++;
					// ...and stay in the SEEN_CR state
				} else {
					// CR line ending, replace it with a newline
					sb.setCharAt(i - 1, '\n');
					i++;
					state = START;
				}
			}
		}

		if (state == SEEN_CR) {
			// CR line ending, replace it with a newline
		}
	}

	private static class StackEntry {
		public VEXElement element;
		public int offset;
		public boolean pre;

		public StackEntry(VEXElement element, int offset, boolean pre) {
			this.element = element;
			this.offset = offset;
			this.pre = pre;
		}
	}
}
