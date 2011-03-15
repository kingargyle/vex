/*******************************************************************************
 * Copyright (c) 2008 Standards for Technology in Automotive Retail and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     David Carver (STAR) - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.dom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMDocument;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Class for creating documents given a DOM Document.
 */
@SuppressWarnings("restriction")
public class DOMDocumentReader extends DocumentReader {

	/**
	 * Reads a document given a DOM Document
	 * 
	 * @param domDocument
	 *            IDOMDocument from which to load the document.
	 */
	public Document read(IDOMDocument domDocument) throws IOException,
			ParserConfigurationException, SAXException {
		final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(domDocument.getModel().getBaseLocation()));
		final Reader reader = new StringReader(domDocument.getSource());
		final InputSource inputSource = new InputSource(new BufferedReader(reader));
		inputSource.setSystemId(file.getLocationURI().toString());
		final Document result = read(inputSource);
		return result;
	}

}
