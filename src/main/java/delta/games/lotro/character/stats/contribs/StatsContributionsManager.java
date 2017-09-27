package delta.games.lotro.character.stats.contribs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;

/**
 * Manager for a collection of stats contributions.
 * @author DAM
 */
public class StatsContributionsManager
{
  private HashMap<String,StatsContribution> _contribs;

  /**
   * Constructor.
   */
  public StatsContributionsManager()
  {
    _contribs=new HashMap<String,StatsContribution>();
  }

  /**
   * Add a contribution from a source.
   * @param contrib Contribution to add.
   */
  public void addContrib(StatsContribution contrib)
  {
    _contribs.put(contrib.getSource(),contrib);
  }

  /**
   * Sort contributions by stat.
   * @return A map from stats to contributions.
   */
  public Map<STAT,List<SourceContribution>> sortByStat()
  {
    Map<STAT,List<SourceContribution>> ret=new HashMap<STAT,List<SourceContribution>>();
    for(StatsContribution contrib :_contribs.values())
    {
      String source=contrib.getSource();
      BasicStatsSet stats=contrib.getStats();
      for(STAT stat : stats.getStats())
      {
        List<SourceContribution> contribs=ret.get(stat);
        if (contribs==null)
        {
          contribs=new ArrayList<SourceContribution>();
          ret.put(stat,contribs);
        }
        SourceContribution sourceContrib=new SourceContribution(source,stats.getStat(stat));
        contribs.add(sourceContrib);
      }
    }
    return ret;
  }

  @Override
  public String toString()
  {
    return sortByStat().toString();
  }
}
