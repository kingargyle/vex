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

import java.util.List;

import org.eclipse.wst.xml.vex.ui.internal.handlers.VexHandlerUtil.SelectedRows;

/**
 * Moves the current table row up above its previous sibling.
 *
 * @see AbstractMoveRowHandler
 * @see MoveRowDownHandler
 */
public class MoveRowUpHandler extends AbstractMoveRowHandler {

    @Override
    protected Object targetRow(SelectedRows selected) {
        return selected.getRowBefore();
    }

    @Override
    protected int target(SelectedRows selected) {
        List rows = selected.getRows();
        Object lastRow = rows.get(rows.size() - 1);
        return VexHandlerUtil.getOuterRange(lastRow).getEnd();
    }

}
