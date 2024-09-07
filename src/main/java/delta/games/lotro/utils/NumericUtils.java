package delta.games.lotro.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.NumericTools;

/**
 * Utility methods related to numbers management.
 * @author DAM
 */
public class NumericUtils
{
  private static final Logger LOGGER=LoggerFactory.getLogger(NumericUtils.class);

  /**
   * Ensure that the given number is positive. Build and return another
   * one if needed.
   * @param source Input number.
   * @return A new number, or the same one if positive.
   */
  public static Number ensurePositive(Number source)
  {
    if (source==null)
    {
      return null;
    }
    if (source instanceof Integer)
    {
      int value=((Integer)source).intValue();
      if (value<0)
      {
        return Integer.valueOf(-value);
      }
      return source;
    }
    if (source instanceof Float)
    {
      float value=((Float)source).floatValue();
      if (value<0)
      {
        return Float.valueOf(-value);
      }
      return source;
    }
    LOGGER.warn("Unmanaged number in ensurePositive: "+source);
    return source;
  }

  /**
   * Ensure that the given number is positive. Build and return another
   * one if needed.
   * @param source Input number.
   * @return A new number, or the same one if positive.
   */
  public static Number negate(Number source)
  {
    if (source==null)
    {
      return null;
    }
    if (source instanceof Integer)
    {
      int value=((Integer)source).intValue();
      return Integer.valueOf(-value);
    }
    if (source instanceof Float)
    {
      float value=((Float)source).floatValue();
      return Float.valueOf(-value);
    }
    LOGGER.warn("Unmanaged number in negate: "+source);
    return source;
  }

  /**
   * Add 2 numbers.
   * Keep number type if possible:
   * <ul>
   * <li>2 integers will give an integer,
   * <li>if at least one float, then it gives a float.
   * </ul>
   * @param o1 Input number 1.
   * @param o2 Input number 2.
   * @return the result number.
   */
  public static Number add(Number o1, Number o2)
  {
    if ((o1 instanceof Integer) && (o2 instanceof Integer))
    {
      int newValue=((Integer)o1).intValue()+((Integer)o2).intValue();
      return Integer.valueOf(newValue);
    }
    // Use float
    float newValue=o1.floatValue()+o2.floatValue();
    return Float.valueOf(newValue);
  }

  /**
   * Substract/diff 2 numbers.
   * Keep number type if possible:
   * <ul>
   * <li>2 integers will give an integer,
   * <li>if at least one float, then it gives a float.
   * </ul>
   * @param o1 Input number 1.
   * @param o2 Input number 2.
   * @return the result number.
   */
  public static Number diff(Number o1, Number o2)
  {
    if ((o1 instanceof Integer) && (o2 instanceof Integer))
    {
      int newValue=((Integer)o1).intValue()-((Integer)o2).intValue();
      return Integer.valueOf(newValue);
    }
    // Use float
    float newValue=o1.floatValue()-o2.floatValue();
    return Float.valueOf(newValue);
  }

  /**
   * Multiply 2 numbers.
   * Keep number type if possible:
   * <ul>
   * <li>2 integers will give an integer,
   * <li>if at least one float, then it gives a float.
   * </ul>
   * @param o1 Input number 1.
   * @param o2 Input number 2.
   * @return the result number.
   */
  public static Number multiply(Number o1, Number o2)
  {
    if ((o1 instanceof Integer) && (o2 instanceof Integer))
    {
      int value1=((Integer)o1).intValue();
      int value2=((Integer)o2).intValue();
      return Integer.valueOf(value1*value2);
    }
    // Use float
    float newValue=o1.floatValue()*o2.floatValue();
    return Float.valueOf(newValue);
  }

  /**
   * Indicates if the given number is strictly positive or not.
   * @param n Input number.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public static boolean isStrictlyPositive(Number n)
  {
    if (n instanceof Integer)
    {
      return ((Integer)n).intValue()>0;
    }
    else if (n instanceof Float)
    {
      return ((Float)n).floatValue()>0;
    }
    LOGGER.warn("Unmanaged number in isStrictlyPositive: "+n);
    return false;
  }

  /**
   * Compute the sign of the given number.
   * @param n Input number.
   * @return 1 if positive, 0 if zero or -1 if negative.
   */
  public static int sign(Number n)
  {
    if (n instanceof Integer)
    {
      int value=((Integer)n).intValue();
      return Integer.signum(value);
    }
    if (n instanceof Float)
    {
      float value=((Float)n).floatValue();
      if (isZero(value))
      {
        return 0;
      }
      return value<0?-1:1;
    }
    LOGGER.warn("Unmanaged number in sign: "+n);
    return 0;
  }

  /**
   * Indicates if the given number is zero or not.
   * @param n Input number.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public static boolean isZero(Number n)
  {
    if (n instanceof Integer)
    {
      int value=((Integer)n).intValue();
      return (value==0);
    }
    if (n instanceof Float)
    {
      float value=((Float)n).floatValue();
      return isZero(value);
    }
    LOGGER.warn("Unmanaged number in isZero: "+n);
    return true;
  }

  /**
   * Indicates if the given float is zero or not.
   * @param value Input number.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public static boolean isZero(float value)
  {
    return (Math.abs(value)<0.01);
  }

  /**
   * Build a number from a fixed precision number (2 decimal digits: value*100). 
   * @param input Input string.
   * @return An integer if possible, or a float.
   */
  public static Number fromPersistenceString(String input)
  {
    Integer rawValue=NumericTools.parseInteger(input);
    if (rawValue==null)
    {
      return null;
    }
    if ((rawValue.intValue()%100)==0)
    {
      return Integer.valueOf(rawValue.intValue()/100);
    }
    return Float.valueOf(rawValue.floatValue()/100);
  }

  /**
   * Build a persistence string for a number.
   * @param value Input number.
   * @return A string to be persisted.
   */
  public static String toPersistenceString(Number value)
  {
    if (value==null)
    {
      return null;
    }
    if (value instanceof Integer)
    {
      return String.valueOf(value.intValue()*100);
    }
    return String.valueOf(Math.round(value.floatValue()*100));
  }
}
