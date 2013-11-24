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
package afc.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;

public final class RoundUtil
{
    private RoundUtil()
    {
    }
    
    public static float round(final float number, final int maxFractionDigits, final RoundingMode roundingMode)
    {
        // Validating input.
        if (roundingMode == null) {
            throw new NullPointerException("roundingMode");
        }
        if (maxFractionDigits < 0) {
            throw new IllegalArgumentException(MessageFormat.format(
                    "Negative maxFractionDigits: {0}.", String.valueOf(maxFractionDigits)));
        }
        
        return roundImpl(new BigDecimal(Float.toString(number)), 0, maxFractionDigits, roundingMode).floatValue();
    }
    
    public static double round(final double number, final int maxFractionDigits, final RoundingMode roundingMode)
    {
        // Validating input.
        if (roundingMode == null) {
            throw new NullPointerException("roundingMode");
        }
        if (maxFractionDigits < 0) {
            throw new IllegalArgumentException(MessageFormat.format(
                    "Negative maxFractionDigits: {0}.", String.valueOf(maxFractionDigits)));
        }
        
        return roundImpl(BigDecimal.valueOf(number), 0, maxFractionDigits, roundingMode).doubleValue();
    }
    
    public static BigDecimal round(final BigDecimal number, final int minFractionDigits, final int maxFractionDigits,
            final RoundingMode roundingMode)
    {
        // Validating input.
        if (number == null) {
            throw new NullPointerException("number");
        }
        if (roundingMode == null) {
            throw new NullPointerException("roundingMode");
        }
        if (minFractionDigits < 0) {
            throw new IllegalArgumentException(MessageFormat.format(
                    "Negative minFractionDigits: {0}.", String.valueOf(minFractionDigits)));
        }
        if (maxFractionDigits < 0) {
            throw new IllegalArgumentException(MessageFormat.format(
                    "Negative maxFractionDigits: {0}.", String.valueOf(maxFractionDigits)));
        }
        if (minFractionDigits > maxFractionDigits) {
            throw new IllegalArgumentException(MessageFormat.format(
                    "minFractionDigits ({0}) is greater than maxFractionDigits ({1}).",
                    String.valueOf(minFractionDigits), String.valueOf(maxFractionDigits)));
        }
        
        return roundImpl(number, minFractionDigits, maxFractionDigits, roundingMode);
    }
    
    private static BigDecimal roundImpl(final BigDecimal number, final int minFractionDigits, final int maxFractionDigits,
            final RoundingMode mode)
    {
        final int scale = number.scale();
        if (scale < minFractionDigits) {
            return number.setScale(minFractionDigits, mode);
        }
        if (scale > maxFractionDigits) {
            return number.setScale(maxFractionDigits, mode);
        }
        return number;
    }
}
