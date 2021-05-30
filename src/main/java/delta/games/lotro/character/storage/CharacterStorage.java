package delta.games.lotro.character.storage;

import delta.games.lotro.character.storage.bags.BagsManager;
import delta.games.lotro.character.storage.vaults.Vault;
import delta.games.lotro.character.storage.wallet.Wallet;

/**
 * Storage data for a single character.
 * @author DAM
 */
public class CharacterStorage
{
  /**
   * Own vault.
   */
  private Vault _ownVault;
  /**
   * Bags.
   */
  private BagsManager _bags;
  /**
   * Own wallet.
   */
  private Wallet _wallet;

  /**
   * Constructor.
   * @param ownVault Own vault.
   * @param bags Bags.
   * @param wallet Wallet.
   */
  public CharacterStorage(Vault ownVault, BagsManager bags, Wallet wallet)
  {
    _ownVault=ownVault;
    _bags=bags;
    _wallet=wallet;
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
   * Get the wallet of the associated character.
   * @return a wallet.
   */
  public Wallet getWallet()
  {
    return _wallet;
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
