package delta.games.lotro.character.titles.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.titles.TitlesStatusManager;
import delta.games.lotro.character.titles.io.xml.TitlesStatusXMLParser;
import delta.games.lotro.character.titles.io.xml.TitlesStatusXMLWriter;

/**
 * I/O methods for titles status.
 * @author DAM
 */
public class TitlesStatusIo
{
  /**
   * Load the titles status for a character.
   * @param character Targeted character.
   * @return A titles status manager.
   */
  public static TitlesStatusManager load(CharacterFile character)
  {
    File fromFile=getStatusFile(character);
    TitlesStatusManager status=null;
    if (fromFile.exists())
    {
      TitlesStatusXMLParser parser=new TitlesStatusXMLParser();
      status=parser.parseXML(fromFile);
    }
    if (status==null)
    {
      status=new TitlesStatusManager();
      save(character,status);
    }
    return status;
  }

  /**
   * Save the titles status for a character.
   * @param character Targeted character.
   * @param status Status to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, TitlesStatusManager status)
  {
    File toFile=getStatusFile(character);
    TitlesStatusXMLWriter writer=new TitlesStatusXMLWriter();
    boolean ok=writer.write(toFile,status,EncodingNames.UTF_8);
    return ok;
  }

  private static File getStatusFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File statusFile=new File(rootDir,"titles.xml");
    return statusFile;
  }
}
