/* Copyright (c) 2011-2013, Dźmitry Laŭčuk
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
package afc.jsp.fn;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;

import afc.util.RoundUtil;

public final class Functions
{
    private Functions()
    {
    }
    
    public static Object round(final Object number, final int minFractionDigits, final int maxFractionDigits,
            final RoundingMode roundingMode)
    {
        if (number == null) {
            return null;
        }
        if (number instanceof BigDecimal) {
            return RoundUtil.round((BigDecimal) number, minFractionDigits, maxFractionDigits, roundingMode);
        }
        if (number instanceof Double)
        {
            return RoundUtil.round(((Double) number).doubleValue(), minFractionDigits, maxFractionDigits, roundingMode);
        }
        if (number instanceof Float)
        {
            return RoundUtil.round(((Float) number).floatValue(), minFractionDigits, maxFractionDigits, roundingMode);
        }
        if (number instanceof Number) {
            return RoundUtil.round(new BigDecimal(number.toString()), minFractionDigits, maxFractionDigits, roundingMode);
        }
        if (number instanceof String) {
            return RoundUtil.round(new BigDecimal((String) number), minFractionDigits, maxFractionDigits, roundingMode);
        }
        throw new IllegalArgumentException(MessageFormat.format("Unsupported number type: ''{0}''.", number.getClass()));
    }
}
