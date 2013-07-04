package afc.jsp.tag;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;

import junit.framework.Assert;

public class PushEventsJspFragment extends JspFragment
{
    public boolean invoked;
    
    private final EventTag[] events;
    private final String textToWrite;
    
    public PushEventsJspFragment(final String textToWrite, final EventTag... events)
    {
        Assert.assertNotNull(events);
        this.events = events;
        this.textToWrite = textToWrite;
    }
    
    @Override
    public JspContext getJspContext()
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void invoke(final Writer out) throws JspException, IOException
    {
        Assert.assertFalse(invoked);
        invoked = true;
        out.write(textToWrite);
        for (final EventTag event : events) {
            event.doTag();
        }
    }

}
