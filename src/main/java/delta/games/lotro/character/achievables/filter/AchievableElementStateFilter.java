package delta.games.lotro.character.achievables.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.achievables.AchievableElementState;
import delta.games.lotro.character.achievables.AchievableStatus;

/**
 * Filter for an achievable status in a given state.
 * @author DAM
 */
public class AchievableElementStateFilter implements Filter<AchievableStatus>
{
  private AchievableElementState _state;

  /**
   * Constructor.
   * @param state State to select (may be <code>null</code>).
   */
  public AchievableElementStateFilter(AchievableElementState state)
  {
    _state=state;
  }

  /**
   * Get the state to use.
   * @return A state or <code>null</code>.
   */
  public AchievableElementState getState()
  {
    return _state;
  }

  /**
   * Set the state to select.
   * @param state State to use, may be <code>null</code>.
   */
  public void setState(AchievableElementState state)
  {
    _state=state;
  }

  @Override
  public boolean accept(AchievableStatus achievable)
  {
    if (_state==null)
    {
      return true;
    }
    return achievable.getState()==_state;
  }
}
