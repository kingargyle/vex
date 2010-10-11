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
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.resources.IFile;
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
import org.eclipse.wst.xml.vex.ui.internal.VexPlugin;

/**
 * Property page for .css files.
 */
public class StylePropertyPage extends PropertyPage {

	@Override
	protected Control createContents(final Composite parent) {

		pane = new Composite(parent, SWT.NONE);

		createPropertySheet();

		configListener = new IConfigListener() {

			public void configChanged(final ConfigEvent e) {
				populateDoctypes();
			}

			public void configLoaded(final ConfigEvent e) {
				setMessage(getTitle());
				populateStyle();
				setValid(true);

				try { // force an incremental build
					getPluginProject().writeConfigXml();
				} catch (final Exception ex) {
					final String message = MessageFormat.format(Messages.getString("StylePropertyPage.writeError"), //$NON-NLS-1$
							new Object[] { PluginProject.PLUGIN_XML });
					VexPlugin.getInstance().log(IStatus.ERROR, message, ex);
				}

			}
		};

		ConfigurationRegistry.INSTANCE.addConfigListener(configListener);

		if (ConfigurationRegistry.INSTANCE.isLoaded()) {

			populateStyle();
			populateDoctypes();

		} else {

			setValid(false);

			setMessage(Messages.getString("StylePropertyPage.loading")); //$NON-NLS-1$

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
		label.setText(Messages.getString("StylePropertyPage.name")); //$NON-NLS-1$
		nameText = new Text(pane, SWT.BORDER);
		gd = new GridData();
		gd.widthHint = NAME_WIDTH;
		nameText.setLayoutData(gd);

		final String resourcePath = ((IFile) getElement()).getProjectRelativePath().toString();

		final ConfigSource config = getPluginProject();

		style = (Style) config.getItemForResource(resourcePath);
		if (style == null) {
			style = new Style(config);
			style.setResourcePath(resourcePath);
			config.addItem(style);
		}

		// Generate a simple ID for this one if necessary
		if (style.getSimpleId() == null || style.getSimpleId().length() == 0)
			style.setSimpleId(style.generateSimpleId());

		label = new Label(pane, SWT.NONE);
		label.setText(Messages.getString("StylePropertyPage.doctypes")); //$NON-NLS-1$
		gd = new GridData();
		gd.horizontalSpan = 2;
		label.setLayoutData(gd);

		final Composite tablePane = new Composite(pane, SWT.BORDER);

		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 200;
		gd.horizontalSpan = 2;
		tablePane.setLayoutData(gd);

		final FillLayout fillLayout = new FillLayout();
		tablePane.setLayout(fillLayout);

		doctypesTable = new Table(tablePane, SWT.CHECK);

	}

	/**
	 * Returns the PluginProject associated with this resource.
	 * 
	 * @return
	 */
	public PluginProject getPluginProject() {
		final IFile file = (IFile) getElement();
		return ConfigurationRegistry.INSTANCE.getPluginProject(file.getProject());
	}

	@Override
	public boolean performOk() {

		performApply();

		return super.performOk();
	}

	@Override
	public void performApply() {

		style.setName(nameText.getText());

		final ArrayList<String> selectedDoctypes = new ArrayList<String>();
		for (final TableItem item : doctypesTable.getItems())
			if (item.getChecked())
				selectedDoctypes.add(item.getText());

		style.removeAllDocumentTypes();

		final DocumentType[] documentTypes = ConfigurationRegistry.INSTANCE.getDocumentTypes();
		Arrays.sort(documentTypes);
		for (final DocumentType documentType : documentTypes)
			if (selectedDoctypes.contains(documentType.getName()))
				style.addDocumentType(documentType.getPublicId());

		try {
			getPluginProject().writeConfigXml();
		} catch (final Exception e) {
			final String message = MessageFormat.format(Messages.getString("StylePropertyPage.writeError"), //$NON-NLS-1$
					new Object[] { PluginProject.PLUGIN_XML });
			VexPlugin.getInstance().log(IStatus.ERROR, message, e);
		}

		ConfigurationRegistry.INSTANCE.fireConfigChanged(new ConfigEvent(this));
	}

	@Override
	protected void performDefaults() {

		super.performDefaults();

		populateStyle();

		populateDoctypes();

	}

	@Override
	public void dispose() {
		super.dispose();

		if (configListener != null)
			ConfigurationRegistry.INSTANCE.removeConfigListener(configListener);
	}

	// ======================================================= PRIVATE

	private Style style;
	private static final int NAME_WIDTH = 150;

	private Composite pane;
	private Text nameText;
	private Table doctypesTable;

	private IConfigListener configListener;

	private void populateStyle() {
		setText(nameText, style.getName());

	}

	private void populateDoctypes() {
		final Set<String> selectedDoctypes = new TreeSet<String>(style.getDocumentTypes());
		doctypesTable.removeAll();

		final DocumentType[] documentTypes = ConfigurationRegistry.INSTANCE.getDocumentTypes();
		Arrays.sort(documentTypes);
		for (final DocumentType documentType : documentTypes) {
			final TableItem item = new TableItem(doctypesTable, SWT.NONE);
			item.setText(documentType.getName());
			if (selectedDoctypes.contains(documentType.getPublicId()))
				item.setChecked(true);
		}
	}

	private void setText(final Text textBox, final String s) {
		textBox.setText(s == null ? "" : s); //$NON-NLS-1$
	}

}
