package delta.games.lotro.common.treasure;

import delta.games.lotro.utils.Registry;

/**
 * Loots manager.
 * @author DAM
 */
public class LootsManager
{
  private Registry<FilteredTrophyTable> _filteredTrophyTables;
  private Registry<ItemsTable> _itemTables;
  private Registry<TreasureList> _treasureLists;
  private Registry<TrophyList> _trophyLists;
  private Registry<WeightedTreasureTable> _weightedTreasureTables;

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
  }
}
