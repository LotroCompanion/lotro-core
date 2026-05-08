package delta.games.lotro.character.status.portraitFrames.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.status.portraitFrames.PortraitFramesStatus;
import delta.games.lotro.character.status.portraitFrames.io.xml.PortraitFramesStatusXMLParser;
import delta.games.lotro.character.status.portraitFrames.io.xml.PortraitFramesStatusXMLWriter;

/**
 * I/O methods for the status of portrait frames.
 * @author DAM
 */
public class PortraitFramesStatusIo
{
  /**
   * Load the portrait frames status for a character.
   * @param character Targeted character.
   * @return A portrait frames status manager.
   */
  public static PortraitFramesStatus load(CharacterFile character)
  {
    File fromFile=getStatusFile(character);
    PortraitFramesStatus status=null;
    if (fromFile.exists())
    {
      PortraitFramesStatusXMLParser parser=new PortraitFramesStatusXMLParser();
      status=parser.parseXML(fromFile);
    }
    if (status==null)
    {
      status=new PortraitFramesStatus();
      save(character,status);
    }
    return status;
  }

  /**
   * Save the portrait frames status for a character.
   * @param character Targeted character.
   * @param status Status to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, PortraitFramesStatus status)
  {
    File toFile=getStatusFile(character);
    PortraitFramesStatusXMLWriter writer=new PortraitFramesStatusXMLWriter();
    boolean ok=writer.write(toFile,status,EncodingNames.UTF_8);
    return ok;
  }

  private static File getStatusFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File statusFile=new File(rootDir,"portraitFrames.xml");
    return statusFile;
  }
}
