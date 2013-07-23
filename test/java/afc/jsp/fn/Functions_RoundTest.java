package afc.jsp.fn;

import java.math.BigDecimal;
import java.math.RoundingMode;

import junit.framework.TestCase;

public class Functions_RoundTest extends TestCase
{
    public void testRound_NullValue()
    {
        assertEquals(null, Functions.round(null, 0, 0, RoundingMode.CEILING));
        assertEquals(null, Functions.round(null, 0, 1, RoundingMode.UNNECESSARY));
        assertEquals(null, Functions.round(null, 2, 3, RoundingMode.HALF_DOWN));
        assertEquals(null, Functions.round(null, 4, 4, RoundingMode.HALF_UP));
    }
    
    public void testRound_NullValueAndNegativeMinFractionDigits()
    {
        try {
            Functions.round(null, -1, 0, RoundingMode.HALF_UP);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Negative minFractionDigits: -1.", ex.getMessage());
        }
    }
    
    public void testRound_NullValueAndNegativeMaxFractionDigits()
    {
        try {
            Functions.round(null, 0, -1, RoundingMode.HALF_UP);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Negative maxFractionDigits: -1.", ex.getMessage());
        }
    }
    
    public void testRound_NullValueAndMinFractionDigitsIsGreaterThenMaxFractionDigits()
    {
        try {
            Functions.round(null, 2, 1, RoundingMode.HALF_UP);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("minFractionDigits (2) is greater than maxFractionDigits (1).", ex.getMessage());
        }
    }
    
    public void testRound_NullValueAndNullRoundingMode()
    {
        try {
            Functions.round(null, 2, 3, null);
            fail();
        }
        catch (NullPointerException ex) {
            assertEquals("roundingMode", ex.getMessage());
        }
    }
    
    public void testRoundBigDecimal_Zero()
    {
        assertEquals(bd("0"), Functions.round(BigDecimal.ZERO, 0, 0, RoundingMode.UNNECESSARY));
        assertEquals(bd("0.000"), Functions.round(BigDecimal.ZERO, 3, 5, RoundingMode.UNNECESSARY));
        assertEquals(bd("0.00000"), Functions.round(BigDecimal.ZERO, 5, 5, RoundingMode.UNNECESSARY));
    }
    
    public void testRoundBigDecimal_IntegerValue()
    {
        assertEquals(bd("12344"), Functions.round(bd("12344"), 0, 0, RoundingMode.UNNECESSARY));
        assertEquals(bd("12344.000"), Functions.round(bd("12344"), 3, 5, RoundingMode.UNNECESSARY));
        assertEquals(bd("12344.00000"), Functions.round(bd("12344"), 5, 5, RoundingMode.UNNECESSARY));
    }
    
    public void testRoundBigDecimal_DecimalValue_ExactRounding()
    {
        assertEquals(bd("12344.32"), Functions.round(bd("12344.32"), 2, 2, RoundingMode.UNNECESSARY));
        assertEquals(bd("12344.320"), Functions.round(bd("12344.32"), 3, 5, RoundingMode.UNNECESSARY));
        assertEquals(bd("12344.32000"), Functions.round(bd("12344.32"), 5, 5, RoundingMode.UNNECESSARY));
    }
    
    public void testRoundBigDecimal_DecimalValues_RoundingUp()
    {
        assertEquals(bd("12345"), Functions.round(bd("12344.325"), 0, 0, RoundingMode.UP));
        assertEquals(bd("12344.4"), Functions.round(bd("12344.325"), 0, 1, RoundingMode.UP));
        assertEquals(bd("12344.33"), Functions.round(bd("12344.325"), 0, 2, RoundingMode.UP));
        assertEquals(bd("12344.4"), Functions.round(bd("12344.325"), 1, 1, RoundingMode.UP));
        assertEquals(bd("12344.33"), Functions.round(bd("12344.325"), 1, 2, RoundingMode.UP));
        assertEquals(bd("12344.33"), Functions.round(bd("12344.325"), 2, 2, RoundingMode.UP));
        assertEquals(bd("12344.325"), Functions.round(bd("12344.325"), 1, 3, RoundingMode.UP));
        assertEquals(bd("12344.325"), Functions.round(bd("12344.325"), 3, 5, RoundingMode.UP));
        assertEquals(bd("12344.32500"), Functions.round(bd("12344.325"), 5, 5, RoundingMode.UP));
    }
    
