package ge.utils.log;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 04/09/13
 * Time: 15:11
 */
public class LogCompilerEvent
{
    private String message;

    private LogCompilerLevel level;

    private Date timeStamp;

    private String className;

    private String methodName;

    private Long lineNumber;

    private Throwable thrown;

    public String getMessage()
    {
        return message;
    }

    public void setMessage( String message )
    {
        this.message = message;
    }

    public LogCompilerLevel getLevel()
    {
        return level;
    }

    public void setLevel( LogCompilerLevel level )
    {
        this.level = level;
    }

    public void setTimeStamp( Date timeStamp )
    {
        this.timeStamp = timeStamp;
    }

    public Date getTimeStamp()
    {
        return timeStamp;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName( String className )
    {
        this.className = className;
    }

    public String getMethodName()
    {
        return methodName;
    }

    public void setMethodName( String methodName )
    {
        this.methodName = methodName;
    }

    public Long getLineNumber()
    {
        return lineNumber;
    }

    public void setLineNumber( Long lineNumber )
    {
        this.lineNumber = lineNumber;
    }

    public void setThrown( Throwable thrown )
    {
        this.thrown = thrown;
    }

    public Throwable getThrown()
    {
        return thrown;
    }
}
