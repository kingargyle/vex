package org.eclipse.jst.jsf.facelet.core.internal.facet;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/*package*/ class FacetInstallModel extends FacetChangeModel
{
    // default bean listeners
    private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(
                                                              this);

    public void addPropertyChangeListener(final String propertyName,
            final PropertyChangeListener listener)
    {
        changeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(final String propertyName,
            final PropertyChangeListener listener)
    {
        changeSupport.removePropertyChangeListener(propertyName, listener);
    }

    @Override
    public ChangeActionType getChangeActionType()
    {
        return ChangeActionType.ADD;
    }
}
