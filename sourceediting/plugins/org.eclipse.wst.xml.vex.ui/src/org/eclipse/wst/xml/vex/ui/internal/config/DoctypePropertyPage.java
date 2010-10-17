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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.Validator;
import org.eclipse.wst.xml.vex.ui.internal.VexPlugin;

/**
 * Property page for .dtd files.
 */
public class DoctypePropertyPage extends PropertyPage {

	private static final int NAME_WIDTH = 150;

	private PluginProject pluginProject;
	
	private DocumentType doctype;

	private Composite pane;

	private Text nameText;

	private Text publicIdText;

	private Text systemIdText;

	private Table rootElementsTable;

	private IConfigListener configListener;
	
	@Override
	protected Control createContents(final Composite parent) {
		pane = new Composite(parent, SWT.NONE);

		pluginProject = new PluginProject(((IFile) getElement()).getProject());
		try {
			pluginProject.load();
		} catch (CoreException e) {
			VexPlugin.getInstance().getLog().log(e.getStatus());
		}
		
		createPropertySheet();

		configListener = new IConfigListener() {
			public void configChanged(final ConfigEvent event) {
				// This is fired when we open properties for a new doctype
				// and we force it to be re-built to get a validator
				// from which we get our list of prospective root elements.
				try {
					pluginProject.load();
				} catch (CoreException e) {
					VexPlugin.getInstance().getLog().log(e.getStatus());
				}

				final String resourcePath = ((IFile) getElement()).getProjectRelativePath().toString();
				doctype = (DocumentType) pluginProject.getItemForResource(resourcePath);

				populateRootElements();
			}

			public void configLoaded(final ConfigEvent e) {
				setMessage(getTitle());
				populateDoctype();
				setValid(true);

				try { // force an incremental build
					pluginProject.writeConfigXml();
				} catch (final Exception ex) {
					final String message = MessageFormat.format(Messages.getString("DoctypePropertyPage.errorWritingConfig"), //$NON-NLS-1$
							new Object[] { PluginProject.PLUGIN_XML });
					VexPlugin.getInstance().log(IStatus.ERROR, message, ex);
				}
			}
		};
		ConfigurationRegistry.INSTANCE.addConfigListener(configListener);

		if (ConfigurationRegistry.INSTANCE.isLoaded()) {
			populateDoctype();
			populateRootElements();
		} else {
			setValid(false);
			setMessage(Messages.getString("DoctypePropertyPage.loading")); //$NON-NLS-1$
		}

		return pane;
	}
	
	private void createPropertySheet() {
		final GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		pane.setLayout(layout);
		GridData gd;

		Label label;

		label = new Label(pane, SWT.NONE);
		label.setText(Messages.getString("DoctypePropertyPage.name")); //$NON-NLS-1$
		nameText = new Text(pane, SWT.BORDER);
		gd = new GridData();
		gd.widthHint = NAME_WIDTH;
		nameText.setLayoutData(gd);

		label = new Label(pane, SWT.NONE);
		label.setText(Messages.getString("DoctypePropertyPage.publicId")); //$NON-NLS-1$
		publicIdText = new Text(pane, SWT.BORDER);
		gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = GridData.FILL;
		publicIdText.setLayoutData(gd);

		label = new Label(pane, SWT.NONE);
		label.setText(Messages.getString("DoctypePropertyPage.systemId")); //$NON-NLS-1$
		systemIdText = new Text(pane, SWT.BORDER);
		gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = GridData.FILL;
		systemIdText.setLayoutData(gd);

		final String resourcePath = ((IFile) getElement()).getProjectRelativePath().toString();

		doctype = (DocumentType) pluginProject.getItemForResource(resourcePath);
		if (doctype == null) {
			doctype = new DocumentType(pluginProject);
			doctype.setResourcePath(resourcePath);
			pluginProject.addItem(doctype);
		}

		// Generate a simple ID for this one if necessary
		if (doctype.getSimpleId() == null || doctype.getSimpleId().length() == 0)
			doctype.setSimpleId(doctype.generateSimpleId());

		// need to do GridLayout and GridData for this guy them fill with items

		label = new Label(pane, SWT.NONE);
		label.setText(Messages.getString("DoctypePropertyPage.rootElements")); //$NON-NLS-1$

		gd = new GridData();
		// gd.widthHint = COLUMN_1_WIDTH;
		gd.verticalAlignment = GridData.BEGINNING;
		gd.horizontalSpan = 2;
		label.setLayoutData(gd);

		final Composite tablePane = new Composite(pane, SWT.BORDER);
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 200;
		gd.horizontalSpan = 2;
		tablePane.setLayoutData(gd);
		tablePane.setLayout(new FillLayout());

		rootElementsTable = new Table(tablePane, SWT.CHECK);
	}
	
	private void populateDoctype() {
		setText(nameText, doctype.getName());
		setText(publicIdText, doctype.getPublicId());
		setText(systemIdText, doctype.getSystemId());
	}

	private static void setText(final Text textBox, final String text) {
		textBox.setText(text == null ? "" : text); //$NON-NLS-1$
	}

	private void populateRootElements() {
		final String resourcePath = ((IFile) getElement()).getProjectRelativePath().toString();
		final Validator validator = (Validator) pluginProject.getParsedResource(resourcePath);
		if (validator != null) {
			final List<String> list = Arrays.asList(doctype.getRootElements());
			final Set<String> selectedRootElements = new TreeSet<String>(list);

			rootElementsTable.removeAll();
			final List<String> l = new ArrayList<String>(validator.getValidRootElements());
			Collections.sort(l);
			for (int i = 0; i < l.size(); i++) {
				final TableItem item1 = new TableItem(rootElementsTable, SWT.NONE);
				item1.setText(l.get(i));

				if (selectedRootElements.contains(l.get(i)))
					item1.setChecked(true);
			}
		} else
			try {
				pluginProject.writeConfigXml();
			} catch (final Exception ex) {
				final String message = MessageFormat.format(Messages.getString("DoctypePropertyPage.errorWritingConfig"), //$NON-NLS-1$
						new Object[] { PluginProject.PLUGIN_XML });
				VexPlugin.getInstance().log(IStatus.ERROR, message, ex);
			}
	}
	
	@Override
	public boolean performOk() {
		performApply();
		return super.performOk();
	}

	@Override
	public void performApply() {
		doctype.setName(nameText.getText());
		doctype.setPublicId(publicIdText.getText());
		doctype.setSystemId(systemIdText.getText());

		// collect root Elements from the rootElementsTable
		final ArrayList<String> selectedRootElements = new ArrayList<String>();
		for (final TableItem item : rootElementsTable.getItems())
			if (item.getChecked())
				selectedRootElements.add(item.getText());
		doctype.setRootElements(selectedRootElements.toArray(new String[selectedRootElements.size()]));

		try {
			pluginProject.writeConfigXml();
		} catch (final Exception ex) {
			final String message = MessageFormat.format(Messages.getString("DoctypePropertyPage.errorWritingConfig"), //$NON-NLS-1$
					new Object[] { PluginProject.PLUGIN_XML });
			VexPlugin.getInstance().log(IStatus.ERROR, message, ex);
		}
	}

	@Override
	public void performDefaults() {
		super.performDefaults();
		populateDoctype();
		populateRootElements();
	}

	@Override
	public void dispose() {
		super.dispose();
		if (configListener != null)
			ConfigurationRegistry.INSTANCE.removeConfigListener(configListener);
	}
}