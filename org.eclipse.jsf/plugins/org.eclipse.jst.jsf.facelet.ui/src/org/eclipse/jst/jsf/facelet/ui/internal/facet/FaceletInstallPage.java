package org.eclipse.jst.jsf.facelet.ui.internal.facet;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jst.jsf.facelet.core.internal.facet.FacetInstallModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.wst.common.project.facet.ui.AbstractFacetWizardPage;

public class FaceletInstallPage extends AbstractFacetWizardPage// DataModelWizardPage
// implements
// IFacetWizardPage
{
    public FaceletInstallPage()
    {
        super("Facelet Install Page");
    }

    private Button             _addDefaultSuffix;
    private Button             _addViewHandler;
    private Button             _addConfigureListener;
    private Button             _addWebappLifecycleListener;
    
    private FacetInstallModel  _dataModel;
    private DataBindingContext _bindingContext;

    public void setConfig(final Object config)
    {
        _dataModel = (FacetInstallModel) config;

    }

    private void initDefaultSuffixButton(final Composite parent)
    {
        _addDefaultSuffix = new Button(parent, SWT.CHECK);
        _addDefaultSuffix.setText("Add '.html' DEFAULT_SUFFIX parameter");
        _addDefaultSuffix.setSelection(_dataModel.isAddDefaultSuffix());
        _addDefaultSuffix.setLayoutData(new RowData());
        IObservableValue modelObservable = BeansObservables.observeValue(
                _dataModel, "addDefaultSuffix");

        _bindingContext.bindValue(SWTObservables
                .observeSelection(_addDefaultSuffix), modelObservable, null,
                null);
    }

    private void initViewHandlerButton(final Composite parent)
    {
        _addViewHandler = new Button(parent, SWT.CHECK);
        _addViewHandler.setText("Add Facelet view handler");
        _addViewHandler.setSelection(_dataModel.isAddViewHandler());
        _addViewHandler.setLayoutData(new RowData());
        IObservableValue modelObservable = BeansObservables.observeValue(
                _dataModel, "addViewHandler");

        _bindingContext
                .bindValue(SWTObservables.observeSelection(_addViewHandler),
                        modelObservable, null, null);
    }

    private void  initConfigureListener(final Composite parent)
    {
        _addConfigureListener = new Button(parent, SWT.CHECK);
        _addConfigureListener.setText("Add configure listener (needed by some Tomcat containers)");
        _addConfigureListener.setSelection(_dataModel.isAddConfigureListener());
        _addConfigureListener.setLayoutData(new RowData());
        IObservableValue modelObservable = BeansObservables.observeValue(
                _dataModel, "addConfigureListener");

        _bindingContext
                .bindValue(SWTObservables.observeSelection(_addConfigureListener),
                        modelObservable, null, null);
    }
    
    private void initWebappLifecycleListener(final Composite parent)
    {
        _addWebappLifecycleListener = new Button(parent, SWT.CHECK);
        _addWebappLifecycleListener.setText("Add web application lifecycle listener (needed by some Tomcat containers)");
        _addWebappLifecycleListener.setSelection(_dataModel.isAddConfigureListener());
        _addWebappLifecycleListener.setLayoutData(new RowData());
        IObservableValue modelObservable = BeansObservables.observeValue(
                _dataModel, "addWebAppLifecycleListener");

        _bindingContext
                .bindValue(SWTObservables.observeSelection(_addWebappLifecycleListener),
                        modelObservable, null, null);
    }
    public void createControl(final Composite parent)
    {
        setTitle("Facelet");
        setMessage("Configure Facelet settings");

        _bindingContext = new DataBindingContext();

        // WizardPageSupport.create(this, _bindingContext);

        final Composite control = new Composite(parent, SWT.NONE);
        final RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
        rowLayout.fill = true;
        control.setLayout(rowLayout);

        final Group webXMLGroup = new Group(control, SWT.NONE);
        webXMLGroup.setLayout(rowLayout);
        webXMLGroup.setText("Deployment Descriptor (web.xml) Configuration");
        initDefaultSuffixButton(webXMLGroup);
        initConfigureListener(webXMLGroup);
        initWebappLifecycleListener(webXMLGroup);

        final Group facesConfigGroup = new Group(control, SWT.NONE);
        facesConfigGroup.setLayout(rowLayout);
        facesConfigGroup
                .setText("Application (faces-config.xml) Configuration");
        initViewHandlerButton(facesConfigGroup);

        setControl(control);
    }

    @Override
    public void transferStateToConfig()
    {

    }
}
