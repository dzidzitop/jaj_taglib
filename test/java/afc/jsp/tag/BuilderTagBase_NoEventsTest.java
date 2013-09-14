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

import java.io.IOException;

import javax.servlet.jsp.JspException;

import junit.framework.TestCase;

public class BuilderTagBase_NoEventsTest extends TestCase
{
    private CallTagsJspFragment body;
    private StringJspWriter out;
    private MockJspContext ctx;
    private NoEventsTag tag;
    
    @Override
    protected void setUp()
    {
        body = new CallTagsJspFragment("foo");
        out = new StringJspWriter();
        ctx = new MockJspContext(out);
        tag = new NoEventsTag();
        
        tag.setJspBody(body);
        tag.setJspContext(ctx);
    }
    
    public void testBuildWithNoEvents() throws Exception
    {
        tag.setJspBody(body);
        
        tag.doTag();
        
        assertTrue(tag.buildInvoked);
        assertTrue(body.invoked);
        assertEquals("", out.getOutput());
    }
    
    public void testBuildWithNoEventsAndNoBody() throws Exception
    {
        tag.setJspBody(null);
        
        tag.doTag();
        
        assertTrue(tag.buildInvoked);
        assertEquals("", out.getOutput());
    }
    
    public void testBuildWithNoEvents_RuntimeExceptionIsThrownByBuild() throws Exception
    {
        tag.setJspBody(body);
        
        final RuntimeException exception = new RuntimeException();
        tag.exceptionToThrow = exception;
        
        try {
            tag.doTag();
            fail();
        }
        catch (RuntimeException ex) {
            assertSame(exception, ex);
        }
        
        assertTrue(tag.buildInvoked);
        assertTrue(body.invoked);
        assertEquals("", out.getOutput());
    }
    
    public void testBuildWithNoEvents_ErrorIsThrownByBuild() throws Exception
    {
        tag.setJspBody(body);
        
        final Error exception = new Error();
        tag.exceptionToThrow = exception;
        
        try {
            tag.doTag();
            fail();
        }
        catch (Error ex) {
            assertSame(exception, ex);
        }
        
        assertTrue(tag.buildInvoked);
        assertTrue(body.invoked);
        assertEquals("", out.getOutput());
    }
    
    public void testBuildWithNoEvents_IOExceptionIsThrownByBuild() throws Exception
    {
        tag.setJspBody(null);
        
        final IOException exception = new IOException();
        tag.exceptionToThrow = exception;
        
        try {
            tag.doTag();
            fail();
        }
        catch (IOException ex) {
            assertSame(exception, ex);
        }
        
        assertTrue(tag.buildInvoked);
        assertEquals("", out.getOutput());
    }
    
    public void testBuildWithNoEvents_JspExceptionIsThrownByBuild() throws Exception
    {
        tag.setJspBody(body);
        
        final JspException exception = new JspException();
        tag.exceptionToThrow = exception;
        
        try {
            tag.doTag();
            fail();
        }
        catch (JspException ex) {
            assertSame(exception, ex);
        }
        
        assertTrue(tag.buildInvoked);
        assertTrue(body.invoked);
        assertEquals("", out.getOutput());
    }
    
    private static class NoEventsTag extends BuilderTagBase
    {
        public Throwable exceptionToThrow;
        public boolean buildInvoked;
        
        @Override
        protected void build() throws IOException, JspException
        {
            assertFalse(buildInvoked);
            buildInvoked = true;
            if (exceptionToThrow instanceof RuntimeException) {
                throw (RuntimeException) exceptionToThrow;
            }
            if (exceptionToThrow instanceof Error) {
                throw (Error) exceptionToThrow;
            }
            if (exceptionToThrow instanceof IOException) {
                throw (IOException) exceptionToThrow;
            }
            if (exceptionToThrow instanceof JspException) {
                throw (JspException) exceptionToThrow;
            }
        }
    }
}
