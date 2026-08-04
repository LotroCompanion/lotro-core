package delta.games.lotro.common;

import delta.games.lotro.common.enums.LotroEnumsRegistry;

/**
 * Well-known sizes.
 * @author DAM
 */
public class Sizes
{
  /**
   * Solo.
   */
  public static final Size SOLO=LotroEnumsRegistry.getInstance().get(Size.class).getEntry(100);
  /**
   * Small Fellowship.
   */
  public static final Size SMALL_FELLOWSHIP=LotroEnumsRegistry.getInstance().get(Size.class).getEntry(101);
  /**
   * Fellowship.
   */
  public static final Size FELLOWSHIP=LotroEnumsRegistry.getInstance().get(Size.class).getEntry(102);
  /**
   * Raid.
   */
  public static final Size RAID=LotroEnumsRegistry.getInstance().get(Size.class).getEntry(103);
}
