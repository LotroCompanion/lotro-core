package delta.games.lotro.character.achievables.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.achievables.AchievableStatus;

/**
 * Filter for achievable statuses.
 * @author DAM
 */
public abstract class AchievableStatusFilter implements Filter<AchievableStatus>
{
  private AchievableElementStateFilter _stateFilter;

  /**
   * Constructor.
   */
  public AchievableStatusFilter()
  {
    _stateFilter=new AchievableElementStateFilter(null);
  }

  /**
   * Get the state filter.
   * @return the state filter.
   */
  public AchievableElementStateFilter getStateFilter()
  {
    return _stateFilter;
  }

  @Override
  public boolean accept(AchievableStatus item)
  {
    return _stateFilter.accept(item);
  }
}
