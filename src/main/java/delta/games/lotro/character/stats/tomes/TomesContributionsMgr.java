package delta.games.lotro.character.stats.tomes;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Manager for contributions of stat tomes to player stats.
 * @author DAM
 */
public class TomesContributionsMgr
{
  private static final int[] CUMULATED_STAT_CONTRIBS_BY_RANK= {
    0,15,30,45,60,75,90,120,160,200,240,
    280,320,360,400,440,480,520,560,600,640
  };

  /**
   * Get the contribution for a set of tomes.
   * @param tomes Tomes to use.
   * @return A stats set.
   */
  public BasicStatsSet getContribution(TomesSet tomes)
  {
    BasicStatsSet stats=new BasicStatsSet();
    for(STAT stat : TomesSet.AVAILABLE_TOMES)
    {
      int rank=tomes.getTomeRank(stat);
      if (rank>0)
      {
        stats.addStat(stat, new FixedDecimalsInteger(CUMULATED_STAT_CONTRIBS_BY_RANK[rank]));
      }
    }
    return stats;
  }
}
