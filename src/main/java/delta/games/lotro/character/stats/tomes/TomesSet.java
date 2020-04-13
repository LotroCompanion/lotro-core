package delta.games.lotro.character.stats.tomes;

import java.util.HashMap;
import java.util.Map;

import delta.games.lotro.common.stats.StatDescription;

/**
 * Storage for the stat tomes of a single character.
 * @author DAM
 */
public class TomesSet
{
  private Map<StatDescription,Integer> _ranks;

  /**
   * Constructor.
   */
  public TomesSet()
  {
    _ranks=new HashMap<StatDescription,Integer>();
  }

  /**
   * Set the contents of this object from a given source.
   * @param source Source to copy.
   */
  public void copyFrom(TomesSet source)
  {
    _ranks.clear();
    _ranks.putAll(source._ranks);
  }

  /**
   * Clear data.
   */
  public void clear()
  {
    _ranks.clear();
  }

  /**
   * Set the rank for a tome kind.
   * @param stat Targeted stat.
   * @param rank Rank to set.
   */
  public void setTomeRank(StatDescription stat, int rank)
  {
    _ranks.put(stat,Integer.valueOf(rank));
  }

  /**
   * Get rank of a given tome.
   * @param stat Targeted stat.
   * @return A rank value (starting at 0).
   */
  public int getTomeRank(StatDescription stat)
  {
    Integer rank=_ranks.get(stat);
    return rank!=null?rank.intValue():0;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    int index=0;
    for(StatDescription stat : StatTomesManager.getInstance().getStats())
    {
      int rank=getTomeRank(stat);
      if (rank>0)
      {
        if (index>0)
        {
          sb.append(',');
        }
        sb.append(stat.getName());
        sb.append(' ');
        sb.append(rank);
        index++;
      }
    }
    return sb.toString();
  }
}
