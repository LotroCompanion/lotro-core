package delta.games.lotro.common.rewards.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.rewards.Rewards;

/**
 * Filter for rewards that contain item XP, or that do NOT contain item XP.
 * @author DAM
 */
public class ItemXpRewardFilter implements Filter<Rewards>
{
  private Boolean _hasItemXp;

  /**
   * Constructor.
   * @param hasItemXp Indicates if this filter shall select rewards
   * with or without item XP (<code>null</code> means no filter).
   */
  public ItemXpRewardFilter(Boolean hasItemXp)
  {
    _hasItemXp=hasItemXp;
  }

  /**
   * Get the value of the 'has item XP' flag.
   * @return A boolean value or <code>null</code>.
   */
  public Boolean getHasItemXpFlag()
  {
    return _hasItemXp;
  }

  /**
   * Set the value of the 'has item XP' flag.
   * @param hasItemXp Flag to set, may be <code>null</code>.
   */
  public void setHasItemXpFlag(Boolean hasItemXp)
  {
    _hasItemXp=hasItemXp;
  }

  public boolean accept(Rewards rewards)
  {
    if (_hasItemXp==null)
    {
      return true;
    }
    int itemXp=rewards.getItemXp();
    return (_hasItemXp.booleanValue())?(itemXp>0):(itemXp==0);
  }
}
