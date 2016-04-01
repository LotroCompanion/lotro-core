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
   * Finesse.
   */
  FINESSE("Finesse"),
  /**
   * Physical Mastery.
   */
  PHYSICAL_MASTERY("Physical Mastery"),
  /**
   * Tactical Mastery.
   */
  TACTICAL_MASTERY("Tactical Mastery"),
  // Defence
  /**
   * Resistance.
   */
  RESISTANCE("Resistance", "Resist"),
  /**
   * Critical Defence.
   */
  CRITICAL_DEFENCE("Critical Defence", "CRITICAL_AVOID", "Critical avoidance"),
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
   * Parry.
   */
  PARRY("Parry", "Parry Rating"),
  /**
   * Evade.
   */
  EVADE("Evade", "Evade Rating"),
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
   * Tactical mitigation.
   */
  TACTICAL_MITIGATION("Tactical Mitigation", "Tactical mitigation", "TacMit"),
  /**
   * Tactical mitigation percentage.
   */
  TACTICAL_MITIGATION_PERCENTAGE("Tactical Mitigation %"),
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
   * Ranged Defence.
   */
  RANGED_DEFENCE_PERCENTAGE("Ranged Defence"),
  /**
   * Parry (percentage).
   */
  PARRY_PERCENTAGE("Parry %"),
  /**
   * Critical chance of ranged auto-attack (percentage).
   */
  RANGED_AUTO_ATTACKS_CRIT_CHANCE_PERCENTAGE("Ranged Auto-attacks Critical Chance %"),
  /**
   * Devastate magnitude (percentage).
   */
  DEVASTATE_MAGNITUDE_PERCENTAGE("Devastate Magnitude %"),
  /**
   * Blade line AOE power cost (percentage).
   */
  BLADE_LINE_AOE_POWER_COST_PERCENTAGE("Blade Line AOE Power Cost %"),
  /**
   * Strike skills power cost (percentage).
   */
  STRIKE_SKILLS_POWER_COST_PERCENTAGE("Strike Skills Power Cost %"),
  /**
   * Attack duration (percentage).
   */
  ATTACK_DURATION_PERCENTAGE("Attack Duration %"),
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
  PROSPECTOR_MINING_DURATION("Prospector Mining Duration (s)");

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
