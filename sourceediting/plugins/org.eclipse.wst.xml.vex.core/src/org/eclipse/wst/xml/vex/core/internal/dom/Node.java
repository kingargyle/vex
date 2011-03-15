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

/**
 * <code>Node</code> represents a component of an XML document. .
 *
 */
public abstract class Node {

	private Content content;
	private Position startPosition;
	private Position endPosition;
	
	public Content getContent() {
		return content;
	}

	public int getEndOffset() {
		return endPosition.getOffset();
	}

	public Position getEndPosition() {
		return endPosition;
	}

	public int getStartOffset() {
		return startPosition.getOffset();
	}

	public Position getStartPosition() {
		return startPosition;
	}

	public String getText() {
		return content.getString(getStartOffset(), getEndOffset() - getStartOffset());
	}

	/**
	 * Sets the content of this node
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
	
	public abstract String getBaseURI();
}
