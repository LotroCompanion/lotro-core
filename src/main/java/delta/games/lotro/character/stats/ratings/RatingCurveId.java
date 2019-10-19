package delta.games.lotro.character.stats.ratings;

/**
 * Identifiers for 'rating to percentage' curves.
 * @author DAM
 */
public enum RatingCurveId
{
  /**
   * Critical hit chance.
   */
  CRITICAL_HIT,
  /**
   * Decvastate hit chance.
   */
  DEVASTATE_HIT,
  /**
   * Critical/devastate hits magnitude.
   */
  CRIT_DEVASTATE_MAGNITUDE,
  /**
   * Finesse.
   */
  FINESSE,
  /**
   * Damage.
   */
  DAMAGE,
  /**
   * Healing.
   */
  HEALING,
  /**
   * Resistance.
   */
  RESISTANCE,
  /**
   * Critical defence.
   */
  CRITICAL_DEFENCE,
  /**
   * Incoming healing.
   */
  INCOMING_HEALING,
  /**
   * Avoidance chance: chance of block, parry and evade.
   */
  AVOIDANCE,
  /**
   * Partial avoidance: for block, parry and evade.
   */
  PARTIAL_AVOIDANCE,
  /**
   * Partial avoidance mitigation: for block, parry and evade.
   */
  PARTIAL_MITIGATION,
  /**
   * Light armour mitigation percentage.
   */
  LIGHT_MITIGATION,
  /**
   * Medium armour mitigation percentage.
   */
  MEDIUM_MITIGATION,
  /**
   * Heavy armour mitigation percentage.
   */
  HEAVY_MITIGATION
}
