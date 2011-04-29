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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.wst.xml.vex.core.internal.validator.AttributeDefinition;

/**
 * Writes a document to an output stream, using a stylesheet to provide
 * formatting hints.
 * 
 * <ul>
 * <li>Children of an element are indented by a configurable amount.</li>
 * <li>Text is wrapped to fit within a configurable width.
 * <li>
 * </ul>
 * 
 * <p>
 * Documents are currently saved UTF-8 encoding, with no encoding specified in
 * the XML declaration.
 * </p>
 */
public class DocumentWriter {

	private IWhitespacePolicy whitespacePolicy;
	private String indent;
	private int wrapColumn;

	/**
	 * Class constructor.
	 */
	public DocumentWriter() {
		indent = "  ";
		wrapColumn = 72;
	}

/**
     * Escapes special XML characters. Changes '<', '>', and '&' to
     * '&lt;', '&gt;' and '&amp;', respectively.
     *
     * @param s the string to be escaped.
     * @return the escaped string
     */
	public static String escape(final String s) {
		final StringBuffer sb = new StringBuffer(s.length());

		for (int i = 0; i < s.length(); i++) {
			final char c = s.charAt(i);
			if (c == '<')
				sb.append("&lt;");
			else if (c == '>')
				sb.append("&gt;");
			else if (c == '&')
				sb.append("&amp;");
			else if (c == '"')
				sb.append("&quot;");
			else if (c == '\'')
				sb.append("&apos;");
			else
				sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * Returns the indent string. By default this is two spaces.
	 */
	public String getIndent() {
		return indent;
	}

	/**
	 * Returns the whitespace policy used by this writer.
	 */
	public IWhitespacePolicy getWhitespacePolicy() {
		return whitespacePolicy;
	}

	/**
	 * Returns the column at which text should be wrapped. By default this is
	 * 72.
	 */
	public int getWrapColumn() {
		return wrapColumn;
	}

	/**
	 * Sets the value of the indent string.
	 * 
	 * @param indent
	 *            new value for the indent string.
	 */
	public void setIndent(final String indent) {
		this.indent = indent;
	}

	/**
	 * Sets the whitespace policy for this writer. The whitespace policy tells
	 * the writer which elements are block-formatted and which are
	 * pre-formatted.
	 * 
	 * @param whitespacePolicy
	 *            The whitespacePolicy to set.
	 */
	public void setWhitespacePolicy(final IWhitespacePolicy whitespacePolicy) {
		this.whitespacePolicy = whitespacePolicy;
	}

	/**
	 * Sets the value of the wrap column.
	 * 
	 * @param wrapColumn
	 *            new value for the wrap column.
	 */
	public void setWrapColumn(final int wrapColumn) {
		this.wrapColumn = wrapColumn;
	}

	public void write(final Document doc, final OutputStream os) throws IOException {

		final OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
		final PrintWriter pw = new PrintWriter(osw);
		pw.println("<?xml version='1.0'?>");

		if ((doc).getSystemID() != null) {
			final StringBuffer sb = new StringBuffer();
			sb.append("<!DOCTYPE ");
			sb.append(doc.getRootElement().getPrefixedName());
			if (doc.getPublicID() != null) {
				sb.append(" PUBLIC");
				sb.append(" \"");
				sb.append(doc.getPublicID());
				sb.append("\"");
			} else
				sb.append(" SYSTEM");
			sb.append(" \"");
			sb.append((doc).getSystemID());
			sb.append("\">");
			pw.println(sb.toString());
		}
		writeNode(doc.getRootElement(), pw, "");
		pw.flush();
	}

	// ====================================================== PRIVATE

	private void writeNode(final Node node, final PrintWriter pw, final String indent) {

		if (node instanceof Text) {
			final TextWrapper wrapper = new TextWrapper();
			wrapper.add(escape(node.getText()));

			final String[] lines = wrapper.wrap(wrapColumn - indent.length());

			for (final String line : lines) {
				pw.print(indent);
				pw.println(line);
			}

		} else {

			final Element element = (Element) node;

			if (whitespacePolicy != null && whitespacePolicy.isPre(element)) {
				pw.print(indent);
				writeNodeNoWrap(node, pw);
				pw.println();
				return;
			}

			boolean hasBlockChild = false;
			final List<Element> children = element.getChildElements();
			for (int i = 0; i < children.size(); i++)
				if (whitespacePolicy != null && whitespacePolicy.isBlock(children.get(i))) {
					hasBlockChild = true;
					break;
				}

			if (hasBlockChild) {
				pw.print(indent);
				pw.print("<");
				pw.print(element.getPrefixedName());

				final TextWrapper wrapper = new TextWrapper();
				wrapper.addNoSplit(getNamespaceDeclarationsString(element));
				wrapper.addNoSplit(getAttributeString(element));
				final int outdent = indent.length() + 1 + element.getPrefixedName().length();
				final String[] lines = wrapper.wrap(wrapColumn - outdent);
				final char[] bigIndent = new char[outdent];
				Arrays.fill(bigIndent, ' ');
				for (int i = 0; i < lines.length; i++) {
					if (i > 0)
						pw.print(bigIndent);
					pw.print(lines[i]);
					if (i < lines.length - 1)
						pw.println();
				}
				pw.println(">");

				final String childIndent = indent + this.indent;
				final List<Node> content = element.getChildNodes();
				for (int i = 0; i < content.size(); i++)
					writeNode(content.get(i), pw, childIndent);
				pw.print(indent);
				pw.print("</");
				pw.print(element.getPrefixedName());
				pw.println(">");
			} else {
				final TextWrapper wrapper = new TextWrapper();
				addNode(element, wrapper);
				final String[] lines = wrapper.wrap(wrapColumn - indent.length());
				for (final String line : lines) {
					pw.print(indent);
					pw.println(line);
				}
			}

		}
	}

	private void writeNodeNoWrap(final Node node, final PrintWriter pw) {

		if (node instanceof Text)
			pw.print(escape(node.getText()));
		else {

			final Element element = (Element) node;

			pw.print("<");
			pw.print(element.getPrefixedName());
			pw.print(getNamespaceDeclarationsString(element));
			pw.print(getAttributeString(element));
			pw.print(">");

			for (Node child : element.getChildNodes())
				writeNodeNoWrap(child, pw);

			pw.print("</");
			pw.print(element.getPrefixedName());
			pw.print(">");
		}
	}
	
	private String getNamespaceDeclarationsString(final Element element) {
		final StringBuilder result = new StringBuilder();
		final String declaredNamespaceURI = element.getDeclaredDefaultNamespaceURI();
		if (declaredNamespaceURI != null)
			result.append(" xmlns=\"").append(declaredNamespaceURI).append("\"");
		for (final String prefix : element.getDeclaredNamespacePrefixes()) {
			result.append(" xmlns:")
			.append(prefix)
			.append("=\"")
			.append(element.getNamespaceURI(prefix))
			.append("\"");
		}
		return result.toString();
	}

	private String attrToString(final Attribute attribute) {
		final StringBuffer result = new StringBuffer();
		result.append(" ");
		result.append(attribute.getPrefixedName());
		result.append("=\"");
		result.append(escape(attribute.getValue()));
		result.append("\"");
		return result.toString();
	}

	private void addNode(final Node node, final TextWrapper wrapper) {
		if (node instanceof Text)
			wrapper.add(escape(node.getText()));
		else {
			final Element element = (Element) node;
			final List<Node> content = element.getChildNodes();
			final Collection<Attribute> attributes = element.getAttributes();

			if (attributes.isEmpty()) {
				if (content.isEmpty())
					wrapper.add("<" + element.getPrefixedName() + "/>");
				else
					wrapper.add("<" + element.getPrefixedName() + ">");
			} else {
				final Validator validator = element.getDocument().getValidator();
				final StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("<" + element.getPrefixedName());
				stringBuffer.append(getNamespaceDeclarationsString(element));
				for (final Attribute attribute : attributes) {
					if (!attrHasDefaultValue(validator, attribute))
						stringBuffer.append(attrToString(attribute));
				}
				if (content.isEmpty())
					stringBuffer.append("/>");
				else
					stringBuffer.append(">");
				wrapper.addNoSplit(stringBuffer.toString());
			}

			for (int i = 0; i < content.size(); i++)
				addNode(content.get(i), wrapper);

			if (!content.isEmpty())
				wrapper.add("</" + element.getPrefixedName() + ">");
		}
	}

	private String getAttributeString(final Element element) {
		final Validator validator = element.getDocument().getValidator();

		final StringBuffer result = new StringBuffer();
		for (final Attribute attribute : element.getAttributes())
			if (!attrHasDefaultValue(validator, attribute)) {
				result.append(" ");
				result.append(attribute.getPrefixedName());
				result.append("=\"");
				result.append(escape(attribute.getValue()));
				result.append("\"");
			}
		return result.toString();
	}

	private static boolean attrHasDefaultValue(final Validator validator, final Attribute attribute) {
		if (validator != null) {
			final AttributeDefinition attributeDefinition = validator.getAttributeDefinition(attribute);
			if (attributeDefinition != null) {
				final String currentValue = attribute.getValue();
				final String defaultValue = attributeDefinition.getDefaultValue();
				return currentValue.equals(defaultValue);
			}
		}
		return false;
	}
}
