package delta.games.lotro.lore.quests.objectives;

/**
 * Condition of an objective.
 * @author DAM
 */
public abstract class ObjectiveCondition
{
  private int _index;

  /**
   * Constructor.
   */
  public ObjectiveCondition()
  {
    _index=1;
  }

  /**
   * Get the objective index, starting at 1.
   * @return An index.
   */
  public int getIndex()
  {
    return _index;
  }

  /**
   * Set the objective index.
   * @param index Index to set.
   */
  public void setIndex(int index)
  {
    _index=index;
  }

  /**
   * Get the condition type.
   * @return a condition type.
   */
  public abstract ConditionType getType();
}
