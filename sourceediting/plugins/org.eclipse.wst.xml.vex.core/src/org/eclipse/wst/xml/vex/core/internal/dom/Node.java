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

import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Content;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Position;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.VEXNodeImpl;

/**
 * <code>Node</code> represents a component of an XML document. .
 *
 */
public class Node extends VEXNodeImpl implements VEXNode {

	/**
	 * Class constructor.
	 */
	public Node() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXNode#getContent()
	 */
	public Content getContent() {
		return this.content;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXNode#getEndOffset()
	 */
	public int getEndOffset() {
		return endPosition.getOffset();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXNode#getEndPosition()
	 */
	public Position getEndPosition() {
		return endPosition;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXNode#getStartOffset()
	 */
	public int getStartOffset() {
		return startPosition.getOffset();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXNode#getStartPosition()
	 */
	public Position getStartPosition() {
		return startPosition;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXNode#getText()
	 */
	public String getText() {
		return content.getString(getStartOffset(), getEndOffset() - getStartOffset());
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
	public void setContent(Content content, int startOffset, int endOffset) {

		this.content = content;
		startPosition = content.createPosition(startOffset);
		endPosition = content.createPosition(endOffset);
	}

	public String getNodeType() {
		return null;
	}

}
