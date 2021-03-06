/* Copyright (c) 2011-2014, Dźmitry Laŭčuk
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
package afc.util;

import java.io.Writer;

/**
 * <p>A {@link Writer java.io.Writer} that throws away any data that is written to it.
 * {@code DevNull} is a singleton whose instance is accessed via the static field
 * {@link #instance}.</p>
 * 
 * @author D&#378;mitry La&#365;&#269;uk
 */
public final class DevNull extends Writer
{
    /**
     * <p>The only instance of {@code DevNull}.</p>
     */
    public static final DevNull instance = new DevNull();
    
    /**
     * <p>Prohibits having instances of {@code DevNull} other than {@link #instance}.</p>
     */
    private DevNull()
    {
    }
    
    /**
     * <p>Does nothing.</p>
     */
    @Override
    public void close()
    {
    }
    
    /**
     * <p>Does nothing.</p>
     */
    @Override
    public void flush()
    {
    }
    
    /**
     * <p>Does nothing.</p>
     */
    @Override
    public void write(final char[] buf, final int off, final int len)
    {
    }
}