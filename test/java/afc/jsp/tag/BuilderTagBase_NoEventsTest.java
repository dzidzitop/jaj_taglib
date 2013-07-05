package afc.jsp.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import junit.framework.TestCase;

public class BuilderTagBase_NoEventsTest extends TestCase
{
    private PushEventsJspFragment body;
    private StringJspWriter out;
    private MockJspContext ctx;
    private NoEventsTag tag;
    
    @Override
    protected void setUp()
    {
        body = new PushEventsJspFragment("foo");
        out = new StringJspWriter();
        ctx = new MockJspContext(out);
        tag = new NoEventsTag();
        
        tag.setJspBody(body);
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
