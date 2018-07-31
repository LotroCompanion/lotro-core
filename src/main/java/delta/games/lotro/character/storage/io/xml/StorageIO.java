package delta.games.lotro.character.storage.io.xml;

import java.io.File;

import delta.games.lotro.character.CharacterFile;
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
    File walletFile=getWalletFile(character);
    Wallet wallet=storage.getWallet();
    writer.writeWallet(walletFile,wallet);
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
}
