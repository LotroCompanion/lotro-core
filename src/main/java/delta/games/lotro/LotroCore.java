package delta.games.lotro;

import delta.games.lotro.character.storage.currencies.CharacterCurrenciesListener;
import delta.games.lotro.misc.migration.CarryAllsMigration;

/**
 * Facade for lotro-core.
 * @author DAM
 */
public class LotroCore
{
  private static CharacterCurrenciesListener _characterCurrenciesListener;

  /**
   * Init.
   */
  public static void init()
  {
    migration();
    setupListeners();
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
    if (_characterCurrenciesListener!=null)
    {
      _characterCurrenciesListener.dispose();
      _characterCurrenciesListener=null;
    }
  }

  private static void setupListeners()
  {
    _characterCurrenciesListener=new CharacterCurrenciesListener();
  }
}
