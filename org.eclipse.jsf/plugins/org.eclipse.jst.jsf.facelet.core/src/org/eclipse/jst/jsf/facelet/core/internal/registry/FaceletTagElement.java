package org.eclipse.jst.jsf.facelet.core.internal.registry;

import org.eclipse.jem.internal.proxy.core.IBeanProxy;
import org.eclipse.jem.internal.proxy.core.ProxyFactoryRegistry;

/*package*/class FaceletTagElement
{
    private final String               _uri;
    private final String               _name;
    // TODO: these are temporary.
    private final IBeanProxy           _factory;
    private final ProxyFactoryRegistry _registry;

    /**
     * @param factory
     * @param name
     * @param uri
     */
    FaceletTagElement(String uri, String name, IBeanProxy factory,
            final ProxyFactoryRegistry registry)
    {
        super();
        this._factory = factory;
        this._name = name;
        this._uri = uri;
        this._registry = registry;
    }

    public String getUri()
    {
        return _uri;
    }

    public String getName()
    {
        return _name;
    }

    public IBeanProxy getFactory()
    {
        return _factory;
    }

    public ProxyFactoryRegistry getRegistry()
    {
        return _registry;
    }

}
