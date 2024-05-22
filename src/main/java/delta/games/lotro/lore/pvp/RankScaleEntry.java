package delta.games.lotro.lore.pvp;

/**
 * Entry in a rank scale.
 * @author DAM
 */
public class RankScaleEntry
{
  private int _value;
  private Rank _rank;

  /**
   * Constructor.
   * @param value Minimum value for this rank.
   * @param rank Rank.
   */
  public RankScaleEntry(int value, Rank rank)
  {
    _value=value;
    _rank=rank;
  }

  /**
   * Get the minimum value for this rank.
   * @return A value.
   */
  public int getValue()
  {
    return _value;
  }

  /**
   * Get the rank.
   * @return the rank.
   */
  public Rank getRank()
  {
    return _rank;
  }

  @Override
  public String toString()
  {
    return _value+" => "+_rank;
  }
}
