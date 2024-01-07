package delta.games.lotro.utils.maths;

/**
 * Progression that uses predefined Y values for all supported X values.
 * @author DAM
 */
public class LongArrayProgression extends AbstractArrayProgression implements Progression
{
  private long[] _yValues;

  /**
   * Constructor.
   * @param identifier Identifier.
   * @param size Number of supported points.
   */
  public LongArrayProgression(int identifier, int size)
  {
    super(identifier,size);
    _yValues=new long[size];
  }

  /**
   * Get the Y value at the specified index.
   * @param index Point index, starting at 0.
   * @return A Y value.
   */
  public long getY(int index)
  {
    return _yValues[index];
  }

  /**
   * Set the value of a single point.
   * @param index Index of the point to set.
   * @param x X value to set.
   * @param y Y value to set.
   */
  public void set(int index, int x, long y)
  {
    super.set(index,x);
    _yValues[index]=y;
  }

  /**
   * Get a Y value for a given X value. 
   * @param x X value.
   * @return A Y value or <code>null</code> if not supported.
   */
  public Long getLongValue(int x)
  {
    for(int i=0;i<_xValues.length;i++)
    {
      if (x==_xValues[i]) return Long.valueOf(_yValues[i]);
    }
    return null;

  }

  @Override
  public Float getValue(int x)
  {
    // Caution! Precision loss!
    Long value=getLongValue(x);
    if (value!=null)
    {
      return Float.valueOf(value.floatValue());
    }
    return null;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    int nbPoints=_xValues.length;
    for(int i=0;i<nbPoints;i++)
    {
      if (i>0) sb.append(", ");
      sb.append(_xValues[i]).append(':').append(_yValues[i]);
    }
    return sb.toString();
  }
}
