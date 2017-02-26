package delta.games.lotro.character.storage;

/**
 * Storage data for a single character.
 * @author DAM
 */
public class CharacterStorage
{
  /**
   * Associated shared vault.
   */
  private Vault _sharedVault;
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
   * Shared wallet.
   */
  private Wallet _sharedWallet;

  /**
   * Constructor.
   * @param sharedVault Associated shared vault.
   * @param sharedWallet Associated shared wallet.
   */
  public CharacterStorage(Vault sharedVault, Wallet sharedWallet)
  {
    _sharedVault=sharedVault;
    _ownVault=new Vault();
    _bags=new Vault();
    _wallet=new Wallet();
    _sharedWallet=sharedWallet;
  }

  /**
   * Get the associated shared vault.
   * @return the shared vault.
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
   * Get the associated shared wallet.
   * @return the shared wallet.
   */
  public Wallet getSharedWallet()
  {
    return _sharedWallet;
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
