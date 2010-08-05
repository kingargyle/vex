/*******************************************************************************
 * Copyright (c) 2010 Mohamadou Nassourou and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Mohamadou Nassourou - Bug 298912 - rudimentary support for images 
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.css;

import org.w3c.css.sac.LexicalUnit;

public class WidthProperty extends AbstractProperty {

	public static final float DEFAULT_WIDTH = 50.0f;

	public WidthProperty() {
		super(CSS.WIDTH);
	}

	public Object calculate(final LexicalUnit lexicalUnit, final Styles parentStyles, final Styles styles) {
		if (lexicalUnit != null)
			return new Float(Math.max(DEFAULT_WIDTH, lexicalUnit.getFloatValue()));
		return DEFAULT_WIDTH;
	}
}