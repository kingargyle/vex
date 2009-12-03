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

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.wst.xml.vex.ui.internal.swt.VexWidget;

/**
 * Interface implemented by handler objects that can act on a {@link VexWidget}.
 */
public interface IVexWidgetHandler {

    /**
     * Executes handler at the specified {@link VexWidget}.
     *
     * @param event the {@link VexWidget} at which to execute handler
     * @throws ExecutionException if an exception occurred during execution
     */
    void execute(VexWidget widget) throws ExecutionException;

}
