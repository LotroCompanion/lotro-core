package delta.games.lotro.common.stats;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Stats provider.
 * @author DAM
 */
public class StatsProvider
{
  private List<StatProvider> _stats;

  /**
   * Constructor.
   */
  public StatsProvider()
  {
    _stats=new ArrayList<StatProvider>();
  }

  /**
   * Add a stat provider.
   * @param statProvider Stat provider to add.
   */
  public void addStatProvider(StatProvider statProvider)
  {
    if (statProvider!=null)
    {
      _stats.add(statProvider);
    }
  }

  /**
   * Get the number of stats providers.
   * @return a count.
   */
  public int getNumberOfStatProviders()
  {
    return _stats.size();
  }

  /**
   * Get a displayable label for this stats provider.
   * @return a displayable label.
   */
  public String getLabel()
  {
    StatDescription stat=getFirstStat();
    return (stat!=null)?stat.getName():"?";
  }

  /**
   * Get the first stat in this provider.
   * @return A stat or <code>null</code> if no stat.
   */
  public StatDescription getFirstStat()
  {
    int nbStats=getNumberOfStatProviders();
    if (nbStats>0)
    {
      StatProvider statProvider=getStatProvider(0);
      StatDescription stat=statProvider.getStat();
      return stat;
    }
    return null;
  }

  /**
   * Get the stat provider for a given stat.
   * @param stat Stat to use.
   * @return A stat provider or <code>null</code> if not found.
   */
  public StatProvider getStat(StatDescription stat)
  {
    StatProvider ret=null;
    for(StatProvider provider : _stats)
    {
      if (stat==provider.getStat())
      {
        ret=provider;
        break;
      }
    }
    return ret;
  }

  /**
   * Get the stat provider at the given index.
   * @param index Index of provider, starting at 0.
   * @return A stat provider.
   */
  public StatProvider getStatProvider(int index)
  {
    return _stats.get(index);
  }

  /**
   * Remove a stat provider.
   * @param stat Stat to use.
   */
  public void removeStat(StatDescription stat)
  {
    StatProvider provider=getStat(stat);
    if (provider!=null)
    {
      _stats.remove(provider);
    }
  }

  /**
   * Compute stats for a given tier and level.
   * @param tier Tier to use, starting at 1.
   * @param level Level to use, starting at 1.
   * @return A set of stats.
   */
  public BasicStatsSet getStats(int tier, int level)
  {
    return getStats(tier,level,false);
  }

  /**
   * Compute stats for a given tier and level.
   * @param tier Tier to use, starting at 1.
   * @param level Level to use, starting at 1.
   * @param round Perform rounding to integer values or not.
   * @return A set of stats.
   */
  public BasicStatsSet getStats(int tier, int level, boolean round)
  {
    BasicStatsSet stats=new BasicStatsSet();
    for(StatProvider provider : _stats)
    {
      StatOperator operator=provider.getOperator();
      // Ignore multiplicative stats
      if (operator==StatOperator.MULTIPLY)
      {
        continue;
      }
      Float value=provider.getStatValue(tier,level);
      if (value!=null)
      {
        FixedDecimalsInteger statValue=null;
        StatDescription stat=provider.getStat();
        float floatValue=value.floatValue();
        if (round)
        {
          if (!stat.isPercentage())
          {
            int intValue;
            if (shallRound(stat))
            {
              intValue=Math.round(floatValue);
            }
            else
            {
              intValue=(int)(floatValue);
            }
            if (operator==StatOperator.SUBSTRACT)
            {
              intValue=-intValue;
            }
            statValue=new FixedDecimalsInteger(intValue);
          }
        }
        if (statValue==null)
        {
          if (operator==StatOperator.SUBSTRACT)
          {
            floatValue=-floatValue;
          }
          statValue=new FixedDecimalsInteger(floatValue);
        }
        stats.setStat(stat,statValue);
      }
    }
    return stats;
  }

  private boolean shallRound(StatDescription stat)
  {
    if (stat==WellKnownStat.ARMOUR) return false;
    if (stat==WellKnownStat.MIGHT) return false;
    if (stat==WellKnownStat.AGILITY) return false;
    if (stat==WellKnownStat.VITALITY) return false;
    if (stat==WellKnownStat.WILL) return false;
    if (stat==WellKnownStat.FATE) return false;
    return true;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    int nbStats=getNumberOfStatProviders();
    for(int i=0;i<nbStats;i++)
    {
      if (i>0)
      {
        sb.append(" / ");
      }
      StatProvider statProvider=getStatProvider(i);
      StatDescription stat=statProvider.getStat();
      sb.append(stat.getName());
    }
    return sb.toString();
  }
}
