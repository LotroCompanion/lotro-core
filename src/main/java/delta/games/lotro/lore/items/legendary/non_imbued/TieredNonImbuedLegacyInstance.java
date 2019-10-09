package delta.games.lotro.lore.items.legendary.non_imbued;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.stats.StatsProvider;

/**
 * Tiered, non-imbued legacy instance.
 * @author DAM
 */
public class TieredNonImbuedLegacyInstance extends NonImbuedLegacyInstance
{
  private NonImbuedLegacyTier _legacyTier;

  /**
   * Constructor.
   */
  public TieredNonImbuedLegacyInstance()
  {
    super();
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public TieredNonImbuedLegacyInstance(TieredNonImbuedLegacyInstance source)
  {
    super(source);
    _legacyTier=source._legacyTier;
  }

  @Override
  public TieredNonImbuedLegacy getLegacy()
  {
    if (_legacyTier!=null)
    {
      return _legacyTier.getParentLegacy();
    }
    return null;
  }

  /**
   * Get the associated legacy tier.
   * @return the associated legacy tier.
   */
  public NonImbuedLegacyTier getLegacyTier()
  {
    return _legacyTier;
  }

  /**
   * Set the associated legacy tier.
   * @param legacyTier the legacy tier to set.
   */
  public void setLegacyTier(NonImbuedLegacyTier legacyTier)
  {
    _legacyTier=legacyTier;
  }

  /**
   * Get the stats contribution for this legacy instance.
   * @return some stats.
   */
  public BasicStatsSet getStats()
  {
    BasicStatsSet stats;
    if (_legacyTier!=null)
    {
      StatsProvider statsProvider=_legacyTier.getEffect().getStatsProvider();
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
    sb.append(_legacyTier!=null?_legacyTier.toString():"?");
    sb.append(", rank=").append(getRank());
    sb.append(", UI rank=").append(getUiRank());
    return sb.toString();
  }
}
