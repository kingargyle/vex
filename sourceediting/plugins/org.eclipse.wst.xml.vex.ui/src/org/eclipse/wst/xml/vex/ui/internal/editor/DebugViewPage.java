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
package org.eclipse.wst.xml.vex.ui.internal.editor;

import java.lang.reflect.Field;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.wst.xml.vex.core.internal.core.Caret;
import org.eclipse.wst.xml.vex.core.internal.core.Rectangle;
import org.eclipse.wst.xml.vex.core.internal.layout.Box;
import org.eclipse.wst.xml.vex.core.internal.widget.HostComponent;
import org.eclipse.wst.xml.vex.core.internal.widget.VexWidgetImpl;
import org.eclipse.wst.xml.vex.ui.internal.swt.VexWidget;

/**
 * Page in the debug view.
 */
class DebugViewPage implements IPageBookViewPage {

	public DebugViewPage(VexEditor vexEditor) {
		this.vexEditor = vexEditor;
	}

	public void createControl(Composite parent) {

		this.composite = new Composite(parent, SWT.NONE);
		this.composite.setLayout(new FillLayout());

		if (this.vexEditor.isLoaded()) {
			this.createDebugPanel();
		} else {
			this.loadingLabel = new Label(this.composite, SWT.NONE);
			this.loadingLabel.setText("Loading...");
		}

		this.vexEditor.getEditorSite().getSelectionProvider()
				.addSelectionChangedListener(this.selectionListener);
	}

	public void dispose() {
		if (this.vexWidget != null && !this.vexWidget.isDisposed()) {
			this.vexWidget.removeMouseMoveListener(this.mouseMoveListener);
		}
		this.vexEditor.getEditorSite().getSelectionProvider()
				.removeSelectionChangedListener(this.selectionListener);
	}

	public Control getControl() {
		return this.composite;
	}

	public IPageSite getSite() {
		return this.site;
	}

	public void init(IPageSite site) throws PartInitException {
		this.site = site;
	}

	public void setActionBars(IActionBars actionBars) {
	}

	public void setFocus() {
	}

	// ================================================== PRIVATE

	private static final int X = 1;
	private static final int Y = 2;
	private static final int WIDTH = 3;
	private static final int HEIGHT = 4;

	private static Field implField;
	private static Field rootBoxField;
	private static Field caretField;
	private static Field hostComponentField;

