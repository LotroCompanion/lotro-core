package delta.games.lotro.common.stats;

import org.apache.log4j.Logger;

/**
 * A LOTRO character stat.
 * @author DAM
 */
public class WellKnownStat
{
  private static final Logger LOGGER=Logger.getLogger(WellKnownStat.class);

  /**
   * Morale.
   */
  public static StatDescription MORALE=get("MORALE");
  /**
   * Power.
   */
  public static StatDescription POWER=get("POWER");
  /**
   * Armour.
   */
  public static StatDescription ARMOUR=get("ARMOUR");
  /**
   * Might.
   */
  public static StatDescription MIGHT=get("MIGHT");
  /**
   * Agility.
   */
  public static StatDescription AGILITY=get("AGILITY");
  /**
   * Vitality.
   */
  public static StatDescription VITALITY=get("VITALITY");
  /**
   * Will.
   */
  public static StatDescription WILL=get("WILL");
  /**
   * Fate.
   */
  public static StatDescription FATE=get("FATE");
  // Offence
  /**
   * Critical rating.
   */
  public static StatDescription CRITICAL_RATING=get("CRITICAL_RATING");
  /**
   * Critical % (melee).
   */
  public static StatDescription CRITICAL_MELEE_PERCENTAGE=get("CRITICAL_MELEE_PERCENTAGE");
  /**
   * Critical % (ranged).
   */
  public static StatDescription CRITICAL_RANGED_PERCENTAGE=get("CRITICAL_RANGED_PERCENTAGE");
  /**
   * Critical % (tactical).
   */
  public static StatDescription CRITICAL_TACTICAL_PERCENTAGE=get("CRITICAL_TACTICAL_PERCENTAGE");
  /**
   * Devastate % (melee).
   */
  public static StatDescription DEVASTATE_MELEE_PERCENTAGE=get("DEVASTATE_MELEE_PERCENTAGE");
  /**
   * Critical % (ranged).
   */
  public static StatDescription DEVASTATE_RANGED_PERCENTAGE=get("DEVASTATE_RANGED_PERCENTAGE");
  /**
   * Critical % (tactical).
   */
  public static StatDescription DEVASTATE_TACTICAL_PERCENTAGE=get("DEVASTATE_TACTICAL_PERCENTAGE");
  /**
   * Cri&Devastate Magnitude % (melee).
   */
  public static StatDescription CRIT_DEVASTATE_MAGNITUDE_MELEE_PERCENTAGE=get("CRIT_DEVASTATE_MAGNITUDE_MELEE_PERCENTAGE");
  /**
   * Cri&Devastate Magnitude % (ranged).
   */
  public static StatDescription CRIT_DEVASTATE_MAGNITUDE_RANGED_PERCENTAGE=get("CRIT_DEVASTATE_MAGNITUDE_RANGED_PERCENTAGE");
  /**
   * Cri&Devastate Magnitude % (tactical).
   */
  public static StatDescription CRIT_DEVASTATE_MAGNITUDE_TACTICAL_PERCENTAGE=get("CRIT_DEVASTATE_MAGNITUDE_TACTICAL_PERCENTAGE");
  /**
   * Finesse.
   */
  public static StatDescription FINESSE=get("FINESSE");
  /**
   * Finesse %.
   */
  public static StatDescription FINESSE_PERCENTAGE=get("FINESSE_PERCENTAGE");
  /**
   * Physical Mastery.
   */
  public static StatDescription PHYSICAL_MASTERY=get("PHYSICAL_MASTERY");
  /**
   * Melee Damage %.
   */
  public static StatDescription MELEE_DAMAGE_PERCENTAGE=get("MELEE_DAMAGE_PERCENTAGE");
  /**
   * Ranged Damage %.
   */
  public static StatDescription RANGED_DAMAGE_PERCENTAGE=get("RANGED_DAMAGE_PERCENTAGE");
  /**
   * Tactical Mastery.
   */
  public static StatDescription TACTICAL_MASTERY=get("TACTICAL_MASTERY");
  /**
   * Tactical Damage %.
   */
  public static StatDescription TACTICAL_DAMAGE_PERCENTAGE=get("TACTICAL_DAMAGE_PERCENTAGE");
  /**
   * Outgoing Healing Rating.
   */
  public static StatDescription OUTGOING_HEALING=get("OUTGOING_HEALING");
  /**
   * Outgoing Healing %.
   */
  public static StatDescription OUTGOING_HEALING_PERCENTAGE=get("OUTGOING_HEALING_PERCENTAGE");
  // Defence
  /**
   * Resistance.
   */
  public static StatDescription RESISTANCE=get("RESISTANCE");
  /**
   * Resistance %.
   */
  public static StatDescription RESISTANCE_PERCENTAGE=get("RESISTANCE_PERCENTAGE");
  /**
   * Critical Defence.
   */
  public static StatDescription CRITICAL_DEFENCE=get("CRITICAL_DEFENCE");
  /**
   * Critical Defence % (melee/ranged/tactical).
   */
  public static StatDescription CRITICAL_DEFENCE_PERCENTAGE=get("CRITICAL_DEFENCE_PERCENTAGE");
  /**
   * Critical Defence % (melee).
   */
  public static StatDescription MELEE_CRITICAL_DEFENCE=get("MELEE_CRITICAL_DEFENCE");
  /**
   * Critical Defence % (ranged).
   */
  public static StatDescription RANGED_CRITICAL_DEFENCE=get("RANGED_CRITICAL_DEFENCE");
  /**
   * Critical Defence % (tactical).
   */
  public static StatDescription TACTICAL_CRITICAL_DEFENCE=get("TACTICAL_CRITICAL_DEFENCE");
  /**
   * Incoming Healing.
   */
  public static StatDescription INCOMING_HEALING=get("INCOMING_HEALING");
  /**
   * Incoming Healing percentage.
   */
  public static StatDescription INCOMING_HEALING_PERCENTAGE=get("INCOMING_HEALING_PERCENTAGE");
  // Avoidance
  /**
   * Block.
   */
  public static StatDescription BLOCK=get("BLOCK");
  /**
   * Block (percentage).
   */
  public static StatDescription BLOCK_PERCENTAGE=get("BLOCK_PERCENTAGE");
  /**
   * Partial Block (percentage).
   */
  public static StatDescription PARTIAL_BLOCK_PERCENTAGE=get("PARTIAL_BLOCK_PERCENTAGE");
  /**
   * Partial Block Mitigation (percentage).
   */
  public static StatDescription PARTIAL_BLOCK_MITIGATION_PERCENTAGE=get("PARTIAL_BLOCK_MITIGATION_PERCENTAGE");
  /**
   * Parry.
   */
  public static StatDescription PARRY=get("PARRY");
  /**
   * Parry (percentage).
   */
  public static StatDescription PARRY_PERCENTAGE=get("PARRY_PERCENTAGE");
  /**
   * Partial Parry (percentage).
   */
  public static StatDescription PARTIAL_PARRY_PERCENTAGE=get("PARTIAL_PARRY_PERCENTAGE");
  /**
   * Partial Parry Mitigation (percentage).
   */
  public static StatDescription PARTIAL_PARRY_MITIGATION_PERCENTAGE=get("PARTIAL_PARRY_MITIGATION_PERCENTAGE");
  /**
   * Evade.
   */
  public static StatDescription EVADE=get("EVADE");
  /**
   * Evade (percentage).
   */
  public static StatDescription EVADE_PERCENTAGE=get("EVADE_PERCENTAGE");
  /**
   * Partial Evade (percentage).
   */
  public static StatDescription PARTIAL_EVADE_PERCENTAGE=get("PARTIAL_EVADE_PERCENTAGE");
  /**
   * Partial Evade Mitigation (percentage).
   */
  public static StatDescription PARTIAL_EVADE_MITIGATION_PERCENTAGE=get("PARTIAL_EVADE_MITIGATION_PERCENTAGE");
  // Mitigations
  // Damage Source: Melee, Ranged, Tactical
  // Damage Type: Physical Mitigation, Tactical Mitigation
  /**
   * Physical mitigation.
   */
  public static StatDescription PHYSICAL_MITIGATION=get("PHYSICAL_MITIGATION");
  /**
   * Physical mitigation percentage.
   */
  public static StatDescription PHYSICAL_MITIGATION_PERCENTAGE=get("PHYSICAL_MITIGATION_PERCENTAGE");
  /**
   * Orc-craft and Fell-wrought mitigation.
   */
  public static StatDescription OCFW_MITIGATION=get("OCFW_MITIGATION");
  /**
   * Orc-craft and Fell-wrought mitigation percentage.
   */
  public static StatDescription OCFW_MITIGATION_PERCENTAGE=get("OCFW_MITIGATION_PERCENTAGE");
  /**
   * Tactical mitigation.
   */
  public static StatDescription TACTICAL_MITIGATION=get("TACTICAL_MITIGATION");
  /**
   * Tactical mitigation percentage.
   */
  public static StatDescription TACTICAL_MITIGATION_PERCENTAGE=get("TACTICAL_MITIGATION_PERCENTAGE");
  /**
   * Fire mitigation percentage.
   */
  public static StatDescription FIRE_MITIGATION_PERCENTAGE=get("FIRE_MITIGATION_PERCENTAGE");
  /**
   * Lightning mitigation percentage.
   */
  public static StatDescription LIGHTNING_MITIGATION_PERCENTAGE=get("LIGHTNING_MITIGATION_PERCENTAGE");
  /**
   * Frost mitigation percentage.
   */
  public static StatDescription FROST_MITIGATION_PERCENTAGE=get("FROST_MITIGATION_PERCENTAGE");
  /**
   * Acid mitigation percentage.
   */
  public static StatDescription ACID_MITIGATION_PERCENTAGE=get("ACID_MITIGATION_PERCENTAGE");
  /**
   * Shadow mitigation percentage.
   */
  public static StatDescription SHADOW_MITIGATION_PERCENTAGE=get("SHADOW_MITIGATION_PERCENTAGE");
  /**
   * non-Combat Morale Regeneration.
   */
  public static StatDescription OCMR=get("OCMR");
  /**
   * In-Combat Morale Regeneration.
   */
  public static StatDescription ICMR=get("ICMR");
  /**
   * non-Combat Power Regeneration.
   */
  public static StatDescription OCPR=get("OCPR");
  /**
   * In-Combat Power Regeneration.
   */
  public static StatDescription ICPR=get("ICPR");
  /**
   * Hope.
   */
  public static StatDescription HOPE=get("HOPE");
  /**
   * Light of Eärendil.
   */
  public static StatDescription LIGHT_OF_EARENDIL=get("LIGHT_OF_EARENDIL");
  /**
   * Ranged Defence (%).
   */
  public static StatDescription RANGED_DEFENCE_PERCENTAGE=get("RANGED_DEFENCE_PERCENTAGE");
  /**
   * War-steed Endurance.
   */
  public static StatDescription WARSTEED_ENDURANCE=get("WARSTEED_ENDURANCE");
  /**
   * War-steed Power.
   */
  public static StatDescription WARSTEED_POWER=get("WARSTEED_POWER");
  /**
   * War-steed Agility.
   */
  public static StatDescription WARSTEED_AGILITY=get("WARSTEED_AGILITY");
  /**
   * War-steed Strength.
   */
  public static StatDescription WARSTEED_STRENGTH=get("WARSTEED_STRENGTH");

  /**
   * Get a well-known stat using its legacy key.
   * @param legacyKey Key to use.
   * @return A stat or <code>null</code> if not found.
   */
  public static final StatDescription get(String legacyKey)
  {
    StatDescription ret=StatsRegistry.getInstance().getByKey(legacyKey);
    if (ret==null)
    {
      LOGGER.warn("Well-known stat not found! Key="+legacyKey);
    }
    return ret;
  }
}
