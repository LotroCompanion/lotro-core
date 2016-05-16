package delta.games.lotro.character.stats;

import java.util.HashMap;

/**
 * A LOTRO character stat.
 * @author DAM
 */
public enum STAT
{
  /**
   * Morale.
   */
  MORALE("Morale"),
  /**
   * Power.
   */
  POWER("Power"),
  /**
   * Armour.
   */
  ARMOUR("Armour"),
  /**
   * Might.
   */
  MIGHT("Might"),
  /**
   * Agility.
   */
  AGILITY("Agility"),
  /**
   * Vitality.
   */
  VITALITY("Vitality"),
  /**
   * Will.
   */
  WILL("Will"),
  /**
   * Fate.
   */
  FATE("Fate"),
  // Offence
  /**
   * Critical rating.
   */
  CRITICAL_RATING("Critical Rating", "CRITICAL_HIT", "Critical hit"),
  /**
   * Critical % (melee).
   */
  CRITICAL_MELEE_PERCENTAGE("Critical % (melee)"),
  /**
   * Critical % (ranged).
   */
  CRITICAL_RANGED_PERCENTAGE("Critical % (ranged)"),
  /**
   * Critical % (tactical).
   */
  CRITICAL_TACTICAL_PERCENTAGE("Critical % (tactical)"),
  /**
   * Devastate % (melee).
   */
  DEVASTATE_MELEE_PERCENTAGE("Devastate % (melee)"),
  /**
   * Critical % (ranged).
   */
  DEVASTATE_RANGED_PERCENTAGE("Devastate % (ranged)"),
  /**
   * Critical % (tactical).
   */
  DEVASTATE_TACTICAL_PERCENTAGE("Devastate % (tactical)"),
  /**
   * Cri&Devastate Magnitude % (melee).
   */
  CRIT_DEVASTATE_MAGNITUDE_MELEE_PERCENTAGE("Critical & Devastate Magnitude % (melee)"),
  /**
   * Cri&Devastate Magnitude % (ranged).
   */
  CRIT_DEVASTATE_MAGNITUDE_RANGED_PERCENTAGE("Critical & Devastate Magnitude % (ranged)"),
  /**
   * Cri&Devastate Magnitude % (tactical).
   */
  CRIT_DEVASTATE_MAGNITUDE_TACTICAL_PERCENTAGE("Critical & Devastate Magnitude % (tactical)"),
  /**
   * Finesse.
   */
  FINESSE("Finesse"),
  /**
   * Finesse %.
   */
  FINESSE_PERCENTAGE("Finesse %"),
  /**
   * Physical Mastery.
   */
  PHYSICAL_MASTERY("Physical Mastery"),
  /**
   * Melee Damage %.
   */
  MELEE_DAMAGE_PERCENTAGE("Melee Damage %"),
  /**
   * Ranged Damage %.
   */
  RANGED_DAMAGE_PERCENTAGE("Ranged Damage %"),
  /**
   * Tactical Mastery.
   */
  TACTICAL_MASTERY("Tactical Mastery"),
  /**
   * Tactical Damage %.
   */
  TACTICAL_DAMAGE_PERCENTAGE("Tactical Damage %"),
  /**
   * Outgoing Healing %.
   */
  OUTGOING_HEALING_PERCENTAGE("Outgoing Healing %"),
  // Defence
  /**
   * Resistance.
   */
  RESISTANCE("Resistance", "Resist"),
  /**
   * Resistance %.
   */
  RESISTANCE_PERCENTAGE("Resistance %"),
  /**
   * Critical Defence.
   */
  CRITICAL_DEFENCE("Critical Defence", "CRITICAL_AVOID", "Critical avoidance"),
  /**
   * Critical Defence % (melee).
   */
  MELEE_CRITICAL_DEFENCE("Melee Critical Defence %"),
  /**
   * Critical Defence % (ranged).
   */
  RANGED_CRITICAL_DEFENCE("Ranged Critical Defence %"),
  /**
   * Critical Defence % (tactical).
   */
  TACTICAL_CRITICAL_DEFENCE("Tactical Critical Defence %"),
  /**
   * Incoming Healing.
   */
  INCOMING_HEALING("Incoming Healing"),
  /**
   * Incoming Healing percentage.
   */
  INCOMING_HEALING_PERCENTAGE("Incoming Healing %"),
  // Avoidance
  /**
   * Block.
   */
  BLOCK("Block", "Block Rating"),
  /**
   * Block (percentage).
   */
  BLOCK_PERCENTAGE("Block %"),
  /**
   * Partial Block (percentage).
   */
  PARTIAL_BLOCK_PERCENTAGE("Partial Block %"),
  /**
   * Partial Block Mitigation (percentage).
   */
  PARTIAL_BLOCK_MITIGATION_PERCENTAGE("Partial Block Mitigation %"),
  /**
   * Parry.
   */
  PARRY("Parry", "Parry Rating"),
  /**
   * Parry (percentage).
   */
  PARRY_PERCENTAGE("Parry %"),
  /**
   * Partial Parry (percentage).
   */
  PARTIAL_PARRY_PERCENTAGE("Partial Parry %"),
  /**
   * Partial Parry Mitigation (percentage).
   */
  PARTIAL_PARRY_MITIGATION_PERCENTAGE("Partial Parry Mitigation %"),
  /**
   * Evade.
   */
  EVADE("Evade", "Evade Rating"),
  /**
   * Evade (percentage).
   */
  EVADE_PERCENTAGE("Evade %"),
  /**
   * Partial Evade (percentage).
   */
  PARTIAL_EVADE_PERCENTAGE("Partial Evade %"),
  /**
   * Partial Evade Mitigation (percentage).
   */
  PARTIAL_EVADE_MITIGATION_PERCENTAGE("Partial Evade Mitigation %"),
  // Mitigations
  // Damage Source: Melee, Ranged, Tactical
  // Damage Type: Physical Mitigation, Tactical Mitigation
  /**
   * Physical mitigation.
   */
  PHYSICAL_MITIGATION("Physical Mitigation", "Physical mitigation", "PhyMit"),
  /**
   * Physical mitigation percentage.
   */
  PHYSICAL_MITIGATION_PERCENTAGE("Physical Mitigation %"),
  /**
   * Orc-craft and Fell-wrought mitigation.
   */
  OCFW_MITIGATION("Orc-craft/Fell-wrought Mitigation"),
  /**
   * Orc-craft and Fell-wrought mitigation percentage.
   */
  OCFW_MITIGATION_PERCENTAGE("Orc-craft/Fell-wrought Mitigation %"),
  /**
   * Tactical mitigation.
   */
  TACTICAL_MITIGATION("Tactical Mitigation", "Tactical mitigation", "TacMit"),
  /**
   * Tactical mitigation percentage.
   */
  TACTICAL_MITIGATION_PERCENTAGE("Tactical Mitigation %"),
  /**
   * Fire mitigation percentage.
   */
  FIRE_MITIGATION_PERCENTAGE("Fire Mitigation %"),
  /**
   * Lightning mitigation percentage.
   */
  LIGHTNING_MITIGATION_PERCENTAGE("Lightning Mitigation %"),
  /**
   * Frost mitigation percentage.
   */
  FROST_MITIGATION_PERCENTAGE("Frost Mitigation %"),
  /**
   * Acid mitigation percentage.
   */
  ACID_MITIGATION_PERCENTAGE("Acid Mitigation %"),
  /**
   * Shadow mitigation percentage.
   */
  SHADOW_MITIGATION_PERCENTAGE("Shadow Mitigation %"),
  /**
   * In-Combat Morale Regeneration.
   */
  OCMR("Non-Combat Morale Regeneration", "NCMR"),
  /**
   * In-Combat Morale Regeneration.
   */
  ICMR("In-Combat Morale Regeneration"),
  /**
   * non-Combat Power Regeneration.
   */
  OCPR("Non-Combat Power Regeneration", "NCPR"),
  /**
   * In-Combat Power Regeneration.
   */
  ICPR("In-Combat Power Regeneration"),
  /**
   * Audacity.
   */
  AUDACITY("Audacity"),
  /**
   * Hope.
   */
  HOPE("Hope"),
  /**
   * Stealth level.
   */
  STEALTH_LEVEL("Stealth Level"),
  /**
   * Ranged Defence.
   */
  RANGED_DEFENCE_PERCENTAGE("Ranged Defence"),
  /**
   * Critical chance of ranged auto-attack (percentage).
   */
  RANGED_AUTO_ATTACKS_CRIT_CHANCE_PERCENTAGE("Ranged Auto-attacks Critical Chance %"),
  /**
   * Devastate magnitude (percentage).
   */
  DEVASTATE_MAGNITUDE_PERCENTAGE("Devastate Magnitude %"),
  /**
   * Tactical critical multiplier (percentage).
   */
  TACTICAL_CRITICAL_MULTIPLIER("Tactical Critical Multiplier %"),
  /**
   * Blade line AOE power cost (percentage).
   */
  BLADE_LINE_AOE_POWER_COST_PERCENTAGE("Blade Line AOE Power Cost %"),
  /**
   * Strike skills power cost (percentage).
   */
  STRIKE_SKILLS_POWER_COST_PERCENTAGE("Strike Skills Power Cost %"),
  /**
   * Blade line AOE skills power cost (percentage).
   */
  BLADE_AOE_SKILLS_POWER_COST_PERCENTAGE("Blade Line AOE Skills Power Cost %"),
  /**
   * Tricks power cost (percentage).
   */
  TRICKS_POWER_COST_PERCENTAGE("Tricks Power Cost %"),
  /**
   * Sign of the wild skills power cost (percentage).
   */
  SIGN_OF_THE_WILD_POWER_COST_PERCENTAGE("Sign of the Wild Skills Power Cost %"),
  /**
   * Ballad and coda damage (percentage).
   */
  BALLAD_AND_CODA_DAMAGE_PERCENTAGE("Ballad and Coda Damage %"),
  /**
   * Attack duration (percentage).
   */
  ATTACK_DURATION_PERCENTAGE("Attack Duration %"),
  /**
   * Jeweller critical chance (percentage).
   */
  JEWELLER_CRIT_CHANCE_PERCENTAGE("Jeweller Critical Chance %"),
  /**
   * Cook critical chance (percentage).
   */
  COOK_CRIT_CHANCE_PERCENTAGE("Cook Critical Chance %"),
  /**
   * Scholar critical chance (percentage).
   */
  SCHOLAR_CRIT_CHANCE_PERCENTAGE("Scholar Critical Chance %"),
  /**
   * Tailor critical chance (percentage).
   */
  TAILOR_CRIT_CHANCE_PERCENTAGE("Tailor Critical Chance %"),
  /**
   * Metalsmith critical chance (percentage).
   */
  METALSMITH_CRIT_CHANCE_PERCENTAGE("Metalsmith Critical Chance %"),
  /**
   * Weaponsmith critical chance (percentage).
   */
  WEAPONSMITH_CRIT_CHANCE_PERCENTAGE("Weaponsmith Critical Chance %"),
  /**
   * Woodworker critical chance (percentage).
   */
  WOODWORKER_CRIT_CHANCE_PERCENTAGE("Woodworker Critical Chance %"),
  /**
   * Prospector mining duration (seconds).
   */
  PROSPECTOR_MINING_DURATION("Prospector Mining Duration (s)"),
  /**
   * Farmer harvesting duration (seconds).
   */
  FARMER_MINING_DURATION("Farmer Harvesting Duration (s)"),
  /**
   * Forester chopping duration (seconds).
   */
  FORESTER_CHOPPING_DURATION("Forester Chopping Duration (s)"),
  /**
   * Perceived threat (percentage).
   */
  PERCEIVED_THREAT("Perceived Threat (%)"),
  /**
   * All skill induction (%).
   */
  ALL_SKILL_INDUCTION("All Skill Induction (%)");

  private String _name;
  private String[] _aliases;

  private static HashMap<String,STAT> _map=new HashMap<String,STAT>();

  static {
    for (STAT stat : STAT.values()) {
      stat.register();
    }
  }

  private STAT(String name, String... aliases)
  {
    _name=name;
    _aliases=aliases;
  }

  private void register() {
    _map.put(_name,this);
    _map.put(name(),this);
    if (_aliases!=null)
    {
      for(String alias : _aliases)
      {
        _map.put(alias,this);
      }
    }
  }

  /**
   * Get a stat by name.
   * @param name Name to use.
   * @return A stat instance or <code>null</code> if not found.
   */
  public static STAT getByName(String name)
  {
    return _map.get(name);
  }

  /**
   * Get the name of this stat.
   * @return a stat name.
   */
  public String getKey()
  {
    return name();
  }

  /**
   * Get the name of this stat.
   * @return a stat name.
   */
  public String getName()
  {
    return _name;
  }

  @Override
  public String toString()
  {
    return _name;
  }
}
