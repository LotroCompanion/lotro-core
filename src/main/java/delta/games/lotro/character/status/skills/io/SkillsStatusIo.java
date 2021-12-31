package delta.games.lotro.character.status.skills.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.status.skills.SkillsStatusManager;
import delta.games.lotro.character.status.skills.io.xml.SkillsStatusXMLParser;
import delta.games.lotro.character.status.skills.io.xml.SkillsStatusXMLWriter;

/**
 * I/O methods for skills status.
 * @author DAM
 */
public class SkillsStatusIo
{
  /**
   * Load the skills status for a character.
   * @param character Targeted character.
   * @return A skills status manager.
   */
  public static SkillsStatusManager load(CharacterFile character)
  {
    File fromFile=getStatusFile(character);
    SkillsStatusManager status=null;
    if (fromFile.exists())
    {
      SkillsStatusXMLParser parser=new SkillsStatusXMLParser();
      status=parser.parseXML(fromFile);
    }
    if (status==null)
    {
      status=new SkillsStatusManager();
      save(character,status);
    }
    return status;
  }

  /**
   * Save the skills status for a character.
   * @param character Targeted character.
   * @param status Status to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, SkillsStatusManager status)
  {
    File toFile=getStatusFile(character);
    SkillsStatusXMLWriter writer=new SkillsStatusXMLWriter();
    boolean ok=writer.write(toFile,status,EncodingNames.UTF_8);
    return ok;
  }

  private static File getStatusFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File statusFile=new File(rootDir,"skills.xml");
    return statusFile;
  }
}
