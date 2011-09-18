/*******************************************************************************
 * Copyright (c) 2004, 2008 John Krasnay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     John Krasnay - initial API and implementation
 *     Igor Jacy Lino Campista - Java 5 warnings fixed (bug 311325)
 *     Holger Voormann - bug 315914: content assist should only show elements 
 *			valid in the current context
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.widget;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.wst.xml.vex.core.internal.core.Caret;
import org.eclipse.wst.xml.vex.core.internal.core.Color;
import org.eclipse.wst.xml.vex.core.internal.core.Graphics;
import org.eclipse.wst.xml.vex.core.internal.core.IntRange;
import org.eclipse.wst.xml.vex.core.internal.core.QualifiedNameComparator;
import org.eclipse.wst.xml.vex.core.internal.core.Rectangle;
import org.eclipse.wst.xml.vex.core.internal.css.CSS;
import org.eclipse.wst.xml.vex.core.internal.css.StyleSheet;
import org.eclipse.wst.xml.vex.core.internal.css.StyleSheetReader;
import org.eclipse.wst.xml.vex.core.internal.css.Styles;
import org.eclipse.wst.xml.vex.core.internal.dom.Document;
import org.eclipse.wst.xml.vex.core.internal.dom.DocumentEvent;
import org.eclipse.wst.xml.vex.core.internal.dom.DocumentFragment;
import org.eclipse.wst.xml.vex.core.internal.dom.DocumentListener;
import org.eclipse.wst.xml.vex.core.internal.dom.DocumentReader;
import org.eclipse.wst.xml.vex.core.internal.dom.DocumentValidationException;
import org.eclipse.wst.xml.vex.core.internal.dom.Element;
import org.eclipse.wst.xml.vex.core.internal.dom.IWhitespacePolicy;
import org.eclipse.wst.xml.vex.core.internal.dom.IWhitespacePolicyFactory;
import org.eclipse.wst.xml.vex.core.internal.dom.Position;
import org.eclipse.wst.xml.vex.core.internal.dom.Validator;
import org.eclipse.wst.xml.vex.core.internal.layout.BlockBox;
import org.eclipse.wst.xml.vex.core.internal.layout.Box;
import org.eclipse.wst.xml.vex.core.internal.layout.BoxFactory;
import org.eclipse.wst.xml.vex.core.internal.layout.CssBoxFactory;
import org.eclipse.wst.xml.vex.core.internal.layout.LayoutContext;
import org.eclipse.wst.xml.vex.core.internal.layout.RootBox;
import org.eclipse.wst.xml.vex.core.internal.undo.CannotRedoException;
import org.eclipse.wst.xml.vex.core.internal.undo.CannotUndoException;
import org.eclipse.wst.xml.vex.core.internal.undo.CompoundEdit;
import org.eclipse.wst.xml.vex.core.internal.undo.IUndoableEdit;
import org.xml.sax.SAXException;

/**
 * A component that allows the display and edit of an XML document with an
 * associated CSS stylesheet.
 */
public class VexWidgetImpl implements IVexWidget {

	/**
	 * Number of pixel rows above and below the caret that are rendered at a
	 * time.
	 */
	private static final int LAYOUT_WINDOW = 5000;

	/**
	 * Because the height of each BlockElementBox is initially estimated, we
	 * sometimes have to try several times before the band being laid out is
	 * properly positioned about the offset. When the position of the offset
	 * changes by less than this amount between subsequent layout calls, the
	 * layout is considered stable.
	 */
	private static final int LAYOUT_TOLERANCE = 500;

	/**
	 * Minimum layout width, in pixels. Prevents performance problems when width
	 * is very small.
	 */
	private static final int MIN_LAYOUT_WIDTH = 200;

	private boolean debugging;

	private final HostComponent hostComponent;
	private int layoutWidth = 500; // something reasonable to handle a document
	// being set before the widget is sized

	private Document document;
	private StyleSheet styleSheet;
	private BoxFactory boxFactory = new CssBoxFactory();

	private RootBox rootBox;

	/** Stacks of UndoableEditEvents; items added and removed from end of list */
	private LinkedList<UndoableAndOffset> undoList = new LinkedList<UndoableAndOffset>();
	private LinkedList<UndoableAndOffset> redoList = new LinkedList<UndoableAndOffset>();
	private static final int MAX_UNDO_STACK_SIZE = 100;
	private int undoDepth;

	/** Support for beginWork/endWork */
	private int beginWorkCount = 0;
	private int beginWorkCaretOffset;
	private CompoundEdit compoundEdit;

	private int caretOffset;
	private int mark;
	private int selectionStart;
	private int selectionEnd;

	private Element currentElement;

	private boolean caretVisible = true;
	private Caret caret;
	private Color caretColor;

	// x offset to be maintained when moving vertically
	private int magicX = -1;

	private boolean antiAliased = false;

	private final DocumentListener documentListener = new DocumentListener() {

		public void attributeChanged(final DocumentEvent e) {
			invalidateElementBox(e.getParentElement());

			// flush cached styles, since they might depend attribute values
			// via conditional selectors
			getStyleSheet().flushStyles(e.getParentElement());

			if (beginWorkCount == 0)
				VexWidgetImpl.this.relayout();

			addEdit(e.getUndoableEdit(), getCaretOffset());
			hostComponent.fireSelectionChanged();
		}

		public void beforeContentDeleted(final DocumentEvent e) {
		}

		public void beforeContentInserted(final DocumentEvent e) {
		}

		public void contentDeleted(final DocumentEvent e) {
			invalidateElementBox(e.getParentElement());

			if (beginWorkCount == 0)
				VexWidgetImpl.this.relayout();

			addEdit(e.getUndoableEdit(), getCaretOffset());
		}

		public void contentInserted(final DocumentEvent e) {
			invalidateElementBox(e.getParentElement());

			if (beginWorkCount == 0)
				VexWidgetImpl.this.relayout();

			addEdit(e.getUndoableEdit(), getCaretOffset());
		}

	};

