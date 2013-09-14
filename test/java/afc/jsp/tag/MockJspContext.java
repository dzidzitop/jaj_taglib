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
