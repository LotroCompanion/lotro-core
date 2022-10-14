package delta.games.lotro.character;

import java.util.HashSet;
import java.util.Set;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassesManager;
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
public class CharacterProficiencies
{
  /**
   * Get the set of weapons useable by a character class/level.
   * @param cClass Class.
   * @param level Level.
   * @return A set of weapons.
   */
  public static Set<WeaponType> getWeaponProficiencies(CharacterClass cClass, int level)
  {
    Set<WeaponType> ret=new HashSet<WeaponType>();
    ClassDescription classDescription=ClassesManager.getInstance().getClassDescription(cClass);
    if (classDescription!=null)
    {
      ret.addAll(classDescription.getProficiencies().getWeaponProficiencies().getEntries(level));
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
    Set<ArmourType> ret=new HashSet<ArmourType>();
    ClassDescription classDescription=ClassesManager.getInstance().getClassDescription(cClass);
    if (classDescription!=null)
    {
      ret.addAll(classDescription.getProficiencies().getArmourProficiencies().getEntries(level));
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
