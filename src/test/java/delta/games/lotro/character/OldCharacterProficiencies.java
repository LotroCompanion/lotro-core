package delta.games.lotro.character;

import java.util.HashSet;
import java.util.Set;

import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.lore.items.ArmourType;
import delta.games.lotro.lore.items.WeaponType;

/**
 * Character proficiencies.
 * <br>
 * This includes:
 * <ul>
 * <li>Weapons,
 * <li>Armour types,
 * <li>Dual wield.
 * </ul>
 * @author DAM
 */
public class OldCharacterProficiencies
{
  /**
   * Get the set of weapons useable by a character class/level.
   * @param cClass Class.
   * @param level Level.
   * @return A set of weapons.
   */
  public static Set<WeaponType> getWeaponProficiencies(CharacterClass cClass, int level)
  {
    HashSet<WeaponType> ret=new HashSet<WeaponType>();
    if (cClass==CharacterClass.CHAMPION)
    {
      ret.add(WeaponType.DAGGER);
      ret.add(WeaponType.ONE_HANDED_AXE);
      ret.add(WeaponType.ONE_HANDED_CLUB);
      ret.add(WeaponType.ONE_HANDED_HAMMER);
      ret.add(WeaponType.ONE_HANDED_MACE);
      ret.add(WeaponType.ONE_HANDED_SWORD);
      if (level>=10)
      {
        ret.add(WeaponType.SPEAR);
        ret.add(WeaponType.TWO_HANDED_AXE);
        ret.add(WeaponType.TWO_HANDED_HAMMER);
        ret.add(WeaponType.TWO_HANDED_SWORD);
      }
      if (level>=7)
      {
        ret.add(WeaponType.BOW);
        ret.add(WeaponType.CROSSBOW);
      }
    }
    else if (cClass==CharacterClass.GUARDIAN)
    {
      ret.add(WeaponType.DAGGER);
      ret.add(WeaponType.ONE_HANDED_AXE);
      ret.add(WeaponType.ONE_HANDED_CLUB);
      ret.add(WeaponType.ONE_HANDED_HAMMER);
      ret.add(WeaponType.ONE_HANDED_MACE);
      ret.add(WeaponType.ONE_HANDED_SWORD);
      ret.add(WeaponType.SPEAR);
      ret.add(WeaponType.TWO_HANDED_AXE);
      ret.add(WeaponType.TWO_HANDED_CLUB);
      ret.add(WeaponType.TWO_HANDED_HAMMER);
      ret.add(WeaponType.TWO_HANDED_SWORD);
      if (level>=7)
      {
        ret.add(WeaponType.BOW);
        ret.add(WeaponType.CROSSBOW);
      }
    }
    else if (cClass==CharacterClass.CAPTAIN)
    {
      ret.add(WeaponType.DAGGER);
      ret.add(WeaponType.ONE_HANDED_AXE);
      ret.add(WeaponType.ONE_HANDED_CLUB);
      ret.add(WeaponType.ONE_HANDED_HAMMER);
      ret.add(WeaponType.ONE_HANDED_MACE);
      ret.add(WeaponType.ONE_HANDED_SWORD);
      ret.add(WeaponType.TWO_HANDED_AXE);
      ret.add(WeaponType.TWO_HANDED_CLUB);
      ret.add(WeaponType.TWO_HANDED_HAMMER);
      ret.add(WeaponType.TWO_HANDED_SWORD);
      if (level>=10)
      {
        ret.add(WeaponType.SPEAR);
      }
      if (level>=15)
      {
        ret.add(WeaponType.HALBERD);
      }
    }
    else if (cClass==CharacterClass.HUNTER)
    {
      ret.add(WeaponType.BOW);
      ret.add(WeaponType.DAGGER);
      ret.add(WeaponType.ONE_HANDED_AXE);
      ret.add(WeaponType.ONE_HANDED_SWORD);
      if (level>=7)
      {
        ret.add(WeaponType.CROSSBOW);
      }
      if (level>=15)
      {
        ret.add(WeaponType.ONE_HANDED_CLUB);
        ret.add(WeaponType.ONE_HANDED_HAMMER);
        ret.add(WeaponType.ONE_HANDED_MACE);
      }
      if (level>=20)
      {
        ret.add(WeaponType.SPEAR);
      }
    }
    else if (cClass==CharacterClass.BURGLAR)
    {
      ret.add(WeaponType.DAGGER);
      ret.add(WeaponType.ONE_HANDED_CLUB);
      ret.add(WeaponType.ONE_HANDED_MACE);
      ret.add(WeaponType.ONE_HANDED_SWORD);
    }
    else if (cClass==CharacterClass.BEORNING)
    {
      ret.add(WeaponType.BOW);
      ret.add(WeaponType.CROSSBOW);
      ret.add(WeaponType.DAGGER);
      ret.add(WeaponType.ONE_HANDED_AXE);
      ret.add(WeaponType.ONE_HANDED_CLUB);
      ret.add(WeaponType.SPEAR);
      ret.add(WeaponType.TWO_HANDED_AXE);
      ret.add(WeaponType.TWO_HANDED_CLUB);
    }
    else if (cClass==CharacterClass.MINSTREL)
    {
      ret.add(WeaponType.DAGGER);
      ret.add(WeaponType.ONE_HANDED_CLUB);
      ret.add(WeaponType.ONE_HANDED_MACE);
      ret.add(WeaponType.ONE_HANDED_SWORD);
    }
    else if (cClass==CharacterClass.RUNE_KEEPER)
    {
      ret.add(WeaponType.RUNE_STONE);
    }
    else if (cClass==CharacterClass.LORE_MASTER)
    {
      if (level>=40)
      {
        ret.add(WeaponType.ONE_HANDED_SWORD);
      }
      ret.add(WeaponType.STAFF);
    }
    else if (cClass==CharacterClass.WARDEN)
    {
      ret.add(WeaponType.ONE_HANDED_CLUB);
      ret.add(WeaponType.DAGGER);
      ret.add(WeaponType.JAVELIN);
      ret.add(WeaponType.ONE_HANDED_AXE);
      ret.add(WeaponType.ONE_HANDED_HAMMER);
      ret.add(WeaponType.ONE_HANDED_MACE);
      ret.add(WeaponType.SPEAR);
      ret.add(WeaponType.ONE_HANDED_SWORD);
    }
    else if (cClass==CharacterClass.BRAWLER)
    {
      ret.add(WeaponType.BATTLE_GAUNTLETS);
    }
    return ret;
  }

