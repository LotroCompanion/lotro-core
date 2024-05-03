package delta.games.lotro.character.storage.vaults.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.account.Account;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.storage.summary.CharacterStorageSummary;
import delta.games.lotro.character.storage.summary.SingleStorageSummary;
import delta.games.lotro.character.storage.summary.StorageSummaryIO;
import delta.games.lotro.character.storage.vaults.Vault;
import delta.games.lotro.character.storage.vaults.io.xml.VaultsXMLParser;
import delta.games.lotro.character.storage.vaults.io.xml.VaultsXMLWriter;

/**
 * I/O methods for vaults.
 * @author DAM
 */
public class VaultsIo
{
  /**
   * Load the own vault for a character.
   * @param character Targeted character.
   * @return A vault.
   */
  public static Vault load(CharacterFile character)
  {
    File fromFile=getVaultFile(character);
    Vault vault=null;
    if (fromFile.exists())
    {
      VaultsXMLParser parser=new VaultsXMLParser();
      vault=parser.parseXML(fromFile);
    }
    if (vault==null)
    {
      vault=new Vault();
      save(character,vault);
    }
    return vault;
  }

  /**
   * Save the own vault for a character.
   * @param character Targeted character.
   * @param vault Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, Vault vault)
  {
    File toFile=getVaultFile(character);
    VaultsXMLWriter writer=new VaultsXMLWriter();
    boolean ok=writer.write(toFile,vault,EncodingNames.UTF_8);
    saveSummary(character,vault);
    return ok;
  }

  private static void saveSummary(CharacterFile character, Vault vault)
  {
    CharacterStorageSummary summary=StorageSummaryIO.loadCharacterStorageSummary(character);
    SingleStorageSummary vaultSummary=summary.getOwnVault();
    int max=vault.getCapacity();
    vaultSummary.setMax(max);
    int used=vault.getUsed();
    vaultSummary.setAvailable(max-used);
    StorageSummaryIO.save(character,summary);
  }

  private static File getVaultFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File ownVaultFile=new File(rootDir,"characterVault.xml");
    return ownVaultFile;
  }

  /**
   * Load the shared vault for an account on a given server.
   * @param account Targeted account.
   * @param serverName Server name.
   * @return A vault.
   */
  public static Vault load(Account account, String serverName)
  {
    File fromFile=getSharedVaultFile(account,serverName);
    Vault vault=null;
    if (fromFile.exists())
    {
      VaultsXMLParser parser=new VaultsXMLParser();
      vault=parser.parseXML(fromFile);
    }
    if (vault==null)
    {
      vault=new Vault();
      save(account,serverName,vault);
    }
    return vault;
  }

  /**
   * Save the shared vault for an account/server.
   * @param account Targeted account.
   * @param serverName Server name.
   * @param vault Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(Account account, String serverName, Vault vault)
  {
    File toFile=getSharedVaultFile(account,serverName);
    VaultsXMLWriter writer=new VaultsXMLWriter();
    boolean ok=writer.write(toFile,vault,EncodingNames.UTF_8);
    return ok;
  }

  private static File getSharedVaultFile(Account account, String server)
  {
    File rootDir=account.getRootDir();
    File serverDir=new File(rootDir,server);
    return new File(serverDir,"sharedStorage.xml");
  }
}
