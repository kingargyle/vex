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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.FaceletXMLDefnTaglib;
import org.eclipse.jst.jsf.facelet.core.internal.registry.taglib.faceletTaglib.TagDefn;

/**
 * A tag record based on an xml tag defined Facelet taglib.xml
 * @author cbateman
 *
 */
public class XMLBasedTagRecord extends FaceletTagRecord
{
    /**
     * 
     */
    private static final long serialVersionUID = 1411551451386954263L;
    private final FaceletXMLDefnTaglib _taglibDefn;
    private Map<String, TagDefn>       _tagIndexByName;

    /**
     * @param taglibDefn
     */
    public XMLBasedTagRecord(final FaceletXMLDefnTaglib taglibDefn)
    {
        _taglibDefn = taglibDefn;
    }

    @Override
    public String getURI()
    {
        return _taglibDefn.getNamespace();
    }


    @Override
    public TagDefn getTag(final String name)
    {
        return getAndIndexElementDeclaration(name);
    }

    private synchronized TagDefn getAndIndexElementDeclaration(final String name)
    {
        TagDefn tagDefn = null;

        if (_tagIndexByName == null)
        {
            _tagIndexByName = new HashMap<String, TagDefn>();
        }
        else
        {
            tagDefn = _tagIndexByName.get(name);
        }

        if (tagDefn == null && _tagIndexByName.size() < _taglibDefn.getTags().size())
        {
            tagDefn = findTag(name);
        }

        return tagDefn;
    }

    private TagDefn findTag(final String name)
    {
        for (final TagDefn tag : _taglibDefn.getTags())
        {
            if (name.equals(tag.getName()))
            {
                return tag;
            }
        }
        return null;
    }

    @Override
    public List<TagDefn> getTags()
    {
        return Collections.unmodifiableList(_taglibDefn.getTags());
    }

    public int getNumTags()
    {
        return _taglibDefn.getTags().size();
    }
}
