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
package org.eclipse.jst.jsf.facelet.core.internal.facet;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/*package*/ class FaceletUninstallModel extends FacetChangeModel
{
    // default bean listeners
    private final PropertyChangeSupport _changeSupport = new PropertyChangeSupport(
                                                              this);

    public void addPropertyChangeListener(final String propertyName,
            final PropertyChangeListener listener)
    {
        _changeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(final String propertyName,
            final PropertyChangeListener listener)
    {
        _changeSupport.removePropertyChangeListener(propertyName, listener);
    }

    @Override
    public ChangeActionType getChangeActionType()
    {
        return ChangeActionType.REMOVE;
    }
}