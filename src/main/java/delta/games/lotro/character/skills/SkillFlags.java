package delta.games.lotro.character.skills;

/**
 * Skill flags.
 * @author DAM
 */
public class SkillFlags
{
  /**
   * Fast skill.
   */
  public static final int FAST=1<<0;
  /**
   * Immediate skill.
   */
  public static final int IMMEDIATE=1<<1;
  /**
   * Uses magic.
   */
  public static final int USES_MAGIC=1<<2;
  /**
   * Uses melee.
   */
  public static final int USES_MELEE=1<<3;
  /**
   * Uses ranged.
   */
  public static final int USES_RANGED=1<<4;
  /**
   * Is toggle.
   */
  public static final int IS_TOGGLE=1<<5;
  /**
   * Harmful.
   */
  public static final int HARMFUL=1<<6;
}
