package delta.games.lotro.character.stats.virtues;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.CharacterStat.STAT;
import delta.games.lotro.character.stats.BasicStatsSet;
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
  }

  /**
   * Initialize contributions.
   * @param stats Contributed stats.
   * @param values Contributions values.
   */
  public void setContribs(STAT[] stats, int[][] values)
  {
    _contribs.clear();
    // Rank 0
    _contribs.add(new BasicStatsSet());
    int ranks=values.length;
    for(int i=0;i<ranks;i++)
    {
      if (values[i].length==stats.length+1)
      {
        if (values[i][0]==i+1)
        {
          BasicStatsSet contrib=new BasicStatsSet();
          int j=1;
          for(STAT stat : stats)
          {
            contrib.addStat(stat, new FixedDecimalsInteger(values[i][j]));
            j++;
          }
          _contribs.add(contrib);
        }
      }
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
