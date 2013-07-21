package afc.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import junit.framework.TestCase;

public class RoundUtilTest extends TestCase
{
    public void testRoundBigDecimal_Zero()
    {
        assertEquals(bd("0"), RoundUtil.round(BigDecimal.ZERO, 0, 0, RoundingMode.UNNECESSARY));
        assertEquals(bd("0.000"), RoundUtil.round(BigDecimal.ZERO, 3, 5, RoundingMode.UNNECESSARY));
        assertEquals(bd("0.00000"), RoundUtil.round(BigDecimal.ZERO, 5, 5, RoundingMode.UNNECESSARY));
    }
    
    public void testRoundBigDecimal_IntegerValue()
    {
        assertEquals(bd("12344"), RoundUtil.round(bd("12344"), 0, 0, RoundingMode.UNNECESSARY));
        assertEquals(bd("12344.000"), RoundUtil.round(bd("12344"), 3, 5, RoundingMode.UNNECESSARY));
        assertEquals(bd("12344.00000"), RoundUtil.round(bd("12344"), 5, 5, RoundingMode.UNNECESSARY));
    }
    
    public void testRoundBigDecimal_IntegerWithTrailingZeroes()
    {
        assertEquals(bd("123440000"), RoundUtil.round(bd("123440000"), 0, 0, RoundingMode.UNNECESSARY));
        assertEquals(bd("123440000.000"), RoundUtil.round(bd("123440000"), 3, 5, RoundingMode.UNNECESSARY));
        assertEquals(bd("123440000.00000"), RoundUtil.round(bd("123440000"), 5, 5, RoundingMode.UNNECESSARY));
    }
    
    public void testRoundBigDecimal_ScaledIntegerValue()
    {
        BigDecimal number = new BigDecimal(123);
        number = number.setScale(10);
        assertEquals(bd("123"), RoundUtil.round(number, 0, 0, RoundingMode.UNNECESSARY));
        assertEquals(bd("123.00000"), RoundUtil.round(number, 3, 5, RoundingMode.UNNECESSARY));
        assertEquals(bd("123.00000"), RoundUtil.round(number, 5, 5, RoundingMode.UNNECESSARY));
    }
    
    public void testRoundBigDecimal_DecimalValue_ExactRounding()
    {
        assertEquals(bd("12344.32"), RoundUtil.round(bd("12344.32"), 2, 2, RoundingMode.UNNECESSARY));
        assertEquals(bd("12344.320"), RoundUtil.round(bd("12344.32"), 3, 5, RoundingMode.UNNECESSARY));
        assertEquals(bd("12344.32000"), RoundUtil.round(bd("12344.32"), 5, 5, RoundingMode.UNNECESSARY));
    }
    
    public void testRoundBigDecimal_DecimalValues_RoundingUp()
    {
        assertEquals(bd("12345"), RoundUtil.round(bd("12344.325"), 0, 0, RoundingMode.UP));
        assertEquals(bd("12344.4"), RoundUtil.round(bd("12344.325"), 0, 1, RoundingMode.UP));
        assertEquals(bd("12344.33"), RoundUtil.round(bd("12344.325"), 0, 2, RoundingMode.UP));
        assertEquals(bd("12344.4"), RoundUtil.round(bd("12344.325"), 1, 1, RoundingMode.UP));
        assertEquals(bd("12344.33"), RoundUtil.round(bd("12344.325"), 1, 2, RoundingMode.UP));
        assertEquals(bd("12344.33"), RoundUtil.round(bd("12344.325"), 2, 2, RoundingMode.UP));
        assertEquals(bd("12344.325"), RoundUtil.round(bd("12344.325"), 1, 3, RoundingMode.UP));
        assertEquals(bd("12344.325"), RoundUtil.round(bd("12344.325"), 3, 5, RoundingMode.UP));
        assertEquals(bd("12344.32500"), RoundUtil.round(bd("12344.325"), 5, 5, RoundingMode.UP));
    }
    
