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
   * Tactical mitigation.
   */
  TACTICAL_MITIGATION("Tactical Mitigation", "Tactical mitigation", "TacMit"),
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
  ICPR("In-Combat Power Regeneration");

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
