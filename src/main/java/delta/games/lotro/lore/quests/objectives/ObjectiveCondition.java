package delta.games.lotro.lore.quests.objectives;

/**
 * Condition of an objective.
 * @author DAM
 */
public abstract class ObjectiveCondition
{
  private int _index;
  private String _loreInfo;
  private String _progressOverride;

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
   * Get lore info.
   * @return a lore info string or <code>null</code>.
   */
  public String getLoreInfo()
  {
    return _loreInfo;
  }

  /**
   * Set the lore info string.
   * @param loreInfo the lore info to set.
   */
  public void setLoreInfo(String loreInfo)
  {
    _loreInfo=loreInfo;
  }

  /**
   * Get the progress override string.
   * @return a progress override string or <code>null</code>.
   */
  public String getProgressOverride()
  {
    return _progressOverride;
  }

  /**
   * Set the progress override string.
   * @param progressOverride the progress override to set.
   */
  public void setProgressOverride(String progressOverride)
  {
    _progressOverride=progressOverride;
  }

  /**
   * Get the condition type.
   * @return a condition type.
   */
  public abstract ConditionType getType();
}
