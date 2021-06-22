package delta.games.lotro.character.achievables.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.achievables.AchievablesStatusManager;
import delta.games.lotro.character.achievables.io.xml.AchievablesStatusXMLParser;
import delta.games.lotro.character.achievables.io.xml.AchievablesStatusXMLWriter;

/**
 * I/O methods for deeds status.
 * @author DAM
 */
public class DeedsStatusIo
{
  /**
   * Load the deeds status for a character.
   * @param character Targeted character.
   * @return A deeds status.
   */
  public static AchievablesStatusManager load(CharacterFile character)
  {
    File fromFile=getStatusFile(character);
    AchievablesStatusManager status=null;
    if (fromFile.exists())
    {
      AchievablesStatusXMLParser parser=new AchievablesStatusXMLParser();
      status=parser.parseXML(fromFile);
    }
    if (status==null)
    {
      // Initialize from reputation status
      status=new AchievablesStatusManager();
      //ReputationStatus repStatus=character.getReputation();
      //SyncDeedsStatusAndReputationStatus.syncDeedsStatus(repStatus,status);
      save(character,status);
    }
    return status;
  }

  /**
   * Save the deeds status for a character.
   * @param character Targeted character.
   * @param status Status to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, AchievablesStatusManager status)
  {
    File toFile=getStatusFile(character);
    AchievablesStatusXMLWriter writer=new AchievablesStatusXMLWriter();
    boolean ok=writer.write(toFile,status,EncodingNames.UTF_8);
    return ok;
  }

  private static File getStatusFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File statusFile=new File(rootDir,"deedsStatus.xml");
    return statusFile;
  }
}
