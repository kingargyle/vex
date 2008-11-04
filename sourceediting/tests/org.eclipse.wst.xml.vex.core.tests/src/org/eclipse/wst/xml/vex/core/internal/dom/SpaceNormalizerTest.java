/*******************************************************************************
 * Copyright (c) 2004, 2008 John Krasnay and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     John Krasnay - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.dom;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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
import org.eclipse.wst.xml.vex.core.internal.dom.DocumentBuilder;
import org.eclipse.wst.xml.vex.core.internal.dom.Element;
import org.eclipse.wst.xml.vex.core.internal.dom.Text;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IVEXDocument;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IVEXElement;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IVEXNode;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IWhitespacePolicy;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IWhitespacePolicyFactory;
import org.eclipse.wst.xml.vex.core.internal.widget.CssWhitespacePolicy;
import org.eclipse.wst.xml.vex.core.tests.VEXCoreTestPlugin;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * Test the SpaceNormalizer class.
 */
@SuppressWarnings("restriction")
public class SpaceNormalizerTest extends TestCase {

	protected static IProject fTestProject;
	private static boolean fTestProjectInitialized;
	private static final String TEST_PROJECT_NAME = "testproject";

	protected void setUp() throws Exception {
		super.setUp();
		DisplayDevice.setCurrent(new MockDisplayDevice(90, 90));

		if (!fTestProjectInitialized) {
			getAndCreateProject();

			Enumeration<String> e = Platform.getBundle(
					VEXCoreTestPlugin.PLUGIN_ID).getEntryPaths("/projectFiles");
			while (e.hasMoreElements()) {
				String path = e.nextElement();
				URL url = Platform.getBundle(VEXCoreTestPlugin.PLUGIN_ID)
						.getEntry(path);
				if (!url.getFile().endsWith("/")) {
					url = FileLocator.resolve(url);
					path = path.substring("projectfiles".length());
					IFile destFile = fTestProject.getFile(path);
					System.out.println(destFile.getLocation() + " --> "
							+ url.toExternalForm());
					destFile.createLink(url.toURI(), IResource.REPLACE,
							new NullProgressMonitor());
				}
			}
			fTestProject.refreshLocal(IResource.DEPTH_INFINITE, null);
			fTestProjectInitialized = true;
		}
	}

	protected IFile getFile(String path) {
		return fTestProject.getFile(new Path(path));
	}

	private static void getAndCreateProject() throws CoreException {
		IWorkspace workspace = getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		fTestProject = root.getProject(TEST_PROJECT_NAME);

		createProject(fTestProject, null, null);
		fTestProject.refreshLocal(IResource.DEPTH_INFINITE, null);
		assertTrue(fTestProject.exists());
	}

	private static void createProject(IProject project, IPath locationPath,
			IProgressMonitor monitor) throws CoreException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		monitor.beginTask("creating test project", 10);
		// create the project
		try {
			if (!project.exists()) {
				IProjectDescription desc = project.getWorkspace()
						.newProjectDescription(project.getName());
				if (Platform.getLocation().equals(locationPath)) {
					locationPath = null;
				}
				desc.setLocation(locationPath);
				project.create(desc, monitor);
				monitor = null;
			}
			if (!project.isOpen()) {
				project.open(monitor);
				monitor = null;
			}
		} finally {
			if (monitor != null) {
				monitor.done();
			}
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

		String input = "<doc>\n\t  "
				+ "<block>\n\t foo\n\t <inline>foo\n\t bar</inline>\n\t baz\n\t </block>\n\t "
				+ "<block>\n\t foo\n\t <block>bar</block>\n\t baz</block>"
				+ "<block>\n\t foo<inline> foo bar </inline>baz \n\t </block>"
				+ "<block>\n\t foo<block>bar</block>baz \n\t</block>"
				+ "\n\t </doc>";

		StyleSheet ss = getStyleSheet();

		IVEXDocument doc = createDocument(input, ss);
		IVEXElement element;

		element = doc.getRootElement();
		assertContent(element, new String[] { "<block>", "<block>", "<block>",
				"<block>" });

		List<IVEXElement> children = element.getChildElements();

		// --- Block 0 ---

		assertContent(children.get(0), new String[] { "foo ", "<inline>", " baz" });
		List<IVEXElement> c2 = children.get(0).getChildElements();
		assertContent(c2.get(0), new String[] { "foo bar" });

		// --- Block 1 ---

		assertContent(children.get(1), new String[] { "foo", "<block>", "baz" });
		c2 = children.get(1).getChildElements();
		assertContent(c2.get(0), new String[] { "bar" });

		// --- Block 2 ---

		assertContent(children.get(2), new String[] { "foo", "<inline>", "baz" });
		c2 = children.get(2).getChildElements();
		assertContent(c2.get(0), new String[] { "foo bar" });

		// --- Block 3 ---

		assertContent(children.get(3), new String[] { "foo", "<block>", "baz" });
		c2 = children.get(3).getChildElements();
		assertContent(c2.get(0), new String[] { "bar" });

	}

