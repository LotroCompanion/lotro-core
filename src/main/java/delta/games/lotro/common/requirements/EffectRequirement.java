package delta.games.lotro.common.requirements;

import delta.games.lotro.common.effects.Effect2;

/**
 * Effect requirement.
 * @author DAM
 */
public class EffectRequirement
{
  private Effect2 _effect;

  /**
   * Constructor.
   * @param effect Required effect.
   */
  public EffectRequirement(Effect2 effect)
  {
    _effect=effect;
  }

  /**
   * Get the required effect.
   * @return the required effect.
   */
  public Effect2 getEffect()
  {
    return _effect;
  }
}
