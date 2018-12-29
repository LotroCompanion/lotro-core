package delta.games.lotro.plugins.updates;

import java.util.List;

import delta.games.lotro.account.Account;
import delta.games.lotro.account.AccountUtils;
import delta.games.lotro.account.AccountsManager;
import delta.games.lotro.account.events.AccountEvent;
import delta.games.lotro.account.events.AccountEventProperties;
import delta.games.lotro.account.events.AccountEventType;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharactersManager;
import delta.games.lotro.character.events.CharacterEvent;
import delta.games.lotro.character.events.CharacterEventType;
import delta.games.lotro.character.storage.AccountServerStorage;
import delta.games.lotro.character.storage.CharacterStorage;
import delta.games.lotro.character.storage.io.xml.StorageIO;
import delta.games.lotro.plugins.StorageLoader;
import delta.games.lotro.utils.events.EventsManager;

/**
 * Updated for character or account/server storage data.
 * @author DAM
 */
public class StorageUpdater
{
  /**
   * Update storage data for a single character.
   * @param toon Targeted character.
   * @return <code>true</code> if it was done, <code>false</code> otherwise.
   */
  public static boolean updateCharacterStorage(CharacterFile toon)
  {
    String serverName=toon.getServerName();
    String accountName=toon.getAccountName();

    // TODO if accountName is not set, automagically find out?
    if (accountName.isEmpty())
    {
      return false;
    }

    StorageLoader loader=new StorageLoader();
    AccountServerStorage storage=loader.loadStorage(accountName,serverName);
    return handleCharacterStorage(storage,toon);
  }

  private static boolean handleCharacterStorage(AccountServerStorage storage, CharacterFile toon)
  {
    boolean ret=false;
    if (storage!=null)
    {
      String characterName=toon.getName();
      CharacterStorage characterStorage=storage.getStorage(characterName,false);
      if (characterStorage!=null)
      {
        StorageIO.writeCharacterStorage(characterStorage,toon);
        // Broadcast event
        CharacterEvent event=new CharacterEvent(CharacterEventType.CHARACTER_STORAGE_UPDATED,toon,null);
        EventsManager.invokeEvent(event);
        ret=true;
      }
    }
    return ret;
  }

  /**
   * Update storage data for an account on a server.
   * <p>It updates the storage for all the characters on this account/server.
   * @param account Targeted account.
   * @param serverName Targeted server.
   * @return <code>true</code> if at least one update was done, <code>false</code> otherwise.
   */
  public static boolean updateAccountServerStorage(Account account, String serverName)
  {
    String accountName=account.getName();
    List<CharacterFile> toonsToUse=AccountUtils.getCharacters(accountName,serverName);
    boolean ret=false;
    if (toonsToUse.size()>0)
    {
      StorageLoader loader=new StorageLoader();
      AccountServerStorage storage=loader.loadStorage(accountName,serverName);
      if (storage!=null)
      {
        // Handle toon storage
        for(CharacterFile toon : toonsToUse)
        {
          boolean storageLoading=handleCharacterStorage(storage,toon);
          if (storageLoading)
          {
            ret=true;
          }
        }
        // Handle account/server storage
        StorageIO.writeAccountStorage(storage,account);
        AccountEvent event=new AccountEvent(AccountEventType.STORAGE_UPDATED,account);
        event.getProperties().setStringProperty(AccountEventProperties.SERVER_NAME,serverName);
        EventsManager.invokeEvent(event);
      }
    }
    return ret;
  }

  /**
   * Main method to test this class.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    Account account=AccountsManager.getInstance().getAccountByName("glorfindel666");
    if (account!=null)
    {
      updateAccountServerStorage(account,"Landroval");
      updateAccountServerStorage(account,"Elendilmir");
    }
    // Character
    // - with storage data
    CharacterFile toon=CharactersManager.getInstance().getToonById("Landroval","Meva");
    if (toon!=null)
    {
      updateCharacterStorage(toon);
    }
    // - without storage data (kiki/altinventory not used) or remote character
    toon=CharactersManager.getInstance().getToonById("Landroval","Blastof");
    if (toon!=null)
    {
      updateCharacterStorage(toon);
    }
  }
}
