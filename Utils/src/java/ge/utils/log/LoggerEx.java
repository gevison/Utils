package ge.utils.log;

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
    private static Logger rootLogger = LogManager.getRootLogger();

    public static void log( Level level, Object message )
    {
        log( level, message, null );
    }

    public static void log( Level level, Object message, Throwable throwable )
    {
        if ( rootLogger.isEnabledFor( level ) == true )
        {
            Throwable th = new Throwable(  );
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

            if ( lastCallPoint != null )
            {
                Logger logger = LogManager.getLogger( lastCallPoint.getClassName() );

                logger.log( LoggerEx.class.getName(), level, message, throwable );
            }
        }
    }

    public static void fatal( Object message )
    {
        log( Level.FATAL, message );
    }

    public static void fatal( Object message,
                       Throwable t )
    {
        log( Level.FATAL, message, t );
    }

    public static void error( Object message )
    {
        log( Level.ERROR, message );
    }

    public static void error( Object message,
                              Throwable t )
    {
        log( Level.ERROR, message, t );
    }

    public static void warn( Object message )
    {
        log( Level.WARN, message );
    }

    public static void warn( Object message,
                             Throwable t )
    {
        log( Level.WARN, message, t );
    }

    public static void info( Object message )
    {
        log( Level.INFO, message );
    }

    public static void info( Object message,
                             Throwable t )
    {
        log( Level.INFO, message, t );
    }

    public static void debug( Object message )
    {
        log( Level.DEBUG, message );
    }

    public static void debug( Object message,
                              Throwable t )
    {
        log( Level.DEBUG, message, t );
    }

    public static void trace( Object message )
    {
        log( Level.TRACE, message );
    }

    public static void trace( Object message, Throwable t )
    {
        log( Level.TRACE, message, t );
    }

    public static void entry( final Object... params )
    {
        log( Level.TRACE, entryMsg( params.length, params ) );
    }

    public static void exit()
    {
        exit( null );
    }

    public static <R> R exit( final R result )
    {
        log( Level.TRACE, exitMsg( result ) );

        return result;
    }

    public static <T extends Throwable> T throwing( final T t )
    {
        return throwing( Level.ERROR, t );
    }

    public static <T extends Throwable> T throwing( final Level level, final T t )
    {
        log( level, "throwing", t );

        return t;
    }

    public static void catching( final Throwable t )
    {
        catching( Level.ERROR, t );
    }

    public static void catching( final Level level, final Throwable t )
    {
        log( level, "catching", t );
    }

    private static String entryMsg( final int count, final Object... params )
    {
        if ( count == 0 )
        {
            return "entry";
        }

        final StringBuilder sb = new StringBuilder( "entry params(" );
        int i = 0;
        for ( final Object parm : params )
        {
            if ( parm != null )
            {
                if ( parm.getClass().isArray() == false )
                {
                    sb.append( parm.toString() );
                }
                else
                {
                    sb.append( Arrays.toString( ( Object[] ) parm ) );
                }
            }
            else
            {
                sb.append( "null" );
            }

            if ( ++i < params.length )
            {
                sb.append( ", " );
            }
        }
        sb.append( ")" );
        return sb.toString();
    }

    private static String exitMsg( final Object result )
    {
        if ( result == null )
        {
            return "exit";
        }
        else if ( result.getClass().isArray() == true )
        {
            return "exit with(" + Arrays.toString( ( Object[] ) result ) + ")";
        }
        else
        {
            return "exit with(" + result + ")";
        }
    }
}