	static {
		try {
			implField = VexWidget.class.getDeclaredField("impl");
			implField.setAccessible(true);
			rootBoxField = VexWidgetImpl.class.getDeclaredField("rootBox");
			rootBoxField.setAccessible(true);
			caretField = VexWidgetImpl.class.getDeclaredField("caret");
			caretField.setAccessible(true);
			hostComponentField = VexWidgetImpl.class
					.getDeclaredField("hostComponent");
			hostComponentField.setAccessible(true);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private IPageSite site;
	private VexEditor vexEditor;
	private VexWidget vexWidget;
	private VexWidgetImpl impl;
	private Composite composite;

	private Label loadingLabel;

	private Table table;
	private TableItem documentItem;
	private TableItem viewportItem;
	private TableItem caretOffsetItem;
	private TableItem caretAbsItem;
	private TableItem caretRelItem;
	private TableItem mouseAbsItem;
	private TableItem mouseRelItem;

	private void createDebugPanel() {

		if (this.loadingLabel != null) {
			this.loadingLabel.dispose();
			this.loadingLabel = null;
		}

		this.vexWidget = this.vexEditor.getVexWidget();
		try {
			this.impl = (VexWidgetImpl) implField.get(this.vexWidget);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		composite.setLayout(layout);
		GridData gd;

		ScrolledComposite sc = new ScrolledComposite(this.composite,
				SWT.V_SCROLL);
		this.table = new Table(sc, SWT.NONE);
		this.table.setHeaderVisible(true);
		sc.setContent(table);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = GridData.FILL;
		sc.setLayoutData(gd);

		TableColumn column;
		column = new TableColumn(this.table, SWT.LEFT);
		column.setText("Item");
		column = new TableColumn(this.table, SWT.RIGHT);
		column.setText("X");
		column = new TableColumn(this.table, SWT.RIGHT);
		column.setText("Y");
		column = new TableColumn(this.table, SWT.RIGHT);
		column.setText("Width");
		column = new TableColumn(this.table, SWT.RIGHT);
		column.setText("Height");

		this.table.addControlListener(this.controlListener);

		this.documentItem = new TableItem(this.table, SWT.NONE);
		this.documentItem.setText(0, "Document");
		this.viewportItem = new TableItem(this.table, SWT.NONE);
		this.viewportItem.setText(0, "Viewport");
		this.caretOffsetItem = new TableItem(this.table, SWT.NONE);
		this.caretOffsetItem.setText(0, "Caret Offset");
		this.caretAbsItem = new TableItem(this.table, SWT.NONE);
		this.caretAbsItem.setText(0, "Caret Abs.");
		this.caretRelItem = new TableItem(this.table, SWT.NONE);
		this.caretRelItem.setText(0, "Caret Rel.");
		this.mouseAbsItem = new TableItem(this.table, SWT.NONE);
		this.mouseAbsItem.setText(0, "Mouse Abs.");
		this.mouseRelItem = new TableItem(this.table, SWT.NONE);
		this.mouseRelItem.setText(0, "Mouse Rel.");

		Button updateButton = new Button(composite, SWT.PUSH);
		updateButton.setText("Refresh");
		updateButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				repopulate();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		this.composite.layout();

		this.vexWidget.addMouseMoveListener(this.mouseMoveListener);

		this.repopulate();

	}

	private ISelectionChangedListener selectionListener = new ISelectionChangedListener() {
		public void selectionChanged(SelectionChangedEvent event) {
			if (vexWidget == null) {
				createDebugPanel();
			}
			repopulate();
		}
	};

	private ControlListener controlListener = new ControlListener() {
		public void controlMoved(ControlEvent e) {
		}

		public void controlResized(ControlEvent e) {
			int width = table.getSize().x;
			int numWidth = Math.round(width * 0.125f);
			table.getColumn(0).setWidth(width / 2);
			table.getColumn(1).setWidth(numWidth);
			table.getColumn(2).setWidth(numWidth);
			table.getColumn(3).setWidth(numWidth);
		}
	};

	private MouseMoveListener mouseMoveListener = new MouseMoveListener() {

		public void mouseMove(MouseEvent e) {
			Rectangle rect = new Rectangle(e.x, e.y, 0, 0);
			Rectangle viewport = getViewport();
			setItemFromRect(mouseAbsItem, rect);
			setItemRel(mouseRelItem, viewport, rect);
		}

	};

	private Rectangle getCaretBounds() {
		Caret caret = (Caret) this.getFieldValue(caretField, this.impl);
		return caret.getBounds();
	}

	private Rectangle getRootBoxBounds() {
		Box rootBox = (Box) this.getFieldValue(rootBoxField, this.impl);
		return new Rectangle(rootBox.getX(), rootBox.getY(),
				rootBox.getWidth(), rootBox.getHeight());
	}

	private Rectangle getViewport() {
		HostComponent hc = (HostComponent) this.getFieldValue(
				hostComponentField, this.impl);
		return hc.getViewport();
	}

	private Object getFieldValue(Field field, Object o) {
		try {
			return field.get(o);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void repopulate() {
		setItemFromRect(this.documentItem, this.getRootBoxBounds());
		Rectangle viewport = this.getViewport();
		this.caretOffsetItem.setText(1, Integer.toString(this.impl
				.getCaretOffset()));
		setItemFromRect(this.viewportItem, viewport);
		setItemFromRect(this.caretAbsItem, this.getCaretBounds());
		setItemRel(this.caretRelItem, viewport, this.getCaretBounds());
	}

	private static void setItemFromRect(TableItem item, Rectangle rect) {
		item.setText(X, Integer.toString(rect.getX()));
		item.setText(Y, Integer.toString(rect.getY()));
		item.setText(WIDTH, Integer.toString(rect.getWidth()));
		item.setText(HEIGHT, Integer.toString(rect.getHeight()));
	}

	private static void setItemRel(TableItem item, Rectangle viewport,
			Rectangle rect) {
		item.setText(X, Integer.toString(rect.getX() - viewport.getX()));
		item.setText(Y, Integer.toString(rect.getY() - viewport.getY()));
		item.setText(WIDTH, Integer.toString(rect.getWidth()));
		item.setText(HEIGHT, Integer.toString(rect.getHeight()));
	}
}