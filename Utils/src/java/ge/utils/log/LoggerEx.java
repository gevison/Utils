package ge.utils.log;

import org.apache.commons.lang3.ClassUtils;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 10/09/13
 * Time: 16:13
 */
public class LoggerEx
{

    public static final String RETURNING = "Returning - ";

    public static final String VOID = "void";

    public static final String ENTERING = "Entering - ";

    public static final String NULL = "null";

    public static final String CAUGHT = "Caught - ";

    public static final String THROWING = "Throwing - ";

    private static Logger rootLogger = LogManager.getRootLogger();

    public static void fatal( final Object message )
    {
        log( Level.FATAL, message );
    }

    public static void log( final Level level, final Object message )
    {
        log( level, message, null );
    }

    public static void log( final Level level, final Object message, final Throwable throwable )
    {
        if ( rootLogger.isEnabledFor( level ) == true )
        {
            StackTraceElement lastCallPoint = getLastCallPoint();

            if ( lastCallPoint != null )
            {
                Logger logger = LogManager.getLogger( lastCallPoint.getClassName() );

                logger.log( LoggerEx.class.getName(), level, message, throwable );
            }
        }
    }

    private static StackTraceElement getLastCallPoint()
    {
        Throwable th = new Throwable();
        StackTraceElement[] stackTrace = th.getStackTrace();

        StackTraceElement lastCallPoint = null;

        for ( StackTraceElement stackTraceElement : stackTrace )
        {
            String className = stackTraceElement.getClassName();

            if ( className.equals( LoggerEx.class.getName() ) == false )
            {
                lastCallPoint = stackTraceElement;
                break;
            }
        }
        return lastCallPoint;
    }

    public static void fatal( final Object message, final Throwable throwable )
    {
        log( Level.FATAL, message, throwable );
    }

    public static void error( final Object message )
    {
        log( Level.ERROR, message );
    }

    public static void error( final Object message, final Throwable throwable )
    {
        log( Level.ERROR, message, throwable );
    }

    public static void warn( final Object message )
    {
        log( Level.WARN, message );
    }

    public static void warn( final Object message, final Throwable throwable )
    {
        log( Level.WARN, message, throwable );
    }

    public static void info( final Object message )
    {
        log( Level.INFO, message );
    }

    public static void info( final Object message, final Throwable throwable )
    {
        log( Level.INFO, message, throwable );
    }

    public static void debug( final Object message )
    {
        log( Level.DEBUG, message );
    }

    public static void debug( final Object message, final Throwable throwable )
    {
        log( Level.DEBUG, message, throwable );
    }

    public static void trace( final Object message )
    {
        log( Level.TRACE, message );
    }

    public static void trace( final Object message, final Throwable throwable )
    {
        log( Level.TRACE, message, throwable );
    }

    public static void entry( final Object... parameters )
    {
        entry( Level.TRACE, parameters );
    }

    public static void entry( final Level level, final Object... parameters )
    {
        if ( rootLogger.isEnabledFor( level ) == true )
        {
            log( level, entryMsg( parameters ) );
        }
    }

    private static String entryMsg( Object... parameters )
    {
        StackTraceElement lastCallPoint = getLastCallPoint();

        String message = ENTERING;
        message += "(";

        if ( ( parameters != null ) && ( parameters.length != 0 ) )
        {
            if ( isEllipsisArgument( lastCallPoint, parameters ) == true )
            {
                parameters = new Object[]{ parameters };
            }

            String parameterString = "";

            for ( Object parameter : parameters )
            {
                if ( parameterString.length() != 0 )
                {
                    parameterString += ",";
                }

                if ( parameter != null )
                {
                    if ( parameter.getClass().isArray() == false )
                    {
                        parameterString += parameter.toString();
                    }
                    else
                    {
                        parameterString += Arrays.toString( ( Object[] ) parameter );
                    }
                }
                else
                {
                    parameterString += NULL;
                }
            }

            message += parameterString;
        }

        message += ")";

        return message;
    }

    private static boolean isEllipsisArgument( StackTraceElement lastCallPoint, Object[] parameters )
    {
        try
        {
            Class aClass = ClassUtils.getClass( lastCallPoint.getClassName() );

            if ( lastCallPoint.getMethodName().equals( "<init>" ) == true )
            {
                try
                {
                    aClass.getDeclaredConstructor( parameters.getClass() );

                    return true;
                }
                catch ( NoSuchMethodException e )
                {
                    // Ignoring
                }
            }
            else
            {
                try
                {
                    aClass.getDeclaredMethod( lastCallPoint.getMethodName(), parameters.getClass() );

                    return true;
                }
                catch ( NoSuchMethodException e )
                {
                    // Ignoring
                }
            }
        }
        catch ( ClassNotFoundException e )
        {
            // Ignoring
        }

        return false;
    }

    public static void exit()
    {
        exit( Level.TRACE );
    }

    public static void exit( final Level level )
    {
        exit( level, Void.TYPE );
    }

    public static <R> R exit( final R result )
    {
        return exit( Level.TRACE, result );
    }

    public static <R> R exit( final Level level, final R result )
    {
        if ( rootLogger.isEnabledFor( level ) == true )
        {
            log( level, exitMsg( result ) );
        }

        return result;
    }

    private static String exitMsg( final Object result )
    {
        String message = RETURNING;

        if ( result == null )
        {
            message += NULL;
        }
        else if ( result.getClass().isArray() == true )
        {
            message += Arrays.toString( ( Object[] ) result );
        }
        else
        {
            message += result;
        }

        return message;
    }

    public static <T extends Throwable> T throwing( final T thrown )
    {
        return throwing( Level.ERROR, thrown );
    }

    public static <T extends Throwable> T throwing( final Level level, final T thrown )
    {
        if ( ( thrown != null ) && ( rootLogger.isEnabledFor( level ) == true ) )
        {
            log( level, throwingMessage( thrown ), thrown );
        }

        return thrown;
    }

    private static String throwingMessage( Throwable thrown )
    {
        String message = THROWING;

        message += thrown.getClass().getName();
        message += ": ";
        message += thrown.getMessage();

        return message;
    }

    public static void catching( final Throwable caught )
    {
        catching( Level.ERROR, caught );
    }

    public static void catching( final Level level, final Throwable caught )
    {
        if ( ( caught != null ) && ( rootLogger.isEnabledFor( level ) == true ) )
        {
            log( level, catchingMessage( caught ), caught );
        }
    }

    private static String catchingMessage( Throwable caught )
    {
        String message = CAUGHT;

        message += caught.getClass().getName();
        message += ": ";
        message += caught.getMessage();

        return message;
    }
}