    public void testRoundBigDecimal_DecimalValues_RoundingDown()
    {
        assertEquals(bd("12344"), Functions.round(bd("12344.325"), 0, 0, RoundingMode.DOWN));
        assertEquals(bd("12344.3"), Functions.round(bd("12344.325"), 0, 1, RoundingMode.DOWN));
        assertEquals(bd("12344.32"), Functions.round(bd("12344.325"), 0, 2, RoundingMode.DOWN));
        assertEquals(bd("12344.3"), Functions.round(bd("12344.325"), 1, 1, RoundingMode.DOWN));
        assertEquals(bd("12344.32"), Functions.round(bd("12344.325"), 1, 2, RoundingMode.DOWN));
        assertEquals(bd("12344.32"), Functions.round(bd("12344.325"), 2, 2, RoundingMode.DOWN));
        assertEquals(bd("12344.325"), Functions.round(bd("12344.325"), 1, 3, RoundingMode.DOWN));
        assertEquals(bd("12344.325"), Functions.round(bd("12344.325"), 3, 5, RoundingMode.DOWN));
        assertEquals(bd("12344.32500"), Functions.round(bd("12344.325"), 5, 5, RoundingMode.DOWN));
    }
    
    public void testRoundBigDecimal_DecimalValues_RoundingHalfUp()
    {
        assertEquals(bd("12344"), Functions.round(bd("12344.325"), 0, 0, RoundingMode.HALF_UP));
        assertEquals(bd("12344.3"), Functions.round(bd("12344.325"), 0, 1, RoundingMode.HALF_UP));
        assertEquals(bd("12344.33"), Functions.round(bd("12344.325"), 0, 2, RoundingMode.HALF_UP));
        assertEquals(bd("12344.3"), Functions.round(bd("12344.325"), 1, 1, RoundingMode.HALF_UP));
        assertEquals(bd("12344.33"), Functions.round(bd("12344.325"), 1, 2, RoundingMode.HALF_UP));
        assertEquals(bd("12344.33"), Functions.round(bd("12344.325"), 2, 2, RoundingMode.HALF_UP));
        assertEquals(bd("12344.325"), Functions.round(bd("12344.325"), 1, 3, RoundingMode.HALF_UP));
        assertEquals(bd("12344.325"), Functions.round(bd("12344.325"), 3, 5, RoundingMode.HALF_UP));
        assertEquals(bd("12344.32500"), Functions.round(bd("12344.325"), 5, 5, RoundingMode.HALF_UP));
    }
    
    public void testRoundBigDecimal_NegativeMinFractionDigits()
    {
        try {
            Functions.round(bd("12344.325"), -1, 0, RoundingMode.HALF_UP);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Negative minFractionDigits: -1.", ex.getMessage());
        }
    }
    
    public void testRoundBigDecimal_NegativeMaxFractionDigits()
    {
        try {
            Functions.round(bd("12344.325"), 0, -1, RoundingMode.HALF_UP);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Negative maxFractionDigits: -1.", ex.getMessage());
        }
    }
    
    public void testRoundBigDecimal_MinFractionDigitsIsGreaterThenMaxFractionDigits()
    {
        try {
            Functions.round(bd("12344.325"), 2001, 2000, RoundingMode.HALF_UP);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("minFractionDigits (2001) is greater than maxFractionDigits (2000).", ex.getMessage());
        }
    }
    
    public void testRoundBigDecimal_NullRoundingMode()
    {
        try {
            Functions.round(bd("12344.3"), 2, 3, null);
            fail();
        }
        catch (NullPointerException ex) {
            assertEquals("roundingMode", ex.getMessage());
        }
    }
    
