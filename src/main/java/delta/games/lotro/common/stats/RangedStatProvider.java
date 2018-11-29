package delta.games.lotro.common.stats;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.stats.STAT;

/**
 * Stat provider with level range constraints.
 * @author DAM
 */
public class RangedStatProvider extends AbstractStatProvider
{
  private List<Integer> _minLevels;
  private List<Integer> _maxLevels;
  private List<StatProvider> _providers;

  /**
   * Constructor.
   * @param stat Targeted stat.
   */
  public RangedStatProvider(STAT stat)
  {
    super(stat);
    _minLevels=new ArrayList<Integer>();
    _maxLevels=new ArrayList<Integer>();
    _providers=new ArrayList<StatProvider>();
  }

  /**
   * Add a range.
   * @param minLevel Minimum level constraint (may be <code>null</code>).
   * @param maxLevel Maximum level constraint (may be <code>null</code>).
   * @param provider Wrapped provider.
   */
  public void addRange(Integer minLevel, Integer maxLevel, StatProvider provider)
  {
    if (provider!=null)
    {
      _providers.add(provider);
      _minLevels.add(minLevel);
      _maxLevels.add(maxLevel);
    }
  }

  /**
   * Get the number of ranges.
   * @return A number of ranges.
   */
  public int getNumberOfRanges()
  {
    return _minLevels.size();
  }

  /**
   * Get the minimum level for a range.
   * @param index Index of the targeted range, starting at 0.
   * @return A minimum level or <code>null</code> if none.
   */
  public Integer getMinimumLevel(int index)
  {
    return _minLevels.get(index);
  }

  /**
   * Get the maximum level for a range.
   * @param index Index of the targeted range, starting at 0.
   * @return A maximum level or <code>null</code> if none.
   */
  public Integer getMaximumLevel(int index)
  {
    return _maxLevels.get(index);
  }

  /**
   * Get the stat provider for a range.
   * @param index Index of the targeted range, starting at 0.
   * @return A stat provider.
   */
  public StatProvider getStatProvider(int index)
  {
    return _providers.get(index);
  }

  @Override
  public Float getStatValue(int tier, int level)
  {
    int nbRanges=_minLevels.size();
    for(int i=0;i<nbRanges;i++)
    {
      Integer minLevel=_minLevels.get(i);
      if ((minLevel!=null) && (level<minLevel.intValue()))
      {
        continue;
      }
      Integer maxLevel=_maxLevels.get(i);
      if ((maxLevel!=null) && (level>maxLevel.intValue()))
      {
        continue;
      }
      StatProvider provider=_providers.get(i);
      return provider.getStatValue(tier,level);
    }
    return null;
  }
}
