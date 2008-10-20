package org.eclipse.wst.xml.vex.ui.internal.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextInputListener;

public class TextInputListener implements ITextInputListener {
	
	
	

	public void inputDocumentAboutToBeChanged(IDocument oldInput, IDocument newInput) {
		// do nothing
	}

	public void inputDocumentChanged(IDocument oldInput, IDocument newInput) {
		// TODO  Implement document update and switching. 
//		if ((fDesignViewer != null) && (newInput != null)) {
//			fDesignViewer.setDocument(newInput);
//		}
	}
}