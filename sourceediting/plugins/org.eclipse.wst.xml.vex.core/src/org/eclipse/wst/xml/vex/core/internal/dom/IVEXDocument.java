package org.eclipse.wst.xml.vex.core.internal.dom;

public interface IVEXDocument {

	/**
	 * Adds a document listener to the list of listeners to be notified of
	 * document changes.
	 * 
	 * @param listener
	 *            <code>DocumentListener</code> to add.
	 * @model
	 */
	public abstract void addDocumentListener(DocumentListener listener);

	/**
	 * Returns true if the given document fragment can be inserted at the given
	 * offset.
	 * 
	 * @param offset
	 *            offset where the insertion is to occur
	 * @param fragment
	 *            fragment to be inserted
	 * @model
	 */
	public abstract boolean canInsertFragment(int offset,
			IVEXDocumentFragment fragment);

	/**
	 * Returns true if text can be inserted at the given offset.
	 * 
	 * @param offset
	 *            offset where the insertion is to occur
	 * @model
	 */
	public abstract boolean canInsertText(int offset);

	/**
	 * Creates a <code>Position</code> object at the given character offset.
	 * 
	 * @param offset
	 *            initial character offset of the position
	 * @model
	 */
	public abstract Position createPosition(int offset);

	/**
	 * Deletes a portion of the document. No element may straddle the deletion
	 * span.
	 * 
	 * @param startOffset
	 *            start of the range to delete
	 * @param endOffset
	 *            end of the range to delete
	 * @throws DocumentValidationException
	 *             if the change would result in an invalid document.
	 * @model
	 */
	public abstract void delete(int startOffset, int endOffset)
			throws DocumentValidationException;

	/**
	 * Finds the lowest element that contains both of the given offsets.
	 * 
	 * @param offset1
	 *            the first offset
	 * @param offset2
	 *            the second offset
	 * @model
	 */
	public abstract IVEXElement findCommonElement(int offset1, int offset2);

	/**
	 * Returns the character at the given offset.
	 * 
	 * @model
	 */
	public abstract char getCharacterAt(int offset);

	/**
	 * Returns the element at the given offset. The given offset must be greater
	 * or equal to 1 and less than the current document length.
	 * 
	 * @model
	 */
	public abstract IVEXElement getElementAt(int offset);

	/**
	 * Returns the encoding used for this document, or null if no encoding has
	 * been declared.
	 * 
	 * @model
	 */
	public abstract String getEncoding();

	/**
	 * Create a <code>DocumentFragment</code> representing the given range of
	 * offsets.
	 * 
	 * @return
	 * 
	 * @model
	 */
	public abstract IVEXDocumentFragment getFragment(int startOffset, int endOffset);

	/**
	 * Returns the length of the document in characters, including the null
	 * characters that delimit each element.
	 * 
	 * @model
	 */
	public abstract int getLength();

	/**
	 * Returns an array of element names and Validator.PCDATA representing the
	 * content between the given offsets. The given offsets must both be
	 * directly in the same element.
	 * 
	 * @param startOffset
	 *            the offset at which the sequence begins
	 * @param endOffset
	 *            the offset at which the sequence ends
	 * @model
	 */
	public abstract String[] getNodeNames(int startOffset, int endOffset);

	/**
	 * Returns an array of Nodes representing the selected range. The given
	 * offsets must both be directly in the same element.
	 * 
	 * @param startOffset
	 *            the offset at which the sequence begins
	 * @param endOffset
	 *            the offset at which the sequence ends
	 *            
	 * @model
	 */
	public abstract IVEXNode[] getNodes(int startOffset, int endOffset);

	/**
	 * Returns the public ID of the document type.
	 * 
	 * @model
	 */
	public abstract String getPublicID();

	/**
	 * Returns the text between the two given offsets. Unlike getText, sentinel
	 * characters are not removed.
	 * 
	 * @param startOffset
	 *            character offset of the start of the text
	 * @param endOffset
	 *            character offset of the end of the text
	 * @model
	 */
	public abstract String getRawText(int startOffset, int endOffset);

	/**
	 * Returns the root element of this document.
	 * 
	 * @model
	 */
	public abstract IVEXElement getRootElement();

	/**
	 * Returns the system ID of the document type.
	 */
	public abstract String getSystemID();

	/**
	 * Returns the text between the two given offsets. Sentinal characters are
	 * removed.
	 * 
	 * @param startOffset
	 *            character offset of the start of the text
	 * @param endOffset
	 *            character offset of the end of the text
	 */
	public abstract String getText(int startOffset, int endOffset);

	/**
	 * Returns the validator used to validate the document, or null if a
	 * validator has not been set. Note that the DocumentFactory does not
	 * automatically create a validator.
	 */
	public abstract Validator getValidator();

	/**
	 * Inserts an element at the given position.
	 * 
	 * @param offset
	 *            character offset at which the element is to be inserted. Must
	 *            be greater or equal to 1 and less than the current length of
	 *            the document, i.e. it must be within the range of the root
	 *            element.
	 * @param defaults
	 *            element to insert
	 * @throws DocumentValidationException
	 *             if the change would result in an invalid document.
	 */
	public abstract void insertElement(int offset, IVEXElement defaults)
			throws DocumentValidationException;

	/**
	 * Inserts a document fragment at the given position.
	 * 
	 * @param offset
	 *            character offset at which the element is to be inserted. Must
	 *            be greater or equal to 1 and less than the current length of
	 *            the document, i.e. it must be within the range of the root
	 *            element.
	 * @param fragment
	 *            fragment to insert
	 * @throws DocumentValidationException
	 *             if the change would result in an invalid document.
	 */
	public abstract void insertFragment(int offset, IVEXDocumentFragment fragment)
			throws DocumentValidationException;

	/**
	 * Inserts text at the given position.
	 * 
	 * @param offset
	 *            character offset at which the text is to be inserted. Must be
	 *            greater or equal to 1 and less than the current length of the
	 *            document, i.e. it must be within the range of the root
	 *            element.
	 * @param text
	 *            text to insert
	 * @return UndoableEdit that can be used to undo the deletion
	 * @throws DocumentValidationException
	 *             if the change would result in an invalid document.
	 */
	public abstract void insertText(int offset, String text)
			throws DocumentValidationException;

	/**
	 * Returns true if undo is enabled, that is, undoable edit events are fired
	 * to registered listeners.
	 */
	public abstract boolean isUndoEnabled();

	/**
	 * Removes a document listener from the list of listeners so that it is no
	 * longer notified of document changes.
	 * 
	 * @param listener
	 *            <code>DocumentListener</code> to remove.
	 */
	public abstract void removeDocumentListener(DocumentListener listener);

	/**
	 * Sets the public ID for the document's document type.
	 * 
	 * @param publicID
	 *            New value for the public ID.
	 */
	public abstract void setPublicID(String publicID);

	/**
	 * Sets the system ID for the document's document type.
	 * 
	 * @param systemID
	 *            New value for the system ID.
	 */
	public abstract void setSystemID(String systemID);

	/**
	 * Sets whether undo events are enabled. Typically, undo events are disabled
	 * while an edit is being undone or redone.
	 * 
	 * @param undoEnabled
	 *            If true, undoable edit events are fired to registered
	 *            listeners.
	 */
	public abstract void setUndoEnabled(boolean undoEnabled);

	/**
	 * Sets the validator to use for this document.
	 * 
	 * @param validator
	 *            Validator to use for this document.
	 */
	public abstract void setValidator(Validator validator);

	public abstract void fireAttributeChanged(DocumentEvent documentEvent);

}