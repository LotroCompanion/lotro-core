package delta.games.lotro.common.treasure;

import delta.games.lotro.lore.items.Item;

/**
 * Entry in an 'items table':
 * <ul>
 * <li>weight,
 * <li>item,
 * <li>quantity.
 * </ul>
 * @author DAM
 */
public class ItemsTableEntry
{
  private int _weight;
  private Item _item;
  private int _quantity;

  /**
   * Constructor.
   * @param weight Weight.
   * @param item Referenced item.
   * @param quantity Quantity.
   */
  public ItemsTableEntry(int weight, Item item, int quantity)
  {
    _weight=weight;
    _item=item;
    _quantity=quantity;
  }

  /**
   * Get the weight.
   * @return the weight.
   */
  public int getWeight()
  {
    return _weight;
  }

  /**
   * Get the referenced item.
   * @return an item.
   */
  public Item getItem()
  {
    return _item;
  }

  /**
   * Get the quantity.
   * @return the quantity.
   */
  public int getQuantity()
  {
    return _quantity;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append('(').append(_weight).append(") ");
    if (_quantity!=1)
    {
      sb.append(_quantity).append("x ");
    }
    sb.append(_item.getName());
    return sb.toString();
  }
}
