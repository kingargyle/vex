package org.eclipse.wst.xml.vex.core.internal.provisional.dom.util;

import org.eclipse.wst.common.internal.emf.resource.RootTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage;


public class VEXModelTranslator extends RootTranslator {

	private static Translator[] children;
	
	public VEXModelTranslator(String domNameAndPath) {
		super(domNameAndPath, DomEMFPackage.eINSTANCE.getVEXDocument());
	}
	

}