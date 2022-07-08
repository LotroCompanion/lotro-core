package delta.games.lotro.character.status.emotes.filters;

import java.util.Set;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.status.emotes.EmoteState;
import delta.games.lotro.character.status.emotes.EmoteStatus;

/**
 * Filter for an emote status in a given state.
 * @author DAM
 */
public class EmoteStateFilter implements Filter<EmoteStatus>
{
  private Set<EmoteState> _states;

  /**
   * Constructor.
   * @param states States to select.
   */
  public EmoteStateFilter(Set<EmoteState> states)
  {
    _states=states;
  }

  /**
   * Get the selected states.
   * @return A possibly empty but never <code>null</code> set of states.
   */
  public Set<EmoteState> getSelectedStates()
  {
    return _states;
  }

  /**
   * Set the states to select.
   * @param states States to select.
   */
  public void setStates(Set<EmoteState> states)
  {
    _states=states;
  }

  @Override
  public boolean accept(EmoteStatus status)
  {
    return _states.contains(status.getState());
  }
}
