package afc.jsp.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import junit.framework.Assert;

public class GridTag extends BuilderTagBase
{
    public boolean buildInvoked;
    
    public int rowCount;
    public int columnCount;
    
    @Override
    protected void build() throws IOException, JspException
    {
        Assert.assertFalse(buildInvoked);
        buildInvoked = true;
        for (int i = 0; i < rowCount; ++i) {
            raiseEvent("rowStart");
            for (int j = 0; j < columnCount; ++j) {
                raiseEvent("cell");
            }
            raiseEvent("rowEnd");
        }
    }
}
