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

import junit.framework.TestCase;

public class BuilderTagBase_BuildGridTest extends TestCase
{
    private MockJspContext ctx;
    private StringJspWriter out;
    private EventTag rowStartTag;
    private EventTag rowEndTag;
    private EventTag cellTag;
    private GridTag gridTag;
    private CallTagsJspFragment body;
    
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
        
        body = new CallTagsJspFragment("foo", rowStartTag, rowEndTag, cellTag);
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
    
    public void testBuildGrid_TwoRowsThreeColumns_CellTagWithEmptyBody() throws Exception
    {
        cellTag.setJspBody(null);
        
        gridTag.rowCount = 2;
        gridTag.columnCount = 3;
        
        gridTag.doTag();
        
        assertTrue(gridTag.buildInvoked);
        assertTrue(body.invoked);
        assertEquals("^$^$", out.getOutput());
    }
}
