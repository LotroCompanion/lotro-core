package delta.games.lotro.common.enums;

/**
 * Well-known damage qualifiers.
 * @author DAM
 */
public class DamageQualifiers
{
  /**
   * Damage qualifier: MELEE.
   */
  public static final DamageQualifier MELEE=getByCode(1);
  /**
   * Damage qualifier: TACTICAL.
   */
  public static final DamageQualifier TACTICAL=getByCode(2);
  /**
   * Damage qualifier: RANGED.
   */
  public static final DamageQualifier RANGED=getByCode(3);

  /**
   * Get a damage qualifier using its code.
   * @param code Code to use.
   * @return A damage qualifier or <code>null</code>.
   */
  public static DamageQualifier getByCode(int code)
  {
    return LotroEnumsRegistry.getInstance().get(DamageQualifier.class).getEntry(code);
  }
}
