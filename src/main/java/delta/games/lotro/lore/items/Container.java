package delta.games.lotro.lore.items;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.treasure.FilteredTrophyTable;
import delta.games.lotro.common.treasure.TreasureList;
import delta.games.lotro.common.treasure.TrophyList;
import delta.games.lotro.common.treasure.WeightedTreasureTable;

/**
 * Container-specific data.
 * @author DAM
 */
public class Container implements Identifiable
{
  private int _identifier;
  private FilteredTrophyTable _filteredTable;
  private WeightedTreasureTable _weightedTable;
  private TrophyList _trophyList;
  private TreasureList _treasureList;

  /**
   * Constructor.
   * @param identifier Item identifier.
   */
  public Container(int identifier)
  {
    _identifier=identifier;
  }

  @Override
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Get the filtered trophy table for this container, if any.
   * @return a filtered trophy table or <code>null</code>.
   */
  public FilteredTrophyTable getFilteredTable()
  {
    return _filteredTable;
  }

  /**
   * Set the filtered trophy table for this container.
   * @param filteredTable the filtered trophy table to set.
   */
  public void setFilteredTable(FilteredTrophyTable filteredTable)
  {
    _filteredTable=filteredTable;
  }

  /**
   * Get the weighted treasure table for this container.
   * @return a weighted treasure table or <code>null</code>.
   */
  public WeightedTreasureTable getWeightedTable()
  {
    return _weightedTable;
  }

  /**
   * Set the weighted treasure table for this container.
   * @param weightedTable the weighted treasure table to set.
   */
  public void setWeightedTable(WeightedTreasureTable weightedTable)
  {
    _weightedTable=weightedTable;
  }

  /**
   * Get the trophy list for this container.
   * @return a trophy list or <code>null</code>.
   */
  public TrophyList getTrophyList()
  {
    return _trophyList;
  }

  /**
   * Set the trophy list for this container.
   * @param trophyList the trophy list to set.
   */
  public void setTrophyList(TrophyList trophyList)
  {
    _trophyList=trophyList;
  }

  /**
   * get the treasure list for this container.
   * @return a treasure list or <code>null</code>.
   */
  public TreasureList getTreasureList()
  {
    return _treasureList;
  }

  /**
   * Set the treasure list for this container.
   * @param treasureList the treasure list to set.
   */
  public void setTreasureList(TreasureList treasureList)
  {
    _treasureList=treasureList;
  }
}
