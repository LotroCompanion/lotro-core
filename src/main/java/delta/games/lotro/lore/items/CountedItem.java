package delta.games.lotro.lore.items;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;

/**
 * Count and item provider (item or item instance).
 * @author DAM
 * @param <T> Type of managed items.
 */
public class CountedItem<T extends ItemProvider> implements Named,ItemProvider,Identifiable
{
  private T _item;
  private int _quantity;

  /**
   * Constructor.
   * @param item Item.
   * @param quantity Quantity.
   */
  public CountedItem(T item, int quantity)
  {
    _item=item;
    _quantity=quantity;
  }

  /**
   * Get the managed item.
   * @return the managed item.
   */
  public T getManagedItem()
  {
    return _item;
  }

  /**
   * Get the identifier of the item.
   * @return an identifier.
   */
  public int getId()
  {
    if (_item!=null)
    {
      Item item=_item.getItem();
      if (item!=null)
      {
        return item.getIdentifier();
      }
    }
    return 0;
  }

  @Override
  public int getIdentifier()
  {
    return getId();
  }

  /**
   * Get the name of the item.
   * @return an item name.
   */
  public String getName()
  {
    if (_item!=null)
    {
      Item item=_item.getItem();
      if (item!=null)
      {
        return item.getName();
      }
    }
    return null;
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
    if (_item!=null)
    {
      Item item=_item.getItem();
      if (item!=null)
      {
        return item.getIcon();
      }
    }
    return null;
  }

  /**
   * Get the managed item.
   * @return an item.
   */
  public Item getItem()
  {
    if (_item!=null)
    {
      return _item.getItem();
    }
    return null;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_quantity).append(' ');
    sb.append(_item);
    return sb.toString();
  }
}
