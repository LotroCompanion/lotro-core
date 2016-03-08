package delta.games.lotro.character;

import java.util.HashMap;

import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Represents a LOTRO character stat value.
 * @author DAM
 */
public class CharacterStat
{
  private static HashMap<String,STAT> _map=new HashMap<String,STAT>();
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
    RESISTANCE("Resistance"),
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
    PHYSICAL_MITIGATION("Physical Mitigation", "Physical mitigation"),
    /**
     * Tactical mitigation.
     */
    TACTICAL_MITIGATION("Tactical Mitigation", "Tactical mitigation"),
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

    private STAT(String name, String... aliases)
    {
      _name=name;
      _map.put(name,this);
      _map.put(name(),this);
      if (aliases!=null)
      {
        for(String alias : aliases)
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

  private STAT _stat;
  private FixedDecimalsInteger _value;

  /**
   * Constructor.
   * @param stat Associated stat.
   */
  public CharacterStat(STAT stat)
  {
    _stat=stat;
  }

  /**
   * Constructor.
   * @param stat Associated stat.
   * @param value Value to set.
   */
  public CharacterStat(STAT stat, Integer value)
  {
    _stat=stat;
    if (value!=null)
    {
      _value=new FixedDecimalsInteger(value.intValue());
    }
  }

  /**
   * Constructor.
   * @param stat Associated stat.
   * @param value Value to set.
   */
  public CharacterStat(STAT stat, FixedDecimalsInteger value)
  {
    _stat=stat;
    _value=value;
  }

  /**
   * Get the associated stat.
   * @return the associated stat.
   */
  public STAT getStat()
  {
    return _stat;
  }
  
  /**
   * Get the value of this stat.
   * @return A stat value or <code>null</code> if undefined.
   */
  public FixedDecimalsInteger getValue()
  {
    return _value;
  }

  /**
   * Set the value of this stat.
   * @param value Value to set (<code>null</code> means undefined).
   */
  public void setValue(FixedDecimalsInteger value)
  {
    _value=value;
  }

  /**
   * Set the value of this stat.
   * @param value Value to set (<code>null</code> means undefined).
   */
  public void setValue(Integer value)
  {
    if (value!=null)
    {
      _value=new FixedDecimalsInteger(value.intValue());
    }
    else
    {
      _value=null;
    }
  }

  @Override
  public String toString()
  {
    return _stat+": "+((_value!=null)?_value:"N/A");
  }
}
