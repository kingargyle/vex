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
import org.eclipse.wst.xml.vex.core.internal.layout.Box;
import org.eclipse.wst.xml.vex.core.internal.layout.TableRowBox;
import org.eclipse.wst.xml.vex.core.internal.widget.IBoxFilter;
import org.eclipse.wst.xml.vex.ui.internal.swt.VexWidget;

/**
 * Navigates either to the next or previous table cell (usual shortcut: {@code
 * Tab} or {@code Shift+Tab}).
 *
 * @see PreviousTableCellHandler
 */
public abstract class AbstractNavigateTableCellHandler extends
        AbstractVexWidgetHandler {

    @Override
    public void execute(VexWidget widget) throws ExecutionException {
        IBoxFilter filter = new IBoxFilter() {
            public boolean matches(Box box) {
                return box instanceof TableRowBox;
            }
        };
        TableRowBox row = (TableRowBox) widget.findInnermostBox(filter);

        // not in a table row?
        if (row == null) return;

        int offset = widget.getCaretOffset();
        navigate(widget, row, offset);
    }

    /**
     * Navigates either to the next or previous table cell.
     *
     * @param widget the Vex widget containing the document
     * @param row the current row
     * @param offset the current offset
     */
    protected abstract void navigate(VexWidget widget,
                                     TableRowBox row,
                                     int offset);

}
