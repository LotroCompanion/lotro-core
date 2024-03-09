package delta.games.lotro.character.status.traits.mountedAppearances;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.status.traits.shared.SlottedTraitsStatus;
import delta.games.lotro.character.status.traits.shared.io.xml.SlottedTraitsStatusXMLConstants;
import delta.games.lotro.character.status.traits.shared.io.xml.SlottedTraitsStatusXMLParser;
import delta.games.lotro.character.status.traits.shared.io.xml.SlottedTraitsStatusXMLWriter;

/**
 * I/O methods for mounted appearances status.
 * @author DAM
 */
public class MountedAppearancesIo
{
  /**
   * Load the mounted appearance status for a character.
   * @param character Targeted character.
   * @return A status.
   */
  public static SlottedTraitsStatus load(CharacterFile character)
  {
    File fromFile=getStatusFile(character);
    SlottedTraitsStatus status=null;
    if (fromFile.exists())
    {
      SlottedTraitsStatusXMLParser parser=new SlottedTraitsStatusXMLParser();
      status=parser.parseXML(fromFile);
    }
    if (status==null)
    {
      status=new SlottedTraitsStatus();
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
  public static boolean save(CharacterFile character, SlottedTraitsStatus status)
  {
    File toFile=getStatusFile(character);
    SlottedTraitsStatusXMLWriter writer=new SlottedTraitsStatusXMLWriter();
    boolean ok=writer.write(toFile,status,SlottedTraitsStatusXMLConstants.MOUNTED_APPEARANCES_STATUS_TAG,EncodingNames.UTF_8);
    return ok;
  }

  private static File getStatusFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File statusFile=new File(rootDir,"mountedAppearances.xml");
    return statusFile;
  }
}
