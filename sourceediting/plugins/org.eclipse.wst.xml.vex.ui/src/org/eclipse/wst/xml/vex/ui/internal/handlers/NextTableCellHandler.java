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

import org.eclipse.wst.xml.vex.core.internal.layout.Box;
import org.eclipse.wst.xml.vex.core.internal.layout.TableRowBox;
import org.eclipse.wst.xml.vex.ui.internal.swt.VexWidget;

/**
 * Navigates to the next table cell (usual shortcut: {@code Tab}).
 *
 * @see PreviousTableCellHandler
 */
public class NextTableCellHandler extends AbstractNavigateTableCellHandler {

    @Override
    protected void navigate(VexWidget widget, TableRowBox row, int offset) {
        Box[] cells = row.getChildren();

        // in this row
        for (int i = 0; i < cells.length; i++) {
            if (cells[i].getStartOffset() > offset) {
                widget.moveTo(cells[i].getStartOffset());
                widget.moveTo(cells[i].getEndOffset(), true);
                return;
            }
        }

        // in other row
        Box[] rows = row.getParent().getChildren();
        for (int i = 0; i < rows.length; i++) {
            if (rows[i].getStartOffset() > offset) {
                cells = rows[i].getChildren();
                if (cells.length > 0) {
                    Box cell = cells[0];
                    widget.moveTo(cell.getStartOffset());
                    widget.moveTo(cell.getEndOffset(), true);
                } else {
                    System.out.println("TODO - dup row into new empty row");
                }
                return;
            }
        }

        // We didn't find a "next row", so let's dup the current one
        VexHandlerUtil.duplicateTableRow(widget, row);

    }

}
