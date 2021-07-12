package delta.games.lotro.character.titles.filter;

import java.util.Set;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.titles.TitleStatus;

/**
 * Filter for a title status in a given state.
 * @author DAM
 */
public class TitleStateFilter implements Filter<TitleStatus>
{
  private Set<Boolean> _states;

  /**
   * Constructor.
   * @param states States to select.
   */
  public TitleStateFilter(Set<Boolean> states)
  {
    _states=states;
  }

  /**
   * Get the selected states.
   * @return A possibly empty but never <code>null</code> set of states.
   */
  public Set<Boolean> getSelectedStates()
  {
    return _states;
  }

  /**
   * Set the states to select.
   * @param states States to select.
   */
  public void setStates(Set<Boolean> states)
  {
    _states=states;
  }

  @Override
  public boolean accept(TitleStatus titleStatus)
  {
    return _states.contains(Boolean.valueOf(titleStatus.isAcquired()));
  }
}
