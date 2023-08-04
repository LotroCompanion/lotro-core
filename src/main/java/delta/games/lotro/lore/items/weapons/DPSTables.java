package delta.games.lotro.lore.items.weapons;

import java.io.File;

import delta.games.lotro.common.utils.valueTables.ValueTablesManager;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Facade for DPS tables access.
 * @author DAM
 */
public class DPSTables
{
  private static ValueTablesManager _instance=null;

  /**
   * Get the DPS tables manager.
   * @return the DPS tables manager.
   */
  public static ValueTablesManager getDPSTablesManager()
  {
    if (_instance==null)
    {
      _instance=buildDPSTablesManager();
    }
    return _instance;
  }

  private static ValueTablesManager buildDPSTablesManager()
  {
    ValueTablesManager ret=new ValueTablesManager();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File dpsTablesFile=cfg.getFile(DataFiles.DPS_TABLES);
    ret.loadAll(dpsTablesFile);
    return ret;
  }
}