    public void testRoundDouble_Zero()
    {
        assertEquals(Double.valueOf(0d), Functions.round(0d, 0, 0, RoundingMode.UNNECESSARY));
        assertEquals(Double.valueOf(0d), Functions.round(0d, 3, 5, RoundingMode.UNNECESSARY));
        assertEquals(Double.valueOf(0d), Functions.round(0d, 5, 5, RoundingMode.UNNECESSARY));
    }
    
    public void testRoundDouble_IntegerValue()
    {
        assertEquals(Double.valueOf(12344d), Functions.round(12344d, 0, 0, RoundingMode.UNNECESSARY));
        assertEquals(Double.valueOf(12344d), Functions.round(12344d, 3, 5, RoundingMode.UNNECESSARY));
        assertEquals(Double.valueOf(12344d), Functions.round(12344d, 5, 5, RoundingMode.UNNECESSARY));
    }
    
    public void testRoundDouble_DecimalValue_ExactRounding()
    {
        assertEquals(Double.valueOf(12344.32d), Functions.round(12344.32d, 2, 2, RoundingMode.UNNECESSARY));
        assertEquals(Double.valueOf(12344.32d), Functions.round(12344.32d, 3, 5, RoundingMode.UNNECESSARY));
        assertEquals(Double.valueOf(12344.32d), Functions.round(12344.32d, 5, 5, RoundingMode.UNNECESSARY));
    }
    
    public void testRoundDouble_DecimalValues_RoundingUp()
    {
        assertEquals(Double.valueOf(12345d), Functions.round(12344.325d, 0, 0, RoundingMode.UP));
        assertEquals(Double.valueOf(12344.4d), Functions.round(12344.325d, 0, 1, RoundingMode.UP));
        assertEquals(Double.valueOf(12344.33d), Functions.round(12344.325d, 0, 2, RoundingMode.UP));
        assertEquals(Double.valueOf(12344.4d), Functions.round(12344.325d, 1, 1, RoundingMode.UP));
        assertEquals(Double.valueOf(12344.33d), Functions.round(12344.325d, 2, 2, RoundingMode.UP));
        assertEquals(Double.valueOf(-12344.33d), Functions.round(-12344.325d, 2, 2, RoundingMode.UP));
        assertEquals(Double.valueOf(12344.325d), Functions.round(12344.325d, 1, 3, RoundingMode.UP));
        assertEquals(Double.valueOf(12344.325d), Functions.round(12344.325d, 3, 5, RoundingMode.UP));
        assertEquals(Double.valueOf(12344.325d), Functions.round(12344.325d, 5, 5, RoundingMode.UP));
    }
    
    public void testRoundDouble_DecimalValues_RoundingDown()
    {
        assertEquals(Double.valueOf(12344d), Functions.round(12344.325d, 0, 0, RoundingMode.DOWN));
        assertEquals(Double.valueOf(12344.3d), Functions.round(12344.325d, 0, 1, RoundingMode.DOWN));
        assertEquals(Double.valueOf(12344.32d), Functions.round(12344.325d, 0, 2, RoundingMode.DOWN));
        assertEquals(Double.valueOf(12344.3d), Functions.round(12344.325d, 1, 1, RoundingMode.DOWN));
        assertEquals(Double.valueOf(12344.32d), Functions.round(12344.325d, 2, 2, RoundingMode.DOWN));
        assertEquals(Double.valueOf(-12344.32d), Functions.round(-12344.325d, 2, 2, RoundingMode.DOWN));
        assertEquals(Double.valueOf(12344.325d), Functions.round(12344.325d, 1, 3, RoundingMode.DOWN));
        assertEquals(Double.valueOf(12344.325d), Functions.round(12344.325d, 3, 5, RoundingMode.DOWN));
        assertEquals(Double.valueOf(12344.325d), Functions.round(12344.325d, 5, 5, RoundingMode.DOWN));
    }
    
