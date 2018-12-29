package delta.games.lotro;

import delta.games.lotro.character.storage.currencies.CurrenciesUpdater;

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
