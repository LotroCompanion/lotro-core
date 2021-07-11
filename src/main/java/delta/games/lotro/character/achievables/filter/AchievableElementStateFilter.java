package delta.games.lotro.character.achievables.filter;

import java.util.Set;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.achievables.AchievableElementState;
import delta.games.lotro.character.achievables.AchievableStatus;

/**
 * Filter for an achievable status in a given state.
 * @author DAM
 */
public class AchievableElementStateFilter implements Filter<AchievableStatus>
{
  private Set<AchievableElementState> _states;

  /**
   * Constructor.
   * @param states States to select.
   */
  public AchievableElementStateFilter(Set<AchievableElementState> states)
  {
    _states=states;
  }

  /**
   * Get the selected states.
   * @return A possibly empty but never <code>null</code> set of states.
   */
  public Set<AchievableElementState> getSelectedStates()
  {
    return _states;
  }

  /**
   * Set the states to select.
   * @param states States to select.
   */
  public void setStates(Set<AchievableElementState> states)
  {
    _states=states;
  }

  @Override
  public boolean accept(AchievableStatus achievable)
  {
    return _states.contains(achievable.getState());
  }
}
