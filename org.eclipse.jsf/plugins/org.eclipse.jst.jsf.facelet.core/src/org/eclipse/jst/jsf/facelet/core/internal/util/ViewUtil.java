package org.eclipse.jst.jsf.facelet.core.internal.util;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jst.jsf.core.internal.CompositeTagRegistryFactory;
import org.eclipse.jst.jsf.core.internal.CompositeTagRegistryFactory.TagRegistryIdentifier;
import org.eclipse.jst.jsf.designtime.internal.view.model.ITagRegistry;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public final class ViewUtil
{
    /**
     * @param attributes
     * @return the set of uri's that declared in attributes
     */
    public static Set<Attr> getDeclaredNamespaces(final NamedNodeMap attributes)
    {
        final Set<Attr> alreadyUsed = new HashSet<Attr>();
        for (int i = 0; i < attributes.getLength(); i++)
        {
            final Node node = attributes.item(i);
            if ("xmlns".equals(node.getNodeName())
                    || "xmlns".equals(node.getPrefix()))
            {
                final String attrValue = node.getNodeValue();

                if (attrValue != null && !"".equals(attrValue.trim())
                        && node instanceof Attr)
                {
                    alreadyUsed.add((Attr) node);
                }
            }
        }

        return alreadyUsed;
    }

    public static boolean hasAttributeValue(final Set<Attr> attrSet,
            final String value)
    {
        for (final Attr attr : attrSet)
        {
            if (value.equals(attr.getValue()))
            {
                return true;
            }
        }
        return false;
    }

    public static ITagRegistry getHtmlTagRegistry(final IProject project)
    {
        final IContentType contentType = Platform.getContentTypeManager()
                .getContentType("org.eclipse.wst.html.core.htmlsource");
        final TagRegistryIdentifier id = new TagRegistryIdentifier(project,
                contentType);
        final ITagRegistry tagRegistry = CompositeTagRegistryFactory
                .getInstance().getRegistry(id);
        return tagRegistry;
    }
}
