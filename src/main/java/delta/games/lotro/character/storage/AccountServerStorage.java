package delta.games.lotro.character.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.character.storage.vaults.Vault;
import delta.games.lotro.character.storage.wallet.Wallet;

/**
 * Storage for an account on a server.
 * @author DAM
 */
public class AccountServerStorage
{
  private String _account;
  private String _server;
  private Vault _sharedVault;
  private Wallet _sharedWallet;
  private HashMap<String,CharacterStorage> _storages;

  /**
   * Constructor.
   * @param account Account name.
   * @param server Server name.
   */
  public AccountServerStorage(String account, String server)
  {
    _account=account;
    _server=server;
    _sharedVault=new Vault();
    _sharedWallet=new Wallet();
    _storages=new HashMap<String,CharacterStorage>();
  }

  /**
   * Get the storage for a character.
   * @param characterName Name of the targeted character.
   * @return A character storage or <code>null</code>.
   */
  public CharacterStorage getStorage(String characterName)
  {
    CharacterStorage storage=_storages.get(characterName);
    return storage;
  }

  /**
   * Register a character storage.
   * @param characterName Character name for this storage.
   * @param storage Storage to add.
   */
  public void addStorage(String characterName, CharacterStorage storage)
  {
    _storages.put(characterName,storage);
  }

  /**
   * Get all managed characters.
   * @return A set of character names.
   */
  public Set<String> getCharacters()
  {
    return new HashSet<String>(_storages.keySet());
  }

  /**
   * Get the name of the managed account.
   * @return an account name.
   */
  public String getAccount()
  {
    return _account;
  }

  /**
   * Get the name of the managed server.
   * @return a server name.
   */
  public String getServer()
  {
    return _server;
  }

  /**
   * Get the shared vault.
   * @return the shared vault.
   */
  public Vault getSharedVault()
  {
    return _sharedVault;
  }

  /**
   * Set the shared vault.
   * @param sharedVault Vault to use.
   */
  public void setSharedVault(Vault sharedVault)
  {
    _sharedVault=sharedVault;
  }

  /**
   * Get the shared wallet.
   * @return the shared wallet.
   */
  public Wallet getSharedWallet()
  {
    return _sharedWallet;
  }

  /**
   * Set the shared wallet.
   * @param sharedWallet Wallet to use.
   */
  public void setSharedWallet(Wallet sharedWallet)
  {
    _sharedWallet=sharedWallet;
  }

  /**
   * Dump contents.
   */
  public void dump()
  {
    System.out.println("Account: "+_account);
    System.out.println("Server: "+_server);
    System.out.println("Shared Vault: ");
    _sharedVault.dump(1);
    System.out.println("Shared Wallet: ");
    _sharedWallet.dump(1);
    List<String> characterNames=new ArrayList<String>(_storages.keySet());
    Collections.sort(characterNames);
    for(String characterName : characterNames)
    {
      CharacterStorage storage=_storages.get(characterName);
      System.out.println(characterName);
      storage.dump(1);
    }
  }
}
