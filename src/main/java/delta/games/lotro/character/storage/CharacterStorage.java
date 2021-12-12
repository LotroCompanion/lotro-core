package delta.games.lotro.character.storage;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.storage.bags.BagsManager;
import delta.games.lotro.character.storage.vaults.Vault;
import delta.games.lotro.character.storage.wallet.Wallet;
import delta.games.lotro.lore.items.carryalls.CarryAllInstance;

/**
 * Storage data for a single character.
 * @author DAM
 */
public class CharacterStorage
{
  /**
   * Shared vault.
   */
  private Vault _sharedVault;
  /**
   * Own vault.
   */
  private Vault _ownVault;
  /**
   * Bags.
   */
  private BagsManager _bags;
  /**
   * Shared wallet.
   */
  private Wallet _sharedWallet;
  /**
   * Own wallet.
   */
  private Wallet _wallet;

  /**
   * Constructor.
   * @param sharedVault Shared vault.
   * @param ownVault Own vault.
   * @param bags Bags.
   * @param sharedWallet Shared wallet.
   * @param wallet Wallet.
   */
  public CharacterStorage(Vault sharedVault, Vault ownVault, BagsManager bags, Wallet sharedWallet, Wallet wallet)
  {
    _sharedVault=sharedVault;
    _ownVault=ownVault;
    _bags=bags;
    _sharedWallet=sharedWallet;
    _wallet=wallet;
  }

  /**
   * Get the shared vault of the associated character.
   * @return a vault.
   */
  public Vault getSharedVault()
  {
    return _sharedVault;
  }

  /**
   * Get the vault of the associated character.
   * @return a vault.
   */
  public Vault getOwnVault()
  {
    return _ownVault;
  }

  /**
   * Get the bags of the associated character.
   * @return a vault.
   */
  public BagsManager getBags()
  {
    return _bags;
  }

  /**
   * Get the shared wallet of the associated character.
   * @return a wallet.
   */
  public Wallet getSharedWallet()
  {
    return _sharedWallet;
  }

  /**
   * Get the wallet of the associated character.
   * @return a wallet.
   */
  public Wallet getWallet()
  {
    return _wallet;
  }

  /**
   * Get the carry-alls from this character storage.
   * @param includeShared Include shared carry-alls or not.
   * @return A list of carry-alls.
   */
  public List<CarryAllInstance> getCarryAlls(boolean includeShared)
  {
    List<CarryAllInstance> ret=new ArrayList<CarryAllInstance>();
    // Own vault
    ret.addAll(_ownVault.getCarryAlls());
    if (includeShared)
    {
      if (_sharedVault!=null)
      {
        ret.addAll(_sharedVault.getCarryAlls());
      }
    }
    // Bags
    ret.addAll(_bags.getCarryAlls());
    // Wallet
    // ... no carry alls in the wallet!
    return ret;
  }

  /**
   * Dump contents.
   * @param level Indentation level.
   */
  public void dump(int level)
  {
    for(int i=0;i<level;i++) System.out.print('\t');
    System.out.println("Own vault:");
    _ownVault.dump(level+1);
    for(int i=0;i<level;i++) System.out.print('\t');
    System.out.println("Own wallet:");
    _wallet.dump(level+1);
    for(int i=0;i<level;i++) System.out.print('\t');
    System.out.println("Bags:");
    _bags.dumpContents();
  }
}
