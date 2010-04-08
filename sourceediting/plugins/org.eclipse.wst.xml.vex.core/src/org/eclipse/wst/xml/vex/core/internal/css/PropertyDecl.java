/*******************************************************************************
 * Copyright (c) 2004, 2010 John Krasnay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     John Krasnay - initial API and implementation
 *     Florian Thienel - bug 306639 - remove serializability from StyleSheet
 *                       and dependend classes
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.css;

import org.w3c.css.sac.LexicalUnit;

/**
 * Represents a particular CSS property declaration.
 */
public class PropertyDecl {

	private final Rule rule;
	private final String property;
	private final LexicalUnit value;
	private final boolean important;
	
	/**
	 * Class constructor.
	 */
	public PropertyDecl(Rule rule, String property, LexicalUnit value,
			boolean important) {
		this.rule = rule;
		this.property = property;
		this.value = value;
		this.important = important;
	}

	/**
	 * Return the value of the <code>important</code> property.
	 */
	public boolean isImportant() {
		return this.important;
	}

	/**
	 * Return the value of the <code>property</code> property.
	 */
	public String getProperty() {
		return this.property;
	}

	/**
	 * Return the value of the <code>rule</code> property.
	 */
	public Rule getRule() {
		return this.rule;
	}

	/**
	 * Return the value of the <code>value</code> property.
	 */
	public LexicalUnit getValue() {
		return this.value;
	}
	
}
