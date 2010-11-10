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
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement;
import org.w3c.css.sac.LexicalUnit;

/**
 * A property that represents lengths, such as a margin or padding.
 */
public class LengthProperty extends AbstractProperty {

	private final Axis axis;

	public LengthProperty(final String name, final Axis axis) {
		super(name);
		this.axis = axis;
	}

	public Object calculate(final LexicalUnit lu, final Styles parentStyles, final Styles styles, final VEXElement element) {
		final int ppi = getPpi();

		if (isLength(lu)) {
			final int length = getIntLength(lu, styles.getFontSize(), ppi);
			return RelativeLength.createAbsolute(length);
		} else if (isPercentage(lu))
			return RelativeLength.createRelative(lu.getFloatValue() / 100);
		else if (isInherit(lu) && parentStyles != null)
			return parentStyles.get(getName());
		else
			// not specified, "auto", or other unknown value
			return RelativeLength.createAbsolute(0);
	}

	private int getPpi() {
		final DisplayDevice device = DisplayDevice.getCurrent();
		final int ppi = axis == Axis.HORIZONTAL ? device.getHorizontalPPI() : device.getVerticalPPI();
		return ppi;
	}
}
