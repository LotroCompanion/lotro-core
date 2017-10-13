package delta.games.lotro.lore.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.lore.items.comparators.EquipmentLocationComparator;

/**
 * Equipment location.
 * @author DAM
 */
public class EquipmentLocation
{
  private static HashMap<String,EquipmentLocation> _instances=new HashMap<String,EquipmentLocation>();
  private static HashMap<String,EquipmentLocation> _keyMap=new HashMap<String,EquipmentLocation>();
  private String _key;
  private String _label;

  /**
   * Head.
   */
  public static final EquipmentLocation HEAD=new EquipmentLocation("HEAD","Head");
  /**
   * Shoulder.
   */
  public static final EquipmentLocation SHOULDER=new EquipmentLocation("SHOULDER","Shoulder");
  /**
   * Back.
   */
  public static final EquipmentLocation BACK=new EquipmentLocation("BACK","Back");
  /**
   * Chest.
   */
  public static final EquipmentLocation CHEST=new EquipmentLocation("CHEST","Chest");
  /**
   * Hand (gloves).
   */
  public static final EquipmentLocation HAND=new EquipmentLocation("HAND","Hand");
  /**
   * Leg.
   */
  public static final EquipmentLocation LEGS=new EquipmentLocation("LEGS","Leg");
  /**
   * Feet.
   */
  public static final EquipmentLocation FEET=new EquipmentLocation("FEET","Feet");

  /**
   * Ear.
   */
  public static final EquipmentLocation EAR=new EquipmentLocation("EAR","Ear");
  /**
   * Neck.
   */
  public static final EquipmentLocation NECK=new EquipmentLocation("NECK","Neck");
  /**
   * Wrist.
   */
  public static final EquipmentLocation WRIST=new EquipmentLocation("WRIST","Wrist");
  /**
   * Finger.
   */
  public static final EquipmentLocation FINGER=new EquipmentLocation("FINGER","Finger");
  /**
   * Pocket.
   */
  public static final EquipmentLocation POCKET=new EquipmentLocation("POCKET","Pocket");

  /**
   * Melee Weapon.
   */
  public static final EquipmentLocation MAIN_HAND=new EquipmentLocation("MAIN_HAND","Main hand", "Melee Weapon");
  /**
   * Shield or off-hand.
   */
  public static final EquipmentLocation OFF_HAND=new EquipmentLocation("OFF_HAND","Off-hand", "Shield");
  /**
   * Ranged Weapon.
   */
  public static final EquipmentLocation RANGED_ITEM=new EquipmentLocation("RANGED_ITEM","Ranged", "Ranged Weapon");
  /**
   * Tool.
   */
  public static final EquipmentLocation TOOL=new EquipmentLocation("TOOL","Tool");
  /**
   * Class slot.
   */
  public static final EquipmentLocation CLASS_SLOT=new EquipmentLocation("CLASS_SLOT","Class Slot");
  /**
   * Bridle.
   */
  public static final EquipmentLocation BRIDLE=new EquipmentLocation("BRIDLE","Bridle");

  private EquipmentLocation(String key, String label, String... aliases)
  {
    _key=key;
    _keyMap.put(_key,this);
    _label=label;
    _instances.put(_label,this);
    for(String alias:aliases)
    {
      _instances.put(alias,this);
    }
  }

  /**
   * Get an internal key for this location.
   * @return A string key.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Get the displayable name of this class.
   * @return A displayable label.
   */
  public String getLabel()
  {
    return _label;
  }

  /**
   * Get an equipment location instance by its name or alias.
   * @param name Name/alias to search.
   * @return An equipment location instance or <code>null</code> if not found.
   */
  public static EquipmentLocation getByName(String name)
  {
    EquipmentLocation ret=_instances.get(name);
    return ret;
  }

  /**
   * Get an equipment location using its key.
   * @param key Key of equipment location to get.
   * @return An equipment location instance or <code>null</code> if not found.
   */
  public static EquipmentLocation getByKey(String key)
  {
    return _keyMap.get(key);
  }

  /**
   * Get all instances of this class.
   * @return an array of all instances of this class.
   */
  public static EquipmentLocation[] getAll()
  {
    List<EquipmentLocation> values=new ArrayList<EquipmentLocation>(_keyMap.values());
    Collections.sort(values,new EquipmentLocationComparator());
    return values.toArray(new EquipmentLocation[values.size()]);
  }

  /**
   * Indicates if the given location is for a jewel.
   * @param location Location to test.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public static boolean isJewelrySlot(EquipmentLocation location)
  {
    if (location==EquipmentLocation.NECK) return true;
    if (location==EquipmentLocation.WRIST) return true;
    if (location==EquipmentLocation.EAR) return true;
    if (location==EquipmentLocation.FINGER) return true;
    if (location==EquipmentLocation.POCKET) return true;
    return false;
  }

  /**
   * Indicates if the given location is for a jewel.
   * @param location Location to test.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public static boolean isArmourSlot(EquipmentLocation location)
  {
    if (location==EquipmentLocation.HEAD) return true;
    if (location==EquipmentLocation.SHOULDER) return true;
    if (location==EquipmentLocation.CHEST) return true;
    if (location==EquipmentLocation.BACK) return true;
    if (location==EquipmentLocation.LEGS) return true;
    if (location==EquipmentLocation.FEET) return true;
    return false;
  }

  @Override
  public String toString()
  {
    return _label;
  }
}
