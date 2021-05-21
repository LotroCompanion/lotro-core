package delta.games.lotro.character.storage.io.xml;

import java.io.File;

import delta.games.lotro.account.Account;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.storage.AccountServerStorage;
import delta.games.lotro.character.storage.CharacterStorage;
import delta.games.lotro.character.storage.Vault;
import delta.games.lotro.character.storage.Wallet;

/**
 * Storage I/O methods.
 * @author DAM
 */
public class StorageIO
{
  /**
   * Load character storage.
   * @param character Parent character.
   * @return the loaded storage.
   */
  public static CharacterStorage loadCharacterStorage(CharacterFile character)
  {
    CharacterStorage storage=new CharacterStorage();
    // Vault
    File vaultFile=getVaultFile(character);
    if (vaultFile.exists())
    {
      Vault ownVault=storage.getOwnVault();
      StorageXMLParser.parseVaultXML(vaultFile,ownVault);
    }
    // Bags
    File bagsFile=getBagsFile(character);
    if (bagsFile.exists())
    {
      Vault bags=storage.getBags();
      StorageXMLParser.parseVaultXML(bagsFile,bags);
    }
    // Wallet
    File walletFile=getWalletFile(character);
    if (walletFile.exists())
    {
      Wallet wallet=storage.getWallet();
      StorageXMLParser.parseWalletXML(walletFile,wallet);
    }
    return storage;
  }

  /**
   * Write character storage.
   * @param storage Storage to use.
   * @param character Parent character.
   */
  public static void writeCharacterStorage(CharacterStorage storage, CharacterFile character)
  {
    StorageXMLWriter writer=new StorageXMLWriter();
    // Vault
    File vaultFile=getVaultFile(character);
    Vault ownVault=storage.getOwnVault();
    writer.writeVault(vaultFile,ownVault);
    // Bags
    File bagsFile=getBagsFile(character);
    Vault bags=storage.getBags();
    writer.writeVault(bagsFile,bags);
    // Wallet
    writeWallet(character,storage.getWallet());
  }

  /**
   * Write wallet.
   * @param character Character to use.
   * @param wallet Wallet to write.
   * @return <code>true</code> if successfull, <code>false</code> otherwise.
   */
  public static boolean writeWallet(CharacterFile character, Wallet wallet)
  {
    File walletFile=getWalletFile(character);
    StorageXMLWriter writer=new StorageXMLWriter();
    boolean ok=writer.writeWallet(walletFile,wallet);
    return ok;
  }

  /**
   * Load shared vault for an account.
   * @param account Parent account.
   * @param server Targeted server.
   * @return A vault.
   */
  public static Vault loadAccountSharedVault(Account account, String server)
  {
    Vault vault=new Vault();
    File sharedVaultFile=getSharedVaultFile(account,server);
    if (sharedVaultFile.canRead())
    {
      StorageXMLParser.parseVaultXML(sharedVaultFile,vault);
    }
    return vault;
  }

  /**
   * Load shared wallet for an account.
   * @param account Parent account.
   * @param server Targeted server.
   * @return A wallet.
   */
  public static Wallet loadAccountSharedWallet(Account account, String server)
  {
    Wallet wallet=new Wallet();
    File sharedWalletFile=getSharedWalletFile(account,server);
    if (sharedWalletFile.canRead())
    {
      StorageXMLParser.parseWalletXML(sharedWalletFile,wallet);
    }
    return wallet;
  }

  /**
   * Write account/server storage.
   * @param storage Storage to use.
   * @param account Parent account.
   */
  public static void writeAccountStorage(AccountServerStorage storage, Account account)
  {
    StorageXMLWriter writer=new StorageXMLWriter();
    // Shared vault
    File sharedVaultFile=getSharedVaultFile(account,storage.getServer());
    Vault sharedVault=storage.getSharedVault();
    writer.writeVault(sharedVaultFile,sharedVault);
    // Shared wallet
    writeSharedWallet(account,storage.getServer(),storage.getSharedWallet());
  }

  /**
   * Write shared wallet.
   * @param account Account to use.
   * @param serverName Server name.
   * @param sharedWallet Wallet to write.
   * @return <code>true</code> if successfull, <code>false</code> otherwise.
   */
  public static boolean writeSharedWallet(Account account, String serverName, Wallet sharedWallet)
  {
    File sharedWalletFile=getSharedWalletFile(account,serverName);
    StorageXMLWriter writer=new StorageXMLWriter();
    boolean ok=writer.writeWallet(sharedWalletFile,sharedWallet);
    return ok;
  }


  private static File getWalletFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    return new File(rootDir,"wallet.xml");
  }

  private static File getVaultFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    return new File(rootDir,"vault.xml");
  }

  private static File getBagsFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    return new File(rootDir,"bags.xml");
  }

  private static File getSharedVaultFile(Account account, String server)
  {
    File rootDir=account.getRootDir();
    File serverDir=new File(rootDir,server);
    return new File(serverDir,"sharedVault.xml");
  }

  private static File getSharedWalletFile(Account account, String server)
  {
    File rootDir=account.getRootDir();
    File serverDir=new File(rootDir,server);
    return new File(serverDir,"sharedWallet.xml");
  }
}
