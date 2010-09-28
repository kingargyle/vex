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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.xml.vex.ui.internal.config.ConfigLoaderJob;
import org.eclipse.wst.xml.vex.ui.internal.config.ConfigSource;
import org.eclipse.wst.xml.vex.ui.internal.config.PluginProject;
import org.eclipse.wst.xml.vex.ui.internal.config.PluginProjectNature;
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
		final List<ConfigSource> allConfigSources = job.getAllConfigSources();
		assertContainsConfiguration(allConfigSources, "org.eclipse.wst.xml.vex.ui.tests", "testdata/test.dtd", "testdata/test.css");
		assertContainsEachPluginOnlyOnce(allConfigSources);
	}

	@Test
	public void loadWorkspacePluginConfiguration() throws Exception {
		createVexPluginProject(name.getMethodName());
		final ConfigLoaderJob job = new ConfigLoaderJob();
		job.schedule();
		job.join();
		final List<ConfigSource> allConfigSources = job.getAllConfigSources();
		assertContainsConfiguration(allConfigSources, name.getMethodName(), "plugintest.dtd", "plugintest.css");
		assertContainsEachPluginOnlyOnce(allConfigSources);
	}

	private static IProject createVexPluginProject(final String name) throws CoreException {
		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		if (project.exists())
			project.delete(true, true, null);
		project.create(null);
		project.open(null);
		project.getFile("plugintest.dtd").create(new ByteArrayInputStream(new byte[0]), true, null);
		project.getFile("plugintest.css").create(new ByteArrayInputStream(new byte[0]), true, null);
		createVexPluginFile(project);
		addVexProjectNature(project);
		return project;
	}

	private static void createVexPluginFile(final IProject project) throws CoreException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintStream out = new PrintStream(baos);
		out.println("<?xml version='1.0'?>"); //$NON-NLS-1$
		// HINT: It is important to set the id attribute, because this is used as the unique identifier for the configuration. 
		out.println("<plugin id=\"" + project.getName() + "\">"); //$NON-NLS-1$
		out.println("<extension id=\"plugintest\" name=\"plugin test doctype\" point=\"org.eclipse.wst.xml.vex.ui.doctypes\">"); //$NON-NLS-1$
		out.println("<doctype systemId=\"plugintest.dtd\" dtd=\"plugintest.dtd\" publicId=\"-//Vex//Plugin Test//EN\" />"); //$NON-NLS-1$
		out.println("</extension>"); //$NON-NLS-1$
		out.println("<extension id=\"plugintest\" name=\"plugin test style\" point=\"org.eclipse.wst.xml.vex.ui.styles\">"); //$NON-NLS-1$
		out.println("<style css=\"plugintest.css\"><doctypeRef publicId=\"-//Vex//Plugin Test//EN\" /></style>"); //$NON-NLS-1$
		out.println("</extension>"); //$NON-NLS-1$
		out.println("</plugin>"); //$NON-NLS-1$
		out.close();
		project.getFile(PluginProject.PLUGIN_XML).create(new ByteArrayInputStream(baos.toByteArray()), true, null);
	}

	private static void addVexProjectNature(final IProject project) throws CoreException {
		final IProjectDescription description = project.getDescription();
		final String[] natures = description.getNatureIds();
		final String[] newNatures = new String[natures.length + 1];
		System.arraycopy(natures, 0, newNatures, 0, natures.length);
		newNatures[natures.length] = PluginProjectNature.ID;
		description.setNatureIds(newNatures);
		project.setDescription(description, null);
	}

	private static void assertContainsConfiguration(final List<ConfigSource> configSources, final String uniqueIdentifier, final String... configuredResources) {
		for (final ConfigSource configSource : configSources) {
			System.out.println(configSource.getUniqueIdentifer()); // TODO remove
			if (uniqueIdentifier.equals(configSource.getUniqueIdentifer())) {
				for (final String configuredResource : configuredResources)
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
