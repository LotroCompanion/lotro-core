package delta.games.lotro.lore.items;

/**
 * Item instance and count.
 * @author DAM
 */
public class CountedItemInstance
{
  private ItemInstance<? extends Item> _itemInstance;
  private int _quantity;

  /**
   * Constructor.
   * @param itemInstance Item instance.
   * @param quantity Quantity.
   */
  public CountedItemInstance(ItemInstance<? extends Item> itemInstance, int quantity)
  {
    _itemInstance=itemInstance;
    _quantity=quantity;
  }

  /**
   * Get the managed item instance.
   * @return the managed item instance.
   */
  public ItemInstance<? extends Item> getItemInstance()
  {
    return _itemInstance;
  }

  /**
   * Get the name of the item.
   * @return an item name.
   */
  public String getName()
  {
    return _itemInstance.getName();
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
   * Get the managed item.
   * @return an item.
   */
  public Item getItem()
  {
    return _itemInstance.getReference();
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_quantity).append(' ');
    sb.append(_itemInstance);
    return sb.toString();
  }
}
