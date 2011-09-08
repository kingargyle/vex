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
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.dom;

import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.wst.xml.vex.core.internal.validator.AttributeDefinition;

/**
 * Represents an object that can validate the structure of a document.
 * Validators must be serializable.
 * @model
 */
public interface Validator {

	/**
	 * QualifiedName indicating that character data is allowed at the given point in
	 * the document.
	 * @model
	 */
	public static final QualifiedName PCDATA = new QualifiedName(null, "#PCDATA");

	/**
	 * Returns the AttributeDefinition for a particular attribute.
	 * 
	 * @param element
	 *            Name of the element.
	 * @param attribute
	 *            Name of the attribute.
	 * @model
	 */
	public AttributeDefinition getAttributeDefinition(Attribute attribute);

	/**
	 * Returns the attribute definitions that apply to the given element.
	 * 
	 * @param element
	 *            the element to check.
	 * @model
	 */
	public List<AttributeDefinition> getAttributeDefinitions(Element element);

	/**
	 * Returns a set of QualifiedNames representing valid root elements for the given
	 * document type.
	 * @model 
	 */
	public Set<QualifiedName> getValidRootElements();

	/**
	 * Returns a set of QualifiedNames representing items that are valid at point in
	 * the child nodes of a given element. Each string is either an element name
	 * or Validator.PCDATA.
	 * 
	 * @param element
	 *            the parent element.
	 * @model 
	 */
	public Set<QualifiedName> getValidItems(final Element element);

	/**
	 * Returns true if the given sequence is valid for the given element.
	 * Accepts three sequences, which will be concatenated before doing the
	 * check.
	 * 
	 * @param element
	 *            Name of the element being tested.
	 * @param nodes
	 *            Array of element names and Validator.PCDATA.
	 * @param partial
	 *            If true, an valid but incomplete sequence is acceptable.
	 * @model
	 */
	public boolean isValidSequence(QualifiedName element, List<QualifiedName> nodes,
			boolean partial);

	/**
	 * Returns true if the given sequence is valid for the given element.
	 * Accepts three sequences, which will be concatenated before doing the
	 * check.
	 * 
	 * @param element
	 *            Name of the element being tested.
	 * @param seq1
	 *            List of element names and Validator.PCDATA.
	 * @param seq2
	 *            List of element names and Validator.PCDATA. May be null or
	 *            empty.
	 * @param seq3
	 *            List of element names and Validator.PCDATA. May be null or
	 *            empty.
	 * @param partial
	 *            If true, an valid but incomplete sequence is acceptable.
	 * @model
	 */
	public boolean isValidSequence(QualifiedName element, List<QualifiedName> seq1,
			List<QualifiedName> seq2, List<QualifiedName> seq3, boolean partial);

}
