package delta.games.lotro.common.treasure;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.utils.Proxy;

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
  private Proxy<Item> _item;
  private int _quantity;

  /**
   * Constructor.
   * @param weight Weight.
   * @param item Referenced item.
   * @param quantity Quantity.
   */
  public ItemsTableEntry(int weight, Proxy<Item> item, int quantity)
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
   * @return an item proxy.
   */
  public Proxy<Item> getItem()
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
}
