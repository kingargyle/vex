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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.wst.xml.vex.core.internal.core.Caret;
import org.eclipse.wst.xml.vex.core.internal.core.Color;
import org.eclipse.wst.xml.vex.core.internal.core.ColorResource;
import org.eclipse.wst.xml.vex.core.internal.core.FontMetrics;
import org.eclipse.wst.xml.vex.core.internal.core.Graphics;
import org.eclipse.wst.xml.vex.core.internal.core.Insets;
import org.eclipse.wst.xml.vex.core.internal.core.IntRange;
import org.eclipse.wst.xml.vex.core.internal.css.CSS;
import org.eclipse.wst.xml.vex.core.internal.css.StyleSheet;
import org.eclipse.wst.xml.vex.core.internal.css.Styles;
import org.eclipse.wst.xml.vex.core.internal.dom.Element;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.Position;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.VEXDocument;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.VEXElement;

/**
 * Base class of block boxes that can contain other block boxes. This class
 * implements the layout method and various navigation methods. Subclasses must
 * implement the createChildren method.
 * 
 * Subclasses can be anonymous or non-anonymous (i.e. generated by an element).
 * Since the vast majority of instances will be non-anonymous, this class can
 * manage the element and top and bottom margins without too much inefficiency.
 * 
 * <p>
 * Subclasses that can be anonymous must override the getStartPosition and
 * getEndPosition classes to return the range covered by the box.
 * </p>
 */
public abstract class AbstractBlockBox extends AbstractBox implements BlockBox {

	/**
	 * Class constructor for non-anonymous boxes.
	 * 
	 * @param context
	 *            LayoutContext being used.
	 * @param parent
	 *            Parent box.
	 * @param element
	 *            Element associated with this box. anonymous box.
	 */
	public AbstractBlockBox(LayoutContext context, BlockBox parent,
			VEXElement element) {

		this.parent = parent;
		this.element = element;

		Styles styles = context.getStyleSheet().getStyles(element);
		int parentWidth = parent.getWidth();
		this.marginTop = styles.getMarginTop().get(parentWidth);
		this.marginBottom = styles.getMarginBottom().get(parentWidth);

	}

	/**
	 * Class constructor for anonymous boxes.
	 * 
	 * @param context
	 *            LayoutContext to use.
	 * @param parent
	 *            Parent box.
	 * @param startOffset
	 *            Start of the range covered by the box.
	 * @param endOffset
	 *            End of the range covered by the box.
	 */
	public AbstractBlockBox(LayoutContext context, BlockBox parent,
			int startOffset, int endOffset) {
		this.parent = parent;
		this.marginTop = 0;
		this.marginBottom = 0;

		VEXDocument doc = context.getDocument();
		this.startPosition = doc.createPosition(startOffset);
		this.endPosition = doc.createPosition(endOffset);
	}

	/**
	 * Walks the box tree and returns the nearest enclosing element.
	 */
	protected VEXElement findContainingElement() {
		BlockBox box = this;
		VEXElement element = box.getElement();
		while (element == null) {
			box = box.getParent();
			element = box.getElement();
		}
		return element;
	}

	/**
	 * Returns this box's children as an array of BlockBoxes.
	 */
	protected BlockBox[] getBlockChildren() {
		return (BlockBox[]) this.getChildren();
	}

	public Caret getCaret(LayoutContext context, int offset) {

		// If we haven't yet laid out this block, estimate the caret.
		if (this.getLayoutState() != LAYOUT_OK) {
			int relative = offset - this.getStartOffset();
			int size = this.getEndOffset() - this.getStartOffset();
			int y = 0;
			if (size > 0) {
				y = this.getHeight() * relative / size;
			}
			return new HCaret(0, y, this.getHCaretWidth());
		}

		int y;

		Box[] children = this.getContentChildren();
		for (int i = 0; i < children.length; i++) {

			if (offset < children[i].getStartOffset()) {
				if (i > 0) {
					y = (children[i - 1].getY() + children[i - 1].getHeight() + children[i]
							.getY()) / 2;
				} else {
					y = 0;
				}
				return new HCaret(0, y, this.getHCaretWidth());
			}

			if (offset >= children[i].getStartOffset()
					&& offset <= children[i].getEndOffset()) {

				Caret caret = children[i].getCaret(context, offset);
				caret.translate(children[i].getX(), children[i].getY());
				return caret;
			}
		}

		if (this.hasChildren()) {
			y = this.getHeight();
		} else {
			y = this.getHeight() / 2;
		}

		return new HCaret(0, y, this.getHCaretWidth());
	}

