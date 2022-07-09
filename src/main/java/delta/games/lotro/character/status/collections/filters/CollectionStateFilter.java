package delta.games.lotro.character.status.collections.filters;

import java.util.Set;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.status.collections.CollectionState;
import delta.games.lotro.character.status.collections.CollectionStatus;

/**
 * Filter for a collection status in a given state.
 * @author DAM
 */
public class CollectionStateFilter implements Filter<CollectionStatus>
{
  private Set<CollectionState> _states;

  /**
   * Constructor.
   * @param states States to select.
   */
  public CollectionStateFilter(Set<CollectionState> states)
  {
    _states=states;
  }

  /**
   * Get the selected states.
   * @return A possibly empty but never <code>null</code> set of states.
   */
  public Set<CollectionState> getSelectedStates()
  {
    return _states;
  }

  /**
   * Set the states to select.
   * @param states States to select.
   */
  public void setStates(Set<CollectionState> states)
  {
    _states=states;
  }

  @Override
  public boolean accept(CollectionStatus status)
  {
    return _states.contains(status.getState());
  }
}
