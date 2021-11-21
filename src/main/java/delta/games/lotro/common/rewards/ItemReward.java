package delta.games.lotro.common.rewards;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.utils.Proxy;

/**
 * Item reward.
 * @author DAM
 */
public class ItemReward extends RewardElement
{
  /**
   * Item proxy.
   */
  private Proxy<Item> _item;
  /**
   * Item quantity.
   */
  private int _quantity;

  /**
   * Constructor.
   * @param item Item proxy.
   * @param quantity Quantity.
   */
  public ItemReward(Proxy<Item> item, int quantity)
  {
    _item=item;
    _quantity=quantity;
  }

  /**
   * Get the item proxy.
   * @return an item proxy or <code>null</code> if not set.
   */
  public Proxy<Item> getItemProxy()
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

  /**
   * Add a quantity.
   * @param quantity Quantity to add.
   */
  public void addQuantity(int quantity)
  {
    _quantity+=quantity;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    if (_item!=null)
    {
      if (_quantity!=1)
      {
        sb.append(_quantity).append("x");
      }
      sb.append(_item.getName());
    }
    else
    {
      sb.append('?');
    }
    return sb.toString();
  }
}