    public void testRoundBigDecimal_DecimalValues_RoundingDown()
    {
        assertEquals(bd("12344"), RoundUtil.round(bd("12344.325"), 0, 0, RoundingMode.DOWN));
        assertEquals(bd("12344.3"), RoundUtil.round(bd("12344.325"), 0, 1, RoundingMode.DOWN));
        assertEquals(bd("12344.32"), RoundUtil.round(bd("12344.325"), 0, 2, RoundingMode.DOWN));
        assertEquals(bd("12344.3"), RoundUtil.round(bd("12344.325"), 1, 1, RoundingMode.DOWN));
        assertEquals(bd("12344.32"), RoundUtil.round(bd("12344.325"), 1, 2, RoundingMode.DOWN));
        assertEquals(bd("12344.32"), RoundUtil.round(bd("12344.325"), 2, 2, RoundingMode.DOWN));
        assertEquals(bd("12344.325"), RoundUtil.round(bd("12344.325"), 1, 3, RoundingMode.DOWN));
        assertEquals(bd("12344.325"), RoundUtil.round(bd("12344.325"), 3, 5, RoundingMode.DOWN));
        assertEquals(bd("12344.32500"), RoundUtil.round(bd("12344.325"), 5, 5, RoundingMode.DOWN));
    }
    
    public void testRoundBigDecimal_DecimalValues_RoundingHalfUp()
    {
        assertEquals(bd("12344"), RoundUtil.round(bd("12344.325"), 0, 0, RoundingMode.HALF_UP));
        assertEquals(bd("12344.3"), RoundUtil.round(bd("12344.325"), 0, 1, RoundingMode.HALF_UP));
        assertEquals(bd("12344.33"), RoundUtil.round(bd("12344.325"), 0, 2, RoundingMode.HALF_UP));
        assertEquals(bd("12344.3"), RoundUtil.round(bd("12344.325"), 1, 1, RoundingMode.HALF_UP));
        assertEquals(bd("12344.33"), RoundUtil.round(bd("12344.325"), 1, 2, RoundingMode.HALF_UP));
        assertEquals(bd("12344.33"), RoundUtil.round(bd("12344.325"), 2, 2, RoundingMode.HALF_UP));
        assertEquals(bd("12344.325"), RoundUtil.round(bd("12344.325"), 1, 3, RoundingMode.HALF_UP));
        assertEquals(bd("12344.325"), RoundUtil.round(bd("12344.325"), 3, 5, RoundingMode.HALF_UP));
        assertEquals(bd("12344.32500"), RoundUtil.round(bd("12344.325"), 5, 5, RoundingMode.HALF_UP));
    }
    
    public void testRoundBigDecimal_NegativeMinFractionDigits()
    {
        try {
            RoundUtil.round(bd("12344.325"), -1, 0, RoundingMode.HALF_UP);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Negative minFractionDigits: -1.", ex.getMessage());
        }
    }
    
    public void testRoundBigDecimal_LargeNegativeMinFractionDigits()
    {
        try {
            RoundUtil.round(bd("12344.325"), -10000, 0, RoundingMode.HALF_UP);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Negative minFractionDigits: -10000.", ex.getMessage());
        }
    }
    
    public void testRoundBigDecimal_NegativeMaxFractionDigits()
    {
        try {
            RoundUtil.round(bd("12344.325"), 0, -1, RoundingMode.HALF_UP);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Negative maxFractionDigits: -1.", ex.getMessage());
        }
    }
    
    public void testRoundBigDecimal_LargeNegativeMaxFractionDigits()
    {
        try {
            RoundUtil.round(bd("12344.325"), 0, -10000, RoundingMode.HALF_UP);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Negative maxFractionDigits: -10000.", ex.getMessage());
        }
    }
    
