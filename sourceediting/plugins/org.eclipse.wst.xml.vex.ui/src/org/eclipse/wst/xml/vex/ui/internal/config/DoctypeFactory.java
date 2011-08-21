/*******************************************************************************
 * Copyright (c) 2004, 2010 John Krasnay and others.
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

import org.eclipse.wst.xml.vex.core.internal.validator.WTPVEXValidator;

/**
 * Factory for DocumentType objects.
 */
public class DoctypeFactory implements IConfigItemFactory {

	private static final String[] EXTS = new String[] { "dtd" }; //$NON-NLS-1$

	private static final String ELT_DOCTYPE = "doctype"; //$NON-NLS-1$
	private static final String ATTR_OUTLINE_PROVIDER = "outlineProvider"; //$NON-NLS-1$
	private static final String ATTR_SYSTEM_ID = "systemId"; //$NON-NLS-1$
	private static final String ATTR_PUBLIC_ID = "publicId"; //$NON-NLS-1$

	private static final String ELT_ROOT_ELEMENT = "rootElement"; //$NON-NLS-1$
	private static final String ATTR_NAME = "name"; //$NON-NLS-1$

	public IConfigElement[] createConfigurationElements(final ConfigItem item) {
		final DocumentType doctype = (DocumentType) item;
		final ConfigurationElement doctypeElement = new ConfigurationElement(ELT_DOCTYPE);
		doctypeElement.setAttribute(ATTR_PUBLIC_ID, doctype.getPublicId());
		doctypeElement.setAttribute(ATTR_SYSTEM_ID, doctype.getSystemId());
		doctypeElement.setAttribute(ATTR_OUTLINE_PROVIDER, doctype.getOutlineProvider());

		for (final String name : doctype.getRootElements()) {
			final ConfigurationElement rootElement = new ConfigurationElement(ELT_ROOT_ELEMENT);
			rootElement.setAttribute(ATTR_NAME, name);
			doctypeElement.addChild(rootElement);
		}

		return new IConfigElement[] { doctypeElement };
	}

	public ConfigItem createItem(final ConfigSource config, final IConfigElement[] configElements) throws IOException {
		if (configElements.length < 1)
			return null;
		final IConfigElement configElement = configElements[0];
		final String publicId = configElement.getAttribute(ATTR_PUBLIC_ID);
		final String systemId = configElement.getAttribute(ATTR_SYSTEM_ID);
		final DocumentType doctype = new DocumentType(config);
		doctype.setPublicId(publicId);
		doctype.setSystemId(systemId);
		doctype.setResourcePath(config.resolve(publicId, systemId));
		doctype.setOutlineProvider(configElement.getAttribute(ATTR_OUTLINE_PROVIDER));

		final IConfigElement[] rootElementRefs = configElement.getChildren();
		final String[] rootElements = new String[rootElementRefs.length];
		for (int i = 0; i < rootElementRefs.length; i++)
			rootElements[i] = rootElementRefs[i].getAttribute("name"); //$NON-NLS-1$
		doctype.setRootElements(rootElements);

		return doctype;
	}
	
	public String getExtensionPointId() {
		return DocumentType.EXTENSION_POINT;
	}

	public String[] getFileExtensions() {
		return EXTS;
	}

	public String getPluralName() {
		return Messages.getString("DoctypeFactory.pluralName"); //$NON-NLS-1$
	}

	public Object parseResource(final URL baseUrl, final String resourcePath, final IBuildProblemHandler problemHandler) throws IOException {
		try {
			return new WTPVEXValidator(new URL(baseUrl, resourcePath));
		} catch (final IOException ex) {
			if (problemHandler != null) {
				final BuildProblem problem = new BuildProblem();
				problem.setSeverity(BuildProblem.SEVERITY_ERROR);
				problem.setResourcePath(resourcePath);
				problem.setMessage(ex.getMessage());
				//problem.setLineNumber(ex.getLineNumber());
				problemHandler.foundProblem(problem);
			}
			return null;
		}
	}
}
