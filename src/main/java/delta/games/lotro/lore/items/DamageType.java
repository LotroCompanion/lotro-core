package delta.games.lotro.lore.items;

import delta.games.lotro.common.enums.LotroEnumEntry;
import delta.games.lotro.common.enums.LotroEnumsRegistry;

/**
 * Damage type.
 * @author DAM
 */
public final class DamageType extends LotroEnumEntry
{
  /**
   * Get the damage type name.
   * @return A name.
   */
  public String getName()
  {
    return getLabel();
  }

  @Override
  public String toString()
  {
    return getLabel();
  }

  /**
   * Get a damage type using its key.
   * @param key Key of damage type.
   * @return A damage type instance or <code>null</code> if not found.
   */
  public static DamageType getDamageTypeByKey(String key)
  {
    return LotroEnumsRegistry.getInstance().get(DamageType.class).getByKey(key);
  }
}