	public Box[] getChildren() {
		return this.children;
	}

	/**
	 * Return an array of children that contain content.
	 */
	protected BlockBox[] getContentChildren() {
		Box[] children = this.getChildren();
		List contentChildren = new ArrayList(children.length);
		for (int i = 0; i < children.length; i++) {
			if (children[i].hasContent()) {
				contentChildren.add(children[i]);
			}
		}
		return (BlockBox[]) contentChildren
				.toArray(new BlockBox[contentChildren.size()]);
	}

	public VEXElement getElement() {
		return this.element;
	}

	public int getEndOffset() {
		VEXElement element = this.getElement();
		if (element != null) {
			return element.getEndOffset();
		} else if (this.getEndPosition() != null) {
			return this.getEndPosition().getOffset();
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 * Returns the estimated size of the box, based on the the current font size
	 * and the number of characters covered by the box. This is a utility method
	 * that can be used in implementation of setInitialSize. It assumes the
	 * width of the box has already been correctly set.
	 * 
	 * @param context
	 *            LayoutContext to use.
	 */
	protected int getEstimatedHeight(LayoutContext context) {

		VEXElement element = this.findContainingElement();
		Styles styles = context.getStyleSheet().getStyles(element);
		int charCount = this.getEndOffset() - this.getStartOffset();

		float fontSize = styles.getFontSize();
		float lineHeight = styles.getLineHeight();
		float estHeight = lineHeight * fontSize * 0.6f * charCount
				/ this.getWidth();

		return Math.round(Math.max(estHeight, lineHeight));
	}

	public LineBox getFirstLine() {
		if (this.hasChildren()) {
			BlockBox firstChild = (BlockBox) this.getChildren()[0];
			return firstChild.getFirstLine();
		} else {
			return null;
		}
	}

	/**
	 * Returns the width of the horizontal caret. This is overridden by TableBox
	 * to return a caret that is the full width of the table.
	 */
	protected int getHCaretWidth() {
		return H_CARET_LENGTH;
	}

	public Insets getInsets(LayoutContext context, int containerWidth) {

		if (this.getElement() != null) {
			Styles styles = context.getStyleSheet()
					.getStyles(this.getElement());

			int top = this.marginTop + styles.getBorderTopWidth()
					+ styles.getPaddingTop().get(containerWidth);

			int left = styles.getMarginLeft().get(containerWidth)
					+ styles.getBorderLeftWidth()
					+ styles.getPaddingLeft().get(containerWidth);

			int bottom = this.marginBottom + styles.getBorderBottomWidth()
					+ styles.getPaddingBottom().get(containerWidth);

			int right = styles.getMarginRight().get(containerWidth)
					+ styles.getBorderRightWidth()
					+ styles.getPaddingRight().get(containerWidth);

			return new Insets(top, left, bottom, right);
		} else {
			return new Insets(this.marginTop, 0, this.marginBottom, 0);
		}
	}

	public LineBox getLastLine() {
		if (this.hasChildren()) {
			BlockBox lastChild = (BlockBox) this.getChildren()[this
					.getChildren().length - 1];
			return lastChild.getLastLine();
		} else {
			return null;
		}
	}

	/**
	 * Returns the layout state of this box.
	 */
	protected byte getLayoutState() {
		return this.layoutState;
	}

	public int getLineEndOffset(int offset) {
		BlockBox[] children = this.getContentChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i].containsOffset(offset)) {
				return children[i].getLineEndOffset(offset);
			}
		}
		return offset;
	}

	public int getLineStartOffset(int offset) {
		BlockBox[] children = this.getContentChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i].containsOffset(offset)) {
				return children[i].getLineStartOffset(offset);
			}
		}
		return offset;
	}

	public int getMarginBottom() {
		return this.marginBottom;
	}

	public int getMarginTop() {
		return this.marginTop;
	}

	public int getNextLineOffset(LayoutContext context, int offset, int x) {

		//
		// This algorithm works when this block owns the offsets between
		// its children.
		//

		if (offset == this.getEndOffset()) {
			return -1;
		}

		BlockBox[] children = this.getContentChildren();

		if (offset < this.getStartOffset() && children.length > 0
				&& children[0].getStartOffset() > this.getStartOffset()) {
			//
			// If there's an offset before the first child, put the caret there.
			//
			return this.getStartOffset();
		}

		for (int i = 0; i < children.length; i++) {
			BlockBox child = children[i];
			if (offset <= child.getEndOffset()) {
				int newOffset = child.getNextLineOffset(context, offset, x
						- child.getX());
				if (newOffset < 0 /* && i < children.length-1 */) {
					return child.getEndOffset() + 1;
				} else {
					return newOffset;
				}
			}
		}

		return this.getEndOffset();
	}

	public BlockBox getParent() {
		return this.parent;
	}

	public int getPreviousLineOffset(LayoutContext context, int offset, int x) {

		if (offset == this.getStartOffset()) {
			return -1;
		}

		BlockBox[] children = this.getContentChildren();

		if (offset > this.getEndOffset()
				&& children.length > 0
				&& children[children.length - 1].getEndOffset() < this
						.getEndOffset()) {
			//
			// If there's an offset after the last child, put the caret there.
			//
			return this.getEndOffset();
		}

		for (int i = children.length; i > 0; i--) {
			BlockBox child = children[i - 1];
			if (offset >= child.getStartOffset()) {
				int newOffset = child.getPreviousLineOffset(context, offset, x
						- child.getX());
				if (newOffset < 0 && i > 0) {
					return child.getStartOffset() - 1;
				} else {
					return newOffset;
				}
			}
		}

		return this.getStartOffset();
	}

	public int getStartOffset() {
		VEXElement element = this.getElement();
		if (element != null) {
			return element.getStartOffset() + 1;
		} else if (this.getStartPosition() != null) {
			return this.getStartPosition().getOffset();
		} else {
			throw new IllegalStateException();
		}
	}

	public boolean hasContent() {
		return true;
	}

	public void invalidate(boolean direct) {

		if (direct) {
			this.layoutState = LAYOUT_REDO;
		} else {
			this.layoutState = LAYOUT_PROPAGATE;
		}

		if (this.getParent() instanceof AbstractBlockBox) {
			((AbstractBlockBox) this.getParent()).invalidate(false);
		}
	}

	public boolean isAnonymous() {
		return this.getElement() == null;
	}

	/**
	 * Call the given callback for each child matching one of the given display
	 * styles. Any nodes that do not match one of the given display types cause
	 * the onRange callback to be called, with a range covering all such
	 * contiguous nodes.
	 * 
	 * @param styleSheet
	 *            StyleSheet from which to determine display styles.
	 * @param displayStyles
	 *            Display types to be explicitly recognized.
	 * @param callback
	 *            DisplayStyleCallback through which the caller is notified of
	 *            matching elements and non-matching ranges.
	 */
	protected void iterateChildrenByDisplayStyle(StyleSheet styleSheet,
			Set displayStyles, ElementOrRangeCallback callback) {
		LayoutUtils.iterateChildrenByDisplayStyle(styleSheet, displayStyles,
				this.findContainingElement(), this.getStartOffset(), this
						.getEndOffset(), callback);
	}

	public void paint(LayoutContext context, int x, int y) {

		if (this.skipPaint(context, x, y)) {
			return;
		}

		boolean drawBorders = !context.isElementSelected(this.getElement());

		this.drawBox(context, x, y, this.getParent().getWidth(), drawBorders);

		this.paintChildren(context, x, y);

		this.paintSelectionFrame(context, x, y, true);
	}

	/**
	 * Default implementation. Width is calculated as the parent's width minus
	 * this box's insets. Height is calculated by getEstimatedHeight.
	 */
	public void setInitialSize(LayoutContext context) {
		int parentWidth = this.getParent().getWidth();
		Insets insets = this.getInsets(context, parentWidth);
		this.setWidth(Math.max(0, parentWidth - insets.getLeft()
				- insets.getRight()));
		this.setHeight(this.getEstimatedHeight(context));
	}

	public int viewToModel(LayoutContext context, int x, int y) {

		Box[] children = this.getChildren();

		if (children == null) {
			int charCount = this.getEndOffset() - this.getStartOffset() - 1;
			if (charCount == 0 || this.getHeight() == 0) {
				return this.getEndOffset();
			} else {
				return this.getStartOffset() + charCount * y / this.getHeight();
			}
		} else {
			for (int i = 0; i < children.length; i++) {
				Box child = children[i];
				if (!child.hasContent()) {
					continue;
				}
				if (y < child.getY()) {
					return child.getStartOffset() - 1;
				} else if (y < child.getY() + child.getHeight()) {
					return child.viewToModel(context, x - child.getX(), y
							- child.getY());
				}
			}
		}

		return this.getEndOffset();
	}

	// ===================================================== PRIVATE

	private BlockBox parent;
	private Box[] children;

	/**
	 * Paint a frame that indicates a block element box has been selected.
	 * 
	 * @param context
	 *            LayoutContext to use.
	 * @param x
	 *            x-coordinate at which to draw
	 * @param y
	 *            y-coordinate at which to draw.
	 * @param selected
	 */
	protected void paintSelectionFrame(LayoutContext context, int x, int y,
			boolean selected) {

		VEXElement element = this.getElement();
		VEXElement parent = element == null ? null : element.getParent();

		boolean paintFrame = context.isElementSelected(element)
				&& !context.isElementSelected(parent);

		if (!paintFrame) {
			return;
		}

		Graphics g = context.getGraphics();
		ColorResource foreground;
		ColorResource background;

		if (selected) {
			foreground = g.getSystemColor(ColorResource.SELECTION_FOREGROUND);
			background = g.getSystemColor(ColorResource.SELECTION_BACKGROUND);
		} else {
			foreground = g.createColor(new Color(0, 0, 0));
			background = g.createColor(new Color(0xcc, 0xcc, 0xcc));
		}

		FontMetrics fm = g.getFontMetrics();
		ColorResource oldColor = g.setColor(background);
		g.setLineStyle(Graphics.LINE_SOLID);
		g.setLineWidth(1);
		int tabWidth = g.stringWidth(this.getElement().getName())
				+ fm.getLeading();
		int tabHeight = fm.getHeight();
		int tabX = x + this.getWidth() - tabWidth;
		int tabY = y + this.getHeight() - tabHeight;
		g.drawRect(x, y, this.getWidth(), this.getHeight());
		g.fillRect(tabX, tabY, tabWidth, tabHeight);
		g.setColor(foreground);
		g.drawString(this.getElement().getName(), tabX + fm.getLeading() / 2,
				tabY);

		g.setColor(oldColor);
		if (!selected) {
			foreground.dispose();
			background.dispose();
		}
	}

	/** Layout is OK */
	public static final byte LAYOUT_OK = 0;

	/** My layout is OK, but one of my children needs to be laid out */
	public static final byte LAYOUT_PROPAGATE = 1;

	/** I need to be laid out */
	public static final byte LAYOUT_REDO = 2;

	private byte layoutState = LAYOUT_REDO;

	public IntRange layout(LayoutContext context, int top, int bottom) {

		int repaintStart = Integer.MAX_VALUE;
		int repaintEnd = 0;
		boolean repaintToBottom = false;
		int originalHeight = this.getHeight();

		if (this.layoutState == LAYOUT_REDO) {

			// System.out.println("Redo layout of " +
			// this.getElement().getName());

			List childList = this.createChildren(context);
			this.children = (BlockBox[]) childList
					.toArray(new BlockBox[childList.size()]);

			// Even though we position children after layout, we have to
			// do a preliminary positioning here so we now which ones
			// overlap our layout band
			for (int i = 0; i < this.children.length; i++) {
				BlockBox child = (BlockBox) this.children[i];
				child.setInitialSize(context);
			}
			this.positionChildren(context);

			// repaint everything
			repaintToBottom = true;
			repaintStart = 0;
		}

		Box[] children = this.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof BlockBox) {
				BlockBox child = (BlockBox) children[i];
				if (top <= child.getY() + child.getHeight()
						&& bottom >= child.getY()) {

					IntRange repaintRange = child.layout(context, top
							- child.getY(), bottom - child.getY());
					if (repaintRange != null) {
						repaintStart = Math.min(repaintStart, repaintRange
								.getStart()
								+ child.getY());
						repaintEnd = Math.max(repaintEnd, repaintRange.getEnd()
								+ child.getY());
					}
				}
			}
		}

		int childRepaintStart = this.positionChildren(context);
		if (childRepaintStart != -1) {
			repaintToBottom = true;
			repaintStart = Math.min(repaintStart, childRepaintStart);
		}

		this.layoutState = LAYOUT_OK;

		if (repaintToBottom) {
			repaintEnd = Math.max(originalHeight, this.getHeight());
		}

		if (repaintStart < repaintEnd) {
			return new IntRange(repaintStart, repaintEnd);
		} else {
			return null;
		}
	}

	protected abstract List createChildren(LayoutContext context);

	/**
	 * Creates a list of block boxes for a given document range. beforeInlines
	 * and afterInlines are prepended/appended to the first/last block child,
	 * and each may be null.
	 */
	protected List createBlockBoxes(LayoutContext context, int startOffset,
			int endOffset, int width, List beforeInlines, List afterInlines) {

		List blockBoxes = new ArrayList();
		List pendingInlines = new ArrayList();

		if (beforeInlines != null) {
			pendingInlines.addAll(beforeInlines);
		}
		
		VEXDocument document = context.getDocument();

		VEXElement element = document.findCommonElement(startOffset,
				endOffset);

		if (startOffset == endOffset) {
			int relOffset = startOffset - element.getStartOffset();
			pendingInlines.add(new PlaceholderBox(context, element, relOffset));
		} else {

			BlockInlineIterator iter = new BlockInlineIterator(context,
					element, startOffset, endOffset);

			while (true) {

				Object next = iter.next();

				if (next == null) {
					break;
				}

				if (next instanceof IntRange) {

					IntRange range = (IntRange) next;

					InlineElementBox.InlineBoxes inlineBoxes = InlineElementBox
							.createInlineBoxes(context, element, range
									.getStart(), range.getEnd());
					pendingInlines.addAll(inlineBoxes.boxes);
					pendingInlines.add(new PlaceholderBox(context, element,
							range.getEnd() - element.getStartOffset()));

				} else {

					if (pendingInlines.size() > 0) {
						blockBoxes.add(ParagraphBox.create(context, element,
								pendingInlines, width));
						pendingInlines.clear();
					}

					if (isTableChild(context, next)) {

						// Consume continguous table children and create an
						// anonymous table.

						int tableStartOffset = ((Element) next)
								.getStartOffset();
						int tableEndOffset = -1; // dummy to hide warning
						while (isTableChild(context, next)) {
							tableEndOffset = ((Element) next).getEndOffset() + 1;
							next = iter.next();
						}

						// add anonymous table
						blockBoxes.add(new TableBox(context, this,
								tableStartOffset, tableEndOffset));

						if (next == null) {
							break;
						} else {
							iter.push(next);
						}

					} else { // next is a block box element
						Element blockElement = (Element) next;
						blockBoxes.add(context.getBoxFactory().createBox(
								context, blockElement, this, width));
					}
				}
			}
		}

		if (afterInlines != null) {
			pendingInlines.addAll(afterInlines);
		}

		if (pendingInlines.size() > 0) {
			blockBoxes.add(ParagraphBox.create(context, element,
					pendingInlines, width));
			pendingInlines.clear();
		}

		return blockBoxes;
	}

	private class BlockInlineIterator {

		public BlockInlineIterator(LayoutContext context, VEXElement element,
				int startOffset, int endOffset) {
			this.context = context;
			this.element = element;
			this.startOffset = startOffset;
			this.endOffset = endOffset;
		}

		/**
		 * Returns the next block element or inline range, or null if we're at
		 * the end.
		 */
		public Object next() {
			if (this.pushStack.size() > 0) {
				return this.pushStack.removeLast();
			} else if (startOffset == endOffset) {
				return null;
			} else {
				VEXElement blockElement = findNextBlockElement(this.context,
						this.element, startOffset, endOffset);
				if (blockElement == null) {
					if (startOffset < endOffset) {
						IntRange result = new IntRange(startOffset, endOffset);
						startOffset = endOffset;
						return result;
					} else {
						return null;
					}
				} else if (blockElement.getStartOffset() > startOffset) {
					this.pushStack.addLast(blockElement);
					IntRange result = new IntRange(startOffset, blockElement
							.getStartOffset());
					startOffset = blockElement.getEndOffset() + 1;
					return result;
				} else {
					startOffset = blockElement.getEndOffset() + 1;
					return blockElement;
				}
			}
		}

		public Object peek() {
			if (this.pushStack.size() == 0) {
				Object next = next();
				if (next == null) {
					return null;
				} else {
					push(next);
				}
			}
			return pushStack.getLast();
		}

		public void push(Object pushed) {
			this.pushStack.addLast(pushed);
		}

		private LayoutContext context;
		private VEXElement element;
		private int startOffset;
		private int endOffset;
		private LinkedList pushStack = new LinkedList();
	}

	protected boolean hasChildren() {
		return this.getChildren() != null && this.getChildren().length > 0;
	}

	/**
	 * Positions the children of this box. Vertical margins are collapsed here.
	 * Returns the vertical offset of the top of the first child to move, or -1
	 * if not children were actually moved.
	 */
	protected int positionChildren(LayoutContext context) {

		int childY = 0;
		int prevMargin = 0;
		BlockBox[] children = this.getBlockChildren();
		int repaintStart = -1;

		Styles styles = null;

		if (!this.isAnonymous()) {
			styles = context.getStyleSheet().getStyles(this.getElement());
		}

		if (styles != null && children.length > 0) {
			if (styles.getBorderTopWidth()
					+ styles.getPaddingTop().get(this.getWidth()) == 0) {
				// first child's top margin collapses into ours
				this.marginTop = Math.max(this.marginTop, children[0]
						.getMarginTop());
				childY -= children[0].getMarginTop();
			}
		}

		for (int i = 0; i < children.length; i++) {

			Insets insets = children[i].getInsets(context, this.getWidth());

			childY += insets.getTop();

			if (i > 0) {
				childY -= Math.min(prevMargin, children[i].getMarginTop());
			}

			if (repaintStart == -1 && children[i].getY() != childY) {
				repaintStart = Math.min(children[i].getY(), childY);
			}

			children[i].setX(insets.getLeft());
			children[i].setY(childY);

			childY += children[i].getHeight() + insets.getBottom();
			prevMargin = children[i].getMarginBottom();
		}

		if (styles != null && children.length > 0) {
			if (styles.getBorderBottomWidth()
					+ styles.getPaddingBottom().get(this.getWidth()) == 0) {
				// last child's bottom margin collapses into ours
				this.marginBottom = Math.max(this.marginBottom, prevMargin);
				childY -= prevMargin;
			}
		}

		this.setHeight(childY);

		return repaintStart;
	}

	/**
	 * Sets the layout state of the box.
	 * 
	 * @param layoutState
	 *            One of the LAYOUT_* constants
	 */
	protected void setLayoutState(byte layoutState) {
		this.layoutState = layoutState;
	}

	// ========================================================= PRIVATE
	/** The length, in pixels, of the horizontal caret between block boxes */
	private static final int H_CARET_LENGTH = 20;

	/**
	 * Element with which we are associated. For anonymous boxes, this is null.
	 */
	private VEXElement element;

	/*
	 * We cache the top and bottom margins, since they may be affected by our
	 * children.
	 */
	private int marginTop;
	private int marginBottom;

	/**
	 * Start position of an anonymous box. For non-anonymous boxes, this is
	 * null.
	 */
	private Position startPosition;

	/**
	 * End position of an anonymous box. For non-anonymous boxes, this is null.
	 */
	private Position endPosition;

	/**
	 * Searches for the next block-formatted child.
	 * 
	 * @param context
	 *            LayoutContext to use.
	 * @param element
	 *            Element within which to search.
	 * @param startOffset
	 *            The offset at which to start the search.
	 * @param endOffset
	 *            The offset at which to end the search.
	 */
	private static VEXElement findNextBlockElement(LayoutContext context,
			VEXElement element, int startOffset, int endOffset) {

		List<VEXElement> children = element.getChildElements();
		for (int i = 0; i < children.size(); i++) {
			VEXElement child = children.get(i);
			if (child.getEndOffset() < startOffset) {
				continue;
			} else if (child.getStartOffset() >= endOffset) {
				break;
			} else {
				Styles styles = context.getStyleSheet().getStyles(child);
				if (!styles.getDisplay().equals(CSS.INLINE)) { // TODO do proper
																// block display
																// determination
					return child;
				} else {
					VEXElement fromChild = findNextBlockElement(context, child,
							startOffset, endOffset);
					if (fromChild != null) {
						return fromChild;
					}
				}
			}
		}

		return null;
	}

	/**
	 * Return the end position of an anonymous box. The default implementation
	 * returns null.
	 */
	private Position getEndPosition() {
		return this.endPosition;
	}

	/**
	 * Return the start position of an anonymous box. The default implementation
	 * returns null.
	 */
	private Position getStartPosition() {
		return this.startPosition;
	}

	private boolean isTableChild(LayoutContext context, Object rangeOrElement) {
		if (rangeOrElement != null && rangeOrElement instanceof Element) {
			return LayoutUtils.isTableChild(context.getStyleSheet(),
					(VEXElement) rangeOrElement);
		} else {
			return false;
		}
	}

}
