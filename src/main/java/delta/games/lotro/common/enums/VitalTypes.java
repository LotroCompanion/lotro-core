package delta.games.lotro.common.enums;

/**
 * Well-known vital types.
 * @author DAM
 */
public class VitalTypes
{
  /**
   * Vital type: morale.
   */
  public static final VitalType MORALE=getByCode(1);
  /**
   * Vital type: power.
   */
  public static final VitalType POWER=getByCode(2);

  /**
   * Get a vital type using its code.
   * @param code Code to use.
   * @return A vital type or <code>null</code>.
   */
  public static VitalType getByCode(int code)
  {
    return LotroEnumsRegistry.getInstance().get(VitalType.class).getEntry(code);
  }
}
