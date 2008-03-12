package org.eclipse.jst.jsf.facelet.core.internal.tagmodel;


/**
 * 
 *
 */
public class ValidatorTag extends FaceletTag 
{
    private final String _validatorId;

    /**
     * @param name
     * @param uri 
     * @param type
     */
    public ValidatorTag(String uri, final String name, final String validatorId, final String handlerClass) 
    {
        super(uri, name, TagType.VALIDATOR, handlerClass);
        _validatorId = validatorId;
    }
    public String getValidatorId() 
    {
        return _validatorId;
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
