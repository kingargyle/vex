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
package org.eclipse.jst.jsf.facelet.core.internal.cm.attributevalues;

import java.util.Collections;
import java.util.List;

import org.eclipse.jst.jsf.metadataprocessors.features.IPossibleValues;
import org.eclipse.jst.jsf.taglibprocessing.attributevalues.WebPathType;

/**
 * Web-path attribute value type that adds possible values support
 * 
 * @author cbateman
 * 
 */
public class TemplateWebPathType extends WebPathType implements IPossibleValues
{

    public List getPossibleValues()
    {
//        final IStructuredDocumentContext context = getStructuredDocumentContext();
//        final IDOMContextResolver resolver = IStructuredDocumentContextResolverFactory.INSTANCE
//                .getDOMContextResolver(context);
//        if (resolver != null)
//        {
//            final Node node = resolver.getNode();
//            if (node instanceof Attr)
//            {
//                return createPossibleValues((Attr) node);
//            }
//        }
        return Collections.EMPTY_LIST;
    }

//    private List createPossibleValues(Attr node)
//    {
//        final IVirtualContainer webRoot = getWebRoot();
//        final String currentPathString = node.getNodeValue();
//
//        if (currentPathString == null || "".equals(currentPathString.trim())) //$NON-NLS-1$ 
//        {
//            return Collections.EMPTY_LIST;
//        }
//
//        final IPath currentPath = new Path(currentPathString);
//        String[] segments = currentPath.segments();
//        if (segments != null)
//        {
//            final List possibleValues = new ArrayList();
//            IPath longestSubPath = new Path(""); //$NON-NLS-1$
//            IVirtualResource deepestElement = null;
//            for (final String segment : segments)
//            {
//                longestSubPath = longestSubPath.append(segment);
//                deepestElement = webRoot.findMember(longestSubPath);
//                if (deepestElement == null)
//                {
//                    break;
//                }
//            }
//        }
//    }

//    private IVirtualContainer getWebRoot()
//
//    {
//        IVirtualContainer webRoot = ComponentCore.createComponent(getProject())
//                .getRootFolder();
//
//        return webRoot;
//    }
}
