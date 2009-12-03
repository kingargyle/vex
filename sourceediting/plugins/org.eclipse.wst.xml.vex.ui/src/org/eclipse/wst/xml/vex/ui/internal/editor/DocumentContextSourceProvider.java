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
package org.eclipse.wst.xml.vex.ui.internal.editor;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;
import org.eclipse.wst.xml.vex.ui.internal.handlers.VexHandlerUtil;
import org.eclipse.wst.xml.vex.ui.internal.handlers.VexHandlerUtil.RowColumnInfo;
import org.eclipse.wst.xml.vex.ui.internal.swt.VexWidget;

/**
 * If this class is declared in {@code org.eclipse.ui.services} extension then
 * state information about {@link VexWidget} could be exposed by variables.
 * This variables could then be used to manage visibility or enablement of UI
 * elements declarative in plug-in extensions (e.g. see 'enabledWhen' in
 * {@code org.eclipse.ui.handlers} extension or 'visibleWhen' in
 * {@code org.eclipse.ui.menus} extension).
 */
public class DocumentContextSourceProvider extends AbstractSourceProvider {

    /** Variable ID of the <em>is-column</em> flag.*/
    public static final String IS_COLUMN =
        "org.eclipse.wst.xml.vex.ui.isColumn";

    /** Variable ID of the <em>is-first-column</em> flag.*/
    public static final String IS_FIRST_COLUMN =
        "org.eclipse.wst.xml.vex.ui.isFirstColumn";

    /** Variable ID of the <em>is-last-column</em> flag.*/
    public static final String IS_LAST_COLUMN =
        "org.eclipse.wst.xml.vex.ui.isLastColumn";

    /** Variable ID of the <em>is-row</em> flag.*/
    public static final String IS_ROW =
        "org.eclipse.wst.xml.vex.ui.isRow";

    /** Variable ID of the <em>is-fist-row</em> flag.*/
    public static final String IS_FIRST_ROW =
        "org.eclipse.wst.xml.vex.ui.isFirstRow";

    /** Variable ID of the <em>is-last-row</em> flag.*/
    public static final String IS_LAST_ROW =
        "org.eclipse.wst.xml.vex.ui.isLastRow";

    private boolean isColumn;
    private boolean isFirstColumn;
    private boolean isLastColumn;
    private boolean isRow;
    private boolean isFirstRow;
    private boolean isLastRow;

    public void dispose() {
        // nothing to clean-up (all fields are primitives)
    }

    public String[] getProvidedSourceNames() {
        return new String[] {IS_COLUMN};
    }

    public Map<String, Boolean> getCurrentState() {
        Map<String, Boolean> currentState = new HashMap<String, Boolean>(6);
        currentState.put(IS_COLUMN, Boolean.valueOf(isColumn));
        currentState.put(IS_FIRST_COLUMN, Boolean.valueOf(isFirstColumn));
        currentState.put(IS_LAST_COLUMN, Boolean.valueOf(isLastColumn));
        currentState.put(IS_ROW, Boolean.valueOf(isRow));
        currentState.put(IS_FIRST_ROW, Boolean.valueOf(isFirstRow));
        currentState.put(IS_LAST_ROW, Boolean.valueOf(isLastRow));
        return currentState;
    }

    /**
     * Synchronizes the variable values which will be exposed by this service
     * with the specified {@link VexWidget}.
     *
     * @param widget the Vex widget containing the actual states
     */
    public void fireUpdate(VexWidget widget) {
        Map<String, Boolean> changes = new HashMap<String, Boolean>();
        RowColumnInfo rowColumnInfo = VexHandlerUtil.getRowColumnInfo(widget);

        // column
        int columnIndex = VexHandlerUtil.getCurrentColumnIndex(widget);
        int columnCount = rowColumnInfo == null ? -1 : rowColumnInfo.maxColumnCount;
        isColumn = update(changes, isColumn, columnIndex != -1, IS_COLUMN);
        isFirstColumn = update(changes, isFirstColumn, columnIndex == 0, IS_FIRST_COLUMN);
        isLastColumn = update(changes, isLastColumn, columnIndex == columnCount - 1, IS_LAST_COLUMN);

        // row
        int rowCount = rowColumnInfo == null ? -1 : rowColumnInfo.rowCount;
        int rowIndex = rowColumnInfo == null ? -1 : rowColumnInfo.rowIndex;
        isRow = update(changes, isRow, rowIndex != -1, IS_ROW);
        isFirstRow = update(changes, isFirstRow, rowIndex == 0, IS_FIRST_ROW);
        isLastRow = update(changes, isLastRow, rowIndex == rowCount - 1, IS_LAST_ROW);

        if (!changes.isEmpty()) {
            fireSourceChanged(ISources.WORKBENCH, changes);
        }
    }

    private static boolean update(Map<String, Boolean> changes,
                                  boolean oldValue,
                                  boolean newValue,
                                  String valueName) {
        if (newValue == oldValue) return oldValue;

        changes.put(valueName, Boolean.valueOf(newValue));
        return newValue;
    }
}
