package delta.games.lotro.lore.trade.vendor;

import delta.games.lotro.common.money.Money;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemProxy;

/**
 * Item and value.
 * @author DAM
 */
public class ValuedItem
{
  private ItemProxy _proxy;
  private Money _value;

  /**
   * Constructor.
   * @param itemProxy Item proxy.
   * @param value Value.
   */
  public ValuedItem(ItemProxy itemProxy, Money value)
  {
    _proxy=itemProxy;
    _value=value;
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
   * Get the identifier of the item.
   * @return an identifier.
   */
  public int getId()
  {
    return _proxy.getId();
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
   * Get the item value.
   * @return a value.
   */
  public Money getValue()
  {
    return _value;
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
    sb.append(_value).append(' ');
    sb.append(_proxy);
    return sb.toString();
  }
}
