package ge.utils.log;


import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttr;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;

import javax.swing.event.EventListenerList;
import java.util.ArrayList;
import java.util.List;

@Plugin( name = "LoggerAppender", category = "Core", elementType = "appender", printObject = true )
public class LoggerAppender extends AbstractAppender
{
    private static EventListenerList listenerList = new EventListenerList();

    private static List<Log4jLogEvent> eventList = new ArrayList<Log4jLogEvent>();


    protected LoggerAppender( String name )
    {
        super( name, null, null );
    }

    @PluginFactory
    public static LoggerAppender createAppender( @PluginAttr( "name" ) String name,
                                                 @PluginAttr( "suppressExceptions" ) String suppress,
                                                 @PluginElement( "layout" ) Layout layout,
                                                 @PluginElement( "filters" ) Filter filter )
    {
        if ( name == null )
        {
            LOGGER.error( "No name provided for LoggerAppender" );
            return null;
        }

        return new LoggerAppender( name );
    }

    @Override
    public void append( LogEvent loggingEvent )
    {
        eventList.add( ( Log4jLogEvent ) loggingEvent );

        LoggerListener[] listeners = getLoggerListeners();
        for ( LoggerListener listener : listeners )
        {
            listener.newLoggingEvent();
        }
    }

    public static void addLoggerListener( LoggerListener loggerListener )
    {
        listenerList.add( LoggerListener.class, loggerListener );
    }

    public static void removeLoggerListener( LoggerListener loggerListener )
    {
        listenerList.remove( LoggerListener.class, loggerListener );
    }

    public static LoggerListener[] getLoggerListeners()
    {
        return listenerList.getListeners( LoggerListener.class );
    }

    public static Log4jLogEvent get( int index )
    {
        return eventList.get( index );
    }

    public static int size()
    {
        return eventList.size();
    }
}
