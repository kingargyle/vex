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
package org.eclipse.wst.xml.vex.ui.internal.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.wst.xml.vex.core.internal.dom.DocumentWriter;
import org.eclipse.wst.xml.vex.ui.internal.VexPlugin;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Represents a Vex plugin project in the workspace.
 */
public class PluginProject extends ConfigSource {

	private static final long serialVersionUID = 1L;

	public static final String PLUGIN_XML = "vex-plugin.xml"; //$NON-NLS-1$

	/**
	 * Class constructor.
	 * 
	 * @param config
	 *            VexConfiguration associated with this project.
	 */
	protected PluginProject(final IProject project) {
		projectPath = project.getFullPath().toString();
	}

	@Override
	public URL getBaseUrl() {
		try {
			return getProject().getLocation().toFile().toURI().toURL();
		} catch (final MalformedURLException e) {
			throw new RuntimeException(Messages.getString("PluginProject.malformedUrl"), e); //$NON-NLS-1$
		}
	}

	/**
	 * Returns the IProject associated with this plugin project.
	 */
	public IProject getProject() {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(projectPath);
	}

	/**
	 * Loads the project from it's serialized state file and registers it with
	 * the ConfigRegistry. If the serialized state cannot be loaded, a new
	 * PluginProject is created and the builder is launched.
	 */
	public static PluginProject load(final IProject project) {
		try {
			if (!project.isOpen() || !project.hasNature(PluginProjectNature.ID))
				throw new IllegalArgumentException(MessageFormat.format(Messages.getString("PluginProject.notPluginProject"), //$NON-NLS-1$
						project.getName()));
		} catch (final CoreException e) {
			throw new IllegalArgumentException(MessageFormat.format(Messages.getString("PluginProject.notPluginProject"), //$NON-NLS-1$
					project.getName()));
		}

		final PluginProject pluginProject = new PluginProject(project);
		try {
			pluginProject.parseConfigXml();
		} catch (final SAXException e) {
			throw new IllegalArgumentException(MessageFormat.format(Messages.getString("PluginProject.loadingError"), //$NON-NLS-1$
					project.getName()));
		} catch (final IOException e) {
			throw new IllegalArgumentException(MessageFormat.format(Messages.getString("PluginProject.loadingError"), //$NON-NLS-1$
					project.getName()));
		}

		try {
			project.build(IncrementalProjectBuilder.FULL_BUILD, null);
		} catch (final Exception ex) {
			final String message = MessageFormat.format(Messages.getString("PluginProject.buildError"), //$NON-NLS-1$
					new Object[] { project.getName() });
			VexPlugin.getInstance().log(IStatus.ERROR, message, ex);
		}

		return pluginProject;
	}

	/**
	 * Re-parses the vex-plugin.xml file.
	 */
	public void parseConfigXml() throws SAXException, IOException {
		final DocumentBuilder builder;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (final ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (final FactoryConfigurationError e) {
			throw new RuntimeException(e);
		}

		removeAllItems();

		final URL url = new URL(getBaseUrl(), PluginProject.PLUGIN_XML);
		final Document doc = builder.parse(url.toString());

		final Element root = doc.getDocumentElement();

		setUniqueIdentifer(root.getAttribute("id")); //$NON-NLS-1$

		final NodeList nodeList = doc.getElementsByTagName("extension"); //$NON-NLS-1$

		for (int i = 0; i < nodeList.getLength(); i++) {
			final Element element = (Element) nodeList.item(i);
			final String extPoint = element.getAttribute("point"); //$NON-NLS-1$
			final String id = element.getAttribute("id"); //$NON-NLS-1$
			final String name = element.getAttribute("name"); //$NON-NLS-1$

			final List<Node> configElementList = new ArrayList<Node>();
			final NodeList childList = element.getChildNodes();
			for (int j = 0; j < childList.getLength(); j++) {
				final Node child = childList.item(j);
				if (child instanceof Element)
					configElementList.add(child);
			}

			final IConfigElement[] configElements = new IConfigElement[configElementList.size()];
			for (int j = 0; j < configElementList.size(); j++)
				configElements[j] = new DomConfigurationElement((Element) configElementList.get(j));

			this.addItem(extPoint, id, name, configElements);
		}

	}

	/**
	 * Writes this configuraton to the file vex-config.xml in the project.
	 */
	public void writeConfigXml() throws CoreException, IOException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintWriter out = new PrintWriter(new OutputStreamWriter(baos, "utf-8")); //$NON-NLS-1$

		final ConfigurationElement root = new ConfigurationElement("plugin"); //$NON-NLS-1$
		for (final ConfigItem item : this.getAllItems()) {
			final ConfigurationElement extElement = new ConfigurationElement("extension"); //$NON-NLS-1$
			extElement.setAttribute("id", item.getSimpleId()); //$NON-NLS-1$
			extElement.setAttribute("name", item.getName()); //$NON-NLS-1$
			extElement.setAttribute("point", item.getExtensionPointId()); //$NON-NLS-1$
			final IConfigItemFactory factory = getConfigItemFactory(item.getExtensionPointId());
			if (factory != null) {
				extElement.setChildren(factory.createConfigurationElements(item));
				root.addChild(extElement);
			}
		}
		writeElement(root, out, 0);

		out.close();

		final InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());

		final IFile file = getProject().getFile(PLUGIN_XML);
		if (file.exists())
			file.setContents(inputStream, true, false, null);
		else
			file.create(inputStream, true, null);
	}

	// =========================================================== PRIVATE

	private final String projectPath;

	private static void writeElement(final IConfigElement element, final PrintWriter out, final int level) {
		final StringBuffer elementIndent = new StringBuffer();
		for (int i = 0; i < level; i++)
			elementIndent.append("  "); //$NON-NLS-1$
		final StringBuffer elementPrefix = new StringBuffer();
		elementPrefix.append("<"); //$NON-NLS-1$
		elementPrefix.append(element.getName());

		final StringBuffer attributeIndent = new StringBuffer(elementIndent.toString());
		for (int i = 0; i < elementPrefix.length() + 1; i++)
			attributeIndent.append(" "); //$NON-NLS-1$

		out.print(elementIndent.toString());
		out.print(elementPrefix.toString());
		final String[] attributeNames = element.getAttributeNames();
		for (int i = 0; i < attributeNames.length; i++) {
			final String attributeName = attributeNames[i];
			if (i > 0) {
				out.println();
				out.print(attributeIndent);
			} else
				out.print(" "); //$NON-NLS-1$

			out.print(attributeName);
			out.print("=\""); //$NON-NLS-1$
			out.print(DocumentWriter.escape(element.getAttribute(attributeName)));
			out.print("\""); //$NON-NLS-1$
		}
		out.println(">"); //$NON-NLS-1$

		final IConfigElement[] children = element.getChildren();
		for (final IConfigElement element2 : children)
			writeElement(element2, out, level + 1);

		out.print(elementIndent);
		out.print("</"); //$NON-NLS-1$
		out.print(element.getName());
		out.println(">"); //$NON-NLS-1$
	}

}