	/**
	 * Class constructor.
	 */
	public VexWidgetImpl(final HostComponent hostComponent) {
		this.hostComponent = hostComponent;
	}

	public void beginWork() {
		if (beginWorkCount == 0) {
			beginWorkCaretOffset = getCaretOffset();
			compoundEdit = new CompoundEdit();
		}
		beginWorkCount++;
	}

	/**
	 * Returns true if the given fragment can be inserted at the current caret
	 * position.
	 * 
	 * @param frag
	 *            DocumentFragment to be inserted.
	 */
	public boolean canInsertFragment(final DocumentFragment frag) {

		final Document doc = getDocument();
		if (doc == null)
			return false;

		final Validator validator = doc.getValidator();
		if (validator == null)
			return true;

		int startOffset = getCaretOffset();
		int endOffset = getCaretOffset();
		if (hasSelection()) {
			startOffset = getSelectionStart();
			endOffset = getSelectionEnd();
		}

		final Element parent = getDocument().getElementAt(startOffset);
		final List<QualifiedName> seq1 = doc.getNodeNames(parent.getStartOffset() + 1, startOffset);
		final List<QualifiedName> seq2 = frag.getNodeNames();
		final List<QualifiedName> seq3 = doc.getNodeNames(endOffset, parent.getEndOffset());

		return validator.isValidSequence(parent.getQualifiedName(), seq1, seq2, seq3, true);
	}

	/**
	 * Returns true if text can be inserted at the current position.
	 */
	public boolean canInsertText() {
		final Document doc = getDocument();
		if (doc == null)
			return false;

		final Validator validator = document.getValidator();
		if (validator == null)
			return true;

		int startOffset = getCaretOffset();
		int endOffset = getCaretOffset();
		if (hasSelection()) {
			startOffset = getSelectionStart();
			endOffset = getSelectionEnd();
		}

		final Element parent = getDocument().getElementAt(startOffset);
		final List<QualifiedName> seq1 = doc.getNodeNames(parent.getStartOffset() + 1, startOffset);
		final List<QualifiedName> seq2 = Collections.singletonList(Validator.PCDATA);
		final List<QualifiedName> seq3 = doc.getNodeNames(endOffset, parent.getEndOffset());

		return validator.isValidSequence(parent.getQualifiedName(), seq1, seq2, seq3, true);
	}

	public boolean canPaste() {
		throw new UnsupportedOperationException("Must be implemented in tookit-specific widget.");
	}

	public boolean canPasteText() {
		throw new UnsupportedOperationException("Must be implemented in tookit-specific widget.");
	}

	public boolean canRedo() {
		return !redoList.isEmpty();
	}

	public boolean canUndo() {
		return !undoList.isEmpty();
	}

	public boolean canUnwrap() {
		final Document doc = getDocument();
		if (doc == null)
			return false;

		final Validator validator = doc.getValidator();
		if (validator == null)
			return false;

		final Element element = doc.getElementAt(getCaretOffset());
		final Element parent = element.getParent();
		if (parent == null)
			// can't unwrap the root
			return false;

		final List<QualifiedName> seq1 = doc.getNodeNames(parent.getStartOffset() + 1, element.getStartOffset());
		final List<QualifiedName> seq2 = doc.getNodeNames(element.getStartOffset() + 1, element.getEndOffset());
		final List<QualifiedName> seq3 = doc.getNodeNames(element.getEndOffset() + 1, parent.getEndOffset());

		return validator.isValidSequence(parent.getQualifiedName(), seq1, seq2, seq3, true);
	}

	public void copySelection() {
		throw new UnsupportedOperationException("Must be implemented in tookit-specific widget.");
	}

	public void cutSelection() {
		throw new UnsupportedOperationException("Must be implemented in tookit-specific widget.");
	}

	public void deleteNextChar() throws DocumentValidationException {
		if (hasSelection())
			deleteSelection();
		else {
			final int offset = getCaretOffset();
			final Document doc = getDocument();
			final int n = doc.getLength() - 1;
			final Element element = doc.getElementAt(offset);

			if (offset == n) {
				// nop
			} else if (isBetweenMatchingElements(offset))
				joinElementsAt(offset);
			else if (isBetweenMatchingElements(offset + 1))
				joinElementsAt(offset + 1);
			else if (element.isEmpty()) {
				// deleting the right sentinel of an empty element
				// so just delete the whole element an move on
				this.moveTo(offset - 1, false);
				this.moveTo(offset + 1, true);
				deleteSelection();
			} else if (doc.getElementAt(offset + 1).isEmpty()) {
				// deleting the left sentinel of an empty element
				// so just delete the whole element an move on
				this.moveTo(offset + 2, true);
				deleteSelection();
			} else if (doc.getCharacterAt(offset) != 0) {
				this.moveTo(offset, false);
				this.moveTo(offset + 1, true);
				deleteSelection();
			}
		}
	}

	public void deletePreviousChar() throws DocumentValidationException {

		if (hasSelection())
			deleteSelection();
		else {
			int offset = getCaretOffset();
			final Document doc = getDocument();
			final Element element = doc.getElementAt(offset);

			if (offset == 1) {
				// nop
			} else if (isBetweenMatchingElements(offset))
				joinElementsAt(offset);
			else if (isBetweenMatchingElements(offset - 1))
				joinElementsAt(offset - 1);
			else if (element.isEmpty()) {
				// deleting the left sentinel of an empty element
				// so just delete the whole element an move on
				this.moveTo(offset - 1, false);
				this.moveTo(offset + 1, true);
				deleteSelection();
			} else if (doc.getElementAt(offset - 1).isEmpty()) {
				// deleting the right sentinel of an empty element
				// so just delete the whole element an move on
				this.moveTo(offset - 2, true);
				deleteSelection();
			} else {
				offset--;
				if (doc.getCharacterAt(offset) != 0) {
					this.moveTo(offset, false);
					this.moveTo(offset + 1, true);
					deleteSelection();
				}
			}
		}

	}

