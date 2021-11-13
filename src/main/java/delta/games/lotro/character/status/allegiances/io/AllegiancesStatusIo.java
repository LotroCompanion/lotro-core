package delta.games.lotro.character.status.allegiances.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.status.allegiances.AllegiancesStatusManager;
import delta.games.lotro.character.status.allegiances.io.xml.AllegiancesStatusXMLParser;
import delta.games.lotro.character.status.allegiances.io.xml.AllegiancesStatusXMLWriter;

/**
 * I/O methods for allegiances status.
 * @author DAM
 */
public class AllegiancesStatusIo
{
  /**
   * Load the allegiances status for a character.
   * @param character Targeted character.
   * @return An allegiances status manager.
   */
  public static AllegiancesStatusManager load(CharacterFile character)
  {
    File fromFile=getStatusFile(character);
    AllegiancesStatusManager status=null;
    if (fromFile.exists())
    {
      AllegiancesStatusXMLParser parser=new AllegiancesStatusXMLParser();
      status=parser.parseXML(fromFile);
    }
    if (status==null)
    {
      status=new AllegiancesStatusManager();
      save(character,status);
    }
    return status;
  }

  /**
   * Save the allegiances status for a character.
   * @param character Targeted character.
   * @param status Status to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, AllegiancesStatusManager status)
  {
    File toFile=getStatusFile(character);
    AllegiancesStatusXMLWriter writer=new AllegiancesStatusXMLWriter();
    boolean ok=writer.write(toFile,status,EncodingNames.UTF_8);
    return ok;
  }

  private static File getStatusFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File statusFile=new File(rootDir,"allegiances.xml");
    return statusFile;
  }
}
