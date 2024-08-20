package delta.games.lotro.common.stats;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.StatsSetElement;

/**
 * Stats provider.
 * @author DAM
 */
public class StatsProvider
{
  private List<StatsProviderEntry> _entries;

  /**
   * Constructor.
   */
  public StatsProvider()
  {
    _entries=new ArrayList<StatsProviderEntry>();
  }

  /**
   * Add a stat provider.
   * @param statProvider Stat provider to add.
   */
  public void addStatProvider(StatProvider statProvider)
  {
    if (statProvider!=null)
    {
      _entries.add(statProvider);
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
      _entries.add(effect);
    }
  }

  /**
   * Get the number of entries.
   * @return a count.
   */
  public int getEntriesCount()
  {
    return _entries.size();
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
   * Get the first stat provider.
   * @return A stat provider or <code>null</code>.
   */
  public StatProvider getFirstStatProvider()
  {
    if (!_entries.isEmpty())
    {
      for(StatsProviderEntry entry : _entries)
      {
        if (entry instanceof StatProvider)
        {
          return (StatProvider)entry;
        }
      }
    }
    return null;
  }

  /**
   * Get the first stat in this provider.
   * @return A stat or <code>null</code> if no stat.
   */
  public StatDescription getFirstStat()
  {
    StatProvider provider=getFirstStatProvider();
    if (provider!=null)
    {
      return provider.getStat();
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
    for(StatsProviderEntry entry : _entries)
    {
      if (entry instanceof StatProvider)
      {
        StatProvider provider=(StatProvider)entry;
        if (stat==provider.getStat())
        {
          ret=provider;
          break;
        }
      }
    }
    return ret;
  }

  /**
   * Add an entry.
   * @param entry Entry to add.
   */
  public void addEntry(StatsProviderEntry entry)
  {
    _entries.add(entry);
  }

  /**
   * Get the stats provider enty at the given index.
   * @param index Index of entry, starting at 0.
   * @return A stats provider entry.
   */
  public StatsProviderEntry getEntry(int index)
  {
    return _entries.get(index);
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
      _entries.remove(provider);
    }
  }

  /**
   * Get the managed stat providers.
   * @return a possibly empty but never <code>null</code> list of stat providers.
   */
  public List<StatProvider> getStatProviders()
  {
    List<StatProvider> ret=new ArrayList<StatProvider>();
    for(StatsProviderEntry entry : _entries)
    {
      if (entry instanceof StatProvider)
      {
        ret.add((StatProvider)entry);
      }
    }
    return ret;
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
    for(StatProvider provider : getStatProviders())
    {
      StatsSetElement element=getStat(provider,tier,level);
      if (element!=null)
      {
        stats.addStat(element);
      }
    }
    return stats;
  }

  /**
   * Compute a single stat.
   * @param provider Stat provider.
   * @param tier Tier to use.
   * @param level Level to use.
   * @return A stats set element.
   */
  public StatsSetElement getStat(StatProvider provider, int tier, int level)
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
        int intValue=(int)(floatValue);
        statValue=Integer.valueOf(intValue);
      }
      else
      {
        statValue=Float.valueOf(floatValue);
      }
      StatsSetElement ret=new StatsSetElement(stat,operator);
      ret.setValue(statValue);
      ret.setDescriptionOverride(provider.getDescriptionOverride());
      return ret;
    }
    return null;
  }

  /**
   * Get the special effects.
   * @return a possibly empty but never <code>null</code> list of special effects.
   */
  public List<SpecialEffect> getSpecialEffects()
  {
    List<SpecialEffect> ret=new ArrayList<SpecialEffect>();
    for(StatsProviderEntry entry : _entries)
    {
      if (entry instanceof SpecialEffect)
      {
        ret.add((SpecialEffect)entry);
      }
    }
    return ret;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    int nbEntries=getEntriesCount();
    for(int i=0;i<nbEntries;i++)
    {
      if (i>0)
      {
        sb.append(" / ");
      }
      StatsProviderEntry entry=getEntry(i);
      if (entry instanceof StatProvider)
      {
        StatProvider statProvider=(StatProvider)entry;
        StatDescription stat=statProvider.getStat();
        sb.append(stat.getName());
      }
      else if (entry instanceof SpecialEffect)
      {
        SpecialEffect specialEffect=(SpecialEffect)entry;
        String line=specialEffect.getLabel();
        if ((line!=null) && (!line.isEmpty()))
        {
          sb.append(line);
        }
      }
    }
    return sb.toString();
  }
}
