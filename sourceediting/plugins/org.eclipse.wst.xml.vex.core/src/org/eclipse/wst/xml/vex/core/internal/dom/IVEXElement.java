package org.eclipse.wst.xml.vex.core.internal.dom;

import java.util.Iterator;

/**
 * 
 * @author dcarver
 *
 * @model
 */
public interface IVEXElement extends IVEXNode {


	/**
	 * Adds the given child to the end of the child list. Sets the parent
	 * attribute of the given element to this element.
	 * @model
	 */
	public abstract void addChild(IVEXElement child);

	/**
	 * Clones the element and its attributes. The returned element has no parent
	 * or children.
	 * @model
	 */
	public abstract Object clone();

	/**
	 * Returns the value of an attribute given its name. If no such attribute
	 * exists, returns null.
	 * 
	 * @param name
	 *            Name of the attribute.
	 * @model
	 */
	public abstract String getAttribute(String name);

	/**
	 * Returns an array of names of the attributes in the element.
	 * @model
	 */
	public abstract String[] getAttributeNames();

	/**
	 * Returns an iterator over the children. Used by
	 * <code>Document.delete</code> to safely delete children.
	 * @model
	 */
	public abstract Iterator getChildIterator();

	/**
	 * Returns an array of the elements children.
	 * @model
	 */
	public abstract IVEXElement[] getChildElements();

	/**
	 * Returns an array of nodes representing the content of this element. The
	 * array includes child elements and runs of text returned as
	 * <code>Text</code> objects.
	 * @model
	 */
	public abstract IVEXNode[] getChildNodes();

	/**
	 * @return The document to which this element belongs. Returns null if this
	 *         element is part of a document fragment.
	 * @model
	 */
	public abstract IVEXDocument getDocument();

	/**
	 * Returns the name of the element.
	 * @model
	 */
	public abstract String getName();

	/**
	 * Returns the parent of this element, or null if this is the root element.
	 * @model
	 */
	public abstract IVEXElement getParent();

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.wst.xml.vex.core.internal.dom.IVEXNode#getText()
	 * @model
	 */
	public abstract String getText();

	/**
	 * Returns true if the element has no content.
	 * @model
	 */
	public abstract boolean isEmpty();

	/**
	 * Removes the given attribute from the array.
	 * 
	 * @param name
	 *            name of the attribute to remove.
	 * @model
	 */
	public abstract void removeAttribute(String name)
			throws DocumentValidationException;

	/**
	 * Sets the value of an attribute for this element.
	 * 
	 * @param name
	 *            Name of the attribute to be set.
	 * @param value
	 *            New value for the attribute. If null, this call has the same
	 *            effect as removeAttribute(name).
	 * @model
	 */
	public abstract void setAttribute(String name, String value)
			throws DocumentValidationException;

	/**
	 * Sets the parent of this element.
	 * 
	 * @param parent
	 *            Parent element.
	 * @model
	 */
	public abstract void setParent(IVEXElement parent);

	/**
	 * 
	 * @return
	 * @model
	 */
	public abstract String toString();

	/**
	 * 
	 * @param content
	 * @param offset
	 * @param i
	 * @model
	 */
	public abstract void setContent(Content content, int offset, int i);
	
	/**
	 * 
	 * @param index
	 * @param child
	 * @model
	 */
	public abstract void insertChild(int index, IVEXElement child);

}