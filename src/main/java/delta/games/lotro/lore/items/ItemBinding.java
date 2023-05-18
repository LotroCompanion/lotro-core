package delta.games.lotro.lore.items;

import delta.games.lotro.common.enums.LotroEnumEntry;
import delta.games.lotro.common.enums.LotroEnumsRegistry;

/**
 * Item binding.
 * @author DAM
 */
public class ItemBinding extends LotroEnumEntry
{
  @Override
  public String toString()
  {
    return getLabel();
  }

  /**
   * Get an item binding using its key.
   * @param key Key of item binding.
   * @return An item binding instance or <code>null</code> if not found.
   */
  public static ItemBinding getItemBindingByKey(String key)
  {
    return LotroEnumsRegistry.getInstance().get(ItemBinding.class).getByKey(key);
  }
}
