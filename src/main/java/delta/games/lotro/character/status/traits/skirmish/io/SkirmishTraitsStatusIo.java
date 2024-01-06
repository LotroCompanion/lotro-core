package delta.games.lotro.character.status.traits.skirmish.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.status.traits.skirmish.SkirmishTraitsStatus;
import delta.games.lotro.character.status.traits.skirmish.io.xml.SkirmishTraitsStatusXMLParser;
import delta.games.lotro.character.status.traits.skirmish.io.xml.SkirmishTraitsStatusXMLWriter;

/**
 * I/O methods for skirmish traits status.
 * @author DAM
 */
public class SkirmishTraitsStatusIo
{
  /**
   * Load the skirmish traits status for a character.
   * @param character Targeted character.
   * @return A skirmish traits status.
   */
  public static SkirmishTraitsStatus load(CharacterFile character)
  {
    File fromFile=getStatusFile(character);
    SkirmishTraitsStatus status=null;
    if (fromFile.exists())
    {
      SkirmishTraitsStatusXMLParser parser=new SkirmishTraitsStatusXMLParser();
      status=parser.parseXML(fromFile);
    }
    if (status==null)
    {
      status=new SkirmishTraitsStatus();
      save(character,status);
    }
    return status;
  }

  /**
   * Save the skirmish traits for a character.
   * @param character Targeted character.
   * @param status Status to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, SkirmishTraitsStatus status)
  {
    File toFile=getStatusFile(character);
    SkirmishTraitsStatusXMLWriter writer=new SkirmishTraitsStatusXMLWriter();
    boolean ok=writer.write(toFile,status,EncodingNames.UTF_8);
    return ok;
  }

  private static File getStatusFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File statusFile=new File(rootDir,"skirmishTraits.xml");
    return statusFile;
  }
}
