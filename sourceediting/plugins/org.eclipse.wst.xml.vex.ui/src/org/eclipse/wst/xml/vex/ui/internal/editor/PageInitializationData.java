package org.eclipse.wst.xml.vex.ui.internal.editor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.wst.xml.ui.internal.Logger;

public class PageInitializationData {
	IConfigurationElement fElement;
	String fPropertyName;
	Object fData;

	PageInitializationData(IConfigurationElement cfig, String propertyName, Object data) {
		super();
		fElement = cfig;
		fPropertyName = propertyName;
		fData = data;
	}
	void sendInitializationData(IExecutableExtension executableExtension) {
		try {
			executableExtension.setInitializationData(fElement, fPropertyName, fData);
		} catch (CoreException e) {
			Logger.logException(e);
		}
	}
}