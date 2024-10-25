package delta.games.lotro.common.math;

/**
 * Linear function defined on a given X range.
 * @author DAM
 */
public class LinearFunction
{
  private float _minX;
  private float _maxX;
  private float _minY;
  private float _maxY;

  /**
   * Constructor.
   * @param minX Minimum X.
   * @param maxX Maximum X.
   * @param minY Minimum Y.
   * @param maxY Maximum Y.
   */
  public LinearFunction(float minX, float maxX, float minY, float maxY)
  {
    _minX=minX;
    _maxX=maxX;
    _minY=minY;
    _maxY=maxY;
  }

  /**
   * Get the minimum X.
   * @return the minimum X.
   */
  public float getMinX()
  {
    return _minX;
  }

  /**
   * Get the maximum X.
   * @return the maximum X.
   */
  public float getMaxX()
  {
    return _maxX;
  }

  /**
   * Get the minimum Y.
   * @return the minimum Y.
   */
  public float getMinY()
  {
    return _minY;
  }

  /**
   * Get the maximum Y.
   * @return the maximum Y.
   */
  public float getMaxY()
  {
    return _maxY;
  }

  /**
   * Get the Y value for a given X value.
   * @param x X value.
   * @return A Y value or <code>null</code> if undefined (X is not within X range).
   */
  public Float getValue(float x)
  {
    if ((x>=_minX) && (x<=_maxX))
    {
      float value=_minY+(x-_minX)*(_maxY-_minY)/(_maxX-_minX);
      return Float.valueOf(value);
    }
    if (x<_minX)
    {
      return Float.valueOf(_minY);
    }
    if (x>_maxX)
    {
      return Float.valueOf(_maxY);
    }
    return null;
  }

  @Override
  public String toString()
  {
    return "("+_minX+";"+_maxX+") => ("+_minY+";"+_maxY+")";
  }
}
