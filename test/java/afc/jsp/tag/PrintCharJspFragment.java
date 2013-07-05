package afc.jsp.tag;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;

public class PrintCharJspFragment extends JspFragment
{
    private final char c;
    
    public PrintCharJspFragment(final char c)
    {
        this.c = c;
    }
    
    @Override
    public JspContext getJspContext()
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void invoke(final Writer out) throws JspException, IOException
    {
        out.write(c);
    }
}
