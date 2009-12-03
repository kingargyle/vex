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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.wst.xml.vex.core.internal.dom.Element;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement;
import org.eclipse.wst.xml.vex.ui.internal.swt.VexWidget;

/**
 * Inserts one or more table rows either above or below the currently selected
 * one(s). If more than one row is selected the same number of new rows will be
 * created.
 *
 * @see AddRowBelowHandler
 * @see AddRowAboveHandler
 */
public abstract class AbstractAddRowHandler extends AbstractVexWidgetHandler {

    @Override
    public void execute(final VexWidget widget) throws ExecutionException {
        widget.doWork(new Runnable() {
            public void run() {
                addRow(widget);
            }
        });
    }

    /**
     * @return {@code true} to add new table row above current row or
     *         {@code false} to add new row below current row
     */
    protected abstract boolean addAbove();

    private void addRow(final VexWidget widget) {
        final List<Object> rowsToInsert = new ArrayList<Object>();
        final List<Object> rowCellsToInsert = new ArrayList<Object>();

        VexHandlerUtil.iterateTableCells(widget, new ITableCellCallback() {

            private boolean rowSelected;
            private List<Object> cellsToInsert;

            public void startRow(Object row, int rowIndex) {
                rowSelected = VexHandlerUtil
                .elementOrRangeIsPartiallySelected(
                        widget, row);

                if (rowSelected) {
                    cellsToInsert = new ArrayList<Object>();
                }
            }

            public void onCell(Object row, Object cell,
                    int rowIndex, int cellIndex) {
                if (rowSelected) {
                    cellsToInsert.add(cell);
                }
            }

            public void endRow(Object row, int rowIndex) {
                if (rowSelected) {
                    rowsToInsert.add(row);
                    rowCellsToInsert.add(cellsToInsert);
                }
            }

        });

        if (rowsToInsert.size() == 0) {
            return;
        }

        //
        // save the caret offset so that we return just inside the first
        // table cell
        //
        // (innerOffset - outerOffset) represents the final offset of
        // the caret, relative to the insertion point of the new rows
        //
        int outerOffset = VexHandlerUtil.getOuterRange(rowsToInsert.get(0)).getStart();
        List firstCells = (List) rowCellsToInsert.get(0);
        Object firstInner = firstCells.isEmpty()
                            ? rowsToInsert.get(0)
                            : firstCells.get(0);
        int innerOffset = VexHandlerUtil.getInnerRange(firstInner).getStart();

        int insertOffset = addAbove()
            ? VexHandlerUtil.getOuterRange(rowsToInsert.get(0)).getStart()
            : VexHandlerUtil.getOuterRange(rowsToInsert.get(rowsToInsert.size() - 1)).getEnd();

        int finalOffset = insertOffset + (innerOffset - outerOffset);

        widget.moveTo(insertOffset);

        for (int i = 0; i < rowsToInsert.size(); i++) {

            Object row = rowsToInsert.get(i);

            if (row instanceof Element) {
                widget.insertElement((Element) ((VEXElement) row)
                        .clone());
            }

            List cellsToInsert = (List) rowCellsToInsert.get(i);
            for (int j = 0; j < cellsToInsert.size(); j++) {
                Object cell = cellsToInsert.get(j);
                if (cell instanceof Element) {
                    widget.insertElement((Element) ((VEXElement) cell)
                            .clone());
                    widget.moveBy(+1);
                } else {
                    widget.insertText(" ");
                }
            }

            if (row instanceof Element) {
                widget.moveBy(+1);
            }

        }

        widget.moveTo(finalOffset);
    }

}
