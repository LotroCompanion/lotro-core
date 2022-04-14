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
   * @param accountName Account name.
   * @return A possibly empty but not <code>null</code> list of server names.
   */
  public static List<String> getServers(String accountName)
  {
    Set<String> servers=new HashSet<String>();
    List<CharacterFile> characters=getCharacters(accountName,null);
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
    String accountName=account.getName();
    String serverName=accountOnServer.getServerName();
    return getCharacters(accountName,serverName);
  }

  /**
   * 
   * Get the characters for the given account/server.
   * @param accountName Account name.
   * @param server Server name or <code>null</code> to avoid filtering on server name.
   * @return A possibly empty but not <code>null</code> list of characters.
   */
  public static List<CharacterFile> getCharacters(String accountName, String server)
  {
    List<CharacterFile> ret=new ArrayList<CharacterFile>();
    CharactersManager charsManager=CharactersManager.getInstance();
    List<CharacterFile> characters=charsManager.getAllToons();
    for(CharacterFile character : characters)
    {
      String charAccount=character.getAccountName();
      if (accountName.equals(charAccount))
      {
        String charServer=character.getServerName();
        if ((server==null) || (server.equals(charServer)))
        {
          ret.add(character);
        }
      }
    }
    return ret;
  }
}
