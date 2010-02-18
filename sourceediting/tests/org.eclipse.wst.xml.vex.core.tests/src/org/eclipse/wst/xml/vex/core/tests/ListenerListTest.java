/*******************************************************************************
 * Copyright (c) 2010 Florian Thienel and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Florian Thienel - initial API and implementation
 *     Holger Voormann
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.tests;

import java.lang.reflect.InvocationTargetException;
import java.util.EventListener;
import java.util.EventObject;

import junit.framework.TestCase;

import org.eclipse.wst.xml.vex.core.internal.core.ListenerList;

public class ListenerListTest extends TestCase {

    private ListenerList<MockEventListener, EventObject> listenerList;
    private Exception handledException;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        listenerList = new ListenerList<MockEventListener, EventObject>(MockEventListener.class) {
            @Override
            public void handleException(Exception e) {
                handledException = e;
            }
        };
    }

    @Override
    protected void tearDown() throws Exception {
        listenerList = null;
        handledException = null;
        super.tearDown();
    }

    public static class MockEventListener implements EventListener {
        public int invocations = 0;
        public boolean throwExceptionMethodInvoced;

        public void handleEvent(EventObject event) {
            invocations++;
        }

        public void throwException(EventObject event) {
            throwExceptionMethodInvoced = true;
            throw new MyException();
        }
        
    }
    
    private static class MyException extends RuntimeException {

		private static final long serialVersionUID = 1L;

    }
    
    public void testListenerInvocation() throws Exception {
        final MockEventListener eventListener = new MockEventListener();

        listenerList.add(eventListener);
        assertEquals(0, eventListener.invocations);
        listenerList.fireEvent("handleEvent", new EventObject(""));
        assertEquals(1, eventListener.invocations);
        listenerList.fireEvent("handleEvent", new EventObject(""));
        assertEquals(2, eventListener.invocations);

        // add another listener
        listenerList.add(new MockEventListener());
        assertEquals(2, eventListener.invocations);
        listenerList.fireEvent("handleEvent", new EventObject(""));
        assertEquals(3, eventListener.invocations);

        if (handledException != null) {
            throw handledException;
        }
    }

    public void testAddRemove() throws Exception {
        final MockEventListener listener = new MockEventListener();
        final MockEventListener anotherListener = new MockEventListener();


        listenerList.fireEvent("handleEvent", new EventObject(""));
        assertEquals("The listener should not be invoked if it was never added to the list.",
                     0,
                     listener.invocations);

        listenerList.add(listener);
        listenerList.fireEvent("handleEvent", new EventObject(""));
        assertEquals("The listener should not be invoked if it was never added to the list.",
                     0,
                     anotherListener.invocations);
        assertEquals("The listener should be invoked after it was added to the list",
                     1,
                     listener.invocations);

        listenerList.remove(listener);
        listenerList.fireEvent("handleEvent", new EventObject(""));
        assertEquals("The listener should not be invoked after it was removed from the list.",
                     1,
                     listener.invocations);

        if (handledException != null) {
            throw handledException;
        }
    }

    public void testNoSuchMethod() throws Exception {
        listenerList.add(new MockEventListener());
        listenerList.fireEvent("unknownMethod", new EventObject(""));
        assertTrue(handledException instanceof NoSuchMethodException);
    }

    public void testExceptionWhileFireEvent() throws Exception {
        final MockEventListener eventListener = new MockEventListener();

        listenerList.add(eventListener);
        listenerList.fireEvent("throwException", new EventObject(""));
        assertTrue(eventListener.throwExceptionMethodInvoced);
        assertTrue(handledException instanceof InvocationTargetException);
        assertTrue(handledException.getCause() instanceof MyException);
    }
    
}
