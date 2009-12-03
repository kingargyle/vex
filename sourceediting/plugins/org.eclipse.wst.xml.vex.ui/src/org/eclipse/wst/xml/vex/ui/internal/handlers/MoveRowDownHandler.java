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

import org.eclipse.wst.xml.vex.ui.internal.handlers.VexHandlerUtil.SelectedRows;

/**
 * Moves the current table row down below its next sibling.
 *
 * @see AbstractMoveRowHandler
 * @see MoveRowUpHandler
 */
public class MoveRowDownHandler extends AbstractMoveRowHandler {

    @Override
    protected Object targetRow(SelectedRows selected) {
        return selected.getRowAfter();
    }

    @Override
    protected int target(SelectedRows selected) {
        Object firstRow = selected.getRows().get(0);
        return VexHandlerUtil.getOuterRange(firstRow).getStart();
    }

}
