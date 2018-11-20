package delta.games.lotro.common.stats;

import delta.games.lotro.character.stats.STAT;

/**
 * Stat provider with level range constraints.
 * @author DAM
 */
public class RangedStatProvider implements StatProvider
{
  private Integer _minLevel;
  private Integer _maxLevel;
  private StatProvider _provider;

  /**
   * Constructor.
   * @param provider Wrapped provider.
   * @param minlevel Minimum level constraint (may be <code>null</code>).
   * @param maxLevel Maximum level constraint (may be <code>null</code>).
   */
  public RangedStatProvider(StatProvider provider, Integer minlevel, Integer maxLevel)
  {
    _provider=provider;
    _minLevel=minlevel;
    _maxLevel=maxLevel;
  }

  @Override
  public STAT getStat()
  {
    return _provider.getStat();
  }

  @Override
  public Float getStatValue(int tier, int level)
  {
    if ((_minLevel!=null) && (level<_minLevel.intValue()))
    {
      return null;
    }
    if ((_maxLevel!=null) && (level>_maxLevel.intValue()))
    {
      return null;
    }
    return _provider.getStatValue(tier,level);
  }
}
