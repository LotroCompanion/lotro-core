package delta.games.lotro.character.status.traits.racial;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.status.traits.shared.SlottedTraitsStatus;
import delta.games.lotro.character.status.traits.shared.io.xml.SlottedTraitsStatusXMLConstants;
import delta.games.lotro.character.status.traits.shared.io.xml.SlottedTraitsStatusXMLParser;
import delta.games.lotro.character.status.traits.shared.io.xml.SlottedTraitsStatusXMLWriter;

/**
 * I/O methods for racial traits status.
 * @author DAM
 */
public class RacialTraitsIo
{
  /**
   * Load the racial traits status for a character.
   * @param character Targeted character.
   * @return A racial traits status.
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
    boolean ok=writer.write(toFile,status,EncodingNames.UTF_8,SlottedTraitsStatusXMLConstants.RACIAL_TRAITS_STATUS_TAG);
    return ok;
  }

  private static File getStatusFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File statusFile=new File(rootDir,"racialTraits.xml");
    return statusFile;
  }
}
