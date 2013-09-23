/* Copyright (c) 2013, Dźmitry Laŭčuk
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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import junit.framework.TestCase;

public class EventTag_InvalidUseTest extends TestCase
{
    public void testEventTagOutsideBuilderTagBase_NoParent_WithBody() throws Exception
    {
        final EventTag tag = new EventTag();
        final PrintCharJspFragment body = new PrintCharJspFragment('-');
        tag.setJspBody(body);
        final StringJspWriter out = new StringJspWriter();
        tag.setJspContext(new MockJspContext(out));
        
        tag.setName("someEvent");
        
        try {
            tag.doTag();
            fail();
        }
        catch (JspException ex) {
            assertEquals("EventTag must be enclosed directly or indirectly by a BuilderTagBase tag.", ex.getMessage());
        }
        
        assertEquals("", out.getOutput());
    }
    
    public void testEventTagOutsideBuilderTagBase_NoParent_WithEmptyBody() throws Exception
    {
        final EventTag tag = new EventTag();
        final StringJspWriter out = new StringJspWriter();
        tag.setJspContext(new MockJspContext(out));
        
        tag.setName("someEvent");
        
        try {
            tag.doTag();
            fail();
        }
        catch (JspException ex) {
            assertEquals("EventTag must be enclosed directly or indirectly by a BuilderTagBase tag.", ex.getMessage());
        }
        
        assertEquals("", out.getOutput());
    }
    
    public void testEventTagOutsideBuilderTagBase_WithInvalidParent_WithBody() throws Exception
    {
        final EventTag tag = new EventTag();
        final PrintCharJspFragment body = new PrintCharJspFragment('-');
        tag.setJspBody(body);
        final StringJspWriter out = new StringJspWriter();
        tag.setJspContext(new MockJspContext(out));
        
        tag.setParent(new SimpleTagSupport());
        
        tag.setName("someEvent");
        
        try {
            tag.doTag();
            fail();
        }
        catch (JspException ex) {
            assertEquals("EventTag must be enclosed directly or indirectly by a BuilderTagBase tag.", ex.getMessage());
        }
        
        assertEquals("", out.getOutput());
    }
    
    public void testEventTagOutsideBuilderTagBase_WithInvalidParent_WithEmptyBody() throws Exception
    {
        final EventTag tag = new EventTag();
        final PrintCharJspFragment body = new PrintCharJspFragment('-');
        tag.setJspBody(body);
        final StringJspWriter out = new StringJspWriter();
        tag.setJspContext(new MockJspContext(out));
        
        tag.setParent(new SimpleTagSupport());
        
        tag.setName("someEvent");
        
        try {
            tag.doTag();
            fail();
        }
        catch (JspException ex) {
            assertEquals("EventTag must be enclosed directly or indirectly by a BuilderTagBase tag.", ex.getMessage());
        }
        
        assertEquals("", out.getOutput());
    }
}