	public void deleteSelection() {
		try {
			if (hasSelection()) {
				document.delete(getSelectionStart(), getSelectionEnd());
				this.moveTo(getSelectionStart());
			}
		} catch (final DocumentValidationException ex) {
			ex.printStackTrace(); // This should never happen, because we
			// constrain the selection
		}
	}

	public void doWork(final Runnable runnable) {
		this.doWork(false, runnable);
	}

	public void doWork(final boolean savePosition, final Runnable runnable) {
		Position position = null;

		if (savePosition)
			position = getDocument().createPosition(getCaretOffset());

		boolean success = false;
		try {
			beginWork();
			runnable.run();
			success = true;
		} catch (final Exception ex) {
			ex.printStackTrace();
		} finally {
			endWork(success);
			if (position != null)
				this.moveTo(position.getOffset());
		}
	}

	public void endWork(final boolean success) {
		beginWorkCount--;
		if (beginWorkCount == 0) {
			// this.compoundEdit.end();
			if (success) {
				undoList.add(new UndoableAndOffset(compoundEdit, beginWorkCaretOffset));
				undoDepth++;
				if (undoList.size() > MAX_UNDO_STACK_SIZE)
					undoList.removeFirst();
				redoList.clear();
				relayout();
				hostComponent.fireSelectionChanged();
			} else
				try {
					compoundEdit.undo();
					this.moveTo(beginWorkCaretOffset);
				} catch (final CannotUndoException e) {
					// TODO: handle exception
				}
			compoundEdit = null;
		}
	}

	public Box findInnermostBox(final IBoxFilter filter) {
		return this.findInnermostBox(filter, getCaretOffset());
	}

	/**
	 * Returns the innermost box containing the given offset that matches the
	 * given filter.
	 * 
	 * @param filter
	 *            IBoxFilter that determines which box to return
	 * @param offset
	 *            Document offset around which to search.
	 */
	private Box findInnermostBox(final IBoxFilter filter, final int offset) {

		Box box = rootBox.getChildren()[0];
		Box matchingBox = null;

		for (;;) {
			if (filter.matches(box))
				matchingBox = box;

			final Box original = box;
			final Box[] children = box.getChildren();
			for (final Box child : children)
				if (child.hasContent() && offset >= child.getStartOffset() && offset <= child.getEndOffset()) {
					box = child;
					break;
				}

			if (box == original)
				// No child found containing offset,
				// so just return the latest match.
				return matchingBox;
		}

	}

	/**
	 * Returns the background color for the control, which is the same as the
	 * background color of the root element.
	 */
	public Color getBackgroundColor() {
		return styleSheet.getStyles(document.getRootElement()).getBackgroundColor();
	}

	public BoxFactory getBoxFactory() {
		return boxFactory;
	}

	/**
	 * Returns the current caret.
	 */
	public Caret getCaret() {
		return caret;
	}

	public int getCaretOffset() {
		return caretOffset;
	}

	public Element getCurrentElement() {
		return currentElement;
	}

	public Document getDocument() {
		return document;
	}

	/**
	 * Returns the natural height of the widget based on the current layout
	 * width.
	 */
	public int getHeight() {
		return rootBox.getHeight();
	}

	public String[] getValidInsertElements() {
		final Document doc = getDocument();
		if (doc == null)
			return new String[0];

		final Validator validator = doc.getValidator();
		if (validator == null)
			return new String[0];

		int startOffset = getCaretOffset();
		int endOffset = getCaretOffset();
		if (hasSelection()) {
			startOffset = getSelectionStart();
			endOffset = getSelectionEnd();
		}

		final Element parent = doc.getElementAt(startOffset);

		final List<QualifiedName> candidates = createCandidatesList(validator, parent, Validator.PCDATA);

		// filter invalid sequences
		final List<QualifiedName> nodesBefore = doc.getNodeNames(parent.getStartOffset() + 1, startOffset);
		final List<QualifiedName> nodesAfter = doc.getNodeNames(endOffset, parent.getEndOffset());
		final int sequenceLength = nodesBefore.size() + 1 + nodesAfter.size();
		for (final Iterator<QualifiedName> iter = candidates.iterator(); iter.hasNext();) {
			final QualifiedName candidate = iter.next();
			final List<QualifiedName> sequence = new ArrayList<QualifiedName>(sequenceLength);
			sequence.addAll(nodesBefore);
			sequence.add(candidate);
			sequence.addAll(nodesAfter);
			if (!validator.isValidSequence(parent.getQualifiedName(), sequence, true))
				iter.remove();
		}

		// If there's a selection, root out those candidates that can't contain it.
		if (hasSelection()) {
			final List<QualifiedName> selectedNodes = doc.getNodeNames(startOffset, endOffset);

			for (final Iterator<QualifiedName> iter = candidates.iterator(); iter.hasNext();) {
				final QualifiedName candidate = iter.next();
				if (!validator.isValidSequence(candidate, selectedNodes, true))
					iter.remove();
			}
		}

		Collections.sort(candidates, new QualifiedNameComparator());
		final String[] result = new String[candidates.size()];
		int i = 0;
		for (QualifiedName candidate : candidates)
			result[i++] = candidate.getLocalName(); // TODO handle namespaces in VexWidget
		return result;
	}

	private static List<QualifiedName> createCandidatesList(final Validator validator, final Element parent, final QualifiedName... exceptions) {
		final Set<QualifiedName> validItems = validator.getValidItems(parent);
		final List<QualifiedName> exceptionItems = Arrays.asList(exceptions);
		final List<QualifiedName> result = new ArrayList<QualifiedName>();
		for (final QualifiedName validItem : validItems)
			if (!exceptionItems.contains(validItem))
				result.add(validItem);
		return result;
	}

