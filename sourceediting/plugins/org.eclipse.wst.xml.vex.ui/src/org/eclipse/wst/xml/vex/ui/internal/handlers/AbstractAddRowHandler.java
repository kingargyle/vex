/*******************************************************************************
 * Copyright (c) 2004, 2008 John Krasnay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     John Krasnay - initial API and implementation
 *     Igor Jacy Lino Campista - Java 5 warnings fixed (bug 311325)
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
    	final List<RowCells> rowCellsToInsert = new ArrayList<RowCells>();

        VexHandlerUtil.iterateTableCells(widget, new ITableCellCallback() {

            private boolean selectedRow;
            private List<Object> cellsToInsert;

            public void startRow(Object row, int rowIndex) {
                selectedRow =
                	VexHandlerUtil.elementOrRangeIsPartiallySelected(widget,
                			                                         row);

                if (selectedRow) {
                    cellsToInsert = new ArrayList<Object>();
                }
            }

            public void onCell(Object row, Object cell,
                    int rowIndex, int cellIndex) {
                if (selectedRow) {
                    cellsToInsert.add(cell);
                }
            }

            public void endRow(Object row, int rowIndex) {
                if (selectedRow) {
                	rowCellsToInsert.add(new RowCells(row, cellsToInsert));
                }
            }

        });

        // something to do?
        if (rowCellsToInsert.isEmpty()) return;

        // save the caret offset to return inside the first table cell after
        // row has been added
        RowCells firstRow = rowCellsToInsert.get(0);
        int outerOffset = VexHandlerUtil.getOuterRange(firstRow.row).getStart();
        Object firstInner = firstRow.cells.isEmpty()
                            ? firstRow.row
                            : firstRow.cells.get(0);
        int innerOffset = VexHandlerUtil.getInnerRange(firstInner).getStart();
        int insertOffset = addAbove()
            ? VexHandlerUtil.getOuterRange(firstRow.row).getStart()
            : VexHandlerUtil.getOuterRange(rowCellsToInsert.get(rowCellsToInsert.size() - 1).row).getEnd();

        // (innerOffset - outerOffset) represents the final offset of
        // the caret, relative to the insertion point of the new rows
        int finalOffset = insertOffset + (innerOffset - outerOffset);
        widget.moveTo(insertOffset);
        
        for (RowCells rowCells : rowCellsToInsert) {
            if (rowCells.row instanceof Element) {
                widget.insertElement((Element) ((VEXElement) rowCells.row).clone());
            }

            //cells that are to be inserted.
            for (Object cell : rowCells.cells) {
            	if (cell instanceof Element) {
                    widget.insertElement((Element) ((VEXElement) cell).clone());
                    widget.moveBy(+1);
                } else {
                    widget.insertText(" ");
                }
			}

            if (rowCells.row instanceof Element) {
                widget.moveBy(+1);
            }
		}
        
        // move inside first inserted table cell
        widget.moveTo(finalOffset);
    }
    
    /** Represents a row and its cells. */
    private static class RowCells {
    	
    	/** The row. */
    	private final Object row;
    	
    	/** All cell objects that belong to this row.*/
    	private final List<Object> cells;
    	
		private RowCells(Object row, List<Object> cells) {
			this.row = row;
			this.cells = cells;
		}
		
    }
        
}
