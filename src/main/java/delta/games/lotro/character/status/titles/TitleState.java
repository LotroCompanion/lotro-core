package delta.games.lotro.character.status.titles;

/**
 * Title state.
 * @author DAM
 */
public enum TitleState
{
  /**
   * Undefined.
   */
  UNDEFINED("Not acquired"),
  /**
   * Obsolete.
   */
  SUPERSEDED("Superseded"),
  /**
   * Completed.
   */
  ACQUIRED("Completed");

  private String _label;
  private TitleState(String label)
  {
    _label=label;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}
