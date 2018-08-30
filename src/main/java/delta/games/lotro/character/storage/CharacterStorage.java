package delta.games.lotro.character.storage;


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
  private Vault _bags;
  /**
   * Own wallet.
   */
  private Wallet _wallet;

  /**
   * Constructor.
   */
  public CharacterStorage()
  {
    _ownVault=new Vault();
    _bags=new Vault();
    _wallet=new Wallet();
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
  public Vault getBags()
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
    _bags.dump(level+1);
  }
}
