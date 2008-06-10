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
package org.eclipse.jst.jsf.facelet.core.internal.cm.strategy;

import org.eclipse.jst.jsf.common.dom.TagIdentifier;
import org.eclipse.jst.jsf.common.internal.strategy.IIdentifiableStrategy;
import org.eclipse.jst.jsf.facelet.core.internal.cm.ExternalTagInfo;

/**
 * A strategy whose algorithm loads the tag CM data for a tag.
 * 
 * @author cbateman
 * 
 */
public interface IExternalMetadataStrategy extends
        IIdentifiableStrategy<TagIdentifier, ExternalTagInfo, String>
{
    
    /**
     * A null strategy that returns no result for all queries
     * @author cbateman
     *
     */
    static class NullExternalMetadataStrategy implements IExternalMetadataStrategy
    {

        public ExternalTagInfo getNoResult()
        {
            return ExternalTagInfo.NULL_INSTANCE;
        }

        public ExternalTagInfo perform(TagIdentifier input) throws Exception
        {
            return getNoResult();
        }

        public String getDisplayName()
        {
            return "NULL instance; you shouldn't see this label!"; //$NON-NLS-1$
        }

        public String getId()
        {
            return "NULL Strategy"; //$NON-NLS-1$
        }
        
    }
}
