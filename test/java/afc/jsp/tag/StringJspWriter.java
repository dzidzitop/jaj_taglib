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

import javax.servlet.jsp.JspWriter;

public class StringJspWriter extends JspWriter
{
    private final StringBuilder output;
    
    public StringJspWriter()
    {
        super(1024, true);
        output = new StringBuilder();
    }
    
    public String getOutput()
    {
        return output.toString();
    }

    @Override
    public void clear() throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void clearBuffer() throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void close() throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void flush() throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public int getRemaining()
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void newLine() throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void print(boolean arg0) throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void print(char arg0) throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void print(int arg0) throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void print(long arg0) throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void print(float arg0) throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void print(double arg0) throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void print(char[] arg0) throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void print(String arg0) throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void print(Object arg0) throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void println() throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void println(boolean arg0) throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void println(char arg0) throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void println(int arg0) throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void println(long arg0) throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void println(float arg0) throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void println(double arg0) throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void println(char[] arg0) throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void println(String arg0) throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void println(Object arg0) throws IOException
    {
        throw new UnsupportedOperationException("implement me");
    }

    @Override
    public void write(final char[] buf, int off, int len) throws IOException
    {
        output.append(buf, off, len);
    }
}
