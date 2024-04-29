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
    if (slot==GearSlots.MAIN_HAND_AURA) return EquipmentLocations.MAIN_HAND_AURA;
    if (slot==GearSlots.OFF_HAND_AURA) return EquipmentLocations.OFF_HAND_AURA;
    if (slot==GearSlots.RANGED_AURA) return EquipmentLocations.RANGED_AURA;
    return null;
  }
}
