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

import org.eclipse.wst.xml.vex.ui.internal.swt.VexWidget;

/**
 * Deletes selected row(s).
 */
public class RemoveRowHandler extends AbstractRemoveTableCellsHandler {

    protected List<Object> collectCellsToDelete(VexWidget widget,
                                                VexHandlerUtil.RowColumnInfo rcInfo) {
        return VexHandlerUtil.getSelectedTableRows(widget).getRows();
    }

}
