package delta.games.lotro.common.effects;

/**
 * Effect flags.
 * @author DAM
 */
public class EffectFlags
{
  /**
   * Debuff.
   */
  public static final int DEBUFF=1<<0;
  /**
   * Harmful.
   */
  public static final int HARMFUL=1<<1;
  /**
   * Curable.
   */
  public static final int CURABLE=1<<2;
  /**
   * Removal only in combat.
   */
  public static final int REMOVAL_ONLY_IN_COMBAT=1<<3;
  /**
   * Remove on awaken.
   */
  public static final int REMOVE_ON_AWAKEN=1<<4;
  /**
   * Remove on defeat.
   */
  public static final int REMOVE_ON_DEFEAT=1<<5;
  /**
   * Remove on pulse resist.
   */
  public static final int REMOVE_ON_PULSE_RESIST=1<<6;
  /**
   * Send to client.
   */
  public static final int SEND_TO_CLIENT=1<<7;
  /**
   * UI visible.
   */
  public static final int UI_VISIBLE=1<<8;
  /**
   * Duration: combat only.
   */
  public static final int DURATION_COMBAT_ONLY=1<<9;
  /**
   * Duration: expires in real time (or in gaming time).
   */
  public static final int DURATION_EXPIRES_IN_REAL_TIME=1<<10;
  /**
   * Duration: permanent.
   */
  public static final int DURATION_PERMANENT=1<<11;
}
