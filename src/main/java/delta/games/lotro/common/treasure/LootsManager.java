package delta.games.lotro.common.treasure;

import java.io.File;

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

  private Registry<FilteredTrophyTable> _filteredTrophyTables;
  private Registry<ItemsTable> _itemTables;
  private Registry<TreasureList> _treasureLists;
  private Registry<TrophyList> _trophyLists;
  private Registry<WeightedTreasureTable> _weightedTreasureTables;
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
    _filteredTrophyTables=new Registry<FilteredTrophyTable>();
    _itemTables=new Registry<ItemsTable>();
    _treasureLists=new Registry<TreasureList>();
    _trophyLists=new Registry<TrophyList>();
    _weightedTreasureTables=new Registry<WeightedTreasureTable>();
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
   * Get the registry for filtered trophy tables.
   * @return the registry for filtered trophy tables.
   */
  public Registry<FilteredTrophyTable> getFilteredTrophyTables()
  {
    return _filteredTrophyTables;
  }

  /**
   * Get the registry for items tables.
   * @return the registry for items tables.
   */
  public Registry<ItemsTable> getItemsTables()
  {
    return _itemTables;
  }

  /**
   * Get the registry for treasure lists.
   * @return the registry for treasure lists.
   */
  public Registry<TreasureList> getTreasureLists()
  {
    return _treasureLists;
  }

  /**
   * Get the registry for trophy lists.
   * @return the registry for trophy lists.
   */
  public Registry<TrophyList> getTrophyLists()
  {
    return _trophyLists;
  }

  /**
   * Get the registry for weighted treasure tables.
   * @return the registry for weighted treasure tables.
   */
  public Registry<WeightedTreasureTable> getWeightedTreasureTables()
  {
    return _weightedTreasureTables;
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
   */
  public void dump()
  {
    System.out.println("Loots manager has:");
    System.out.println("\t"+_filteredTrophyTables.size()+" filtered trophy tables");
    System.out.println("\t"+_itemTables.size()+" items tables");
    System.out.println("\t"+_treasureLists.size()+" treasure lists");
    System.out.println("\t"+_trophyLists.size()+" trophy lists");
    System.out.println("\t"+_weightedTreasureTables.size()+" weighted treasure tables");
    System.out.println("\t"+_relicsLists.size()+" relics lists");
    System.out.println("\t"+_relicsTreasureGroups.size()+" relics treasure groups");
  }
}
