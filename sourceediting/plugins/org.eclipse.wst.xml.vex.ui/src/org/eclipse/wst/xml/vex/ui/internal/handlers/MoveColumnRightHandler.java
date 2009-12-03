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

import org.eclipse.wst.xml.vex.ui.internal.handlers.VexHandlerUtil.RowColumnInfo;

/**
 * Moves the current table column to the right.
 *
 * @see AbstractMoveColumnHandler
 * @see MoveColumnLeftHandler
 */
public class MoveColumnRightHandler extends AbstractMoveColumnHandler {

    @Override
    protected boolean moveRight() {
        return true;
    }

    @Override
    protected boolean movingPossible(RowColumnInfo rcInfo) {
        return rcInfo.cellIndex < rcInfo.maxColumnCount - 1;
    }

}