    public void testRoundBigDecimal_MinFractionDigitsIsGreaterThenMaxFractionDigits_LargeValues()
    {
        try {
            RoundUtil.round(bd("12344.325"), 2001, 2000, RoundingMode.HALF_UP);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("minFractionDigits (2001) is greater than maxFractionDigits (2000).", ex.getMessage());
        }
    }
    
    public void testRoundBigDecimal_NullNumber()
    {
        try {
            RoundUtil.round(null, 2, 3, RoundingMode.HALF_UP);
            fail();
        }
        catch (NullPointerException ex) {
            assertEquals("number", ex.getMessage());
        }
    }
    
    public void testRoundBigDecimal_NullRoundingMode()
    {
        try {
            RoundUtil.round(bd("12344.3"), 2, 3, null);
            fail();
        }
        catch (NullPointerException ex) {
            assertEquals("roundingMode", ex.getMessage());
        }
    }
    
    public void testRoundDouble_Zero()
    {
        assertEquals(0d, RoundUtil.round(0d, 0, 0, RoundingMode.UNNECESSARY));
        assertEquals(0d, RoundUtil.round(0d, 3, 5, RoundingMode.UNNECESSARY));
        assertEquals(0d, RoundUtil.round(0d, 5, 5, RoundingMode.UNNECESSARY));
    }
    
    public void testRoundDouble_IntegerValue()
    {
        assertEquals(12344d, RoundUtil.round(12344d, 0, 0, RoundingMode.UNNECESSARY));
        assertEquals(12344d, RoundUtil.round(12344d, 3, 5, RoundingMode.UNNECESSARY));
        assertEquals(12344d, RoundUtil.round(12344d, 5, 5, RoundingMode.UNNECESSARY));
    }
    
    public void testRoundDouble_IntegerWithTrailingZeroes()
    {
        assertEquals(123440000d, RoundUtil.round(123440000d, 0, 0, RoundingMode.UNNECESSARY));
        assertEquals(123440000d, RoundUtil.round(123440000d, 3, 5, RoundingMode.UNNECESSARY));
        assertEquals(123440000d, RoundUtil.round(123440000d, 5, 5, RoundingMode.UNNECESSARY));
    }
    
    public void testRoundDouble_DecimalValue_ExactRounding()
    {
        assertEquals(12344.32d, RoundUtil.round(12344.32, 2, 2, RoundingMode.UNNECESSARY));
        assertEquals(12344.32d, RoundUtil.round(12344.32, 3, 5, RoundingMode.UNNECESSARY));
        assertEquals(12344.32d, RoundUtil.round(12344.32d, 5, 5, RoundingMode.UNNECESSARY));
    }
    
    public void testRoundDouble_DecimalValues_RoundingUp()
    {
        assertEquals(12345d, RoundUtil.round(12344.325d, 0, 0, RoundingMode.UP));
        assertEquals(12344.4d, RoundUtil.round(12344.325d, 0, 1, RoundingMode.UP));
        assertEquals(12344.33d, RoundUtil.round(12344.325d, 0, 2, RoundingMode.UP));
        assertEquals(12344.4d, RoundUtil.round(12344.325d, 1, 1, RoundingMode.UP));
        assertEquals(12344.33d, RoundUtil.round(12344.325d, 1, 2, RoundingMode.UP));
        assertEquals(12344.33d, RoundUtil.round(12344.325d, 2, 2, RoundingMode.UP));
        assertEquals(12344.325d, RoundUtil.round(12344.325d, 1, 3, RoundingMode.UP));
        assertEquals(12344.325d, RoundUtil.round(12344.325d, 3, 5, RoundingMode.UP));
        assertEquals(12344.325d, RoundUtil.round(12344.325d, 5, 5, RoundingMode.UP));
    }
    
