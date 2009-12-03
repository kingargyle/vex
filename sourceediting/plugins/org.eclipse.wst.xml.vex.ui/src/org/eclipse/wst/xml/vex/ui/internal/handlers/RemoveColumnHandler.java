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

import org.eclipse.wst.xml.vex.ui.internal.swt.VexWidget;

/**
 * Deletes current column.
 */
public class RemoveColumnHandler extends AbstractRemoveTableCellsHandler {

    protected List<Object> collectCellsToDelete(final VexWidget widget,
                                                final VexHandlerUtil.RowColumnInfo rcInfo) {
        final List<Object> cellsToDelete = new ArrayList<Object>();
        VexHandlerUtil.iterateTableCells(widget, new TableCellCallbackAdapter() {
            public void onCell(Object row,
                               Object cell,
                               int rowIndex,
                               int cellIndex) {
                if (cellIndex == rcInfo.cellIndex) {
                    cellsToDelete.add(cell);
                }
            }
        });
        return cellsToDelete;
    }

}
