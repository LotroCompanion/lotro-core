package delta.games.lotro.account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharactersManager;

/**
 * Utility methods related to accounts.
 * @author DAM
 */
public class AccountUtils
{
  /**
   * Get the servers with characters for the given account.
   * @param accountSummary Account summary.
   * @return A possibly empty but not <code>null</code> list of server names.
   */
  public static List<String> getServers(AccountSummary accountSummary)
  {
    Set<String> servers=new HashSet<String>();
    List<CharacterFile> characters=getCharacters(accountSummary,null);
    for(CharacterFile character : characters)
    {
      String serverName=character.getServerName();
      if ((serverName!=null) && (!serverName.isEmpty()))
      {
        servers.add(serverName);
      }
    }
    List<String> ret=new ArrayList<String>(servers);
    Collections.sort(ret);
    return ret;
  }

  /**
   * 
   * Get the characters for the given account/server.
   * @param accountOnServer Account/server to use.
   * @return A possibly empty but not <code>null</code> list of characters.
   */
  public static List<CharacterFile> getCharacters(AccountOnServer accountOnServer)
  {
    Account account=accountOnServer.getAccount();
    AccountSummary accountSummary=account.getSummary();
    String serverName=accountOnServer.getServerName();
    return getCharacters(accountSummary,serverName);
  }

  /**
   * Get the characters for the given account/server.
   * @param accountSummary Account summary.
   * @param server Server name or <code>null</code> to avoid filtering on server name.
   * @return A possibly empty but not <code>null</code> list of characters.
   */
  public static List<CharacterFile> getCharacters(AccountSummary accountSummary, String server)
  {
    List<CharacterFile> ret=new ArrayList<CharacterFile>();
    List<CharacterFile> characters=CharactersManager.getInstance().getAllToons();
    List<Account> accounts=AccountsManager.getInstance().getAllAccounts();
    CharacterAccountMatcher matcher=new CharacterAccountMatcher();
    matcher.setData(accounts,characters);
    List<CharacterFile> charactersForAccount=matcher.getCharacters(accountSummary.getAccountID());
    for(CharacterFile character : charactersForAccount)
    {
      String charServer=character.getServerName();
      if ((server==null) || (server.equals(charServer)))
      {
        ret.add(character);
      }
    }
    return ret;
  }
}
