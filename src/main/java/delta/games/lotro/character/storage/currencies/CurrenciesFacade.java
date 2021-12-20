package delta.games.lotro.character.storage.currencies;

import delta.games.lotro.account.Account;
import delta.games.lotro.account.AccountsManager;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharactersManager;
import delta.games.lotro.character.storage.currencies.io.CurrenciesIo;

/**
 * Facade for curreny-related facilities.
 * @author DAM
 */
public class CurrenciesFacade
{
  /**
   * Update the currency value for a single character.
   * @param server Server name.
   * @param characterName Character name.
   * @param currencyId Currency identifier.
   * @param value Value to set.
   * @param withHistory Indicates if we shall track history or not.
   */
  public static void updateToonCurrency(String server, String characterName, String currencyId, int value, boolean withHistory)
  {
    long now=System.currentTimeMillis();
    CharactersManager charsManager=CharactersManager.getInstance();
    CharacterFile character=charsManager.getToonById(server,characterName);
    if (character!=null)
    {
      CurrenciesSummary summary=CurrenciesIo.load(character);
      CurrencyStatus status=summary.getCurrency(currencyId,true);
      status.setDate(now);
      status.setValue(value);
      CurrenciesIo.save(character,summary);
      if (withHistory)
      {
        Currency currency=Currencies.get().getByKey(currencyId);
        CurrencyHistory history=CurrenciesIo.load(character,currency);
        if (history==null)
        {
          history=new CurrencyHistory(currency);
        }
        history.getStorage().setValueAt(now,value);
        CurrenciesIo.save(character,history);
      }
    }
  }

  /**
   * Update the currency value for an account on a server.
   * @param server Server name.
   * @param accountName Account name.
   * @param currencyId Currency identifier.
   * @param value Value to set.
   * @param withHistory Indicates if we shall track history or not.
   */
  public static void updateAccountServerCurrency(String server, String accountName, String currencyId, int value, boolean withHistory)
  {
    Account account=AccountsManager.getInstance().getAccountByName(accountName);
    if (account!=null)
    {
      long now=System.currentTimeMillis();
      CurrenciesSummary summary=CurrenciesIo.load(account,server);
      CurrencyStatus status=summary.getCurrency(currencyId,true);
      status.setDate(now);
      status.setValue(value);
      CurrenciesIo.save(account,server,summary);
      if (withHistory)
      {
        Currency currency=Currencies.get().getByKey(currencyId);
        CurrencyHistory history=CurrenciesIo.load(account,server,currency);
        if (history==null)
        {
          history=new CurrencyHistory(currency);
        }
        history.getStorage().setValueAt(now,value);
        CurrenciesIo.save(account,server,history);
      }
    }
  }
}
