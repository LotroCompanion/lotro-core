package delta.games.lotro.lore.items.details;

/**
 * Item XP.
 * @author DAM
 */
public class ItemDye extends ItemDetail
{
  private Float _color;

  /**
   * Constructor.
   * @param amount Item XP amount.
   */
  public ItemDye(Float color)
  {
    _color=color;
  }

  /**
   * Get the item XP amount.
   * @return a quantity.
   */
  public Float getColor()
  {
    return _color;
  }
}
