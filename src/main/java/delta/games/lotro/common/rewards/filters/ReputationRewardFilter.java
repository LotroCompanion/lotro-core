package delta.games.lotro.common.rewards.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.Reputation;
import delta.games.lotro.common.ReputationItem;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.lore.reputation.Faction;

/**
 * Filter for rewards that contain a reputation gain.
 * @author DAM
 */
public class ReputationRewardFilter implements Filter<Rewards>
{
  private Faction _faction;

  /**
   * Constructor.
   * @param faction Faction to select (may be <code>null</code>).
   */
  public ReputationRewardFilter(Faction faction)
  {
    _faction=faction;
  }

  /**
   * Get the faction to use.
   * @return A faction or <code>null</code>.
   */
  public Faction getFaction()
  {
    return _faction;
  }

  /**
   * Set the faction to select.
   * @param faction Faction to use, may be <code>null</code>.
   */
  public void setFaction(Faction faction)
  {
    _faction=faction;
  }

  public boolean accept(Rewards rewards)
  {
    if (_faction==null)
    {
      return true;
    }
    Reputation reputation=rewards.getReputation();
    ReputationItem[] reputationItems=reputation.getItems();
    for(ReputationItem reputationItem : reputationItems)
    {
      if (_faction==reputationItem.getFaction())
      {
        return true;
      }
    }
    return false;
  }
}
