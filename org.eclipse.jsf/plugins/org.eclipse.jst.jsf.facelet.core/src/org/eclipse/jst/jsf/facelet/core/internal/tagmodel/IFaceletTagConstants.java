package org.eclipse.jst.jsf.facelet.core.internal.tagmodel;

import org.eclipse.jst.jsf.common.dom.TagIdentifier;
import org.eclipse.jst.jsf.core.internal.tld.TagIdentifierFactory;

/**
 * Common constants for Facelet ui tags.
 * @author cbateman
 *
 */
public interface IFaceletTagConstants
{
    /**
     * The JSF tag library uri
     */
    final static public String URI_JSF_FACELETS           = "http://java.sun.com/jsf/facelets"; //$NON-NLS-1$

    /**
     * component tagname
     */
    final static public String TAG_COMPONENT              = "component"; //$NON-NLS-1$

    /**
     * composition tagname
     */
    final static public String TAG_COMPOSITION              = "composition"; //$NON-NLS-1$
    /**
     * debug tagname
     */
    final static public String TAG_DEBUG                  = "debug"; //$NON-NLS-1$
    /**
     * decorate tagname
     */
    final static public String TAG_DECORATE               = "decorate"; //$NON-NLS-1$
    /**
     * define tagname
     */
    final static public String TAG_DEFINE                 = "define"; //$NON-NLS-1$
    /**
     * fragment tagname
     */
    final static public String TAG_FRAGMENT               = "fragment"; //$NON-NLS-1$
    /**
     * include tagname
     */
    final static public String TAG_INCLUDE                = "include"; //$NON-NLS-1$
    /**
     * insert tagname
     */
    final static public String TAG_INSERT                 = "insert"; //$NON-NLS-1$
    /**
     * param tagname
     */
    final static public String TAG_PARAM                  = "param"; //$NON-NLS-1$
    
    /**
     * remove tagname
     */
    final static public String TAG_REMOVE                 = "remove"; //$NON-NLS-1$
    /**
     * repeat tagname
     */
    final static public String TAG_REPEAT                 = "repeat"; //$NON-NLS-1$

    /**
     * TagIdentifier for COMPONENT 
     */
    final static TagIdentifier TAG_IDENTIFIER_COMPONENT = TagIdentifierFactory
                                                                  .createJSPTagWrapper(
                                                                          URI_JSF_FACELETS,
                                                                          TAG_COMPONENT);
    /**
     * TagIdentifier for COMPOSITE 
     */
    final static TagIdentifier TAG_IDENTIFIER_COMPOSITION = TagIdentifierFactory
                                                                  .createJSPTagWrapper(
                                                                          URI_JSF_FACELETS,
                                                                          TAG_COMPOSITION);

    /**
     * TagIdentifier for DEBUG 
     */
    final static TagIdentifier TAG_IDENTIFIER_DEBUG = TagIdentifierFactory
                                                                  .createJSPTagWrapper(
                                                                          URI_JSF_FACELETS,
                                                                          TAG_DEBUG);
    
    /**
     * TagIdentifier for DEBUG 
     */
    final static TagIdentifier TAG_IDENTIFIER_DECORATE = TagIdentifierFactory
                                                                  .createJSPTagWrapper(
                                                                          URI_JSF_FACELETS,
                                                                          TAG_DECORATE);

    /**
     * TagIdentifier for DEFINE 
     */
    final static TagIdentifier TAG_IDENTIFIER_DEFINE = TagIdentifierFactory
                                                                  .createJSPTagWrapper(
                                                                          URI_JSF_FACELETS,
                                                                          TAG_DEFINE);
    
    /**
     * TagIdentifier for FRAGMENT 
     */
    final static TagIdentifier TAG_IDENTIFIER_FRAGMENT = TagIdentifierFactory
                                                                  .createJSPTagWrapper(
                                                                          URI_JSF_FACELETS,
                                                                          TAG_FRAGMENT);
    
    /**
     * TagIdentifier for INCLUDE 
     */
    final static TagIdentifier TAG_IDENTIFIER_INCLUDE = TagIdentifierFactory
                                                                  .createJSPTagWrapper(
                                                                          URI_JSF_FACELETS,
                                                                          TAG_INCLUDE);

    /**
     * TagIdentifier for INCLUDE 
     */
    final static TagIdentifier TAG_IDENTIFIER_INSERT = TagIdentifierFactory
                                                                  .createJSPTagWrapper(
                                                                          URI_JSF_FACELETS,
                                                                          TAG_INSERT);

    /**
     * TagIdentifier for PARAM 
     */
    final static TagIdentifier TAG_IDENTIFIER_PARAM = TagIdentifierFactory
                                                                  .createJSPTagWrapper(
                                                                          URI_JSF_FACELETS,
                                                                          TAG_PARAM);
    /**
     * TagIdentifier for REPEAT 
     */
    final static TagIdentifier TAG_IDENTIFIER_REMOVE = TagIdentifierFactory
                                                                  .createJSPTagWrapper(
                                                                          URI_JSF_FACELETS,
                                                                          TAG_REMOVE);
    /**
     * TagIdentifier for REPEAT 
     */
    final static TagIdentifier TAG_IDENTIFIER_REPEAT = TagIdentifierFactory
                                                                  .createJSPTagWrapper(
                                                                          URI_JSF_FACELETS,
                                                                          TAG_REPEAT);
}
