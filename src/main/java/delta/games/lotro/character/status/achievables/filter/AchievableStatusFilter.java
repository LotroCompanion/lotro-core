package delta.games.lotro.character.status.achievables.filter;

import java.util.HashSet;
import java.util.Set;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.status.achievables.AchievableElementState;
import delta.games.lotro.character.status.achievables.AchievableStatus;

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
    Set<AchievableElementState> states=new HashSet<AchievableElementState>();
    for(AchievableElementState state : AchievableElementState.values())
    {
      states.add(state);
    }
    _stateFilter=new AchievableElementStateFilter(states);
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