    public void testRoundDouble_DecimalValues_RoundingDown()
    {
        assertEquals(12344d, RoundUtil.round(12344.325d, 0, 0, RoundingMode.DOWN));
        assertEquals(12344.3d, RoundUtil.round(12344.325d, 0, 1, RoundingMode.DOWN));
        assertEquals(12344.32d, RoundUtil.round(12344.325d, 0, 2, RoundingMode.DOWN));
        assertEquals(12344.3d, RoundUtil.round(12344.325d, 1, 1, RoundingMode.DOWN));
        assertEquals(12344.32d, RoundUtil.round(12344.325d, 1, 2, RoundingMode.DOWN));
        assertEquals(12344.32d, RoundUtil.round(12344.325d, 2, 2, RoundingMode.DOWN));
        assertEquals(12344.325d, RoundUtil.round(12344.325d, 1, 3, RoundingMode.DOWN));
        assertEquals(12344.325d, RoundUtil.round(12344.325d, 3, 5, RoundingMode.DOWN));
        assertEquals(12344.325d, RoundUtil.round(12344.325d, 5, 5, RoundingMode.DOWN));
    }
    
    public void testRoundDouble_DecimalValues_RoundingHalfUp()
    {
        assertEquals(12344d, RoundUtil.round(12344.325d, 0, 0, RoundingMode.HALF_UP));
        assertEquals(12344.3d, RoundUtil.round(12344.325d, 0, 1, RoundingMode.HALF_UP));
        assertEquals(12344.33d, RoundUtil.round(12344.325d, 0, 2, RoundingMode.HALF_UP));
        assertEquals(12344.3d, RoundUtil.round(12344.325d, 1, 1, RoundingMode.HALF_UP));
        assertEquals(12344.33d, RoundUtil.round(12344.325d, 1, 2, RoundingMode.HALF_UP));
        assertEquals(12344.33d, RoundUtil.round(12344.325d, 2, 2, RoundingMode.HALF_UP));
        assertEquals(12344.325d, RoundUtil.round(12344.325d, 1, 3, RoundingMode.HALF_UP));
        assertEquals(12344.325d, RoundUtil.round(12344.325d, 3, 5, RoundingMode.HALF_UP));
        assertEquals(12344.325d, RoundUtil.round(12344.325d, 5, 5, RoundingMode.HALF_UP));
    }
    
    public void testRoundDouble_NegativeMinFractionDigits()
    {
        try {
            RoundUtil.round(12344.325d, -1, 0, RoundingMode.HALF_UP);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Negative minFractionDigits: -1.", ex.getMessage());
        }
    }
    
    public void testRoundDouble_LargeNegativeMinFractionDigits()
    {
        try {
            RoundUtil.round(12344.325d, -10000, 0, RoundingMode.HALF_UP);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Negative minFractionDigits: -10000.", ex.getMessage());
        }
    }
    
    public void testRoundDouble_NegativeMaxFractionDigits()
    {
        try {
            RoundUtil.round(12344.325d, 0, -1, RoundingMode.HALF_UP);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Negative maxFractionDigits: -1.", ex.getMessage());
        }
    }
    
    public void testRoundDouble_LargeNegativeMaxFractionDigits()
    {
        try {
            RoundUtil.round(12344.325d, 0, -10000, RoundingMode.HALF_UP);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Negative maxFractionDigits: -10000.", ex.getMessage());
        }
    }
    
    public void testRoundDouble_MinFractionDigitsIsGreaterThenMaxFractionDigits_LargeValues()
    {
        try {
            RoundUtil.round(12344.325d, 2001, 2000, RoundingMode.HALF_UP);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("minFractionDigits (2001) is greater than maxFractionDigits (2000).", ex.getMessage());
        }
    }
    
    public void testRoundDouble_NullRoundingMode()
    {
        try {
            RoundUtil.round(12344.3d, 2, 3, null);
            fail();
        }
        catch (NullPointerException ex) {
            assertEquals("roundingMode", ex.getMessage());
        }
    }
    
    private static BigDecimal bd(final String value)
    {
        return new BigDecimal(value);
    }
}
