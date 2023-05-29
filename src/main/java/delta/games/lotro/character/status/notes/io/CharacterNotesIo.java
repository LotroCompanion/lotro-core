package delta.games.lotro.character.status.notes.io;

import java.io.File;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.status.notes.CharacterNotes;
import delta.games.lotro.character.status.notes.io.xml.CharacterNotesXMLParser;
import delta.games.lotro.character.status.notes.io.xml.CharacterNotesXMLWriter;

/**
 * I/O methods for character notes.
 * @author DAM
 */
public class CharacterNotesIo
{
  /**
   * Load the character notes for a character.
   * @param character Targeted character.
   * @return Some character notes.
   */
  public static CharacterNotes load(CharacterFile character)
  {
    File fromFile=getDataFile(character);
    CharacterNotes ret=null;
    if (fromFile.exists())
    {
      CharacterNotesXMLParser parser=new CharacterNotesXMLParser();
      ret=parser.parseXML(fromFile);
    }
    if (ret==null)
    {
      ret=new CharacterNotes();
      save(character,ret);
    }
    return ret;
  }

  /**
   * Save the notes for a character.
   * @param character Targeted character.
   * @param data Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, CharacterNotes data)
  {
    File toFile=getDataFile(character);
    CharacterNotesXMLWriter writer=new CharacterNotesXMLWriter();
    boolean ok=writer.write(toFile,data);
    return ok;
  }

  private static File getDataFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File statusFile=new File(rootDir,"notes.xml");
    return statusFile;
  }
}
