package delta.games.lotro.common.effects;

import delta.games.lotro.common.enums.CombatState;
import delta.games.lotro.common.math.LinearFunction;

/**
 * 'Induce combat state' effect.
 * @author DAM
 */
public class InduceCombatStateEffect extends Effect
{
  private CombatState _state;
  private float _duration;
  private LinearFunction _durationFunction;

  /**
   * Constructor.
   */
  public InduceCombatStateEffect()
  {
    super();
    _state=null;
    _duration=0f;
    _durationFunction=null;
  }

  /**
   * Get the combat state.
   * @return the combat state.
   */
  public CombatState getCombatState()
  {
    return _state;
  }

  /**
   * Set the combat state.
   * @param state Combat state to set.
   */
  public void setCombatState(CombatState state)
  {
    _state=state;
  }

  /**
   * Get the constant duration.
   * @return A duration (seconds).
   */
  public float getDuration()
  {
    return _duration;
  }

  /**
   * Set the duration.
   * @param duration Duration (seconds).
   */
  public void setDuration(float duration)
  {
    _duration=duration;
  }

  /**
   * Get the duration function.
   * @return the duration function (or <code>null</code> if not set).
   */
  public LinearFunction getDurationFunction()
  {
    return _durationFunction;
  }

  /**
   * Set the duration function.
   * @param durationFunction Function to set.
   */
  public void setDurationFunction(LinearFunction durationFunction)
  {
    _durationFunction=durationFunction;
 }

  /**
   * Get the duration.
   * @param level Spell-craft level.
   * @return A duration (seconds).
   */
  public float getDuration(int level)
  {
    float ret=_duration;
    if (_durationFunction!=null)
    {
      Float duration=_durationFunction.getValue(level);
      if (duration!=null)
      {
        ret=duration.floatValue();
      }
    }
    return ret;
  }
}
