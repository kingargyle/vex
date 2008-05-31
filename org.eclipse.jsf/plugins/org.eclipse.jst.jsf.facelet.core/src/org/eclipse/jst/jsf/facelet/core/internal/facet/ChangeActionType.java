/*******************************************************************************
 * Copyright (c) 2008 Oracle Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Cameron Bateman - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.jsf.facelet.core.internal.facet;

/**
 * Indicates the type of change that should be made with the data in a
 * FacetChangeModel.
 * 
 * @author cbateman
 * 
 */
public enum ChangeActionType
{
    /**
     * Change is to add indicated values
     */
    ADD,

    /**
     * Change is to remove indicated values
     */
    REMOVE
}
