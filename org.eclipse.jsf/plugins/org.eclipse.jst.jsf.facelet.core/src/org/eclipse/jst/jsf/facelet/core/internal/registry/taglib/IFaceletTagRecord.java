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

import java.io.Serializable;
import java.util.Collection;
import java.util.EventObject;

import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.TagDefn;

/**
 * @author cbateman
 * 
 */
public interface IFaceletTagRecord extends Serializable
{
    /**
     * @return the uri of the tag library
     */
    String getURI();

    /**
     * This may be implemented differently than getTags().size(), since getting
     * all tags may be very expensive, while the overall number may be cheap.
     * 
     * However, it should always be true that getTags().size() == getNumTags()
     * 
     * @return the number of tags in this record.
     */
    int getNumTags();

    /**
     * @return the tag definitions
     */
    Collection<? extends TagDefn> getTags();

    /**
     * @param name
     * @return the tag definition for name or null.
     */
    TagDefn getTag(final String name);

    /**
     * @param listener
     */
    void addListener(final ITagRecordChangeListener listener);
    
    /**
     * @param listener
     */
    void removeListener(final ITagRecordChangeListener listener);

    /**
     * Indicates that a tag record has changed
     */
    static class TagRecordChangeEvent extends EventObject
    {
        /**
         * 
         */
        private static final long serialVersionUID = 5655356157624922019L;

        TagRecordChangeEvent(IFaceletTagRecord source)
        {
            super(source);
        }
    }

    /**
     * A listener for tag record change events.
     *
     */
    interface ITagRecordChangeListener
    {
        public void changed(final TagRecordChangeEvent event);
    }
}
