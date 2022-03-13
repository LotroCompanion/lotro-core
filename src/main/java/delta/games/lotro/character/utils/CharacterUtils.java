package delta.games.lotro.character.utils;

import delta.games.lotro.account.Account;
import delta.games.lotro.account.AccountsManager;
import delta.games.lotro.character.CharacterFile;

/**
 * Utility methods related to characters management.
 * @author DAM
 */
public class CharacterUtils
{
  /**
   * Get the account for a character.
   * @param character Parent character.
   * @return an account or <code>null</code>.
   */
  public static Account getAccount(CharacterFile character)
  {
    if (character==null)
    {
      return null;
    }
    Account account=null;
    String accountName=character.getAccountName();
    if (accountName.length()>0)
    {
      AccountsManager accountsMgr=AccountsManager.getInstance();
      account=accountsMgr.getAccountByName(accountName);
    }
    return account;
  }
}
