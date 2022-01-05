package delta.games.lotro.character.status.travels.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.status.travels.AnchorsStatusManager;
import delta.games.lotro.character.status.travels.io.xml.AnchorsStatusXMLParser;
import delta.games.lotro.character.status.travels.io.xml.AnchorsStatusXMLWriter;

/**
 * I/O methods for anchors status.
 * @author DAM
 */
public class AnchorsStatusIo
{
  /**
   * Load the anchors status for a character.
   * @param character Targeted character.
   * @return An anchors status manager.
   */
  public static AnchorsStatusManager load(CharacterFile character)
  {
    File fromFile=getStatusFile(character);
    AnchorsStatusManager status=null;
    if (fromFile.exists())
    {
      AnchorsStatusXMLParser parser=new AnchorsStatusXMLParser();
      status=parser.parseXML(fromFile);
    }
    if (status==null)
    {
      status=new AnchorsStatusManager();
      save(character,status);
    }
    return status;
  }

  /**
   * Save the anchors status for a character.
   * @param character Targeted character.
   * @param status Status to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, AnchorsStatusManager status)
  {
    File toFile=getStatusFile(character);
    AnchorsStatusXMLWriter writer=new AnchorsStatusXMLWriter();
    boolean ok=writer.write(toFile,status,EncodingNames.UTF_8);
    return ok;
  }

  private static File getStatusFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File statusFile=new File(rootDir,"anchors.xml");
    return statusFile;
  }
}
