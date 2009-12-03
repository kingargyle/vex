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
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.wst.xml.vex.core.internal.dom.Element;
import org.eclipse.wst.xml.vex.ui.internal.swt.VexWidget;

/**
 * Inserts a single table column before (left of) or after (right of) the
 * current one.
 *
 * @see AddColumnLeftHandler
 * @see AddColumnRightHandler
 */
public abstract class AbstractAddColumnHandler extends AbstractHandler {

    public Object execute(ExecutionEvent event) throws ExecutionException {
        final VexWidget widget = VexHandlerUtil.computeWidget(event);
        widget.doWork(new Runnable() {
            public void run() {
                try {
                    addColumn(widget);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return null;
    }

    private void addColumn(VexWidget widget) throws ExecutionException {
        final int indexToDup = VexHandlerUtil.getCurrentColumnIndex(widget);

        // adding possible?
        if (indexToDup == -1) return;

        final List cellsToDup = new ArrayList();
        VexHandlerUtil.iterateTableCells(widget, new TableCellCallbackAdapter() {
            public void onCell(Object row, Object cell, int rowIndex,
                    int cellIndex) {
                if (cellIndex == indexToDup && cell instanceof Element) {
                    cellsToDup.add(cell);
                }
            }
        });

        int finalOffset = -1;
        for (Iterator it = cellsToDup.iterator(); it.hasNext();) {
            Element element = (Element) it.next();
            if (finalOffset == -1) {
                finalOffset = element.getStartOffset() + 1;
            }
            widget.moveTo(addBefore()
                          ? element.getStartOffset()
                          : element.getEndOffset() + 1);
            widget.insertElement((Element) element.clone());
        }

        if (finalOffset != -1) {
            widget.moveTo(finalOffset);
        }
    }

    /**
     * @return {@code true} to add new column before (left of) current column or
     *         {@code false} to add new column after (right of) current column
     */
    protected abstract boolean addBefore();

}
