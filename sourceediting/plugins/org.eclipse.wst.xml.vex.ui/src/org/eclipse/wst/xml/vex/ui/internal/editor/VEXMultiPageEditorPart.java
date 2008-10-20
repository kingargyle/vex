package org.eclipse.wst.xml.vex.ui.internal.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.wst.sse.ui.StructuredTextEditor;
import org.eclipse.wst.xml.ui.internal.Logger;
import org.eclipse.wst.xml.ui.internal.tabletree.XMLEditorMessages;

public class VEXMultiPageEditorPart extends MultiPageEditorPart {

	private StructuredTextEditor textEditor = null;
	private VexEditor vexEditor = null;
	private int sourcePageIndex;
	private int visualPageIndex;
	
	protected void createPages() {
		try {
			createVisualEditorPage();
			createSourcePage();

			addVisualEditorPage();
			addSourcePage();
			vexEditor.setDocument(getDocument());
			setActivePage(visualPageIndex);
		} catch (PartInitException e) {
			Logger.logException(e);
			throw new RuntimeException(e);
		}

	}
	
	private void createSourcePage() {
		textEditor = new StructuredTextEditor();
		textEditor.setEditorPart(this);
	}
	
	private void createVisualEditorPage() {
		vexEditor = new VexEditor();
	}
	
	/**
	 * Adds the source page of the multi-page editor.
	 */
	private void addSourcePage() throws PartInitException {
		sourcePageIndex = addPage(textEditor, getEditorInput());
		setPageText(sourcePageIndex, XMLEditorMessages.XMLMultiPageEditorPart_0);

		firePropertyChange(PROP_TITLE);

		// Changes to the Text Viewer's document instance should also
		// force an input refresh
		textEditor.getTextViewer().addTextInputListener(new TextInputListener());
	}
	
	private void addVisualEditorPage() throws PartInitException {
		visualPageIndex = addPage(vexEditor, getEditorInput());
		setPageText(visualPageIndex, "VEX");
		firePropertyChange(PROP_TITLE);
	}

	public void doSave(IProgressMonitor monitor) {
	   textEditor.doSave(monitor);
	}

	public void doSaveAs() {
		textEditor.doSaveAs();
	}

	public boolean isSaveAsAllowed() {
		return textEditor.isSaveAsAllowed();
	}

	private IDocument getDocument() {
		IDocument document = null;
		if (textEditor != null) {
			document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
		}
		return document;
	}	
}
