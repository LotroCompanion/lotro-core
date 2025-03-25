package delta.games.lotro.character.status.collections.birds.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.status.collections.birds.BirdsStatusManager;
import delta.games.lotro.character.status.collections.birds.io.xml.BirdsStatusXMLParser;
import delta.games.lotro.character.status.collections.birds.io.xml.BirdsStatusXMLWriter;

/**
 * I/O methods for birds status.
 * @author DAM
 */
public class BirdsStatusIo
{
  /**
   * Load the birds status for a character.
   * @param character Targeted character.
   * @return A birds status manager.
   */
  public static BirdsStatusManager load(CharacterFile character)
  {
    File fromFile=getStatusFile(character);
    BirdsStatusManager status=null;
    if (fromFile.exists())
    {
      BirdsStatusXMLParser parser=new BirdsStatusXMLParser();
      status=parser.parseXML(fromFile);
    }
    if (status==null)
    {
      status=new BirdsStatusManager();
      save(character,status);
    }
    return status;
  }

  /**
   * Save the birds status for a character.
   * @param character Targeted character.
   * @param status Status to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, BirdsStatusManager status)
  {
    File toFile=getStatusFile(character);
    BirdsStatusXMLWriter writer=new BirdsStatusXMLWriter();
    boolean ok=writer.write(toFile,status,EncodingNames.UTF_8);
    return ok;
  }

  private static File getStatusFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File statusFile=new File(rootDir,"birds.xml");
    return statusFile;
  }
}