	/**
	 * Returns the value of the antiAliased flag.
	 */
	public boolean isAntiAliased() {
		return antiAliased;
	}

	public boolean isDebugging() {
		return debugging;
	}

	public String[] getValidMorphElements() {
		final Document doc = getDocument();
		if (doc == null)
			return new String[0];

		final Validator validator = doc.getValidator();
		if (validator == null)
			return new String[0];

		final Element element = doc.getElementAt(getCaretOffset());
		final Element parent = element.getParent();
		if (parent == null)
			// can't morph the root
			return new String[0];

		final List<QualifiedName> result = createCandidatesList(validator, parent, Validator.PCDATA, element.getQualifiedName());

		// root out those that can't contain the current content
		final List<QualifiedName> content = doc.getNodeNames(element.getStartOffset() + 1, element.getEndOffset());

		for (final Iterator<QualifiedName> iter = result.iterator(); iter.hasNext();) {
			final QualifiedName candidate = iter.next();
			if (!validator.isValidSequence(candidate, content, true))
				iter.remove();
		}

		Collections.sort(result, new QualifiedNameComparator());
		return result.toArray(new String[result.size()]);
	}

	public int getSelectionEnd() {
		return selectionEnd;
	}

	public int getSelectionStart() {
		return selectionStart;
	}

	public DocumentFragment getSelectedFragment() {
		if (hasSelection())
			return document.getFragment(getSelectionStart(), getSelectionEnd());
		else
			return null;
	}

	public String getSelectedText() {
		if (hasSelection())
			return document.getText(getSelectionStart(), getSelectionEnd());
		else
			return "";
	}

	public StyleSheet getStyleSheet() {
		return styleSheet;
	}

	public int getUndoDepth() {
		return undoDepth;
	}

	public int getLayoutWidth() {
		return layoutWidth;
	}

	public RootBox getRootBox() {
		return rootBox;
	}

	public boolean hasSelection() {
		return getSelectionStart() != getSelectionEnd();
	}

	public void insertChar(final char c) throws DocumentValidationException {
		if (hasSelection())
			deleteSelection();
		document.insertText(getCaretOffset(), Character.toString(c));
		this.moveBy(+1);
	}

	public void insertFragment(final DocumentFragment frag) throws DocumentValidationException {

		if (hasSelection())
			deleteSelection();

		document.insertFragment(getCaretOffset(), frag);
		this.moveTo(getCaretOffset() + frag.getLength());
	}

	public void insertElement(final Element element) throws DocumentValidationException {

		boolean success = false;
		try {
			beginWork();

			DocumentFragment frag = null;
			if (hasSelection()) {
				frag = getSelectedFragment();
				deleteSelection();
			}

			document.insertElement(getCaretOffset(), element);
			this.moveTo(getCaretOffset() + 1);
			if (frag != null)
				insertFragment(frag);
			scrollCaretVisible();
			success = true;
		} finally {
			endWork(success);
		}
	}

	public void insertText(final String text) throws DocumentValidationException {

		if (hasSelection())
			deleteSelection();

		boolean success = false;
		try {
			beginWork();
			int i = 0;
			for (;;) {
				final int j = text.indexOf('\n', i);
				if (j == -1)
					break;
				document.insertText(getCaretOffset(), text.substring(i, j));
				this.moveTo(getCaretOffset() + j - i);
				split();
				i = j + 1;
			}

			if (i < text.length()) {
				document.insertText(getCaretOffset(), text.substring(i));
				this.moveTo(getCaretOffset() + text.length() - i);
			}
			success = true;
		} finally {
			endWork(success);
		}
	}

	public void morph(final Element element) throws DocumentValidationException {

		final Document doc = getDocument();
		final int offset = getCaretOffset();
		final Element currentElement = doc.getElementAt(offset);

		if (currentElement == doc.getRootElement())
			throw new DocumentValidationException("Cannot morph the root element.");

		boolean success = false;
		try {
			beginWork();
			this.moveTo(currentElement.getStartOffset() + 1, false);
			this.moveTo(currentElement.getEndOffset(), true);
			final DocumentFragment frag = getSelectedFragment();
			deleteSelection();
			this.moveBy(-1, false);
			this.moveBy(2, true);
			deleteSelection();
			insertElement(element);
			if (frag != null)
				insertFragment(frag);
			this.moveTo(offset, false);
			success = true;
		} finally {
			endWork(success);
		}

	}

	public void moveBy(final int distance) {
		this.moveTo(getCaretOffset() + distance, false);
	}

	public void moveBy(final int distance, final boolean select) {
		this.moveTo(getCaretOffset() + distance, select);
	}

	public void moveTo(final int offset) {
		this.moveTo(offset, false);
	}

