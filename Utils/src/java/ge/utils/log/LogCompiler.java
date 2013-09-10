package ge.utils.log;

import javax.swing.event.EventListenerList;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 04/09/13
 * Time: 15:10
 */
public class LogCompiler
{
    private static EventListenerList listenerList = new EventListenerList();

    private static List<LogCompilerEvent> eventList = new ArrayList<LogCompilerEvent>();

    private static LogCompilerLevel level = LogCompilerLevel.INFO;

    public static void setLevel( LogCompilerLevel level )
    {
        LogCompiler.level = level;
    }

    public static LogCompilerLevel getLevel()
    {
        return level;
    }

    public static void addLoggerListener( LoggerListener loggerListener )
    {
        listenerList.add( LoggerListener.class, loggerListener );
    }

    public static void removeLoggerListener( LoggerListener loggerListener )
    {
        listenerList.remove( LoggerListener.class, loggerListener );
    }

    public static LoggerListener[] getLoggerListeners()
    {
        return listenerList.getListeners( LoggerListener.class );
    }

    public static LogCompilerEvent get( int index )
    {
        return eventList.get( index );
    }

    public static int size()
    {
        return eventList.size();
    }

    public static void add( LogCompilerEvent logCompilerEvent )
    {
        if (( level == null ) || ( level.ordinal() >= logCompilerEvent.getLevel().ordinal() ))
        {
            eventList.add( logCompilerEvent );

            LoggerListener[] listeners = getLoggerListeners();
            for ( LoggerListener listener : listeners )
            {
                listener.newLoggingEvent();
            }
        }
    }
}
