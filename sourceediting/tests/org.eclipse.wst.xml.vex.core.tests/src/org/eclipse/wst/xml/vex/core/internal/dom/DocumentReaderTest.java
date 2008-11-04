package org.eclipse.wst.xml.vex.core.internal.dom;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IVEXDocument;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IVEXElement;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import junit.framework.TestCase;

public class DocumentReaderTest extends TestCase {

	String xml;
	@Override
	protected void setUp() throws Exception {
		xml = "<xhtml:html xmlns:xhtml=\"http://www.w3.org/1999/xhtml\"><xhtml:body/></xhtml:html>";
		super.setUp();
	}
	
    public void testReadXMLDocument() throws Exception {
    	IVEXDocument document = setupDocument();
    	this.assertNotNull("Document is null", document);
    	
    }

	private IVEXDocument setupDocument() throws IOException,
			ParserConfigurationException, SAXException {
		Reader reader = new StringReader(xml);
    	DocumentReader docReader = new DocumentReader();
    	IVEXDocument document = docReader.read(new InputSource(reader));
		return document;
	}
    
    public void testNamespaceExists() throws Exception {
    	IVEXDocument document = setupDocument();
    	IVEXElement element = document.getRootElement();
    	assertEquals("Missing XHTML namespace.", "http://www.w3.org/1999/xhtml", element.getNamespace());
    }
    
    public void testNamespacePrefixExists() throws Exception {
    	IVEXDocument document = setupDocument();
    	IVEXElement element = document.getRootElement();
    	assertEquals("Missing XHTML namespace.", "xhtml", element.getNamespacePrefix());
    }
    
    
}
