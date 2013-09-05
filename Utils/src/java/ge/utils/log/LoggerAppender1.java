package ge.utils.log;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import java.util.Date;

public class LoggerAppender1 extends AppenderSkeleton
{
    @Override
    protected void append( LoggingEvent loggingEvent )
    {
        LocationInfo locationInformation = loggingEvent.getLocationInformation();
        ThrowableInformation throwableInformation = loggingEvent.getThrowableInformation();

        LogCompilerEvent logCompilerEvent = new LogCompilerEvent();

        logCompilerEvent.setMessage( ( String ) loggingEvent.getMessage() );
        logCompilerEvent.setLevel( getLevel(loggingEvent.getLevel()) );
        logCompilerEvent.setTimeStamp( new Date( loggingEvent.getTimeStamp()) );
        logCompilerEvent.setClassName( locationInformation.getClassName() );
        logCompilerEvent.setMethodName( locationInformation.getMethodName() );
        logCompilerEvent.setLineNumber( new Long( locationInformation.getLineNumber()) );

        if ( throwableInformation != null )
        {
            logCompilerEvent.setThrown( throwableInformation.getThrowable() );
        }

        LogCompiler.add( logCompilerEvent );
    }

    private LogCompilerLevel getLevel( Level level )
    {
        if ( level.isGreaterOrEqual( Level.FATAL ) == true )
        {
            return LogCompilerLevel.FATAL;
        }
        else if ( level.isGreaterOrEqual( Level.ERROR ) == true )
        {
            return LogCompilerLevel.ERROR;
        }
        else if( level.isGreaterOrEqual( Level.WARN ) == true )
        {
            return LogCompilerLevel.WARNING;
        }
        else if( level.isGreaterOrEqual( Level.INFO ) == true )
        {
            return LogCompilerLevel.INFO;
        }
        else if( level.isGreaterOrEqual( Level.DEBUG ) == true )
        {
            return LogCompilerLevel.DEBUG;
        }
        else
        {
            return LogCompilerLevel.TRACE;
        }
    }

    @Override
    public void close()
    {
    }

    @Override
    public boolean requiresLayout()
    {
        return false;
    }
}
