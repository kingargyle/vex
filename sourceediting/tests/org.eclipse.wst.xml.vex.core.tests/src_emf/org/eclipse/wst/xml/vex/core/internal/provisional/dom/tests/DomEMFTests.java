/*******************************************************************************
 *Copyright (c) 2008 Standards for Technology in Automotive Retail and others.
 *All rights reserved. This program and the accompanying materials
 *are made available under the terms of the Eclipse Public License v1.0
 *which accompanies this distribution, and is available at
 *http://www.eclipse.org/legal/epl-v10.html
 *
 *Contributors:
 *    David Carver (STAR)  - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.vex.core.internal.provisional.dom.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>dom</b></em>' package.
 * <!-- end-user-doc -->
 * @generated
 */
public class DomEMFTests extends TestSuite {

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
		TestSuite suite = new DomEMFTests("dom Tests");
		suite.addTestSuite(ContentTest.class);
		suite.addTestSuite(VEXAttributeTest.class);
		suite.addTestSuite(VEXDocumentTest.class);
		suite.addTestSuite(VEXElementTest.class);
		suite.addTestSuite(VEXProcessingInstructionTest.class);
		suite.addTestSuite(ValidatorTest.class);
		return suite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomEMFTests(String name) {
		super(name);
	}

} //DomEMFTests
