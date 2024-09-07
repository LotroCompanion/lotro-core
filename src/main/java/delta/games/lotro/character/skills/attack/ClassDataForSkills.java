package delta.games.lotro.character.skills.attack;

import delta.games.lotro.common.enums.DamageQualifier;

/**
 * Class data for skill details.
 * @author DAM
 */
public class ClassDataForSkills
{
  /**
   * Get the base damage for this class and the given damage qualifier.
   * @param damageQualifier Damage qualifier
   * @return A value.
   */
  public float getBaseDamageForQualifier(DamageQualifier damageQualifier)
  {
    return 1;
  }
}
