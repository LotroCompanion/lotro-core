package delta.games.lotro.utils.maths;

/**
 * Progression that uses predefined Y values for all supported X values.
 * @author DAM
 */
public class ArrayProgression implements Progression
{
  private int[] _xValues;
  private float[] _yValues;

  /**
   * Constructor.
   * @param size Number of supported points.
   */
  public ArrayProgression(int size)
  {
    _xValues=new int[size];
    _yValues=new float[size];
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
    return null;
  }
}
