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
    File inputFile=getBlacklistFile(toon,quest);
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

  private static File getBlacklistFile(CharacterFile toon, boolean quest)
  {
    File rootDir=toon.getRootDir();
    String path=quest?"blacklist-quests.xml":"blacklist-deeds.xml";
    return new File(rootDir,path);
  }
}
