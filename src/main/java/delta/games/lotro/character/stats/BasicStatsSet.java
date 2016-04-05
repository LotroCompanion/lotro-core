package delta.games.lotro.character.stats;

import java.util.HashMap;
import java.util.Map;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Set of basic stats.
 * @author DAM
 */
public class BasicStatsSet
{
  private HashMap<STAT,FixedDecimalsInteger> _stats;

  /**
   * Constructor.
   */
  public BasicStatsSet()
  {
    _stats=new HashMap<STAT,FixedDecimalsInteger>();
  }

  /**
   * Copy constructor.
   * @param source Source object.
   */
  public BasicStatsSet(BasicStatsSet source)
  {
    _stats=new HashMap<STAT,FixedDecimalsInteger>();
    setStats(source);
  }

  /**
   * Get a stat value.
   * @param stat Stat to get.
   * @return A stat value or <code>null</code> if not found.
   */
  public FixedDecimalsInteger getStat(STAT stat)
  {
    FixedDecimalsInteger statValue=_stats.get(stat);
    return statValue;
  }

  /**
   * Set stat value.
   * @param stat Stat to set.
   * @param value Value to set.
   */
  public void setStat(STAT stat, FixedDecimalsInteger value)
  {
    _stats.put(stat, value);
  }

  /**
   * Set stat value.
   * @param stat Stat to set.
   * @param value Value to set.
   */
  public void setStat(STAT stat, int value)
  {
    _stats.put(stat, new FixedDecimalsInteger(value));
  }

  /**
   * Copy stats from a source.
   * @param stats Source stats.
   */
  public void setStats(BasicStatsSet stats)
  {
    for(Map.Entry<STAT,FixedDecimalsInteger> entry : stats._stats.entrySet())
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
  public void setStat(STAT stat, float value)
  {
    _stats.put(stat, new FixedDecimalsInteger(value));
  }

  /**
   * Add stat value.
   * @param stat Stat to set.
   * @param value Value to set.
   */
  public void addStat(STAT stat, FixedDecimalsInteger value)
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
    for(Map.Entry<STAT,FixedDecimalsInteger> entry : stats._stats.entrySet())
    {
      STAT stat=entry.getKey();
      FixedDecimalsInteger value=entry.getValue();
      addStat(stat,value);
    }
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
    for(STAT stat : STAT.values())
    {
      FixedDecimalsInteger statValue=_stats.get(stat);
      if (statValue!=null)
      {
        if (index>0)
        {
          sb.append(separator);
        }
        sb.append(stat).append(": ");
        sb.append((statValue!=null)?statValue:"N/A");
        index++;
      }
    }
    return sb.toString().trim();
  }
}
