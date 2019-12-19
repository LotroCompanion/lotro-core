package delta.games.lotro.common.treasure;

/**
 * Entry in a 'treasure list'.
 * @author DAM
 */
public class TreasureListEntry
{
  private int _weight;
  private TrophyList _trophyList;

  /**
   * Constructor.
   * @param weight Weight.
   * @param trophyList Trophy list.
   */
  public TreasureListEntry(int weight, TrophyList trophyList)
  {
    _weight=weight;
    _trophyList=trophyList;
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
   * Get the trophy list.
   * @return the trophy list.
   */
  public TrophyList getTrophyList()
  {
    return _trophyList;
  }
}
