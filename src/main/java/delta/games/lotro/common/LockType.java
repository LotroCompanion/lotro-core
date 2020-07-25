package delta.games.lotro.common;

/**
 * Lock type.
 * @author DAM
 */
public enum LockType
{
  /**
   * Daily.
   */
  DAILY("Daily"),
  /**
   * Weekly.
   */
  WEEKLY("Weekly"),
  /**
   * Bi-weekly.
   */
  BIWEEKLY("Bi-weekly");

  private String _label;
  private LockType(String label)
  {
    _label=label;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}
