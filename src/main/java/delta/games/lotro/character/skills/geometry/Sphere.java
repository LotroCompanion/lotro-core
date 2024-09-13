package delta.games.lotro.character.skills.geometry;

/**
 * Sphere.
 * @author DAM
 */
public class Sphere implements Shape
{
  private float _radius; // From Skill_AEDetectionVolume_SphereRadius

  /**
   * Constructor.
   */
  public Sphere()
  {
    _radius=1;
  }

  /**
   * Get the radius.
   * @return a radius in meters.
   */
  public float getRadius()
  {
    return _radius;
  }

  /**
   * Set the radius.
   * @param radius Radius in meters.
   */
  public void setRadius(float radius)
  {
    _radius=radius;
  }

  @Override
  public String toString()
  {
    return "Sphere: radius="+_radius+"m";
  }
}
