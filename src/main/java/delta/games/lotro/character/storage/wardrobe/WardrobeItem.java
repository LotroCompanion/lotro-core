package delta.games.lotro.character.storage.wardrobe;

import delta.games.lotro.common.colors.ColorDescription;
import delta.games.lotro.lore.items.Item;

/**
 * Item of the wardrobe.
 * @author DAM
 */
public class WardrobeItem
{
  private Item _item;
  private ColorDescription _color;

  /**
   * Constructor.
   * @param item Item.
   * @param color Color.
   */
  public WardrobeItem(Item item, ColorDescription color)
  {
    _item=item;
    _color=color;
  }

  /**
   * Get the managed item.
   * @return an item.
   */
  public Item getItem()
  {
    return _item;
  }

  /**
   * Get the managed code.
   * @return A color.
   */
  public ColorDescription getColor()
  {
    return _color;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    if (_item!=null)
    {
      sb.append("Item: ").append(_item);
    }
    if (_color!=null)
    {
      if (sb.length()>0)
      {
        sb.append(", ");
      }
      sb.append("color: ");
      sb.append(_color.getName());
    }
    return sb.toString();
  }
}
