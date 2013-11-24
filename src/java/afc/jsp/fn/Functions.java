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
package afc.jsp.fn;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;

import afc.util.RoundUtil;

/**
 * <p>Contains utility functions that are imported as JSP functions as a part of
 * the JAJ library.</p>
 * 
 * @author D&#378;mitry La&#365;&#269;uk
 */
public final class Functions
{
    private Functions()
    {
    }
    
    /**
     * <p>Rounds a given number according to the min/max fraction digit limits and rounding mode
     * specified. The following types of the input number are supported:</p>
     * <ul>
     *  <li>{@code null} &mdash; {@code null} is returned</li>
     *  <li>{@link BigDecimal} &mdash; its scale is adjusted if needed with the rounding mode
     *      specified to be not greater than max fraction digit limit and not less than min
     *      fraction digits. A {@code BigDecimal} is returned</li>
     *  <li>{@link Double} &mdash; same as for {@code BigDecimal} but a {@code Double} is
     *      returned</li>
     *  <li>{@link Float} &mdash; same as for {@code BigDecimal} but a {@code Float} is
     *      returned</li>
     *  <li>{@link Number} &mdash; converted to {@code BigDecimal} via its
     *      {@link Object#toString() string representation} and then rounded as if a
     *      {@code BigDecimal} was passed</li>
     *  <li>{@link String} &mdash; converted to {@code BigDecimal} and then rounded as if a
     *      {@code BigDecimal} was passed. However, if an empty string is passed then
     *      {@code null} is returned</li>
     * </ul>
     * 
     * <h3>Examples</h3>
     * <table border="1">
     * <thead>
     *  <tr><th>Number</th>
     *      <th>Number type</th>
     *      <th>Min fraction digits / max fraction digits / rounding mode</th>
     *      <th>Output number</th>
     *      <th>Output type</th></tr>
     * </thead>
     * <tbody>
     *  <tr><td>12344.325</td><td>Double</td><td>5 / 5 / DOWN</td><td>12344.325</td><td>Double</td></tr>
     *  <tr><td>12344.325</td><td>Double</td><td>0 / 2 / DOWN</td><td>12344.32</td><td>Double</td></tr>
     *  <tr><td>12344.325</td><td>Double</td><td>0 / 2 / UP</td><td>12344.33</td><td>Double</td></tr>
     *  <tr><td>12344.325</td><td>BigDecimal</td><td>5 / 5 / DOWN</td><td>12344.32500</td><td>BigDecimal</td></tr>
     *  <tr><td>12344.325</td><td>BigDecimal</td><td>0 / 2 / DOWN</td><td>12344.32</td><td>BigDecimal</td></tr>
     *  <tr><td>12344.32500001</td><td>String</td><td>0 / 2 / UP</td><td>12344.33</td><td>BigDecimal</td></tr>
     *  <tr><td>&quot;&quot; (empty string)</td><td>String</td><td>0 / 2 / UP</td><td>null</td><td>N/A</td></tr>
     *  <tr><td>null</td><td>N/A</td><td>0 / 2 / UP</td><td>null</td><td>N/A</td></tr>
     * </table>
     * 
     * @param number the number to be rounded.
     * @param minFractionDigits the minimal number of fraction digits that the rounded value must
     *      have. If the length of the fraction part of the number is less than this limit then
     *      its scale is expanded so that the length is equal to this value. It must be a
     *      non-negative value and be not greater than <em>maxFractionDigits</em>. This parameter
     *      is not applicable for {@code Double} and {@code Float} values in the sense that these
     *      values do not have scale.
     * @param maxFractionDigits the maximal number of fraction digits that the rounded value can
     *      have. If the length of the fraction part of the number exceeds this limit then it is
     *      truncated so that the length is equal to this value. It must be a non-negative value and
     *      be not less than <em>minFractionDigits</em>.
     * @param roundingMode the mode of this rounding operation. It must be non-{@code null}.
     * 
     * @return the rounded number. If <em>number</em> is {@code null} or an empty string then
     *      {@code null} is returned. If <em>number</em> is {@code Double} or {@code Float} then
     *      the {@code Double} or {@code Float} value is returned, respectively. In other cases
     *      the {@code BigDecimal} value is returned.
     * 
     * @throws NullPointerException if <em>roundingMode</em> is {@code null}.
     * @throws IllegalArgumentException in any of these cases:
     *      <ul>
     *          <li><em>number</em> is not {@code null} and not an instance of a supported type</li>
     *          <li><em>minFractionDigits</em> is negative</li>
     *          <li><em>maxFractionDigits</em> is negative</li>
     *          <li><em>minFractionDigits</em> is greater than <em>maxFractionDigits</em></li>
     *      </ul>
     * @throws ArithmeticException if the result is inexact but the rounding mode is
     *      {@link RoundingMode#UNNECESSARY UNNECESSARY}.
     * @throws NumberFormatException if <em>number</em> is a non-empty string that contains
     *      a non-number value.
     */
    public static Object round(final Object number, final int minFractionDigits, final int maxFractionDigits,
            final RoundingMode roundingMode)
    {
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
            final String str = (String) number;
            if (str.length() == 0) {
                return null;
            }
            return RoundUtil.round(new BigDecimal(str), minFractionDigits, maxFractionDigits, roundingMode);
        }
        throw new IllegalArgumentException(MessageFormat.format("Unsupported number type: ''{0}''.", number.getClass().getName()));
    }
}
