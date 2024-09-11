package delta.games.lotro.common.enums;

/**
 * Well-known damage qualifiers.
 * @author DAM
 */
public class ImplementUsageTypes
{
  /**
   * Implement usage: PRIMARY.
   */
  public static final ImplementUsageType PRIMARY=getByCode(2);
  /**
   * Implement usage: SECONDARY.
   */
  public static final ImplementUsageType SECONDARY=getByCode(6);
  /**
   * Implement usage: RANGED.
   */
  public static final ImplementUsageType RANGED=getByCode(3);
  /**
   * Implement usage: TACTICAL (DPS).
   */
  public static final ImplementUsageType TACTICAL_DPS=getByCode(4);
  /**
   * Implement usage: TACTICAL (healing).
   */
  public static final ImplementUsageType TACTICAL_HPS=getByCode(1);
  /**
   * Implement usage: NATURAL.
   */
  public static final ImplementUsageType NATURAL=getByCode(5);

  /**
   * Get an implement usage type using its code.
   * @param code Code to use.
   * @return An implement usage type or <code>null</code>.
   */
  public static ImplementUsageType getByCode(int code)
  {
    return LotroEnumsRegistry.getInstance().get(ImplementUsageType.class).getEntry(code);
  }
}
