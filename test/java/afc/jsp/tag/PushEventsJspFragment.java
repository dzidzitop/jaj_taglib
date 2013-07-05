package afc.jsp.tag;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTag;

import junit.framework.Assert;

public class PushEventsJspFragment extends JspFragment
{
    public boolean invoked;
    
    private final SimpleTag[] tags;
    private final String textToWrite;
    
    public PushEventsJspFragment(final SimpleTag... tags)
    {
        this("", tags);
    }
    
    public PushEventsJspFragment(final String textToWrite, final SimpleTag... tags)
    {
        Assert.assertNotNull(tags);
        this.tags = tags;
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
        for (final SimpleTag tag : tags) {
            tag.doTag();
        }
    }

}
