package delta.games.lotro.lore.items.weapons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.lore.items.WeaponType;

/**
 * Speed data for a single weapon type.
 * @author DAM
 */
public class WeaponSpeedTable
{
  private WeaponType _type;
  private Map<Integer,WeaponSpeedEntry> _speedsMap;

  /**
   * Constructor.
   * @param weaponType Managed weapon type.
   */
  public WeaponSpeedTable(WeaponType weaponType)
  {
    _type=weaponType;
    _speedsMap=new HashMap<Integer,WeaponSpeedEntry>();
  }

  /**
   * Get the managed weapon type.
   * @return A weapon type.
   */
  public WeaponType getWeaponType()
  {
    return _type;
  }

  /**
   * Get the managed speed codes.
   * @return A sorted list of speed codes.
   */
  public List<Integer> getSpeedCodes()
  {
    List<Integer> ret=new ArrayList<Integer>(_speedsMap.keySet());
    Collections.sort(ret);
    return ret;
  }

  /**
   * Register a weapon speed entry.
   * @param entry Entry to add.
   */
  public void addSpeedEntry(WeaponSpeedEntry entry)
  {
    Integer key=Integer.valueOf(entry.getWeaponSpeedCode());
    _speedsMap.put(key,entry);
  }

  /**
   * Get the weapon speed entry for the given speed code.
   * @param speedCode Speed code.
   * @return An entry or <code>null</code> if not found.
   */
  public WeaponSpeedEntry getEntry(int speedCode)
  {
    Integer key=Integer.valueOf(speedCode);
    return _speedsMap.get(key);
  }
}
