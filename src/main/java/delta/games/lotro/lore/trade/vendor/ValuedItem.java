package delta.games.lotro.lore.trade.vendor;

import delta.games.lotro.common.money.Money;
import delta.games.lotro.lore.items.Item;

/**
 * Item and value.
 * @author DAM
 */
public class ValuedItem
{
  private Item _item;
  private Money _value;

  /**
   * Constructor.
   * @param item Item.
   * @param value Value.
   */
  public ValuedItem(Item item, Money value)
  {
    _item=item;
    _value=value;
  }

  /**
   * Get the managed item.
   * @return the managed item .
   */
  public Item getItem()
  {
    return _item;
  }

  /**
   * Get the identifier of the item.
   * @return an identifier.
   */
  public int getId()
  {
    return _item.getIdentifier();
  }

  /**
   * Get the name of the item.
   * @return an item name.
   */
  public String getName()
  {
    return _item.getName();
  }

  /**
   * Get the item value.
   * @return a value.
   */
  public Money getValue()
  {
    return _value;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_value).append(' ');
    sb.append(_item);
    return sb.toString();
  }
}
