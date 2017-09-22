package delta.games.lotro.lore.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.lore.items.comparators.ArmourTypeComparator;

/**
 * Armour type.
 * @author DAM
 */
public class ArmourType
{
  private static HashMap<String,ArmourType> _map=new HashMap<String,ArmourType>();
  private static HashMap<String,ArmourType> _keyMap=new HashMap<String,ArmourType>();

  /**
   * Heavy.
   */
  public static final ArmourType HEAVY=new ArmourType(2, "HEAVY","Heavy Armour",false,"Heavy");
  /**
   * Medium.
   */
  public static final ArmourType MEDIUM=new ArmourType(1, "MEDIUM","Medium Armour",false,"Medium");
  /**
   * Light.
   */
  public static final ArmourType LIGHT=new ArmourType(0, "LIGHT","Light Armour",false,"Light","Cloak");
  /**
   * Warden's Shield.
   */
  public static final ArmourType WARDEN_SHIELD=new ArmourType(5, "WARDEN_SHIELD","Warden's Shield",true);
  /**
   * Heavy Shield.
   */
  public static final ArmourType HEAVY_SHIELD=new ArmourType(4, "HEAVY_SHIELD","Heavy Shield",true);
  /**
   * Shield.
   */
  public static final ArmourType SHIELD=new ArmourType(3, "SHIELD","Shield",true);

  /**
   * Ordered non-shield armour types.
   */
  public static final ArmourType[] ARMOUR_TYPES = {
      LIGHT, MEDIUM, HEAVY,
  };

  /**
   * Ordered shield armour types.
   */
  public static final ArmourType[] SHIELD_ARMOUR_TYPES = {
      SHIELD, HEAVY_SHIELD, WARDEN_SHIELD
  };

  private int _code;
  private String _key;
  private String _name;
  private boolean _isShield;

  private ArmourType(int code, String key, String name, boolean isShield, String... aliases)
  {
    _code=code;
    _key=key;
    _keyMap.put(key,this);
    _name=name;
    _isShield=isShield;
    _map.put(name,this);
    for(String alias : aliases) _map.put(alias,this);
  }

  /**
   * Get sort code.
   * @return a integer value.
   */
  public int getCode()
  {
    return _code;
  }

  /**
   * Get the armour type key.
   * @return A key.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Get the armour type name.
   * @return A name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Indicates if this is a shield armour type or not.
   * @return <code>true</code> for a shield type, <code>false</code> otherwise.
   */
  public boolean isShield() {
    return _isShield;
  }

  @Override
  public String toString()
  {
    return _name;
  }

  /**
   * Get an armour type using its name.
   * @param name Name of armour type.
   * @return An armour type instance or <code>null</code> if not found.
   */
  public static ArmourType getArmourTypeByName(String name)
  {
    return _map.get(name);
  }

  /**
   * Get an armour type using its key.
   * @param key Key of armour type.
   * @return An armour type instance or <code>null</code> if not found.
   */
  public static ArmourType getArmourTypeByKey(String key)
  {
    return _keyMap.get(key);
  }

  /**
   * Get all instances of this class.
   * @return an array of all instances of this class.
   */
  public static List<ArmourType> getAll()
  {
    List<ArmourType> ret=new ArrayList<ArmourType>(_keyMap.values());
    Collections.sort(ret,new ArmourTypeComparator());
    return ret;
  }
}
