/*******************************************************************************
 * Copyright (c) 2004, 2008 John Krasnay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     John Krasnay - initial API and implementation
 *     David Carver (STAR) - Migrated to WTP Content Model
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.validator;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.wst.xml.core.internal.contentmodel.CMAttributeDeclaration;
import org.eclipse.wst.xml.core.internal.contentmodel.CMContent;
import org.eclipse.wst.xml.core.internal.contentmodel.CMDataType;
import org.eclipse.wst.xml.core.internal.contentmodel.CMDocument;
import org.eclipse.wst.xml.core.internal.contentmodel.CMElementDeclaration;
import org.eclipse.wst.xml.core.internal.contentmodel.CMGroup;
import org.eclipse.wst.xml.core.internal.contentmodel.CMNode;
import org.eclipse.wst.xml.core.internal.contentmodel.CMNodeList;
import org.eclipse.wst.xml.core.internal.contentmodel.ContentModelManager;
import org.eclipse.wst.xml.core.internal.contentmodel.modelquery.ModelQuery;
import org.eclipse.wst.xml.core.internal.modelquery.ModelQueryUtil;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IValidator;
import org.eclipse.wst.xml.vex.core.internal.validator.AttributeDefinition.Type;
import org.eclipse.wst.xml.vex.core.internal.validator.DFABuilder.Node;

/**
 * A validator driven by a DTD.
 */
public class DTDValidator extends AbstractValidator {

	// DFA representing an EMPTY element; just a single non-accepting state
	// with no transitions.
	private static final DFAState emptyDFA = new DFAState();

	// map element names to DFAs
	private Map elementDFAs = new HashMap();

	// list of all element names plus PCDATA
	private Set anySet;

	// map element names to arrays of AttributeDefinition objects
	private Map attributeArrays = new HashMap();

	// map element names to maps of attribute name to attribute def
	private Map attributeMaps = new HashMap();
	
	private static CMDocument contentModel;

	/**
	 * Creates a instance of DtdValidator given a URL.
	 * 
	 * @param url
	 *            URL of the DTD file to use.
	 */
	@SuppressWarnings( { "restriction", "unchecked" })
	public static DTDValidator create(URL url) throws IOException {

		// Compute the DFAs for each element in the DTD

		ContentModelManager contentModelManager = ContentModelManager
				.getInstance();
		String resolved = url.toString();
		contentModel = contentModelManager.createCMDocument(
				resolved, null);

		DTDValidator validator = new DTDValidator();
		Iterator iter = contentModel.getElements().iterator();
		while (iter.hasNext()) {
			CMElementDeclaration element = (CMElementDeclaration) iter.next();
			String elementName = element.getNodeName();
			int elementContentType = element.getContentType();
			DFAState dfa;
			if (element.getContentType() == CMElementDeclaration.EMPTY) {
				dfa = emptyDFA;
			} else if (element.getContentType() == CMElementDeclaration.ANY) {
				dfa = null;
			} else {
				DFABuilder.Node node = createDFANode(element);
				dfa = DFABuilder.createDFA(node);
			}
			validator.elementDFAs.put(element.getElementName(), dfa);

			Map defMap = new HashMap();
			AttributeDefinition[] defArray = new AttributeDefinition[element
					.getAttributes().getLength()];
			int i = 0;

			Iterator iter2 = element.getAttributes().iterator();
			while (iter2.hasNext()) {
				CMAttributeDeclaration attr = (CMAttributeDeclaration) iter2
						.next();
				AttributeDefinition.Type type;
				String[] values = null;
				if (attr.getAttrType().equals(CMDataType.ENUM)) {
					type = AttributeDefinition.Type.ENUMERATION;
					values = attr.getAttrType().getEnumeratedValues();
				} else if (attr.getAttrType().equals(CMDataType.NOTATION)) {
					type = AttributeDefinition.Type.ENUMERATION;
					values = attr.getAttrType().getEnumeratedValues();
				} else
					type = AttributeDefinition.Type.get(attr.getAttrType()
							.getDataTypeName());

				AttributeDefinition ad = new AttributeDefinition(attr
						.getAttrName(), type, attr.getDefaultValue(), values,
						attr.getUsage() == CMAttributeDeclaration.REQUIRED,
						attr.getUsage() == CMAttributeDeclaration.FIXED);

				defMap.put(attr.getAttrName(), ad);
				defArray[i] = ad;

				i++;
			}
			validator.attributeMaps.put(element.getElementName(), defMap);
			Arrays.sort(defArray);
			validator.attributeArrays.put(element.getElementName(), defArray);
		}

		// Calculate anySet

		validator.anySet = new HashSet();
		validator.anySet.addAll(validator.elementDFAs.keySet());
		validator.anySet.add(IValidator.PCDATA);

		return validator;
	}

	public AttributeDefinition getAttributeDefinition(String element,
			String attribute) {
		Map attrMap = (Map) this.attributeMaps.get(element);
		return attrMap == null ? null : (AttributeDefinition) attrMap
				.get(attribute);
	}

	public AttributeDefinition[] getAttributeDefinitions(String element) {
		if (this.attributeArrays.containsKey(element)) {
			return (AttributeDefinition[]) this.attributeArrays.get(element);
		} else {
			return new AttributeDefinition[0];
		}
	}

//	public Set getValidRootElements() {
//		return this.elementDFAs.keySet();
//	}
	
	@SuppressWarnings({ "unchecked", "restriction" })
	public Set getValidRootElements() {
		Set results = new HashSet();
		
		Iterator iterator = contentModel.getElements().iterator();
		while (iterator.hasNext()) {
			CMElementDeclaration element = (CMElementDeclaration) iterator.next();
			results.add(element.getElementName());
		}
		return results;
	}
	

