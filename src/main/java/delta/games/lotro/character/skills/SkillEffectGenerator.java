package delta.games.lotro.character.skills;

import delta.games.lotro.common.effects.Effect2;
import delta.games.lotro.common.effects.EffectGenerator;

/**
 * Effect generator used in skills: adds optional duration.
 * @author DAM
 */
public class SkillEffectGenerator extends EffectGenerator
{
  private Float _duration;

  /**
   * Constructor.
   * @param effect Effect.
   * @param spellcraft Spellcraft value.
   * @param duration Duration.
   */
  public SkillEffectGenerator(Effect2 effect, Float spellcraft, Float duration)
  {
    super(effect,spellcraft);
    _duration=duration;
  }

  /**
   * Get the duration.
   * @return A duration (seconds) or <code>null</code>.
   */
  public Float getDuration()
  {
    return _duration;
  }
}
