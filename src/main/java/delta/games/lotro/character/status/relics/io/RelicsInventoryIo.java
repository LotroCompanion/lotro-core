package delta.games.lotro.character.status.relics.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.status.relics.RelicsInventory;
import delta.games.lotro.character.status.relics.io.xml.RelicsInventoryXMLParser;
import delta.games.lotro.character.status.relics.io.xml.RelicsInventoryXMLWriter;

/**
 * I/O methods for the relics inventory.
 * @author DAM
 */
public class RelicsInventoryIo
{
  /**
   * Load the relics inventory for a character.
   * @param character Targeted character.
   * @return A relics inventory.
   */
  public static RelicsInventory load(CharacterFile character)
  {
    File fromFile=getRelicsInventoryFile(character);
    RelicsInventory ret=null;
    if (fromFile.exists())
    {
      RelicsInventoryXMLParser parser=new RelicsInventoryXMLParser();
      ret=parser.parseXML(fromFile);
    }
    if (ret==null)
    {
      ret=new RelicsInventory();
      save(character,ret);
    }
    return ret;
  }

  /**
   * Save the relics inventory for a character.
   * @param character Targeted character.
   * @param inventory Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, RelicsInventory inventory)
  {
    File toFile=getRelicsInventoryFile(character);
    RelicsInventoryXMLWriter writer=new RelicsInventoryXMLWriter();
    boolean ok=writer.write(toFile,inventory,EncodingNames.UTF_8);
    return ok;
  }

  private static File getRelicsInventoryFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File statusFile=new File(rootDir,"skirmishStats.xml");
    return statusFile;
  }
}
