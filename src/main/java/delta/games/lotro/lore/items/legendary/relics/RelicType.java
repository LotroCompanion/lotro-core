package delta.games.lotro.lore.items.legendary.relics;

/**
 * Relic type.
 * @author DAM
 */
public enum RelicType
{
  /**
   * Rune.
   */
  RUNE("Rune"),
  /**
   * Gem.
   */
  GEM("Gem"),
  /**
   * Setting.
   */
  SETTING("Setting"),
  /**
   * Crafted relic.
   */
  CRAFTED_RELIC("Crafted Relic");

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
