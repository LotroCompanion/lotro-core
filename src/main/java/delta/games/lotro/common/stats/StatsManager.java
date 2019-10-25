package delta.games.lotro.common.stats;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Stats manager.
 * Builds a result stats set from:
 * <ul>default/automatic stats,
 * <ul>custom stats,
 * <ul>a merge mode.
 * </ul>
 * @author DAM
 */
public class StatsManager
{
  private CustomStatsMergeMode _mode;
  private BasicStatsSet _default;
  private BasicStatsSet _custom;
  private BasicStatsSet _result;

  /**
   * Constructor.
   */
  public StatsManager()
  {
    _mode=CustomStatsMergeMode.AUTO;
    _default=new BasicStatsSet();
    _custom=new BasicStatsSet();
    _result=new BasicStatsSet();
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public StatsManager(StatsManager source)
  {
    _mode=source._mode;
    _default=new BasicStatsSet(source._default);
    _custom=new BasicStatsSet(source._custom);
    _result=new BasicStatsSet(source._result);
  }

  /**
   * Get the merge mode.
   * @return the merge mode.
   */
  public CustomStatsMergeMode getMode()
  {
    return _mode;
  }

  /**
   * Set the merge mode.
   * @param mode Mode to set.
   */
  public void setMode(CustomStatsMergeMode mode)
  {
    _mode=mode;
  }

  /**
   * Get the default/automatic stats set.
   * @return the default/automatic stats set.
   */
  public BasicStatsSet getDefault()
  {
    return _default;
  }

  /**
   * Get the custom stats set.
   * @return the custom stats set.
   */
  public BasicStatsSet getCustom()
  {
    return _custom;
  }

  /**
   * Get the result stats set.
   * @return the result stats set.
   */
  public BasicStatsSet getResult()
  {
    return _result;
  }

  /**
   * Apply merge using current merge mode and the current stats.
   */
  public void apply()
  {
    if (_mode==CustomStatsMergeMode.AUTO)
    {
      // Ignore custom stats
      _result.clear();
      _result.addStats(_default);
    }
    else if (_mode==CustomStatsMergeMode.SET)
    {
      // Ignore default stats
      _result.setStats(_custom);
    }
    else if (_mode==CustomStatsMergeMode.ADD)
    {
      // Add both stats sets
      _result.clear();
      _result.addStats(_default);
      _result.addStats(_custom);
    }
    else if (_mode==CustomStatsMergeMode.MERGE)
    {
      // Merge stats sets
      _result.clear();
      _result.addStats(_default);
      for(StatDescription stat : _custom.getStats())
      {
        FixedDecimalsInteger value=_custom.getStat(stat);
        int internalValue=value.getInternalValue();
        if (internalValue==0)
        {
          _result.removeStat(stat);
        }
        else
        {
          _result.setStat(stat,value);
        }
      }
    }
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Mode: ").append(_mode);
    if (_default.getStatsCount()>0)
    {
      sb.append(", auto=").append(_default);
    }
    if (_default.getStatsCount()>0)
    {
      sb.append(", custom=").append(_custom);
    }
    if (_default.getStatsCount()>0)
    {
      sb.append(", result=").append(_result);
    }
    return sb.toString();
  }
}
