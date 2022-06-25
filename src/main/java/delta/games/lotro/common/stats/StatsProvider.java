package delta.games.lotro.common.stats;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.stats.BasicStatsSet;

/**
 * Stats provider.
 * @author DAM
 */
public class StatsProvider
{
  private List<StatProvider> _stats;
  private List<SpecialEffect> _effects;

  /**
   * Constructor.
   */
  public StatsProvider()
  {
    _stats=new ArrayList<StatProvider>();
    _effects=new ArrayList<SpecialEffect>();
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
   * Add a special effect.
   * @param effect Special effect to add.
   */
  public void addSpecialEffect(SpecialEffect effect)
  {
    if (effect!=null)
    {
      _effects.add(effect);
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
    BasicStatsSet stats=new BasicStatsSet();
    for(StatProvider provider : _stats)
    {
      StatOperator operator=provider.getOperator();
      Float value=provider.getStatValue(tier,level);
      if (value!=null)
      {
        Number statValue=null;
        StatDescription stat=provider.getStat();
        float floatValue=value.floatValue();
        StatType type=stat.getType();
        if (type==StatType.INTEGER)
        {
          //int intValue=Math.round(floatValue);
          int intValue=(int)(floatValue);
          statValue=Integer.valueOf(intValue);
        }
        else
        {
          statValue=Float.valueOf(floatValue);
        }
        stats.setStat(stat,operator,statValue,provider.getDescriptionOverride());
      }
    }
    return stats;
  }

  /**
   * Get the special effects.
   * @return a possibly empty but never <code>null</code> list of special effects.
   */
  public List<SpecialEffect> getSpecialEffects()
  {
    return _effects;
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
    if (_effects.size()>0)
    {
      sb.append(", effects=").append(_effects);
    }
    return sb.toString();
  }
}
