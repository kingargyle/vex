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
package org.eclipse.wst.xml.vex.ui.internal.editor;

import java.util.ResourceBundle;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.texteditor.FindNextAction;
import org.eclipse.ui.texteditor.FindReplaceAction;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;
import org.eclipse.wst.xml.ui.internal.tabletree.XMLMultiPageEditorActionBarContributor;
import org.eclipse.wst.xml.vex.core.internal.dom.DocumentValidationException;
import org.eclipse.wst.xml.vex.ui.internal.swt.VexWidget;

/**
 * Contribute actions on behalf of the VexEditor.
 */
public class VexActionBarContributor extends XMLMultiPageEditorActionBarContributor {

	public void dispose() {
	}

	public VexEditor getVexEditor() {
		return (VexEditor) activeEditor;
	}

	public VexWidget getVexWidget() {
		if (activeEditor != null) {
			return ((VexEditor) activeEditor).getVexWidget();
		} else {
			return null;
		}
	}

	public void init(IActionBars bars, IWorkbenchPage page) {
		super.init(bars, page);
		page.addSelectionListener(this.selectionListener);
	}

	public void setActiveEditor(IEditorPart activeEditor) {

		// This can occur if we have an error loading the editor,
		// in which case Eclipse provides its own part
		if (!(activeEditor instanceof VexEditor)) {
			return;
		}

		this.activeEditor = activeEditor;
		setId(this.copyAction, ActionFactory.COPY.getId());
		setId(this.cutAction, ActionFactory.CUT.getId());
		setId(this.deleteAction, ActionFactory.DELETE.getId());
		setId(this.pasteAction, ActionFactory.PASTE.getId());
		setId(this.redoAction, ActionFactory.REDO.getId());
		setId(this.selectAllAction, ActionFactory.SELECT_ALL.getId());
		setId(this.undoAction, ActionFactory.UNDO.getId());

		String findActionMessagesBundleId =
			"org.eclipse.ui.texteditor.ConstructedEditorMessages";
		ResourceBundle resourceBundle =
			ResourceBundle.getBundle(findActionMessagesBundleId);

		// Find/Replace
		IAction findAction = new FindReplaceAction(resourceBundle,
                                                   "Editor.FindReplace.",
                                                   this.activeEditor);
		setId(findAction, ActionFactory.FIND.getId());

		// Find Next
		IAction findNextAction = new FindNextAction(resourceBundle,
				                                    "Editor.FindNext.",
				                                    this.activeEditor,
				                                    true);
		setIds(findNextAction,
			   ITextEditorActionConstants.FIND_NEXT,
			   IWorkbenchActionDefinitionIds.FIND_NEXT);

		// Find Previous
		IAction findPreviousAction = new FindNextAction(resourceBundle,
				                                        "Editor.FindPrevious.",
				                                        this.activeEditor,
				                                        false);
		setIds(findPreviousAction,
  			   ITextEditorActionConstants.FIND_PREVIOUS,
  			   IWorkbenchActionDefinitionIds.FIND_PREVIOUS);

		enableActions();
	}

	private void setIds(IAction action,
			            String actionId,
			            String commandId) {
		action.setActionDefinitionId(commandId);
		setId(action, actionId);
	}

	private void setId(IAction action, String actionId) {
		getActionBars().setGlobalActionHandler(actionId, action);
	}

	private IEditorPart activeEditor;

	private IAction copyAction = new CopyAction();
	private IAction cutAction = new CutAction();
	private IAction deleteAction = new DeleteAction();
	private IAction pasteAction = new PasteAction();
	private IAction redoAction = new RedoAction();
	private IAction selectAllAction = new SelectAllAction();
	private IAction undoAction = new UndoAction();

	private void enableActions() {
		VexWidget widget = this.getVexWidget();
		this.copyAction.setEnabled(widget != null && widget.hasSelection());
		this.cutAction.setEnabled(widget != null && widget.hasSelection());
		this.deleteAction.setEnabled(widget != null && widget.hasSelection());
		this.redoAction.setEnabled(widget != null && widget.canRedo());
		this.undoAction.setEnabled(widget != null && widget.canUndo());
	}

	private ISelectionListener selectionListener = new ISelectionListener() {
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			enableActions();
		}
	};

	private class CopyAction extends Action {
		public void run() {
			getVexWidget().copySelection();
		}
	};

	private class CutAction extends Action {
		public void run() {
			getVexWidget().cutSelection();
		}
	}

	private class DeleteAction extends Action {
		public void run() {
			getVexWidget().deleteSelection();
		}
	};

	private class PasteAction extends Action {
		public void run() {
			try {
				getVexWidget().paste();
			} catch (DocumentValidationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	private class SelectAllAction extends Action {
		public void run() {
			getVexWidget().selectAll();
		}
	};

	private class RedoAction extends Action {
		public void run() {
			if (getVexWidget().canRedo()) {
				getVexWidget().redo();
			}
		}
	};

	private class UndoAction extends Action {
		public void run() {
			if (getVexWidget().canUndo()) {
				getVexWidget().undo();
			}
		}
	}

}
