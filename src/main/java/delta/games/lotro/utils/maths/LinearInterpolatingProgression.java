package delta.games.lotro.utils.maths;

/**
 * Progression that uses linear interpolation between control points.
 * @author DAM
 */
public class LinearInterpolatingProgression implements Progression
{
  private int[] _xValues;
  private float[] _yValues;

  /**
   * Constructor.
   * @param size Number of control points.
   */
  public LinearInterpolatingProgression(int size)
  {
    _xValues=new int[size];
    _yValues=new float[size];
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
}
