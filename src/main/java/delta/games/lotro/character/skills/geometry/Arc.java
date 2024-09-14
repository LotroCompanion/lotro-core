package delta.games.lotro.character.skills.geometry;

/**
 * Arc.
 * @author DAM
 */
public class Arc implements Shape
{
  private float _radius;
  private float _degrees;
  private Float _headingOffset;

  /**
   * Constructor.
   */
  public Arc()
  {
    _radius=1;
    _degrees=90;
    _headingOffset=null;
  }

  /**
   * @return the radius
   */
  public float getRadius()
  {
    return _radius;
  }

  /**
   * @param radius the radius to set
   */
  public void setRadius(float radius)
  {
    _radius=radius;
  }

  /**
   * @return the degrees
   */
  public float getDegrees()
  {
    return _degrees;
  }

  /**
   * @param degrees the degrees to set
   */
  public void setDegrees(float degrees)
  {
    _degrees=degrees;
  }

  /**
   * @return the headingOffset
   */
  public Float getHeadingOffset()
  {
    return _headingOffset;
  }

  /**
   * @param headingOffset the headingOffset to set
   */
  public void setHeadingOffset(Float headingOffset)
  {
    _headingOffset=headingOffset;
  }

  @Override
  public String toString()
  {
    return "Arc: radius="+_radius+", degrees="+_degrees+((_headingOffset!=null)?", heading="+_headingOffset:"");
  }
}
