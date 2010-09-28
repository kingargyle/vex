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
package org.eclipse.wst.xml.vex.ui.internal.config.tests;

import static org.junit.Assert.*;

import org.eclipse.wst.xml.vex.ui.internal.config.ConfigEvent;
import org.eclipse.wst.xml.vex.ui.internal.config.ConfigurationRegistry;
import org.eclipse.wst.xml.vex.ui.internal.config.ConfigurationRegistryImpl;
import org.eclipse.wst.xml.vex.ui.internal.config.IConfigListener;
import org.junit.After;
import org.junit.Test;

/**
 * @author Florian Thienel
 */
@SuppressWarnings("restriction")
public class ConfigurationRegistryTest {

	private ConfigurationRegistry registry;

	@After
	public void disposeRegistry() {
		if (registry != null)
			registry.dispose();
		registry = null;
	}
	
	@Test
	public void emptyOnCreate() throws Exception {
		registry = new ConfigurationRegistryImpl();
		assertFalse(registry.isLoaded());
	}

	@Test(expected = IllegalStateException.class)
	public void cannotGetConfigSourcesWhenNotYetLoaded() throws Exception {
		registry = new ConfigurationRegistryImpl();
		registry.getAllConfigSources().isEmpty();
	}

	@Test
	public void loadConfigurations() throws Exception {
		registry = new ConfigurationRegistryImpl();
		MockConfigListener listener = new MockConfigListener();
		registry.addConfigListener(listener);
		registry.loadConfigurations();
		assertFalse(listener.loaded);
		assertFalse(registry.getAllConfigSources().isEmpty());
		assertTrue(listener.loaded);
	}

	private static class MockConfigListener implements IConfigListener {
		public boolean changed = false;
		public boolean loaded = false;
		
		public void configChanged(ConfigEvent e) {
			changed = true;
		}

		public void configLoaded(ConfigEvent e) {
			loaded = true;
		}
		
		public void reset() {
			changed = false;
			loaded = false;
		}
	};
}
