package afc.jsp.tag;

import java.util.Enumeration;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;

import junit.framework.Assert;

public class MockJspContext extends JspContext
{
    private final JspWriter out;
    
    public MockJspContext(final JspWriter out)
    {
        Assert.assertNotNull(out);
        this.out = out;
    }
    
    @Override
    public Object findAttribute(String arg0)
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public Object getAttribute(String arg0)
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public Object getAttribute(String arg0, int arg1)
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public Enumeration getAttributeNamesInScope(int arg0)
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public int getAttributesScope(String arg0)
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public ExpressionEvaluator getExpressionEvaluator()
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public JspWriter getOut()
    {
        return out;
    }

    @Override
    public VariableResolver getVariableResolver()
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void removeAttribute(String arg0)
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void removeAttribute(String arg0, int arg1)
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void setAttribute(String arg0, Object arg1)
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void setAttribute(String arg0, Object arg1, int arg2)
    {
        throw new UnsupportedOperationException("implement me");
    }

}
