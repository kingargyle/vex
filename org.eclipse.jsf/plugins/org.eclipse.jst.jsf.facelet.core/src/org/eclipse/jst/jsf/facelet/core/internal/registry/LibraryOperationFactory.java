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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.IFaceletTagRecord;

class LibraryOperationFactory
{
    private final FaceletTagRegistry _tagRegistry;

    public LibraryOperationFactory(final FaceletTagRegistry tagRegistry)
    {
        _tagRegistry = tagRegistry;
    }

    LibraryOperation createAddOperation(final IFaceletTagRecord changeRecord)
    {
        return new AddTagLibrary(_tagRegistry, changeRecord);
    }

    LibraryOperation createRemoveOperation(final IFaceletTagRecord changeRecord)
    {
        return new RemoveTagLibrary(_tagRegistry, changeRecord);
    }

    LibraryOperation createChangeOperation(final IFaceletTagRecord changeRecord)
    {
        if (changeRecord == null)
        {
            throw new IllegalArgumentException();
        }
        return new ChangeTagLibrary(_tagRegistry, changeRecord);
    }

    private static class AddTagLibrary extends LibraryOperation
    {
        private final FaceletTagRegistry _tagRegistry;

        public AddTagLibrary(final FaceletTagRegistry tagRegistry,
                final IFaceletTagRecord newRecord)
        {
            super(newRecord);
            _tagRegistry = tagRegistry;
        }

        @Override
        protected IStatus doRun()
        {
            synchronized (_tagRegistry)
            {
                // fire change event if applicable
                _tagRegistry.initialize(_changeRecord, true);
                return Status.OK_STATUS;
            }
        }
    }

    private static class RemoveTagLibrary extends LibraryOperation
    {
        private final FaceletTagRegistry _tagRegistry;

        protected RemoveTagLibrary(final FaceletTagRegistry tagRegistry,
                final IFaceletTagRecord changeRecord)
        {
            super(changeRecord);
            _tagRegistry = tagRegistry;
        }

        @Override
        protected IStatus doRun()
        {
            _tagRegistry.remove(_changeRecord);
            return Status.OK_STATUS;

        }

    }

    private static class ChangeTagLibrary extends LibraryOperation
    {
        private final FaceletTagRegistry _tagRegistry;

        protected ChangeTagLibrary(final FaceletTagRegistry tagRegistry,
                final IFaceletTagRecord changeRecord)
        {
            super(changeRecord);
            _tagRegistry = tagRegistry;
        }

        @Override
        protected IStatus doRun()
        {
            IStatus result = null;

            synchronized (_tagRegistry)
            {
                result = new RemoveTagLibrary(_tagRegistry, _changeRecord).doRun();

                if (result.getSeverity() != IStatus.ERROR
                        && result.getSeverity() != IStatus.CANCEL)
                {
                    result = new AddTagLibrary(_tagRegistry, _changeRecord)
                            .doRun();
                }
            }

            return result;
        }

    }

}
