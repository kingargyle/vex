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
package org.eclipse.jst.jsf.facelet.core.internal.cm.addtagmd.provider;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * A translated string provider.
 * 
 * @author cbateman
 * 
 */
public interface IResourceProvider
{
    /**
     * @param object
     * @param feature
     * @return the string value of object.eGet(feature)
     */
    String getTranslatedString(final EObject object,
            final EStructuralFeature feature);
}
