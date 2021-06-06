package delta.games.lotro.kinship.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.kinship.KinshipMember;
import delta.games.lotro.kinship.KinshipRank;

/**
 * Filter for members of a given rank.
 * @author DAM
 */
public class KinshipRankFilter implements Filter<KinshipMember>
{
  private KinshipRank _rank;

  /**
   * Constructor.
   * @param rank Rank to select (may be <code>null</code>).
   */
  public KinshipRankFilter(KinshipRank rank)
  {
    _rank=rank;
  }

  /**
   * Get the rank to use.
   * @return A rank or <code>null</code>.
   */
  public KinshipRank getRank()
  {
    return _rank;
  }

  /**
   * Set the rank to select.
   * @param rank Rank to use, may be <code>null</code>.
   */
  public void setRank(KinshipRank rank)
  {
    _rank=rank;
  }

  @Override
  public boolean accept(KinshipMember rank)
  {
    if (_rank==null)
    {
      return true;
    }
    return rank.getRank()==_rank;
  }
}
