/*******************************************************************************
 * Copyright (c) 2004, 2008 John Krasnay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     John Krasnay - initial API and implementation
 *     Igor Jacy Lino Campista - Java 5 warnings fixed (bug 311325)
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.dom;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.wst.xml.vex.core.internal.core.DisplayDevice;
import org.eclipse.wst.xml.vex.core.internal.css.MockDisplayDevice;
import org.eclipse.wst.xml.vex.core.internal.css.StyleSheet;
import org.eclipse.wst.xml.vex.core.internal.css.StyleSheetReader;
import org.eclipse.wst.xml.vex.core.internal.widget.CssWhitespacePolicy;
import org.eclipse.wst.xml.vex.core.tests.VEXCoreTestPlugin;
import org.osgi.framework.Bundle;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class SpaceNormalizerTest extends TestCase {

	protected static IProject fTestProject;
	private static boolean fTestProjectInitialized;
	private static final String TEST_PROJECT_NAME = "testproject";
	private static final String PROJECT_FILES_FOLDER_NAME = "projectFiles";

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		DisplayDevice.setCurrent(new MockDisplayDevice(90, 90));
		if (fTestProjectInitialized)
			return;
		
		getAndCreateProject();
		final Bundle coreTestBundle = Platform.getBundle(VEXCoreTestPlugin.PLUGIN_ID);
		@SuppressWarnings("unchecked")
		final Enumeration<String> projectFilePaths = coreTestBundle.getEntryPaths("/" + PROJECT_FILES_FOLDER_NAME);
		while (projectFilePaths.hasMoreElements()) {
			final String absolutePath = projectFilePaths.nextElement();
			final URL url = coreTestBundle.getEntry(absolutePath);

			if (isFileUrl(url)) {
				final URL resolvedUrl = FileLocator.resolve(url);
				final String relativePath = absolutePath.substring(PROJECT_FILES_FOLDER_NAME.length());
				final IFile destFile = fTestProject.getFile(relativePath);
				System.out.println(destFile.getLocation() + " --> " + resolvedUrl.toExternalForm());
				if (isFromJarFile(resolvedUrl)) {
					copyTestFileToProject(coreTestBundle, absolutePath, destFile);
				} else
					//if resource is not compressed, link
					destFile.createLink(resolvedUrl.toURI(), IResource.REPLACE, new NullProgressMonitor());
			}
		}
		fTestProject.refreshLocal(IResource.DEPTH_INFINITE, null);
		fTestProjectInitialized = true;
	}

	private static boolean isFileUrl(URL url) {
		return !url.getFile().endsWith("/");
	}
	
	private boolean isFromJarFile(final URL resolvedUrl) {
		return resolvedUrl.toExternalForm().startsWith("jar:file");
	}
	
	private void copyTestFileToProject(final Bundle coreTestBundle, final String sourcePath, final IFile destinationFile) throws IOException, CoreException {
		final InputStream source = FileLocator.openStream(coreTestBundle, new Path(sourcePath), false);
		if (destinationFile.exists())
			destinationFile.delete(true, new NullProgressMonitor());
		destinationFile.create(source, true, new NullProgressMonitor());
	}

	protected IFile getFileInProject(final String path) {
		return fTestProject.getFile(new Path(path));
	}

	private static void getAndCreateProject() throws CoreException {
		final IWorkspace workspace = getWorkspace();
		final IWorkspaceRoot root = workspace.getRoot();
		fTestProject = root.getProject(TEST_PROJECT_NAME);

		createProject(fTestProject, null, null);
		fTestProject.refreshLocal(IResource.DEPTH_INFINITE, null);
		assertTrue(fTestProject.exists());
	}

	private static void createProject(final IProject project, IPath locationPath, IProgressMonitor monitor) throws CoreException {
		if (monitor == null)
			monitor = new NullProgressMonitor();
		monitor.beginTask("creating test project", 10);
		// create the project
		try {
			if (!project.exists()) {
				final IProjectDescription desc = project.getWorkspace().newProjectDescription(project.getName());
				if (Platform.getLocation().equals(locationPath))
					locationPath = null;
				desc.setLocation(locationPath);
				project.create(desc, monitor);
				monitor = null;
			}
			if (!project.isOpen()) {
				project.open(monitor);
				monitor = null;
			}
		} finally {
			if (monitor != null)
				monitor.done();
		}
	}

	/**
	 * Returns the workspace instance.
	 */
	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	/**
	 * Test the normalize method. Test cases are as follows.
	 * 
	 * <ul>
	 * <li>leading w/s trimmed</li>
	 * <li>trailing w/s trimmed</li>
	 * <li>internal w/s collapsed to a single space</li>
	 * <li>internal w/s before and after an inline child element collapsed to a
	 * single space.</li>
	 * <li>internal w/s before and after a block child element removed.</li>
	 * <li>spaces between blocks eliminated.</li>
	 * <li>no extraneous spaces before or after elements added</li>
	 * </ul>
	 */
	public void testNormalize() throws Exception {

		final String input = "<doc>\n\t  " + "<block>\n\t foo\n\t <inline>foo\n\t bar</inline>\n\t baz\n\t </block>\n\t "
				+ "<block>\n\t foo\n\t <block>bar</block>\n\t baz</block>" + "<block>\n\t foo<inline> foo bar </inline>baz \n\t </block>"
				+ "<block>\n\t foo<block>bar</block>baz \n\t</block>" + "\n\t </doc>";

		final StyleSheet ss = getStyleSheet();

		final Document doc = createDocument(input, ss);
		Element element;

		element = doc.getRootElement();
		assertContent(element, "<block>", "<block>", "<block>", "<block>");

		final List<Element> children = element.getChildElements();

		// --- Block 0 ---

		assertContent(children.get(0), "foo ", "<inline>", " baz");
		List<Element> c2 = children.get(0).getChildElements();
		assertContent(c2.get(0), "foo bar");

		// --- Block 1 ---

		assertContent(children.get(1), "foo", "<block>", "baz");
		c2 = children.get(1).getChildElements();
		assertContent(c2.get(0), "bar");

		// --- Block 2 ---

		assertContent(children.get(2), "foo", "<inline>", "baz");
		c2 = children.get(2).getChildElements();
		assertContent(c2.get(0), "foo bar");

		// --- Block 3 ---

		assertContent(children.get(3), "foo", "<block>", "baz");
		c2 = children.get(3).getChildElements();
		assertContent(c2.get(0), "bar");

	}

	public void testPreNormalize1() throws ParserConfigurationException, SAXException, IOException {
		// ========= Now test with a PRE element =========

		final String input = "<doc>\n " + "<pre>\n foo\n</pre>\n " + "\n </doc>";

		final Document doc = createDocument(input, getStyleSheet());

		final Element element = doc.getRootElement();
		assertContent(element, "<pre>");

		final Element pre = element.getChildElements().get(0);
		assertContent(pre, "\n foo\n");
	}

	public void testPreNormalize2() throws Exception {
		// ========= Now test with a PRE element =========

		final String input = "<doc>\n " + "<pre>\n foo\n <inline>\n foo\n bar\n </inline></pre>\n " + "\n </doc>";

		final Document doc = createDocument(input, getStyleSheet());

		final Element element = doc.getRootElement();
		final Element pre = element.getChildElements().get(0);
		final Element inline = pre.getChildElements().get(0);
		assertContent(inline, "\n foo\n bar\n ");
	}

	public void testPreElementNormalize() throws ParserConfigurationException, SAXException, IOException {
		// ========= Now test with a PRE element =========

		final String input = "<doc>\n  " + "<pre>\n\t foo\n\t <inline>\n\t foo\n\t bar\n\t </inline>\n\t baz\n\t </pre>\n " + "\n </doc>";

		final Document doc = createDocument(input, getStyleSheet());

		final Element element = doc.getRootElement();
		assertContent(element, "<pre>");

		final Element pre = element.getChildElements().get(0);
		assertContent(pre, "\n\t foo\n\t ", "<inline>", "\n\t baz\n\t ");

		final Element inline = pre.getChildElements().get(0);
		assertContent(inline, "\n\t foo\n\t bar\n\t ");
	}

	private StyleSheet getStyleSheet() throws IOException {
		final StyleSheetReader reader = new StyleSheetReader();
		final URL url = getFileInProject("test.css").getLocationURI().toURL();
		final StyleSheet ss = reader.read(url);
		return ss;
	}

	// ========================================================= PRIVATE

	// private static final String DTD = "<!ELEMENT doc ANY>";

	/**
	 * Asserts the content of the given element matches the given list. If a
	 * string in content is enclosed in angle brackets, it's assume to refer to
	 * the name of an element; otherwise, it represents text content.
	 */
	private void assertContent(final Element element, final String... strings) {
		final List<Node> content = element.getChildNodes();
		assertEquals(strings.length, content.size());
		for (int i = 0; i < strings.length; i++)
			if (strings[i].startsWith("<")) {
				final String name = strings[i].substring(1, strings[i].length() - 1);
				assertTrue(content.get(i) instanceof Element);
				assertEquals(name, ((Element) content.get(i)).getPrefixedName());
			} else {
				assertTrue(content.get(i) instanceof Text);
				final String contentText = content.get(i).getText();
				assertEquals(strings[i], contentText);
			}
	}

	private Document createDocument(final String s, final StyleSheet ss) throws ParserConfigurationException, SAXException, IOException {

		final SAXParserFactory factory = SAXParserFactory.newInstance();
		final XMLReader xmlReader = factory.newSAXParser().getXMLReader();
		final StyleSheet mySS = ss;
		final DocumentBuilder builder = new DocumentBuilder(new IWhitespacePolicyFactory() {

			public IWhitespacePolicy getPolicy(final String publicId) {
				return new CssWhitespacePolicy(mySS);
			}

		});

		final InputSource is = new InputSource(new ByteArrayInputStream(s.getBytes()));
		xmlReader.setContentHandler(builder);
		xmlReader.parse(is);
		return builder.getDocument();
	}

}
