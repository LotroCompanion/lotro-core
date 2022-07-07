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
   * @param color Dye color.
   */
  public ItemDye(Float color)
  {
    _color=color;
  }

  /**
   * Get the dye color.
   * @return a color.
   */
  public Float getColor()
  {
    return _color;
  }
}
