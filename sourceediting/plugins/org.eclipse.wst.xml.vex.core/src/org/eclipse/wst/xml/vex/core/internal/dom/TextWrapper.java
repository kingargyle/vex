/*******************************************************************************
 * Copyright (c) 2004, 2008 John Krasnay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     John Krasnay - initial API and implementation
 *     David Carver unit tests fixes
 *     Igor Jacy Lino Campista - Java 5 warnings fixed (bug 311325)
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.dom;

import java.util.ArrayList;
import java.util.List;

/**
 * Wraps text to a given width.
 */
public class TextWrapper {

	private List<String> parts = new ArrayList<String>();

	private boolean lastIsWhite = true;

	/**
	 * Class constructor.
	 */
	public TextWrapper() {
	}

	/**
	 * Adds text to the list of things to be wrapped.
	 * 
	 * @param s
	 *            Text to be added.
	 */
	public void add(String s) {
		int i = 0;
		int j = 0;
		boolean thisIsWhite = true;
		while (j < s.length()) {

			// skip non-whitespace
			while (j < s.length() && !Character.isWhitespace(s.charAt(j))) {
				j++;
				thisIsWhite = false;
			}

			// skip whitespace
			while (j < s.length() && Character.isWhitespace(s.charAt(j))) {
				j++;
				thisIsWhite = true;
			}

			if (lastIsWhite || parts.size() == 0) {
				this.parts.add(s.substring(i, j));
			}
			else if (parts.size() > 0) { 
				
				this.parts.add(((String) this.parts
						.remove(this.parts.size() - 1))
						+ s.substring(i, j));
			} 
			i = j;
			lastIsWhite = thisIsWhite;
		}
	}

	/**
	 * Adds text to the list of things to be wrapped. The given text will be
	 * treated as a single unit and will not be split across lines.
	 * 
	 * @param s
	 *            Text to be added.
	 */
	public void addNoSplit(String s) {
		this.parts.add(s);
	}

	/**
	 * Clears any added text.
	 */
	public void clear() {
		this.parts.clear();
	}

	/**
	 * Wraps the text into the given width. The text is only broken at spaces,
	 * meaning the returned lines will not necessarily fit within width.
	 * 
	 * @param width
	 */
	public String[] wrap(int width) {
		List<String> lines = new ArrayList<String>();
		StringBuffer line = new StringBuffer();

		for (String s : this.parts) {
			if (line.length() > 0 && line.length() + s.length() > width) {
				// part won't fit on the current line
				lines.add(line.toString());
				line.setLength(0);

				if (s.length() > width) {
					lines.add(s);
				} else {
					line.append(s);
				}
			} else {
				line.append(s);
			}
		}

		if (line.length() > 0) {
			lines.add(line.toString());
		}

		return (String[]) lines.toArray(new String[lines.size()]);
	}

}
