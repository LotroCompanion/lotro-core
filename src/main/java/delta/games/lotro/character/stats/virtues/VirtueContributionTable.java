package delta.games.lotro.character.stats.virtues;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Contributions table for a single virtue.
 * @author DAM
 */
public class VirtueContributionTable
{
  private List<BasicStatsSet> _contribs;

  /**
   * Constructor.
   */
  public VirtueContributionTable()
  {
    _contribs=new ArrayList<BasicStatsSet>();
    // Rank 0
    _contribs.add(new BasicStatsSet());
  }

  /**
   * Add contributions.
   * @param stat Targeted stat.
   * @param values Values for ranks, starting at rank 1.
   */
  public void addContrib(STAT stat, FixedDecimalsInteger[] values)
  {
    for(int i=_contribs.size();i<=values.length;i++)
    {
      _contribs.add(new BasicStatsSet());
    }
    for(int i=0;i<values.length;i++)
    {
      BasicStatsSet contrib=getContrib(i+1);
      contrib.addStat(stat, values[i]);
    }
  }

  /**
   * Get the stats contribution for a given rank.
   * @param rank Rank to use.
   * @return A stats set or <code>null</code> if not found.
   */
  public BasicStatsSet getContrib(int rank)
  {
    int nbRanks=_contribs.size()-1;
    if ((rank>=0) && (rank<=nbRanks))
    {
      return _contribs.get(rank);
    }
    return null;
  }
}
