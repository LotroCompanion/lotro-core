package delta.games.lotro.lore.items;

import delta.games.lotro.common.enums.LotroEnumEntry;
import delta.games.lotro.common.enums.LotroEnumsRegistry;

/**
 * Item binding.
 * @author DAM
 */
public class ItemBinding extends LotroEnumEntry
{
  /**
   * Item is bound on equip.
   */
  public static final ItemBinding BIND_ON_EQUIP=ItemBinding.getItemBindingByKey("BIND_ON_EQUIP");
  /**
   * Item is bound on acquire.
   */
  public static final ItemBinding BIND_ON_ACQUIRE=ItemBinding.getItemBindingByKey("BIND_ON_ACQUIRE");
  /**
   * Item is bound to account on acquire.
   */
  public static final ItemBinding BOUND_TO_ACCOUNT_ON_ACQUIRE=ItemBinding.getItemBindingByKey("BOUND_TO_ACCOUNT_ON_ACQUIRE");
  /**
   * Item is not bound at all.
   */
  public static final ItemBinding NONE=ItemBinding.getItemBindingByKey("NONE");

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
