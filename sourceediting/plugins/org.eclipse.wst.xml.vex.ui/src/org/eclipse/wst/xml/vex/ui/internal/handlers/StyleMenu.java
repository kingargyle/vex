/*******************************************************************************
 * Copyright (c) 2009 Holger Voormann and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Holger Voormann - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.ui.internal.handlers;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.xml.vex.ui.internal.config.ConfigurationRegistry;
import org.eclipse.wst.xml.vex.ui.internal.config.Style;
import org.eclipse.wst.xml.vex.ui.internal.editor.VexEditor;

/**
 * Menu of all registered styles available for the current document to choose
 * from.
 */
public class StyleMenu extends ContributionItem {

    @Override
    public void fill(Menu menu, int index) {
        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
        final VexEditor editor = VexHandlerUtil.computeVexEditor(window);
        if (editor == null) return;

        String publicId= editor.getVexWidget().getDocument().getPublicID();
        for (final Style style : ConfigurationRegistry.INSTANCE.getStyles(publicId)) {
            MenuItem menuItem = new MenuItem(menu, SWT.RADIO, index);
            menuItem.setText(style.getName());
            menuItem.setSelection(style == editor.getStyle());
            menuItem.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent e) {
                    editor.setStyle(style);
                }
            });
        }
    }

}
