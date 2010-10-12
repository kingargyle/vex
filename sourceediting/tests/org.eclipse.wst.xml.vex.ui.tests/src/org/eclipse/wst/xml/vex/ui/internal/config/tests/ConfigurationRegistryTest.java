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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.eclipse.wst.xml.vex.ui.internal.config.ConfigEvent;
import org.eclipse.wst.xml.vex.ui.internal.config.ConfigSource;
import org.eclipse.wst.xml.vex.ui.internal.config.ConfigurationLoader;
import org.eclipse.wst.xml.vex.ui.internal.config.ConfigurationRegistry;
import org.eclipse.wst.xml.vex.ui.internal.config.ConfigurationRegistryImpl;
import org.eclipse.wst.xml.vex.ui.internal.config.IConfigListener;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * @author Florian Thienel
 */
@SuppressWarnings("restriction")
public class ConfigurationRegistryTest {

	private ConfigurationRegistry registry;

	@Rule
	public TestName name = new TestName();

	@After
	public void disposeRegistry() {
		if (registry != null)
			registry.dispose();
		registry = null;
	}
	
	@Test
	public void notAutomaticallyLoaded() throws Exception {
		registry = new ConfigurationRegistryImpl(new MockConfigurationLoader());
		assertFalse(registry.isLoaded());
	}

	@Test
	public void fireLoadedEventOnLoad() throws Exception {
		registry = new ConfigurationRegistryImpl(new MockConfigurationLoader());
		final MockConfigListener configListener = new MockConfigListener();
		registry.addConfigListener(configListener);
		registry.loadConfigurations();
		assertTrue(registry.isLoaded());
		assertTrue(configListener.loaded);
		assertFalse(configListener.changed);
	}
	
	@Test
	public void loadNewPluginProjectAndFireChangedEvent() throws Exception {
		registry = new ConfigurationRegistryImpl(new MockConfigurationLoader());
		final MockConfigListener configListener = new MockConfigListener();
		registry.addConfigListener(configListener);
		registry.loadConfigurations();
		configListener.reset();
		PluginProjectTest.createVexPluginProject(name.getMethodName());
		assertFalse(configListener.loaded);
		assertTrue(configListener.changed);
	}
	
	private static class MockConfigurationLoader implements ConfigurationLoader {
		private List<ConfigSource> loadedConfigSources;

		public MockConfigurationLoader() {
			this(Collections.<ConfigSource> emptyList());
		}
		
		public MockConfigurationLoader(List<ConfigSource> loadedConfigSources) {
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
	}
	
	private static class MockConfigSource extends ConfigSource {
		@Override
		public URL getBaseUrl() {
			try {
				return new URL("test:/test");
			} catch (MalformedURLException e) {
				throw new AssertionError(e);
			}
		}
	}
}
