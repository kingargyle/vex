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
package org.eclipse.wst.xml.vex.core.internal.provisional.dom;


/**
 * Interface for classes that manage a string of characters representing the
 * content of a document.
 * 
 * @model
 */
public interface Content {

	/**
	 * Creates a new Position object at the given initial offset.
	 * 
	 * @param offset
	 *            initial offset of the position
	 * @model
	 */
	public Position createPosition(int offset);

	/**
	 * Insert a string into the content.
	 * 
	 * @param offset
	 *            Offset at which to insert the string.
	 * @param s
	 *            String to insert.
	 * @model
	 */
	public void insertString(int offset, String s);

	/**
	 * Deletes the given range of characters.
	 * 
	 * @param offset
	 *            Offset from which characters should be deleted.
	 * @param length
	 *            Number of characters to delete.
	 * @model
	 */
	public void remove(int offset, int length);

	/**
	 * Gets a substring of the content.
	 * 
	 * @param offset
	 *            Offset at which the string begins.
	 * @param length
	 *            Number of characters to return.
	 * @model
	 */
	public String getString(int offset, int length);

	/**
	 * Return the length of the content.
	 * 
	 * @model
	 */
	public int getLength();
}
