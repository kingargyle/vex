/*******************************************************************************
 * Copyright (c) 2009 Holger Voormann and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Holger Voormann - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.ui.internal.handlers;

import java.text.MessageFormat;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.menus.UIElement;
import org.eclipse.ui.services.IServiceScopes;
import org.eclipse.wst.xml.vex.ui.internal.editor.Messages;
import org.eclipse.wst.xml.vex.ui.internal.swt.VexWidget;

/**
 * Abstract supper class of all command handlers which can be performed on a
 * {@link VexWidget}.
 *
 * @see IVexWidgetHandler
 */
public abstract class AbstractVexWidgetHandler extends AbstractHandler
        implements IVexWidgetHandler {

    public Object execute(ExecutionEvent event) throws ExecutionException {
        execute(VexHandlerUtil.computeWidget(event));
        return null;
    }

    public abstract void execute(VexWidget widget) throws ExecutionException;

    /**
     * Helper method to implement
     * {@link org.eclipse.ui.commands.IElementUpdater}: Updates the name of the
     * UI element with the specified message where <code>{0}</code> is replaced
     * with the name of the current element.
     *
     * @param element the UI element to be update
     * @param parameters parameters containing the workbench window of the UI
     *                   element
     * @param dynamicLabelId the ID of the message where <code>{0}</code> is
     *                       replaced with the name of the current element
     */
    public void updateElement(UIElement element,
                              Map parameters,
                              String windowScopeDynamicLabelId,
                              String partsiteScopeDynamicLabelId) {
        Object windowObject = parameters.get(IServiceScopes.WINDOW_SCOPE);
        if (!(windowObject instanceof IWorkbenchWindow)) return;

        IWorkbenchWindow window = (IWorkbenchWindow) windowObject;
        VexWidget widget = VexHandlerUtil.computeWidget(window);
        if (widget == null) return;

        String name = widget.getCurrentElement().getName();
        String dynamicLabelId =
            parameters.containsKey(IServiceScopes.PARTSITE_SCOPE)
            ? partsiteScopeDynamicLabelId
            : windowScopeDynamicLabelId;
        String message = Messages.getString(dynamicLabelId);
        element.setText(MessageFormat.format(message, name));
    }

}
