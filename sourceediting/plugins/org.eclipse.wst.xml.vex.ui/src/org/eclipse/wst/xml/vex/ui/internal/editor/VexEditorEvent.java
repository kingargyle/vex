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
package org.eclipse.wst.xml.vex.ui.internal.editor;

import java.util.EventObject;

/**
 * Event object published through the IVexEditorListener interface.
 */
public class VexEditorEvent extends EventObject {

    /**
     * Class constructor.
     * @param source VexEditor that originated this event.
     */
    public VexEditorEvent(VexEditor source) {
        super(source);
    }
    
    /**
     * Returns the VexEditor that originated this event.
     */
    public VexEditor getVexEditor() {
        return (VexEditor) this.getSource();
    }
}
