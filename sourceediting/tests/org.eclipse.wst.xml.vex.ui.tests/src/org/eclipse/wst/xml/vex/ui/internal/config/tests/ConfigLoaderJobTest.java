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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.wst.xml.vex.ui.internal.config.ConfigLoaderJob;
import org.eclipse.wst.xml.vex.ui.internal.config.ConfigSource;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * @author Florian Thienel
 */
@SuppressWarnings("restriction")
public class ConfigLoaderJobTest {

	@Rule
	public TestName name = new TestName();

	@Test
	public void loadUiTestsPluginConfiguration() throws Exception {
		final ConfigLoaderJob job = new ConfigLoaderJob();
		job.schedule();
		job.join();
		final List<ConfigSource> allConfigSources = job.getLoadedConfigSources();
		assertContainsConfiguration(allConfigSources, "org.eclipse.wst.xml.vex.ui.tests", "test-doctype", "test-style");
		assertContainsEachPluginOnlyOnce(allConfigSources);
	}

	@Test
	public void loadWorkspacePluginConfiguration() throws Exception {
		IProject pluginProject = PluginProjectTest.createVexPluginProject(name.getMethodName());
		final ConfigLoaderJob job = new ConfigLoaderJob();
		job.schedule();
		job.join();
		final List<ConfigSource> allConfigSources = job.getLoadedConfigSources();
		assertContainsConfiguration(allConfigSources, name.getMethodName(), pluginProject.getFile("plugintest.dtd"), pluginProject.getFile("plugintest.css"));
		assertContainsEachPluginOnlyOnce(allConfigSources);
	}
	
	@Test
	public void runRunnableWhenDone() throws Exception {
		final boolean[] runnableRun = new boolean[2];
		runnableRun[0] = false;
		runnableRun[1] = false;
		final ConfigLoaderJob job = new ConfigLoaderJob();
		job.load(new Runnable() {
			public void run() {
				runnableRun[0] = true;
			}
		});
		job.join();
		assertTrue(runnableRun[0]);
		assertFalse(runnableRun[1]);
		
		runnableRun[0] = false;
		runnableRun[1] = false;
		job.load(new Runnable() {
			public void run() {
				runnableRun[1] = true;
			}
		});
		job.join();
		assertFalse(runnableRun[0]);
		assertTrue(runnableRun[1]);
	}

	private static void assertContainsConfiguration(final List<ConfigSource> configSources, final String uniqueIdentifier, final String... simpleIds) {
		for (final ConfigSource configSource : configSources) {
			if (uniqueIdentifier.equals(configSource.getUniqueIdentifer())) {
				for (final String simpleId : simpleIds)
					assertNotNull(simpleId + " is not configured", configSource.getItem(simpleId));
				return;
			}
		}
		fail("Cannot find configuration " + uniqueIdentifier);
	}
	
	private static void assertContainsConfiguration(final List<ConfigSource> configSources, final String uniqueIdentifier, final IResource... configuredResources) {
		for (final ConfigSource configSource : configSources) {
			if (uniqueIdentifier.equals(configSource.getUniqueIdentifer())) {
				for (final IResource configuredResource : configuredResources)
					assertNotNull(configuredResource + " is not configured", configSource.getItemForResource(configuredResource));
				return;
			}
		}
		fail("Cannot find configuration " + uniqueIdentifier);
	}

	private static void assertContainsEachPluginOnlyOnce(final List<ConfigSource> configSources) {
		final HashSet<String> configSourceIds = new HashSet<String>();
		for (final ConfigSource configSource : configSources)
			assertTrue("ConfigRegistry contains " + configSource.getUniqueIdentifer() + " twice.", configSourceIds.add(configSource.getUniqueIdentifer()));
	}

}
