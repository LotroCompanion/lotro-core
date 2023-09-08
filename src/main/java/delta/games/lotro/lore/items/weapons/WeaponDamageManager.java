package delta.games.lotro.lore.items.weapons;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.enums.comparator.LotroEnumEntryCodeComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.WeaponType;
import delta.games.lotro.lore.items.weapons.io.xml.WeaponDamageXMLParser;

/**
 * Weapon damage manager.
 * @author DAM
 */
public class WeaponDamageManager
{
  private static WeaponDamageManager _instance=null;
  private Map<WeaponType,Float> _typeToVariance;

  /**
   * Get the weapon damage data manager.
   * @return the weapon damage data manager.
   */
  public static WeaponDamageManager getWeaponDamageManager()
  {
    if (_instance==null)
    {
      _instance=load();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public WeaponDamageManager()
  {
    _typeToVariance=new HashMap<WeaponType,Float>();
  }

  private static WeaponDamageManager load()
  {
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File weaponDamageFile=cfg.getFile(DataFiles.WEAPON_DAMAGE);
    return new WeaponDamageXMLParser().parseXML(weaponDamageFile);
  }

  /**
   * Get the managed weapon types.
   * @return A sorted list of weapon types.
   */
  public List<WeaponType> getWeaponTypes()
  {
    List<WeaponType> ret=new ArrayList<WeaponType>(_typeToVariance.keySet());
    Collections.sort(ret,new LotroEnumEntryCodeComparator<WeaponType>());
    return ret;
  }

  /**
   * Set the damage variance for the given type.
   * @param type Weapon type.
   * @param variance Variance.
   */
  public void setVariance(WeaponType type, float variance)
  {
    _typeToVariance.put(type,Float.valueOf(variance));
  }

  /**
   * Get the weapon variance for the given weapon type.
   * @param type Weapon type to use.
   * @return A variance or <code>null</code> if not defined.
   */
  public Float getVariance(WeaponType type)
  {
    return _typeToVariance.get(type);
  }
}
