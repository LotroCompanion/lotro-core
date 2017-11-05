package delta.games.lotro.character.stats.contribs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Manager for a collection of stats contributions.
 * @author DAM
 */
public class StatsContributionsManager
{
  private List<StatsContribution> _contribs;

  /**
   * Constructor.
   */
  public StatsContributionsManager()
  {
    _contribs=new ArrayList<StatsContribution>();
  }

  /**
   * Add a contribution from a source.
   * @param contrib Contribution to add.
   */
  public void addContrib(StatsContribution contrib)
  {
    _contribs.add(contrib);
  }

  /**
   * Sort contributions by stat.
   * @return A map from stats to contributions.
   */
  public Map<STAT,ContribsByStat> sortByStat()
  {
    Map<STAT,ContribsByStat> ret=new HashMap<STAT,ContribsByStat>();
    for(StatsContribution contrib : _contribs)
    {
      StatContributionSource sourceId=contrib.getSource();
      BasicStatsSet stats=contrib.getStats();
      for(STAT stat : stats.getStats())
      {
        ContribsByStat contribs=ret.get(stat);
        if (contribs==null)
        {
          contribs=new ContribsByStat(stat);
          ret.put(stat,contribs);
        }
        StatContribution sourceContrib=new StatContribution(sourceId,stats.getStat(stat));
        contribs.addContrib(sourceContrib);
      }
    }
    return ret;
  }

  /**
   * Resolve derivated contributions.
   * @param contribs Contributions to resolve.
   */
  public void resolveDerivatedContributions(Map<STAT,ContribsByStat> contribs)
  {
    //System.out.println("Resolving derivated contributions");
    // Iterating in the STAT enum order implies that 'primary' stats are resolved
    // before the ones that may reference them...
    for(STAT stat : STAT.values())
    {
      resolveSingleStat(contribs,stat);
    }
  }

  private void resolveSingleStat(Map<STAT,ContribsByStat> contribs, STAT stat)
  {
    ContribsByStat contribsForStat=contribs.get(stat);
    if (contribsForStat==null)
    {
      return;
    }
    //System.out.println("stat="+stat);
    List<StatContribution> statContribs=contribsForStat.getContribs();
    for(StatContribution statContrib : statContribs)
    {
      String id=statContrib.getSource().getId();
      if (id.startsWith(StatsContribution.STAT_SEED))
      {
        String[] idParts=id.split(":");
        STAT sourceStat=STAT.valueOf(idParts[1]);
        FixedDecimalsInteger factor=FixedDecimalsInteger.fromString(idParts[2]);
        //System.out.println("\tFound source stat:"+sourceStat);
        //System.out.println("\t\tFactor: "+factor);
        ContribsByStat contribsForSourceStat=contribs.get(sourceStat);
        if (contribsForSourceStat!=null)
        {
          for(StatContribution sourceStatContrib : contribsForSourceStat.getContribs())
          {
            FixedDecimalsInteger value=new FixedDecimalsInteger(sourceStatContrib.getValue());
            value.multiply(factor);
            StatContribution toAdd=new StatContribution(sourceStatContrib.getSource(),value);
            contribsForStat.addContrib(toAdd);
            //System.out.println("\t\tAdd contrib: "+sourceStatContrib+" -> "+value);
          }
        }
        contribsForStat.remove(id);
      }
      else
      {
        //System.out.println("\tFound source:"+statContrib);
      }
    }
  }

  @Override
  public String toString()
  {
    return sortByStat().toString();
  }
}
