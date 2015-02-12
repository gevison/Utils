package ge.utils.log.jul;

import org.apache.log4j.Level;

public interface JULLevelConverter
{
	/**
	 * Convertes a java.util.logging.Level class into an appropriate log4j
	 * Level, based on whatever policy you want.
	 * 
	 * 
	 * @param juliLevel
	 * @return
	 */
	public Level convertJuliLevel( java.util.logging.Level juliLevel );

	/**
	 * Converts a log4j {@link org.apache.log4j.Level} into a juli
	 * {@link java.util.logging.Level}.
	 * 
	 * @param log4jLevel
	 * @return
	 */
	public java.util.logging.Level convertLog4jLevel( Level log4jLevel );
}
