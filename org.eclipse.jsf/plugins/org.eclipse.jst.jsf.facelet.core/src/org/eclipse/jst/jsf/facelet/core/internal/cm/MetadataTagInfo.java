package org.eclipse.jst.jsf.facelet.core.internal.cm;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jst.jsf.common.dom.TagIdentifier;
import org.eclipse.jst.jsf.core.internal.tld.TagIdentifierFactory;
import org.eclipse.jst.jsf.facelet.core.internal.FaceletCorePlugin;
import org.eclipse.jst.jsf.facelet.core.internal.cm.strategy.IExternalMetadataStrategy;
import org.eclipse.jst.jsf.facelet.core.internal.cm.strategy.JSPExternalMetadataStrategy;
import org.eclipse.jst.jsf.facelet.core.internal.cm.strategy.MDExternalMetadataStrategy;
import org.eclipse.jst.jsf.facelet.core.internal.cm.strategy.TagInfoStrategyComposite;
import org.eclipse.jst.jsp.core.internal.contentmodel.tld.provisional.TLDDocument;
import org.eclipse.wst.xml.core.internal.contentmodel.CMNamedNodeMap;

/**
 * An external tag info that checks first the meta-data repository and second in
 * the provided TLDDocument for data.
 * 
 * @author cbateman
 * 
 */
/* package */class MetadataTagInfo extends ExternalTagInfo
{
    private final String                      _uri;
//    private final MDExternalMetadataStrategy  _mdStrategy;
//    private final JSPExternalMetadataStrategy _jspStrategy;
    private final TagInfoStrategyComposite    _compositeStrategy;

    private MetadataTagInfo(final IProject project, final TLDDocument doc,
            final String uri)
    {
        _uri = uri;
        IExternalMetadataStrategy mdStrategy = MDExternalMetadataStrategy.create(project);
        JSPExternalMetadataStrategy jspStrategy = new JSPExternalMetadataStrategy(doc);

        final List<String> ids = new ArrayList<String>();
        ids.add(MDExternalMetadataStrategy.STRATEGY_ID);
        ids.add(JSPExternalMetadataStrategy.STRATEGY_ID);

        _compositeStrategy = new TagInfoStrategyComposite(ids);
        _compositeStrategy.addStrategy(mdStrategy);
        _compositeStrategy.addStrategy(jspStrategy);
    }

    public MetadataTagInfo(final IProject project, final String uri)
    {
        this(project, null, uri);
    }

    /**
     * @param project
     * @param doc
     */
    public MetadataTagInfo(final IProject project, final TLDDocument doc)
    {
        this(project, doc, doc.getUri());
    }

    @Override
    public Object getTagProperty(final String tagName, final String key)
    {
        final TagIdentifier tagId = TagIdentifierFactory.createJSPTagWrapper(
                _uri, tagName);
        _compositeStrategy.resetIterator();

        for (ExternalTagInfo tagInfo = getNextExternalInfo(tagId); tagInfo != _compositeStrategy
                .getNoResult(); tagInfo = getNextExternalInfo(tagId))
        {
            try
            {
                if (tagInfo != _compositeStrategy.getNoResult())
                {
                    final Object value = tagInfo.getTagProperty(tagName, key);

                    if (value != null)
                    {
                        return value;
                    }
                }

                // fall-through
            }
            catch (final Exception e)
            {
                FaceletCorePlugin.log("During meta-data strategy", e); //$NON-NLS-1$
            }
        }

        return null;
    }

    /**
     * @param tagName
     * @return a named node map of known attributes for the tag, or null if not
     *         found
     */
    @Override
    public CMNamedNodeMap getAttributes(final String tagName)
    {
        final TagIdentifier tagId = TagIdentifierFactory.createJSPTagWrapper(
                _uri, tagName);
        _compositeStrategy.resetIterator();

        for (ExternalTagInfo tagInfo = getNextExternalInfo(tagId); tagInfo != _compositeStrategy
                .getNoResult(); tagInfo = getNextExternalInfo(tagId))
        {
            try
            {
                if (tagInfo != _compositeStrategy.getNoResult())
                {
                    final CMNamedNodeMap nodeMap = tagInfo
                            .getAttributes(tagName);

                    if (nodeMap != null)
                    {
                        return nodeMap;
                    }
                }

                // fall-through
            }
            catch (final Exception e)
            {
                FaceletCorePlugin.log("During meta-data strategy", e); //$NON-NLS-1$
            }
        }

        return null;
    }

    private ExternalTagInfo getNextExternalInfo(final TagIdentifier input)
    {
        return _compositeStrategy.perform(input);
    }
}
