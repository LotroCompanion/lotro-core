package delta.games.lotro.utils.dat;

import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;

/**
 * Misc enum utils.
 * @author DAM
 */
public class DatEnumsUtils
{
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
}
