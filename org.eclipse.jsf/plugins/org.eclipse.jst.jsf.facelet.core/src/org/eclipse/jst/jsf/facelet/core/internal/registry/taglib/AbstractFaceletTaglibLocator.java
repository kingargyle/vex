/*******************************************************************************
 * Copyright (c) 2008 Oracle Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Cameron Bateman - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.jsf.facelet.core.internal.registry.taglib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jst.jsf.facelet.core.internal.FaceletCorePlugin;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibDefn;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Parent of all locators of facelet taglibs.
 * 
 * @author cbateman
 * 
 */
public abstract class AbstractFaceletTaglibLocator
{
    private static final String FACELET_TAGLIB_DTD_PATH = "/dtd/facelet-taglib_1_0.dtd"; //$NON-NLS-1$

    private final CopyOnWriteArrayList<Listener>        _listeners = new CopyOnWriteArrayList<Listener>();

    /**
     * @return a list of all tag libraries known to this locator
     */
    public abstract Map<String, ? extends IFaceletTagRecord> locate();

    /**
     * @param listener
     */
    public void addListener(final Listener listener)
    {
        _listeners.addIfAbsent(listener);
    }
    
    /**
     * @param listener
     */
    public void removeListener(final Listener listener)
    {
        _listeners.remove(listener);
    }

    /**
     */
    public abstract void startLocating();

    /**
     * 
     */
    public abstract void stopLocating();

    /**
     * @param event
     */
    protected final void fireEvent(final Listener.TaglibChangedEvent event)
    {
        for (final Listener listener : _listeners)
        {
            listener.tagLibChanged(event);
        }
    }
    

    /**
     * @param buffer
     * @param defaultDtdStream
     * @return the tag library definition (loaded EMF model) for the buffer
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    protected final FaceletTaglibDefn loadFromBuffer(final byte[] buffer,
            final InputStream defaultDtdStream) throws IOException,
            ParserConfigurationException, SAXException
    {
        final InputSource inputSource = new InputSource(
                new ByteArrayInputStream(buffer));

        final Document doc = TagModelParser.getDefaultTaglibDocument(
                inputSource, new InputSource(defaultDtdStream));
        final FaceletTaglibDefn tagLib = TagModelParser.processDocument(doc);
        return tagLib;
    }

    /**
     * @return the input stream for the default bundle Facelet dtd.
     * @throws IOException
     */
    protected final InputStream getDefaultDTDSource() throws IOException
    {
        final URL url = FaceletCorePlugin.getDefault().getBundle().getEntry(
                FACELET_TAGLIB_DTD_PATH);

        if (url != null)
        {
            return url.openStream();
        }
        return null;
    }

    /**
     * @param is must be open.  Caller is responsible for closing.
     * @return load the stream into a byte buffer.  
     */
    protected final byte[] getBufferForEntry(final InputStream is)
    {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        final byte[] buffer = new byte[2048];

        int bytesRead = 0;

        try
        {
            while (((bytesRead = is.read(buffer))) != -1)
            {
                stream.write(buffer, 0, bytesRead);
            }
        }
        catch (final IOException e)
        {
            FaceletCorePlugin.log("Error loading buffer", e); //$NON-NLS-1$
            return null;
        }

        return stream.toByteArray();
    }

}
