package afc.jsp.tag;

import junit.framework.TestCase;

public class BuilderTagBase_BuildGridTest extends TestCase
{
    private MockJspContext ctx;
    private StringJspWriter out;
    private EventTag rowStartTag;
    private EventTag rowEndTag;
    private EventTag cellTag;
    private GridTag gridTag;
    private PushEventsJspFragment body;
    
    @Override
    protected void setUp()
    {
        out = new StringJspWriter();
        ctx = new MockJspContext(out);
        
        gridTag = new GridTag();
        gridTag.setJspContext(ctx);
        
        rowStartTag = initEvent("rowStart", '^');
        rowEndTag = initEvent("rowEnd", '$');
        cellTag = initEvent("cell", '-');
        
        body = new PushEventsJspFragment("foo", rowStartTag, rowEndTag, cellTag);
        gridTag.setJspBody(body);
    }
    
    private EventTag initEvent(final String name, final char charToPrint)
    {
        final EventTag tag = new EventTag();
        tag.setJspContext(ctx);
        tag.setParent(gridTag);
        tag.setName(name);
        tag.setJspBody(new PrintCharJspFragment(charToPrint));
        return tag;
    }
    
    public void testBuildGrid_SingleCell() throws Exception
    {
        gridTag.rowCount = 1;
        gridTag.columnCount = 1;
        
        gridTag.doTag();
        
        assertTrue(gridTag.buildInvoked);
        assertTrue(body.invoked);
        assertEquals("^-$", out.getOutput());
    }
    
    public void testBuildGrid_SingleRow() throws Exception
    {
        gridTag.rowCount = 1;
        gridTag.columnCount = 3;
        
        gridTag.doTag();
        
        assertTrue(gridTag.buildInvoked);
        assertTrue(body.invoked);
        assertEquals("^---$", out.getOutput());
    }
    
    public void testBuildGrid_SingleColumn() throws Exception
    {
        gridTag.rowCount = 3;
        gridTag.columnCount = 1;
        
        gridTag.doTag();
        
        assertTrue(gridTag.buildInvoked);
        assertTrue(body.invoked);
        assertEquals("^-$^-$^-$", out.getOutput());
    }
    
    public void testBuildGrid_ZeroRows() throws Exception
    {
        gridTag.rowCount = 0;
        gridTag.columnCount = 3;
        
        gridTag.doTag();
        
        assertTrue(gridTag.buildInvoked);
        assertTrue(body.invoked);
        assertEquals("", out.getOutput());
    }
    
    public void testBuildGrid_TwoRowsThreeColumns() throws Exception
    {
        gridTag.rowCount = 2;
        gridTag.columnCount = 3;
        
        gridTag.doTag();
        
        assertTrue(gridTag.buildInvoked);
        assertTrue(body.invoked);
        assertEquals("^---$^---$", out.getOutput());
    }
}
