package delta.games.lotro.utils.maths;

import java.util.function.Function;

import delta.common.utils.NumericTools;
import delta.games.lotro.values.codec.ValueReader;
import delta.games.lotro.values.codec.ValueWriter;

/**
 * Progression that uses predefined Y values for all supported X values.
 * @author DAM
 */
public class ArrayProgression extends AbstractProgression implements Progression
{
  private static final Function<String,Object> PARSE_INT=NumericTools::parseInteger;
  private static final Function<String,Object> PARSE_LONG=NumericTools::parseLong;
  private static final Function<String,Object> PARSE_FLOAT=NumericTools::parseFloat;
  private static final Function<String,Object> PARSE_OTHER=ValueReader::read;

  private String _valueType;
  private int _minX;
  private Object[] _yValues;
  private Function<String,Object> _parsingFunction;

  /**
   * Constructor.
   * @param identifier Identifier.
   * @param valueType Value type.
   * @param minX Minimum X.
   * @param size Number of supported points.
   */
  public ArrayProgression(int identifier, String valueType, int minX, int size)
  {
    super(identifier);
    _valueType=valueType;
    _parsingFunction=getParseFunction(valueType);
    _minX=minX;
    _yValues=new Object[size];
  }

  /**
   * Get the value type.
   * @return a value type.
   */
  public String getValueType()
  {
    return _valueType;
  }

  /**
   * Get the minimum X value.
   * @return A X value.
   */
  public int getMinX()
  {
    return _minX;
  }

  /**
   * Get the number of points.
   * @return the number of points.
   */
  public int getNumberOfPoints()
  {
    return _yValues.length;
  }

  /**
   * Get the Y value at the specified index.
   * @param index Point index, starting at 0.
   * @return A Y value.
   */
  public Object getY(int index)
  {
    return _yValues[index];
  }

  /**
   * Set the value of a single point.
   * @param x X value to set.
   * @param y Y value to set.
   */
  public void set(int x, Object y)
  {
    _yValues[x-_minX]=y;
  }

  /**
   * Get the raw value for a given X.
   * @param x X to use.
   * @return A Number value.
   * @throws IllegalArgumentException if x is below minX.
   */
  public Number getRawValue(int x)
  {
    Object value=getObjectValue(x);
    if (value instanceof Number)
    {
      return (Number)value;
    }
    return null;
  }

  /**
   * Get a raw Y value for a given X value. 
   * @param x X value.
   * @return A Y value (may not be a Number) or <code>null</code> if not supported.
   */
  public Object getObjectValue(int x)
  {
    int index=x-_minX;
    if (index<0)
    {
      return null;
    }
    if (index<_yValues.length)
    {
      return _yValues[index];
    }
    return _yValues[_yValues.length-1];
  }

  @Override
  public Float getValue(int x)
  {
    Object value=getObjectValue(x);
    if (value!=null)
    {
      if (value instanceof Float)
      {
        return (Float)value;
      }
      else if (value instanceof Number)
      {
        return Float.valueOf(((Number)value).floatValue());
      }
    }
    return null;
  }

  private static Function<String,Object> getParseFunction(String type)
  {
    if (ArrayProgressionConstants.FLOAT.equals(type)) return PARSE_FLOAT;
    if (ArrayProgressionConstants.INTEGER.equals(type)) return PARSE_INT;
    if (ArrayProgressionConstants.LONG.equals(type)) return PARSE_LONG;
    if (ArrayProgressionConstants.OTHER.equals(type)) return PARSE_OTHER;
    return null;
  }

  /**
   * Parse a string value.
   * @param valueStr Value to parse.
   * @return the parsed Number value.
   */
  public Object parseValue(String valueStr)
  {
    return _parsingFunction.apply(valueStr);
  }

  /**
   * Get the persistence string for the given value.
   * @param value Value to use.
   * @return A persistene string (<code>null</code> if value is <code>null</code>).
   */
  public static String writeValue(Object value)
  {
    if (value==null)
    {
      return null;
    }
    if (value instanceof Number)
    {
      return value.toString();
    }
    return ValueWriter.write(value);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    int nbPoints=_yValues.length;
    for(int i=0;i<nbPoints;i++)
    {
      if (i>0) sb.append(", ");
      sb.append(i+_minX).append(':').append(_yValues[i]);
    }
    return sb.toString();
  }
}
