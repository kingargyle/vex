package org.eclipse.wst.xml.vex.core.internal.dom;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import junit.framework.TestCase;

import org.eclipse.emf.common.util.EList;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.xml.core.internal.document.DOMModelImpl;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMDocument;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement;
import org.eclipse.wst.xml.vex.core.tests.VEXCoreTestPlugin;
import org.w3c.dom.Attr;

public class DOMSynchronizationTest extends TestCase {
	private IStructuredDocument loadTestFile()
			throws UnsupportedEncodingException, IOException {
		IModelManager modelManager = StructuredModelManager.getModelManager();
		URL url = VEXCoreTestPlugin.getDefault().getBundle().getEntry(
				"/projectFiles/SecuringYourApacheServer.xml");
		IStructuredDocument structuredDocument = null;
		IStructuredModel model = null;
		try {
		model = modelManager.getModelForRead(
				"SecuringYourApacheServer.xml", url.openStream(), null);
		structuredDocument = model.getStructuredDocument();
		} finally {
			model.releaseFromRead();
		}
		return structuredDocument;
	}

	private IDOMDocument getDOM() throws Exception {
		IStructuredDocument document = loadTestFile();
		IDOMModel model = new DOMModelImpl();
		model.setStructuredDocument(document);
		IDOMDocument domDocument = model.getDocument();
		return domDocument;
	}
	
	public void testLoadXML() throws Exception {
		IStructuredDocument structuredDocument = loadTestFile();
		assertNotNull("Structured Document was null", structuredDocument);
	}
	
	public void testGetDOMForStructuredDocument() throws Exception {
		IDOMDocument domDocument = getDOM();
		assertNotNull("DOM Model not loaded.", domDocument);
	}
	
	public void testDOMtoVEXDocument() throws Exception {
		org.w3c.dom.Document domDocument = getDOM();
		VEXDocument vexDocument = new Document(domDocument);
		assertEquals("DOM's not equal.", domDocument, vexDocument.getDOMDocument());
	}
	
	public void testRootElement() throws Exception {
		org.w3c.dom.Document domDocument = getDOM();
		VEXDocument vexDocument = new Document(domDocument);
		VEXElement rootElement = vexDocument.getRootElement();
		assertEquals("Root element is incorrect.", "chapter", rootElement.getName());
		assertEquals("Synchronization in elements incorrect.", rootElement.getName(), domDocument.getDocumentElement().getNodeName());
		assertEquals("Root element's dom incorrect.", rootElement.getElement(), domDocument.getDocumentElement());
	}
	
	public void testChapterElementAttributes() throws Exception {
		org.w3c.dom.Document domDocument = getDOM();
		VEXDocument vexDocument = new Document(domDocument);
		VEXElement rootElement = vexDocument.getRootElement();
		
		EList<String> attributeNameList = rootElement.getAttributeNames();
		assertEquals("Wrong number of attributes", 1, attributeNameList.size());
		
		org.w3c.dom.Element domElement = domDocument.getDocumentElement();
		for (int cnt = 0; cnt < attributeNameList.size(); cnt++) {
			String attrName = attributeNameList.get(cnt);
			Attr domAttr = domElement.getAttributeNode(attrName);
			assertNotNull("Missing DOM Attribute.", domAttr);
			assertEquals("Incorrect Attribute Name", attrName, domAttr.getName());
		}
	}
	
//	public void testChildrenElements() throws Exception {
//		org.w3c.dom.Document domDocument = getDOM();
//		VEXDocument vexDocument = new Document(domDocument);
//		VEXElement rootElement = vexDocument.getRootElement();
//		assertEquals("Wrong number of children.", 2, rootElement.getChildElements().size());
//		EList nodeList = rootElement.getChildNodes();
//		VEXElement chapterElement = (VEXElement) rootElement.getChildNodes().get(0);
//		assertTrue("No children found.", chapterElement.getChildNodes().size() > 0);
//	}
	
//	public void testTitleElementContent() throws Exception {
//		org.w3c.dom.Document domDocument = getDOM();
//		VEXDocument vexDocument = new Document(domDocument);
//		VEXElement rootElement = vexDocument.getRootElement();
//		VEXElement chapterElement = rootElement.getChildElements().get(0);
//		VEXElement titleElement = chapterElement.getChildElements().get(0);
//		
//		assertNotNull("Title Element missing.", titleElement);
//		assertEquals("Wrong element", "title", titleElement.getName());
//		
//		int startOffset = titleElement.getStartOffset();
//		int length = titleElement.getEndOffset() - titleElement.getEndOffset();
//		assertEquals("Wrong Content", "Securing Your Apache Server with SSL", titleElement.getContent().getString(startOffset, length));
//	}
}
