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

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jst.jsf.common.dom.TagIdentifier;
import org.eclipse.jst.jsf.common.internal.policy.IdentifierOrderedIteratorPolicy;
import org.eclipse.jst.jsf.common.internal.strategy.IteratorPolicyBasedStrategyComposite;
import org.eclipse.jst.jsf.facelet.core.internal.cm.ExternalTagInfo;

/**
 * A composite of strategies for deriving external tag metadata.
 * 
 * @author cbateman
 * 
 */
public class TagInfoStrategyComposite
        extends
        IteratorPolicyBasedStrategyComposite<TagIdentifier, ExternalTagInfo, String, IExternalMetadataStrategy>
{
    private final Iterable<String> _policyOrder;

    /**
     * @param policyOrder
     */
    public TagInfoStrategyComposite(final Iterable<String> policyOrder)
    {
        super(new MyIteratorPolicy(policyOrder));
        _policyOrder = policyOrder;
    }

    @Override
    public ExternalTagInfo getNoResult()
    {
        return ExternalTagInfo.NULL_INSTANCE;
    }

    /**
     * 
     */
    public void resetIterator()
    {
        setPolicy(new MyIteratorPolicy(_policyOrder));
    }

    private static class MyIteratorPolicy extends
            IdentifierOrderedIteratorPolicy<String>
    {
        private Iterator<String> _iterator;

        public MyIteratorPolicy(final Iterable<String> policyOrder)
        {
            super(policyOrder);
            setExcludeNonExplicitValues(true);
        }

        @Override
        public Iterator<String> getIterator(
                final Collection<String> forCollection)
        {
            if (_iterator == null)
            {
                _iterator = super.getIterator(forCollection);
            }
            return _iterator;
        }
    }
}
