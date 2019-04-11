package delta.games.lotro.lore.deeds.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.lore.deeds.DeedDescription;

/**
 * Deed filter that delegates filtering to a rewards filter.
 * @author DAM
 */
public class DeedRewardFilter implements Filter<DeedDescription>
{
  private Filter<Rewards> _rewardsFilter;

  /**
   * Constructor.
   * @param rewardsFilter Rewards filter.
   */
  public DeedRewardFilter(Filter<Rewards> rewardsFilter)
  {
    _rewardsFilter=rewardsFilter;
  }

  public boolean accept(DeedDescription deed)
  {
    return _rewardsFilter.accept(deed.getRewards());
  }
}
