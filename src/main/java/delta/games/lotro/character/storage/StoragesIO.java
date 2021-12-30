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
    Wallet sharedWallet=loadSharedWallet(character);
    Vault sharedVault=loadSharedVault(character);
    CharacterStorage storage=new CharacterStorage(sharedVault,ownVault,bagsManager,sharedWallet,wallet);
    return storage;
  }

  /**
   * Load shared vault.
   * @param character Parent character.
   * @return a vault or <code>null</code>.
   */
  public static Vault loadSharedVault(CharacterFile character)
  {
    Vault sharedVault=null;
    Account account=getAccount(character);
    String serverName=character.getServerName();
    if ((account!=null) && (serverName.length()>0))
    {
      sharedVault=VaultsIo.load(account,serverName);
    }
    return sharedVault;
  }

  /**
   * Load shared wallet.
   * @param character Parent character.
   * @return a vault or <code>null</code>.
   */
  public static Wallet loadSharedWallet(CharacterFile character)
  {
    Wallet sharedWallet=null;
    Account account=getAccount(character);
    String serverName=character.getServerName();
    if ((account!=null) && (serverName.length()>0))
    {
      sharedWallet=WalletsIO.loadAccountSharedWallet(account,serverName);
    }
    return sharedWallet;
  }

  /**
   * Get the account for a character.
   * @param character Parent character.
   * @return an account or <code>null</code>.
   */
  public static Account getAccount(CharacterFile character)
  {
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
