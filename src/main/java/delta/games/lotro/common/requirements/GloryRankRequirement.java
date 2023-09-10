package delta.games.lotro.common.requirements;

/**
 * Glory rank requirement.
 * @author DAM
 */
public class GloryRankRequirement
{
  private int _rank;

  /**
   * Constructor.
   */
  public GloryRankRequirement()
  {
    _rank=1;
  }

  /**
   * Get the required rank.
   * @return the required rank.
   */
  public int getRank()
  {
    return _rank;
  }

  /**
   * Set the required rank.
   * @param rank Rank to set.
   */
  public void setRank(int rank)
  {
    _rank=rank;
  }
}
