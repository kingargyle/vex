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

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.wst.xml.vex.ui.internal.config.ConfigEvent;
import org.eclipse.wst.xml.vex.ui.internal.config.ConfigSource;
import org.eclipse.wst.xml.vex.ui.internal.config.ConfigurationLoader;
import org.eclipse.wst.xml.vex.ui.internal.config.ConfigurationRegistry;
import org.eclipse.wst.xml.vex.ui.internal.config.ConfigurationRegistryImpl;
import org.eclipse.wst.xml.vex.ui.internal.config.IConfigListener;
import org.eclipse.wst.xml.vex.ui.internal.config.PluginProject;
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

	@Test
	public void fireConfigChangedEventOnPluginModification() throws Exception {
		registry = new ConfigurationRegistryImpl(new MockConfigurationLoader());
		registry.loadConfigurations();
		final IProject project = PluginProjectTest.createVexPluginProject(name.getMethodName());
		final MockConfigListener configListener = new MockConfigListener();
		registry.addConfigListener(configListener);
		final String fileContent = PluginProjectTest.createVexPluginFileContent(project);
		project.getFile(PluginProject.PLUGIN_XML).setContents(new ByteArrayInputStream(fileContent.getBytes()), true, true, null);
		assertFalse(configListener.loaded);
		assertTrue(configListener.changed);
	}

	private static class MockConfigurationLoader implements ConfigurationLoader {
		private final List<ConfigSource> loadedConfigSources;

		public MockConfigurationLoader() {
			this(Collections.<ConfigSource> emptyList());
		}

		public MockConfigurationLoader(final List<ConfigSource> loadedConfigSources) {
			this.loadedConfigSources = loadedConfigSources;
		}

		public void load(final Runnable whenDone) {
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

		public void configChanged(final ConfigEvent e) {
			changed = true;
		}

		public void configLoaded(final ConfigEvent e) {
			loaded = true;
		}

		public void reset() {
			changed = false;
			loaded = false;
		}
	}
}
