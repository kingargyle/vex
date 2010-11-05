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
package org.eclipse.wst.xml.vex.core.internal.layout;

/**
 * @author Florian Thienel
 */
public abstract class AbstractInlineBox extends AbstractBox implements InlineBox {

	public void alignOnBaseline(int baseline) {
		setY(baseline - getBaseline());
	}
}
