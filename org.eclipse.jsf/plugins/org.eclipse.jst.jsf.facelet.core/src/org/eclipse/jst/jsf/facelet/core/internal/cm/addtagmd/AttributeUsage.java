/**
 * <copyright>
 * </copyright>
 *
 * $Id: AttributeUsage.java,v 1.1 2008/06/10 17:23:59 cbateman Exp $
 */
package org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Attribute Usage</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.AddTagMDPackage#getAttributeUsage()
 * @model
 * @generated
 */
public enum AttributeUsage implements Enumerator
{
    /**
     * The '<em><b>OPTIONAL</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #OPTIONAL_VALUE
     * @generated
     * @ordered
     */
    OPTIONAL(0, "OPTIONAL", "OPTIONAL"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>REQUIRED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #REQUIRED_VALUE
     * @generated
     * @ordered
     */
    REQUIRED(1, "REQUIRED", "REQUIRED"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>FIXED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #FIXED_VALUE
     * @generated
     * @ordered
     */
    FIXED(2, "FIXED", "FIXED"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>PROHIBITED</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #PROHIBITED_VALUE
     * @generated
     * @ordered
     */
    PROHIBITED(3, "PROHIBITED", "PROHIBITED"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>OPTIONAL</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>OPTIONAL</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #OPTIONAL
     * @model
     * @generated
     * @ordered
     */
    public static final int OPTIONAL_VALUE = 0;

    /**
     * The '<em><b>REQUIRED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>REQUIRED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #REQUIRED
     * @model
     * @generated
     * @ordered
     */
    public static final int REQUIRED_VALUE = 1;

    /**
     * The '<em><b>FIXED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>FIXED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #FIXED
     * @model
     * @generated
     * @ordered
     */
    public static final int FIXED_VALUE = 2;

    /**
     * The '<em><b>PROHIBITED</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>PROHIBITED</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #PROHIBITED
     * @model
     * @generated
     * @ordered
     */
    public static final int PROHIBITED_VALUE = 3;

    /**
     * An array of all the '<em><b>Attribute Usage</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final AttributeUsage[] VALUES_ARRAY =
        new AttributeUsage[]
        {
            OPTIONAL,
            REQUIRED,
            FIXED,
            PROHIBITED,
        };

    /**
     * A public read-only list of all the '<em><b>Attribute Usage</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<AttributeUsage> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Attribute Usage</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * @param literal 
     * @return the attribute usage
     * <!-- end-user-doc -->
     * @generated
     */
    public static AttributeUsage get(String literal)
    {
        for (int i = 0; i < VALUES_ARRAY.length; ++i)
        {
            AttributeUsage result = VALUES_ARRAY[i];
            if (result.toString().equals(literal))
            {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Attribute Usage</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * @param name 
     * @return the attribute usage
     * <!-- end-user-doc -->
     * @generated
     */
    public static AttributeUsage getByName(String name)
    {
        for (int i = 0; i < VALUES_ARRAY.length; ++i)
        {
            AttributeUsage result = VALUES_ARRAY[i];
            if (result.getName().equals(name))
            {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Attribute Usage</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * @param value 
     * @return the attribute usage
     * <!-- end-user-doc -->
     * @generated
     */
    public static AttributeUsage get(int value)
    {
        switch (value)
        {
            case OPTIONAL_VALUE: return OPTIONAL;
            case REQUIRED_VALUE: return REQUIRED;
            case FIXED_VALUE: return FIXED;
            case PROHIBITED_VALUE: return PROHIBITED;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final int value;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final String name;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final String literal;

    /**
     * Only this class can construct instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private AttributeUsage(int value, String name, String literal)
    {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getValue()
    {
      return value;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName()
    {
      return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLiteral()
    {
      return literal;
    }

    /**
     * Returns the literal value of the enumerator, which is its string representation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString()
    {
        return literal;
    }
    
} //AttributeUsage
