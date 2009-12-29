/*******************************************************************************
 * Copyright (c) 2009 Holger Voormann and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Holger Voormann - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.ui.internal;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * This enumeration contains the icons used by this plug-in that are very
 * frequently used and so need to be globally shared within the plug-in. The
 * icons are lazy loaded and disposed when the plug-in/bundle is stopped.
 */
public enum Icon {

    /** 16x16 icon: XML element (resource). */
    ELEMENT("icons/element_obj.gif"), //$NON-NLS-1$

    /** 16x16 icon: Convert (action). */
    CONVERT("icons/convert.gif"); //$NON-NLS-1$

    private final String iconFilePath;

    private Icon(String iconFilePath) {
        this.iconFilePath = iconFilePath;
    }

    /**
     * @param icon the icon to be returned as {@link Image}
     * @return the specified icon as {@link Image}
     */
    public static Image get(Icon icon) {
        ImageRegistry registry = VexPlugin.getInstance().getImageRegistry();
        Image value = registry.get(icon.iconFilePath);
        if (value == null) {
            ImageDescriptor imageDescriptor =
                createImageDescriptor(icon.iconFilePath);
            registry.put(icon.iconFilePath, imageDescriptor);
            value = registry.get(icon.iconFilePath);
        }
        return value;
    }

    private static ImageDescriptor createImageDescriptor(String filePath) {
        ImageDescriptor descriptor =
            AbstractUIPlugin.imageDescriptorFromPlugin(VexPlugin.ID, filePath);

        Assert.isNotNull(descriptor,
                         "Image file not found: " + filePath); //$NON-NLS-1$
        return descriptor;
    }

}
