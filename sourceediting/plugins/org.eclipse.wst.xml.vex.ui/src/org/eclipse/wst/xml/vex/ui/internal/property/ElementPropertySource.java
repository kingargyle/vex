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
import org.eclipse.wst.xml.vex.core.internal.dom.DocumentValidationException;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Validator;
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
	public ElementPropertySource(VEXElement element, Validator validator,
			boolean multi) {
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
		List<AttributeDefinition> attrDefs = validator
				.getAttributeDefinitions(element.getName());
		IPropertyDescriptor[] pds = new IPropertyDescriptor[attrDefs.size()];
		for (int i = 0; i < attrDefs.size(); i++) {
			AttributeDefinition def = attrDefs.get(i);
			if (this.multi && def.getName().equals(ATTR_ID)) {
				pds[i] = new PropertyDescriptor(def.getName(), def.getName());
			} else if (def.isFixed()) {
				pds[i] = new PropertyDescriptor(def.getName(), def.getName());
			} else if (def.getType() == AttributeDefinition.Type.ENUMERATION) {
				pds[i] = new ComboBoxPropertyDescriptor(def.getName(), def
						.getName(), this.getEnumValues(def));
			} else {
				pds[i] = new TextPropertyDescriptor(def.getName(), def
						.getName());
			}
		}
		return pds;
	}

	public Object getPropertyValue(Object id) {

		if (this.multi && id.equals(ATTR_ID)) {
			return Messages.getString("ElementPropertySource.multiple"); //$NON-NLS-1$
		}

		// note that elements from DocumentFragments don't have access
		// to their original document, so we get it from the VexWidget
		AttributeDefinition def = this.validator.getAttributeDefinition(
				this.element.getName(), (String) id);
		String value = this.element.getAttribute((String) id);
		if (value == null) {
			value = def.getDefaultValue();
			if (value == null) {
				value = ""; //$NON-NLS-1$
			}
		}

		if (def.getType() == AttributeDefinition.Type.ENUMERATION) {
			String[] values = this.getEnumValues(def);
			for (int i = 0; i < values.length; i++) {
				if (values[i].equals(value)) {
					return Integer.valueOf(i);
				}
			}
			return Integer.valueOf(0); // TODO: if the actual value is not
			// in the list, we should probably
			// add it
		} else {
			return value;
		}
	}

	public boolean isPropertySet(Object id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void resetPropertyValue(Object id) {
		// TODO Auto-generated method stub

	}

	public void setPropertyValue(Object id, Object value) {

		try {
			// note that elements from DocumentFragments don't have access
			// to their original document, so we get it from the VexWidget
			AttributeDefinition def = this.validator.getAttributeDefinition(
					this.element.getName(), (String) id);

			if (def.getType() == AttributeDefinition.Type.ENUMERATION) {
				int i = ((Integer) value).intValue();
				String s = this.getEnumValues(def)[i];
				if (!def.isRequired() && s.equals("")) { //$NON-NLS-1$
					this.element.removeAttribute(def.getName());
				} else {
					this.element.setAttribute(def.getName(), s);
				}
			} else {
				String s = (String) value;
				if (s.equals("")) { //$NON-NLS-1$
					this.element.removeAttribute(def.getName());
				} else {
					this.element.setAttribute(def.getName(), s);
				}
			}
		} catch (DocumentValidationException e) {
		}
	}

	public boolean isPropertyResettable(Object id) {
		// TODO Auto-generated method stub
		return true;
	}

	// ======================================== PRIVATE

	private static final String ATTR_ID = "id"; //$NON-NLS-1$

	private VEXElement element;
	private Validator validator;
	private boolean multi;

	private String[] getEnumValues(AttributeDefinition def) {
		String[] values = def.getValues();
		if (def.isRequired()) {
			return values;
		} else {
			if (values == null) {
				values = new String[1];
				values[0] = "";
			}
			String[] values2 = new String[values.length + 1];
			values2[0] = ""; //$NON-NLS-1$
			System.arraycopy(values, 0, values2, 1, values.length);
			return values2;
		}
	}

}