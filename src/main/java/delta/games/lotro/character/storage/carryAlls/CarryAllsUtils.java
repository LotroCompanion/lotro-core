package delta.games.lotro.character.storage.carryAlls;

import delta.games.lotro.account.Account;
import delta.games.lotro.account.AccountsManager;
import delta.games.lotro.character.CharacterFile;

/**
 * Utility methods related to carry-alls management.
 * @author DAM
 */
public class CarryAllsUtils
{
  /**
   * Build a carry-alls manager for a character.
   * @param toon Character to use.
   * @return A carry-alls manager.
   */
  public static CarryAllsManager buildCarryAllManager(CharacterFile toon)
  {
    Account account=getAccount(toon);
    if (account!=null)
    {
      String serverName=toon.getServerName();
      CarryAllsManager mgr=new CarryAllsManager(account,serverName);
      return mgr;
    }
    return null;
  }

  private static Account getAccount(CharacterFile toon)
  {
    AccountsManager accountsMgr=AccountsManager.getInstance();
    String accountName=toon.getAccountName();
    Account account=accountsMgr.getAccountByName(accountName);
    return account;
  }
}
