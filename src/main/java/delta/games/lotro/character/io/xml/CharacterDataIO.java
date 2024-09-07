package delta.games.lotro.character.io.xml;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterData;

/**
 * I/O methods for character data.
 * @author DAM
 */
public class CharacterDataIO
{
  private static final Logger LOGGER=LoggerFactory.getLogger(CharacterDataIO.class);

  /**
   * Get the character data for a given file.
   * @param file File to read.
   * @return A character data or <code>null</code> if a problem occurs.
   */
  public static CharacterData getCharacterDescription(File file)
  {
    CharacterData data=new CharacterData();
    data.setFile(file);
    CharacterXMLParser xmlInfoParser=new CharacterXMLParser();
    boolean ok=xmlInfoParser.parseXML(file,data);
    return (ok?data:null);
  }

  /**
   * Load/reload character data.
   * @param data Data to use.
   * @return <code>true</code> if it succeeded, <code>false</code> otherwise.
   */
  public static boolean loadCharacter(CharacterData data)
  {
    CharacterXMLParser xmlInfoParser=new CharacterXMLParser();
    boolean ok=xmlInfoParser.parseXML(data.getFile(),data);
    return ok;
  }

  /**
   * Write a new info file for this toon.
   * @param toFile File to write to.
   * @param info Character info to write.
   * @return <code>true</code> it it succeeds, <code>false</code> otherwise.
   */
  public static boolean saveInfo(File toFile, CharacterData info)
  {
    boolean ret=true;
    File parentFile=toFile.getParentFile();
    if (!parentFile.exists())
    {
      ret=parentFile.mkdirs();
      if (!ret)
      {
        LOGGER.error("Cannot create directory ["+parentFile+"]!");
      }
    }
    if (ret)
    {
      CharacterXMLWriter writer=new CharacterXMLWriter();
      ret=writer.write(toFile,info,EncodingNames.UTF_8);
    }
    return ret;
  }
}
