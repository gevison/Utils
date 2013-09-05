package ge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 23/08/13
 * Time: 15:23
 */
public class Test
{
    public static void main( String[] args )
    {
        Logger logger = LogManager.getLogger( Test.class );

        logger.debug( "Test" );

        org.apache.log4j.Logger logger1 = org.apache.log4j.LogManager.getLogger( Test.class );

        logger1.debug( "Test 1" );
    }
}
