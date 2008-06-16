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
package org.eclipse.jst.jsf.facelet.core.internal.registry.taglib;

import org.eclipse.core.resources.IProject;
import org.eclipse.jst.jsf.common.internal.resource.ResourceSingletonObjectManager;

/**
 * @author cbateman
 *
 */
public class FaceletTagIndex extends ResourceSingletonObjectManager<IProjectTaglibDescriptor, IProject>
{
    private final static FaceletTagIndex INSTANCE = new FaceletTagIndex();

    /**
     * @return the singleton instance
     */
    public static FaceletTagIndex getInstance()
    {
        return INSTANCE;
    }

    @Override
    protected IProjectTaglibDescriptor createNewInstance(IProject project)
    {
        return new ProjectTaglibDescriptor(project);
    }
    
    /**
     * Force a project to refresh its tag index info
     * @param project
     */
    public void flush(final IProject project)
    {
        unmanageResource(project);
    }
}