	public void testPreNormalize1() throws ParserConfigurationException,
			SAXException, IOException {
		// ========= Now test with a PRE element =========

		String input = "<doc>\n " + "<pre>\n foo\n</pre>\n " + "\n </doc>";

		IVEXDocument doc = createDocument(input, getStyleSheet());

		IVEXElement element = doc.getRootElement();
		assertContent(element, new String[] { "<pre>" });

		IVEXElement pre = element.getChildElements().get(0);
		assertContent(pre, new String[] { "\n foo\n" });
	}

	public void testPreNormalize2() throws Exception {
		// ========= Now test with a PRE element =========

		String input = "<doc>\n "
				+ "<pre>\n foo\n <inline>\n foo\n bar\n </inline></pre>\n "
				+ "\n </doc>";

		IVEXDocument doc = createDocument(input, getStyleSheet());

		IVEXElement element = doc.getRootElement();
		IVEXElement pre = element.getChildElements().get(0);
		IVEXElement inline = pre.getChildElements().get(0);
		assertContent(inline, new String[] { "\n foo\n bar\n " });
	}

	public void testPreElementNormalize() throws ParserConfigurationException,
			SAXException, IOException {
		// ========= Now test with a PRE element =========

		String input = "<doc>\n  "
				+ "<pre>\n\t foo\n\t <inline>\n\t foo\n\t bar\n\t </inline>\n\t baz\n\t </pre>\n "
				+ "\n </doc>";

		IVEXDocument doc = createDocument(input, getStyleSheet());

		IVEXElement element = doc.getRootElement();
		assertContent(element, new String[] { "<pre>" });

		IVEXElement pre = element.getChildElements().get(0);
		assertContent(pre,
				new String[] { "\n\t foo\n\t ", "<inline>", "\n\t baz\n\t " });

		IVEXElement inline = pre.getChildElements().get(0);
		assertContent(inline, new String[] { "\n\t foo\n\t bar\n\t " });
	}

	private StyleSheet getStyleSheet() throws IOException {
		StyleSheetReader reader = new StyleSheetReader();
		URL url = getFile("test.css").getLocationURI().toURL();
		StyleSheet ss = reader.read(url);
		return ss;
	}

	// ========================================================= PRIVATE

	// private static final String DTD = "<!ELEMENT doc ANY>";

	/**
	 * Asserts the content of the given element matches the given list. If a
	 * string in content is enclosed in angle brackets, it's assume to refer to
	 * the name of an element; otherwise, it represents text content.
	 */
	private void assertContent(IVEXElement element, String[] strings) {
		IVEXNode[] content = element.getChildNodes();
		assertEquals(strings.length, content.length);
		for (int i = 0; i < strings.length; i++) {
			if (strings[i].startsWith("<")) {
				String name = strings[i].substring(1, strings[i].length() - 1);
				assertTrue(content[i] instanceof Element);
				assertEquals(name, ((IVEXElement) content[i]).getName());
			} else {
				assertTrue(content[i] instanceof Text);
				String contentText = content[i].getText();
				assertEquals(strings[i], contentText);
			}
		}
	}

	private IVEXDocument createDocument(String s, StyleSheet ss)
			throws ParserConfigurationException, SAXException, IOException {

		SAXParserFactory factory = SAXParserFactory.newInstance();
		XMLReader xmlReader = factory.newSAXParser().getXMLReader();
		final StyleSheet mySS = ss;
		DocumentBuilder builder = new DocumentBuilder(
				new IWhitespacePolicyFactory() {

					public IWhitespacePolicy getPolicy(String publicId) {
						return new CssWhitespacePolicy(mySS);
					}

				});

		InputSource is = new InputSource(new ByteArrayInputStream(s.getBytes()));
		xmlReader.setContentHandler(builder);
		xmlReader.parse(is);
		return builder.getDocument();
	}

}
