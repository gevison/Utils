package ge.utils.log.jul;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerRepository;

import java.util.Enumeration;
import java.util.logging.Filter;

public class JULLog4jBridge
{
	public static void bridgeJULLogger( String JULLoggerName )
	{
		bridgeJULLogger( JULLoggerName, LogManager.getLoggerRepository(), JULLog4jEventConverter.DEFAULT_LEVEL_CONVERTER );
	}

	public static void bridgeJULLogger( String JULLoggerName, LoggerRepository repository )
	{
		bridgeJULLogger( JULLoggerName, repository, JULLog4jEventConverter.DEFAULT_LEVEL_CONVERTER );
	}

	public static void bridgeJULLogger( String JULLoggerName, LoggerRepository repository, JULLevelConverter levelConverter )
	{
		Logger log4jLogger = repository.getLogger( JULLoggerName );
		java.util.logging.Logger JULLogger = java.util.logging.Logger.getLogger( JULLoggerName );

		JULLog4jEventConverter converter = new JULLog4jEventConverter( repository, levelConverter );
		Filter filter = JULLogger.getFilter();
		JULBridgeFilter newFilter = new JULBridgeFilter( filter, log4jLogger, converter );
		JULLogger.setFilter( newFilter );
	}

	public static void assimilate( LoggerRepository repository, JULLevelConverter levelConverter )
	{
		java.util.logging.LogManager.getLogManager().reset();

		Level threshold = repository.getThreshold();

		java.util.logging.Level convertLog4jLevel = levelConverter.convertLog4jLevel( threshold );
		java.util.logging.Logger logger = java.util.logging.Logger.getLogger( "" );

        logger.setLevel( convertLog4jLevel );

        JULLog4jEventConverter converter = new JULLog4jEventConverter( repository, levelConverter );

        JULBridgeHandler handler = new JULBridgeHandler( repository, converter );

        logger.addHandler( handler );
	}

	public static void assimilate( LoggerRepository repository )
	{
		assimilate( repository, JULLog4jEventConverter.DEFAULT_LEVEL_CONVERTER );
	}

	public static void assimilate()
	{
		assimilate( LogManager.getLoggerRepository() );
	}

	@SuppressWarnings( "rawtypes" )
	public static void repatriate()
	{
		java.util.logging.LogManager logManager = java.util.logging.LogManager.getLogManager();

		Enumeration loggerNames = logManager.getLoggerNames();

        while ( loggerNames.hasMoreElements() )
		{
			Object nextElement = loggerNames.nextElement();

			java.util.logging.Logger logger = logManager.getLogger( nextElement.toString() );

			logger.setFilter( null );
		}

		logManager.reset();
	}
}
