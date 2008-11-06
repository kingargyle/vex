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
package org.eclipse.wst.xml.vex.core.internal.dom;

import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IContent;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IPosition;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.VEXNode;

/**
 * <code>Node</code> represents a component of an XML document. .
 *
 */
public class Node implements VEXNode {

	private IContent content = null;
	private IPosition start = null;
	private IPosition end = null;

	/**
	 * Class constructor.
	 */
	public Node() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXNode#getContent()
	 */
	public IContent getContent() {
		return this.content;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXNode#getEndOffset()
	 */
	public int getEndOffset() {
		return this.end.getOffset();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXNode#getEndPosition()
	 */
	public IPosition getEndPosition() {
		return this.end;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXNode#getStartOffset()
	 */
	public int getStartOffset() {
		return this.start.getOffset();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXNode#getStartPosition()
	 */
	public IPosition getStartPosition() {
		return this.start;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXNode#getText()
	 */
	public String getText() {
		return this.content.getString(this.getStartOffset(), this
				.getEndOffset()
				- this.getStartOffset());
	}

	/**
	 * Sets the content of the node
	 * 
	 * @param content
	 *            Content object holding the node's content
	 * @param startOffset
	 *            offset at which the node's content starts
	 * @param endOffset
	 *            offset at which the node's content ends
	 */
	public void setContent(IContent content, int startOffset, int endOffset) {

		this.content = content;
		this.start = content.createPosition(startOffset);
		this.end = content.createPosition(endOffset);
	}

}
