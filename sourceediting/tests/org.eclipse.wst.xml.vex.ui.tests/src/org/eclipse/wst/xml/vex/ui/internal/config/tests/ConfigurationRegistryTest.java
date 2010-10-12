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

import java.util.Collections;
import java.util.List;

import org.eclipse.wst.xml.vex.ui.internal.config.ConfigEvent;
import org.eclipse.wst.xml.vex.ui.internal.config.ConfigSource;
import org.eclipse.wst.xml.vex.ui.internal.config.ConfigurationRegistry;
import org.eclipse.wst.xml.vex.ui.internal.config.ConfigurationRegistryImpl;
import org.eclipse.wst.xml.vex.ui.internal.config.IConfigListener;
import org.eclipse.wst.xml.vex.ui.internal.config.LoadConfiguration;
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
		registry = new ConfigurationRegistryImpl(new MockLoadConfiguration());
		assertFalse(registry.isLoaded());
	}

	private static class MockLoadConfiguration implements LoadConfiguration {
		private List<ConfigSource> loadedConfigSources;

		public MockLoadConfiguration() {
			this(Collections.<ConfigSource> emptyList());
		}
		
		public MockLoadConfiguration(List<ConfigSource> loadedConfigSources) {
			this.loadedConfigSources = loadedConfigSources;
		}
		
		public void load(Runnable whenDone) {
			whenDone.run();
		}

		public boolean isLoading() {
			return false;
		}

		public List<ConfigSource> getLoadedConfigSources() {
			return loadedConfigSources;
		}

		public void join() throws InterruptedException {
			return;
		}
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
