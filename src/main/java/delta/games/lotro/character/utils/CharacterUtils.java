package delta.games.lotro.character.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.account.Account;
import delta.games.lotro.account.AccountReference;
import delta.games.lotro.account.AccountsManager;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharactersManager;

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

  /**
   * Build a list of toons from a list of character IDs.
   * @param toonIds Input IDs.
   * @return A list of unique toons, possibly empty but never <code>null</code>.
   */
  public static List<CharacterFile> getToons(List<String> toonIds)
  {
    List<CharacterFile> toons=new ArrayList<CharacterFile>();
    CharactersManager manager=CharactersManager.getInstance();
    if ((toonIds!=null) && (!toonIds.isEmpty()))
    {
      Set<String> addedToons=new HashSet<String>();
      for(String toonID : toonIds)
      {
        if (!addedToons.contains(toonID))
        {
          CharacterFile toon=manager.getToonById(toonID);
          if (toon!=null)
          {
            toons.add(toon);
          }
          addedToons.add(toonID);
        }
      }
    }
    return toons;
  }
}
