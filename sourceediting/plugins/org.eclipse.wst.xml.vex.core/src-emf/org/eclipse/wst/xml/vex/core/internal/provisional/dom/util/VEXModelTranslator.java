package org.eclipse.wst.xml.vex.core.internal.provisional.dom.util;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.wst.common.internal.emf.resource.GenericTranslator;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.RootTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;
import org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFPackage;


public class VEXModelTranslator extends RootTranslator {


	private static Translator[] children;
	private static final DomEMFPackage VEX_PKG = DomEMFPackage.eINSTANCE;

	
	public VEXModelTranslator(String domNameAndPath) {
		super(domNameAndPath, VEX_PKG.eINSTANCE.getVEXDocument());
	}
	

}