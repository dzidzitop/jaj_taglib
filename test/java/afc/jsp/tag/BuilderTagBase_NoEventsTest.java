package afc.jsp.tag;

import junit.framework.TestCase;

public class BuilderTagBase_NoEventsTest extends TestCase
{
    public void testNoEventsTest() throws Exception
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
    
    public void testNoEventsAndNoBodyTest() throws Exception
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
    
    private static class NoEventsTag extends BuilderTagBase
    {
        public boolean buildInvoked;
        
        @Override
        protected void build()
        {
            assertFalse(buildInvoked);
            buildInvoked = true;
        }
    }
}
