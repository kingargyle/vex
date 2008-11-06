package org.eclipse.wst.xml.vex.core.internal.validator;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

import org.eclipse.wst.xml.core.internal.contentmodel.CMDocument;
import org.eclipse.wst.xml.core.internal.contentmodel.ContentModelManager;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.IValidator;

public class WTPVEXValidator implements IValidator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7632029717211069257L;
	private static CMDocument contentModel;
	
	private WTPVEXValidator() {
		
	}
	
	public static WTPVEXValidator create(URL url) throws IOException {

		// Compute the DFAs for each element in the DTD
		
		ContentModelManager contentModelManager = ContentModelManager.getInstance();
		String resolved = url.toString();
		contentModel = contentModelManager.createCMDocument(resolved, null);

		return new WTPVEXValidator();
		
	}

	public AttributeDefinition getAttributeDefinition(String element,
			String attribute) {
		// TODO Auto-generated method stub
		return null;
	}

	public AttributeDefinition[] getAttributeDefinitions(String element) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set getValidItems(String element, String[] prefix, String[] suffix) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set getValidRootElements() {
		return null;
	}

	public boolean isValidSequence(String element, String[] nodes,
			boolean partial) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isValidSequence(String element, String[] seq1,
			String[] seq2, String[] seq3, boolean partial) {
		// TODO Auto-generated method stub
		return false;
	}

}
