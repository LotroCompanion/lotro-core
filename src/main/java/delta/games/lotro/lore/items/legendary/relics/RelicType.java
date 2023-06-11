package delta.games.lotro.lore.items.legendary.relics;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.enums.LotroEnumEntry;
import delta.games.lotro.common.enums.LotroEnumsRegistry;

/**
 * Relic type.
 * @author DAM
 */
public class RelicType extends LotroEnumEntry
{
  /**
   * Get a displayable name for this relic type.
   * @return A displayable name.
   */
  public String getName()
  {
    return getLabel();
  }


  /**
   * Get a relic type using its key.
   * @param key Key of relic type.
   * @return A relic type instance or <code>null</code> if not found.
   */
  public static RelicType getRelicTypeByKey(String key)
  {
    return LotroEnumsRegistry.getInstance().get(RelicType.class).getByKey(key);
  }

  /**
   * Get all instances of this class (sorted in the legacy order).
   * @return an array of all instances of this class.
   */
  public static List<RelicType> getAll()
  {
    List<RelicType> ret=new ArrayList<RelicType>();
    ret.add(RelicTypes.SETTING);
    ret.add(RelicTypes.GEM);
    ret.add(RelicTypes.RUNE);
    ret.add(RelicTypes.CRAFTED_RELIC);
    return ret;
  }
}
