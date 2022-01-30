package delta.games.lotro.character.virtues.io;

import java.io.File;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.virtues.VirtuesStatus;
import delta.games.lotro.character.virtues.io.xml.VirtuesStatusXMLParser;
import delta.games.lotro.character.virtues.io.xml.VirtuesStatusXMLWriter;

/**
 * I/O methods for virtues status.
 * @author DAM
 */
public class VirtuesStatusIO
{
  /**
   * Load the virtues status for a character.
   * @param character Targeted character.
   * @return A virtues status manager.
   */
  public static VirtuesStatus load(CharacterFile character)
  {
    File fromFile=getStatusFile(character);
    VirtuesStatus status=null;
    if (fromFile.exists())
    {
      status=VirtuesStatusXMLParser.parseVirtuesStatusFile(fromFile);
    }
    if (status==null)
    {
      status=new VirtuesStatus();
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
  public static boolean save(CharacterFile character, VirtuesStatus status)
  {
    File toFile=getStatusFile(character);
    boolean ok=VirtuesStatusXMLWriter.write(toFile,status);
    return ok;
  }

  private static File getStatusFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File statusFile=new File(rootDir,"virtues.xml");
    return statusFile;
  }
}
