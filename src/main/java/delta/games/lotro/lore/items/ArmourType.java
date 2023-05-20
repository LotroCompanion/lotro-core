package delta.games.lotro.lore.items;

import delta.games.lotro.common.enums.LotroEnumEntry;
import delta.games.lotro.common.enums.LotroEnumsRegistry;

/**
 * Armour type.
 * @author DAM
 */
public final class ArmourType extends LotroEnumEntry
{
  /**
   * Constructor.
   */
  public ArmourType()
  {
    super();
  }

  @Override
  public String toString()
  {
    return getLabel();
  }

  /**
   * Get an armour type using its key.
   * @param key Key of armour type.
   * @return An armour type instance or <code>null</code> if not found.
   */
  public static ArmourType getArmourTypeByKey(String key)
  {
    return LotroEnumsRegistry.getInstance().get(ArmourType.class).getByKey(key);
  }
}
