/*******************************************************************************
 * Copyright (c) 2010 Florian Thienel and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 		Florian Thienel - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.ui.internal.swt;

import org.eclipse.wst.xml.vex.core.internal.core.Image;

/**
 * @author Florian Thienel
 */
public class SwtImage implements Image {

	public final org.eclipse.swt.graphics.Image image;

	public SwtImage(org.eclipse.swt.graphics.Image image) {
		this.image = image;
	}

	public int getHeight() {
		return image.getBounds().height;
	}

	public int getWidth() {
		return image.getBounds().width;
	}
}
