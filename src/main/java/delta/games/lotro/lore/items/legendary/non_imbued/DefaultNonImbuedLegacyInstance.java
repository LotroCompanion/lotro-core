package delta.games.lotro.lore.items.legendary.non_imbued;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.stats.StatsProvider;

/**
 * Default, non-imbued legacy instance.
 * @author DAM
 */
public class DefaultNonImbuedLegacyInstance extends NonImbuedLegacyInstance
{
  private DefaultNonImbuedLegacy _legacy;

  /**
   * Constructor.
   */
  public DefaultNonImbuedLegacyInstance()
  {
    super();
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public DefaultNonImbuedLegacyInstance(DefaultNonImbuedLegacyInstance source)
  {
    super(source);
    _legacy=source._legacy;
  }

  /**
   * Get the associated legacy.
   * @return the associated legacy.
   */
  @Override
  public DefaultNonImbuedLegacy getLegacy()
  {
    return _legacy;
  }

  /**
   * Set the associated legacy.
   * @param legacy the legacy to set.
   */
  public void setLegacy(DefaultNonImbuedLegacy legacy)
  {
    _legacy=legacy;
  }


  /**
   * Get the stats contribution for this legacy instance.
   * @return some stats.
   */
  public BasicStatsSet getStats()
  {
    BasicStatsSet stats;
    if (_legacy!=null)
    {
      StatsProvider statsProvider=_legacy.getStatsProvider();
      int rank=getRank();
      stats=statsProvider.getStats(1,rank);
    }
    else
    {
      stats=new BasicStatsSet();
    }
    return stats;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_legacy!=null?_legacy.toString():"?");
    sb.append(", rank=").append(getRank());
    sb.append(", UI rank=").append(getUiRank());
    return sb.toString();
  }
}
