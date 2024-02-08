package delta.games.lotro.lore.items.legendary.non_imbued;

import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatsProvider;

/**
 * Non imbued legacy tier.
 * @author DAM
 */
public class NonImbuedLegacyTier
{
  private TieredNonImbuedLegacy _parent;
  private int _tier;
  private Integer _startRank;
  private int _effectID;
  private StatsProvider _statsProvider;

  /**
   * Constructor.
   * @param legacy Parent legacy.
   * @param tier Tier (1 to 6).
   * @param effectID Legacy effect ID.
   * @param statsProvider Stats provider.
   */
  public NonImbuedLegacyTier(TieredNonImbuedLegacy legacy, int tier, int effectID, StatsProvider statsProvider)
  {
    _parent=legacy;
    _tier=tier;
    _effectID=effectID;
    _statsProvider=statsProvider;
  }

  /**
   * Get the parent legacy.
   * @return the parent legacy.
   */
  public TieredNonImbuedLegacy getParentLegacy()
  {
    return _parent;
  }

  /**
   * Get the tier (1 to 6).
   * @return the tier (1 to 6).
   */
  public int getTier()
  {
    return _tier;
  }

  /**
   * Get the start rank (if any).
   * @return A rank value or <code>null</code>.
   */
  public Integer getStartRank()
  {
    return _startRank;
  }

  /**
   * Set the start rank.
   * @param startRank A rank value or <code>null</code>.
   */
  public void setStartRank(Integer startRank)
  {
    _startRank=startRank;
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
   * Get the stats provider.
   * @return the stats provider.
   */
  public StatsProvider getStatsProvider()
  {
    return _statsProvider;
  }

  /**
   * Get the associated stat.
   * @return a stat or <code>null</code> if not found.
   */
  public StatDescription getStat()
  {
    return (_parent!=null)?_parent.getStat():null;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    if (_effectID>0)
    {
      sb.append("ID:").append(_effectID).append(' ');
    }
    StatDescription stat=getStat();
    String statLabel=(stat!=null)?stat.getName():"???";
    sb.append(statLabel);
    sb.append(" (tier ");
    sb.append(_tier);
    sb.append(')');
    if (_startRank!=null)
    {
      sb.append(" (start rank ");
      sb.append(_startRank);
      sb.append(')');
    }
    return sb.toString();
  }
}
