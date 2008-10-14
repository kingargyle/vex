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

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.wst.xml.vex.core.internal.widget.IVexWidget;
import org.eclipse.wst.xml.vex.ui.internal.VexPlugin;
import org.eclipse.wst.xml.vex.ui.internal.action.ChangeElementAction;
import org.eclipse.wst.xml.vex.ui.internal.action.IVexAction;
import org.eclipse.wst.xml.vex.ui.internal.action.RemoveElementAction;
import org.eclipse.wst.xml.vex.ui.internal.swt.VexWidget;

/**
 * An IWorkbenchWindowActionDelegate that defers to to an instance of
 * IVexAction. The IDs of actions in plugin.xml using this delegate must be the
 * classnames of actions implementing IVexAction. Such classes must have a
 * no-args constructor.
 */
public class VexActionDelegate implements IWorkbenchWindowActionDelegate {

	private static Map actions = new HashMap();

	public VexActionDelegate() {
	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	public void run(IAction action) {
		IVexAction vexAction = getAction(action.getId());
		if (vexAction == null) {
			return;
		}

		VexWidget vexWidget = this.getVexWidget();
		if (vexWidget != null) {
			vexWidget.setFocus();
			vexAction.run(vexWidget);
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {

		IVexAction vexAction = getAction(action.getId());
		boolean enabled;

		if (vexAction == null) {
			enabled = false;
		} else {
			IVexWidget vexWidget = this.getVexWidget();
			if (vexWidget == null) {
				enabled = false;
			} else {
				enabled = vexAction.isEnabled(vexWidget);

				if (action.getId().equals(ChangeElementAction.class.getName())) {
					String elementName = vexWidget.getCurrentElement()
							.getName();
					String message = Messages
							.getString("ChangeElementAction.dynamic.label"); //$NON-NLS-1$
					action.setText(MessageFormat.format(message,
							new Object[] { elementName }));
				}

				if (action.getId().equals(RemoveElementAction.class.getName())) {
					String elementName = vexWidget.getCurrentElement()
							.getName();
					String message = Messages
							.getString("RemoveElementAction.dynamic.label"); //$NON-NLS-1$
					action.setText(MessageFormat.format(message,
							new Object[] { elementName }));
				}
			}
		}

		action.setEnabled(enabled);
	}

	private IWorkbenchWindow window;

	private VexWidget getVexWidget() {
		IEditorPart editor = this.window.getActivePage().getActiveEditor();
		if (editor != null && editor instanceof VexEditor) {
			return ((VexEditor) editor).getVexWidget();
		} else {
			return null;
		}
	}

	private static IVexAction getAction(String actionId) {
		try {
			IVexAction action = (IVexAction) actions.get(actionId);
			if (action == null) {
				action = (IVexAction) Class.forName(actionId).newInstance();
				actions.put(actionId, action);
			}
			return action;
		} catch (Exception ex) {
			String message = MessageFormat.format(Messages
					.getString("VexActionDelegate.errorLoadingAction"), //$NON-NLS-1$
					new Object[] { actionId, ex.getMessage() });
			VexPlugin.getInstance().log(IStatus.ERROR, message, ex);
			return null;
		}
	}
}
