package afc.util;

import java.io.Writer;

public final class DevNull extends Writer
{
    public static final DevNull instance = new DevNull();
    
    private DevNull()
    {
    }

    @Override
    public void close()
    {
    }

    @Override
    public void flush()
    {
    }

    @Override
    public void write(final char[] cbuf, final int off, final int len)
    {
    }
}