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
import org.eclipse.jst.jsf.facelet.core.internal.cm.ExternalTagInfo;
import org.eclipse.jst.jsp.core.internal.contentmodel.tld.provisional.TLDDocument;
import org.eclipse.wst.xml.core.internal.contentmodel.CMElementDeclaration;
import org.eclipse.wst.xml.core.internal.contentmodel.CMNamedNodeMap;

/**
 * A metadata strategy that uses JSP TLD metadata.
 * 
 * @author cbateman
 * 
 */
public class JSPExternalMetadataStrategy extends
        AbstractExternalMetadataStrategy
{
    private final TLDDocument  _doc;
    /**
     * The unique identifier for the strategy.
     */
    public final static String STRATEGY_ID = "org.eclipse.jst.jsf.facelet.core.internal.cm.strategy.JSPExternalMetadataStrategy"; //$NON-NLS-1$

    /**
     * @param doc 
     */
    public JSPExternalMetadataStrategy(final TLDDocument doc)
    {
        super(STRATEGY_ID, Messages.JSPExternalMetadataStrategy_DisplayName);
        _doc = doc;
    }

    @Override
    public ExternalTagInfo perform(final TagIdentifier input) throws Exception
    {
        if (_doc != null)
        {
            return new TLDMetadataExternalInfo(_doc);
        }
        return getNoResult();
    }

    private static class TLDMetadataExternalInfo extends ExternalTagInfo
    {
        private final TLDDocument _doc;

        public TLDMetadataExternalInfo(final TLDDocument doc)
        {
            _doc = doc;
        }

        @Override
        public CMNamedNodeMap getAttributes(final String tagName)
        {
            final CMElementDeclaration element = (CMElementDeclaration) _doc
                    .getElements().getNamedItem(tagName);

            if (element != null)
            {
                return element.getAttributes();
            }
            return null;
        }

        @Override
        public Object getTagProperty(final String tagName, final String key)
        {
            final CMElementDeclaration element = (CMElementDeclaration) _doc
                    .getElements().getNamedItem(tagName);
            if (element != null)
            {
                return element.getProperty(key);
            }
            return null;
        }
    }
}
