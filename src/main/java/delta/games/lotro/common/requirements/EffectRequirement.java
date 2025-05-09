package delta.games.lotro.common.requirements;

import delta.games.lotro.common.effects.Effect;

/**
 * Effect requirement.
 * @author DAM
 */
public class EffectRequirement implements Requirement
{
  private Effect _effect;

  /**
   * Constructor.
   * @param effect Required effect.
   */
  public EffectRequirement(Effect effect)
  {
    _effect=effect;
  }

  /**
   * Get the required effect.
   * @return the required effect.
   */
  public Effect getEffect()
  {
    return _effect;
  }

  @Override
  public String toString()
  {
    if (_effect!=null)
    {
      return "Effect "+_effect.getName();
    }
    return "";
  }
}
