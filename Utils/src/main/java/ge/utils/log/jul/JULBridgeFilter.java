package ge.utils.log.jul;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

class JULBridgeFilter implements Filter
{
	private final Logger loggerToPostTo;
	private final JULLog4jEventConverter converter;
	private final Filter existingJuliFilter;

	public JULBridgeFilter( final Filter existingJuliFilter, Logger loggerToPostTo, JULLog4jEventConverter converter )
	{
		this.existingJuliFilter = existingJuliFilter;
		this.loggerToPostTo = loggerToPostTo;
		this.converter = converter;
	}

	public boolean isLoggable( LogRecord record )
	{
		LoggingEvent event = converter.convert( record );
		loggerToPostTo.callAppenders( event );
		return ( existingJuliFilter != null ? existingJuliFilter.isLoggable( record ) : true );
	}

}
