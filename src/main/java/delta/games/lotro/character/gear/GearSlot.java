package delta.games.lotro.character.gear;

import delta.games.lotro.lore.items.EquipmentLocation;

/**
 * Gear slot designators.
 * @author DAM
 */
public enum GearSlot
{
  /**
   * Left ear.
   */
  LEFT_EAR(14,"Left ear",EquipmentLocation.EAR),
  /**
   * Right ear.
   */
  RIGHT_EAR(15,"Right ear",EquipmentLocation.EAR),
  /**
   * Neck.
   */
  NECK(11,"Neck",EquipmentLocation.NECK),
  /**
   * Pocket.
   */
  POCKET(16,"Pocket",EquipmentLocation.POCKET),
  /**
   * Left wrist.
   */
  LEFT_WRIST(9,"Left wrist",EquipmentLocation.WRIST),
  /**
   * Right wrist.
   */
  RIGHT_WRIST(10,"Right wrist",EquipmentLocation.WRIST),
  /**
   * Left finger.
   */
  LEFT_FINGER(12,"Left finger",EquipmentLocation.FINGER),
  /**
   * Right finger.
   */
  RIGHT_FINGER(13,"Right finger",EquipmentLocation.FINGER),
  /**
   * Head.
   */
  HEAD(2,"Head",EquipmentLocation.HEAD),
  /**
   * Shoulder.
   */
  SHOULDER(7,"Shoulder",EquipmentLocation.SHOULDER),
  /**
   * Breast.
   */
  BREAST(3,"Breast",EquipmentLocation.CHEST),
  /**
   * Back.
   */
  BACK(8,"Back",EquipmentLocation.BACK),
  /**
   * Hands.
   */
  HANDS(5,"Hands",EquipmentLocation.HAND),
  /**
   * Legs.
   */
  LEGS(4,"Legs",EquipmentLocation.LEGS),
  /**
   * Feet.
   */
  FEET(6,"Feet",EquipmentLocation.FEET),
  /**
   * Main melee object.
   */
  MAIN_MELEE(17,"Main hand",EquipmentLocation.MAIN_HAND),
  /**
   * Other melee object.
   */
  OTHER_MELEE(18,"Off-hand",EquipmentLocation.OFF_HAND),
  /**
   * Ranged object.
   */
  RANGED(19,"Ranged",EquipmentLocation.RANGED_ITEM),
  /**
   * Tool object.
   */
  TOOL(20,"Tool",EquipmentLocation.TOOL),
  /**
   * Class object item.
   */
  CLASS_ITEM(21,"Class slot",EquipmentLocation.CLASS_SLOT),
  /**
   * Bridle.
   */
  //BRIDLE(22,"Bridle",EquipmentLocation.BRIDLE),
  /**
   * Main hand aura.
   */
  MAIN_HAND_AURA(23,"Main Hand Aura",EquipmentLocation.MAIN_HAND_AURA),
  /**
   * Off-hand aura.
   */
  OFF_HAND_AURA(24,"Off-Hand Aura",EquipmentLocation.OFF_HAND_AURA),
  /**
   * Ranged aura.
   */
  RANGED_AURA(25,"Ranged Aura",EquipmentLocation.RANGED_AURA);

  private int _position;
  private String _label;
  private EquipmentLocation _location;

  private GearSlot(int position, String label, EquipmentLocation location)
  {
    _position=position;
    _label=label;
    _location=location;
  }

  /**
   * Get the integer position associated with this slot.
   * @return an integer value.
   */
  public int getPosition()
  {
    return _position;
  }

  /**
   * Get a displayable label for this slot.
   * @return a label.
   */
  public String getLabel()
  {
    return _label;
  }

  /**
   * Get the associated location.
   * @return the associated location.
   */
  public EquipmentLocation getLocation()
  {
    return _location;
  }
}
