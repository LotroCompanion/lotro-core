package delta.games.lotro.lore.items.legendary.non_imbued;

import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.legendary.LegacyType;

/**
 * Default non-imbued legacy.
 * @author DAM
 */
public class DefaultNonImbuedLegacy extends AbstractNonImbuedLegacy
{
  private int _effectID;
  private StatsProvider _statsProvider;

  /**
   * Constructor.
   */
  public DefaultNonImbuedLegacy()
  {
    // Nothing!
  }

  /**
   * Get the stats provider.
   * @return the stats provider.
   */
  public StatsProvider getStatsProvider()
  {
    return _statsProvider;
  }

  /**
   * Set the stats provider.
   * @param statsProvider Stats provider.
   */
  public void setStatsProvider(StatsProvider statsProvider)
  {
    _statsProvider=statsProvider;
  }

  @Override
  public StatDescription getStat()
  {
    if (_statsProvider!=null)
    {
      return _statsProvider.getFirstStat();
    }
    return null;
  }

  /**
   * Get the effect ID.
   * @return the effect ID.
   */
  public int getEffectID()
  {
    return _effectID;
  }

  /**
   * Set the effect ID.
   * @param effectID Effect ID to set.
   */
  public void setEffectID(int effectID)
  {
    _effectID=effectID;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    StatDescription stat=getStat();
    sb.append("Default non imbued: ").append(stat.getName());
    if (_effectID!=0)
    {
      sb.append(" (effectID=").append(_effectID).append(')');
    }
    LegacyType type=getType();
    if (type!=null)
    {
      sb.append(" (").append(type).append(')');
    }
    return sb.toString();
  }
}
