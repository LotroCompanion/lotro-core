package delta.games.lotro.common.requirements;

/**
 * Requirement on the level cap.
 * @author DAM
 */
public class LevelCapRequirement implements Requirement
{
  private int _levelCap;

  /**
   * Constructor.
   * @param levelCap Level cap.
   */
  public LevelCapRequirement(int levelCap)
  {
    _levelCap=levelCap;
  }

  /**
   * Get the required level cap.
   * @return the required level cap.
   */
  public int getLevelCap()
  {
    return _levelCap;
  }

  @Override
  public String toString()
  {
    return "Level cap "+_levelCap;
  }
}
