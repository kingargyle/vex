/*******************************************************************************
 * Copyright (c) 2004, 2008 John Krasnay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     John Krasnay - initial API and implementation
 *     Igor Jacy Lino Campista - Java 5 warnings fixed (bug 311325)
 *******************************************************************************/
package org.eclipse.wst.xml.vex.ui.internal.config;

import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.wst.xml.vex.ui.internal.VexPlugin;

/**
 * A regular Eclipse plugin that provides Vex configuration items.
 */
public class ConfigPlugin extends ConfigSource {

	private String bundleSymbolicName;
	
	protected ConfigPlugin(String bundleSymbolicName) {
		this.bundleSymbolicName = bundleSymbolicName;
		setUniqueIdentifer(bundleSymbolicName);
		load();
	}

	private void load() {
		removeAllItems();
		for (IExtension extension : Platform.getExtensionRegistry().getExtensions(bundleSymbolicName)) {
			try {
				addItem(extension.getExtensionPointUniqueIdentifier(),
						extension.getSimpleIdentifier(), extension.getLabel(),
						ConfigurationElementWrapper.convertArray(extension
								.getConfigurationElements()));
			} catch (IOException e) {
				String message = MessageFormat.format(Messages
						.getString("ConfigPlugin.loadError"), //$NON-NLS-1$
						new Object[] { extension.getSimpleIdentifier(), bundleSymbolicName });
				VexPlugin.getInstance().log(IStatus.ERROR, message, e);
				return;
			}
		}
		parseResources(null);
	}
	
	public URL getBaseUrl() {
		return Platform.getBundle(bundleSymbolicName).getEntry("plugin.xml"); //$NON-NLS-1$
	}

}
