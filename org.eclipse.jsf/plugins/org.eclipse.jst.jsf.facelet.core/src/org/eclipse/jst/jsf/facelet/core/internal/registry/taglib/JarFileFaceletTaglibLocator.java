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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jst.jsf.facelet.core.internal.FaceletCorePlugin;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletTaglibDefn;
import org.xml.sax.SAXException;

/**
 * A locator that finds Facelet taglibs in jars on the classpath
 * 
 * @author cbateman
 * 
 */
/* package */class JarFileFaceletTaglibLocator extends
        AbstractFaceletTaglibLocator
{

    private final IProject                 _project;
    private final TagRecordFactory         _factory;
    private Map<String, IFaceletTagRecord> _records;

    public JarFileFaceletTaglibLocator(final IProject project,
            final TagRecordFactory factory)
    {
        _project = project;
        _factory = factory;
        _records = new HashMap<String, IFaceletTagRecord>();
    }

    @Override
    public Map<String, ? extends IFaceletTagRecord> locate()
    {
        try
        {
            return findInJars();
        }
        catch (JavaModelException e)
        {
            FaceletCorePlugin
                    .log(
                            "Couldn't locate jar file taglibs: " + _project.getProject(), e); //$NON-NLS-1$
        }
        return Collections.EMPTY_MAP;
    }

    private Map<String, ? extends IFaceletTagRecord> findInJars() throws JavaModelException
    {
        final IJavaProject javaProject = JavaCore.create(_project);

        final IClasspathEntry[] entries = javaProject
                .getResolvedClasspath(true);

        final List<FaceletTaglibDefn> tagLibsFound = new ArrayList<FaceletTaglibDefn>();

        for (final IClasspathEntry entry : entries)
        {

            switch (entry.getEntryKind())
            {
                // this entry describes a source root in its project
                case IClasspathEntry.CPE_SOURCE:

                break;
                // - this entry describes a folder or JAR containing
                // binaries
                case IClasspathEntry.CPE_LIBRARY:
                {
                    tagLibsFound.addAll(processJar(entry));
                }
                break;
                // - this entry describes another project
                case IClasspathEntry.CPE_PROJECT:
                    // {
                    // final IPath pathToProject = entry.getPath();
                    // IWorkspace wkspace = ResourcesPlugin.getWorkspace();
                    // IResource res =
                    // wkspace.getRoot().findMember(pathToProject);
                    // if (res instanceof IProject)
                    // {
                    // tagLibsFound.addAll();
                    // }
                    // }
                break;
                // - this entry describes a project or library indirectly
                // via a
                // classpath variable in the first segment of the path *
                case IClasspathEntry.CPE_VARIABLE:
                break;
                // - this entry describes set of entries referenced
                // indirectly
                // via a classpath container
                case IClasspathEntry.CPE_CONTAINER:
                break;
            }
        }
     
        for (final FaceletTaglibDefn tag :  tagLibsFound)
        {
            IFaceletTagRecord record = _factory.createRecords(tag);
            if (record != null)
            {
                _records.put(record.getURI(), record);
            }
        }
        
        return _records;
    }

    /**
     * TODO: Merge into JSFAppConfigUtils.
     * 
     * @param entry
     * @return
     */
    private static JarFile getJarFileFromCPE(final IClasspathEntry entry)
            throws IOException
    {
        if (entry.getEntryKind() == IClasspathEntry.CPE_LIBRARY)
        {
            IPath libraryPath = entry.getPath();
            if (libraryPath.getFileExtension() != null
                    && libraryPath.getFileExtension().length() > 0)
            {
                final IWorkspaceRoot workspaceRoot = ResourcesPlugin
                        .getWorkspace().getRoot();
                if (libraryPath.getDevice() == null
                        && workspaceRoot.getProject(libraryPath.segment(0))
                                .exists())
                {
                    libraryPath = workspaceRoot.getFile(libraryPath)
                            .getLocation();
                }
                final String libraryPathString = libraryPath.toString();
                return new JarFile(libraryPathString);
            }
        }
        return null;
    }

    /**
     * @param entry
     * @param defaultDtdStream
     */
    private List<FaceletTaglibDefn> processJar(final IClasspathEntry entry)
    {
        JarFile jarFile = null;
        final List<FaceletTaglibDefn> tagLibsFound = new ArrayList<FaceletTaglibDefn>();

        try
        {
            jarFile = getJarFileFromCPE(entry);

            if (jarFile != null)
            {
                final Enumeration<JarEntry> jarEntries = jarFile.entries();
                while (jarEntries.hasMoreElements())
                {
                    final JarEntry jarEntry = jarEntries.nextElement();
                    final String name = jarEntry.getName();

                    if (name.startsWith("META-INF/") //$NON-NLS-1$
                            && name.endsWith(".taglib.xml")) //$NON-NLS-1$
                    {
                        InputStream is = null;
                        try
                        {
                            is = jarFile.getInputStream(jarEntry);

                            FaceletTaglibDefn tagLib = TagModelParser.loadFromInputStream(is, null);

                            if (tagLib != null)
                            {
                                tagLibsFound.add(tagLib);
                            }
                        }
                        catch (final ParserConfigurationException e)
                        {
                            FaceletCorePlugin
                                    .log(
                                            "Error initializing facelet registry entry", //$NON-NLS-1$
                                            e);
                        }
                        catch (final IOException ioe)
                        {
                            FaceletCorePlugin
                                    .log(
                                            "Error initializing facelet registry entry", //$NON-NLS-1$
                                            ioe);
                        }
                        catch (final SAXException ioe)
                        {
                            FaceletCorePlugin
                                    .log(
                                            "Error initializing facelet registry entry", //$NON-NLS-1$
                                            ioe);
                        }
                        finally
                        {
                            if (is != null)
                            {
                                // is.close();
                            }
                        }
                    }
                }
            }
        }
        catch (final IOException e)
        {
            FaceletCorePlugin.log("Error opening classpath jar file", e); //$NON-NLS-1$
        }
        finally
        {
            if (jarFile != null)
            {
                try
                {
                    jarFile.close();
                }
                catch (final IOException ioe)
                {
                    FaceletCorePlugin.log("Error closing jar file", ioe); //$NON-NLS-1$
                }
            }
        }
        return tagLibsFound;
    }

    @Override
    public void startLocating()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void stopLocating()
    {
        // TODO Auto-generated method stub

    }

}
