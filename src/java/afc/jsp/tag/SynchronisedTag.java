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
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * <p>A {@link javax.servlet.jsp.tagext.SimpleTag simple tag} that executes its body
 * synchronised upon the monitor of the object specified with the attribute <em>monitor</em>.
 * In pseudo-code this could be written as follows:</p>
 * 
 * <pre>
 * final Object object = someObject;
 * synchronized (object) {
 *     synchronisedTag.getJspBody().invoke(jspOut);
 * }</pre>
 * 
 * <p>This is needed to render shared objects in a thread-safe manner.</p>
 * 
 * <p>Any exception thrown within the tag body is thrown outside this {@code SynchronisedTag}.</p>
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
 *      <td>yes</td>
 *      <td>The JSP fragment to be executed synchronised upon the given
 *          object's monitor.</td></tr>
 *  <tr><td>monitor</td>
 *      <td>yes</td>
 *      <td>The object to synchronise upon. It must not be {@code null}.</td></tr>
 * </tbody>
 * </table>
 * 
 * <h3>Example</h3>
 * <p>The example below renders items in the user shopping cart as an atomic operation.</p>
 * 
 * <pre>
 * &lt;jaj:synchronisedTag monitor=&quot;${shoppingCart}&quot;&gt;
 *     &lt;c:forEach items=&quot;${shoppingCart}&quot; var=&quot;item&quot;&gt;
 *         &lt;mylib:renderProductItem item=&quot;${item}&quot;/&gt;
 *     &lt;/c:forEach&gt;
 * &lt;jaj:synchronisedTag&gt;</pre>
 * 
 * <p>Without synchronisation (given that <em>shoppingCart</em> is not thread-safe) updates
 * of this shopping cart in parallel could break rendering or lead to inconsistent data rendered.</p>
 * 
 * @author D&#378;mitry La&#365;&#269;uk
 */
public final class SynchronisedTag extends SimpleTagSupport
{
    private Object monitor;
    
    /**
     * Sets an object which monitor is used to synchronise upon to execute the tag body.
     * 
     * @param monitor the object to be used to synchronise upon. It must not be {@code null}.
     * 
     * @throws NullPointerException if <em>monitor</em> is {@code null}.
     */
    public void setMonitor(final Object monitor)
    {
        if (monitor == null) {
            throw new NullPointerException("monitor");
        }
        this.monitor = monitor;
    }
    
    /**
     * <p>Executes the body of this {@code SynchronisedTag} synchronised upon the monitor
     * of the object that is specified with the attribute <em>monitor</em>.
     * 
     * @throws JspException if this tag has no body.
     * @throws JspException if a {@code JspException} is thrown by the body of this tag.
     *      The same exception is thrown outside this tag with no modification.
     * @throws IOException if an {@code IOException} is thrown by the body of this tag.
     *      The same exception is thrown outside this tag with no modification.
     */
    @Override
    public void doTag() throws IOException, JspException
    {
        final JspFragment body = getJspBody();
        if (body == null) {
            throw new JspException("Tag body is undefined.");
        }
        final JspWriter out = getJspContext().getOut();
        synchronized (monitor) {
            body.invoke(out);
        }
    }
}
