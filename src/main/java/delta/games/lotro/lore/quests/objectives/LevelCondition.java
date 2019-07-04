package delta.games.lotro.lore.quests.objectives;

/**
 * Level condition.
 * @author DAM
 */
public class LevelCondition extends ObjectiveCondition
{
  private int _level;

  /**
   * Constructor.
   */
  public LevelCondition()
  {
    _level=1;
  }

  @Override
  public ConditionType getType()
  {
    return ConditionType.LEVEL;
  }

  /**
   * Get the level.
   * @return a level value.
   */
  public int getLevel()
  {
    return _level;
  }

  /**
   * Set the level.
   * @param level the level to set.
   */
  public void setLevel(int level)
  {
    _level=level;
  }

  @Override
  public String toString()
  {
    return "Level: "+_level;
  }
}