	public void moveTo(final int offset, final boolean select) {

		if (offset >= 1 && offset <= document.getLength() - 1) {

			// repaint the selection area, if any
			repaintCaret();
			repaintRange(getSelectionStart(), getSelectionEnd());

			final Element oldElement = currentElement;

			caretOffset = offset;

			currentElement = document.getElementAt(offset);

			if (select) {
				selectionStart = Math.min(mark, caretOffset);
				selectionEnd = Math.max(mark, caretOffset);

				// move selectionStart and selectionEnd to make sure we don't
				// select a partial element
				final Element commonElement = document.findCommonElement(selectionStart, selectionEnd);

				Element element = document.getElementAt(selectionStart);
				while (element != commonElement) {
					selectionStart = element.getStartOffset();
					element = document.getElementAt(selectionStart);
				}

				element = document.getElementAt(selectionEnd);
				while (element != commonElement) {
					selectionEnd = element.getEndOffset() + 1;
					element = document.getElementAt(selectionEnd);
				}

			} else {
				mark = offset;
				selectionStart = offset;
				selectionEnd = offset;
			}

			if (beginWorkCount == 0)
				relayout();

			final Graphics g = hostComponent.createDefaultGraphics();
			final LayoutContext context = createLayoutContext(g);
			caret = rootBox.getCaret(context, offset);

			Element element = getCurrentElement();
			if (element != oldElement) {
				caretColor = Color.BLACK;
				while (element != null) {
					final Color bgColor = styleSheet.getStyles(element).getBackgroundColor();
					if (bgColor != null) {
						final int red = ~bgColor.getRed() & 0xff;
						final int green = ~bgColor.getGreen() & 0xff;
						final int blue = ~bgColor.getBlue() & 0xff;
						caretColor = new Color(red, green, blue);
						break;
					}
					element = element.getParent();
				}
			}

			g.dispose();

			magicX = -1;

			scrollCaretVisible();

			hostComponent.fireSelectionChanged();

			caretVisible = true;

			repaintRange(getSelectionStart(), getSelectionEnd());
		}
	}

	public void moveToLineEnd(final boolean select) {
		this.moveTo(rootBox.getLineEndOffset(getCaretOffset()), select);
	}

	public void moveToLineStart(final boolean select) {
		this.moveTo(rootBox.getLineStartOffset(getCaretOffset()), select);
	}

	public void moveToNextLine(final boolean select) {
		final int x = magicX == -1 ? caret.getBounds().getX() : magicX;

		final Graphics g = hostComponent.createDefaultGraphics();
		final int offset = rootBox.getNextLineOffset(createLayoutContext(g), getCaretOffset(), x);
		g.dispose();

		this.moveTo(offset, select);
		magicX = x;
	}

	public void moveToNextPage(final boolean select) {
		final int x = magicX == -1 ? caret.getBounds().getX() : magicX;
		final int y = caret.getY() + Math.round(hostComponent.getViewport().getHeight() * 0.9f);
		this.moveTo(viewToModel(x, y), select);
		magicX = x;
	}

	public void moveToNextWord(final boolean select) {
		final Document doc = getDocument();
		final int n = doc.getLength() - 1;
		int offset = getCaretOffset();
		while (offset < n && !Character.isLetterOrDigit(doc.getCharacterAt(offset)))
			offset++;

		while (offset < n && Character.isLetterOrDigit(doc.getCharacterAt(offset)))
			offset++;

		this.moveTo(offset, select);
	}

	public void moveToPreviousLine(final boolean select) {
		final int x = magicX == -1 ? caret.getBounds().getX() : magicX;

		final Graphics g = hostComponent.createDefaultGraphics();
		final int offset = rootBox.getPreviousLineOffset(createLayoutContext(g), getCaretOffset(), x);
		g.dispose();

		this.moveTo(offset, select);
		magicX = x;
	}

	public void moveToPreviousPage(final boolean select) {
		final int x = magicX == -1 ? caret.getBounds().getX() : magicX;
		final int y = caret.getY() - Math.round(hostComponent.getViewport().getHeight() * 0.9f);
		this.moveTo(viewToModel(x, y), select);
		magicX = x;
	}

	public void moveToPreviousWord(final boolean select) {
		final Document doc = getDocument();
		int offset = getCaretOffset();
		while (offset > 1 && !Character.isLetterOrDigit(doc.getCharacterAt(offset - 1)))
			offset--;

		while (offset > 1 && Character.isLetterOrDigit(doc.getCharacterAt(offset - 1)))
			offset--;

		this.moveTo(offset, select);
	}

	/**
	 * Paints the contents of the widget in the given Graphics at the given
	 * point.
	 * 
	 * @param g
	 *            Graphics in which to draw the widget contents
	 * @param x
	 *            x-coordinate at which to draw the widget
	 * @param y
	 *            y-coordinate at which to draw the widget
	 */
	public void paint(final Graphics g, final int x, final int y) {

		if (rootBox == null)
			return;

		final LayoutContext context = createLayoutContext(g);

		// Since we may be scrolling to sections of the document that have
		// yet to be layed out, lay out any exposed area.
		//
		// TODO: this will probably be inaccurate, since we should really
		// iterate the layout, but we don't have an offset around which
		// to iterate...what to do, what to do....
		final Rectangle rect = g.getClipBounds();
		final int oldHeight = rootBox.getHeight();
		rootBox.layout(context, rect.getY(), rect.getY() + rect.getHeight());
		if (rootBox.getHeight() != oldHeight)
			hostComponent.setPreferredSize(rootBox.getWidth(), rootBox.getHeight());

		rootBox.paint(context, 0, 0);
		if (caretVisible)
			caret.draw(g, caretColor);

		// Debug hash marks
		/*
		 * ColorResource grey = g.createColor(new Color(160, 160, 160));
		 * ColorResource oldColor = g.setColor(grey); for (int y2 = rect.getY()
		 * - rect.getY() % 50; y2 < rect.getY() + rect.getHeight(); y2 += 50) {
		 * g.drawLine(x, y + y2, x+10, y + y2);
		 * g.drawString(Integer.toString(y2), x + 15, y + y2 - 10); }
		 * g.setColor(oldColor); grey.dispose();
		 */
	}

	public void paste() throws DocumentValidationException {
		throw new UnsupportedOperationException("Must be implemented in tookit-specific widget.");
	}

	public void pasteText() throws DocumentValidationException {
		throw new UnsupportedOperationException("Must be implemented in tookit-specific widget.");
	}

	public void redo() throws CannotRedoException {
		if (redoList.isEmpty())
			throw new CannotRedoException();
		final UndoableAndOffset event = redoList.removeLast();
		this.moveTo(event.caretOffset, false);
		event.edit.redo();
		undoList.add(event);
		undoDepth++;
	}

