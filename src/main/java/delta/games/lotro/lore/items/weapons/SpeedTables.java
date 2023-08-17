package delta.games.lotro.lore.items.weapons;

import java.util.HashMap;
import java.util.Map;

import delta.games.lotro.lore.items.WeaponType;

/**
 * Weapon speed tables.
 * @author DAM
 */
public class SpeedTables
{
  private Map<WeaponType,WeaponSpeed> _types;

  /**
   * Constructor.
   */
  public SpeedTables()
  {
    _types=new HashMap<WeaponType,WeaponSpeed>();
  }

  /**
   * Add a weapon speed data.
   * @param speed Data to add.
   */
  public void addWeaponSpeed(WeaponSpeed speed)
  {
    _types.put(speed.getWeaponType(),speed);
  }

  /**
   * Get the speed entry for a given weapon type and speed code.
   * @param weaponType Weapon type.
   * @param speedCode Speed code.
   * @return A speed entry or <code>null</code> if not found.
   */
  public WeaponSpeedEntry getEntry(WeaponType weaponType, int speedCode)
  {
    WeaponSpeed speed=_types.get(weaponType);
    if (speed!=null)
    {
      return speed.getEntry(speedCode);
    }
    return null;
  }
}
