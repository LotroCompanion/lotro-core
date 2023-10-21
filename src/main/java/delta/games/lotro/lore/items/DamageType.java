package delta.games.lotro.lore.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.games.lotro.common.enums.LotroEnumEntry;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryNameComparator;

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

  /**
   * Get a damage type using its code.
   * @param code Code of damage type.
   * @return A damage type instance or <code>null</code> if not found.
   */
  public static DamageType getDamageTypeByCode(int code)
  {
    return LotroEnumsRegistry.getInstance().get(DamageType.class).getEntry(code);
  }

  /**
   * Get all instances of this class.
   * @return an array of all instances of this class.
   */
  public static List<DamageType> getAll()
  {
    List<DamageType> ret=new ArrayList<DamageType>();
    ret.addAll(LotroEnumsRegistry.getInstance().get(DamageType.class).getAll());
    Collections.sort(ret,new LotroEnumEntryNameComparator<DamageType>());
    return ret;
  }
}
