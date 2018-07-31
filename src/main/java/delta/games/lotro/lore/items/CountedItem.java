package delta.games.lotro.lore.items;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemProxy;

/**
 * Item and count.
 * @author DAM
 */
public class CountedItem
{
  private ItemProxy _proxy;
  private int _quantity;

  /**
   * Constructor.
   * @param itemProxy Item proxy.
   * @param quantity Quantity.
   */
  public CountedItem(ItemProxy itemProxy, int quantity)
  {
    _proxy=itemProxy;
    _quantity=quantity;
  }

  /**
   * Get the managed item proxy.
   * @return the managed item proxy.
   */
  public ItemProxy getProxy()
  {
    return _proxy;
  }

  /**
   * Get the name of the item.
   * @return an item name.
   */
  public String getName()
  {
    return _proxy.getName();
  }

  /**
   * Get the item quantity.
   * @return a quantity.
   */
  public int getQuantity()
  {
    return _quantity;
  }

  /**
   * Add a quantity of this item.
   * @param quantity Quantity to add.
   */
  public void add(int quantity)
  {
    _quantity+=quantity;
  }

  /**
   * Get the icon.
   * @return an icon or <code>null</code> if not set.
   */
  public String getIcon()
  {
    return _proxy.getIcon();
  }

  /**
   * Get the managed item.
   * @return an item.
   */
  public Item getItem()
  {
    return _proxy.getItem();
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_quantity).append(' ');
    sb.append(_proxy);
    return sb.toString();
  }
}
