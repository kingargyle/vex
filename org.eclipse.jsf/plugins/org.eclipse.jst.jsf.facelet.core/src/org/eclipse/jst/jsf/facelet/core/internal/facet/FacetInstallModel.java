package org.eclipse.jst.jsf.facelet.core.internal.facet;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class FacetInstallModel
{
    private boolean _addDefaultSuffix;
    private boolean _addViewHandler;
    private boolean _addConfigureListener;
    private boolean _addWebAppLifecycleListener;
    
    public boolean isAddViewHandler()
    {
        return _addViewHandler;
    }

    public void setAddViewHandler(boolean addViewHandler)
    {
        _addViewHandler = addViewHandler;
    }

    public boolean isAddDefaultSuffix()
    {
        return _addDefaultSuffix;
    }

    public void setAddDefaultSuffix(boolean addDefaultSuffix)
    {
        _addDefaultSuffix = addDefaultSuffix;
    }

    public boolean isAddConfigureListener()
    {
        return _addConfigureListener;
    }

    public void setAddConfigureListener(boolean addConfigureListener)
    {
        _addConfigureListener = addConfigureListener;
    }

    public boolean isAddWebAppLifecycleListener()
    {
        return _addWebAppLifecycleListener;
    }

    public void setAddWebAppLifecycleListener(boolean addWebAppLifecycleListener)
    {
        _addWebAppLifecycleListener = addWebAppLifecycleListener;
    }

    // default bean listeners
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(
                                                        this);

    public void addPropertyChangeListener(String propertyName,
            PropertyChangeListener listener)
    {
        changeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName,
            PropertyChangeListener listener)
    {
        changeSupport.removePropertyChangeListener(propertyName, listener);
    }

}
