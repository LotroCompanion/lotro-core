package delta.games.lotro.character.gear;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumEntry;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryCodeComparator;

/**
 * Gear slot designator.
 * @author DAM
 */
public class GearSlot extends LotroEnumEntry
{
  /**
   * Get an gear slot using its key.
   * @param key Key of gear slot to get.
   * @return A gear slot instance or <code>null</code> if not found.
   */
  public static GearSlot getByKey(String key)
  {
    return LotroEnumsRegistry.getInstance().get(GearSlot.class).getByKey(key);
  }

  /**
   * Get all instances of this class.
   * @return an array of all instances of this class.
   */
  public static GearSlot[] getAll()
  {
    LotroEnum<GearSlot> locationEnum=LotroEnumsRegistry.getInstance().get(GearSlot.class);
    List<GearSlot> values=new ArrayList<GearSlot>(locationEnum.getAll());
    Collections.sort(values,new LotroEnumEntryCodeComparator<GearSlot>());
    return values.toArray(new GearSlot[values.size()]);
  }

  @Override
  public String toString()
  {
    return getLabel();
  }
}
