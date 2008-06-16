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
import org.eclipse.jst.jsf.facelet.core.internal.FaceletCorePlugin;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.IFaceletTagRecord;

abstract class LibraryOperation
{
    protected final IFaceletTagRecord _changeRecord;
    private IStatus               _result;

    protected LibraryOperation(final IFaceletTagRecord changeRecord)
    {
        _changeRecord = changeRecord;
    }

    public final void run()
    {
        try
        {
            _result = doRun();
        }
        catch (final Exception e)
        {
            _result = new Status(IStatus.ERROR, FaceletCorePlugin.PLUGIN_ID,
                    "Problem during run", e); //$NON-NLS-1$
        }
    }

    public IStatus getResult()
    {
        return _result;
    }

    protected abstract IStatus doRun();
}
