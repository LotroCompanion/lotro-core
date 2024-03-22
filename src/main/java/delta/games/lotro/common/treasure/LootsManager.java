package delta.games.lotro.common.treasure;

import java.io.File;
import java.io.PrintStream;

import org.apache.log4j.Logger;

import delta.games.lotro.common.treasure.io.xml.TreasureXMLParser;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.utils.Registry;

/**
 * Loots manager.
 * @author DAM
 */
public class LootsManager
{
  private static final Logger LOGGER=Logger.getLogger(LootsManager.class);

  private static LootsManager _instance;

  private Registry<LootTable> _tables;
  private Registry<RelicsList> _relicsLists;
  private Registry<RelicsTreasureGroup> _relicsTreasureGroups;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static LootsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=loadLootManager();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public LootsManager()
  {
    _tables=new Registry<LootTable>();
    _relicsLists=new Registry<RelicsList>();
    _relicsTreasureGroups=new Registry<RelicsTreasureGroup>();
  }

  private static LootsManager loadLootManager()
  {
    long now=System.currentTimeMillis();
    File lootsFile=LotroCoreConfig.getInstance().getFile(DataFiles.LOOTS);
    TreasureXMLParser parser=new TreasureXMLParser();
    LootsManager lootsManager=parser.parseXML(lootsFile);
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded loot tables in "+duration+"ms.");
    return lootsManager;
  }

  /**
   * Get the registry for loot tables.
   * @return the registry for loot tables.
   */
  public Registry<LootTable> getTables()
  {
    return _tables;
  }

  /**
   * Get the registry for relics lists.
   * @return the registry for relics lists.
   */
  public Registry<RelicsList> getRelicsLists()
  {
    return _relicsLists;
  }

  /**
   * Get the registry for relics treasure groups.
   * @return the registry for relics treasure groups.
   */
  public Registry<RelicsTreasureGroup> getRelicsTreasureGroups()
  {
    return _relicsTreasureGroups;
  }

  /**
   * Dump some statistics about loots.
   * @param out Output stream.
   */
  public void dump(PrintStream out)
  {
    out.println("Loots manager has:");
    out.println("\t"+_tables.size()+" loot tables");
    out.println("\t"+_relicsLists.size()+" relics lists");
    out.println("\t"+_relicsTreasureGroups.size()+" relics treasure groups");
  }
}
