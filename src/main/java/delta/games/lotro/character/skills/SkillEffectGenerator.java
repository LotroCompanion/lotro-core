package delta.games.lotro.character.skills;

import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectGenerator;

/**
 * Effect generator used in skills: adds optional duration.
 * @author DAM
 */
public class SkillEffectGenerator extends EffectGenerator
{
  private Float _duration;
  private SkillEffectType _type;

  // TODO Add Skill_EffectImplementUsage

  /**
   * Constructor.
   * @param effect Effect.
   * @param spellcraft Spellcraft value.
   * @param duration Duration.
   * @param type Type.
   */
  public SkillEffectGenerator(Effect effect, Float spellcraft, Float duration, SkillEffectType type)
  {
    super(effect,spellcraft);
    _duration=duration;
    _type=type;
  }

  /**
   * Get the duration.
   * @return A duration (seconds) or <code>null</code>.
   */
  public Float getDuration()
  {
    return _duration;
  }

  /**
   * Get the skill effect type.
   * @return A skill effect type.
   */
  public SkillEffectType getType()
  {
    return _type;
  }
}
