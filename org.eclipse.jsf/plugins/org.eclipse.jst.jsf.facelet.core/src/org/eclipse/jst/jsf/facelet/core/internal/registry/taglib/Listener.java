package org.eclipse.jst.jsf.facelet.core.internal.registry.taglib;

import java.util.EventObject;

/**
 * A listener for locator detected tag library changes
 * @author cbateman
 *
 */
public abstract class Listener
{
    /**
     * Indicates that a tag library has changed
     * @author cbateman
     *
     */
    public static class TaglibChangedEvent extends EventObject
    {
        /**
         * TODO: what happens if one locator has a namespace collision with
         * another one?
         */
        public enum CHANGE_TYPE
        {
            /**
             * Indicates that the library is new
             */
            ADDED, 
            
            /**
             * Indicates that the library was removed.
             */
            REMOVED, 
            
            /**
             * Indicates that the library is not new, but it's content
             * has changed
             */
            CHANGED
        }

        private final TaglibChangedEvent.CHANGE_TYPE       _changeType;
        private final IFaceletTagRecord _oldValue;
        private final IFaceletTagRecord _newValue;

        /**
         * @param source
         * @param oldValue 
         * @param newValue 
         * @param changeType
         */
        public TaglibChangedEvent(
                final AbstractFaceletTaglibLocator source,
                final IFaceletTagRecord oldValue, 
                final IFaceletTagRecord newValue,
                TaglibChangedEvent.CHANGE_TYPE changeType)
        {
            super(source);
            _changeType = changeType;
            _oldValue = oldValue;
            _newValue = newValue;
        }

        /**
         * 
         */
        private static final long serialVersionUID = -4060018031568577836L;

        @Override
        public AbstractFaceletTaglibLocator getSource()
        {
            return (AbstractFaceletTaglibLocator) super.getSource();
        }

        /**
         * @return the type of the change
         */
        public final TaglibChangedEvent.CHANGE_TYPE getChangeType()
        {
            return _changeType;
        }

        /**
         * @return the old value. This is null if the event is ADDED
         */
        public final IFaceletTagRecord getOldValue()
        {
            return _oldValue;
        }
        
        /**
         * @return the new value.  This is null if the event is REMOVED
         */
        public final IFaceletTagRecord getNewValue()
        {
            return _newValue;
        }
    }

    /**
     * @param event 
     * 
     */
    public abstract void tagLibChanged(Listener.TaglibChangedEvent event);
}