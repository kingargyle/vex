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
package org.eclipse.wst.xml.vex.ui.internal.outline;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.wst.xml.vex.core.internal.css.StyleSheet;
import org.eclipse.wst.xml.vex.core.internal.dom.IVEXDocument;
import org.eclipse.wst.xml.vex.core.internal.dom.IVEXElement;
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
				IVEXElement e = (IVEXElement) o;
				String s = e.getText();
				if (s.length() > 30) {
					s = s.substring(0, 30) + "..."; //$NON-NLS-1$
				}
				return e.getName() + ": " + s; //$NON-NLS-1$
			}
		};

	}

	public ITreeContentProvider getContentProvider() {
		return this.contentProvider;
	}

	public IBaseLabelProvider getLabelProvider() {
		return this.labelProvider;
	}

	public IVEXElement getOutlineElement(IVEXElement child) {
		IVEXElement element = child;
		while (element != null) {
			if (this.whitespacePolicy.isBlock(element)) {
				return element;
			}
			element = element.getParent();
		}
		return element;
	}

	// ====================================================== PRIVATE

	private IWhitespacePolicy whitespacePolicy;
	private ITreeContentProvider contentProvider;
	private IBaseLabelProvider labelProvider;

	private class ContentProvider implements ITreeContentProvider {

		public Object[] getChildren(Object parentElement) {
			List blockChildren = new ArrayList();
			IVEXElement[] children = ((IVEXElement) parentElement).getChildElements();
			for (int i = 0; i < children.length; i++) {
				if (whitespacePolicy.isBlock(children[i])) {
					blockChildren.add(children[i]);
				}
			}
			return blockChildren.toArray();
		}

		public Object getParent(Object element) {
			return ((IVEXElement) element).getParent();
		}

		public boolean hasChildren(Object o) {
			return this.hasBlockChild((IVEXElement) o);
		}

		public Object[] getElements(Object inputElement) {
			return new Object[] { ((IVEXDocument) inputElement).getRootElement() };
			// return this.getChildren(inputElement);
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub

		}

		// ====================================================== PRIVATE

		private boolean hasBlockChild(IVEXElement element) {
			IVEXElement[] children = element.getChildElements();
			for (int i = 0; i < children.length; i++) {
				if (whitespacePolicy.isBlock(children[i])) {
					return true;
				}
			}
			return false;
		}
	}

}
