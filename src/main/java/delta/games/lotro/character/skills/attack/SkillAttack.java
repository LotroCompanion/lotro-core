package delta.games.lotro.character.skills.attack;

import delta.games.lotro.common.enums.DamageQualifier;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.utils.maths.Progression;

/**
 * Skill attack description.
 * @author DAM
 */
public class SkillAttack
{
  private DamageQualifier _damageQualifier;
  // TODO Critical Effects
  // TODO Positional Effects
  // TODO SuperCritical Effects
  // TODO Target Effects
  private DamageType _damageType; // nullable
  private Implement _implementUsage;
  private float _damageMax;
  private Progression _damageMaxProgression;
  // TODO Damage max modifiers (stat modifiers)
  private float _damageAddContributionMultiplier;
  private Progression _damageAddContributionProgression;
  // TODO Damage add contribution mods
  private float _damageMaxVariance;
  private float _damageModifier=1.0f;

  public DamageQualifier getDamageQualifier()
  {
    return _damageQualifier;
  }

  public void setDamageQualifier(DamageQualifier damageQualifier)
  {
    _damageQualifier=damageQualifier;
  }

  public DamageType getDamageType()
  {
    return _damageType;
  }

  public void setDamageType(DamageType damageType)
  {
    _damageType=damageType;
  }

  public float getDamageModifier()
  {
    return _damageModifier;
  }
}
