package delta.games.lotro.character.status.hobbies.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.status.hobbies.HobbiesStatusManager;
import delta.games.lotro.character.status.hobbies.io.xml.HobbiesStatusXMLParser;
import delta.games.lotro.character.status.hobbies.io.xml.HobbiesStatusXMLWriter;

/**
 * I/O methods for hobbies status.
 * @author DAM
 */
public class HobbiesStatusIo
{
  /**
   * Load the hobbies status for a character.
   * @param character Targeted character.
   * @return A hobbies status manager.
   */
  public static HobbiesStatusManager load(CharacterFile character)
  {
    File fromFile=getStatusFile(character);
    HobbiesStatusManager status=null;
    if (fromFile.exists())
    {
      HobbiesStatusXMLParser parser=new HobbiesStatusXMLParser();
      status=parser.parseXML(fromFile);
    }
    if (status==null)
    {
      status=new HobbiesStatusManager();
      save(character,status);
    }
    return status;
  }

  /**
   * Save the hobbies status for a character.
   * @param character Targeted character.
   * @param status Status to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, HobbiesStatusManager status)
  {
    File toFile=getStatusFile(character);
    HobbiesStatusXMLWriter writer=new HobbiesStatusXMLWriter();
    boolean ok=writer.write(toFile,status,EncodingNames.UTF_8);
    return ok;
  }

  private static File getStatusFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File statusFile=new File(rootDir,"hobbies.xml");
    return statusFile;
  }
}
