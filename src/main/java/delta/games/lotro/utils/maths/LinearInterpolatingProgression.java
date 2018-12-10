package delta.games.lotro.utils.maths;

/**
 * Progression that uses linear interpolation between control points.
 * @author DAM
 */
public class LinearInterpolatingProgression extends AbstractProgression implements Progression
{
  private int[] _xValues;
  private float[] _yValues;

  /**
   * Constructor.
   * @param identifier Identifier.
   * @param size Number of control points.
   */
  public LinearInterpolatingProgression(int identifier, int size)
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
   * Set the value of a control point.
   * @param index Index of control point, starting at 0.
   * @param x X value of the control point.
   * @param y Y value of the control point.
   */
  public void set(int index, int x, float y)
  {
    _xValues[index]=x;
    _yValues[index]=y;
  }

  /**
   * Reverse interpolation: get the X value for a given y value.
   * @param y Y value.
   * @return A X value or <code>null</code> if not supported.
   */
  public Float getXValue(float y)
  {
    if (y<_yValues[0])
    {
      return null;
    }
    for(int i=0;i<_yValues.length-1;i++)
    {
      if ((y>=_yValues[i]) && (y<=_yValues[i+1]))
      {
        float result=_xValues[i]+(_xValues[i+1]-_xValues[i])*(y-_yValues[i])/(_yValues[i+1]-_yValues[i]);
        return Float.valueOf(result);
      }
    }
    return null;
  }

  @Override
  public Float getValue(int x)
  {
    if (x<_xValues[0])
    {
      return null;
    }
    if (x>_xValues[_xValues.length-1])
    {
      return null;
    }
    for(int i=0;i<_xValues.length-1;i++)
    {
      if ((x>=_xValues[i]) && (x<=_xValues[i+1]))
      {
        float result=_yValues[i]+(_yValues[i+1]-_yValues[i])*(x-_xValues[i])/(_xValues[i+1]-_xValues[i]);
        return Float.valueOf(result);
      }
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
