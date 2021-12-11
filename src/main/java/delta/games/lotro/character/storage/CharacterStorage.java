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
   * Get the carry-alls from this character storage.
   * @return A list of carry-alls.
   */
  public List<CarryAllInstance> getCarryAlls()
  {
    List<CarryAllInstance> ret=new ArrayList<CarryAllInstance>();
    // Own vault
    ret.addAll(_ownVault.getCarryAlls());
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
