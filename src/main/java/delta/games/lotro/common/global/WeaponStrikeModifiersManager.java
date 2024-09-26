package delta.games.lotro.common.global;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.enums.comparator.LotroEnumEntryCodeComparator;
import delta.games.lotro.lore.items.WeaponType;

/**
 * Manager for the set of weapon strike modifiers.
 * @author DAM
 */
public class WeaponStrikeModifiersManager
{
  private Map<WeaponType,WeaponStrikeModifiers> _map;

  /**
   * Constructor.
   */
  public WeaponStrikeModifiersManager()
  {
    _map=new HashMap<WeaponType,WeaponStrikeModifiers>();
  }

  /**
   * Get the modifiers for a given weapon type.
   * @param type Weapon type.
   * @return A modifiers or <code>null</code> if not found.
   */
  public WeaponStrikeModifiers getStrikeModifiers(WeaponType type)
  {
    return _map.get(type);
  }

  /**
   * Get the known weapon types.
   * @return A list of weapon types.
   */
  public List<WeaponType> getWeaponTypes()
  {
    List<WeaponType> ret=new ArrayList<WeaponType>();
    ret.addAll(_map.keySet());
    Collections.sort(ret,new LotroEnumEntryCodeComparator<WeaponType>());
    return ret;
  }

  /**
   * Set the striker modifiers for a weapon type.
   * @param modifiers Modifiers to register.
   */
  public void registerStrikeModifiers(WeaponStrikeModifiers modifiers)
  {
    WeaponType type=modifiers.getWeaponType();
    _map.put(type,modifiers);
  }
}
