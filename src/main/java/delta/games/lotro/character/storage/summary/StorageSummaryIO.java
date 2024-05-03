package delta.games.lotro.character.storage.summary;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.storage.bags.BagsManager;
import delta.games.lotro.character.storage.bags.io.BagsIo;
import delta.games.lotro.character.storage.summary.io.xml.StorageSummaryXMLParser;
import delta.games.lotro.character.storage.summary.io.xml.StorageSummaryXMLWriter;
import delta.games.lotro.character.storage.vaults.Vault;
import delta.games.lotro.character.storage.vaults.io.VaultsIo;

/**
 * I/O methods for storage summaries.
 * @author DAM
 */
public class StorageSummaryIO
{
  /**
   * Load character storage summary.
   * @param character Parent character.
   * @return the loaded storage summary.
   */
  public static CharacterStorageSummary loadCharacterStorageSummary(CharacterFile character)
  {
    File fromFile=getStorageSummaryFile(character);
    CharacterStorageSummary ret=null;
    if (fromFile.exists())
    {
      StorageSummaryXMLParser parser=new StorageSummaryXMLParser();
      ret=parser.parseXML(fromFile);
    }
    if (ret==null)
    {
      ret=buildStorageSummaryFromDetails(character);
      save(character,ret);
    }
    return ret;
  }

  /**
   * Save the storage summary for a character.
   * @param character Targeted character.
   * @param summary Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, CharacterStorageSummary summary)
  {
    File toFile=getStorageSummaryFile(character);
    StorageSummaryXMLWriter writer=new StorageSummaryXMLWriter();
    boolean ok=writer.write(toFile,summary,EncodingNames.UTF_8);
    return ok;
  }

  private static File getStorageSummaryFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File ret=new File(rootDir,"storageSummary.xml");
    return ret;
  }

  /**
   * Build a character storage summary from available storage details.
   * @param character Targeted character.
   * @return the loaded data.
   */
  public static CharacterStorageSummary buildStorageSummaryFromDetails(CharacterFile character)
  {
    CharacterStorageSummary ret=new CharacterStorageSummary();
    // Bags
    BagsManager bagsManager=BagsIo.load(character);
    SingleStorageSummary bagsSummary=ret.getBags();
    int bagsMax=bagsManager.getCapacity();
    int bagsUsed=bagsManager.getUsed();
    bagsSummary.setAvailable(bagsMax-bagsUsed);
    bagsSummary.setMax(bagsMax);
    // Own vault
    Vault vault=VaultsIo.load(character);
    SingleStorageSummary vaultSummary=ret.getOwnVault();
    int vaultMax=vault.getCapacity();
    int vaultUsed=vault.getUsed();
    vaultSummary.setAvailable(vaultMax-vaultUsed);
    vaultSummary.setMax(vaultMax);
    return ret;
  }
}
