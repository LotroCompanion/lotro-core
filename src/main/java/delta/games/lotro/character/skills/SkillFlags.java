package delta.games.lotro.character.skills;

/**
 * @author dm
 */
public class SkillFlags
{
  /**
   * Fast skill.
   */
  public static int FAST=1<<0;
  /**
   * Immediate skill.
   */
  public static int IMMEDIATE=1<<1;
  /**
   * Uses magic.
   */
  public static int USES_MAGIC=1<<2;
  /**
   * Uses melee.
   */
  public static int USES_MELEE=1<<3;
  /**
   * Uses ranged.
   */
  public static int USES_RANGED=1<<4;
}