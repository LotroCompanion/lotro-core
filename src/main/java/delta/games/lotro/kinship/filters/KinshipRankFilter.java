package delta.games.lotro.kinship.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.kinship.KinshipMember;

/**
 * Filter for members of a given rank.
 * @author DAM
 */
public class KinshipRankFilter implements Filter<KinshipMember>
{
  private Integer _rankID;

  /**
   * Constructor.
   * @param rankID Rank to select (may be <code>null</code>).
   */
  public KinshipRankFilter(Integer rankID)
  {
    _rankID=rankID;
  }

  /**
   * Get the rank ID to use.
   * @return A rank ID or <code>null</code>.
   */
  public Integer getRankID()
  {
    return _rankID;
  }

  /**
   * Set the rank ID to select.
   * @param rankID Rank ID to use, may be <code>null</code>.
   */
  public void setRankID(Integer rankID)
  {
    _rankID=rankID;
  }

  @Override
  public boolean accept(KinshipMember rank)
  {
    if (_rankID==null)
    {
      return true;
    }
    return rank.getRank().getCode()==_rankID.intValue();
  }
}
