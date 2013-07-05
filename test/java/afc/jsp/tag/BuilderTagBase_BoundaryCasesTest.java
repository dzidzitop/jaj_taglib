package afc.jsp.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import junit.framework.TestCase;

public class BuilderTagBase_BoundaryCasesTest extends TestCase
{
    private MockJspContext ctx;
    private StringJspWriter out;
    private GridTag gridTag;
    
    @Override
    protected void setUp()
    {
        out = new StringJspWriter();
        ctx = new MockJspContext(out);
        
        gridTag = new GridTag();
        gridTag.setJspContext(ctx);
    }
    
    private EventTag initEvent(final String name, final char charToPrint)
    {
        return initEvent(gridTag, name, charToPrint);
    }
    
    private EventTag initEvent(final JspTag parent, final String name, final char charToPrint)
    {
        final EventTag tag = new EventTag();
        tag.setJspContext(ctx);
        tag.setParent(parent);
        tag.setName(name);
        tag.setJspBody(new PrintCharJspFragment(charToPrint));
        return tag;
    }
    
    /**
     * Tests that non-registered events handlers are just ignored by BuilderTagBase.
     */
    public void testBuildGrid_NotAllEventHandlersRegistered() throws Exception
    {
        final EventTag rowStartTag = initEvent("rowStart", '^');
        final EventTag cellTag = initEvent("cell", '-');
        
        final PushEventsJspFragment body = new PushEventsJspFragment("foo", rowStartTag, cellTag);
        gridTag.setJspBody(body);
        
        gridTag.rowCount = 3;
        gridTag.columnCount = 2;
        
        gridTag.doTag();
        
        assertTrue(gridTag.buildInvoked);
        assertTrue(body.invoked);
        assertEquals("^--^--^--", out.getOutput());
    }
    
    /**
     * Tests that event tag's parents are scanned deeply to find a BuilderTagBase parent.
     */
    public void testBuildGrid_EventsWithIndirectBuilderParentTag() throws Exception
    {
        final EventTag rowStartTag = initEvent("rowStart", '^');
        final EventTag rowEndTag = initEvent("rowEnd", '$');
        
        final SimpleTag cellTagParent = new SimpleTagSupport()
        {
            @Override
            public void doTag() throws IOException, JspException
            {
                getJspBody().invoke(getJspContext().getOut());
            }
        };
        cellTagParent.setJspContext(ctx);
        cellTagParent.setParent(gridTag);
        final EventTag cellTag = initEvent(cellTagParent, "cell", '-');
        cellTagParent.setJspBody(new PushEventsJspFragment(cellTag));
        
        final PushEventsJspFragment body = new PushEventsJspFragment("foo", rowStartTag, rowEndTag, cellTagParent);
        gridTag.setJspBody(body);
        
        gridTag.rowCount = 3;
        gridTag.columnCount = 2;
        
        gridTag.doTag();
        
        assertTrue(gridTag.buildInvoked);
        assertTrue(body.invoked);
        assertEquals("^--$^--$^--$", out.getOutput());
    }
}
