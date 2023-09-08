package delta.games.lotro.lore.items.weapons;

import java.io.File;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.WeaponType;
import delta.games.lotro.lore.items.weapons.io.xml.WeaponSpeedTablesXMLParser;

/**
 * Weapon speeds manager.
 * @author DAM
 */
public class WeaponSpeedsManager
{
  private static WeaponSpeedsManager _instance=null;
  private WeaponSpeedTables _tables;

  /**
   * Get the speed tables manager.
   * @return the speed tables manager.
   */
  public static WeaponSpeedsManager getWeaponSpeedsManager()
  {
    if (_instance==null)
    {
      _instance=new WeaponSpeedsManager();
    }
    return _instance;
  }

  private WeaponSpeedsManager()
  {
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File speedTablesFile=cfg.getFile(DataFiles.SPEED_TABLES);
    _tables=new WeaponSpeedTablesXMLParser().parseXML(speedTablesFile);
  }

  /**
   * Get the speed entry for a given weapon type and speed code.
   * @param weaponType Weapon type.
   * @param speedCode Speed code.
   * @return A speed entry or <code>null</code> if not found.
   */
  public WeaponSpeedEntry getSpeedEntry(WeaponType weaponType, int speedCode)
  {
    return _tables.getEntry(weaponType,speedCode);
  }
}
