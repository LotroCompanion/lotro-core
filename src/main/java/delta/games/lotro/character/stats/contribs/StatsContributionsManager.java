package delta.games.lotro.character.stats.contribs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.character.stats.base.DerivedStatsContributionsMgr;
import delta.games.lotro.character.stats.base.io.DerivedStatContributionsIO;
import delta.games.lotro.common.CharacterClass;

/**
 * Manager for a collection of stats contributions.
 * @author DAM
 */
public class StatsContributionsManager
{
  private List<StatsContribution> _contribs;
  private Map<STAT,ContribsByStat> _sortedContribs;
  private boolean _resolveIndirectContributions;
  private CharacterClass _characterClass;

  /**
   * Constructor.
   * @param characterClass Character class to use.
   */
  public StatsContributionsManager(CharacterClass characterClass)
  {
    _contribs=new ArrayList<StatsContribution>();
    _resolveIndirectContributions=false;
    _characterClass=characterClass;
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
   * Indicates if indirect contributions resolution is enabled or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isResolveIndirectContributions()
  {
    return _resolveIndirectContributions;
  }

  /**
   * Set the valie of the 'resolve indirect contributions' flag.
   * @param enabled Value to set.
   */
  public void setResolveIndirectContributions(boolean enabled)
  {
    _resolveIndirectContributions=enabled;
  }

  /**
   * Compute contributions.
   */
  public void compute()
  {
    DerivedStatsContributionsMgr derivatedMgr=DerivedStatContributionsIO.load();
    if (_resolveIndirectContributions)
    {
      for(StatsContribution contrib : _contribs)
      {
        BasicStatsSet stats=contrib.getStats();
        BasicStatsSet derivedStats=derivatedMgr.getContribution(_characterClass,stats);
        stats.addStats(derivedStats);
      }
    }
    _sortedContribs=sortByStat();
  }

  /**
   * Get the contributions for a given stat.
   * @param stat Targeted stat.
   * @return A contribs storage or <code>null</code> if no contribs for this stat.
   */
  public ContribsByStat getContribs(STAT stat)
  {
    return _sortedContribs.get(stat);
  }

  /**
   * Sort contributions by stat.
   * @return A map from stats to contributions.
   */
  private Map<STAT,ContribsByStat> sortByStat()
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
   * Remove all contributions.
   */
  public void clear()
  {
    _contribs.clear();
  }

  @Override
  public String toString()
  {
    return sortByStat().toString();
  }
}
