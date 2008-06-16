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
package org.eclipse.jst.jsf.facelet.core.internal.validation;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jst.jsf.core.jsfappconfig.JSFAppConfigUtils;
import org.eclipse.jst.jsf.facelet.core.internal.FaceletCorePlugin;
import org.eclipse.jst.jsf.facelet.core.internal.facet.FaceletFacet;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.TagModelParser;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.WebappConfiguration;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibDefn;
import org.eclipse.jst.jsp.core.internal.Logger;
import org.eclipse.wst.common.componentcore.resources.IVirtualFolder;
import org.eclipse.wst.validation.internal.core.ValidationException;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.wst.validation.internal.provisional.core.IValidationContext;
import org.eclipse.wst.validation.internal.provisional.core.IValidatorJob;
import org.xml.sax.SAXException;

/**
 * FUTURE USE: a build validator for source taglib definition files.
 * 
 * @author cbateman
 *
 */
public class TaglibValidator implements IValidatorJob
{

    public ISchedulingRule getSchedulingRule(IValidationContext helper)
    {
        // no scheduling rule
        return null;
    }

    public IStatus validateInJob(IValidationContext helper, IReporter reporter)
            throws ValidationException
    {
        IStatus status = Status.OK_STATUS;
        try
        {
            validate(helper, reporter);
        }
        catch (ValidationException e)
        {
            Logger.logException(e);
            status = new Status(IStatus.ERROR, FaceletCorePlugin.PLUGIN_ID,
                    IStatus.ERROR, e.getLocalizedMessage(), e);
        }
        return status;

    }

    public void cleanup(IReporter reporter)
    {
        // no cleanup
    }

    public void validate(IValidationContext helper, IReporter reporter)
            throws ValidationException
    {
        String[] uris = helper.getURIs();
        IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
        if (uris.length > 0)
        {
            IFile currentFile = null;

            for (int i = 0; i < uris.length && !reporter.isCancelled(); i++)
            {
                currentFile = wsRoot.getFile(new Path(uris[i]));
                if (currentFile != null && currentFile.exists())
                {
                    if (shouldValidate(currentFile))
                    {

                        validateFile(currentFile, reporter);
                    }
                }
            }
        }
    }

    private boolean shouldValidate(IFile currentFile)
    {
        final IVirtualFolder folder = JSFAppConfigUtils
            .getWebContentFolder(currentFile.getProject());
        final IPath filePath = currentFile.getProjectRelativePath();
        final IPath webFolderPath = folder.getUnderlyingFolder().getProjectRelativePath();
        boolean isInValidPath =  FaceletFacet.hasFacet(currentFile.getProject())
            && webFolderPath.isPrefixOf(filePath);
        
        if (isInValidPath)
        {
            for (final String configuredPath : WebappConfiguration.getConfigFilesFromContextParam(currentFile.getProject()))
            {
                final IPath path = webFolderPath.append(configuredPath);
                if (path.equals(filePath))
                {
                    return true;
                }
            }
        }
        return false;
    }

    private void validateFile(IFile file, IReporter reporter)
    {
        InputStream is = null;
        try
        {
            is = file.getContents();
            FaceletTaglibDefn taglib = TagModelParser.loadFromInputStream(is, null);
            if (taglib != null)
            {
                validate(taglib);
            }
        }
        catch (CoreException e)
        {
            FaceletCorePlugin.log("Validating taglib file: "+file.getName(), e); //$NON-NLS-1$
        }
        catch (IOException e)
        {
            FaceletCorePlugin.log("Validating taglib file: "+file.getName(), e); //$NON-NLS-1$
        }
        catch (ParserConfigurationException e)
        {
            FaceletCorePlugin.log("Validating taglib file: "+file.getName(), e); //$NON-NLS-1$
        }
        catch (SAXException e)
        {
            FaceletCorePlugin.log("Validating taglib file: "+file.getName(), e); //$NON-NLS-1$
        }
        finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    FaceletCorePlugin.log("Closing taglib file: "+file.getName(), e); //$NON-NLS-1$
                }
            }
        }
    }

    private void validate(FaceletTaglibDefn taglib)
    {
//        if (taglib instanceof FaceletXMLDefnTaglib)
//        {
//            
//        }
//        else if (taglib instanceof FaceletLibraryClassTagLib)
//        {
//            
//        }
    }
}
