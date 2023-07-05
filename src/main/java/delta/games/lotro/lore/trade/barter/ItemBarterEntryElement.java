package delta.games.lotro.lore.trade.barter;

import delta.games.lotro.lore.items.Item;

/**
 * Barter entry: item and quantity.
 * @author DAM
 */
public class ItemBarterEntryElement extends BarterEntryElement
{
  /**
   * Item.
   */
  private Item _item;
  /**
   * Item quantity.
   */
  private int _quantity;

  /**
   * Constructor.
   * @param item Item.
   * @param quantity Quantity.
   */
  public ItemBarterEntryElement(Item item, int quantity)
  {
    _item=item;
    _quantity=quantity;
  }

  /**
   * Get the item.
   * @return an item or <code>null</code> if not set.
   */
  public Item getItem()
  {
    return _item;
  }

  /**
   * Get quantity.
   * @return a quantity.
   */
  public int getQuantity()
  {
    return _quantity;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    if (_item!=null)
    {
      sb.append(_quantity).append(" ");
      sb.append(_item.getName());
    }
    else
    {
      sb.append('?');
    }
    return sb.toString();
  }
}
