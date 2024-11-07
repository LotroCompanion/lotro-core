package delta.games.lotro.character.skills.geometry;

/**
 * Positional data for a skill.
 * @author DAM
 */
public class SkillPositionalData
{
  private int _heading;
  private int _spread;

  /**
   * Constructor.
   */
  public SkillPositionalData()
  {
    _heading=0;
    _spread=0;
  }

  /**
   * Get the heading.
   * @return A heading (degrees). 180=behind.
   */
  public int getHeading()
  {
    return _heading;
  }

  /**
   * Set the heading.
   * @param heading the heading to set.
   */
  public void setHeading(int heading)
  {
    _heading=heading;
  }

  /**
   * Get the spread.
   * @return an angle value (degrees).
   */
  public int getSpread()
  {
    return _spread;
  }

  /**
   * Set the spread.
   * @param spread the spread to set.
   */
  public void setSpread(int spread)
  {
    _spread=spread;
  }
}
