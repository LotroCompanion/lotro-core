package delta.games.lotro.lore.travels;

/**
 * Travel mode.
 * @author DAM
 */
public enum TravelMode
{
  /**
   * Boat.
   */
  BOAT("Boat"),
  /**
   * Horse.
   */
  HORSE("Horse"),
  /**
   * Elk.
   */
  ELK("Elk"),
  /**
   * Goat.
   */
  GOAT("Goat"),
  /**
   * Shaggy.
   */
  SHAGGY("Shaggy"),
  /**
   * Boar.
   */
  BOAR("Boar"),
  /**
   * Other.
   */
  OTHER("Other");

  private String _label;

  private TravelMode(String label)
  {
    _label=label;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}
