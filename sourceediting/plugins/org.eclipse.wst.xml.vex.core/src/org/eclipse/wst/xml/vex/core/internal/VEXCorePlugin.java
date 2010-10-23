/*******************************************************************************
 * Copyright (c) 2004, 2008 John Krasnay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     John Krasnay - initial API and implementation
 *     David Carver (STAR) - initial renaming
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class VEXCorePlugin extends AbstractUIPlugin {

	public static final String ID = "org.eclipse.wst.xml.vex.core"; //$NON-NLS-1$

	private static VEXCorePlugin instance;

	public VEXCorePlugin() {
		if (instance != null)
			throw new IllegalStateException("This plug-in must be a singleton."); //$NON-NLS-1$
		instance = this;
	}

	public static VEXCorePlugin getInstance() {
		return instance;
	}

	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
	}

	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}
}
