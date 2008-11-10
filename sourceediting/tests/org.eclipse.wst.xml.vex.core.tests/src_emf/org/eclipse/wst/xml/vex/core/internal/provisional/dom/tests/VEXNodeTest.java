/**
 * <copyright>
 * </copyright>
 *
 * $Id: VEXNodeTest.java,v 1.1 2008/11/10 20:09:46 dacarver Exp $
 */
package org.eclipse.wst.xml.vex.core.internal.provisional.dom.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import org.eclipse.wst.xml.vex.core.internal.provisional.dom.DomEMFFactory;

import org.eclipse.wst.xml.vex.core.internal.provisional.dom.I.VEXNode;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>VEX Node</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class VEXNodeTest extends TestCase {

	/**
	 * The fixture for this VEX Node test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VEXNode fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(VEXNodeTest.class);
	}

	/**
	 * Constructs a new VEX Node test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VEXNodeTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this VEX Node test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(VEXNode fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this VEX Node test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VEXNode getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(DomEMFFactory.eINSTANCE.createVEXNode());
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

} //VEXNodeTest
