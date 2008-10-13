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
package org.eclipse.wst.xml.vex.core.internal.layout;

import org.eclipse.wst.xml.vex.core.internal.dom.Element;
import org.eclipse.wst.xml.vex.core.internal.layout.BlockElementBox;
import org.eclipse.wst.xml.vex.core.internal.layout.Box;
import org.eclipse.wst.xml.vex.core.internal.layout.CssBoxFactory;
import org.eclipse.wst.xml.vex.core.internal.layout.LayoutContext;
import org.eclipse.wst.xml.vex.core.internal.layout.SpaceBox;

/**
 * A box factory that, for an element named &lt;space&gt;, returns a SpaceBox
 * with height and width given by attributes of those names, e.g. &lt;space
 * height="100" width="200"/&gt;
 */
public class MockBoxFactory extends CssBoxFactory {

	public Box createBox(LayoutContext context, Element element,
			BlockElementBox parent, int width) {

		if (element.getName().equals("space")) {
			int w = 0;
			int h = 0;
			try {
				w = Integer.parseInt(element.getAttribute("width"));
			} catch (NumberFormatException ex) {
			}
			try {
				h = Integer.parseInt(element.getAttribute("height"));
			} catch (NumberFormatException ex) {
			}
			return new SpaceBox(w, h);
		}
		// TODO Auto-generated method stub
		return super.createBox(context, element, parent, width);
	}
}
