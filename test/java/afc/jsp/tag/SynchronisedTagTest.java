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
import java.io.Writer;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;

import junit.framework.TestCase;

/**
 * <p>Unit tests for {@link SynchronisedTag}.</p>
 * 
 * @author D&#378;mitry La&#365;&#269;uk
 */
public class SynchronisedTagTest extends TestCase
{
    private Object monitor;
    private SynchronisedTag tag;
    private StringJspWriter out;
    private MockJspContext ctx;
    
    /**
     * <p>Initialises each test.</p>
     */
    @Override
    protected void setUp()
    {
        tag = new SynchronisedTag();
        out = new StringJspWriter();
        ctx = new MockJspContext(out);
        tag.setJspContext(ctx);
        monitor = new Object();
        tag.setMonitor(monitor);
    }
    
    /**
     * <p>De-initialises each test.</p>
     */
    @Override
    protected void tearDown()
    {
        monitor = null;
        tag = null;
        out = null;
        ctx = null;
    }
    
    /**
     * <p>Verifies that the tag body is executed only once and synchronised on the tag's monitor.</p>
     * 
     * @throws Exception if this test fails.
     */
    public void testSynchronisedCall() throws Exception
    {
        final MockBody body = new MockBody();
        body.ctx = ctx;
        body.monitor = monitor;
        
        tag.setJspBody(body);
        
        tag.doTag();
        
        assertTrue(body.bodyInvoked);
    }
    
    /**
     * <p>Verifies that the tag body is executed only once and synchronised on the tag's monitor
     * and that if an exception is thrown in the tag body then it is not suppressed
     * by {@code SynchronisedTag}.</p>
     * 
     * @throws Exception if this test fails.
     */
    public void testSynchronisedCall_TagBodyThrowsException() throws Exception
    {
        final JspException exception = new JspException();
        
        final MockBody body = new MockBody();
        body.ctx = ctx;
        body.monitor = monitor;
        body.exception = exception;
        
        tag.setJspBody(body);
        
        try {
            tag.doTag();
            fail();
        }
        catch (JspException ex)
        {
            assertSame(exception, ex);
        }
        
        assertTrue(body.bodyInvoked);
    }
    
    /**
     * <p>Verifies that {@code null} is not allowed as a tag's monitor object.</p>
     * 
     * @throws Exception if this test fails.
     */
    public void testNullMonitor() throws Exception
    {
        try {
            tag.setMonitor(null);
            tag.doTag();
            fail();
        }
        catch (NullPointerException ex) {
            assertEquals("monitor", ex.getMessage());
        }
    }
    
    /**
     * <p>Verifies that {@code SynchronisedTag} requires to have a body.</p>
     * 
     * @throws Exception if this test fails.
     */
    public void testEmptyBody() throws Exception
    {
        tag.setMonitor(monitor);
        
        try {
            tag.doTag();
            fail();
        }
        catch (JspException ex) {
            assertEquals("Tag body is undefined.", ex.getMessage());
        }
    }
    
    private static class MockBody extends JspFragment
    {
        public Object monitor;
        public boolean bodyInvoked;
        public JspContext ctx;
        public JspException exception;
        
        @Override
        public JspContext getJspContext()
        {
            return ctx;
        }

        @Override
        public void invoke(final Writer out) throws JspException, IOException
        {
            assertFalse(bodyInvoked);
            bodyInvoked = true;
            
            try {
                // Throws an IllegalMonitorStateException if not executed in monitor's synchronised block.
                monitor.wait(1);
            }
            catch (IllegalMonitorStateException ex) {
                fail("Tag body is not invoked synchronised on the tag's monitor.");
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                fail();
            }
            
            if (exception != null) {
                throw exception;
            }
        }
        
    };
}
