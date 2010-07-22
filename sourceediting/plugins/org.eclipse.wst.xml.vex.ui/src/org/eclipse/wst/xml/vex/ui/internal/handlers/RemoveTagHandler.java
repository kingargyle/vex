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

import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.menus.UIElement;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocumentFragment;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement;
import org.eclipse.wst.xml.vex.ui.internal.swt.VexWidget;

/**
 * Removes the current tag: deletes the element but adds its content to the
 * parent element.
 */
public class RemoveTagHandler extends AbstractVexWidgetHandler implements
        IElementUpdater {

    /** ID of the corresponding remove element command. */
    public static final String COMMAND_ID =
        "org.eclipse.wst.xml.vex.ui.RemoveTagCommand"; //$NON-NLS-1$

    /**
     * The message ID of the command label which is in window scope displayed
     * in the 'Remove' menu only.
     */
    private static final String WINDOW_SCOPE_DYNAMIC_LABEL_ID =
        "command.removeTag.inRemoveMenu.dynamicName"; //$NON-NLS-1$

    /**
     * The message ID of the command label which is in partsite scope displayed
     * in the context menu.
     */
    private static final String PARTSITE_SCOPE_DYNAMIC_LABEL_ID =
        "command.removeTag.dynamicName"; //$NON-NLS-1$

    @Override
    public void execute(final VexWidget widget) throws ExecutionException {
        widget.doWork(new Runnable() {
            public void run() {
                VEXElement element = widget.getDocument().getElementAt(
                        widget.getCaretOffset());
                widget.moveTo(element.getStartOffset() + 1, false);
                widget.moveTo(element.getEndOffset(), true);
                final VEXDocumentFragment frag = widget.getSelectedFragment();
                widget.deleteSelection();
                widget.moveBy(-1, false);
                widget.moveBy(2, true);
                widget.deleteSelection();
                if (frag != null)
                	widget.insertFragment(frag);
            }
        });
    }

    public void updateElement(UIElement element, Map parameters) {
        updateElement(element,
                      parameters,
                      WINDOW_SCOPE_DYNAMIC_LABEL_ID,
                      PARTSITE_SCOPE_DYNAMIC_LABEL_ID);
    }

}
