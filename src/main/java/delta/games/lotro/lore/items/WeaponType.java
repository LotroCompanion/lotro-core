package delta.games.lotro.lore.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.games.lotro.common.enums.LotroEnumEntry;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryNameComparator;

/**
 * Weapon type.
 * @author DAM
 */
public final class WeaponType extends LotroEnumEntry
{
  @Override
  public String toString()
  {
    return getLabel();
  }

  /**
   * Get a weapon type using its key.
   * @param key Key of weapon type.
   * @return A weapon type instance or <code>null</code> if not found.
   */
  public static WeaponType getWeaponTypeByKey(String key)
  {
    return LotroEnumsRegistry.getInstance().get(WeaponType.class).getByKey(key);
  }

  /**
   * Get all instances of this class.
   * @return an array of all instances of this class.
   */
  public static List<WeaponType> getAll()
  {
    List<WeaponType> ret=new ArrayList<WeaponType>();
    ret.addAll(LotroEnumsRegistry.getInstance().get(WeaponType.class).getAll());
    Collections.sort(ret,new LotroEnumEntryNameComparator<WeaponType>());
    return ret;
  }
}
