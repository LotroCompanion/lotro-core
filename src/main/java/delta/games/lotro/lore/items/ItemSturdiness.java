package delta.games.lotro.lore.items;

import delta.games.lotro.common.enums.LotroEnumEntry;
import delta.games.lotro.common.enums.LotroEnumsRegistry;

/**
 * Sturdiness of an item.
 * @author DAM
 */
public class ItemSturdiness extends LotroEnumEntry
{
  @Override
  public String toString()
  {
    return getLabel();
  }
  /**
   * Get a item sturdiness using its key.
   * @param key Key of damage type.
   * @return An item sturdiness instance or <code>null</code> if not found.
   */
  public static ItemSturdiness getItemSturdinessByKey(String key)
  {
    return LotroEnumsRegistry.getInstance().get(ItemSturdiness.class).getByKey(key);
  }
}
