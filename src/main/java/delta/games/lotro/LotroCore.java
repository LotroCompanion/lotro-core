package delta.games.lotro;

import delta.games.lotro.character.storage.currencies.CurrenciesUpdater;
import delta.games.lotro.misc.migration.CarryAllsMigration;

/**
 * Facade for lotro-core.
 * @author DAM
 */
public class LotroCore
{
  private static CurrenciesUpdater _currenciesUpdater;

  /**
   * Init.
   */
  public static void init()
  {
    _currenciesUpdater=new CurrenciesUpdater();
    migration();
  }

  private static void migration()
  {
    new CarryAllsMigration().doIt();
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    if (_currenciesUpdater!=null)
    {
      _currenciesUpdater.dispose();
      _currenciesUpdater=null;
    }
  }
}
