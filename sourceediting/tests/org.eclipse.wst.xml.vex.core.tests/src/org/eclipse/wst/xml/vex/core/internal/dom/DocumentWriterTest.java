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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import junit.framework.TestCase;

import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.wst.xml.vex.core.internal.core.DisplayDevice;
import org.eclipse.wst.xml.vex.core.internal.css.MockDisplayDevice;
import org.eclipse.wst.xml.vex.core.internal.css.StyleSheet;
import org.eclipse.wst.xml.vex.core.internal.css.StyleSheetReader;
import org.eclipse.wst.xml.vex.core.internal.widget.CssWhitespacePolicy;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Test the DocumentWriterImpl class.
 */
public class DocumentWriterTest extends TestCase {

	public void testWriteDocument() throws Exception {
		DisplayDevice.setCurrent(new MockDisplayDevice(90, 90));
		final StyleSheetReader reader = new StyleSheetReader();
		final StyleSheet ss = reader.read(this.getClass().getResource("test.css"));

		final URL docUrl = this.getClass().getResource("DocumentWriterTest1.xml");

		final Document docOrig = readDocument(new InputSource(docUrl.toString()), ss);

		final DocumentWriter dw = new DocumentWriter();
		dw.setWhitespacePolicy(new CssWhitespacePolicy(ss));
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		dw.write(docOrig, os);

		final InputStream is = new ByteArrayInputStream(os.toByteArray());

		// Dump document to console
		// BufferedReader br = new BufferedReader(new InputStreamReader(is));
		// while (true) {
		// String s = br.readLine();
		// if (s == null)
		// break;
		// System.out.println(s);
		// }
		// is.reset();

		final Document docNew = readDocument(new InputSource(is), ss);

		assertEquals(docOrig, docNew);
	}

	private void assertEquals(final Document expected, final Document actual) throws Exception {

		assertEquals(expected.getRootElement(), actual.getRootElement());
	}

	private void assertEquals(final Element expected, final Element actual) throws Exception {

		System.out.println("Checking " + actual.getName());
		assertEquals(expected.getName(), actual.getName());

		final List<QualifiedName> expectedAttrs = expected.getAttributeNames();
		final List<QualifiedName> actualAttrs = actual.getAttributeNames();

		assertEquals(expectedAttrs.size(), actualAttrs.size());
		for (int i = 0; i < expectedAttrs.size(); i++)
			assertEquals(expectedAttrs.get(i), actualAttrs.get(i));

		final List<Node> expectedContent = expected.getChildNodes();
		final List<Node> actualContent = actual.getChildNodes();
		assertEquals(expectedContent.size(), actualContent.size());
		for (int i = 0; i < expectedContent.size(); i++) {
			assertEquals(expectedContent.get(i).getClass(), actualContent.get(i).getClass());
			if (expectedContent.get(i) instanceof Element)
				assertEquals((Element) expectedContent.get(i), (Element) actualContent.get(i));
			else
				assertEquals(expectedContent.get(i).getText(), actualContent.get(i).getText());
		}
	}

	private static Document readDocument(final InputSource is, final StyleSheet ss) throws ParserConfigurationException, SAXException, IOException {

		final SAXParserFactory factory = SAXParserFactory.newInstance();
		final XMLReader xmlReader = factory.newSAXParser().getXMLReader();
		final DefaultHandler defaultHandler = new DefaultHandler();

		final IWhitespacePolicy policy = new CssWhitespacePolicy(ss);

		final IWhitespacePolicyFactory wsFactory = new IWhitespacePolicyFactory() {
			public IWhitespacePolicy getPolicy(final String publicId) {
				return policy;
			}
		};

		final org.eclipse.wst.xml.vex.core.internal.dom.DocumentBuilder builder = new org.eclipse.wst.xml.vex.core.internal.dom.DocumentBuilder(wsFactory);

		xmlReader.setContentHandler(builder);
		xmlReader.setDTDHandler(defaultHandler);
		xmlReader.setEntityResolver(defaultHandler);
		xmlReader.setErrorHandler(defaultHandler);
		xmlReader.parse(is);
		return builder.getDocument();
	}

	/**
	 * Which elements are block elements for the purposes of our test cases.
	 */
	/*
	 * private static boolean isBlockElement(Element element) { return
	 * element.getName().equals("html") || element.getName().equals("body") ||
	 * element.getName().equals("p"); }
	 */
	/**
	 * Parse the given document and pass them through to stdout to confirm their
	 * goodness.
	 */
	public static void main(final String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: java DocumentWriterTest filename width");
			System.exit(1);
		}

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(args[0]);
			final int width = Integer.parseInt(args[1]);
			final StyleSheetReader reader = new StyleSheetReader();
			final StyleSheet ss = reader.read(DocumentWriterTest.class.getResource("test.css"));
			final Document doc = readDocument(new InputSource(fis), ss);

			final DocumentWriter writer = new DocumentWriter();
			writer.setWhitespacePolicy(new CssWhitespacePolicy(ss));
			writer.setWrapColumn(width);

			writer.write(doc, System.out);
		} catch (final Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (final IOException ex) {
				}
				fis = null;
			}
		}
	}
}
