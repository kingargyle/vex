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
package org.eclipse.wst.xml.vex.core.internal.dom;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.wst.xml.vex.core.internal.core.Graphics;
import org.eclipse.wst.xml.vex.core.internal.css.StyleSheet;
import org.eclipse.wst.xml.vex.core.internal.css.StyleSheetReader;
import org.eclipse.wst.xml.vex.core.internal.dom.Document;
import org.eclipse.wst.xml.vex.core.internal.dom.DocumentReader;
import org.eclipse.wst.xml.vex.core.internal.layout.BlockElementBox;
import org.eclipse.wst.xml.vex.core.internal.layout.Box;
import org.eclipse.wst.xml.vex.core.internal.layout.CssBoxFactory;
import org.eclipse.wst.xml.vex.core.internal.layout.FakeGraphics;
import org.eclipse.wst.xml.vex.core.internal.layout.LayoutContext;

import junit.framework.TestCase;

public class BlockElementBoxTest extends TestCase {
    
    private Graphics g;
    private LayoutContext context;
    
    public BlockElementBoxTest() throws Exception {
        
        StyleSheetReader ssReader = new StyleSheetReader();
        StyleSheet ss = ssReader.read(this.getClass().getResource("test.css"));

        this.g = new FakeGraphics();
        
        this.context = new LayoutContext();
        this.context.setBoxFactory(new CssBoxFactory());
        this.context.setGraphics(this.g);
        this.context.setStyleSheet(ss);
    
    }
    
    public void testPositioning() throws Exception {
        
        String docString = "<root><small/><medium/><large/></root>";
        DocumentReader docReader = new DocumentReader();
        docReader.setDebugging(true);
        Document doc = docReader.read(docString);
        BlockElementBox box = new BlockElementBox(context, null, doc.getRootElement());
        
        Method createChildren = BlockElementBox.class.getDeclaredMethod("createChildren", new Class[] { LayoutContext.class });
        createChildren.setAccessible(true);
        createChildren.invoke(box, new Object[] { this.context });
        
        Box[] children = box.getChildren();
        assertEquals(3, children.length);
        assertEquals(1 + 10, this.getGap(box, 0));
        assertEquals(30 + 3 + 300 + 2 + 20, this.getGap(box, 1));
        assertEquals(60 + 6 + 600 + 3 + 30, this.getGap(box, 2));
        assertEquals(90 + 9, this.getGap(box, 3));
    }
    
    public int getGap(BlockElementBox box, int n) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Method getGap = BlockElementBox.class.getDeclaredMethod("getGap", new Class[] { Integer.TYPE });
        getGap.setAccessible(true);
        return ((Integer) getGap.invoke(box, new Object[] { new Integer(n) })).intValue();
    }
}
