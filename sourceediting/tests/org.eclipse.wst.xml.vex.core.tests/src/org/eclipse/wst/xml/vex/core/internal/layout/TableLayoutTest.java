/*******************************************************************************
 * Copyright (c) 2009 Holger Voormann and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Holger Voormann - initial API and implementation
 *     Igor Jacy Lino Campista - Java 5 warnings fixed (bug 311325)
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.layout;

import java.util.Arrays;
import java.util.Stack;

import junit.framework.TestCase;

import org.eclipse.wst.xml.vex.core.internal.core.DisplayDevice;
import org.eclipse.wst.xml.vex.core.internal.css.MockDisplayDevice;
import org.eclipse.wst.xml.vex.core.internal.css.StyleSheet;
import org.eclipse.wst.xml.vex.core.internal.css.StyleSheetReader;
import org.eclipse.wst.xml.vex.core.internal.dom.Document;
import org.eclipse.wst.xml.vex.core.internal.dom.Element;
import org.eclipse.wst.xml.vex.core.internal.dom.RootElement;

public class TableLayoutTest extends TestCase {

    private static interface StackVisitor {
        void visit(StackElement element);
    }

    private static class StackElement {
        public final int indent;
        public final Box box;
        public StackElement(int indent, Box box) {
            this.indent = indent;
            this.box = box;
        }
    }

    private LayoutContext context;
    private Document document;
    private RootBox rootBox;
    private int caretPosition;

    @Override
    protected void setUp() throws Exception {

        // display dummy
        DisplayDevice.setCurrent(new MockDisplayDevice(90, 90));

        // context dummy
        context = new LayoutContext();
        context.setBoxFactory(new MockBoxFactory());
        context.setGraphics(new FakeGraphics());

        // set CSS
        String css =   "root   {display:block}"
                     + "inline {display:inline}"
                     + "table  {display:table}"
                     + "tcap   {display:table-caption}"
                     + "td     {display:table-cell}"
                     + "tc     {display:table-column}"
                     + "tcg    {display:table-column-group}"
                     + "tfg    {display:table-footer-group}"
                     + "thg    {display:table-header-group}"
                     + "tr     {display:table-row}"
                     + "trg    {display:table-row-group}";
        StyleSheet styleSheet = new StyleSheetReader().read(css);
        context.setStyleSheet(styleSheet);

        resetDocument();
    }

    @Override
    protected void tearDown() throws Exception {
        rootBox = null;
        document = null;
        context = null;
    }

    private void resetDocument() {
        document = new Document(new RootElement("root"));
        context.setDocument(document);
        caretPosition = 1;
        rootBox = new RootBox(this.context, document.getRootElement(), 500);
    }

    private void insertElement(String elementName) {
        document.insertElement(caretPosition, new Element(elementName));
        caretPosition++;
    }

    private void insertText(String text) {
        document.insertText(caretPosition, text);;
        caretPosition += text.length();
    }

    public void testValidTable() throws Exception {

        // single cell Table
        insertElement("table");
        insertElement("tr");
        insertElement("td");
        insertText("a");
        assertCount(1, TableBox.class);
        assertCount(1, TableRowBox.class);
        assertCount(1, TableCellBox.class);
        assertCount(1, DocumentTextBox.class);
        assertEquals("a", contentAsText());

        // 2x2 table plus text
        resetDocument();
        insertText("_");
        insertElement("table");
        insertElement("tr");
        insertElement("td");
        insertText("a");
        caretPosition++;
        insertElement("td");
        insertText("b");
        caretPosition+=2;
        insertElement("tr");
        insertElement("td");
        insertText("c");
        caretPosition++;
        insertElement("td");
        insertText("d");
        assertCount(1, TableBox.class);
        assertCount(2, TableRowBox.class);
        assertCount(4, TableCellBox.class);
        assertCount(5, DocumentTextBox.class);
        assertEquals("_abcd", contentAsText());
    }

    // table elements outside table (separately tested to improve tracing if
    // StackOverflowError will be thrown)
    public void testCaptionOutsideTable()     { test("tcap"); }
    public void testCellOutsideTable()        { test("td"); }
    public void testColumnOutsideTable()      { test("tc"); }
    public void testColumnGroupOutsideTable() { test("tcg"); }
    public void testFooterGroupOutsideTable() { test("tfg"); }
    public void testHeaderGroupOutsideTable() { test("thg"); }
    public void testRowOutsideTable()         { test("tr"); }
    public void testRowGroupOutsideTable()    { test("trg"); }
    
    // invalid nested table elements (separately tested to improve tracing if
    // StackOverflowError will be thrown)
    public void testInvalidNesting1() { test("inline", "tcap"); }
    public void testInvalidNesting2() { test("table", "td"); }
    public void testInvalidNesting3() { test("td", "tr"); }
    public void testInvalidNesting4() { test("trg", "trg"); }
    public void testInvalidNesting5() { test("tr", "tfg"); }
    public void testInvalidNesting6() { test("td", "thg"); }
    public void testInvalidNesting7() { test("table", "tc"); }
    public void testInvalidNesting8() { test("thg", "tcg"); }
    
    public void test(String ... elements) {
        resetDocument();
        insertElement("inline");
        for (String element : elements) {
        	insertElement(element);
		}
        insertText("x");
        assertCount(1, DocumentTextBox.class);
        assertEquals("x", contentAsText());
    }
    
    private String contentAsText() {
        return document.getText(0, document.getLength());
    }

    private void assertCount(int expected, Class<? extends Box> blockClass) {
        int count = count(blockClass);
        String message =   "expected count of <"
                         + blockClass.getSimpleName()
                         + ">: <"
                         + expected
                         + "> but was: <"
                         + count
                         + ">\n"
                         + "Actual layout stack trace:\n"
                         + layoutStackToString();
        assertEquals(message, expected, count);
    }

    private int count(final Class<? extends Box> blockClass) {
        final int[] mutableInteger = new int[1];
        mutableInteger[0] = 0;
        travelLayoutStack(new StackVisitor() {

            public void visit(StackElement element) {
                if (element.box.getClass().equals(blockClass)) {
                    mutableInteger[0]++;
                }
            }

        });
        return mutableInteger[0];
    }

    private String layoutStackToString() {
        final StringBuilder result = new StringBuilder();
        travelLayoutStack(new StackVisitor() {

            public void visit(StackElement element) {
                if (element.indent > 0) {
                    char[] indentChars = new char[element.indent * 2];
                    Arrays.fill(indentChars, ' ');
                    result.append(indentChars);
                }
                result.append(element.box.getClass().getSimpleName());
                result.append('\n');

            }

        });
        return result.toString();
    }

    private void travelLayoutStack(StackVisitor visitor) {

        // already layouted?
        Box[] rootElementChildren = rootBox.getChildren()[0].getChildren();
        if (rootElementChildren == null || rootElementChildren.length == 0) {
            rootBox.layout(this.context, 0, Integer.MAX_VALUE);
        }

        Stack<StackElement> stack = new Stack<StackElement>();
        stack.push(new StackElement(0, rootBox));
        while (!stack.isEmpty()) {
            StackElement current = stack.pop();
            visitor.visit(current);

            // iterate deep-first
            for (Box child : current.box.getChildren()) {
            	stack.push(new StackElement(current.indent + 1, child));
			}
        }
    }

}