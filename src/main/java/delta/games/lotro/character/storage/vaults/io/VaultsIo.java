package delta.games.lotro.character.storage.vaults.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
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
    return ok;
  }

  private static File getVaultFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File bagsFile=new File(rootDir,"ownVault.xml");
    return bagsFile;
  }
}