	public void removeAttribute(final String attributeName) {
		try {
			final Element element = getCurrentElement();
			if (element.getAttribute(attributeName) != null)
				element.removeAttribute(attributeName);
		} catch (final DocumentValidationException ex) {
			ex.printStackTrace(); // TODO: when can this happen?
		}
	}

	public void savePosition(final Runnable runnable) {
		final Position pos = getDocument().createPosition(getCaretOffset());
		try {
			runnable.run();
		} finally {
			this.moveTo(pos.getOffset());
		}
	}

	public void selectAll() {
		this.moveTo(1);
		this.moveTo(getDocument().getLength() - 1, true);
	}

	public void selectWord() {
		final Document doc = getDocument();
		int startOffset = getCaretOffset();
		int endOffset = getCaretOffset();
		while (startOffset > 1 && Character.isLetterOrDigit(doc.getCharacterAt(startOffset - 1)))
			startOffset--;
		final int n = doc.getLength() - 1;
		while (endOffset < n && Character.isLetterOrDigit(doc.getCharacterAt(endOffset)))
			endOffset++;

		if (startOffset < endOffset) {
			this.moveTo(startOffset, false);
			this.moveTo(endOffset, true);
		}
	}

	/**
	 * Sets the value of the antiAliased flag.
	 * 
	 * @param antiAliased
	 *            if true, text is rendered using antialiasing.
	 */
	public void setAntiAliased(final boolean antiAliased) {
		this.antiAliased = antiAliased;
	}

	public void setAttribute(final String attributeName, final String value) {
		try {
			final Element element = getCurrentElement();
			if (value == null)
				removeAttribute(attributeName);
			else if (!value.equals(element.getAttribute(attributeName)))
				element.setAttribute(attributeName, value);
		} catch (final DocumentValidationException ex) {
			ex.printStackTrace(); // TODO: mebbe throw the exception instead
		}
	}

	public void setBoxFactory(final BoxFactory boxFactory) {
		this.boxFactory = boxFactory;
		if (document != null)
			relayout();
	}

	public void setDebugging(final boolean debugging) {
		this.debugging = debugging;
	}

	public void setDocument(final Document document, final StyleSheet styleSheet) {
		if (this.document != null) {
			final Document doc = document;
			doc.removeDocumentListener(documentListener);
		}

		this.document = document;
		this.styleSheet = styleSheet;

		undoList = new LinkedList<UndoableAndOffset>();
		undoDepth = 0;
		redoList = new LinkedList<UndoableAndOffset>();
		beginWorkCount = 0;
		compoundEdit = null;

		createRootBox();

		this.moveTo(1);
		this.document.addDocumentListener(documentListener);
	}

	/**
	 * Called by the host component when it gains or loses focus.
	 * 
	 * @param focus
	 *            true if the host component has focus
	 */
	public void setFocus(final boolean focus) {
		caretVisible = true;
		repaintCaret();
	}

	public void setLayoutWidth(int width) {
		width = Math.max(width, MIN_LAYOUT_WIDTH);
		if (getDocument() != null && width != getLayoutWidth())
			// this.layoutWidth is set by relayoutAll
			relayoutAll(width, styleSheet);
		else
			// maybe doc is null. Let's store layoutWidth so it's right
			// when we set a doc
			layoutWidth = width;
	}

	public void setStyleSheet(final StyleSheet styleSheet) {
		if (getDocument() != null)
			relayoutAll(layoutWidth, styleSheet);
	}

	public void setStyleSheet(final URL ssUrl) throws IOException {
		final StyleSheetReader reader = new StyleSheetReader();
		final StyleSheet ss = reader.read(ssUrl);
		this.setStyleSheet(ss);
	}

	public void split() throws DocumentValidationException {

		final long start = System.currentTimeMillis();

		final Document doc = getDocument();
		Element element = doc.getElementAt(getCaretOffset());
		Styles styles = getStyleSheet().getStyles(element);
		while (!styles.isBlock()) {
			element = element.getParent();
			styles = getStyleSheet().getStyles(element);
		}

		boolean success = false;
		try {
			beginWork();
			if (styles.getWhiteSpace().equals(CSS.PRE)) {
				// can't call this.insertText() or we'll get an infinite loop
				final int offset = getCaretOffset();
				doc.insertText(offset, "\n");
				this.moveTo(offset + 1);
			} else {
				DocumentFragment frag = null;
				int offset = getCaretOffset();
				final boolean atEnd = offset == element.getEndOffset();
				if (!atEnd) {
					this.moveTo(element.getEndOffset(), true);
					frag = getSelectedFragment();
					deleteSelection();
				}

				// either way, we are now at the end offset for the element
				// let's move just outside
				this.moveTo(getCaretOffset() + 1);

				insertElement(new Element(element.getQualifiedName()));
				// TODO: clone attributes

				if (!atEnd) {
					offset = getCaretOffset();
					insertFragment(frag);
					this.moveTo(offset, false);
				}
			}
			success = true;
		} finally {
			endWork(success);
		}

		if (isDebugging()) {
			final long end = System.currentTimeMillis();
			System.out.println("split() took " + (end - start) + "ms");
		}
	}

	/**
	 * Toggles the caret to produce a flashing caret effect. This method should
	 * be called from the GUI event thread at regular intervals.
	 */
	public void toggleCaret() {
		caretVisible = !caretVisible;
		repaintCaret();
	}

	public void undo() throws CannotUndoException {
		if (undoList.isEmpty())
			throw new CannotUndoException();
		final UndoableAndOffset event = undoList.removeLast();
		undoDepth--;
		event.edit.undo();
		this.moveTo(event.caretOffset, false);
		redoList.add(event);
	}

	public int viewToModel(final int x, final int y) {
		final Graphics g = hostComponent.createDefaultGraphics();
		final LayoutContext context = createLayoutContext(g);
		final int offset = rootBox.viewToModel(context, x, y);
		g.dispose();
		return offset;
	}

