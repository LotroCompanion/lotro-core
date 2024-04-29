package delta.games.lotro.character.gear;

import java.util.ArrayList;
import java.util.List;

/**
 * Gear slot designators.
 * @author DAM
 */
public class GearSlots
{
  /**
   * Left ear.
   */
  public static final GearSlot LEFT_EAR=GearSlot.getByKey("LEFT_EAR");
  /**
   * Right ear.
   */
  public static final GearSlot RIGHT_EAR=GearSlot.getByKey("RIGHT_EAR");
  /**
   * Neck.
   */
  public static final GearSlot NECK=GearSlot.getByKey("NECK");
  /**
   * Pocket.
   */
  public static final GearSlot POCKET=GearSlot.getByKey("POCKET"); 
  /**
   * Left wrist.
   */
  public static final GearSlot LEFT_WRIST=GearSlot.getByKey("LEFT_WRIST");
  /**
   * Right wrist.
   */
  public static final GearSlot RIGHT_WRIST=GearSlot.getByKey("RIGHT_WRIST");
  /**
   * Left finger.
   */
  public static final GearSlot LEFT_FINGER=GearSlot.getByKey("LEFT_FINGER");
  /**
   * Right finger.
   */
  public static final GearSlot RIGHT_FINGER=GearSlot.getByKey("RIGHT_FINGER");
  /**
   * Head.
   */
  public static final GearSlot HEAD=GearSlot.getByKey("HEAD");
  /**
   * Shoulder.
   */
  public static final GearSlot SHOULDER=GearSlot.getByKey("SHOULDER");
  /**
   * Breast.
   */
  public static final GearSlot BREAST=GearSlot.getByKey("BREAST");
  /**
   * Back.
   */
  public static final GearSlot BACK=GearSlot.getByKey("BACK");
  /**
   * Hands.
   */
  public static final GearSlot HANDS=GearSlot.getByKey("HANDS");
  /**
   * Legs.
   */
  public static final GearSlot LEGS=GearSlot.getByKey("LEGS");
  /**
   * Feet.
   */
  public static final GearSlot FEET=GearSlot.getByKey("FEET");
  /**
   * Main melee object.
   */
  public static final GearSlot MAIN_MELEE=GearSlot.getByKey("MAIN_MELEE");
  /**
   * Other melee object.
   */
  public static final GearSlot OTHER_MELEE=GearSlot.getByKey("OTHER_MELEE");
  /**
   * Ranged object.
   */
  public static final GearSlot RANGED=GearSlot.getByKey("RANGED");
  /**
   * Tool object.
   */
  public static final GearSlot TOOL=GearSlot.getByKey("TOOL");
  /**
   * Class object item.
   */
  public static final GearSlot CLASS_ITEM=GearSlot.getByKey("CLASS_ITEM");
  /**
   * Bridle.
   */
  public static final GearSlot BRIDLE=GearSlot.getByKey("BRIDLE");
  /**
   * Main hand aura.
   */
  public static final GearSlot MAIN_HAND_AURA=GearSlot.getByKey("MAIN_HAND_AURA");
  /**
   * Off-hand aura.
   */
  public static final GearSlot OFF_HAND_AURA=GearSlot.getByKey("OFF_HAND_AURA");
  /**
   * Ranged aura.
   */
  public static final GearSlot RANGED_AURA=GearSlot.getByKey("RANGED_AURA");

  /**
   * Get the slots to use for stats computations.
   * @return A list of slots.
   */
  public static List<GearSlot> getSlotsForStatsComputation()
  {
    List<GearSlot> ret=new ArrayList<GearSlot>();
    ret.add(LEFT_EAR);
    ret.add(RIGHT_EAR);
    ret.add(NECK);
    ret.add(POCKET); 
    ret.add(LEFT_WRIST);
    ret.add(RIGHT_WRIST);
    ret.add(LEFT_FINGER);
    ret.add(RIGHT_FINGER);
    ret.add(HEAD);
    ret.add(SHOULDER);
    ret.add(BREAST);
    ret.add(BACK);
    ret.add(HANDS);
    ret.add(LEGS);
    ret.add(FEET);
    ret.add(MAIN_MELEE);
    ret.add(OTHER_MELEE);
    ret.add(RANGED);
    ret.add(TOOL);
    ret.add(CLASS_ITEM);
    return ret;
  }
}
