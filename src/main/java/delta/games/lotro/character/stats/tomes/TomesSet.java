package delta.games.lotro.character.stats.tomes;

import delta.games.lotro.character.stats.STAT;

/**
 * Storage for the stat tomes of a single character.
 * @author DAM
 */
public class TomesSet
{
  /**
   * Stats of available tomes.
   */
  public static STAT[] AVAILABLE_TOMES={
    STAT.MIGHT, STAT.AGILITY, STAT.VITALITY, STAT.WILL, STAT.FATE
  };
  /**
   * Maximum rank.
   */
  public static final int MAX_RANK=20;
  private static final String[] LATINE={
    "I","II","III","IV","V","VI","VII","VIII","IX","X",
    "XI","XII","XIII","XIV","XV","XVI","XVII","XVIII","XIX","XX"
  };

  private int[] _ranks;

  /**
   * Constructor.
   */
  public TomesSet()
  {
    _ranks=new int[AVAILABLE_TOMES.length];
  }

  /**
   * Clear data.
   */
  public void clear()
  {
    for(int i=0;i<AVAILABLE_TOMES.length;i++)
    {
      _ranks[i]=0;
    }
  }

  /**
   * Set the rank for a tome kind.
   * @param stat Targeted stat.
   * @param rank Rank to set.
   */
  public void setTomeRank(STAT stat, int rank)
  {
    Integer index=getIndexForStat(stat);
    if ((rank>=0) && (rank<=MAX_RANK))
    {
      if (index!=null)
      {
        _ranks[index.intValue()]=rank;
      }
    }
  }

  private Integer getIndexForStat(STAT stat)
  {
    Integer ret=null;
    for(int i=0;i<AVAILABLE_TOMES.length;i++)
    {
      if (stat==AVAILABLE_TOMES[i])
      {
        ret=Integer.valueOf(i);
        break;
      }
    }
    return ret;
  }

  /**
   * Get rank of a given tome.
   * @param stat Targeted stat.
   * @return A rank value (starting at 0).
   */
  public int getTomeRank(STAT stat)
  {
    Integer index=getIndexForStat(stat);
    if (index!=null)
    {
      return _ranks[index.intValue()];
    }
    return 0;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    int index=0;
    for(int i=0;i<AVAILABLE_TOMES.length;i++)
    {
      int rank=_ranks[i];
      if (rank>0)
      {
        if (index>0)
        {
          sb.append(',');
        }
        sb.append(AVAILABLE_TOMES[i].getName());
        sb.append(' ');
        sb.append(LATINE[rank-1]);
        index++;
        
      }
    }
    return sb.toString();
  }
}
