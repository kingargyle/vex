package org.eclipse.wst.xml.vex.core.internal.dom;

/*
 * @model
 */
public interface IVEXRootElement extends IVEXElement{

	/**
	 * @return The document associated with this element.
	 * 
	 * @model
	 */
	public abstract IVEXDocument getDocument();

	/**
	 * Sets the document to which this element is associated. This is called by
	 * the document constructor, so it need not be called by client code.
	 * 
	 * @param document
	 *            Document to which this root element is associated.
	 *            
	 */
	public abstract void setDocument(IVEXDocument document);

}