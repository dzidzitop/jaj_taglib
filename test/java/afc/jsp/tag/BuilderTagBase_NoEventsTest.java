package afc.jsp.tag;

import junit.framework.TestCase;

public class BuilderTagBase_NoEventsTest extends TestCase
{
    public void testBuildWithNoEvents() throws Exception
    {
        final PushEventsJspFragment body = new PushEventsJspFragment("foo");
        final StringJspWriter out = new StringJspWriter();
        final MockJspContext ctx = new MockJspContext(out);
        final NoEventsTag tag = new NoEventsTag();
        
        tag.setJspContext(ctx);
        tag.setJspBody(body);
        
        tag.doTag();
        
        assertTrue(tag.buildInvoked);
        assertTrue(body.invoked);
        assertEquals("", out.getOutput());
    }
    
    public void testBuildWithNoEventsAndNoBody() throws Exception
    {
        final StringJspWriter out = new StringJspWriter();
        final MockJspContext ctx = new MockJspContext(out);
        final NoEventsTag tag = new NoEventsTag();
        
        tag.setJspContext(ctx);
        tag.setJspBody(null);
        
        tag.doTag();
        
        assertTrue(tag.buildInvoked);
        assertEquals("", out.getOutput());
    }
    
    public void testBuildWithNoEvents_ExceptionIsThrownByBuild() throws Exception
    {
        final PushEventsJspFragment body = new PushEventsJspFragment("foo");
        final StringJspWriter out = new StringJspWriter();
        final MockJspContext ctx = new MockJspContext(out);
        final NoEventsTag tag = new NoEventsTag();
        
        tag.setJspContext(ctx);
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
    
    private static class NoEventsTag extends BuilderTagBase
    {
        public RuntimeException exceptionToThrow;
        public boolean buildInvoked;
        
        @Override
        protected void build()
        {
            assertFalse(buildInvoked);
            buildInvoked = true;
            if (exceptionToThrow != null) {
                throw exceptionToThrow;
            }
        }
    }
}
