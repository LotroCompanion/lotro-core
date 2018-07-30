package delta.games.lotro.character.storage;

import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.ItemProxy;

/**
 * Stored item.
 * @author DAM
 */
public class StoredItem extends CountedItem
{
  /**
   * Constructor.
   * @param itemProxy Item proxy.
   * @param quantity Quantity.
   */
  public StoredItem(ItemProxy itemProxy, int quantity)
  {
    super(itemProxy,quantity);
  }

  // TODO add location
  // TODO add owner
}
