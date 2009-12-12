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
package org.eclipse.wst.xml.vex.ui.internal.handlers;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.IAction;
import org.eclipse.wst.xml.vex.ui.internal.ContentAssist;
import org.eclipse.wst.xml.vex.ui.internal.editor.Messages;
import org.eclipse.wst.xml.vex.ui.internal.swt.VexWidget;

/**
 * Shows the content assist to add a new element ({@link InsertAssistant}).
 */
public class AddElementHandler extends AbstractVexWidgetHandler {

	@Override
    public void execute(VexWidget widget) throws ExecutionException {
		String title = Messages.getString("dialog.addElement.title"); //$NON-NLS-1$
		IAction[] actions = widget.getValidInsertActions();
		ContentAssist assist = new ContentAssist(widget, actions, title);
		assist.open();
    }

}
