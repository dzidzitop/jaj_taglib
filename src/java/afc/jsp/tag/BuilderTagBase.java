/* Copyright (c) 2011-2013, Dźmitry Laŭčuk
   All rights reserved.

   Redistribution and use in source and binary forms, with or without
   modification, are permitted provided that the following conditions are met: 

   1. Redistributions of source code must retain the above copyright notice, this
      list of conditions and the following disclaimer. 
   2. Redistributions in binary form must reproduce the above copyright notice,
      this list of conditions and the following disclaimer in the documentation
      and/or other materials provided with the distribution. 

   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
   DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
   ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */
package afc.jsp.tag;

import java.io.IOException;
import java.util.TreeMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import afc.util.DevNull;

public abstract class BuilderTagBase extends SimpleTagSupport
{
    private final TreeMap<String, EventTag> events = new TreeMap<String, EventTag>();
    
    void register(final EventTag event) throws JspTagException
    {
        final String name = event.getName();
        // TODO check if this could be checked by a tag validator
        if (name == null || name.length() == 0) {
            throw new JspTagException("Event name is undefined.");
        }
        // TODO check if this could be checked by a tag validator
        if (events.put(event.getName(), event) != null) {
            throw new JspTagException("Duplicate event handler for the event '" + name + "'.");
        }
    }
    
    @Override
    public final void doTag() throws JspException, IOException
    {
        final JspFragment body = getJspBody();
        if (body != null) { // else invoke build against no events
            body.invoke(DevNull.instance);
        }
        build();
    }
    
    protected abstract void build() throws JspException, IOException;
    
    public final void raiseEvent(final String name) throws JspException, IOException
    {
        final EventTag event = events.get(name);
        if (event != null) {
            event.raise();
        }
    }
}
