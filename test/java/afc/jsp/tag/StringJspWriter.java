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
