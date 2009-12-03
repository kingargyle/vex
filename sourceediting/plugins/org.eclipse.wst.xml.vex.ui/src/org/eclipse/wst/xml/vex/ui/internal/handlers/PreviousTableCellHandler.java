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
 * Navigates to the previous table cell (usual shortcut: {@code Shift+Tab}).
 *
 * @see NextTableCellHandler
 */
public class PreviousTableCellHandler extends AbstractNavigateTableCellHandler {

    @Override
    protected void navigate(VexWidget widget, TableRowBox row, int offset) {
        Box[] cells = row.getChildren();

        // in this row
        for (int i = cells.length - 1; i >= 0; i--) {
            if (cells[i].getEndOffset() < offset) {
                widget.moveTo(cells[i].getStartOffset());
                widget.moveTo(cells[i].getEndOffset(), true);
                return;
            }
        }

        // in other row
        Box[] rows = row.getParent().getChildren();
        for (int i = rows.length - 1; i >= 0; i--) {
            if (rows[i].getEndOffset() < offset) {
                cells = rows[i].getChildren();
                if (cells.length > 0) {
                    Box cell = cells[cells.length - 1];
                    widget.moveTo(cell.getStartOffset());
                    widget.moveTo(cell.getEndOffset(), true);
                }
                return;
            }
        }
    }

}