    public void testRoundDouble_DecimalValues_RoundingHalfUp()
    {
        assertEquals(Double.valueOf(12344d), Functions.round(12344.325d, 0, 0, RoundingMode.HALF_UP));
        assertEquals(Double.valueOf(12344.3d), Functions.round(12344.325d, 0, 1, RoundingMode.HALF_UP));
        assertEquals(Double.valueOf(12344.33d), Functions.round(12344.325d, 0, 2, RoundingMode.HALF_UP));
        assertEquals(Double.valueOf(12344.3d), Functions.round(12344.325d, 1, 1, RoundingMode.HALF_UP));
        assertEquals(Double.valueOf(12344.33d), Functions.round(12344.325d, 2, 2, RoundingMode.HALF_UP));
        assertEquals(Double.valueOf(-12344.33d), Functions.round(-12344.325d, 2, 2, RoundingMode.HALF_UP));
        assertEquals(Double.valueOf(12344.325d), Functions.round(12344.325d, 1, 3, RoundingMode.HALF_UP));
        assertEquals(Double.valueOf(12344.325d), Functions.round(12344.325d, 3, 5, RoundingMode.HALF_UP));
        assertEquals(Double.valueOf(12344.325d), Functions.round(12344.325d, 5, 5, RoundingMode.HALF_UP));
    }
    
    public void testRoundDouble_NegativeMinFractionDigits()
    {
        try {
            Functions.round(12344.325d, -1, 0, RoundingMode.HALF_UP);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Negative minFractionDigits: -1.", ex.getMessage());
        }
    }
    
    public void testRoundDouble_NegativeMaxFractionDigits()
    {
        try {
            Functions.round(12344.325d, 0, -1, RoundingMode.HALF_UP);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Negative maxFractionDigits: -1.", ex.getMessage());
        }
    }
    
    public void testRoundDouble_MinFractionDigitsIsGreaterThenMaxFractionDigits()
    {
        try {
            Functions.round(12344.325d, 2001, 2000, RoundingMode.HALF_UP);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("minFractionDigits (2001) is greater than maxFractionDigits (2000).", ex.getMessage());
        }
    }
    
    public void testRoundDouble_NullRoundingMode()
    {
        try {
            Functions.round(12344.3d, 2, 3, null);
            fail();
        }
        catch (NullPointerException ex) {
            assertEquals("roundingMode", ex.getMessage());
        }
    }
    
    public void testRoundFloat_Zero()
    {
        assertEquals(Float.valueOf(0f), Functions.round(0f, 0, 0, RoundingMode.UNNECESSARY));
        assertEquals(Float.valueOf(0f), Functions.round(0f, 3, 5, RoundingMode.UNNECESSARY));
        assertEquals(Float.valueOf(0f), Functions.round(0f, 5, 5, RoundingMode.UNNECESSARY));
    }
    
    public void testRoundFloat_IntegerValue()
    {
        assertEquals(Float.valueOf(12344f), Functions.round(12344f, 0, 0, RoundingMode.UNNECESSARY));
        assertEquals(Float.valueOf(12344f), Functions.round(12344f, 3, 5, RoundingMode.UNNECESSARY));
        assertEquals(Float.valueOf(12344f), Functions.round(12344f, 5, 5, RoundingMode.UNNECESSARY));
    }
    
    public void testRoundFloat_DecimalValue_ExactRounding()
    {
        assertEquals(Float.valueOf(12344.32f), Functions.round(12344.32f, 2, 2, RoundingMode.UNNECESSARY));
        assertEquals(Float.valueOf(12344.32f), Functions.round(12344.32f, 3, 5, RoundingMode.UNNECESSARY));
        assertEquals(Float.valueOf(12344.32f), Functions.round(12344.32f, 5, 5, RoundingMode.UNNECESSARY));
    }
    
    public void testRoundFloat_DecimalValues_RoundingUp()
    {
        assertEquals(Float.valueOf(12345f), Functions.round(12344.325f, 0, 0, RoundingMode.UP));
        assertEquals(Float.valueOf(12344.4f), Functions.round(12344.325f, 0, 1, RoundingMode.UP));
        assertEquals(Float.valueOf(12344.33f), Functions.round(12344.325f, 0, 2, RoundingMode.UP));
        assertEquals(Float.valueOf(12344.4f), Functions.round(12344.325f, 1, 1, RoundingMode.UP));
        assertEquals(Float.valueOf(12344.33f), Functions.round(12344.325f, 2, 2, RoundingMode.UP));
        assertEquals(Float.valueOf(-12344.33f), Functions.round(-12344.325f, 2, 2, RoundingMode.UP));
        assertEquals(Float.valueOf(12344.325f), Functions.round(12344.325f, 1, 3, RoundingMode.UP));
        assertEquals(Float.valueOf(12344.325f), Functions.round(12344.325f, 3, 5, RoundingMode.UP));
        assertEquals(Float.valueOf(12344.325f), Functions.round(12344.325f, 5, 5, RoundingMode.UP));
    }
    
