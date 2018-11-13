package delta.games.lotro.lore.items.legendary.relics;

/**
 * Relic type.
 * @author DAM
 */
public enum RelicType
{
  /**
   * Setting.
   */
  SETTING("Setting"),
  /**
   * Gem.
   */
  GEM("Gem"),
  /**
   * Rune.
   */
  RUNE("Rune"),
  /**
   * Crafted relic.
   */
  CRAFTED_RELIC("Crafted Relic"),
  /**
   * Class relic.
   */
  CLASS_RELIC("Warden Relic"),
  /**
   * Insignia.
   */
  INSIGNIA("Insignia");

  private String _name;

  private RelicType(String name)
  {
    _name=name;
  }

  /**
   * Get a displayable name for this relic type.
   * @return A displayable name.
   */
  public String getName()
  {
    return _name;
  }
}
