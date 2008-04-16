package org.eclipse.jst.jsf.facelet.core.internal.tagmodel;

import org.eclipse.jst.jsf.common.runtime.internal.model.decorator.ValidatorTypeInfo;


/**
 * 
 *
 */
public class ValidatorTag extends FaceletTag
{
    /**
     * 
     */
    private static final long serialVersionUID = 3898280066837027347L;
    private final ValidatorTypeInfo _validatorTypeInfo;

    /**
     * @param name
     * @param uri
     * @param type
     */
    public ValidatorTag(final String uri, final String name, final ValidatorTypeInfo validatorTypeInfo, final String handlerClass)
    {
        super(uri, name, TagType.VALIDATOR, handlerClass);
        _validatorTypeInfo = validatorTypeInfo;
    }
    public ValidatorTypeInfo getValidatorId()
    {
        return _validatorTypeInfo;
    }
    @Override
    public String toString()
    {
        String toString = super.toString();
        toString += "Validator Id: "+getValidatorId()+"\n";

        if (getTagHandlerClassName() != null)
        {
            toString += "Handler Class: "+getTagHandlerClassName()+"\n";
        }
        return toString;
    }
}
