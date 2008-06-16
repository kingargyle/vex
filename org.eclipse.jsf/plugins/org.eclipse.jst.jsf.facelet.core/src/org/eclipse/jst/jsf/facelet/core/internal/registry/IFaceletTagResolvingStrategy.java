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
package org.eclipse.jst.jsf.facelet.core.internal.registry;

import org.eclipse.jst.jsf.designtime.internal.view.model.jsp.ITagResolvingStrategy;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.TagDefn;

/**
 * @author cbateman
 *
 */
public interface IFaceletTagResolvingStrategy extends
        ITagResolvingStrategy<IFaceletTagResolvingStrategy.TLDWrapper, String>
{

    /**
     * Wraps data for Facelet tag information to used by the resolving strategy
     * 
     * @author cbateman
     *
     */
    public static class TLDWrapper
    {
        private final String  _uri;
        private final TagDefn _tagDefn;

        /**
         * @param tagDefn
         * @param uri
         */
        public TLDWrapper(TagDefn tagDefn, String uri)
        {
            super();
            _tagDefn = tagDefn;
            _uri = uri;
        }

        /**
         * @return the uri for the tld namespace
         */
        public final String getUri()
        {
            return _uri;
        }

        /**
         * @return the tag definition information
         */
        public final TagDefn getTagDefn()
        {
            return _tagDefn;
        }

    }
}
