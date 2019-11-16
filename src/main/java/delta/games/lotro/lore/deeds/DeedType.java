package delta.games.lotro.lore.deeds;

/**
 * Deed type.
 * @author DAM
 */
public enum DeedType
{
  /**
   * Class deed.
   */
  CLASS("Class"),
  /**
   * Race deed.
   */
  RACE("Race"),
  /**
   * Event deed.
   */
  EVENT("Event"),
  /**
   * Exploration deed.
   */
  EXPLORER("Explorer"),
  /**
   * Lore-related deed.
   */
  LORE("Lore"),
  /**
   * Reputation deed.
   */
  REPUTATION("Reputation"),
  /**
   * Slayer deed.
   */
  SLAYER("Slayer");

  private String _label;

  private DeedType(String label)
  {
    _label=label;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}
