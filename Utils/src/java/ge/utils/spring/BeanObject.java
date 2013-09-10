package ge.utils.spring;

import ge.utils.log.LoggerEx;
import org.springframework.beans.factory.InitializingBean;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;

import static org.springframework.util.Assert.state;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public abstract class BeanObject implements InitializingBean
{
    private transient boolean initialised = false;

    protected BeanObject()
    {
        LoggerEx.trace( "Created bean object." );
    }

    public final void afterPropertiesSet() throws
                                           Exception
    {
        initialised = true;
        validateBeanObject();
    }

    protected abstract void validateBeanObject();

    public final void testInitialised()
    {
        state( ( initialised == false ), "Definition cannot be changed after initialisation." );
    }

    public boolean isInitialised()
    {
        return initialised;
    }
}
