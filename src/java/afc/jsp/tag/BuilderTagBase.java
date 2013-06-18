package afc.jsp.tag;

import java.io.IOException;
import java.util.TreeMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import afc.util.DevNull;

public abstract class BuilderTagBase extends SimpleTagSupport
{
    private final TreeMap<String, EventTag> events = new TreeMap<String, EventTag>();
    
    void register(final EventTag event) throws JspTagException
    {
        // TODO check if this could be checked by a tag validator
        if (events.put(event.getName(), event) != null) {
            throw new JspTagException("duplicate event: " + event.getName());
        }
    }
    
    @Override
    public final void doTag() throws JspException, IOException
    {
        getJspBody().invoke(DevNull.instance);
        build();
    }
    
    public abstract void build() throws JspException, IOException;
    
    public final void raiseEvent(final String name) throws JspException, IOException
    {
        final EventTag event = events.get(name);
        if (event != null) {
            event.raise();
        }
    }
}
