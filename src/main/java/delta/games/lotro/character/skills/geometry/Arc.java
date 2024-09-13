package delta.games.lotro.character.skills.geometry;

/**
 * Arc.
 * @author DAM
 */
public class Arc implements Shape
{
  private float _radius; // From Skill_AEDetectionVolume_ArcRadius
  private float _degrees; // From Skill_AEDetectionVolume_ArcDegrees
  private float _headingOffset; // From Skill_AEDetectionVolume_HeadingOffset (may be also for arc)

  /**
   * Constructor.
   */
  public Arc()
  {
    _radius=1;
    _degrees=90;
    _headingOffset=0;
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
  public float getHeadingOffset()
  {
    return _headingOffset;
  }

  /**
   * @param headingOffset the headingOffset to set
   */
  public void setHeadingOffset(float headingOffset)
  {
    _headingOffset=headingOffset;
  }

  @Override
  public String toString()
  {
    return "Arc: radius="+_radius+", degrees="+_degrees+", heading="+_headingOffset;
  }
}