    public void testRoundFloat_DecimalValues_RoundingDown()
    {
        assertEquals(Float.valueOf(12344f), Functions.round(12344.325f, 0, 0, RoundingMode.DOWN));
        assertEquals(Float.valueOf(12344.3f), Functions.round(12344.325f, 0, 1, RoundingMode.DOWN));
        assertEquals(Float.valueOf(12344.32f), Functions.round(12344.325f, 0, 2, RoundingMode.DOWN));
        assertEquals(Float.valueOf(12344.3f), Functions.round(12344.325f, 1, 1, RoundingMode.DOWN));
        assertEquals(Float.valueOf(12344.32f), Functions.round(12344.325f, 2, 2, RoundingMode.DOWN));
        assertEquals(Float.valueOf(-12344.32f), Functions.round(-12344.325f, 2, 2, RoundingMode.DOWN));
        assertEquals(Float.valueOf(12344.325f), Functions.round(12344.325f, 1, 3, RoundingMode.DOWN));
        assertEquals(Float.valueOf(12344.325f), Functions.round(12344.325f, 3, 5, RoundingMode.DOWN));
        assertEquals(Float.valueOf(12344.325f), Functions.round(12344.325f, 5, 5, RoundingMode.DOWN));
    }
    
    public void testRoundFloat_DecimalValues_RoundingHalfUp()
    {
        assertEquals(Float.valueOf(12344f), Functions.round(12344.325f, 0, 0, RoundingMode.HALF_UP));
        assertEquals(Float.valueOf(12344.3f), Functions.round(12344.325f, 0, 1, RoundingMode.HALF_UP));
        assertEquals(Float.valueOf(12344.33f), Functions.round(12344.325f, 0, 2, RoundingMode.HALF_UP));
        assertEquals(Float.valueOf(12344.3f), Functions.round(12344.325f, 1, 1, RoundingMode.HALF_UP));
        assertEquals(Float.valueOf(12344.33f), Functions.round(12344.325f, 2, 2, RoundingMode.HALF_UP));
        assertEquals(Float.valueOf(-12344.33f), Functions.round(-12344.325f, 2, 2, RoundingMode.HALF_UP));
        assertEquals(Float.valueOf(12344.325f), Functions.round(12344.325f, 1, 3, RoundingMode.HALF_UP));
        assertEquals(Float.valueOf(12344.325f), Functions.round(12344.325f, 3, 5, RoundingMode.HALF_UP));
        assertEquals(Float.valueOf(12344.325f), Functions.round(12344.325f, 5, 5, RoundingMode.HALF_UP));
    }
    
    public void testRoundFloat_NegativeMinFractionDigits()
    {
        try {
            Functions.round(12344.325f, -1, 0, RoundingMode.HALF_UP);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Negative minFractionDigits: -1.", ex.getMessage());
        }
    }
    
    public void testRoundFloat_NegativeMaxFractionDigits()
    {
        try {
            Functions.round(12344.325f, 0, -1, RoundingMode.HALF_UP);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Negative maxFractionDigits: -1.", ex.getMessage());
        }
    }
    
    public void testRoundFloat_MinFractionDigitsIsGreaterThenMaxFractionDigits()
    {
        try {
            Functions.round(12344.325f, 2001, 2000, RoundingMode.HALF_UP);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("minFractionDigits (2001) is greater than maxFractionDigits (2000).", ex.getMessage());
        }
    }
    
    public void testRoundFloat_NullRoundingMode()
    {
        try {
            Functions.round(12344.3f, 2, 3, null);
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
