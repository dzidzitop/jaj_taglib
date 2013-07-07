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

/**
 * <p>An abstract simple tag whose sub-classes produce a sequence of events which can be handled by
 * event handlers to render a complex structure by small pieces.
 * Event handlers are <tt>event</tt> tags in the <tt>builder</tt> tag body. The <tt>name</tt> attribute of the
 * <tt>event</tt> tag defines the event this tag subscribes to.
 * The algorithmic part of the rendering process is implemented by the <tt>{@link #build}</tt> function.
 * Each time an event is raised by <tt>builder</tt> (see <tt>{@link #raiseEvent(String)}</tt>)
 * the correspondent <tt>event</tt> tag body is evaluated. All parameters to the event handler (if any)
 * are passed in the JSP context.</p>
 * 
 * <p>The advantage of this approach to render content is clear separation between code that generates
 * structure (it is enclosed in tag's @{code build()} function) and code that renders output (it is
 * enclosed in event handler tags' bodies). This allows for having simple and straightforward JSP code
 * while keeping all algorithmic complexity in Java code (which is easier to create, test, and document).
 * In addition, it is possible to implement different representations on top of the same algorithm.</p>
 * 
 * <p>An example below demonstrates a simple use case of the <tt>builder</tt>/<tt>event</tt> tags.</p>
 * <h2>Example.</h2>
 * <h3>The builder tag.</h3>
 * <p><pre>{@code
 * public class DigitsTag extends BuilderTagBase
 * { 
 *   protected void build() throws IOException, JspException
 *   {
 *     raiseEvent("start");
 *     for (int i = 0; i &amp; 10; ++i) {
 *       if (i != 0) {
 *         raiseEvent("separator");
 *       }
 *       getJspContext().setAttribute("digit", i);
 *       raiseEvent("digit");
 *     }
 *     raiseEvent("end");
 *   }
 * }}</pre></p>
 * <h3>JSP fragment.</h3>
 * <p><pre>{@code
 * <afc:digits>
 *   <afc:event name="start">digits: [</afc:event>
 *   <afc:event name="end">];</afc:event>
 *   <afc:event name="digit">${digit}</afc:event>
 *   <afc:event name="separator"> </afc:event>
 * </afc:digits>}</pre></p>
 * <h3>JSP output.</h3>
 * <p><pre>{@code digits: [0 1 2 3 4 5 6 7 8 9];}</pre></p>
 * <p>In this example the JSP code has neither loops nor branches.</p>
 * 
 * <h2>Additional details.</h2>
 * <p>
 * <ul>
 *  <li><tt>builder</tt>'s body is evaluated once and before <tt>build()</tt> is invoked. The content generated
 *      while evaluating the body is omitted</li>
 *  <li>it is allowed to have no body for the <tt>builder</tt> tag. The <tt>build()</tt> function is invoked in this case
 *      while having no output rendered</li>
 *  <li>if an exception is thrown while evaluating <tt>builder</tt>'s body then <tt>build()</tt> is not invoked</li>
 *  <li><tt>event</tt> tags could be direct or indirect inner tags of the <tt>builder</tt> tag to be registered
 *      as event handlers</tt>
 *  <li>only a single event handler could be registered to listen for a given event</li>
 *  <li>it is allowed to have no event handler for an event. In this case the events will be ignored with no error.
 *      Event handler tags with empty bodies will have the same effect</li>
 * </ul>
 * </p>
 *
 * @author D&#378;mitry La&#365;&#269;uk
 */
public abstract class BuilderTagBase extends SimpleTagSupport
{
    private final TreeMap<String, EventTag> events = new TreeMap<String, EventTag>();
    
    void register(final EventTag eventHandler) throws JspTagException
    {
        final String name = eventHandler.getName();
        // TODO check if this could be checked by a tag validator
        if (name == null || name.length() == 0) {
            throw new JspTagException("Event name is undefined.");
        }
        // TODO check if this could be checked by a tag validator
        if (events.put(name, eventHandler) != null) {
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
    
    protected final void raiseEvent(final String name) throws JspException, IOException
    {
        final EventTag event = events.get(name);
        if (event != null) {
            event.raise();
        }
    }
}
