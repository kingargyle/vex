/**
 * <copyright>
 * </copyright>
 *
 * $Id: VexdocumentAllTests.java,v 1.1 2008/11/10 20:09:46 dacarver Exp $
 */
package org.eclipse.wst.xml.vex.core.internal.provisional.dom.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>Vexdocument</b></em>' model.
 * <!-- end-user-doc -->
 * @generated
 */
public class VexdocumentAllTests extends TestSuite {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Test suite() {
		TestSuite suite = new VexdocumentAllTests("Vexdocument Tests");
		suite.addTest(DomEMFTests.suite());
		return suite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VexdocumentAllTests(String name) {
		super(name);
	}

} //VexdocumentAllTests
