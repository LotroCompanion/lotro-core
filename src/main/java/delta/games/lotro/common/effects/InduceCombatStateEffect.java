package delta.games.lotro.common.effects;

import delta.games.lotro.common.enums.CombatState;
import delta.games.lotro.common.math.LinearFunction;
import delta.games.lotro.common.properties.ModPropertyList;

/**
 * 'Induce combat state' effect.
 * @author DAM
 */
public class InduceCombatStateEffect extends InstantEffect
{
  private CombatState _state;
  private float _duration;
  private LinearFunction _durationFunction;
  private ModPropertyList _durationMods; // Effect_CombatState_Induce_StateDuration_ModProp_List
  private Float _breakOnHarmfullSkill; // Effect_CombatState_Induce_BreakOnHarmfulSkill_Override
  private ModPropertyList _breakOnHarmfullSkillMods; // Effect_CombatState_Induce_BreakOnHarmfulSkill_ModProp_List
  private Float _breakOnVitalLossProb; // Effect_CombatState_Induce_BreakOnVitalLossProb_Override
  private ModPropertyList _breakOnVitalLossProbMods; // Effect_CombatState_Induce_BreakOnVitalLoss_ModProp_List
  private Float _gracePeriod; // Effect_CombatState_Induce_BreakOutOfState_GracePeriod_Override
  private ModPropertyList _gracePeriodMods; // Effect_CombatState_Induce_BreakOutOfState_GracePeriod_Override_ModifierList

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
   * Get the duration modifiers.
   * @return Some modifiers or <code>null</code>.
   */
  public ModPropertyList getDurationModifiers()
  {
    return _durationMods;
  }

  /**
   * Set the duration modifiers.
   * @param durationMods Modifiers to set (may be <code>null</code>).
   */
  public void setDurationModifiers(ModPropertyList durationMods)
  {
    _durationMods=durationMods;
  }

  /**
   * Get the grace period, if any.
   * @return A duration (seconds) or <code>null</code>.
   */
  public Float getGracePeriod()
  {
    return _gracePeriod;
  }

  /**
   * Set the grace period.
   * @param gracePeriod Grace period duration (may be <code>null</code>).
   */
  public void setGracePeriod(Float gracePeriod)
  {
    _gracePeriod=gracePeriod;
  }

  /**
   * Set the grace period modifiers.
   * @return some modifiers or <code>null</code>.
   */
  public ModPropertyList getGracePeriodModifiers()
  {
    return _gracePeriodMods;
  }

  /**
   * Set the grace period modifiers.
   * @param gracePeriodMods Modifiers to set (may be <code>null</code>).
   */
  public void setGracePeriodModifiers(ModPropertyList gracePeriodMods)
  {
    _gracePeriodMods=gracePeriodMods;
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
