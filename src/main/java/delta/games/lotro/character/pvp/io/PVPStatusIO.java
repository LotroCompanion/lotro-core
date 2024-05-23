package delta.games.lotro.character.pvp.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.pvp.PVPStatus;
import delta.games.lotro.character.pvp.io.xml.PVPStatusXMLParser;
import delta.games.lotro.character.pvp.io.xml.PVPStatusXMLWriter;

/**
 * I/O methods for PVP status.
 * @author DAM
 */
public class PVPStatusIO
{
  /**
   * Load achievements summary.
   * @param character Parent character.
   * @return the loaded summary.
   */
  public static PVPStatus loadPVPStatus(CharacterFile character)
  {
    File fromFile=getPVPStatusFile(character);
    PVPStatus ret=null;
    if (fromFile.exists())
    {
      PVPStatusXMLParser parser=new PVPStatusXMLParser();
      ret=parser.parseXML(fromFile);
    }
    return ret;
  }

  /**
   * Save the PVP status for a character.
   * @param character Targeted character.
   * @param pvpStatus Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, PVPStatus pvpStatus)
  {
    File toFile=getPVPStatusFile(character);
    PVPStatusXMLWriter writer=new PVPStatusXMLWriter();
    boolean ok=writer.write(toFile,pvpStatus,EncodingNames.UTF_8);
    return ok;
  }

  private static File getPVPStatusFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File ret=new File(rootDir,"pvpStatus.xml");
    return ret;
  }
}
