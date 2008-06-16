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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jem.internal.proxy.core.ICallbackRegistry;
import org.eclipse.jem.internal.proxy.core.ProxyFactoryRegistry;
import org.eclipse.jst.jsf.facelet.core.internal.FaceletCorePlugin;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletLibraryClassTagLib;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibDefn;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletXMLDefnTaglib;

/*package*/class TagRecordFactory
{
    private final ProxyFactoryRegistry                      _registry;

    public TagRecordFactory(final IProject project,
            final ProxyFactoryRegistry registry)
    {
        _registry = registry;
    }

    public IFaceletTagRecord createRecords(final  FaceletTaglibDefn taglibDefn)
    {
        IFaceletTagRecord  retValue = null;

        if (taglibDefn instanceof FaceletLibraryClassTagLib)
        {
            if (_registry != NULL_REGISTRY)
            {
                final LibraryClassBasedTagRecord record = new LibraryClassBasedTagRecord(
                        _registry, (FaceletLibraryClassTagLib) taglibDefn);
                try
                {
                    record.initURI();
                    retValue = record;
                }
                catch (CoreException e)
                {
                    FaceletCorePlugin.log("While creating record: "+record, e); //$NON-NLS-1$
                }
            }
        }
        else if (taglibDefn instanceof FaceletXMLDefnTaglib)
        {
            final XMLBasedTagRecord record = new XMLBasedTagRecord(
                    (FaceletXMLDefnTaglib) taglibDefn);
            retValue = record;
        }
        return retValue;
    }

    private final static NullProxyFactoryRegistry NULL_REGISTRY = new NullProxyFactoryRegistry();

    private static class NullProxyFactoryRegistry extends ProxyFactoryRegistry
    {

        @Override
        public ICallbackRegistry getCallbackRegistry()
        {
            throw new UnsupportedOperationException("This is null proxy"); //$NON-NLS-1$
        }

        @Override
        protected void registryTerminated(final boolean wait)
        {
            throw new UnsupportedOperationException("This is null proxy"); //$NON-NLS-1$
        }
    }
}