package org.eclipse.wst.xml.vex.ui.internal.config.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.List;

import org.eclipse.wst.xml.vex.ui.internal.config.ConfigLoaderJob;
import org.eclipse.wst.xml.vex.ui.internal.config.ConfigRegistry;
import org.eclipse.wst.xml.vex.ui.internal.config.ConfigSource;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("restriction")
public class ConfigLoaderJobTest {

	@Before
	public void setUp() throws Exception {
		ConfigRegistry.getInstance().clear();
	}
	
	@Test
	public void loadUiTestsPlugin() throws Exception {
		assertTrue(ConfigRegistry.getInstance().getAllConfigSources().isEmpty());
		final ConfigLoaderJob job = new ConfigLoaderJob();
		job.schedule();
		job.join();
		List<ConfigSource> allConfigSources = ConfigRegistry.getInstance().getAllConfigSources();
		assertContainsTestsPlugin(allConfigSources);
		assertContainsEachPluginOnlyOnce(allConfigSources);
	}
	
	private static void assertContainsTestsPlugin(final List<ConfigSource> configSources) {
		for (ConfigSource configSource : configSources)
			if ("org.eclipse.wst.xml.vex.ui.tests".equals(configSource.getUniqueIdentifer())) {
				assertNotNull(configSource.getItemForResource("testdata/test.dtd"));
				assertNotNull(configSource.getItemForResource("testdata/test.css"));
				return;
			}
		fail("Cannot find extension from plug-in org.eclipse.wst.xml.vex.ui.tests.");
	}
	
	private static void assertContainsEachPluginOnlyOnce(final List<ConfigSource> configSources) {
		final HashSet<String> configSourceIds = new HashSet<String>();
		for (ConfigSource configSource : configSources)
			assertTrue("ConfigRegistry contains " + configSource.getUniqueIdentifer() + " twice.", configSourceIds.add(configSource.getUniqueIdentifer()));
	}

}
