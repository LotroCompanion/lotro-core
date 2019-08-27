package delta.games.lotro.lore.items.legendary.non_imbued;

import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.stats.StatDescription;

/**
 * Non imbued legacy tier.
 * @author DAM
 */
public class NonImbuedLegacyTier
{
  private TieredNonImbuedLegacy _parent;
  private int _tier;
  private Integer _startRank;
  private Effect _effect;

  /**
   * Constructor.
   * @param legacy Parent legacy.
   * @param tier Tier (1 to 6).
   * @param effect Legadcy effect.
   */
  public NonImbuedLegacyTier(TieredNonImbuedLegacy legacy, int tier, Effect effect)
  {
    _parent=legacy;
    _tier=tier;
    _effect=effect;
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
   * Get the legacy effect.
   * @return the legacy effect.
   */
  public Effect getEffect()
  {
    return _effect;
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
    if (_effect!=null)
    {
      sb.append("ID:").append(_effect.getIdentifier()).append(' ');
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
