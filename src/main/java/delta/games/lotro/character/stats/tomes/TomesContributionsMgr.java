package delta.games.lotro.character.stats.tomes;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.contribs.StatsContribution;
import delta.games.lotro.common.stats.StatDescription;

/**
 * Manager for contributions of stat tomes to player stats.
 * @author DAM
 */
public class TomesContributionsMgr
{
  /**
   * Get the contribution for a set of tomes.
   * @param tomes Tomes to use.
   * @return A stats set.
   */
  public List<StatsContribution> getContribution(TomesSet tomes)
  {
    List<StatsContribution> ret=new ArrayList<StatsContribution>();
    StatTomesManager tomesManager=StatTomesManager.getInstance();
    for(StatDescription stat : tomesManager.getStats())
    {
      int rank=tomes.getTomeRank(stat);
      if (rank>0)
      {
        BasicStatsSet tomeContrib=getContribution(stat,rank);
        StatsContribution contrib=StatsContribution.getTomeContrib(stat,rank,tomeContrib);
        ret.add(contrib);
      }
    }
    return ret;
  }

  /**
   * Get the stats contribution for a tome.
   * @param stat Stat of the targeted tome.
   * @param rank Rank of the targeted tome.
   * @return some contributed stats.
   */
  private BasicStatsSet getContribution(StatDescription stat, int rank)
  {
    BasicStatsSet ret=null;
    if (rank>0)
    {
      StatTomesManager tomesManager=StatTomesManager.getInstance();
      StatTome tome=tomesManager.getStatTome(stat,rank);
      if (tome!=null)
      {
        ret=new BasicStatsSet(tome.getStats());
      }
    }
    else
    {
      ret=new BasicStatsSet();
    }
    return ret;
  }
}
