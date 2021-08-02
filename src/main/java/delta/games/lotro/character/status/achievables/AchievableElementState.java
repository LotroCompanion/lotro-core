package delta.games.lotro.character.status.achievables;

/**
 * Achievable element status (achievable, objective, condition).
 * @author DAM
 */
public enum AchievableElementState
{
  /**
   * Undefined.
   */
  UNDEFINED("Not started"),
  /**
   * Underway.
   */
  UNDERWAY("Underway"),
  /**
   * Completed.
   */
  COMPLETED("Completed");

  private String _label;
  private AchievableElementState(String label)
  {
    _label=label;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}
