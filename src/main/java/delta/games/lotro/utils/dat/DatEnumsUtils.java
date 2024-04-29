package delta.games.lotro.utils.dat;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.gear.GearSlot;
import delta.games.lotro.character.gear.GearSlots;

/**
 * Misc enum utils.
 * @author DAM
 */
public class DatEnumsUtils
{
  /**
   * Indicates if the given slot code is for an equipped item.
   * @param slotCode Code to use.
   * @return <code>true</code> if equipped, <code>false</code> otherwise.
   */
  public static boolean isEquipped(int slotCode)
  {
    return ((slotCode&1L<<29)!=0);
  }

  /**
   * Indicates if the given slot code means an item in the bags.
   * @param slotCode Code to use.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public static boolean isInBags(int slotCode)
  {
    return ((slotCode&1L<<30)!=0);
  }

  /**
   * Indicates if the given slot code means an item in the overflow.
   * @param slotCode Code to use.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public static boolean isInOverflow(int slotCode)
  {
    return ((slotCode&1L<<31)!=0);
  }

  /**
   * Get an equipment slot from a DAT enum code.
   * @param slotCode Input code.
   * @return An equipment slot or <code>null</code> if not supported.
   */
  public static GearSlot getEquipmentSlot(long slotCode)
  {
    // See Enum: ContainerSlot, (id=587202798)
    if ((slotCode&1L<<1)!=0) return GearSlots.HEAD;
    if ((slotCode&1L<<2)!=0) return GearSlots.BREAST;
    if ((slotCode&1L<<3)!=0) return GearSlots.LEGS;
    if ((slotCode&1L<<4)!=0) return GearSlots.HANDS;
    if ((slotCode&1L<<5)!=0) return GearSlots.FEET;
    if ((slotCode&1L<<6)!=0) return GearSlots.SHOULDER;
    if ((slotCode&1L<<7)!=0) return GearSlots.BACK;
    if ((slotCode&1L<<8)!=0) return GearSlots.LEFT_WRIST;
    if ((slotCode&1L<<9)!=0) return GearSlots.RIGHT_WRIST;
    if ((slotCode&1L<<10)!=0) return GearSlots.NECK;
    if ((slotCode&1L<<11)!=0) return GearSlots.LEFT_FINGER;
    if ((slotCode&1L<<12)!=0) return GearSlots.RIGHT_FINGER;
    if ((slotCode&1L<<13)!=0) return GearSlots.LEFT_EAR;
    if ((slotCode&1L<<14)!=0) return GearSlots.RIGHT_EAR;
    if ((slotCode&1L<<15)!=0) return GearSlots.POCKET;
    if ((slotCode&1L<<16)!=0) return GearSlots.MAIN_MELEE;
    if ((slotCode&1L<<17)!=0) return GearSlots.OTHER_MELEE;
    if ((slotCode&1L<<18)!=0) return GearSlots.RANGED;
    if ((slotCode&1L<<19)!=0) return GearSlots.TOOL;
    if ((slotCode&1L<<20)!=0) return GearSlots.CLASS_ITEM;
    if ((slotCode&1L<<21)!=0) return GearSlots.BRIDLE;
    if ((slotCode&1L<<22)!=0) return GearSlots.MAIN_HAND_AURA;
    if ((slotCode&1L<<23)!=0) return GearSlots.OFF_HAND_AURA;
    if ((slotCode&1L<<24)!=0) return GearSlots.RANGED_AURA;
    /*
    26 => Last
    29 => Mail
    30 => Equipment
    31 => Backpack
    32 => Overflow
   */
    return null;
  }

  /**
   * Get the equipment slots from a slot bitfield.
   * @param slotCode Input code.
   * @return A opossibly empty but never <code>null</code> list of equipment slots.
   */
  public static List<GearSlot> getEquipmentSlots(long slotCode)
  {
    List<GearSlot> ret=new ArrayList<GearSlot>();
    // See Enum: ContainerSlot, (id=587202798)
    if ((slotCode&1L<<1)!=0) ret.add(GearSlots.HEAD);
    if ((slotCode&1L<<2)!=0) ret.add(GearSlots.BREAST);
    if ((slotCode&1L<<3)!=0) ret.add(GearSlots.LEGS);
    if ((slotCode&1L<<4)!=0) ret.add(GearSlots.HANDS);
    if ((slotCode&1L<<5)!=0) ret.add(GearSlots.FEET);
    if ((slotCode&1L<<6)!=0) ret.add(GearSlots.SHOULDER);
    if ((slotCode&1L<<7)!=0) ret.add(GearSlots.BACK);
    if ((slotCode&1L<<8)!=0) ret.add(GearSlots.LEFT_WRIST);
    if ((slotCode&1L<<9)!=0) ret.add(GearSlots.RIGHT_WRIST);
    if ((slotCode&1L<<10)!=0) ret.add(GearSlots.NECK);
    if ((slotCode&1L<<11)!=0) ret.add(GearSlots.LEFT_FINGER);
    if ((slotCode&1L<<12)!=0) ret.add(GearSlots.RIGHT_FINGER);
    if ((slotCode&1L<<13)!=0) ret.add(GearSlots.LEFT_EAR);
    if ((slotCode&1L<<14)!=0) ret.add(GearSlots.RIGHT_EAR);
    if ((slotCode&1L<<15)!=0) ret.add(GearSlots.POCKET);
    if ((slotCode&1L<<16)!=0) ret.add(GearSlots.MAIN_MELEE);
    if ((slotCode&1L<<17)!=0) ret.add(GearSlots.OTHER_MELEE);
    if ((slotCode&1L<<18)!=0) ret.add(GearSlots.RANGED);
    if ((slotCode&1L<<19)!=0) ret.add(GearSlots.TOOL);
    if ((slotCode&1L<<20)!=0) ret.add(GearSlots.CLASS_ITEM);
    if ((slotCode&1L<<21)!=0) ret.add(GearSlots.BRIDLE);
    if ((slotCode&1L<<22)!=0) ret.add(GearSlots.MAIN_HAND_AURA);
    if ((slotCode&1L<<23)!=0) ret.add(GearSlots.OFF_HAND_AURA);
    if ((slotCode&1L<<24)!=0) ret.add(GearSlots.RANGED_AURA);
    /*
    26 => Last
    29 => Mail
    30 => Equipment
    31 => Backpack
    32 => Overflow
   */
    return ret;
  }
}
