package org.eclipse.wst.xml.vex.core.internal.validator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.wst.xml.core.internal.contentmodel.CMDocument;
import org.eclipse.wst.xml.core.internal.contentmodel.CMElementDeclaration;
import org.eclipse.wst.xml.core.internal.contentmodel.CMNode;
import org.eclipse.wst.xml.core.internal.contentmodel.internal.modelqueryimpl.ModelQueryExtensionManagerImpl;
import org.eclipse.wst.xml.core.internal.contentmodel.modelquery.ModelQuery;
import org.eclipse.wst.xml.core.internal.contentmodel.modelquery.extension.ModelQueryExtension;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class VEXModelQueryExtensionManagerImpl extends
		ModelQueryExtensionManagerImpl {

	public VEXModelQueryExtensionManagerImpl() {
		// TODO Auto-generated constructor stub
	}

	public void filterAvailableElementContent(List cmnodes, String element,
			CMElementDeclaration ed, int includeOptions) {
		String contentTypeId = getContentTypeId();
		// FIXME: Make VEX Element implement DOM so that we don't need to do this.
//		String parentNamespace = element.getNamespaceURI();

		List modelQueryExtensions = modelQueryExtensionRegistry
				.getApplicableExtensions(contentTypeId, null);
		if ((includeOptions & ModelQuery.INCLUDE_CHILD_NODES) > 0) {
			for (Iterator j = cmnodes.iterator(); j.hasNext();) {
				CMNode cmNode = (CMNode) j.next();
				String namespace = getNamespace(cmNode);
				String name = cmNode.getNodeName();

				boolean include = true;
				for (int k = 0; k < modelQueryExtensions.size() && include; k++) {
					{
						ModelQueryExtension extension = (ModelQueryExtension) modelQueryExtensions
								.get(k);
					//	include = extension.isApplicableChildElement(element,
					//			namespace, name);
						if (!include) {
							// remove the cmNode from the list
							j.remove();
						}
					}
				}
			}
		}
		// add MQE-provided content
		for (int k = 0; k < modelQueryExtensions.size(); k++) {
			ModelQueryExtension extension = (ModelQueryExtension) modelQueryExtensions
					.get(k);
			//cmnodes.addAll(Arrays.asList(extension.getAvailableElementContent(
			//		element, parentNamespace, includeOptions)));
		}
	}

	private String getNamespace(CMNode cmNode) {
		String namespace = null;
		CMDocument cmDocument = (CMDocument) cmNode.getProperty("CMDocument"); //$NON-NLS-1$
		if (cmDocument != null) {
			namespace = (String) cmDocument
					.getProperty("http://org.eclipse.wst/cm/properties/targetNamespaceURI"); //$NON-NLS-1$
		}
		return namespace;
	}

	private String getContentTypeId() {
		String contentTypeId = "org.eclipse.core.runtime.xml"; //$NON-NLS-1$
		return contentTypeId;
	}
}
