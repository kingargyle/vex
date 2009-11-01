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
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.texteditor.FindNextAction;
import org.eclipse.ui.texteditor.FindReplaceAction;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;
import org.eclipse.wst.xml.ui.internal.tabletree.XMLMultiPageEditorActionBarContributor;
import org.eclipse.wst.xml.vex.core.internal.dom.DocumentValidationException;
import org.eclipse.wst.xml.vex.core.internal.widget.IVexWidget;
import org.eclipse.wst.xml.vex.ui.internal.action.ActionUtils;
import org.eclipse.wst.xml.vex.ui.internal.action.DeleteColumnAction;
import org.eclipse.wst.xml.vex.ui.internal.action.DeleteRowAction;
import org.eclipse.wst.xml.vex.ui.internal.action.InsertColumnAfterAction;
import org.eclipse.wst.xml.vex.ui.internal.action.InsertColumnBeforeAction;
import org.eclipse.wst.xml.vex.ui.internal.action.InsertElementAction;
import org.eclipse.wst.xml.vex.ui.internal.action.InsertRowAboveAction;
import org.eclipse.wst.xml.vex.ui.internal.action.InsertRowBelowAction;
import org.eclipse.wst.xml.vex.ui.internal.action.MoveColumnLeftAction;
import org.eclipse.wst.xml.vex.ui.internal.action.MoveColumnRightAction;
import org.eclipse.wst.xml.vex.ui.internal.action.MoveRowDownAction;
import org.eclipse.wst.xml.vex.ui.internal.action.MoveRowUpAction;
import org.eclipse.wst.xml.vex.ui.internal.action.VexActionAdapter;
import org.eclipse.wst.xml.vex.ui.internal.config.Style;
import org.eclipse.wst.xml.vex.ui.internal.swt.VexWidget;

/**
 * Contribute actions on behalf of the VexEditor.
 */
public class VexActionBarContributor extends XMLMultiPageEditorActionBarContributor {

	public void dispose() {
	}

	public MenuManager getContextMenuManager() {
		return this.contextMenuManager;
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

		this.contextMenuManager = new MenuManager();
		this.contextMenuManager.setRemoveAllWhenShown(true);
		this.contextMenuManager.addMenuListener(this.contextMenuListener);

		page.addSelectionListener(this.selectionListener);

		this.globalCopyAction = ActionFactory.COPY.create(page
				.getWorkbenchWindow());
		this.globalCutAction = ActionFactory.CUT.create(page
				.getWorkbenchWindow());
		this.globalDeleteAction = ActionFactory.DELETE.create(page
				.getWorkbenchWindow());
		this.globalPasteAction = ActionFactory.PASTE.create(page
				.getWorkbenchWindow());
		this.globalRedoAction = ActionFactory.REDO.create(page
				.getWorkbenchWindow());
		this.globalUndoAction = ActionFactory.UNDO.create(page
				.getWorkbenchWindow());
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

	private IAction globalCopyAction;
	private IAction globalCutAction;
	private IAction globalDeleteAction;
	private IAction globalPasteAction;
	private IAction globalRedoAction;
	private IAction globalUndoAction;

	private IAction copyAction = new CopyAction();
	private IAction cutAction = new CutAction();
	private IAction deleteAction = new DeleteAction();
	private IAction pasteAction = new PasteAction();
	private IAction redoAction = new RedoAction();
	private IAction selectAllAction = new SelectAllAction();
	private IAction undoAction = new UndoAction();

	private MenuManager contextMenuManager;
	private IMenuListener contextMenuListener = new IMenuListener() {

		public void menuAboutToShow(IMenuManager manager) {

			boolean showTableActions = false;
			IVexWidget vexWidget = getVexWidget();
			if (vexWidget != null) {
				showTableActions = ActionUtils.getSelectedTableRows(vexWidget)
						.getRows() != null;
			}

			manager.add(globalUndoAction);
			manager.add(globalRedoAction);
			manager.add(new Separator());
			manager.add(new VexActionAdapter(getVexEditor(),
					new InsertElementAction()));

			if (showTableActions) {
				manager.add(new Separator());
				manager.add(new RowMenuManager());
				manager.add(new ColumnMenuManager());
			}

			manager.add(new Separator());
			manager.add(globalCutAction);
			manager.add(globalCopyAction);
			manager.add(globalPasteAction);
			manager.add(new Separator());
			manager.add(globalDeleteAction);
			manager.add(new Separator());
			manager.add(new StyleMenuManager());
			manager
					.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		}

	};

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

	private class SetStyleAction extends Action {
		public SetStyleAction(Style style) {
			super(style.getName(), IAction.AS_RADIO_BUTTON);
			this.style = style;
		}

		public void run() {
			getVexEditor().setStyle(style);
		}

		private Style style;
	}

	private class StyleMenuManager extends MenuManager {
		public StyleMenuManager() {
			super(Messages.getString("VexActionBarContributor.styleMenu.name")); //$NON-NLS-1$
			final Action noItemsAction = new Action(Messages
					.getString("VexActionBarContributor.noValidItems")) {public void run() {}}; //$NON-NLS-1$
			noItemsAction.setEnabled(false);
			noItemsAction.setChecked(true);
			this.add(noItemsAction);
			this.addMenuListener(new IMenuListener() {
				public void menuAboutToShow(IMenuManager manager) {
					manager.removeAll();
					String publicId = getVexWidget().getDocument()
							.getPublicID();
					Style[] styles = Style.getStylesForDoctype(publicId);
					for (int i = 0; i < styles.length; i++) {
						Action action = new SetStyleAction(styles[i]);
						if (styles[i] == getVexEditor().getStyle()) {
							action.setChecked(true);
						}
						manager.add(action);
					}
				}
			});
		}
	}

	private class RowMenuManager extends MenuManager {
		public RowMenuManager() {
			super(Messages.getString("VexActionBarContributor.rowMenu.name")); //$NON-NLS-1$
			this.add(new VexActionAdapter(getVexEditor(),
					new InsertRowAboveAction()));
			this.add(new VexActionAdapter(getVexEditor(),
					new InsertRowBelowAction()));
			this
					.add(new VexActionAdapter(getVexEditor(),
							new DeleteRowAction()));
			this
					.add(new VexActionAdapter(getVexEditor(),
							new MoveRowUpAction()));
			this.add(new VexActionAdapter(getVexEditor(),
					new MoveRowDownAction()));
		}
	}

	private class ColumnMenuManager extends MenuManager {
		public ColumnMenuManager() {
			super(Messages.getString("VexActionBarContributor.columnMenu.name")); //$NON-NLS-1$
			this.add(new VexActionAdapter(getVexEditor(),
					new InsertColumnBeforeAction()));
			this.add(new VexActionAdapter(getVexEditor(),
					new InsertColumnAfterAction()));
			this.add(new VexActionAdapter(getVexEditor(),
					new DeleteColumnAction()));
			this.add(new VexActionAdapter(getVexEditor(),
					new MoveColumnLeftAction()));
			this.add(new VexActionAdapter(getVexEditor(),
					new MoveColumnRightAction()));
		}
	}

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
