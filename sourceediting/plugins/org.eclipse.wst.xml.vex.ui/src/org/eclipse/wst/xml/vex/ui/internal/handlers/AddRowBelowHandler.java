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
 * Inserts one or more table rows below the currently selected one(s). If more
 * than one row is selected the same number of new rows will be created.
 *
 * @see AbstractAddRowHandler
 * @see AddRowAboveHandler
 */
public class AddRowBelowHandler extends AbstractAddRowHandler {

    @Override
    protected boolean addAbove() {
        return false;
    }

}
