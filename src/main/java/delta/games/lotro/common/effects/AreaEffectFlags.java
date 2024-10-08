package delta.games.lotro.common.effects;

/**
 * Flags for area effects.
 * @author DAM
 */
public class AreaEffectFlags
{
  /**
   * Should affect caster.
   */
  public static final int SHOULD_AFFECT_CASTER=1<<0;
  /**
   * Affects monster players.
   */
  public static final int AFFECTS_MONSTER_PLAYERS=1<<1;
  /**
   * Affects monsters.
   */
  public static final int AFFECTS_MONSTERS=1<<2;
  /**
   * Affects players.
   */
  public static final int AFFECTS_PLAYERS=1<<3;
  /**
   * Affects PVP players.
   */
  public static final int AFFECTS_PVP_PLAYERS=1<<4;
  /**
   * Affects session players.
   */
  public static final int AFFECTS_SESSION_PLAYERS=1<<5;
  /**
   * Affects distance battle units.
   */
  public static final int AFFECTS_DISTANT_BATTLE_UNITS=1<<6;
  /**
   * Affects player pets.
   */
  public static final int AFFECTS_PLAYER_PETS=1<<7;
  /**
   * Check range to target.
   */
  public static final int CHECK_RANGE_TO_TARGET=1<<8;
}
