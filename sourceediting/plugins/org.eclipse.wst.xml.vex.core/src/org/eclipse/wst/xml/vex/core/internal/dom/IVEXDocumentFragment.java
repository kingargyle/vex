package org.eclipse.wst.xml.vex.core.internal.dom;
/*
 * @model
 */
public interface IVEXDocumentFragment {

	/**
	 * Mime type representing document fragments: "text/x-vex-document-fragment"
	 * @model
	 */
	public static final String MIME_TYPE = "application/x-vex-document-fragment";

	/**
	 * Returns the Content object holding this fragment's content.
	 * 
	 * @model
	 */
	public abstract Content getContent();

	/**
	 * Returns the number of characters, including sentinels, represented by the
	 * fragment.
	 * 
	 * @model
	 */
	public abstract int getLength();

	/**
	 * Returns the elements that make up this fragment.
	 * 
	 * @model
	 */
	public abstract IVEXElement[] getElements();

	/**
	 * Returns an array of element names and Validator.PCDATA representing the
	 * content of the fragment.
	 * 
	 * @model
	 */
	public abstract String[] getNodeNames();

	/**
	 * Returns the nodes that make up this fragment, including elements and
	 * <code>Text</code> objects.
	 * 
	 * @model
	 */
	public abstract IVEXNode[] getNodes();

}