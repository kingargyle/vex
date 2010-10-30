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

import java.io.IOException;
import java.net.URL;

import org.eclipse.wst.xml.vex.core.internal.css.StyleSheetReader;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;

/**
 * Factory for style objects.
 */
public class StyleFactory implements IConfigItemFactory {

	private static final String[] EXTS = new String[] { "css" }; //$NON-NLS-1$

	public IConfigElement[] createConfigurationElements(final ConfigItem item) {
		final Style style = (Style) item;
		final ConfigurationElement element = new ConfigurationElement("style"); //$NON-NLS-1$
		element.setAttribute("css", style.getResourcePath()); //$NON-NLS-1$
		for (final String publicId : style.getDocumentTypes()) {
			final ConfigurationElement child = new ConfigurationElement("doctypeRef"); //$NON-NLS-1$
			child.setAttribute("publicId", publicId); //$NON-NLS-1$
			element.addChild(child);
		}
		return new IConfigElement[] { element };
	}

	public ConfigItem createItem(final ConfigSource config, final IConfigElement[] configElements) throws IOException {

		if (configElements.length < 1)
			return null;
		final IConfigElement configElement = configElements[0];

		final Style style = new Style(config);
		style.setResourcePath(configElement.getAttribute("css")); //$NON-NLS-1$

		final IConfigElement[] doctypeRefs = configElement.getChildren();

		for (final IConfigElement doctypeRef : doctypeRefs)
			style.addDocumentType(doctypeRef.getAttribute("publicId")); //$NON-NLS-1$

		return style;
	}

	public String getExtensionPointId() {
		return Style.EXTENSION_POINT;
	}

	public String[] getFileExtensions() {
		return EXTS;
	}

	public String getPluralName() {
		return Messages.getString("StyleFactory.pluralName"); //$NON-NLS-1$
	}

	public Object parseResource(final URL baseUrl, final String resourcePath, final IBuildProblemHandler problemHandler) throws IOException {
		try {
			return new StyleSheetReader().read(new URL(baseUrl, resourcePath));
		} catch (final CSSParseException e) {
			if (problemHandler != null) {
				final BuildProblem problem = new BuildProblem();
				problem.setSeverity(BuildProblem.SEVERITY_ERROR);
				problem.setResourcePath(e.getURI());
				problem.setMessage(e.getMessage());
				problem.setLineNumber(e.getLineNumber());
				problemHandler.foundProblem(problem);
			}
			return null;
		} catch (final CSSException e) {
			if (problemHandler != null) {
				final BuildProblem problem = new BuildProblem();
				problem.setSeverity(BuildProblem.SEVERITY_ERROR);
				problem.setResourcePath(baseUrl.toString());
				problem.setMessage(e.getMessage());
				problemHandler.foundProblem(problem);
			}
			return null;
		}
	}
}
