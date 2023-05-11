package delta.games.lotro.common.blacklist.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.common.blacklist.Blacklist;
import delta.games.lotro.common.blacklist.io.xml.BlacklistXMLParser;
import delta.games.lotro.common.blacklist.io.xml.BlacklistXMLWriter;

/**
 * I/O methods for blacklists.
 * @author DAM
 */
public class BlackListIO
{
  /**
   * Get the blacklist for a given character.
   * @param toon Input character.
   * @param quest For quests or deeds?
   * @return A blacklist.
   */
  public static Blacklist load(CharacterFile toon, boolean quest)
  {
    String path=(quest?"quests":"deeds");
    return load(toon,path);
  }

  /**
   * Get a blacklist for a given character.
   * @param toon Input character.
   * @param key Blackilist usage key.
   * @return A blacklist.
   */
  public static Blacklist load(CharacterFile toon, String key)
  {
    String path="blacklist-"+key+".xml";
    File rootDir=toon.getRootDir();
    File inputFile=new File(rootDir,path);
    Blacklist blacklist=load(inputFile);
    if (blacklist==null)
    {
      blacklist=new Blacklist(inputFile);
    }
    return blacklist;
  }

  /**
   * Load a blacklist from a file.
   * @param from Input file.
   * @return A new blacklist.
   */
  public static Blacklist load(File from)
  {
    if (from.exists())
    {
      BlacklistXMLParser parser=new BlacklistXMLParser();
      return parser.parseXML(from);
    }
    return null;
  }

  /**
   * Save the given blacklist to its file.
   * @param blacklist Blacklist to save.
   * @return <code>true</code> if it was successful, <code>false</code> otherwise.
   */
  public static boolean save(Blacklist blacklist)
  {
    BlacklistXMLWriter writer=new BlacklistXMLWriter();
    return writer.write(blacklist.getFile(),blacklist,EncodingNames.UTF_8);
  }
}