	// ================================================== PRIVATE

	/**
	 * Captures an UndoableAction and the offset at which it occurred.
	 */
	private static class UndoableAndOffset {
		public IUndoableEdit edit;
		public int caretOffset;

		public UndoableAndOffset(final IUndoableEdit edit, final int caretOffset) {
			this.edit = edit;
			this.caretOffset = caretOffset;
		}
	}

	/**
	 * Processes the given edit, adding it to the undo stack.
	 * 
	 * @param edit
	 *            The edit to process.
	 * @param caretOffset
	 *            Offset of the caret before the edit occurred. If the edit is
	 *            undone, the caret is returned to this offset.
	 */
	private void addEdit(final IUndoableEdit edit, final int caretOffset) {

		if (edit == null)
			return;

		if (compoundEdit != null)
			compoundEdit.addEdit(edit);
		else if (!undoList.isEmpty() && undoList.getLast().edit.combine(edit))
			return;
		else {
			undoList.add(new UndoableAndOffset(edit, caretOffset));
			undoDepth++;
			if (undoList.size() > MAX_UNDO_STACK_SIZE)
				undoList.removeFirst();
			redoList.clear();
		}
	}

	/**
	 * Creates a layout context given a particular graphics context.
	 * 
	 * @param g
	 *            The graphics context to use for the layout context.
	 * @return the new layout context
	 */
	private LayoutContext createLayoutContext(final Graphics g) {
		final LayoutContext context = new LayoutContext();
		context.setBoxFactory(getBoxFactory());
		context.setDocument(getDocument());
		context.setGraphics(g);
		context.setStyleSheet(getStyleSheet());

		if (hasSelection()) {
			context.setSelectionStart(getSelectionStart());
			context.setSelectionEnd(getSelectionEnd());
		} else {
			context.setSelectionStart(getCaretOffset());
			context.setSelectionEnd(getCaretOffset());
		}

		return context;
	}

	private void createRootBox() {
		final Graphics g = hostComponent.createDefaultGraphics();
		final LayoutContext context = createLayoutContext(g);
		rootBox = new RootBox(context, document.getRootElement(), getLayoutWidth());
		g.dispose();
	}

	/**
	 * Invalidates the box tree due to document changes. The lowest box that
	 * completely encloses the changed element is invalidated.
	 * 
	 * @param element
	 *            Element for which to search.
	 */
	private void invalidateElementBox(final Element element) {

		final BlockBox elementBox = (BlockBox) this.findInnermostBox(new IBoxFilter() {
			public boolean matches(final Box box) {
				return box instanceof BlockBox && box.getElement() != null && box.getStartOffset() <= element.getStartOffset() + 1
						&& box.getEndOffset() >= element.getEndOffset();
			}
		});

		if (elementBox != null)
			elementBox.invalidate(true);
	}

	/**
	 * Returns true if the given offset represents the boundary between two
	 * different elements with the same name and parent. This is used to
	 * determine if the elements can be joined via joinElementsAt.
	 * 
	 * @param int offset The offset to check.
	 */
	private boolean isBetweenMatchingElements(final int offset) {
		if (offset <= 1 || offset >= getDocument().getLength() - 1)
			return false;
		final Element e1 = getDocument().getElementAt(offset - 1);
		final Element e2 = getDocument().getElementAt(offset + 1);
		return e1 != e2 && e1.getParent() == e2.getParent() && e1.getQualifiedName().equals(e2.getQualifiedName());
	}

	/**
	 * Calls layout() on the rootBox until the y-coordinate of a caret at the
	 * given offset converges, i.e. is less than LAYOUT_TOLERANCE pixels from
	 * the last call.
	 * 
	 * @param offset
	 *            Offset around which we should lay out boxes.
	 */
	private void iterateLayout(final int offset) {

		int repaintStart = Integer.MAX_VALUE;
		int repaintEnd = 0;
		final Graphics g = hostComponent.createDefaultGraphics();
		final LayoutContext context = createLayoutContext(g);
		int layoutY = rootBox.getCaret(context, offset).getY();

		while (true) {

			final int oldLayoutY = layoutY;
			final IntRange repaintRange = rootBox.layout(context, layoutY - LAYOUT_WINDOW / 2, layoutY + LAYOUT_WINDOW / 2);
			if (repaintRange != null) {
				repaintStart = Math.min(repaintStart, repaintRange.getStart());
				repaintEnd = Math.max(repaintEnd, repaintRange.getEnd());
			}

			layoutY = rootBox.getCaret(context, offset).getY();
			if (Math.abs(layoutY - oldLayoutY) < LAYOUT_TOLERANCE)
				break;
		}
		g.dispose();

		if (repaintStart < repaintEnd) {
			final Rectangle viewport = hostComponent.getViewport();
			if (repaintStart < viewport.getY() + viewport.getHeight() && repaintEnd > viewport.getY()) {
				final int start = Math.max(repaintStart, viewport.getY());
				final int end = Math.min(repaintEnd, viewport.getY() + viewport.getHeight());
				hostComponent.repaint(viewport.getX(), start, viewport.getWidth(), end - start);
			}
		}
	}

	/**
	 * Joins the elements at the given offset. Only works if
	 * isBetweenMatchingElements returns true for the same offset. Afterwards,
	 * the caret is left at the point where the join occurred.
	 * 
	 * @param offset
	 *            Offset where the two elements meet.
	 */
	private void joinElementsAt(final int offset) throws DocumentValidationException {

		if (!isBetweenMatchingElements(offset))
			throw new DocumentValidationException("Cannot join elements at offset " + offset);

		boolean success = false;
		try {
			beginWork();
			this.moveTo(offset + 1);
			final Element element = getCurrentElement();
			final boolean moveContent = !element.isEmpty();
			DocumentFragment frag = null;
			if (moveContent) {
				this.moveTo(element.getEndOffset(), true);
				frag = getSelectedFragment();
				deleteSelection();
			}
			this.moveBy(-1);
			this.moveBy(2, true);
			deleteSelection();
			this.moveBy(-1);
			if (moveContent) {
				final int savedOffset = getCaretOffset();
				insertFragment(frag);
				this.moveTo(savedOffset, false);
			}
			success = true;
		} finally {
			endWork(success);
		}
	}

