package afc.jsp.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public final class EventTag extends SimpleTagSupport
{
    private String name;
    
    public void setName(final String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
    
    @Override
    public void doTag() throws JspException, IOException
    {
        final JspTag parent = getParent();
        if (!(parent instanceof BuilderTagBase)) {
            throw new JspTagException("EventTag must be enclosed directly by a BuilderTagBase tag");
        }
        ((BuilderTagBase) parent).register(this);
    }
    
    final void raise() throws JspException, IOException
    {
        final JspFragment body = getJspBody();
        if (body != null) {
            body.invoke(getJspContext().getOut());
        }
    }
}
