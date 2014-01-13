/* Copyright (c) 2011-2014, Dźmitry Laŭčuk
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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * <p>A {@link javax.servlet.jsp.tagext.SimpleTag simple tag} that registers an event handler
 * to a {@link BuilderTagBase &lt;builder&gt;} tag. An event handler is a body of a given
 * {@code <event>} tag which is invoked each time an event with the given name is fired
 * by the {@code <builder> tag}. The name of events this event handler processes is defined
 * by the tag attribute {@link #setName(String) name}. Generally, an event handler renders
 * some output basing on the state of the JSP context with which the body of the {@code <event>}
 * tag is evaluated.</p>
 * 
 * <h3>Tag input</h3>
 * <table border="1">
 * <thead>
 *  <tr><th>Attribute</th>
 *      <th>Required?</th>
 *      <th>Description</th></tr>
 * </thead>
 * <tbody>
 *  <tr><td><em>tag body</em></td>
 *      <td>no</td>
 *      <td>The JSP fragment that is executed each time an event with the given name
 *          is fired by the parent {@code <builder>} tag.</td></tr>
 *  <tr><td>name</td>
 *      <td>yes</td>
 *      <td>The name of events that are handled by the body of this {@code <event>} tag.
 *          It must be neither {@code null} nor an empty string.</td></tr>
 * </tbody>
 * </table>
 * 
 * <p>Refer to {@link BuilderTagBase} for a example of usage of the tag pair
 * {@code <builder>}/{@code <event>}.</p>
 * 
 * @author D&#378;mitry La&#365;&#269;uk
 */
public final class EventTag extends SimpleTagSupport
{
    /* The name of events to subscribe to.
     * 
     * It is package-private to be accessible for BuilderTagBase.
     */
    String name;
    
    /**
     * <p>Sets the name of events to subscribe this event handler to. The parent
     * {@link BuilderTagBase &lt;builder&gt;} tag invokes the body of this {@code <event>} tag
     * (if the body is defined) when an event with the given name is fired.</p>
     * 
     * @param name the event name to be set. It must be neither {@code null} nor an empty
     *      string. In addition, this name must be unique among other {@code <event>} tags
     *      that are nested within the same {@code <builder>} tag. Otherwise
     *      a {@link JspTagException javax.servlet.jsp.JspTagException} is thrown by
     *      this {@code <builder>} tag.
     */
    public void setName(final String name)
    {
        this.name = name;
    }
    
    /**
     * <p>Executes this tag. Refer to the {@link EventTag class description} for the details
     * about semantics of this tag.</p>
     * 
     * @throws JspTagException in any of the following cases:
     *      <ul>
     *          <li>this tag is invoked outside the body of some
     *              {@link BuilderTagBase &lt;builder&gt;} tag</li>
     *          <li>an event handler with the same name is already registered for the parent
     *              {@code <builder>} tag</li>
     *          <li>the {@link #setName(String) name} attribute if this tag is either empty or
     *              {@code null}</li>
     *      </ul>
     */
    @Override
    public final void doTag() throws JspTagException
    {
        final JspTag parent = findAncestorWithClass(this, BuilderTagBase.class);
        if (parent == null) {
            throw new JspTagException("EventTag must be enclosed directly or indirectly by a BuilderTagBase tag.");
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
