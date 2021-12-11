package delta.games.lotro.lore.items.carryalls;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemCategory;

/**
 * Carry-all.
 * @author DAM
 */
public class CarryAll extends Item
{
  private int _maxItems;
  private int _itemStackMax;

  /**
   * Constructor.
   */
  public CarryAll()
  {
    // Nothing!
  }

  @Override
  public ItemCategory getCategory()
  {
    return ItemCategory.CARRY_ALL;
  }

  /**
   * Get the maximum number of items in this carry-all.
   * @return an items count.
   */
  public int getMaxItems()
  {
    return _maxItems;
  }

  /**
   * Set the maximum number of items in this carry-all.
   * @param maxItems Items count.
   */
  public void setMaxItems(int maxItems)
  {
    _maxItems=maxItems;
  }

  /**
   * Get the maximum size of each stack.
   * @return a stack size.
   */
  public int getItemStackMax()
  {
    return _itemStackMax;
  }

  /**
   * Set the maximum size for each stack.
   * @param itemStackMax Stack size to set.
   */
  public void setItemStackMax(int itemStackMax)
  {
    _itemStackMax=itemStackMax;
  }
}
