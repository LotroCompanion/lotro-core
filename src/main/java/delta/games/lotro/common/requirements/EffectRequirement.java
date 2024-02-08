package delta.games.lotro.common.requirements;

import delta.games.lotro.common.effects.Effect;

/**
 * Effect requirement.
 * @author DAM
 */
public class EffectRequirement
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
}
