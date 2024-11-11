package delta.games.lotro.common.enums;

/**
 * Combat state.
 * @author DAM
 */
public class CombatState extends LotroEnumEntry
{
  @Override
  public String toString()
  {
    return getLabel();
  }

  /**
   * Get a combat state using its code.
   * @param code Code of combat state.
   * @return A combat state instance or <code>null</code> if not found.
   */
  public static CombatState getCombatStateByCode(int code)
  {
    return LotroEnumsRegistry.getInstance().get(CombatState.class).getEntry(code);
  }
}
