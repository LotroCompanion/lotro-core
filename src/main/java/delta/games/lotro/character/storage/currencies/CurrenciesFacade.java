package delta.games.lotro.character.storage.currencies;

import delta.games.lotro.account.Account;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.utils.CharacterUtils;
import delta.games.lotro.common.Scope;

/**
 * Facade to access currencies data.
 * @author DAM
 */
public class CurrenciesFacade
{
  // Context
  private CharacterFile _toon;
  private Account _account;
  private String _serverName;
  // Currencies access
  private CurrenciesManager _accountMgr;
  private CurrenciesManager _accountServerMgr;
  private CurrenciesManager _characterMgr;

  /**
   * Constructor for a character.
   * @param toon Character to use.
   */
  public CurrenciesFacade(CharacterFile toon)
  {
    Account account=CharacterUtils.getAccount(toon);
    if (account!=null)
    {
      String serverName=toon.getServerName();
      _accountServerMgr=new CurrenciesManager(account,serverName);
      _accountMgr=new CurrenciesManager(account);
      _serverName=serverName;
    }
    _toon=toon;
    _account=account;
    _characterMgr=new CurrenciesManager(toon);
  }

  /**
   * Constructor for an account/server.
   * @param account Account to use.
   * @param serverName Server name.
   */
  public CurrenciesFacade(Account account, String serverName)
  {
    _accountServerMgr=new CurrenciesManager(account,serverName);
    _accountMgr=new CurrenciesManager(account);
    _account=account;
    _serverName=serverName;
  }

  /**
   * Get a currency history.
   * @param currency Currency to use.
   * @return An history or <code>null</code> 
   */
  public CurrencyHistory getCurrencyHistory(Currency currency)
  {
    if (currency==null)
    {
      return null;
    }
    Scope scope=currency.getScope();
    if (scope==Scope.CHARACTER)
    {
      return _characterMgr.getHistory(currency);
    }
    if (scope==Scope.SERVER)
    {
      return _accountServerMgr.getHistory(currency);
    }
    if (scope==Scope.ACCOUNT)
    {
      return _accountMgr.getHistory(currency);
    }
    return null;
  }

  /**
   * Get the context.
   * @return A displayable context string.
   */
  public String getContext()
  {
    if (_toon!=null)
    {
      return _toon.getName()+"@"+_toon.getServerName();
    }
    if (_account!=null)
    {
      if (_serverName!=null)
      {
        return _account.getName()+"@"+_serverName;
      }
      return _account.getName();
    }
    return "?";
  }
}
