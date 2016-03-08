package delta.games.lotro.character.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.CharacterStat;
import delta.games.lotro.character.CharacterStat.STAT;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Set of basic stats.
 * @author DAM
 */
public class BasicStatsSet
{
  private HashMap<String,CharacterStat> _stats;

  /**
   * Constructor.
   */
  public BasicStatsSet()
  {
    _stats=new HashMap<String,CharacterStat>();
  }

  /**
   * Get a stat value.
   * @param stat Stat to get.
   * @return A stat vue or <code>null</code> if not found.
   */
  public FixedDecimalsInteger getStat(STAT stat)
  {
    CharacterStat statValue=_stats.get(stat.getKey());
    if (statValue!=null)
    {
      return statValue.getValue();
    }
    return null;
  }

  /**
   * Set stat value.
   * @param stat Stat to set.
   * @param value Value to set.
   */
  public void setStat(STAT stat, FixedDecimalsInteger value)
  {
    CharacterStat statValue=new CharacterStat(stat);
    statValue.setValue(value);
    _stats.put(stat.getKey(), statValue);
  }

  /**
   * Set stat value.
   * @param stat Stat to set.
   * @param value Value to set.
   */
  public void setStat(STAT stat, int value)
  {
    CharacterStat statValue=new CharacterStat(stat);
    statValue.setValue(new FixedDecimalsInteger(value));
    _stats.put(stat.getKey(), statValue);
  }

  /**
   * Add stat value.
   * @param stat Stat to set.
   * @param value Value to set.
   */
  public void addStat(STAT stat, FixedDecimalsInteger value)
  {
    CharacterStat cStat=_stats.get(stat.getKey());
    FixedDecimalsInteger total;
    if (cStat==null)
    {
      cStat=new CharacterStat(stat);
      _stats.put(stat.getKey(),cStat);
      total=new FixedDecimalsInteger(value);
    }
    else
    {
      total=new FixedDecimalsInteger(value);
      total.add(cStat.getValue());
    }
    cStat.setValue(total);
  }

  /**
   * Add some stats.
   * @param stats Stats to add.
   */
  public void addStats(BasicStatsSet stats)
  {
    for(CharacterStat stat : stats._stats.values())
    {
      FixedDecimalsInteger value=stat.getValue();
      if (value!=null)
      {
        addStat(stat.getStat(),value);
      }
    }
  }

  /**
   * Get all stats.
   * @return A list of character stats.
   */
  public List<CharacterStat> getAllStats()
  {
    List<CharacterStat> ret=new ArrayList<CharacterStat>();
    ret.addAll(_stats.values());
    return ret;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    for(CharacterStat.STAT stat : CharacterStat.STAT.values())
    {
      CharacterStat cStat=_stats.get(stat.getKey());
      if (cStat!=null)
      {
        sb.append(cStat);
        sb.append(EndOfLine.NATIVE_EOL);
      }
    }
    return sb.toString().trim();
  }
}
