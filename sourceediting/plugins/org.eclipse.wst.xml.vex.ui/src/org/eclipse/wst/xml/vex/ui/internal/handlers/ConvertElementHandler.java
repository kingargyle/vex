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

import java.text.MessageFormat;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.menus.UIElement;
import org.eclipse.wst.xml.vex.ui.internal.ContentAssist;
import org.eclipse.wst.xml.vex.ui.internal.editor.Messages;
import org.eclipse.wst.xml.vex.ui.internal.swt.VexWidget;

/**
 * Shows the content assist to convert current element ({@link MorphAssistant}).
 */
public class ConvertElementHandler extends AbstractVexWidgetHandler implements IElementUpdater {

    /** ID of the corresponding convert element command. */
    public static final String COMMAND_ID =
        "org.eclipse.wst.xml.vex.ui.ConvertElementCommand"; //$NON-NLS-1$

    private static final String LABEL_ID =
        "command.convertElement.dynamicName"; //$NON-NLS-1$

    @Override
    public void execute(VexWidget widget) throws ExecutionException {

    	// compute title
		String message = Messages
				.getString("dialog.convertElement.dynamicTitle"); //$NON-NLS-1$
		String name = widget.getCurrentElement().getName();
		String title = MessageFormat.format(message, new Object[] { name });

        // show content assist
		IAction[] actions = widget.getValidMorphActions();
		ContentAssist assist = new ContentAssist(widget, actions, title);
		assist.open();
    }

    public void updateElement(UIElement element, Map parameters) {
        updateElement(element, parameters, LABEL_ID, LABEL_ID);
    }

}
