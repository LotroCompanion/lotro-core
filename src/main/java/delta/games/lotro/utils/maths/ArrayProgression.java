package delta.games.lotro.utils.maths;

/**
 * Progression that uses predefined Y values for all supported X values.
 * @author DAM
 */
public class ArrayProgression extends AbstractProgression implements Progression
{
  private int[] _xValues;
  private float[] _yValues;

  /**
   * Constructor.
   * @param identifier Identifier.
   * @param size Number of supported points.
   */
  public ArrayProgression(int identifier, int size)
  {
    super(identifier);
    _xValues=new int[size];
    _yValues=new float[size];
  }

  /**
   * Get the number of points.
   * @return the number of points.
   */
  public int getNumberOfPoints()
  {
    return _xValues.length;
  }

  /**
   * Get the X value at the specified index.
   * @param index Point index, starting at 0.
   * @return A X value.
   */
  public int getX(int index)
  {
    return _xValues[index];
  }

  /**
   * Get the Y value at the specified index.
   * @param index Point index, starting at 0.
   * @return A Y value.
   */
  public float getY(int index)
  {
    return _yValues[index];
  }

  /**
   * Set the value of a single point.
   * @param index Index of the point to set.
   * @param x X value to set.
   * @param y Y value to set.
   */
  public void set(int index, int x, float y)
  {
    _xValues[index]=x;
    _yValues[index]=y;
  }

  @Override
  public Float getValue(int x)
  {
    for(int i=0;i<_xValues.length;i++)
    {
      if (x==_xValues[i]) return Float.valueOf(_yValues[i]);
    }
    if (x>_xValues[_xValues.length-1])
    {
      return Float.valueOf(_yValues[_xValues.length-1]);
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
