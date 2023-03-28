package delta.games.lotro.lore.crafting.recipes;

import delta.games.lotro.lore.items.Item;

/**
 * Ingredient pack.
 * @author DAM
 */
public class IngredientPack
{
  private Item _item;
  private int _count;

  /**
   * Constructor.
   * @param item Item.
   * @param count Count.
   */
  public IngredientPack(Item item, int count)
  {
    _item=item;
    _count=count;
  }

  /**
   * Get the item.
   * @return the item.
   */
  public Item getItem()
  {
    return _item;
  }

  /**
   * Get the needed items count.
   * @return a count.
   */
  public int getCount()
  {
    return _count;
  }
}
