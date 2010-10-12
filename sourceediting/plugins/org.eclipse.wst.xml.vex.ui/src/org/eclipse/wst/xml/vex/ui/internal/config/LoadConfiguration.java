/*******************************************************************************
 * Copyright (c) 2010 Florian Thienel and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 		Florian Thienel - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.ui.internal.config;

import java.util.List;

/**
 * @author Florian Thienel
 */
public interface LoadConfiguration {

	void load(Runnable whenDone);

	boolean isLoading();

	List<ConfigSource> getLoadedConfigSources();

	void join() throws InterruptedException;
}
