package delta.games.lotro.character.storage.bags.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.storage.bags.BagsManager;
import delta.games.lotro.character.storage.bags.io.xml.BagsXMLParser;
import delta.games.lotro.character.storage.bags.io.xml.BagsXMLWriter;

/**
 * I/O methods for bags.
 * @author DAM
 */
public class BagsIo
{
  /**
   * Load the bags for a character.
   * @param character Targeted character.
   * @return A bags manager.
   */
  public static BagsManager load(CharacterFile character)
  {
    File fromFile=getBagsFile(character);
    BagsManager bags=null;
    if (fromFile.exists())
    {
      BagsXMLParser parser=new BagsXMLParser();
      bags=parser.parseXML(fromFile);
    }
    if (bags==null)
    {
      bags=new BagsManager();
      save(character,bags);
    }
    return bags;
  }

  /**
   * Save the bags for a character.
   * @param character Targeted character.
   * @param bags Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, BagsManager bags)
  {
    File toFile=getBagsFile(character);
    BagsXMLWriter writer=new BagsXMLWriter();
    boolean ok=writer.write(toFile,bags,EncodingNames.UTF_8);
    return ok;
  }

  private static File getBagsFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File bagsFile=new File(rootDir,"inventory.xml");
    return bagsFile;
  }
}
