package delta.games.lotro.character.stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatDescriptionComparator;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Set of basic stats.
 * @author DAM
 */
public class BasicStatsSet
{
  private HashMap<StatDescription,FixedDecimalsInteger> _stats;

  /**
   * Constructor.
   */
  public BasicStatsSet()
  {
    _stats=new HashMap<StatDescription,FixedDecimalsInteger>();
  }

  /**
   * Copy constructor.
   * @param source Source object.
   */
  public BasicStatsSet(BasicStatsSet source)
  {
    _stats=new HashMap<StatDescription,FixedDecimalsInteger>();
    setStats(source);
  }

  /**
   * Get a stat value.
   * @param stat Stat to get.
   * @return A stat value or <code>null</code> if not found.
   */
  public FixedDecimalsInteger getStat(StatDescription stat)
  {
    FixedDecimalsInteger statValue=_stats.get(stat);
    return statValue;
  }

  /**
   * Remove all stats.
   */
  public void clear()
  {
    _stats.clear();
  }

  /**
   * Get all registered stats keys.
   * @return A set of stat keys.
   */
  public Set<StatDescription> getStats()
  {
    return new HashSet<StatDescription>(_stats.keySet());
  }

  /**
   * Get a sorted list of all used stats.
   * @return A sorted list of stats.
   */
  public List<StatDescription> getSortedStats()
  {
    List<StatDescription> stats=new ArrayList<StatDescription>(_stats.keySet());
    //Collections.sort(stats,new IdentifiableComparator<StatDescription>());
    Collections.sort(stats,new StatDescriptionComparator());
    return stats;
  }

  /**
   * Get the number of defined stats in this set.
   * @return a count.
   */
  public int getStatsCount()
  {
    return _stats.size();
  }

  /**
   * Remove a stat.
   * @param stat Targeted stat.
   */
  public void removeStat(StatDescription stat)
  {
    _stats.remove(stat);
  }

  /**
   * Set stat value.
   * @param stat Stat to set.
   * @param value Value to set.
   */
  public void setStat(StatDescription stat, FixedDecimalsInteger value)
  {
    _stats.put(stat,new FixedDecimalsInteger(value));
  }

  /**
   * Set stat value.
   * @param stat Stat to set.
   * @param value Value to set.
   */
  public void setStat(StatDescription stat, int value)
  {
    _stats.put(stat, new FixedDecimalsInteger(value));
  }

  /**
   * Copy stats from a source.
   * @param stats Source stats.
   */
  public void setStats(BasicStatsSet stats)
  {
    for(Map.Entry<StatDescription,FixedDecimalsInteger> entry : stats._stats.entrySet())
    {
      FixedDecimalsInteger value=new FixedDecimalsInteger(entry.getValue());
      setStat(entry.getKey(),value);
    }
  }

  /**
   * Set stat value.
   * @param stat Stat to set.
   * @param value Value to set.
   */
  public void setStat(StatDescription stat, float value)
  {
    _stats.put(stat, new FixedDecimalsInteger(value));
  }

  /**
   * Add stat value.
   * @param stat Stat to set.
   * @param value Value to set.
   */
  public void addStat(StatDescription stat, FixedDecimalsInteger value)
  {
    FixedDecimalsInteger currentStat=_stats.get(stat);
    FixedDecimalsInteger total;
    if (currentStat==null)
    {
      total=new FixedDecimalsInteger(value);
    }
    else
    {
      total=new FixedDecimalsInteger(value);
      total.add(currentStat);
    }
    _stats.put(stat,total);
  }

  /**
   * Add some stats.
   * @param stats Stats to add.
   */
  public void addStats(BasicStatsSet stats)
  {
    for(Map.Entry<StatDescription,FixedDecimalsInteger> entry : stats._stats.entrySet())
    {
      StatDescription stat=entry.getKey();
      FixedDecimalsInteger value=entry.getValue();
      addStat(stat,value);
    }
  }

  @Override
  public boolean equals(Object object)
  {
    if (this==object) return true;
    if (!(object instanceof BasicStatsSet))
    {
      return false;
    }
    BasicStatsSet other=(BasicStatsSet)object;
    return _stats.equals(other._stats);
  }

  @Override
  public String toString()
  {
    return toString(" / ");
  }

  /**
   * Dump the contents of this stats set.
   * @return A string with one stat on each line.
   */
  public String dump()
  {
    return toString(EndOfLine.NATIVE_EOL);
  }

  private String toString(String separator)
  {
    StringBuilder sb=new StringBuilder();
    int index=0;
    for(StatDescription stat : getSortedStats())
    {
      FixedDecimalsInteger statValue=_stats.get(stat);
      if (statValue!=null)
      {
        if (index>0)
        {
          sb.append(separator);
        }
        sb.append(stat.getName()).append(": ");
        sb.append((statValue!=null)?statValue:"N/A");
        index++;
      }
    }
    return sb.toString().trim();
  }
}
