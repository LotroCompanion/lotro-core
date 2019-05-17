package delta.games.lotro.common.rewards.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.rewards.Rewards;

/**
 * Filter for rewards that contain XP, or that do NOT contain XP.
 * @author DAM
 */
public class XpRewardFilter implements Filter<Rewards>
{
  private Boolean _hasXp;

  /**
   * Constructor.
   * @param hasXp Indicates if this filter shall select rewards
   * with or without XP (<code>null</code> means no filter).
   */
  public XpRewardFilter(Boolean hasXp)
  {
    _hasXp=hasXp;
  }

  /**
   * Get the value of the 'has XP' flag.
   * @return A boolean value or <code>null</code>.
   */
  public Boolean getHasXpFlag()
  {
    return _hasXp;
  }

  /**
   * Set the value of the 'has XP' flag.
   * @param hasXp Flag to set, may be <code>null</code>.
   */
  public void setHasXpFlag(Boolean hasXp)
  {
    _hasXp=hasXp;
  }

  public boolean accept(Rewards rewards)
  {
    if (_hasXp==null)
    {
      return true;
    }
    int xp=rewards.getXp();
    return (_hasXp.booleanValue())?(xp>0):(xp==0);
  }
}
