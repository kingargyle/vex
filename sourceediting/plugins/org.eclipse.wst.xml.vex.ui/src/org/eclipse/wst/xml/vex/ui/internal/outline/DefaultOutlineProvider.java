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
package org.eclipse.wst.xml.vex.ui.internal.outline;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.wst.xml.vex.core.internal.css.StyleSheet;
import org.eclipse.wst.xml.vex.core.internal.dom.Document;
import org.eclipse.wst.xml.vex.core.internal.dom.Element;
import org.eclipse.wst.xml.vex.core.internal.dom.IWhitespacePolicy;
import org.eclipse.wst.xml.vex.core.internal.widget.CssWhitespacePolicy;
import org.eclipse.wst.xml.vex.ui.internal.editor.VexEditor;

/**
 * Default implementation of IOutlineProvider. Simply displays all block-level
 * elements.
 */
public class DefaultOutlineProvider implements IOutlineProvider {

	public void init(VexEditor editor) {
		StyleSheet ss = editor.getVexWidget().getStyleSheet();
		this.whitespacePolicy = new CssWhitespacePolicy(ss);
		this.contentProvider = new ContentProvider();
		this.labelProvider = new LabelProvider() {
			public String getText(Object o) {
				Element e = (Element) o;
				String s = e.getText();
				if (s.length() > 30) {
					s = s.substring(0, 30) + "..."; //$NON-NLS-1$
				}
				return e.getPrefixedName() + ": " + s; //$NON-NLS-1$
			}
		};

	}

	public ITreeContentProvider getContentProvider() {
		return this.contentProvider;
	}

	public IBaseLabelProvider getLabelProvider() {
		return this.labelProvider;
	}

	public Element getOutlineElement(Element child) {
		Element element = child;
		while (element != null) {
			
			// block element?
			if (this.whitespacePolicy.isBlock(element)) {
				return element;
			}
			
			// root?
			Element parent = element.getParent();
			if (parent == null) {
				return element;
			}
			element = parent;
		}
		return element;
	}

	// ====================================================== PRIVATE

	private IWhitespacePolicy whitespacePolicy;
	private ITreeContentProvider contentProvider;
	private IBaseLabelProvider labelProvider;

	private class ContentProvider implements ITreeContentProvider {

		public Object[] getChildren(Object parentElement) {
			List<Element> blockChildren = new ArrayList<Element>();
			List<Element> children = ((Element) parentElement).getChildElements();
			for (Element child : children) {
				if (whitespacePolicy.isBlock(child)) {
					blockChildren.add(child);
				}
			}
			return blockChildren.toArray();
		}

		public Object getParent(Object element) {
			return ((Element) element).getParent();
		}

		public boolean hasChildren(Object o) {
			return this.hasBlockChild((Element) o);
		}

		public Object[] getElements(Object inputElement) {
			return new Object[] { ((Document) inputElement).getRootElement() };
			// return this.getChildren(inputElement);
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub

		}

		// ====================================================== PRIVATE

		private boolean hasBlockChild(Element element) {
			for (Element child : element.getChildElements()) {
				if (whitespacePolicy.isBlock(child)) {
					return true;
				}
			}
			return false;
		}
	}

}
