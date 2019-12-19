package delta.games.lotro.common.treasure;

import delta.games.lotro.common.requirements.UsageRequirement;

/**
 * Entry in a 'filtered trophy table':
 * <ul>
 * <li>requirements,
 * <li>trophy list or weighted treasure table.
 * </ul>
 * @author DAM
 */
public class FilteredTrophyTableEntry
{
  private UsageRequirement _requirements;
  private TrophyList _trophyList;
  private WeightedTreasureTable _treasureTable;

  /**
   * Constructor.
   * @param trophyList Trophy list.
   * @param treasureTable Treasure table.
   */
  public FilteredTrophyTableEntry(TrophyList trophyList, WeightedTreasureTable treasureTable)
  {
    _requirements=new UsageRequirement();
    _trophyList=trophyList;
    _treasureTable=treasureTable;
  }

  /**
   * Get the usage requirement.
   * @return the usage requirement.
   */
  public UsageRequirement getUsageRequirement()
  {
    return _requirements;
  }

  /**
   * Get the trophy list.
   * @return the trophy list.
   */
  public TrophyList getTrophyList()
  {
    return _trophyList;
  }

  /**
   * Get the treasure table.
   * @return the treasure table.
   */
  public WeightedTreasureTable getTreasureTable()
  {
    return _treasureTable;
  }
}
