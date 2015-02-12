package ge.utils.log.jul;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import org.apache.log4j.Category;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.LoggingEvent;

public class JULBridgeHandler extends Handler
{
	private static final String UNKNOWN_LOGGER_NAME = "unknown.jul.logger";

	private final LoggerRepository repository;
	private final JULLog4jEventConverter converter;

	public JULBridgeHandler( LoggerRepository repository, JULLog4jEventConverter converter )
	{
		this.repository = repository;
		this.converter = converter;
	}

	public void close() throws SecurityException
	{

	}

	public void flush()
	{

	}

	public void publish( LogRecord record )
	{
		LoggingEvent event = converter.convert( record );

		String loggerName = record.getLoggerName();
		if ( loggerName == null )
		{
			loggerName = UNKNOWN_LOGGER_NAME;
		}

		Category localLogger = repository.getLogger( loggerName );
		if ( event.getLevel().isGreaterOrEqual( localLogger.getEffectiveLevel() ) )
		{
			localLogger.callAppenders( event );
		}
	}
}
