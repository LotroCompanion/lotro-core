package delta.games.lotro.character.status.skirmishes;

/**
 * Skirmish level.
 * @author DAM
 */
public enum SkirmishLevel
{
  /**
   * On Level.
   */
  ON_LEVEL("On Level"),
  /**
   * Off Level.
   */
  OFF_LEVEL("Off Level");

  private String _label;
  private SkirmishLevel(String label)
  {
    _label=label;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}
