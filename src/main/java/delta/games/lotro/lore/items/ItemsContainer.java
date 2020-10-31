package delta.games.lotro.lore.items;

import java.util.HashSet;
import java.util.Set;

import delta.games.lotro.common.treasure.FilteredTrophyTable;
import delta.games.lotro.common.treasure.TreasureList;
import delta.games.lotro.common.treasure.TrophyList;
import delta.games.lotro.common.treasure.WeightedTreasureTable;

/**
 * Container-specific data (items).
 * @author DAM
 */
public class ItemsContainer extends Container
{
  private FilteredTrophyTable _filteredTable;
  private WeightedTreasureTable _weightedTable;
  private TrophyList _trophyList;
  private TreasureList _treasureList;

  /**
   * Constructor.
   * @param identifier Item identifier.
   */
  public ItemsContainer(int identifier)
  {
    super(identifier);
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
   * Get the treasure list for this container.
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

  /**
   * Indicates if this container may contain the given item.
   * @param itemId Identifier of the item to search.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean contains(int itemId)
  {
    if ((_filteredTable!=null) && (_filteredTable.contains(itemId)))
    {
      return true;
    }
    if ((_weightedTable!=null) && (_weightedTable.contains(itemId)))
    {
      return true;
    }
    if ((_trophyList!=null) && (_trophyList.contains(itemId)))
    {
      return true;
    }
    if ((_treasureList!=null) && (_treasureList.contains(itemId)))
    {
      return true;
    }
    return false;
  }

  /**
   * Get the identifiers of the reachable items.
   * @return A set of item identifiers.
   */
  public Set<Integer> getItemIds()
  {
    Set<Integer> ret=new HashSet<Integer>();
    if (_filteredTable!=null)
    {
      ret.addAll(_filteredTable.getItemIds());
    }
    if (_weightedTable!=null)
    {
      ret.addAll(_weightedTable.getItemIds());
    }
    if (_trophyList!=null)
    {
      ret.addAll(_trophyList.getItemIds());
    }
    if (_treasureList!=null)
    {
      ret.addAll(_trophyList.getItemIds());
    }
    return ret;
  }
}
