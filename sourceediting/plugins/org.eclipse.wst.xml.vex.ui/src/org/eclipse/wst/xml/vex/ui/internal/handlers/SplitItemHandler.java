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
package org.eclipse.wst.xml.vex.ui.internal.handlers;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.wst.xml.vex.core.internal.css.CSS;
import org.eclipse.wst.xml.vex.core.internal.css.StyleSheet;
import org.eclipse.wst.xml.vex.core.internal.layout.Box;
import org.eclipse.wst.xml.vex.core.internal.layout.TableRowBox;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXElement;
import org.eclipse.wst.xml.vex.core.internal.widget.IBoxFilter;
import org.eclipse.wst.xml.vex.ui.internal.swt.VexWidget;

/**
 * Splits the nearest enclosing table row or list item (usually by hitting
 * {@code Shift+Return}). If a table row is being split, empty versions of the
 * current row's cells are created.
 *
 * @see SplitBlockElementHandler
 */
public class SplitItemHandler extends SplitBlockElementHandler {

    @Override
    public void execute(VexWidget widget) throws ExecutionException {
        final StyleSheet ss = widget.getStyleSheet();

        // Item is either a TableRowBox or a BlockElementBox representing
        // a list item
        Box item = widget.findInnermostBox(new IBoxFilter() {
            public boolean matches(Box box) {
                if (box instanceof TableRowBox) {
                    return true;
                } else {
                    VEXElement element = box.getElement();
                    return element != null
                            && ss.getStyles(element).getDisplay().equals(
                                    CSS.LIST_ITEM);
                }
            }
        });

        if (item instanceof TableRowBox) {
            new AddRowBelowHandler().execute(widget);
            // VexHandlerUtil.duplicateTableRow(vexWidget, (TableRowBox) item);
        } else if (item != null) {
            splitElement(widget, item.getElement());
        }
    }

}
