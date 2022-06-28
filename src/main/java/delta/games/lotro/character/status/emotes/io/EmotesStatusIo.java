package delta.games.lotro.character.status.emotes.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.status.emotes.EmotesStatusManager;
import delta.games.lotro.character.status.emotes.io.xml.EmotesStatusXMLParser;
import delta.games.lotro.character.status.emotes.io.xml.EmotesStatusXMLWriter;

/**
 * I/O methods for emotes status.
 * @author DAM
 */
public class EmotesStatusIo
{
  /**
   * Load the emotes status for a character.
   * @param character Targeted character.
   * @return An emotes status manager.
   */
  public static EmotesStatusManager load(CharacterFile character)
  {
    File fromFile=getStatusFile(character);
    EmotesStatusManager status=null;
    if (fromFile.exists())
    {
      EmotesStatusXMLParser parser=new EmotesStatusXMLParser();
      status=parser.parseXML(fromFile);
    }
    if (status==null)
    {
      status=new EmotesStatusManager();
      save(character,status);
    }
    return status;
  }

  /**
   * Save the emotes status for a character.
   * @param character Targeted character.
   * @param status Status to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, EmotesStatusManager status)
  {
    File toFile=getStatusFile(character);
    EmotesStatusXMLWriter writer=new EmotesStatusXMLWriter();
    boolean ok=writer.write(toFile,status,EncodingNames.UTF_8);
    return ok;
  }

  private static File getStatusFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File statusFile=new File(rootDir,"emotes.xml");
    return statusFile;
  }
}
