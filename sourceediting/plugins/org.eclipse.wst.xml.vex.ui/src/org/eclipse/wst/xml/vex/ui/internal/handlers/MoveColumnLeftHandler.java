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

/**
 * Moves the current table column to the left.
 *
 * @see AbstractMoveColumnHandler
 * @see MoveColumnRightHandler
 */
public class MoveColumnLeftHandler extends AbstractMoveColumnHandler {

    @Override
    protected boolean moveRight() {
        return false;
    }

    @Override
    protected boolean movingPossible(VexHandlerUtil.RowColumnInfo rcInfo) {
        return rcInfo.cellIndex >= 1;
    }

}
