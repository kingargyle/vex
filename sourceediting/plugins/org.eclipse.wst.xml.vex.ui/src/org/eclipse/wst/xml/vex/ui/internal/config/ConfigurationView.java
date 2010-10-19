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
package org.eclipse.wst.xml.vex.ui.internal.config;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wst.xml.vex.ui.internal.VexPlugin;

/**
 * View showing all configuration items defined in Vex.
 */
public class ConfigurationView extends ViewPart {

	@Override
	public void createPartControl(final Composite parent) {

		parentControl = parent;

		VexPlugin.getInstance().getConfigurationRegistry().addConfigListener(configListener);
		if (VexPlugin.getInstance().getConfigurationRegistry().isLoaded())
			createTreeViewer();
		else {
			loadingLabel = new Label(parent, SWT.NONE);
			loadingLabel.setText(Messages.getString("ConfigurationView.loading")); //$NON-NLS-1$
		}

	}

	@Override
	public void dispose() {
		super.dispose();
		VexPlugin.getInstance().getConfigurationRegistry().removeConfigListener(configListener);
	}

	@Override
	public void setFocus() {
		if (treeViewer != null)
			treeViewer.getTree().setFocus();
	}

	// ===================================================== PRIVATE

	private Composite parentControl;

	private Label loadingLabel;

	private TreeViewer treeViewer;

	private void createTreeViewer() {
		treeViewer = new TreeViewer(parentControl, SWT.SINGLE);
		treeViewer.setContentProvider(new MyContentProvider());
		treeViewer.setLabelProvider(new MyLabelProvider());
		treeViewer.setAutoExpandLevel(2);
		treeViewer.setInput(VexPlugin.getInstance().getConfigurationRegistry());
	}

	private static class MyContentProvider implements ITreeContentProvider {
		public Object[] getChildren(final Object parentElement) {
			if (parentElement instanceof ConfigurationRegistry)
				return VexPlugin.getInstance().getConfigurationRegistry().getDocumentTypes();
			if (parentElement instanceof DocumentType)
				return VexPlugin.getInstance().getConfigurationRegistry().getStyles(((DocumentType) parentElement).getPublicId());
			return new Object[0];
		}

		public Object getParent(final Object element) {
			if (element instanceof DocumentType)
				return VexPlugin.getInstance().getConfigurationRegistry();
			if (element instanceof Style) 
				return VexPlugin.getInstance().getConfigurationRegistry().getDocumentType(((Style) element).getDocumentTypes().iterator().next());
			return null;
		}

		public boolean hasChildren(final Object element) {
			return getChildren(element).length > 0;
		}

		public Object[] getElements(final Object inputElement) {
			return getChildren(inputElement);
		}

		public void dispose() {
		}

		public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		}
	}

	private static class MyLabelProvider extends LabelProvider {
		@Override
		public String getText(final Object element) {
			if (element instanceof DocumentType)
				return ((DocumentType) element).getName();
			if (element instanceof Style)
				return ((Style) element).getName();
			return null;
		}
	}

	private final IConfigListener configListener = new IConfigListener() {
		public void configChanged(final ConfigEvent e) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					treeViewer.refresh();
				}
			});
		}

		public void configLoaded(final ConfigEvent e) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					loadingLabel.dispose();
					createTreeViewer();
					parentControl.layout();
				}
			});
		}
	};
}
