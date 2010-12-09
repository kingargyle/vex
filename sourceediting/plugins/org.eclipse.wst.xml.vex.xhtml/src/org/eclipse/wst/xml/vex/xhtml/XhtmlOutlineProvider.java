/*******************************************************************************
 * Copyright (c) 2004, 2010 John Krasnay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     John Krasnay - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.xhtml;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXDocument;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement;
import org.eclipse.wst.xml.vex.ui.internal.editor.VexEditor;
import org.eclipse.wst.xml.vex.ui.internal.outline.IOutlineProvider;

/**
 * Provides an outline of the sections of an XHTML document.
 */
public class XhtmlOutlineProvider implements IOutlineProvider {

	public void init(final VexEditor editor) {
	}

	public ITreeContentProvider getContentProvider() {
		return contentProvider;
	}

	public IBaseLabelProvider getLabelProvider() {
		return labelProvider;
	}

	public VEXElement getOutlineElement(final VEXElement child) {
		VEXElement element = child;
		while (element.getParent() != null) {

			// TODO: compare to all structural element names

			final String name = element.getName();
			if (name.equals("h1") || name.equals("h2") || name.equals("h3") || name.equals("h4") || name.equals("h5") || name.equals("h6"))
				return element;

			element = element.getParent();
		}
		return element;
	}

	//===================================================== PRIVATE

	private final ITreeContentProvider contentProvider = new ITreeContentProvider() {

		public void dispose() {
		}

		public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		}

		public Object[] getChildren(final Object parentElement) {
			return getOutlineChildren((VEXElement) parentElement);
		}

		public Object getParent(final Object element) {
			final VEXElement parent = ((VEXElement) element).getParent();
			if (parent == null)
				return element;
			else
				return getOutlineElement(parent);
		}

		public boolean hasChildren(final Object element) {
			return getOutlineChildren((VEXElement) element).length > 0;
		}

		public Object[] getElements(final Object inputElement) {
			final VEXDocument document = (VEXDocument) inputElement;
			return new Object[] { document.getRootElement() };
		}

	};

	/**
	 * Returns an array of the children of the given element that represent
	 * nodes in the outline. These are structural elements such as "section".
	 * 
	 * @param element
	 * @return
	 */
	private VEXElement[] getOutlineChildren(final VEXElement element) {
		final List children = new ArrayList();
		if (element.getParent() == null) {
			final VEXElement body = findChild(element, "body");
			if (body != null) {
				final EList<VEXElement> childElements = body.getChildElements();

				// First, find the lowest numbered h tag available
				String lowH = "h6";
				for (final VEXElement child : childElements)
					if (isHTag(child) && child.getName().compareTo(lowH) < 0)
						lowH = child.getName();

				// Now, get all body children at that level
				for (final VEXElement child : childElements)
					if (child.getName().equals(lowH))
						children.add(child);
			}
		} else if (isHTag(element)) {
			// get siblings with the next lower number
			// between this element and the next element at the same level
			final int level = Integer.parseInt(element.getName().substring(1));
			final String childName = "h" + (level + 1);
			final List<VEXElement> childElements = element.getParent().getChildElements();
			boolean foundSelf = false;
			for (final VEXElement child : childElements)
				if (child == element)
					foundSelf = true;
				else if (!foundSelf)
					continue;
				else if (child.getName().equals(childName))
					children.add(child);
				else if (child.getName().equals(element.getName()))
					// terminate at next sibling at same level
					break;
		}
		return (VEXElement[]) children.toArray(new VEXElement[children.size()]);
	}

	private final ILabelProvider labelProvider = new LabelProvider() {
		@Override
		public String getText(final Object o) {
			String text;
			final VEXElement element = (VEXElement) o;
			if (element.getParent() == null) {
				text = "html";
				final VEXElement head = findChild(element, "head");
				if (head != null) {
					final VEXElement title = findChild(head, "title");
					if (title != null)
						text = title.getText();
				}
			} else
				text = element.getText();
			return text;
		}
	};

	private VEXElement findChild(final VEXElement parent, final String childName) {
		for (final VEXElement child : parent.getChildElements())
			if (child.getName().equals(childName))
				return child;
		return null;
	}

	private boolean isHTag(final VEXElement element) {
		final String name = element.getName();
		return name.equals("h1") || name.equals("h2") || name.equals("h3") || name.equals("h4") || name.equals("h5") || name.equals("h6");
	}

}