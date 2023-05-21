package delta.games.lotro.lore.items;

import delta.games.lotro.common.enums.LotroEnumEntry;
import delta.games.lotro.common.enums.LotroEnumsRegistry;

/**
 * Item quality.
 * @author DAM
 */
public final class ItemQuality extends LotroEnumEntry
{
  /**
   * Get the quality label.
   * @return the quality label.
   */
  public String getMeaning()
  {
    return getLabel();
  }

  /**
   * Get an item quality using its key.
   * @param key Key of item quality.
   * @return An item quality instance or <code>null</code> if not found.
   */
  public static ItemQuality fromCode(String key)
  {
    return LotroEnumsRegistry.getInstance().get(ItemQuality.class).getByKey(key);
  }

  @Override
  public String toString()
  {
    return getLabel();
  }
}
