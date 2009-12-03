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
 * Inserts a single table column before (left of) the current one.
 *
 * @see AbstractAddColumnHandler
 * @see AddColumnRightHandler
 */
public class AddColumnLeftHandler extends AbstractAddColumnHandler {

    @Override
    protected boolean addBefore() {
        return true;
    }

}
