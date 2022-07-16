package delta.games.lotro.common.blacklist;

import java.io.File;
import java.util.List;

import delta.common.utils.NumericTools;
import delta.common.utils.files.TextFileWriter;
import delta.common.utils.text.TextUtils;
import delta.games.lotro.character.CharacterFile;

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
    return blacklist;
  }

  /**
   * Load a blacklist from a file.
   * @param from Input file.
   * @return A new blacklist.
   */
  public static Blacklist load(File from)
  {
    Blacklist ret=new Blacklist(from);
    if (!from.canRead())
    {
      return ret;
    }
    List<String> lines=TextUtils.readAsLines(from);
    if (lines!=null)
    {
      for(String line : lines)
      {
        Integer id=NumericTools.parseInteger(line);
        if (id!=null)
        {
          ret.add(id.intValue());
        }
      }
    }
    return ret;
  }

  /**
   * Save the given blacklist to its file.
   * @param blacklist Blacklist to save.
   * @return <code>true</code> if it was successful, <code>false</code> otherwise.
   */
  public static boolean save(Blacklist blacklist)
  {
    TextFileWriter writer=new TextFileWriter(blacklist.getFile());
    if (!writer.start())
    {
      return false;
    }
    for(Integer id : blacklist.getAllBLacklistedIDs())
    {
      writer.writeNextLine(id.longValue());
    }
    writer.terminate();
    return true;
  }

  private static File getBlacklistFile(CharacterFile toon, boolean quest)
  {
    File rootDir=toon.getRootDir();
    String path=quest?"blacklist-quests.xml":"blacklist-deeds.xml";
    return new File(rootDir,path);
  }
}
