package delta.games.lotro.character.status.skirmishes.io;

import java.io.File;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.status.skirmishes.SkirmishStatsManager;
import delta.games.lotro.character.status.skirmishes.io.xml.SkirmishStatsXMLParser;
import delta.games.lotro.character.status.skirmishes.io.xml.SkirmishStatsXMLWriter;

/**
 * I/O methods for skirmish stats.
 * @author DAM
 */
public class SkirmishStatsIo
{
  /**
   * Load the skirmish stats for a character.
   * @param character Targeted character.
   * @return A skirmish stats manager.
   */
  public static SkirmishStatsManager load(CharacterFile character)
  {
    File fromFile=getSkimirshStatsFile(character);
    SkirmishStatsManager stats=null;
    if (fromFile.exists())
    {
      SkirmishStatsXMLParser parser=new SkirmishStatsXMLParser();
      stats=parser.parseXML(fromFile);
    }
    if (stats==null)
    {
      stats=new SkirmishStatsManager();
      save(character,stats);
    }
    return stats;
  }

  /**
   * Save the skirmish stats for a character.
   * @param character Targeted character.
   * @param stats Skirmish stats to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean save(CharacterFile character, SkirmishStatsManager stats)
  {
    File toFile=getSkimirshStatsFile(character);
    SkirmishStatsXMLWriter writer=new SkirmishStatsXMLWriter();
    boolean ok=writer.write(toFile,stats,EncodingNames.UTF_8);
    return ok;
  }

  private static File getSkimirshStatsFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    File statusFile=new File(rootDir,"skirmishStats.xml");
    return statusFile;
  }
}
