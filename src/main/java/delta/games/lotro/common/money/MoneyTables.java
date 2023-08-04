package delta.games.lotro.common.money;

import java.io.File;

import delta.games.lotro.common.utils.valueTables.ValueTablesManager;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Facade for money tables access.
 * @author DAM
 */
public class MoneyTables
{
  private static ValueTablesManager _instance=null;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static ValueTablesManager getMoneyTablesManager()
  {
    if (_instance==null)
    {
      _instance=buildMoneyTablesManager();
    }
    return _instance;
  }

  private static ValueTablesManager buildMoneyTablesManager()
  {
    ValueTablesManager ret=new ValueTablesManager();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File valueTablesFile=cfg.getFile(DataFiles.VALUE_TABLES);
    ret.loadAll(valueTablesFile);
    return ret;
  }
}
