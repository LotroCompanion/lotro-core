package delta.games.lotro.character.utils;

import delta.games.lotro.account.Account;
import delta.games.lotro.account.AccountReference;
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
    AccountReference accountID=character.getAccountID();
    if (accountID!=null)
    {
      AccountsManager accountsMgr=AccountsManager.getInstance();
      account=accountsMgr.getAccountByID(accountID);
    }
    return account;
  }
}
