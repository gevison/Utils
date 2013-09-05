package ge.utils.log;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttr;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;

import java.util.Date;

@Plugin( name = "LoggerAppender2", category = "Core", elementType = "appender", printObject = true )
public class LoggerAppender2 extends AbstractAppender
{
    protected LoggerAppender2( String name )
    {
        super( name, null, null );
    }

    @PluginFactory
    public static LoggerAppender2 createAppender( @PluginAttr( "name" ) String name,
                                                 @PluginAttr( "suppressExceptions" ) String suppress,
                                                 @PluginElement( "layout" ) Layout layout,
                                                 @PluginElement( "filters" ) Filter filter )
    {
        if ( name == null )
        {
            LOGGER.error( "No name provided for LoggerAppender2" );
            return null;
        }

        return new LoggerAppender2( name );
    }

    @Override
    public void append( LogEvent loggingEvent )
    {
        Log4jLogEvent log4jLogEvent = ( Log4jLogEvent ) loggingEvent;
        StackTraceElement source = log4jLogEvent.getSource();

        LogCompilerEvent logCompilerEvent = new LogCompilerEvent();

        logCompilerEvent.setMessage( log4jLogEvent.getMessage().getFormattedMessage() );
        logCompilerEvent.setLevel( getLevel(log4jLogEvent.getLevel()) );
        logCompilerEvent.setTimeStamp( new Date( log4jLogEvent.getMillis() ) );
        logCompilerEvent.setClassName( source.getClassName() );
        logCompilerEvent.setMethodName( source.getMethodName() );
        logCompilerEvent.setLineNumber( new Long(source.getLineNumber()) );
        logCompilerEvent.setThrown(log4jLogEvent.getThrown());

        LogCompiler.add( logCompilerEvent );
    }

    private LogCompilerLevel getLevel( Level level )
    {
        if ( level.isAtLeastAsSpecificAs( Level.FATAL ) == true )
        {
            return LogCompilerLevel.FATAL;
        }
        else if ( level.isAtLeastAsSpecificAs( Level.ERROR ) == true )
        {
            return LogCompilerLevel.ERROR;
        }
        else if ( level.isAtLeastAsSpecificAs( Level.WARN ) == true )
        {
            return LogCompilerLevel.WARNING;
        }
        else if ( level.isAtLeastAsSpecificAs( Level.INFO ) == true )
        {
            return LogCompilerLevel.INFO;
        }
        else if ( level.isAtLeastAsSpecificAs( Level.DEBUG ) == true )
        {
            return LogCompilerLevel.DEBUG;
        }
        else
        {
            return LogCompilerLevel.TRACE;
        }
    }
}
