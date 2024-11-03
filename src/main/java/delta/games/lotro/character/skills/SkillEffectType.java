package delta.games.lotro.character.skills;

/**
 * Skill effect type.
 * @author DAM
 */
public enum SkillEffectType
{
  /**
   * Attack (critical result).
   */
  ATTACK_CRITICAL,
  /**
   * Attack (with positional bonus).
   */
  ATTACK_POSITIONAL,
  /**
   * Attack (supercritical/devastate result).
   */
  ATTACK_SUPERCRITICAL,
  /**
   * Attack (default).
   */
  ATTACK,
  /**
   * Applied to self on critical.
   */
  SELF_CRITICAL,
  /**
   * Toggle skill effect.
   */
  TOGGLE,
  /**
   * Toggle user skill effect.
   */
  USER_TOGGLE,
  /**
   * User skill effect.
   */
  USER
}
