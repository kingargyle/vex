/*******************************************************************************
 * Copyright (c) 2010, Florian Thienel and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Florian Thienel - initial implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.widget;

import org.eclipse.wst.xml.vex.core.internal.core.Graphics;
import org.eclipse.wst.xml.vex.core.internal.core.Rectangle;
import org.eclipse.wst.xml.vex.core.internal.layout.FakeGraphics;

public class MockHostComponent implements HostComponent {

	public Graphics createDefaultGraphics() {
		return new FakeGraphics();
	}

	public void fireSelectionChanged() {
	}

	public Rectangle getViewport() {
		return new Rectangle(0, 0, 0, 0);
	}

	public void invokeLater(Runnable runnable) {
	}

	public void repaint() {
	}

	public void repaint(int x, int y, int width, int height) {
	}

	public void scrollTo(int left, int top) {
	}

	public void setPreferredSize(int width, int height) {
	}

}
