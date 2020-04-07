package delta.games.lotro.utils.dat;

import delta.games.lotro.character.CharacterEquipment.EQUIMENT_SLOT;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;

/**
 * Misc enum utils.
 * @author DAM
 */
public class DatEnumsUtils
{
  private static final String SLOT_PROPERTY_NAME_SEED="Inventory_SlotCache_Eq_";

  /**
   * Get a character class from a DAT enum code.
   * @param id Input code.
   * @return A character class or <code>null</code> if not supported.
   */
  public static CharacterClass getCharacterClassFromId(int id)
  {
    if (id==214) return CharacterClass.BEORNING;
    if (id==40) return CharacterClass.BURGLAR;
    if (id==24) return CharacterClass.CAPTAIN;
    if (id==172) return CharacterClass.CHAMPION;
    if (id==23) return CharacterClass.GUARDIAN;
    if (id==162) return CharacterClass.HUNTER;
    if (id==185) return CharacterClass.LORE_MASTER;
    if (id==31) return CharacterClass.MINSTREL;
    if (id==193) return CharacterClass.RUNE_KEEPER;
    if (id==194) return CharacterClass.WARDEN;
    // Monster Play
    if (id==71) return CharacterClass.REAVER;
    if (id==128) return CharacterClass.DEFILER;
    if (id==127) return CharacterClass.WEAVER;
    if (id==179) return CharacterClass.BLACKARROW;
    if (id==52) return CharacterClass.WARLEADER;
    if (id==126) return CharacterClass.STALKER;
    return null;
  }

  /**
   * Get a race from a DAT enum code.
   * @param raceId Input code.
   * @return A race or <code>null</code> if not supported.
   */
  public static Race getRaceFromRaceId(int raceId)
  {
    if (raceId==23) return Race.MAN;
    if (raceId==65) return Race.ELF;
    if (raceId==73) return Race.DWARF;
    if (raceId==81) return Race.HOBBIT;
    if (raceId==114) return Race.BEORNING;
    if (raceId==117) return Race.HIGH_ELF;
    if (raceId==120) return Race.STOUT_AXE_DWARF;
    return null;
  }

