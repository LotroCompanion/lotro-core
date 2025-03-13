package delta.games.lotro.character.gear;

import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.EquipmentLocations;

/**
 * Utility methods related to gear slots.
 * @author DAM
 */
public class GearSlotUtils
{
  /**
   * Get the equipment location associated to a gear slot.
   * @param slot Input slot.
   * @return the result equipment location or <code>null</code> if not found.
   */
  public static EquipmentLocation getEquipmentSlot(GearSlot slot)
  {
    if (slot==GearSlots.LEFT_EAR) return EquipmentLocations.EAR;
    if (slot==GearSlots.RIGHT_EAR) return EquipmentLocations.EAR;
    if (slot==GearSlots.NECK) return EquipmentLocations.NECK;
    if (slot==GearSlots.POCKET) return EquipmentLocations.POCKET;
    if (slot==GearSlots.LEFT_WRIST) return EquipmentLocations.WRIST;
    if (slot==GearSlots.RIGHT_WRIST) return EquipmentLocations.WRIST;
    if (slot==GearSlots.LEFT_FINGER) return EquipmentLocations.FINGER;
    if (slot==GearSlots.RIGHT_FINGER) return EquipmentLocations.FINGER;
    if (slot==GearSlots.HEAD) return EquipmentLocations.HEAD;
    if (slot==GearSlots.SHOULDER) return EquipmentLocations.SHOULDER;
    if (slot==GearSlots.BREAST) return EquipmentLocations.CHEST;
    if (slot==GearSlots.BACK) return EquipmentLocations.BACK;
    if (slot==GearSlots.HANDS) return EquipmentLocations.HAND;
    if (slot==GearSlots.LEGS) return EquipmentLocations.LEGS;
    if (slot==GearSlots.FEET) return EquipmentLocations.FEET;
    if (slot==GearSlots.MAIN_MELEE) return EquipmentLocations.MAIN_HAND;
    if (slot==GearSlots.OTHER_MELEE) return EquipmentLocations.OFF_HAND;
    if (slot==GearSlots.RANGED) return EquipmentLocations.RANGED_ITEM;
    if (slot==GearSlots.TOOL) return EquipmentLocations.TOOL;
    if (slot==GearSlots.CLASS_ITEM) return EquipmentLocations.CLASS_SLOT;
    if (slot==GearSlots.BRIDLE) return EquipmentLocations.BRIDLE;
    if (slot==GearSlots.MAIN_HAND_AURA) return EquipmentLocations.AURA;
    if (slot==GearSlots.OFF_HAND_AURA) return EquipmentLocations.AURA;
    if (slot==GearSlots.RANGED_AURA) return EquipmentLocations.AURA;
    return null;
  }

  private static final GearSlot[] buildSingle(GearSlot slot)
  {
    return new GearSlot[] {slot};
  }

  private static final GearSlot[] buildCouple(GearSlot slot1, GearSlot slot2)
  {
    return new GearSlot[] {slot1,slot2};
  }

