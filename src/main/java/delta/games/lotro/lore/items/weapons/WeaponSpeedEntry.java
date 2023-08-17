package delta.games.lotro.lore.items.weapons;

/**
 * Weapon speed entry.
 * <p>
 * For a given weapon type and speed code, gives:
 * <ul>
 * <li>a base duration,
 * <li>a base animation duration modifier.
 * </ul>
 * @author DAM
 */
public class WeaponSpeedEntry
{
  private int _weaponSpeedCode;
  private float _baseActionDuration;
  private float _baseAnimDurationMod;

  /**
   * Constructor.
   * @param speedCode Speed code.
   * @param baseActionDuration Base action duration.
   * @param baseAnimDurationModifier Base animation duration multiplier modifier.
   */
  public WeaponSpeedEntry(int speedCode, float baseActionDuration, float baseAnimDurationModifier)
  {
    _weaponSpeedCode=speedCode;
    _baseActionDuration=baseActionDuration;
    _baseAnimDurationMod=baseAnimDurationModifier;
  }

  /**
   * Get the managed speed code.
   * @return a weapon speed code.
   */
  public int getWeaponSpeedCode()
  {
    return _weaponSpeedCode;
  }

  /**
   * Get the base action duration.
   * @return a value (unit: unknown, but grows if weapon is slower).
   */
  public float getBaseActionDuration()
  {
    return _baseActionDuration;
  }

  /**
   * Get the base animation duration multiplier modifier.
   * @return a value (positive or negative, higher is slower).
   */
  public float getBaseAnimationDurationMultiplierModifier()
  {
    return _baseAnimDurationMod;
  }
}
