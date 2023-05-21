package delta.games.lotro.lore.items;

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
   * All qualities.
   */
  public static final ItemQuality[] ALL={ COMMON, UNCOMMON, RARE, INCOMPARABLE, LEGENDARY };
}
