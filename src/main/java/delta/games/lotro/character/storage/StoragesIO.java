package delta.games.lotro.character.storage;

import delta.games.lotro.account.Account;
import delta.games.lotro.account.AccountsManager;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.storage.bags.BagsManager;
import delta.games.lotro.character.storage.bags.io.BagsIo;
import delta.games.lotro.character.storage.vaults.Vault;
import delta.games.lotro.character.storage.vaults.io.VaultsIo;
import delta.games.lotro.character.storage.wallet.Wallet;
import delta.games.lotro.character.storage.wallet.io.xml.WalletsIO;

/**
 * Storage I/O methods.
 * @author DAM
 */
public class StoragesIO
{
  /**
   * Load character storage.
   * @param character Parent character.
   * @return the loaded storage.
   */
  public static CharacterStorage loadCharacterStorage(CharacterFile character)
  {
    // Vault
    Vault ownVault=VaultsIo.load(character);
    // Bags
    BagsManager bagsManager=BagsIo.load(character);
    // Wallet
    Wallet wallet=WalletsIO.loadCharacterWallet(character);
    // Shared stuff
    Wallet sharedWallet=null;
    Vault sharedVault=null;
    String accountName=character.getAccountName();
    String serverName=character.getServerName();
    if ((accountName.length()>0) && (serverName.length()>0))
    {
      AccountsManager accountsMgr=AccountsManager.getInstance();
      Account account=accountsMgr.getAccountByName(accountName);
      if (account!=null)
      {
        sharedWallet=WalletsIO.loadAccountSharedWallet(account,serverName);
        sharedVault=VaultsIo.load(account,serverName);
      }
    }
    CharacterStorage storage=new CharacterStorage(sharedVault,ownVault,bagsManager,sharedWallet,wallet);
    return storage;
  }
}
