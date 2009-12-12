/*******************************************************************************
 * Copyright (c) 2004, 2008 John Krasnay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     John Krasnay - initial implementation
 *     Holger Voormann
 *******************************************************************************/
package org.eclipse.wst.xml.vex.ui.internal;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wst.xml.vex.ui.internal.swt.VexWidget;

/**
 * Content assist dialog that is popped up to show a list of actions to select
 * from. The input field above the list could be used to filter the content.
 */
public class ContentAssist extends PopupDialog {

	private static final String SETTINGS_SECTION =
		"contentAssistant"; //$NON-NLS-1$

	private final Point location;
	private Text textWidget;
	private Tree treeWidget;
	private IAction[] actions;

	/**
	 * Constructs a new content assist dialog which can be opened by
	 * {@link #open()}.
	 *
	 * @param vexWidget the vex widget this content assist belongs to
	 * @param actions list of actions to select from
	 * @param title the title of the dialog (e.g. "Add Element" or
	 *              "Change 'xyz' To")
	 */
	public ContentAssist(VexWidget vexWidget, IAction[] actions, String title) {
		super(vexWidget.getShell(),
              SWT.RESIZE,
              true,  // take focus on open
              true,  // persist size
              false, // persist location
              false, // show dialog menu
              false, // show persist actions
              title,
              null);
		this.actions = actions;
		location = vexWidget.toDisplay(vexWidget.getLocationForContentAssist());
	}

	@Override
	protected IDialogSettings getDialogSettings() {
		IDialogSettings root = VexPlugin.getInstance().getDialogSettings();
		IDialogSettings settings = root.getSection(SETTINGS_SECTION);
		if (settings == null) {
			settings = root.addNewSection(SETTINGS_SECTION);
		}
		return settings;
	}

	@Override
	protected Color getBackground() {
		String colorId = JFacePreferences.CONTENT_ASSIST_BACKGROUND_COLOR;
		return JFaceResources.getColorRegistry().get(colorId);
	}

	@Override
	protected Control createDialogArea(Composite parent) {

		// dialog area panel
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(composite);

		// 1. input field
		textWidget = new Text(composite, SWT.SINGLE);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(textWidget);
		textWidget.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				repopulateList();
			}
		});
		textWidget.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.CR) {
					doAction();
				} else if (   e.widget == textWidget
						   && e.keyCode == SWT.ARROW_DOWN) {
					treeWidget.setFocus();
				}
			}
		});

		// 2. separator
		int separatorStyle = SWT.SEPARATOR | SWT.HORIZONTAL | SWT.LINE_DOT;
		Label separator = new Label(composite, separatorStyle);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(separator);

		// 3. tree
		treeWidget = new Tree(composite, SWT.SINGLE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(treeWidget);
		treeWidget.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				doAction();
			}
		});
		treeWidget.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (e.button == 1) {
					doAction();
				}
			}
		});

		// fill with content
		repopulateList();

		return composite;
	}

	@Override
	protected Point getDefaultLocation(Point initialSize) {
		return location;
	}

	/**
	 * Perform the action that is currently selected in the tree view, if any,
	 * and close the dialog.
	 */
	private void doAction() {
		TreeItem[] items = treeWidget.getSelection();
		if (items.length > 0) {
			IAction action = (IAction) items[0].getData();
			action.run();
		}
		close();
	}

	private void repopulateList() {
		String prefix = textWidget.getText();
		treeWidget.removeAll();
		TreeItem first = null;
		for (int i = 0; i < actions.length; i++) {
			IAction action = actions[i];
			if (action.getText().startsWith(prefix)) {
				TreeItem item = new TreeItem(treeWidget, SWT.NONE);
				if (first == null) {
					first = item;
				}
				item.setData(action);
				item.setText(action.getText());
			}
		}
		if (first != null) {
			treeWidget.setSelection(new TreeItem[] { first });
		}
	}

}
