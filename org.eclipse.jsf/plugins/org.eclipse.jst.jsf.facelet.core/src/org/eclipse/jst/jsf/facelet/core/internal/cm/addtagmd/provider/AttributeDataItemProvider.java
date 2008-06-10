/**
 * <copyright>
 * </copyright>
 *
 * $Id: AttributeDataItemProvider.java,v 1.1 2008/06/10 17:24:05 cbateman Exp $
 */
package org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.eclipse.jst.jsf.common.metadata.Trait;
import org.eclipse.jst.jsf.common.metadata.internal.TraitValueHelper;
import org.eclipse.jst.jsf.facelet.core.internal.FaceletCorePlugin;

import org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AddTagMDPackage;
import org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeData;

/**
 * This is the item provider adapter for a {@link org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AttributeData} object.
 * <!-- begin-user-doc -->
 * @extends IResourceProvider
 * <!-- end-user-doc -->
 * @generated
 */
public class AttributeDataItemProvider
    extends ItemProviderAdapter
    implements
        IEditingDomainItemProvider,
        IStructuredItemContentProvider,
        ITreeItemContentProvider,
        IItemLabelProvider,
        IItemPropertySource,
        IResourceProvider
{
    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
     * @param adapterFactory 
     * <!-- end-user-doc -->
     * @generated
     */
    public AttributeDataItemProvider(AdapterFactory adapterFactory)
    {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object)
    {
        if (itemPropertyDescriptors == null)
        {
            super.getPropertyDescriptors(object);

            addNamePropertyDescriptor(object);
            addUsagePropertyDescriptor(object);
            addDescriptionPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Name feature.
     * <!-- begin-user-doc -->
     * @param object 
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addNamePropertyDescriptor(Object object)
    {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_AttributeData_name_feature"), //$NON-NLS-1$
                 getString("_UI_PropertyDescriptor_description", "_UI_AttributeData_name_feature", "_UI_AttributeData_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                 AddTagMDPackage.Literals.ATTRIBUTE_DATA__NAME,
                 true,
                 false,
                 false,
                 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                 null,
                 null));
    }

    /**
     * This adds a property descriptor for the Usage feature.
     * <!-- begin-user-doc -->
     * @param object 
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addUsagePropertyDescriptor(Object object)
    {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_AttributeData_usage_feature"), //$NON-NLS-1$
                 getString("_UI_PropertyDescriptor_description", "_UI_AttributeData_usage_feature", "_UI_AttributeData_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                 AddTagMDPackage.Literals.ATTRIBUTE_DATA__USAGE,
                 true,
                 false,
                 false,
                 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                 null,
                 null));
    }

    /**
     * This adds a property descriptor for the Description feature.
     * <!-- begin-user-doc -->
     * @param object 
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addDescriptionPropertyDescriptor(Object object)
    {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_AttributeData_description_feature"), //$NON-NLS-1$
                 getString("_UI_PropertyDescriptor_description", "_UI_AttributeData_description_feature", "_UI_AttributeData_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                 AddTagMDPackage.Literals.ATTRIBUTE_DATA__DESCRIPTION,
                 true,
                 false,
                 false,
                 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                 null,
                 null));
    }

    /**
     * This returns AttributeData.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object)
    {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/AttributeData")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object)
    {
        String label = ((AttributeData)object).getName();
        return label == null || label.length() == 0 ?
            getString("_UI_AttributeData_type") : //$NON-NLS-1$
            getString("_UI_AttributeData_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to update any cached
     * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void notifyChanged(Notification notification)
    {
        updateChildren(notification);

        switch (notification.getFeatureID(AttributeData.class))
        {
            case AddTagMDPackage.ATTRIBUTE_DATA__NAME:
            case AddTagMDPackage.ATTRIBUTE_DATA__USAGE:
            case AddTagMDPackage.ATTRIBUTE_DATA__DESCRIPTION:
                fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
     * that can be created under this object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object)
    {
        super.collectNewChildDescriptors(newChildDescriptors, object);
    }

    /**
     * Return the resource locator for this item provider's resources.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator()
    {
        return FaceletCorePlugin.INSTANCE;
    }
    
    public String getTranslatedString(EObject object, EStructuralFeature feature)
    {
        Object value = object.eGet(feature);
        
        if (value != null)
        {
            EObject  testObject = object;
            
            while (! (testObject instanceof Trait) 
                    && testObject != null)
            {
                testObject = testObject.eContainer();
            }
            
            if (testObject instanceof Trait)
            {
                Trait trait = (Trait) testObject;
                value = TraitValueHelper.getNLSValue(trait, value.toString());
            }
            return value.toString();
        }

        return object.toString();
    }
}
