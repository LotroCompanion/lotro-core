package delta.games.lotro.character.status.traits.raw.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.status.traits.raw.RawTraitsStatus;
import delta.games.lotro.character.status.traits.raw.io.xml.RawTraitsStatusXMLParser;
import delta.games.lotro.character.status.traits.raw.io.xml.RawTraitsStatusXMLWriter;

/**
 * I/O methods for raw traits status.
 * @author DAM
 */
public class RawTraitsIo
{
  /**
   * Load the raw traits status for a character.
   * @param character Targeted character.
   * @return A ra traits status.
   */
  public static RawTraitsStatus load(CharacterFile character)
  {
    File fromFile=getStatusFile(character);
    RawTraitsStatus status=null;
    if (fromFile.exists())
    {
      RawTraitsStatusXMLParser parser=new RawTraitsStatusXMLParser();
      status=parser.parseXML(fromFile);
    }
    if (status==null)
    {
      status=new RawTraitsStatus();
      save(character,status);
    }
    return status;
  }

  /**
   * Save the status for a character.
   * @param character Targeted character.
   * @param status Status to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, RawTraitsStatus status)
  {
    File toFile=getStatusFile(character);
    RawTraitsStatusXMLWriter writer=new RawTraitsStatusXMLWriter();
    boolean ok=writer.write(toFile,status,EncodingNames.UTF_8);
    return ok;
  }

  private static File getStatusFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File statusFile=new File(rootDir,"traits.xml");
    return statusFile;
  }
}