  /**
   * Get a nationality from a nationality code.
   * @param nationalityId Nationality code.
   * @return A nationality or <code>null</code> if not found.
   */
  public static String getNationalityFromNationalityId(int nationalityId)
  {
    // Men
    if (nationalityId==17) return "Bree-land";
    if (nationalityId==16) return "Dale";
    if (nationalityId==6) return "Gondor";
    if (nationalityId==5) return "Rohan";

    // Elves
    if (nationalityId==15) return "Lindon";
    if (nationalityId==8) return "Lórien";
    if (nationalityId==11) return "Mirkwood";
    if (nationalityId==7) return "Rivendell";
    if (nationalityId==2) return "Edhellond";

    // Dwarves
    if (nationalityId==4) return "Blue Mountains";
    if (nationalityId==3) return "Iron Hills";
    if (nationalityId==12) return "Lonely Mountain";
    if (nationalityId==10) return "Grey Mountains";
    if (nationalityId==9) return "White Mountains";

    // Hobbit
    if (nationalityId==18) return "Fallohides";
    if (nationalityId==14) return "Harfoot";
    if (nationalityId==13) return "Stoors";

    // Beorning
    if (nationalityId==19) return "Vales of Anduin";

    // High Elf
    if (nationalityId==21) return "Beleriand";
    if (nationalityId==22) return "Imladris";
    if (nationalityId==26) return "Nargothrond";
    if (nationalityId==23) return "Gondolin";
    if (nationalityId==24) return "Ossiriand";

    // Stout-axe dwarf
    if (nationalityId==27) return "Mordor Mountains";
    return null;
  }

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
   * Get an equipment slot from a DAT enum code.
   * @param slotCode Input code.
   * @return An equipment slot or <code>null</code> if not supported.
   */
  public static EQUIMENT_SLOT getEquipmentSlot(long slotCode)
  {
    // See Enum: ContainerSlot, (id=587202798)
    if ((slotCode&1L<<1)!=0) return EQUIMENT_SLOT.HEAD;
    if ((slotCode&1L<<2)!=0) return EQUIMENT_SLOT.BREAST;
    if ((slotCode&1L<<3)!=0) return EQUIMENT_SLOT.LEGS;
    if ((slotCode&1L<<4)!=0) return EQUIMENT_SLOT.HANDS;
    if ((slotCode&1L<<5)!=0) return EQUIMENT_SLOT.FEET;
    if ((slotCode&1L<<6)!=0) return EQUIMENT_SLOT.SHOULDER;
    if ((slotCode&1L<<7)!=0) return EQUIMENT_SLOT.BACK;
    if ((slotCode&1L<<8)!=0) return EQUIMENT_SLOT.LEFT_WRIST;
    if ((slotCode&1L<<9)!=0) return EQUIMENT_SLOT.RIGHT_WRIST;
    if ((slotCode&1L<<10)!=0) return EQUIMENT_SLOT.NECK;
    if ((slotCode&1L<<11)!=0) return EQUIMENT_SLOT.LEFT_FINGER;
    if ((slotCode&1L<<12)!=0) return EQUIMENT_SLOT.RIGHT_FINGER;
    if ((slotCode&1L<<13)!=0) return EQUIMENT_SLOT.LEFT_EAR;
    if ((slotCode&1L<<14)!=0) return EQUIMENT_SLOT.RIGHT_EAR;
    if ((slotCode&1L<<15)!=0) return EQUIMENT_SLOT.POCKET;
    if ((slotCode&1L<<16)!=0) return EQUIMENT_SLOT.MAIN_MELEE;
    if ((slotCode&1L<<17)!=0) return EQUIMENT_SLOT.OTHER_MELEE;
    if ((slotCode&1L<<18)!=0) return EQUIMENT_SLOT.RANGED;
    if ((slotCode&1L<<19)!=0) return EQUIMENT_SLOT.TOOL;
    if ((slotCode&1L<<20)!=0) return EQUIMENT_SLOT.CLASS_ITEM;
    //if ((slotCode&1L<<21)!=0) return EQUIMENT_SLOT.BRIDLE;
    if ((slotCode&1L<<22)!=0) return EQUIMENT_SLOT.MAIN_HAND_AURA;
    if ((slotCode&1L<<23)!=0) return EQUIMENT_SLOT.OFF_HAND_AURA;
    if ((slotCode&1L<<24)!=0) return EQUIMENT_SLOT.RANGED_AURA;
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
   * Get the slot associated to the given property name.
   * @param propertyName A property name.
   * @return A slot, if any.
   */
  public static EQUIMENT_SLOT getSlotFromPropertyName(String propertyName)
  {
    if (propertyName.startsWith(SLOT_PROPERTY_NAME_SEED))
    {
      String slotKey=propertyName.substring(SLOT_PROPERTY_NAME_SEED.length());
      if ("Earring1".equals(slotKey)) return EQUIMENT_SLOT.LEFT_EAR;
      else if ("Earring2".equals(slotKey)) return EQUIMENT_SLOT.RIGHT_EAR;
      else if ("Bracelet1".equals(slotKey)) return EQUIMENT_SLOT.LEFT_WRIST;
      else if ("Bracelet2".equals(slotKey)) return EQUIMENT_SLOT.RIGHT_WRIST;
      else if ("Ring1".equals(slotKey)) return EQUIMENT_SLOT.LEFT_FINGER;
      else if ("Ring2".equals(slotKey)) return EQUIMENT_SLOT.RIGHT_FINGER;
      else if ("Pocket1".equals(slotKey)) return EQUIMENT_SLOT.POCKET;
      else if ("Necklace".equals(slotKey)) return EQUIMENT_SLOT.NECK;
      else if ("Head".equals(slotKey)) return EQUIMENT_SLOT.HEAD;
      else if ("Shoulder".equals(slotKey)) return EQUIMENT_SLOT.SHOULDER;
      else if ("Chest".equals(slotKey)) return EQUIMENT_SLOT.BREAST;
      else if ("Back".equals(slotKey)) return EQUIMENT_SLOT.BACK;
      else if ("Gloves".equals(slotKey)) return EQUIMENT_SLOT.HANDS;
      else if ("Legs".equals(slotKey)) return EQUIMENT_SLOT.LEGS;
      else if ("Boots".equals(slotKey)) return EQUIMENT_SLOT.FEET;
      else if ("Weapon_Primary".equals(slotKey)) return EQUIMENT_SLOT.MAIN_MELEE;
      else if ("Weapon_Secondary".equals(slotKey)) return EQUIMENT_SLOT.OTHER_MELEE;
      else if ("Weapon_Ranged".equals(slotKey)) return EQUIMENT_SLOT.RANGED;
      else if ("CraftTool".equals(slotKey)) return EQUIMENT_SLOT.TOOL;
      else if ("Class".equals(slotKey)) return EQUIMENT_SLOT.CLASS_ITEM;
      //else if ("Mounted".equals(slotKey)) return EQUIMENT_SLOT.BRIDLE;
    }
    return null;
  }
}
