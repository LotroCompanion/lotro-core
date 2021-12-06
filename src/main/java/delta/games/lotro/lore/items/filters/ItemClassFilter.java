package delta.games.lotro.lore.items.filters;

import delta.games.lotro.common.enums.ItemClass;
import delta.games.lotro.lore.items.Item;

/**
 * Filter for item class.
 * @author DAM
 */
public class ItemClassFilter implements ItemFilter
{
  private ItemClass _itemClass;

  /**
   * Constructor.
   * @param itemClass Item class to select.
   */
  public ItemClassFilter(ItemClass itemClass)
  {
    _itemClass=itemClass;
  }

  /**
   * Get the managed item class.
   * @return An item class or <code>null</code>.
   */
  public ItemClass getItemClass()
  {
    return _itemClass;
  }

  /**
   * Set the item class to use.
   * @param itemClass An item class or <code>null</code>.
   */
  public void setItemClass(ItemClass itemClass)
  {
    _itemClass=itemClass;
  }

  @Override
  public boolean accept(Item item)
  {
    if (item==null)
    {
      return false;
    }
    ItemClass itemClass=item.getItemClass();
    return ((_itemClass==null) || (itemClass.equals(_itemClass)));
  }
}
