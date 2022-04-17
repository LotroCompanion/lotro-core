package delta.games.lotro.character.cosmetics;

import delta.games.lotro.common.colors.ColorDescription;
import delta.games.lotro.lore.items.Item;

/**
 * Element of an outfit.
 * @author DAM
 */
public class OutfitElement
{
  private Item _item;
  private ColorDescription _color;

  /**
   * Constructor.
   */
  public OutfitElement()
  {
    _item=null;
    _color=null;
  }

  /**
   * Get the managed item.
   * @return An item (not <code>null</code>).
   */
  public Item getItem()
  {
    return _item;
  }

  /**
   * Set the managed item.
   * @param item Item to set.
   */
  public void setItem(Item item)
  {
    _item=item;
  }

  /**
   * Get the color to use.
   * @return A color.
   */
  public ColorDescription getColor()
  {
    return _color;
  }

  /**
   * Set the color.
   * @param color Color to set.
   */
  public void setColor(ColorDescription color)
  {
    _color=color;
  }
}
