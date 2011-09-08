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
package org.eclipse.wst.xml.vex.ui.internal.property;

import java.util.List;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource2;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.eclipse.wst.xml.vex.core.internal.dom.Attribute;
import org.eclipse.wst.xml.vex.core.internal.dom.DocumentValidationException;
import org.eclipse.wst.xml.vex.core.internal.dom.Element;
import org.eclipse.wst.xml.vex.core.internal.dom.Validator;
import org.eclipse.wst.xml.vex.core.internal.validator.AttributeDefinition;
import org.eclipse.wst.xml.vex.ui.internal.editor.Messages;

/**
 * Property source that treats element attributes as properties.
 */
public class ElementPropertySource implements IPropertySource2 {

	/**
	 * Class constructor.
	 * 
	 * @param element
	 *            Selected element.
	 * @param validator
	 *            Validator used to get attribute definitions for the element.
	 * @param multi
	 *            True if multiple elements are selected. In this case the "id"
	 *            attribute will not be editable.
	 */
	public ElementPropertySource(final Element element, final Validator validator, final boolean multi) {
		this.element = element;
		this.validator = validator;
		this.multi = multi;
	}

	public Object getEditableValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		// note that elements from DocumentFragments don't have access
		// to their original document, so we get it from the VexWidget
		final List<AttributeDefinition> attrDefs = validator.getAttributeDefinitions(element);
		final IPropertyDescriptor[] pds = new IPropertyDescriptor[attrDefs.size()];
		for (int i = 0; i < attrDefs.size(); i++) {
			final AttributeDefinition def = attrDefs.get(i);
			if (multi && def.getName().equals(ATTR_ID))
				pds[i] = new PropertyDescriptor(def, def.getName());
			else if (def.isFixed())
				pds[i] = new PropertyDescriptor(def, def.getName());
			else if (def.getType() == AttributeDefinition.Type.ENUMERATION)
				pds[i] = new ComboBoxPropertyDescriptor(def, def.getName(), getEnumValues(def));
			else
				pds[i] = new TextPropertyDescriptor(def, def.getName());
		}
		return pds;
	}

	public Object getPropertyValue(final Object id) {
		if (!(id instanceof AttributeDefinition))
			return "";
		final AttributeDefinition attributeDefinition = (AttributeDefinition) id;
		if (multi && id.equals(ATTR_ID))
			return Messages.getString("ElementPropertySource.multiple"); //$NON-NLS-1$

		final Attribute attribute = element.getAttribute(attributeDefinition.getName());
		final String value;
		if (attribute != null)
			value = attribute.getValue();
		else
			value = nullToEmpty(attributeDefinition.getDefaultValue());

		if (attributeDefinition.getType() == AttributeDefinition.Type.ENUMERATION) {
			final String[] values = getEnumValues(attributeDefinition);
			for (int i = 0; i < values.length; i++)
				if (values[i].equals(value))
					return Integer.valueOf(i);
			return Integer.valueOf(0);
			// TODO: If the actual value is not in the list, we should probably add it.
		}
		return value;
	}

	private static String nullToEmpty(final String string) {
		if (string == null)
			return "";
		return string;
	}

	public boolean isPropertySet(final Object id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void resetPropertyValue(final Object id) {
		// TODO Auto-generated method stub

	}

	public void setPropertyValue(final Object id, final Object value) {
		if (!(id instanceof AttributeDefinition))
			return;
		/*
		 * Note that elements from DocumentFragments don't have access to
		 * their original document, so we get it from the VexWidget.
		 */
		final AttributeDefinition attributeDefinition = (AttributeDefinition) id;
		
		try {
			if (attributeDefinition.getType() == AttributeDefinition.Type.ENUMERATION) {
				final int i = ((Integer) value).intValue();
				final String enumValue = getEnumValues(attributeDefinition)[i];
				if (!attributeDefinition.isRequired() && enumValue.equals(""))
					element.removeAttribute(attributeDefinition.getName());
				else
					element.setAttribute(attributeDefinition.getName(), enumValue);
			} else {
				final String s = (String) value;
				if (s.equals(""))
					element.removeAttribute(attributeDefinition.getName());
				else
					element.setAttribute(attributeDefinition.getName(), s);
			}
		} catch (final DocumentValidationException e) {
		}
	}

	public boolean isPropertyResettable(final Object id) {
		// TODO Auto-generated method stub
		return true;
	}

	// ======================================== PRIVATE

	private static final String ATTR_ID = "id"; //$NON-NLS-1$

	private final Element element;
	private final Validator validator;
	private final boolean multi;

	private String[] getEnumValues(final AttributeDefinition def) {
		String[] values = def.getValues();
		if (def.isRequired())
			return values;
		else {
			if (values == null) {
				values = new String[1];
				values[0] = "";
			}
			final String[] values2 = new String[values.length + 1];
			values2[0] = ""; //$NON-NLS-1$
			System.arraycopy(values, 0, values2, 1, values.length);
			return values2;
		}
	}

}