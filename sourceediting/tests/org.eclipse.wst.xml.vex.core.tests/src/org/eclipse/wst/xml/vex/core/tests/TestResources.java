/*******************************************************************************
 * Copyright (c) 2011 Florian Thienel and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 		Florian Thienel - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.tests;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Provides access to the resources under /testResources.
 * 
 * @author Florian Thienel
 */
public class TestResources {
	
	private static final String ROOT_DIRECTORY = "/testResources/";
	
	public static URL get(final String name) {
		return VEXCoreTestPlugin.getDefault().getBundle().getEntry(ROOT_DIRECTORY + name);
	}

	public static InputStream getAsStream(String name) throws IOException {
		final URL url = get(name);
		return url.openStream();
	}

}
