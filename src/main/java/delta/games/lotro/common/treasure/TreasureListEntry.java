package delta.games.lotro.common.treasure;

/**
 * Entry in a 'treasure list'.
 * @author DAM
 */
public class TreasureListEntry
{
  private int _weight;
  private TreasureGroupProfile _treasureGroup;

  /**
   * Constructor.
   * @param weight Weight.
   * @param treasureGroup Trophy list.
   */
  public TreasureListEntry(int weight, TreasureGroupProfile treasureGroup)
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
