package delta.games.lotro.lore.xrefs.effects;

/**
 * Roles in effect cross-references.
 * @author DAM
 */
public enum EffectRole
{
  /**
   * Effect found as a child effect of another effect.
   */
  PARENT_EFFECT,
  /**
   * Effect used by a skill.
   */
  SKILL_USED_BY,
  /**
   * Effect used by a trait.
   */
  TRAIT_USED_BY,
  /**
   * Effect used by a mob.
   */
  MOB_USED_BY
}
