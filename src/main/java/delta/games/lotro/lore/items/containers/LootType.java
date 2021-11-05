package delta.games.lotro.lore.items.containers;

/**
 * Loot table meaning.
 * @author DAM
 */
public enum LootType
{
  /**
   * Filter trophy table.
   */
  FILTERED_TROPHY_TABLE("filteredTrophyTableId"),
  /**
   * Filtered trophy table (2).
   */
  FILTERED_TROPHY_TABLE2("filteredTrophyTableId2"),
  /**
   * Filtered trophy table (3).
   */
  FILTERED_TROPHY_TABLE3("filteredTrophyTableId3"),
  /**
   * Weighted treasure table.
   */
  WEIGHTED_TREASURE_TABLE("weightedTreasureTableId"),
  /**
   * Trophy list.
   */
  TROPHY_LIST("trophyListId"),
  /**
   * Barter trophy list.
   */
  BARTER_TROPHY_LIST("barterTrophyListId"),
  /**
   * Treasure list.
   */
  TREASURE_LIST("treasureListId");

  private String _tag;

  private LootType(String tag)
  {
    _tag=tag;
  }

  /**
   * Get the asociated tag.
   * @return A tag.
   */
  public String getTag()
  {
    return _tag;
  }
}
