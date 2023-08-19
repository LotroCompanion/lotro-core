package delta.games.lotro.lore.items.weapons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.enums.comparator.LotroEnumEntryCodeComparator;
import delta.games.lotro.lore.items.WeaponType;

/**
 * Weapon speed tables.
 * @author DAM
 */
public class WeaponSpeedTables
{
  private Map<WeaponType,WeaponSpeedTable> _types;

  /**
   * Constructor.
   */
  public WeaponSpeedTables()
  {
    _types=new HashMap<WeaponType,WeaponSpeedTable>();
  }

  /**
   * Get the managed weapon types.
   * @return A sorted list of weapon types.
   */
  public List<WeaponType> getWeaponTypes()
  {
    List<WeaponType> ret=new ArrayList<WeaponType>(_types.keySet());
    Collections.sort(ret,new LotroEnumEntryCodeComparator<WeaponType>());
    return ret;
  }

  /**
   * Add a weapon speed data.
   * @param speed Data to add.
   */
  public void addWeaponSpeed(WeaponSpeedTable speed)
  {
    _types.put(speed.getWeaponType(),speed);
  }

  /**
   * Get the speed table for a weapon type.
   * @param type Weapon type.
   * @return A weapon speed table.
   */
  public WeaponSpeedTable getTable(WeaponType type)
  {
    return _types.get(type);
  }

  /**
   * Get the speed entry for a given weapon type and speed code.
   * @param weaponType Weapon type.
   * @param speedCode Speed code.
   * @return A speed entry or <code>null</code> if not found.
   */
  public WeaponSpeedEntry getEntry(WeaponType weaponType, int speedCode)
  {
    WeaponSpeedTable speed=_types.get(weaponType);
    if (speed!=null)
    {
      return speed.getEntry(speedCode);
    }
    return null;
  }
}
