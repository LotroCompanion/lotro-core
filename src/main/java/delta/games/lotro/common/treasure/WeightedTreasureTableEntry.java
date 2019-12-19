package delta.games.lotro.common.treasure;

/**
 * Entry in a 'weighted treasure table':
 * <ul>
 * <li>weight,
 * <li>treasure group.
 * </ul>
 * @author DAM
 */
public class WeightedTreasureTableEntry
{
  private int _weight;
  private TreasureGroupProfile _treasureGroup;

  /**
   * Constructor.
   * @param weight Weight.
   * @param treasureGroup Treasure group.
   */
  public WeightedTreasureTableEntry(int weight, TreasureGroupProfile treasureGroup)
  {
    _weight=weight;
    _treasureGroup=treasureGroup;
  }

  /**
   * Get the weight.
   * @return the weight.
   */
  public int getWeight()
  {
    return _weight;
  }

  /**
   * Get the treasure group.
   * @return the treasure group.
   */
  public TreasureGroupProfile getTreasureGroup()
  {
    return _treasureGroup;
  }
}
