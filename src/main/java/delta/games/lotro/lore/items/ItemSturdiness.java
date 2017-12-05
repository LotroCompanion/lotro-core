package delta.games.lotro.lore.items;

/**
 * Sturdiness of an item.
 * @author DAM
 */
public enum ItemSturdiness
{
  /**
   * Brittle.
   */
  BRITTLE("Brittle"),
  /**
   * Normal.
   */
  NORMAL("Normal"),
  /**
   * Tough.
   */
  TOUGH("Tough"),
  /**
   * Substantial.
   */
  SUBSTANTIAL("Substantial"),
  /**
   * Weak.
   */
  WEAK("Weak");

  private String _label;

  private ItemSturdiness(String label)
  {
    _label=label;
  }

  /**
   * Get a readable label for this object.
   * @return a readable label.
   */
  public String getLabel()
  {
    return _label;
  }

  @Override
  public String toString()
  {
    return _label;
  }

  /**
   * All.
   */
  public static final ItemSturdiness[] ALL={ WEAK, BRITTLE, NORMAL, TOUGH, SUBSTANTIAL };
}