  /**
   * Indicates if the given character class/level can dual wield or not.
   * @param cClass Class.
   * @param level Level.
   * @return <code>true</code> if dual wield is allowed, <code>false</code> otherwise.
   */
  public boolean isDualWielding(CharacterClass cClass, int level)
  {
    // See trait: 1879064092 Dual Wielding
    /*
Mod_Array: 
  #1: Mod_Entry 
    Inventory_AllowSecondaryWeapon: 1
    Mod_DescriptionOverride: 
    Mod_Modified: 268439425 (Inventory_AllowSecondaryWeapon)
    Mod_Op: 5 (Set)
     */
    if ((cClass==CharacterClass.CHAMPION) || (cClass==CharacterClass.BEORNING)
        || (cClass==CharacterClass.BURGLAR)|| (cClass==CharacterClass.HUNTER))
    {
      return true;
    }
    if ((cClass==CharacterClass.LORE_MASTER) && (level>=40))
    {
      return true;
    }
    return false;
  }

  /**
   * Get the set of armour types useable by a character class/level.
   * @param cClass Class.
   * @param level Level.
   * @return A set of armour types.
   */
  public static Set<ArmourType> getArmourProficiencies(CharacterClass cClass, int level)
  {
    HashSet<ArmourType> ret=new HashSet<ArmourType>();
    ret.add(ArmourType.LIGHT);
    if ((cClass==CharacterClass.HUNTER) || (cClass==CharacterClass.BURGLAR)
        || (cClass==CharacterClass.WARDEN))
    {
      ret.add(ArmourType.MEDIUM);
    }
    if ((cClass==CharacterClass.CHAMPION) || (cClass==CharacterClass.GUARDIAN)
        || (cClass==CharacterClass.CAPTAIN) || (cClass==CharacterClass.BEORNING)
        || (cClass==CharacterClass.BRAWLER) )
    {
      ret.add(ArmourType.MEDIUM);
      ret.add(ArmourType.HEAVY);
    }
    if (cClass==CharacterClass.GUARDIAN)
    {
      ret.add(ArmourType.SHIELD);
      ret.add(ArmourType.HEAVY_SHIELD);
    }
    if (cClass==CharacterClass.WARDEN)
    {
      ret.add(ArmourType.SHIELD);
      ret.add(ArmourType.WARDEN_SHIELD);
    }
    if ((cClass==CharacterClass.BEORNING) && (level>=15))
    {
      ret.add(ArmourType.HEAVY);
    }
    if ((cClass==CharacterClass.CAPTAIN) && (level>=15))
    {
      ret.add(ArmourType.SHIELD);
    }
    if (cClass==CharacterClass.MINSTREL)
    {
      if (level>=20)
      {
        ret.add(ArmourType.SHIELD);
      }
    }
    return ret;
  }

  /**
   * Get the armour type to use for mitigations.
   * @param cClass Character class.
   * @return An armour type (light/medium/heavy).
   */
  public static ArmourType getArmourTypeForMitigations(CharacterClass cClass)
  {
    /*
     * See class properties:
AdvTable_ArmorDefense_Points_CalcType: 14 (HeavyArmorDefense)
AdvTable_ArmorDefense_Points_NonCommon_CalcType: 14 (HeavyArmorDefense)
     */
    if ((cClass==CharacterClass.HUNTER) || (cClass==CharacterClass.BURGLAR)
        || (cClass==CharacterClass.WARDEN))
    {
      return ArmourType.MEDIUM;
    }
    if ((cClass==CharacterClass.CHAMPION) || (cClass==CharacterClass.GUARDIAN)
        || (cClass==CharacterClass.CAPTAIN) || (cClass==CharacterClass.BEORNING)
        || (cClass==CharacterClass.BRAWLER) )
    {
      return ArmourType.HEAVY;
    }
    return ArmourType.LIGHT;
  }
}
