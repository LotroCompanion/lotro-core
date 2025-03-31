package delta.games.lotro.lore.items;

import java.util.ArrayList;
import java.util.List;

/**
 * Well-known item qualities.
 * @author DAM
 */
public class ItemQualities
{
  /**
   * Common.
   */
  public static final ItemQuality COMMON=ItemQuality.fromCode("COMMON");
  /**
   * Uncommon.
   */
  public static final ItemQuality UNCOMMON=ItemQuality.fromCode("UNCOMMON");
  /**
   * Rare.
   */
  public static final ItemQuality RARE=ItemQuality.fromCode("RARE");
  /**
   * Incomparable.
   */
  public static final ItemQuality INCOMPARABLE=ItemQuality.fromCode("INCOMPARABLE");
  /**
   * Epic.
   */
  public static final ItemQuality LEGENDARY=ItemQuality.fromCode("LEGENDARY");

  /**
   * Get all qualities.
   * @return a list of qualities.
   */
  public static List<ItemQuality> getAll()
  {
    List<ItemQuality> ret=new ArrayList<ItemQuality>();
    ret.add(COMMON);
    ret.add(UNCOMMON);
    ret.add(RARE);
    ret.add(INCOMPARABLE);
    ret.add(LEGENDARY);
    return ret;
  }
}
