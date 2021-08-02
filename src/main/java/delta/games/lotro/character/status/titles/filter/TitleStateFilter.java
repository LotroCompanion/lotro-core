package delta.games.lotro.character.status.titles.filter;

import java.util.Set;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.status.titles.TitleState;
import delta.games.lotro.character.status.titles.TitleStatus;

/**
 * Filter for a title status in a given state.
 * @author DAM
 */
public class TitleStateFilter implements Filter<TitleStatus>
{
  private Set<TitleState> _states;

  /**
   * Constructor.
   * @param states States to select.
   */
  public TitleStateFilter(Set<TitleState> states)
  {
    _states=states;
  }

  /**
   * Get the selected states.
   * @return A possibly empty but never <code>null</code> set of states.
   */
  public Set<TitleState> getSelectedStates()
  {
    return _states;
  }

  /**
   * Set the states to select.
   * @param states States to select.
   */
  public void setStates(Set<TitleState> states)
  {
    _states=states;
  }

  @Override
  public boolean accept(TitleStatus titleStatus)
  {
    return _states.contains(titleStatus.getState());
  }
}
