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

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.wst.xml.vex.core.internal.core.IntRange;
import org.eclipse.wst.xml.vex.ui.internal.swt.VexWidget;

/**
 * Deletes a given list of table cells (see
 * {@link #collectCellsToDelete(VexWidget, org.eclipse.wst.xml.vex.ui.internal.handlers.VexHandlerUtil.RowColumnInfo)}
 * ).
 *
 * @see RemoveColumnHandler
 * @see RemoveRowHandler
 */
public abstract class AbstractRemoveTableCellsHandler extends
        AbstractVexWidgetHandler {

    @Override
    public void execute(final VexWidget widget) throws ExecutionException {
        widget.doWork(new Runnable() {
            public void run() {

                final VexHandlerUtil.RowColumnInfo rcInfo =
                    VexHandlerUtil.getRowColumnInfo(widget);

                if (rcInfo == null) return;

                deleteCells(widget, collectCellsToDelete(widget, rcInfo));
            }


        });
    }

    /**
     * @param widget the Vex widget
     * @param rcInfo row/column info of the current table cell
     * @return list of elements to delete
     */
    protected abstract List<Object> collectCellsToDelete(VexWidget widget,
                                                         VexHandlerUtil.RowColumnInfo rcInfo);

    private void deleteCells(final VexWidget widget,
                             final List<Object> cellsToDelete) {
        // Iterate the deletions in reverse, so that we don't mess up offsets
        // that are in anonymous cells, which are not stored as Positions.
        for (int i = cellsToDelete.size() - 1; i >= 0; i--) {
            Object cell = cellsToDelete.get(i);
            IntRange range = VexHandlerUtil.getOuterRange(cell);
            widget.moveTo(range.getStart());
            widget.moveTo(range.getEnd(), true);
            widget.deleteSelection();
        }
    }

}
