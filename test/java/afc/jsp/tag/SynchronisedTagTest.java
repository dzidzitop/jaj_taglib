package afc.jsp.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;

import junit.framework.TestCase;

public class SynchronisedTagTest extends TestCase
{
    private Object monitor;
    private SynchronisedTag tag;
    private StringJspWriter out;
    private MockJspContext ctx;
    
    @Override
    protected void setUp()
    {
        tag = new SynchronisedTag();
        out = new StringJspWriter();
        ctx = new MockJspContext(out);
        tag.setJspContext(ctx);
        monitor = new Object();
        tag.setMonitor(monitor);
    }
    
    public void testSynchronisedCall() throws Exception
    {
        final AtomicBoolean testFailed = new AtomicBoolean();
        final AtomicBoolean inTagBody = new AtomicBoolean();
        final AtomicInteger val = new AtomicInteger();
        
        final Thread t = new Thread() {
            @Override
            public void run()
            {
                try {
                    while (!inTagBody.get()) {
                        synchronized (this) {
                            this.wait();
                        }
                    }
                    synchronized (monitor) {
                        // this should happen after tag body has been executed
                        if (val.get() != 1) {
                            testFailed.set(true);
                        }
                        val.set(2);
                    }
                }
                catch (InterruptedException ex) {
                    return;
                }
            }
        };
        
        t.setDaemon(true); // just in case
        t.start();
        
        final JspFragment body = new JspFragment() {
            @Override
            public JspContext getJspContext()
            {
                return ctx;
            }

            @Override
            public void invoke(final Writer out) throws JspException, IOException
            {
                inTagBody.set(true);
                synchronized (t) {
                    t.notify();
                }
                try {
                    Thread.sleep(50);
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt(); // should never happen in normal case
                    return;
                }
                val.set(1);
            }
            
        };
        
        tag.setJspBody(body);
        
        try {
            tag.doTag();
            
            t.join(1000);
        }
        finally {
            if (t.isAlive()) {
                t.interrupt();
                fail();
            }
        }
        
        assertFalse(testFailed.get());
        assertEquals(2, val.get());
    }
}