  private static final GearSlot[] NONE=new GearSlot[] {};
  private static final GearSlot[] HEAD=buildSingle(GearSlots.HEAD);
  private static final GearSlot[] SHOULDER=buildSingle(GearSlots.SHOULDER);
  private static final GearSlot[] BACK=buildSingle(GearSlots.BACK);
  private static final GearSlot[] CHEST=buildSingle(GearSlots.BREAST);
  private static final GearSlot[] HAND=buildSingle(GearSlots.HANDS);
  private static final GearSlot[] LEGS=buildSingle(GearSlots.LEGS);
  private static final GearSlot[] FEET=buildSingle(GearSlots.FEET);
  private static final GearSlot[] EAR=buildCouple(GearSlots.LEFT_EAR,GearSlots.RIGHT_EAR);
  private static final GearSlot[] LEFT_EAR=buildSingle(GearSlots.LEFT_EAR);
  private static final GearSlot[] RIGHT_EAR=buildSingle(GearSlots.RIGHT_EAR);
  private static final GearSlot[] NECK=buildSingle(GearSlots.NECK);
  private static final GearSlot[] WRIST=buildCouple(GearSlots.LEFT_WRIST,GearSlots.RIGHT_WRIST);
  private static final GearSlot[] LEFT_WRIST=buildSingle(GearSlots.LEFT_WRIST);
  private static final GearSlot[] RIGHT_WRIST=buildSingle(GearSlots.RIGHT_WRIST);
  private static final GearSlot[] FINGER=buildCouple(GearSlots.LEFT_FINGER,GearSlots.RIGHT_FINGER);
  private static final GearSlot[] LEFT_FINGER=buildSingle(GearSlots.LEFT_FINGER);
  private static final GearSlot[] RIGHT_FINGER=buildSingle(GearSlots.RIGHT_FINGER);
  private static final GearSlot[] POCKET=buildSingle(GearSlots.POCKET);
  private static final GearSlot[] MAIN_HAND=buildSingle(GearSlots.MAIN_MELEE);
  private static final GearSlot[] OFF_HAND=buildSingle(GearSlots.OTHER_MELEE);
  private static final GearSlot[] EITHER_HAND=buildCouple(GearSlots.MAIN_MELEE,GearSlots.OTHER_MELEE);
  private static final GearSlot[] RANGED_ITEM=buildSingle(GearSlots.RANGED);
  private static final GearSlot[] TOOL=buildSingle(GearSlots.TOOL);
  private static final GearSlot[] CLASS_SLOT=buildSingle(GearSlots.CLASS_ITEM);
  private static final GearSlot[] BRIDLE=buildSingle(GearSlots.BRIDLE);
  private static final GearSlot[] AURA=new GearSlot[] {GearSlots.MAIN_HAND_AURA,GearSlots.OFF_HAND_AURA,GearSlots.RANGED_AURA};

  /**
   * Get the gear slots associated to a single equipment location.
   * @param location Input location.
   * @return An array of gear slots (may be empty but never <code>null</code>).
   */
  public static GearSlot[] getSlots(EquipmentLocation location)
  {
    if (location==EquipmentLocations.NONE) return NONE;
    if (location==EquipmentLocations.HEAD) return HEAD;
    if (location==EquipmentLocations.SHOULDER) return SHOULDER;
    if (location==EquipmentLocations.BACK) return BACK;
    if (location==EquipmentLocations.CHEST) return CHEST;
    if (location==EquipmentLocations.HAND) return HAND;
    if (location==EquipmentLocations.LEGS) return LEGS;
    if (location==EquipmentLocations.FEET) return FEET;
    if (location==EquipmentLocations.EAR) return EAR;
    if (location==EquipmentLocations.LEFT_EAR) return LEFT_EAR;
    if (location==EquipmentLocations.RIGHT_EAR) return RIGHT_EAR;
    if (location==EquipmentLocations.NECK) return NECK;
    if (location==EquipmentLocations.WRIST) return WRIST;
    if (location==EquipmentLocations.LEFT_WRIST) return LEFT_WRIST;
    if (location==EquipmentLocations.RIGHT_WRIST) return RIGHT_WRIST;
    if (location==EquipmentLocations.FINGER) return FINGER;
    if (location==EquipmentLocations.LEFT_FINGER) return LEFT_FINGER;
    if (location==EquipmentLocations.RIGHT_FINGER) return RIGHT_FINGER;
    if (location==EquipmentLocations.POCKET) return POCKET;
    if (location==EquipmentLocations.MAIN_HAND) return MAIN_HAND;
    if (location==EquipmentLocations.OFF_HAND) return OFF_HAND;
    if (location==EquipmentLocations.EITHER_HAND) return EITHER_HAND;
    if (location==EquipmentLocations.RANGED_ITEM) return RANGED_ITEM;
    if (location==EquipmentLocations.TOOL) return TOOL;
    if (location==EquipmentLocations.CLASS_SLOT) return CLASS_SLOT;
    if (location==EquipmentLocations.BRIDLE) return BRIDLE;
    if (location==EquipmentLocations.AURA) return AURA;
    return NONE;
  }
}
