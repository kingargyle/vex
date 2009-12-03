/*******************************************************************************
 *Copyright (c) 2008 Standard for Technology in Automotive Retail  and others.
 *All rights reserved. This program and the accompanying materials
 *are made available under the terms of the Eclipse Public License v1.0
 *which accompanies this distribution, and is available at
 *http://www.eclipse.org/legal/epl-v10.html
 *
 *Contributors:
 *    David Carver (STAR) - initial API and implementation
 *    Holger Voormann - bug 283646 - Document wizard throws NPW with DITA is selected
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.validator;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.wst.xml.core.internal.contentmodel.CMAttributeDeclaration;
import org.eclipse.wst.xml.core.internal.contentmodel.CMContent;
import org.eclipse.wst.xml.core.internal.contentmodel.CMDataType;
import org.eclipse.wst.xml.core.internal.contentmodel.CMDocument;
import org.eclipse.wst.xml.core.internal.contentmodel.CMElementDeclaration;
import org.eclipse.wst.xml.core.internal.contentmodel.CMGroup;
import org.eclipse.wst.xml.core.internal.contentmodel.CMNode;
import org.eclipse.wst.xml.core.internal.contentmodel.CMNodeList;
import org.eclipse.wst.xml.core.internal.contentmodel.ContentModelManager;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Validator;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.impl.ValidatorImpl;

@SuppressWarnings("restriction")
public class WTPVEXValidator extends ValidatorImpl implements Validator {

	private static final long serialVersionUID = -7632029717211069257L;

	/** The XML schema (= content model), e.g. DTD or XSD */
	private transient CMDocument schema;
	
	private final URL url;
	
	private WTPVEXValidator(URL url) {
		this.url = url;
	}
	
	private CMDocument getSchema() {
		if (schema == null) {
			ContentModelManager modelManager = ContentModelManager.getInstance();
			String resolved = url.toString();
			schema = modelManager.createCMDocument(resolved, null);
		}
		return schema;
	}

	public static WTPVEXValidator create(URL url) {
		return new WTPVEXValidator(url);
	}

	public AttributeDefinition getAttributeDefinition(String element,
			String attribute) {
		CMElementDeclaration cmelement = getElementDeclaration(element);
		CMAttributeDeclaration attr = (CMAttributeDeclaration) cmelement
				.getAttributes().getNamedItem(attribute);
		AttributeDefinition vexAttr = createAttributeDefinition(attr);
		return vexAttr;
	}

	public EList<AttributeDefinition> getAttributeDefinitions(String element) {
		CMElementDeclaration cmelement = getElementDeclaration(element);
		EList<AttributeDefinition> attributeList = new BasicEList<AttributeDefinition>(
				cmelement.getAttributes().getLength());
		Iterator iter = cmelement.getAttributes().iterator();
		while (iter.hasNext()) {
			CMAttributeDeclaration attribute = (CMAttributeDeclaration) iter
					.next();
			AttributeDefinition vexAttr = createAttributeDefinition(attribute);
			attributeList.add(vexAttr);
		}

		return attributeList;
	}

	private CMElementDeclaration getElementDeclaration(String element) {
		CMElementDeclaration cmelement = (CMElementDeclaration) getSchema()
				.getElements().getNamedItem(element);
		return cmelement;
	}

	private AttributeDefinition createAttributeDefinition(
			CMAttributeDeclaration attribute) {
		String defaultValue = attribute.getDefaultValue();
		String[] values = null;
		AttributeDefinition.Type type = null;
		if (attribute.getAttrType().equals(CMDataType.ENUM)) {
			type = AttributeDefinition.Type.ENUMERATION;
			values = attribute.getAttrType().getEnumeratedValues();
		} else if (attribute.getAttrType().equals(CMDataType.NOTATION)) {
			type = AttributeDefinition.Type.ENUMERATION;
			values = attribute.getAttrType().getEnumeratedValues();
		} else
			type = AttributeDefinition.Type.get(attribute.getAttrType()
					.getDataTypeName());
		boolean required = (attribute.getUsage() == CMAttributeDeclaration.REQUIRED);
		boolean fixed = (attribute.getUsage() == CMAttributeDeclaration.FIXED);
		AttributeDefinition vexAttr = new AttributeDefinition(attribute
				.getAttrName(), type, defaultValue, values, required, fixed);
		return vexAttr;
	}

	public Set<String> getValidItems(String element) {
		CMElementDeclaration elementDec = (CMElementDeclaration) getSchema()
				.getElements().getNamedItem(element);
		List<CMNode> nodes = getAvailableContent(element, elementDec);
		Set<String> results = new HashSet<String>();
		Iterator iter = nodes.iterator();
		while (iter.hasNext()) {
			CMNode node = (CMNode) iter.next();
			if (node instanceof CMElementDeclaration) {
				CMElementDeclaration elem = (CMElementDeclaration) node;
				results.add(elem.getElementName());
			}
		}
		return results;
	}

	/**
	 * Returns a list of all CMNode 'meta data' that may be potentially added to
	 * the element.
	 */
	protected List<CMNode> getAvailableContent(String element, CMElementDeclaration ed) {
		List<CMNode> list = new ArrayList<CMNode>();
		if (ed.getContentType() == CMElementDeclaration.ELEMENT
				|| ed.getContentType() == CMElementDeclaration.MIXED) {
			CMContent content = ed.getContent();
			if (content instanceof CMElementDeclaration) {
				list.add(content);
			} else if (content instanceof CMGroup) {
				CMGroup groupContent = (CMGroup) content;
				list.addAll(getAllChildren(groupContent));
			}
		}
		return list;
	}
	
	private List<CMNode> getAllChildren(CMGroup group) {
		List<CMNode> list = new ArrayList<CMNode>();
		CMNodeList nodeList = group.getChildNodes();
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			CMNode node = nodeList.item(i);
			if (node instanceof CMElementDeclaration) {
				list.add(node);
			} else if (node instanceof CMGroup) {
				list.addAll(getAllChildren((CMGroup)node));
			}
		}
		return list;
	}

	public Set getValidRootElements() {
		Set<String> results = new HashSet<String>();

		Iterator iter = getSchema().getElements().iterator();
		while (iter.hasNext()) {
			CMElementDeclaration element = (CMElementDeclaration) iter.next();
			results.add(element.getElementName());
		}

		return results;
	}

	public boolean isValidSequence(String element, EList<String> nodes,
			boolean partial) {
		// TODO
		return true;
	}

	public boolean isValidSequence(String element, EList<String> seq1,
			EList<String> seq2, EList<String> seq3, boolean partial) {

		// TODO

		CMNode parent = getSchema().getElements().getNamedItem(element);
		if (!(parent instanceof CMElementDeclaration)) return true;

		// must be empty?
		CMElementDeclaration declaration = (CMElementDeclaration) parent;
		if (   declaration.getContentType() == CMElementDeclaration.EMPTY
				&& seq2.size() > 0) {
			return false;
		}

		return true;
	}

}
