package delta.games.lotro.character.skills;

import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.enums.ImplementUsageType;

/**
 * Effect generator used in skills: adds optional duration.
 * @author DAM
 */
public class SkillEffectGenerator extends EffectGenerator
{
  private Float _duration;
  private SkillEffectType _type;
  private ImplementUsageType _implementUsage;

  /**
   * Constructor.
   * @param effect Effect.
   * @param spellcraft Spellcraft value.
   * @param duration Duration.
   */
  public SkillEffectGenerator(Effect effect, Float spellcraft, Float duration)
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

  /**
   * Get the skill effect type.
   * @return A skill effect type.
   */
  public SkillEffectType getType()
  {
    return _type;
  }

  /**
   * Set the effect type.
   * @param type Type to set.
   */
  public void setType(SkillEffectType type)
  {
    _type=type;
  }

  /**
   * Get the implement usage.
   * @return the implement usage.
   */
  public ImplementUsageType getImplementUsage()
  {
    return _implementUsage;
  }

  /**
   * Set the implement usage.
   * @param implementUsage Implement usage to set.
   */
  public void setImplementUsage(ImplementUsageType implementUsage)
  {
    _implementUsage=implementUsage;
  }
}