	/**
	 * Lay out the area around the caret.
	 */
	private void relayout() {

		final long start = System.currentTimeMillis();

		final int oldHeight = rootBox.getHeight();

		iterateLayout(getCaretOffset());

		if (rootBox.getHeight() != oldHeight)
			hostComponent.setPreferredSize(rootBox.getWidth(), rootBox.getHeight());

		final Graphics g = hostComponent.createDefaultGraphics();
		final LayoutContext context = createLayoutContext(g);
		caret = rootBox.getCaret(context, getCaretOffset());
		g.dispose();

		if (isDebugging()) {
			final long end = System.currentTimeMillis();
			System.out.println("VexWidget layout took " + (end - start) + "ms");
		}
	}

	/**
	 * Re-layout the entire widget, due to either a layout width change or a
	 * stylesheet range. This method does the actual setting of the width and
	 * stylesheet, since it needs to know where the caret is <i>before</i> the
	 * change, so that it can do a reasonable job of restoring the position of
	 * the viewport after the change.
	 * 
	 * @param newWidth
	 *            New width for the widget.
	 * @param newStyleSheet
	 *            New stylesheet for the widget.
	 */
	private void relayoutAll(final int newWidth, final StyleSheet newStyleSheet) {

		final Graphics g = hostComponent.createDefaultGraphics();
		LayoutContext context = createLayoutContext(g);

		final Rectangle viewport = hostComponent.getViewport();

		// true if the caret is within the viewport
		//
		// TODO: incorrect if caret near the bottom and the viewport is
		// shrinking
		// To fix, we probably need to save the viewport height, just like
		// we now store viewport width (as layout width).
		final boolean caretVisible = viewport.intersects(caret.getBounds());

		// distance from the top of the viewport to the top of the caret
		// use this if the caret is visible in the viewport
		int relCaretY = 0;

		// offset around which we are laying out
		// this is also where we put the top of the viewport if the caret
		// isn't visible
		int offset;

		if (caretVisible) {
			relCaretY = caret.getY() - viewport.getY();
			offset = getCaretOffset();
		} else
			offset = rootBox.viewToModel(context, 0, viewport.getY());

		layoutWidth = newWidth;
		styleSheet = newStyleSheet;

		// Re-create the context, since it holds the old stylesheet
		context = createLayoutContext(g);

		createRootBox();

		iterateLayout(offset);

		hostComponent.setPreferredSize(rootBox.getWidth(), rootBox.getHeight());

		caret = rootBox.getCaret(context, getCaretOffset());

		if (caretVisible) {
			int viewportY = caret.getY() - Math.min(relCaretY, viewport.getHeight());
			viewportY = Math.min(rootBox.getHeight() - viewport.getHeight(), viewportY);
			viewportY = Math.max(0, viewportY); // this must appear after the
												// above line, since
			// that line might set viewportY negative
			hostComponent.scrollTo(viewport.getX(), viewportY);
			scrollCaretVisible();
		} else {
			final int viewportY = rootBox.getCaret(context, offset).getY();
			hostComponent.scrollTo(viewport.getX(), viewportY);
		}

		hostComponent.repaint();

		g.dispose();

	}

	/**
	 * Repaints the area of the caret.
	 */
	private void repaintCaret() {
		if (caret != null) {
			// caret may be null when document is first set
			final Rectangle bounds = caret.getBounds();
			hostComponent.repaint(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
		}
	}

	/**
	 * Repaints area of the control corresponding to a range of offsets in the
	 * document.
	 * 
	 * @param startOffset
	 *            Starting offset of the range.
	 * @param endOffset
	 *            Ending offset of the range.
	 */
	private void repaintRange(final int startOffset, final int endOffset) {

		final Graphics g = hostComponent.createDefaultGraphics();

		final LayoutContext context = createLayoutContext(g);

		final Rectangle startBounds = rootBox.getCaret(context, startOffset).getBounds();
		final int top1 = startBounds.getY();
		final int bottom1 = top1 + startBounds.getHeight();

		final Rectangle endBounds = rootBox.getCaret(context, endOffset).getBounds();
		final int top2 = endBounds.getY();
		final int bottom2 = top2 + endBounds.getHeight();

		final int top = Math.min(top1, top2);
		final int bottom = Math.max(bottom1, bottom2);
		if (top == bottom)
			// Account for zero-height horizontal carets
			hostComponent.repaint(0, top - 1, getLayoutWidth(), bottom - top + 1);
		else
			hostComponent.repaint(0, top, getLayoutWidth(), bottom - top);

		g.dispose();
	}

	private void scrollCaretVisible() {

		final Rectangle caretBounds = caret.getBounds();
		final Rectangle viewport = hostComponent.getViewport();

		final int x = viewport.getX();
		int y = 0;
		final int offset = getCaretOffset();
		if (offset == 1)
			y = 0;
		else if (offset == getDocument().getLength() - 1) {
			if (rootBox.getHeight() < viewport.getHeight())
				y = 0;
			else
				y = rootBox.getHeight() - viewport.getHeight();
		} else if (caretBounds.getY() < viewport.getY())
			y = caretBounds.getY();
		else if (caretBounds.getY() + caretBounds.getHeight() > viewport.getY() + viewport.getHeight())
			y = caretBounds.getY() + caretBounds.getHeight() - viewport.getHeight();
		else
			// no scrolling required
			return;
		hostComponent.scrollTo(x, y);
	}

}