	/** @see IValidator#getValidItems */
	public Set getValidItems(String element, String[] prefix, String[] suffix) {

		// First, get a set of candidates. We'll later test to see if each is
		// valid to insert here.

		Set candidates = null;
		DFAState dfa = (DFAState) elementDFAs.get(element);
		if (dfa == null) {
			// Anything goes!
			return this.anySet;
		}

		DFAState target = dfa.getState(Arrays.asList(prefix));
		if (target == null) {
			return Collections.EMPTY_SET;
		} else {
			// If the last transition was due to PCDATA, adding more PCDATA
			// is also valid
			if (prefix.length > 0
					&& prefix[prefix.length - 1].equals(IValidator.PCDATA)) {
				candidates = new HashSet();
				candidates.addAll(target.getValidSymbols());
				candidates.add(IValidator.PCDATA);
			} else {
				candidates = target.getValidSymbols();
			}
		}

		// Now, see if each candidate can be inserted at the given
		// offset. This second test is necessary in some simple
		// cases. Consider a <section> with an optional <title>; if
		// we're at the first offset of the <section> and a <title>
		// already exists, we should not allow another <title>.
		
		if (candidates.isEmpty()) {
			return Collections.EMPTY_SET;
		}
		
		List<String> listSeq1 = new ArrayList<String>(prefix.length);
		for (int i = 0; i < prefix.length; i++) {
			listSeq1.add(prefix[i]);
		}

		List<String> listSeq2 = new ArrayList<String>(suffix.length);
		for (int i = 0; i < suffix.length; i++) {
			listSeq1.add(suffix[i]);
		}
		

		Set results = new HashSet();
		List<String> middle = new ArrayList<String>(1);
		for (Iterator iter = candidates.iterator(); iter.hasNext();) {
			middle.add(0, (String) iter.next());
			if (this.isValidSequence(element, listSeq1, middle, listSeq2, true)) {
				results.add(middle.get(0));
			}
		}
		

		return Collections.unmodifiableSet(results);
	}

	/**
	 * @see IValidator#isValidSequence
	 */
	public boolean isValidSequence(String element, String[] nodes,
			boolean partial) {

		DFAState dfa = (DFAState) this.elementDFAs.get(element);
		if (dfa == null) {
			// Unrecognized element. Give the user the benefit of the doubt.
			return true;
		}

		DFAState target = dfa.getState(Arrays.asList(nodes));
		
		boolean returnValue;
		if (target != null) {
			returnValue = (partial || target.isAccepting());
		} else {
			returnValue = partial;
		}
			
		
		return returnValue;
	}

	// ==================================================== PRIVATE

	/**
	 * Homeys must call create()
	 */
	private DTDValidator() {
	}

	/**
	 * Create a DFABuilder.Node corresponding to the given DTDItem.
	 */
	@SuppressWarnings("restriction")
	private static DFABuilder.Node createDFANode(CMContent content) {
		DFABuilder.Node node = null;

		if (content == null) {
			return DFABuilder.createSymbolNode(IValidator.PCDATA);
		}
		
		if (content instanceof CMElementDeclaration) {
			CMElementDeclaration element = (CMElementDeclaration) content;
			String elementName = element.getNodeName();
			if (element.getContentType() == CMElementDeclaration.PCDATA) {
				node = DFABuilder.createSymbolNode(IValidator.PCDATA);
			} else if (element.getContentType() == CMElementDeclaration.MIXED) {
				CMContent child = element.getContent();
				DFABuilder.Node newNode = createDFANode(child);
				if (node == null) {
					node = newNode;
				} else {
					node = DFABuilder.createChoiceNode(node, newNode);
				}
				DFABuilder.Node pcdata = DFABuilder
						.createSymbolNode(IValidator.PCDATA);
				node = DFABuilder.createChoiceNode(node, pcdata);

			} else if (element.getContent() != null) {
				CMContent child = element.getContent();
				node = createDFANode(child);
			} else {
				String name = content.getNodeName();
				node = DFABuilder.createSymbolNode(name);
			}
		} else if (content instanceof CMGroup) {
			CMGroup group = (CMGroup) content;
			if (group.getOperator() == CMGroup.CHOICE) {
				CMNodeList childNodes = group.getChildNodes();
				for (int cnt = 0; cnt < childNodes.getLength(); cnt++) {
					CMContent child = (CMContent) childNodes.item(cnt);
					DFABuilder.Node newNode = createDFANode(child);
					if (node == null) {
						node = newNode;
					} else {
						node = DFABuilder.createChoiceNode(node, newNode);
					}
				}
			} else if (group.getOperator() == CMGroup.SEQUENCE) {
				CMNodeList childNodes = group.getChildNodes();
				for (int cnt = 0; cnt < childNodes.getLength(); cnt++) {
					CMContent child = (CMContent) childNodes.item(cnt);
					DFABuilder.Node newNode = createDFANode(child);
					if (node == null) {
						node = newNode;
					} else {
						node = DFABuilder.createSequenceNode(node, newNode);
					}
				}
			}
		} else {
			String name = content.getNodeName();
			node = DFABuilder.createSymbolNode(name);
		}

		// Cardinality is moot if it's a null node
		if (node == null) {
			return node;
		}

		if (content.getMinOccur() == 0 && content.getMaxOccur() == -1) {
			node = DFABuilder.createRepeatingNode(node, 0);
		} else if (content.getMinOccur() > 0 && content.getMaxOccur() == -1) {
			node = DFABuilder.createRepeatingNode(node, 1);
		} else if (content.getMinOccur() == 0) {
			node = DFABuilder.createOptionalNode(node);
		}

		return node;
	}

}
