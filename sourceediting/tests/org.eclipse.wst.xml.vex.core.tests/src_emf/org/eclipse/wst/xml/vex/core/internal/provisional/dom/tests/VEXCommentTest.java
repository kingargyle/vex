/**
 * <copyright>
 * </copyright>
 *
 * $Id: VEXCommentTest.java,v 1.1 2008/11/10 20:09:46 dacarver Exp $
 */
package org.eclipse.wst.xml.vex.core.internal.provisional.dom.tests;

import junit.textui.TestRunner;

import org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFFactory;

import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXComment;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>VEX Comment</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class VEXCommentTest extends VEXNodeTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(VEXCommentTest.class);
	}

	/**
	 * Constructs a new VEX Comment test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VEXCommentTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this VEX Comment test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected VEXComment getFixture() {
		return (VEXComment)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(DomEMFFactory.eINSTANCE.createVEXComment());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //VEXCommentTest
