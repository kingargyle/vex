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
package org.eclipse.wst.xml.vex.core.internal.css;

import org.eclipse.wst.xml.vex.core.internal.core.DisplayDevice;

public class TestDisplayDevice extends DisplayDevice {

    private int horizontalPPI;
    private int verticalPPI;

    public TestDisplayDevice(int horizontalPPI, int verticalPPI) {
        this.horizontalPPI = horizontalPPI;
        this.verticalPPI = verticalPPI;
    }

    public int getHorizontalPPI() {
        return this.horizontalPPI;
    }

    /**
     *
     */

    public int getVerticalPPI() {
        return this.verticalPPI;
    }

}
