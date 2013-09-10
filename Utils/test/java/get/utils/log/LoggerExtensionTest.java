package get.utils.log;

import ge.utils.log.LoggerEx;
import org.apache.log4j.Level;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 10/09/13
 * Time: 16:17
 */
public class LoggerExtensionTest
{
    public static void main( String... args )
    {
        try
        {
            logTest1( args );
        }
        catch ( IllegalArgumentException e )
        {
            LoggerEx.catching( Level.TRACE, e );
        }
    }

    public static String logTest1( String... args ) throws IllegalArgumentException
    {
        logTest( args );
        LoggerEx.entry( args );

        throw LoggerEx.throwing( new IllegalArgumentException() );
    }

    public static String[] logTest( String... args )
    {
        LoggerEx.entry( args );

        LoggerEx.warn( "test message" );

        return LoggerEx.exit( new String[]{"Tree","House"} );
    }
}
