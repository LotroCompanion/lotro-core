package delta.games.lotro.lore.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumEntry;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryCodeComparator;

/**
 * Equipment location.
 * @author DAM
 */
public final class EquipmentLocation extends LotroEnumEntry
{
  /**
   * Get an equipment location using its key.
   * @param key Key of equipment location to get.
   * @return An equipment location instance or <code>null</code> if not found.
   */
  public static EquipmentLocation getByKey(String key)
  {
    return LotroEnumsRegistry.getInstance().get(EquipmentLocation.class).getByKey(key);
  }

  /**
   * Get all instances of this class.
   * @return an array of all instances of this class.
   */
  public static List<EquipmentLocation> getAll()
  {
    LotroEnum<EquipmentLocation> locationEnum=LotroEnumsRegistry.getInstance().get(EquipmentLocation.class);
    List<EquipmentLocation> values=new ArrayList<EquipmentLocation>(locationEnum.getAll());
    Collections.sort(values,new LotroEnumEntryCodeComparator<EquipmentLocation>());
    return values;
  }

  @Override
  public String toString()
  {
    return getLabel();
  }
}
